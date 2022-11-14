package Boundary.UIServlets;

import Control.AccessProxy;
import Entity.Gruppo;
import Entity.Partecipazione;
import Entity.Utente;

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
import java.util.LinkedHashSet;
import java.util.Set;

/*
    -Serve per gestite le richieste GET (visione delle pagine via browser) e POST (pressione dei pulsanti)
    destinate alla pagina relativa a un gruppo
*/

@WebServlet(name = "GruppoServlet", urlPatterns = {"/user_logged_in/gruppo/"})
public class GruppoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[GruppoServlet - doGet]");

        // Ricava l'oggetto session
        HttpSession session = request.getSession();

        // -Controlla se l'AccessProxy relativo alla sessione è già inizializzato e in caso contrario
        // lo inizializza
        AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");
        if (access_proxy != null) {
            System.out.println("[GruppoServlet - doGet] AccessProxy presente");
        } else {
            System.out.println("[GruppoServlet - doGet] AccessProxy non presente");
            response.sendRedirect("/logout");
        }

        // Ricava le informazioni sull'utente e sul gruppo corrente dalla sessione
        Integer id_utente = (Integer) session.getAttribute("id_utente");
        Integer id_gruppo = (Integer) session.getAttribute("id_gruppo");

        ArrayList<Gruppo> gruppi = null;
        try {
            gruppi = access_proxy.GetGroup(id_utente,id_gruppo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[GruppoServlet - doGet] SQLException");
            response.sendRedirect("/user_logged_in/dashboard/");
        }

        String result = gruppi.get(0).getNome();
        if (result.equals("Permit")) {
            Gruppo gruppo = gruppi.get(1);

            // Preleva il gruppo e lo inserisce nella richiesta
            // che poi verrà inoltrata alla pagina .jsp
            request.setAttribute("gruppo", gruppo);

            ArrayList<Partecipazione> partecipazioni = gruppo.getPartecipazione();
            Set<Utente> partecipanti = new LinkedHashSet<Utente>();
            for (Partecipazione par : partecipazioni) {
                partecipanti.add(par.getUtenteAssociato());
            }

            request.setAttribute("partecipanti", partecipanti);

            // Inoltra la richiesta GET alla pagina .jsp corrispondente
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_logged_in/gruppo/Gruppo.jsp");
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }
        } else {
            System.out.println("[GruppoServlet - doGet] Visualizzazione non autorizzata: "+result);

            response.sendRedirect("/user_logged_in/dashboard/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[GruppoServlet - doPost]");

        // Estrae i parametri dalla richiesta POST
        String id_partecipante = request.getParameter("id_partecipante");
        String rimuovi_id_lista = request.getParameter("rimuovi_id_lista");
        String visualizza_id_lista = request.getParameter("visualizza_id_lista");
        String button_pushed = request.getParameter("button_pushed");

        // Ricava l'oggetto session
        HttpSession session = request.getSession();

        // Ricava le informazioni sull'utente, sulla lista e sul gruppo corrente dalla sessione
        Integer id_utente = (Integer) session.getAttribute("id_utente");
        Integer id_gruppo = (Integer) session.getAttribute("id_gruppo");

        // Ricava l'access proxy relativo alla sessione
        AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");

        // Effettua le operazioni opportune in base al pulsante premuto
        if (id_partecipante != null) {
            System.out.println("[GruppoServlet - Disaccoppia partecipante] id_partecipante: "+id_partecipante);

            // Converto l'attributo in intero
            Integer id_partecipante_int = Integer.parseInt(id_partecipante);

            String result = null;
            try {
                result = access_proxy.DecouplePartecipant(id_utente, id_gruppo, id_partecipante_int);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("[GruppoServlet - Disaccoppia partecipante] SQLException");
                response.sendRedirect("/user_logged_in/gruppo/");
            }

            if (result.equals("Permit")) {//permit
                System.out.println("[GruppoServlet - Disaccoppia partecipante] Disaccoppia partecipante permesso");
                response.sendRedirect("/user_logged_in/gruppo/");
            } else if (result.equals("Deny")) {//deny
                System.out.println("[GruppoServlet - Disaccoppia partecipante] Disaccoppia partecipante negato");
                response.sendRedirect("/user_logged_in/permesso_negato/");
            } else if (result.equals("Indeterminate")) {//indeterminate
                System.out.println("[GruppoServlet - Disaccoppia partecipante] valutazione xacml indeterminata");
                response.sendRedirect("/user_logged_in/gruppo/");
            } else if (result.equals("NotApplicable")) {//not applicable
                System.out.println("[GruppoServlet - Disaccoppia partecipante] nessuna policy xacml applicabile");
                response.sendRedirect("/user_logged_in/gruppo/");
            }
        }
        if (rimuovi_id_lista != null) {
            System.out.println("[GruppoServlet - Disaccoppia lista] id_lista: "+rimuovi_id_lista);

            // Converto l'attributo in intero
            Integer id_lista_remove_int = Integer.parseInt(rimuovi_id_lista);

            String result = null;
            try {
                result = access_proxy.DecoupleList(id_utente, id_gruppo, id_lista_remove_int);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("[GruppoServlet - Disaccoppia lista] SQLException");
                response.sendRedirect("/user_logged_in/gruppo/");
            }

            if (result.equals("Permit")) {//permit
                System.out.println("[GruppoServlet - Disaccoppia lista] Disaccoppia lista permesso");
                response.sendRedirect("/user_logged_in/gruppo/");
            } else if (result.equals("Deny")) {//deny
                System.out.println("[GruppoServlet - Disaccoppia lista] Disaccoppia lista negato");
                response.sendRedirect("/user_logged_in/permesso_negato/");
            } else if (result.equals("Indeterminate")) {//indeterminate
                System.out.println("[GruppoServlet - Disaccoppia lista] valutazione xacml indeterminata");
                response.sendRedirect("/user_logged_in/gruppo/");
            } else if (result.equals("NotApplicable")) {//not applicable
                System.out.println("[GruppoServlet - Disaccoppia lista] nessuna policy xacml applicabile");
                response.sendRedirect("/user_logged_in/gruppo/");
            }
        }
        if (visualizza_id_lista != null) {
            System.out.println("[GruppoServlet - Visualizza lista] id_lista: "+visualizza_id_lista);

            // Converto l'attributo in intero
            Integer id_lista_view_int = Integer.parseInt(visualizza_id_lista);

            // Inserisco nell'oggetto session l'id della lista corrente e il suo tipo
            session.setAttribute("id_lista",id_lista_view_int);
            session.setAttribute("tipo_lista", "Condivisa");

            response.sendRedirect("/user_logged_in/lista/");
        }
        if (button_pushed != null) {
            if (button_pushed.equals("rinomina_gruppo")) {
                System.out.println("[GruppoServlet - Rinomina gruppo]");

                // TO DO (per la rinomina si dovrebbe andare a un'altra pagina dove inserire il nuovo nome)

                String result = access_proxy.RenameGroup(id_utente, id_gruppo, "nome");

                if (result.equals("Permit")) {//permit
                    System.out.println("[GruppoServlet - Rinomina gruppo] Rinomina gruppo permesso");
                    response.sendRedirect("/user_logged_in/gruppo/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[GruppoServlet - Rinomina gruppo] Rinomina gruppo negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[GruppoServlet - Rinomina gruppo] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/gruppo/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[GruppoServlet - Rinomina gruppo] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/gruppo/");
                }
            }
            else if (button_pushed.equals("elimina_gruppo")) {
                System.out.println("[GruppoServlet - Elimina gruppo]");

                String result = null;
                try {
                    result = access_proxy.RemoveGroup(id_utente, id_gruppo);
                    // TO DO (devo resettare l'attributo sennò ricaricando cerca di visualizzare un gruppo che non c'è più
                    // però anche se metto 0 cerca di visualizzare un gruppo non esistente)
                    // session.setAttribute("id_gruppo",0); // oppure null

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("[GruppoServlet - Elimina gruppo] SQLException");
                    response.sendRedirect("/user_logged_in/gruppo/");
                }

                if (result.equals("Permit")) {//permit
                    System.out.println("[GruppoServlet - Elimina gruppo] Elimina gruppo permesso");
                    response.sendRedirect("/user_logged_in/dashboard/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[GruppoServlet - Elimina gruppo] Elimina gruppo negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[GruppoServlet - Elimina gruppo] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/gruppo/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[GruppoServlet - Elimina gruppo] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/gruppo/");
                }
            }
            else if (button_pushed.equals("aggiungi_partecipante")) {
                System.out.println("[GruppoServlet - Aggiungi partecipante]");

                // TO DO (per implementarla completamente ci dovrebbe essere un modo per l'utente per cercare
                // altri utenti e selezionarli)

                String result = access_proxy.CouplePartecipant(id_utente, id_gruppo, 2);

                if (result.equals("Permit")) {//permit
                    System.out.println("[GruppoServlet - Aggiungi partecipante] Aggiungi partecipante permesso");
                    response.sendRedirect("/user_logged_in/gruppo/");
                } else if (result.equals("Deny")) {//deny
                    System.out.println("[GruppoServlet - Aggiungi partecipante] Aggiungi partecipante negato");
                    response.sendRedirect("/user_logged_in/permesso_negato/");
                } else if (result.equals("Indeterminate")) {//indeterminate
                    System.out.println("[GruppoServlet - Aggiungi partecipante] valutazione xacml indeterminata");
                    response.sendRedirect("/user_logged_in/gruppo/");
                } else if (result.equals("NotApplicable")) {//not applicable
                    System.out.println("[GruppoServlet - Aggiungi partecipante] nessuna policy xacml applicabile");
                    response.sendRedirect("/user_logged_in/gruppo/");
                }
            }
        }
    }
}