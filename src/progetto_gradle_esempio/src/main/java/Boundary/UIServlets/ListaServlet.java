package Boundary.UIServlets;

import Control.AccessProxy;
import Entity.Lista;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/*
    -Serve per gestite le richieste GET (visione delle pagine via browser) e POST (pressione dei pulsanti)
    destinate alla pagina relativa a una lista
*/

@WebServlet(name = "ListaServlet", urlPatterns = {"/user_logged_in/lista/"})
public class ListaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[ListaServlet - doGet]");

        // Ricava l'oggetto session
        HttpSession session = request.getSession();

        // -Controlla se l'AccessProxy relativo alla sessione è già inizializzato e in caso contrario
        // lo inizializza
        AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");
        if (access_proxy != null) {
            System.out.println("[ListaServlet - doGet] AccessProxy presente");
        } else {
            System.out.println("[ListaServlet - doGet] AccessProxy non presente");
            response.sendRedirect("/logout");
        }

        // Ricava le informazioni sull'utente, sulla lista e sul gruppo corrente dalla sessione
        Integer id_utente = (Integer) session.getAttribute("id_utente");
        Integer id_lista = (Integer) session.getAttribute("id_lista");
        Integer id_gruppo = (Integer) session.getAttribute("id_gruppo");
        String tipo_lista = (String) session.getAttribute("tipo_lista");

        // Verifica se l'operazione di visualizzazione è consentita
        if (tipo_lista != null) {
            if (tipo_lista.equals("Personale")){
                id_gruppo = 0; // oppure null
            }
        }
        ArrayList<Lista> liste = null;
        try {
            liste = access_proxy.GetList(id_utente,id_lista, id_gruppo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[ListaServlet - doGet] SQLException");
            response.sendRedirect("/user_logged_in/dashboard/");
        }

        String result = liste.get(0).getNome();
        if (result.equals("Permit")) {
            Lista lista = liste.get(1);

            // Preleva i nomi degli oggetti contenuti nella lista corrente e li inserisce nella richiesta
            // che poi verrà inoltrata alla pagina .jsp
            request.setAttribute("lista", lista);

            // Inoltra la richiesta GET alla pagina .jsp corrispondente
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_logged_in/lista/Lista.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        } else {
            System.out.println("[ListaServlet - doGet] Visualizzazione non autorizzata: "+result);

            response.sendRedirect("/user_logged_in/dashboard/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[ListaServlet - doPost]");

        // Ricava i parametri dalla richiesta POST
        String id_prodotto = request.getParameter("id_prodotto");
        String button_pushed = request.getParameter("button_pushed");

        // Ricava l'oggetto session
        HttpSession session = request.getSession();

        // Ricava le informazioni sull'utente, sulla lista e sul gruppo corrente dalla sessione
        Integer id_utente = (Integer) session.getAttribute("id_utente");
        Integer id_lista = (Integer) session.getAttribute("id_lista");
        Integer id_gruppo = (Integer) session.getAttribute("id_gruppo");
        String tipo_lista = (String) session.getAttribute("tipo_lista");

        // Ricava l'access proxy relativo alla sessione
        AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");

        // Effettua le operazioni opportune in base al pulsante premuto
        if (id_prodotto != null) {
            System.out.println("[ListaServlet - Rimuovi prodotto] id_prodotto: "+id_prodotto);

            // Converto l'attributo in intero
            Integer id_prodotto_int = Integer.parseInt(id_prodotto);

            if (tipo_lista != null) {
                if (tipo_lista.equals("Personale")){
                    id_gruppo = 0; // oppure null
                }
            }
            String result = null;
            try {
                result = access_proxy.RemoveElement(id_utente, id_lista, id_gruppo, id_prodotto_int);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("[ListaServlet - Rimuovi prodotto] SQLException");
                response.sendRedirect("/user_logged_in/lista/");
            }

            if (result.equals("Permit")) {//permit
                System.out.println("[ListaServlet - Rimuovi prodotto] id_prodotto: "+id_prodotto+" permesso");
                response.sendRedirect("/user_logged_in/lista/");
            } else if (result.equals("Deny")) {//deny
                System.out.println("[ListaServlet - Rimuovi prodotto] id_prodotto: "+id_prodotto+" negato");
                response.sendRedirect("/user_logged_in/permesso_negato/");
            } else if (result.equals("Indeterminate")) {//indeterminate
                System.out.println("[ListaServlet - Rimuovi prodotto] id_prodotto: "+id_prodotto+", valutazione xacml indeterminata");
                response.sendRedirect("/user_logged_in/lista/");
            } else if (result.equals("NotApplicable")) {//not applicable
                System.out.println("[ListaServlet - Rimuovi prodotto] id_prodotto: "+id_prodotto+", nessuna policy xacml applicabile");
                response.sendRedirect("/user_logged_in/lista/");
            }

        }
        if (button_pushed != null) {
            if (button_pushed.equals("rinomina_lista")) {
                System.out.println("[ListaServlet - Rinomina lista]");

                // TO DO (per la rinomina si dovrebbe andare a un'altra pagina dove inserire il nuovo nome)

                if (tipo_lista != null) {
                    if (tipo_lista.equals("Personale")){
                        id_gruppo = 0; // oppure null
                    }
                }
                String result = access_proxy.RenameList(id_utente, id_lista, id_gruppo, "nome");

                if (result.equals("Permit")) {//permit
                    System.out.println("[ListaServlet - Rinomina lista] Rinomina lista permesso");
                    response.sendRedirect("/user_logged_in/lista/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[ListaServlet - Rinomina lista] Rinomina lista negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[ListaServlet - Rinomina lista] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/lista/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[ListaServlet - Rinomina lista] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/lista/");
                }
            }
            else if (button_pushed.equals("elimina_lista")) {
                System.out.println("[ListaServlet - Elimina lista]");

                // TO DO (potrebbe non servire)
                // è stato inserito perché se seleziono una lista condivisa e dopo una personale,
                // manderò nella richiesta xacml l'id della lista personale con l'id del gruppo in cui
                // è contenuta l'altra lista
                if (tipo_lista != null) {
                    if (tipo_lista.equals("Personale")){
                        id_gruppo = 0; // oppure null
                    }
                }

                String result = null;
                try {
                    result = access_proxy.RemoveList(id_utente, id_lista,id_gruppo);
                    // TO DO (devo resettare l'attributo sennò ricaricando cerca di visualizzare una lista che non c'è più
                    // però anche se metto 0 cerca di visualizzare una lista non esistente)
                    // session.setAttribute("id_lista",0);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("[ListaServlet - Elimina lista] SQLException");
                    response.sendRedirect("/user_logged_in/lista/");
                }

                if (result.equals("Permit")) {//permit
                    System.out.println("[ListaServlet - Elimina lista] Elimina lista permesso");

                    // TO DO (oppure tornare alla pagina precedente: gruppo o dashboard)
                    response.sendRedirect("/user_logged_in/dashboard/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[ListaServlet - Elimina lista] Elimina lista negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[ListaServlet - Elimina lista] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/lista/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[ListaServlet - Elimina lista] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/lista/");
                }
            }
            else if (button_pushed.equals("aggiungi_oggetto")) {
                System.out.println("[ListaServlet - Aggiungi prodotto]");

                // TO DO (per aggiungere si dovrebbe andare a un'altra pagina dove inserire il nome dell'oggetto)

                if (tipo_lista != null) {
                    if (tipo_lista.equals("Personale")){
                        id_gruppo = 0; // oppure null
                    }
                }

                String result = null;
                try {
                    result = access_proxy.AddElement(id_utente, id_lista,id_gruppo,"Nuovo prodotto");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("[ListaServlet - Aggiungi prodotto] SQLException");
                    response.sendRedirect("/user_logged_in/lista/");
                }

                if (result.equals("Permit")) {//permit
                    System.out.println("[ListaServlet - Aggiungi prodotto] Aggiungi prodotto permesso");
                    response.sendRedirect("/user_logged_in/lista/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[ListaServlet - Aggiungi prodotto] Aggiungi prodotto negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[ListaServlet - Aggiungi prodotto] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/lista/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[ListaServlet - Aggiungi prodotto] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/lista/");
                }
            }
        }
    }
}