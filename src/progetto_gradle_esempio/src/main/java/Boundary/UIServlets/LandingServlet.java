package Boundary.UIServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Serve semplicemente per ridirezionare qualsiasi richiesta verso l'inidrizzo di base alla
    pagina .jsp che fa da landing page / home page per il sito
*/

@WebServlet(name = "LandingServlet", urlPatterns = {""})
public class LandingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    protected void handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect("/home/Home.jsp");
    }
}
