package Boundary.UIServlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin_logged_in/dashboard/"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handle(req,res);
    }

    protected void handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect("/admin_logged_in/dashboard/DashboardAdmin.jsp");
    }
}
