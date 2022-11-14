package Boundary.UIServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PermessoNegatoServlet", urlPatterns = {"/user_logged_in/permesso_negato/"})
public class PermessoNegatoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    protected void handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect("/user_logged_in/permesso_negato/PermessoNegato.jsp");
    }
}
