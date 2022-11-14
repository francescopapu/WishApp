package Boundary.Authentication;

import com.auth0.SessionUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@WebFilter("/admin_logged_in/*")
public class Auth0AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String accessToken = (String) SessionUtils.get(req, "accessToken");
        String idToken = (String) SessionUtils.get(req, "idToken");

        if (accessToken == null && idToken == null) {
            res.sendRedirect("/login");
            return;
        }

        try {
            DecodedJWT id_token_jwt = JWT.decode(idToken);
            Date exp = id_token_jwt.getExpiresAt();
            Date current = new Date();

            System.out.println("[Auth0AdminFilter - doFilter] data attuale:"+current);
            System.out.println("[Auth0AdminFilter - doFilter] idToken exp:"+exp);

            if (current.after(exp)) {
                System.out.println("[Auth0AdminFilter - doFilter] idToken scaduto");
                res.sendRedirect("/login");
                return;
            }
        } catch (JWTDecodeException exception){
            System.out.println("[Auth0AdminFilter - doFilter] JWTDecodeException nella decodifica dell'idToken");
        }

        Boolean isAdmin = (Boolean) SessionUtils.get(req, "isAdmin");
        if (isAdmin) {
            System.out.println("[Auth0AdminFilter - doFilter] utente amministratore");
            next.doFilter(request, response);
        } else {
            System.out.println("[Auth0AdminFilter - doFilter] utente non amministratore");
            res.sendRedirect("/user_logged_in/dashboard/");
        }
    }

    @Override
    public void destroy() {
    }
}