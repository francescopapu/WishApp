package Boundary.Authentication;

import Control.AccessProxy;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * The Servlet endpoint used as the callback handler in the OAuth 2.0 authorization code grant flow.
 * It will be called with the authorization code after a successful login.
 */
@WebServlet(name = "CallbackServlet", urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {

    private String redirectOnSuccess;
    private String redirectOnSuccessAdmin;
    private String redirectOnFail;
    private AuthenticationController authenticationController;


    /**
     * Initialize this servlet with required configuration.
     * <p>
     * Parameters needed on the Local Servlet scope:
     * <ul>
     * <li>'com.auth0.redirect_on_success': where to redirect after a successful authentication.</li>
     * <li>'com.auth0.redirect_on_error': where to redirect after a failed authentication.</li>
     * </ul>
     * Parameters needed on the Local/Global Servlet scope:
     * <ul>
     * <li>'com.auth0.domain': the Auth0 domain.</li>
     * <li>'com.auth0.client_id': the Auth0 Client id.</li>
     * <li>'com.auth0.client_secret': the Auth0 Client secret.</li>
     * </ul>
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        redirectOnSuccess = "/user_logged_in/dashboard";
        redirectOnSuccessAdmin = "/admin_logged_in/dashboard/";
        redirectOnFail = "/login";

        try {
            authenticationController = AuthenticationControllerProvider.getInstance(config);
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Couldn't create the AuthenticationController instance. Check the configuration.", e);
        }
    }

    /**
     * Process a call to the redirect_uri with a GET HTTP method.
     *
     * @param req the received request with the tokens (implicit grant) or the authorization code (code grant) in the parameters.
     * @param res the response to send back to the server.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        handle(req, res);
    }


    /**
     * Process a call to the redirect_uri with a POST HTTP method. This occurs if the authorize_url included the 'response_mode=form_post' value.
     * This is disabled by default. On the Local Servlet scope you can specify the 'com.auth0.allow_post' parameter to enable this behaviour.
     *
     * @param req the received request with the tokens (implicit grant) or the authorization code (code grant) in the parameters.
     * @param res the response to send back to the server.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Tokens tokens = authenticationController.handle(req, res);
            SessionUtils.set(req, "accessToken", tokens.getAccessToken());
            SessionUtils.set(req, "idToken", tokens.getIdToken());

            String sub = null;
            Boolean isAdmin = false;

            // Estrazione del campo sub
            String id_token = tokens.getIdToken();
            System.out.println("[CallbackServlet - doPost] IdToken: "+id_token);
            try {
                DecodedJWT id_token_jwt = JWT.decode(id_token);
                sub = id_token_jwt.getSubject();
                System.out.println("[CallbackServlet - doPost] IdToken -> sub: "+sub);

            } catch (JWTDecodeException exception){
                System.out.println("[CallbackServlet - doPost] JWTDecodeException nella decodifica dell'IdToken");
                res.sendRedirect(redirectOnFail);
            }

            // -Controlla se l'AccessProxy relativo alla sessione è già inizializzato e in caso contrario
            // lo inizializza
            HttpSession session = req.getSession();
            AccessProxy access_proxy = (AccessProxy) session.getAttribute("access_proxy");
            if (access_proxy != null) {
                System.out.println("[CallbackServlet - doPost] AccessProxy presente");
            } else {
                access_proxy = new AccessProxy();
                session.setAttribute("access_proxy", access_proxy);
                System.out.println("[CallbackServlet - doPost] AccessProxy inizializzato");

                // Ricavo l'id_utente a partire dal campo sub dell'IdToken
                if (sub != null) {
                    try {
                        // Ricavo l'id_utente dal DB e lo inserisco nella sessione
                        Integer id_utente = access_proxy.GetUserId(sub);
                        session.setAttribute("id_utente", id_utente);
                        System.out.println("[CallbackServlet - doPost] id_utente:" + id_utente);

                        // Verifico se l'utente è amministratore e setto un attributo della session
                        try {
                            isAdmin = access_proxy.isAdmin(id_utente);
                            session.setAttribute("isAdmin", isAdmin);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            System.out.println("[CallbackServlet - doPost] isAdmin SQLException");

                            res.sendRedirect(redirectOnFail);
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        System.out.println("[CallbackServlet - doPost] id_utente SQLException");

                        res.sendRedirect(redirectOnFail);
                    }
                } else {
                    System.out.println("[DashboardServlet - doGet] sub = null");

                    res.sendRedirect(redirectOnFail);
                }
            }

            if (isAdmin) {
                res.sendRedirect(redirectOnSuccessAdmin);
            } else {
                res.sendRedirect(redirectOnSuccess);
            }

        } catch (IdentityVerificationException e) {
            e.printStackTrace();

            res.sendRedirect(redirectOnFail);
        }
    }

}
