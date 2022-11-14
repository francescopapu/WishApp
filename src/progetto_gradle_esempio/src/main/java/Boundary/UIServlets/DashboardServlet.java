package Boundary.UIServlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import Control.AccessProxy;

/*
    -Serve per gestite le richieste GET (visione delle pagine via browser) e POST (pressione dei pulsanti)
    destinate alla pagina relativa alla dashboard
*/

@WebServlet(name = "DashboardServlet", urlPatterns = {"/user_logged_in/dashboard/"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[DashboardServlet - doGet]");

        // -Controlla se l'AccessProxy relativo alla sessione è già inizializzato e in caso contrario
        // lo inizializza
        HttpSession session = request.getSession();
        AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");
        if (access_proxy != null) {
            System.out.println("[DashboardServlet - doGet] AccessProxy presente");
        } else {
            System.out.println("[DashboardServlet - doGet] AccessProxy non presente");
            response.sendRedirect("/logout");
        }

        // Ricavo l'id_utente dalla session
        Integer id_utente = (Integer) session.getAttribute("id_utente");

        // Preleva i nomi delle liste personali e dei gruppi personali (di proprietà e di cui è partecipante)
        // e li inserisce nella richiesta che poi verrà inoltrata alla pagina .jsp
        // TO DO (serve l'autorizzazione?)
        try {
            request.setAttribute("liste", access_proxy.GetPersonalLists(id_utente));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DashboardServlet - doGet] SQLException per GetPersonalLists");
            response.sendRedirect("/");
        }
        // TO DO (serve l'autorizzazione?)
        try {
            request.setAttribute("gruppi", access_proxy.GetPersonalGroups(id_utente));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DashboardServlet - doGet] SQLException per GetPersonalGroups");
            response.sendRedirect("/");
        }

        // Inoltra la richiesta GET alla pagina .jsp corrispondente
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user_logged_in/dashboard/Dashboard.jsp");
        if (dispatcher != null){
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[DashboardServlet - doPost]");

        // Estrae i parametri dalla richiesta POST
        String id_lista = request.getParameter("id_lista");
        String id_gruppo = request.getParameter("id_gruppo");
        String button_pushed = request.getParameter("button_pushed");

        // -Ricava l'oggetto session
        // -The HttpSession lives for as long as the client is interacting with the web app with the same browser
        // instance, and the session hasn't timed out at the server side. It is shared among all requests
        // in the same session.
        HttpSession session = request.getSession();

        // Ricavo l'id_utente dalla session
        Integer id_utente = (Integer) session.getAttribute("id_utente");

        // Effettua le operazioni opportune in base al pulsante premuto
        if (id_lista != null) {
            System.out.println("[DashboardServlet - Visualizza lista] id_lista: "+id_lista);

            // Converto l'attributo in intero
            Integer id_lista_int = Integer.parseInt(id_lista);

            // Inserisco nell'oggetto session l'id della lista corrente e il suo tipo
            session.setAttribute("id_lista", id_lista_int);
            session.setAttribute("tipo_lista", "Personale");

            response.sendRedirect("/user_logged_in/lista/");
        }
        if (id_gruppo != null) {
            System.out.println("[DashboardServlet - Visualizza gruppo] id_gruppo: "+id_gruppo);

            // Converto l'attributo in intero
            Integer id_gruppo_int = Integer.parseInt(id_gruppo);

            // Inserisco nell'oggetto session l'id del gruppo corrente
            session.setAttribute("id_gruppo",id_gruppo_int);

            response.sendRedirect("/user_logged_in/gruppo/");
        }
        if (button_pushed != null) {
            if (button_pushed.equals("nuova_lista")) {
                System.out.println("[DashboardServlet - Nuova lista]");

                AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");

                try {
                    // PARAMETRI STUB
                    access_proxy.NewList(id_utente,"Nuova lista");
                    System.out.println("[DashboardServlet - Nuova lista] 'Nuova lista' creata");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("[DashboardServlet - Nuova lista] SQLException");
                }

                response.sendRedirect("/user_logged_in/dashboard/");
            }
            else if (button_pushed.equals("nuovo_gruppo")) {
                System.out.println("[DashboardServlet - Nuovo gruppo]");

                AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");

                try {
                    // PARAMETRI STUB
                    access_proxy.NewGroup(id_utente,"Nuovo gruppo");
                    System.out.println("[DashboardServlet - Nuovo gruppo] 'Nuovo gruppo' creato");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println("[DashboardServlet - Nuovo gruppo] SQLException");
                }

                response.sendRedirect("/user_logged_in/dashboard/");
            }
        }
    }
}
