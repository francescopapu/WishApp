package Control;

import java.sql.*;
import java.util.*;

import Control.Authorization.AuthorizationController;

import Entity.*;

/*
    -Verifica che l'operazione richiesta sia autorizzata, mediante un oggetto AuthorizationController,
    e poi interagisce con il DB.

    MEMO: di volta in volta bisognerà levare le parole chiave static
*/

public class AccessProxy {

    private AuthorizationController controller = null;
    private Connection connection = null;

    public AccessProxy() {
        controller = new AuthorizationController();

        Properties properties = new Properties();

        properties.put("sslMode", "REQUIRED");

        properties.put("trustCertificateKeyStoreUrl","file:/Users/frapiet/Desktop/progetto_gradle_esempio/src/main/resources/Keystores/truststore_DB");
        properties.put("trustCertificateKeyStorePassword", "mypassword");

        properties.put("clientCertificateKeyStoreUrl", "file:/Users/frapiet/Desktop/progetto_gradle_esempio/src/main/resources/Keystores/keystore_DB");
        properties.put("clientCertificateKeyStorePassword", "mypassword");

        properties.put("user", "webserver");
        properties.put("password","webserver");

        String databaseURL = "jdbc:mysql://127.0.0.1:3306/progettossd";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, properties);
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.connection = conn;
    }


    // OPERAZIONI GENERALI ------------------------------------------------------------------------------------

    /*
    Serve per ricavare l'id_utente a partire dal campo sub dell'IdToken
     */
    public Integer GetUserId(String sub) throws SQLException {

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT ID FROM utente AS u WHERE u.sub = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setString(1, sub);

        Integer id_utente = null;

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            id_utente = rs.getInt("ID");

            System.out.println("[AccessProxy - GetUserId] id_utente: " + id_utente);
            System.out.println("[AccessProxy - GetUserId] sub: " + sub);
        }

        prepStatement.close();
        rs.close();

        return id_utente;
    }

    /*
    Serve per verificare se l'utente è amministratore a partire dal suo id
    */
    public Boolean isAdmin(Integer id_utente) throws SQLException {
        Boolean isAdmin = false;

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT IsAdmin FROM utente AS u WHERE u.ID = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_utente);

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            isAdmin = rs.getBoolean("IsAdmin");

            System.out.println("[AccessProxy - GetUserId] id_utente: " + id_utente);
            System.out.println("[AccessProxy - isAdmin] IsAdmin: " + isAdmin);
        }

        prepStatement.close();
        rs.close();

        return isAdmin;
    }

    // OPERAZIONI PER DASHBOARD.JSP ------------------------------------------------------------------------------------

    /*
    Serve per ricavare le liste di cui è proprietario l'utente
    (All'interno degli oggetti lista inizializza solo nome e id)

    TO DO (serve l'autorizzazione?)
    */
    public ArrayList<Lista> GetPersonalLists(Integer id_utente) throws SQLException {

        /*
        // STUB dell'accesso al DB
        String name_1 = new String("Nome lista 1");
        String name_2 = new String("Nome lista 2");
        String name_3 = new String("Nome lista 3");

        Integer id_1 = new Integer(123);
        Integer id_2 = new Integer(234);
        Integer id_3 = new Integer(456);

        Lista lista_1 = new Lista(id_1,name_1);
        Lista lista_2 = new Lista(id_2,name_2);
        Lista lista_3 = new Lista(id_3,name_3);

        ArrayList<Lista> lists = new ArrayList<Lista>();

        lists.add(lista_1);
        lists.add(lista_2);
        lists.add(lista_3);

        return lists;
         */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT ID, Nome FROM listadesideri AS lista WHERE lista.UtenteID = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_utente);

        ArrayList<Lista> liste = new ArrayList<Lista>();

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            Integer id_lista = rs.getInt("ID");
            String nome_lista = rs.getString("Nome");

            Lista nuova_lista = new Lista(id_lista,nome_lista);

            liste.add(nuova_lista);

            System.out.println("[AccessProxy - GetPersonalLists] id_lista: " + id_lista);
            System.out.println("[AccessProxy - GetPersonalLists] nome_lista: " + nome_lista);
        }

        prepStatement.close();
        rs.close();

        return liste;
    }

    /*
    Server per ricavare i gruppi di cui l'utente è proprietario o a cui partecipa
    (All'interno degli oggetti gruppo inizializza solo nome e id)

    TO DO (serve l'autorizzazione?)
    */
    public ArrayList<Gruppo> GetPersonalGroups(Integer id_utente) throws SQLException {

        /*
        // STUB dell'accesso al DB
        String name_1 = new String("Nome gruppo 1");
        String name_2 = new String("Nome gruppo 2");
        String name_3 = new String("Nome gruppo 3");

        Integer id_1 = new Integer(123);
        Integer id_2 = new Integer(234);
        Integer id_3 = new Integer(456);

        Gruppo gruppo_1 = new Gruppo(id_1,name_1);
        Gruppo gruppo_2 = new Gruppo(id_2,name_2);
        Gruppo gruppo_3 = new Gruppo(id_3,name_3);

        ArrayList<Gruppo> lists = new ArrayList<Gruppo>();

        lists.add(gruppo_1);
        lists.add(gruppo_2);
        lists.add(gruppo_3);

        return lists;
        */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT ID, Nome FROM gruppo AS g WHERE EXISTS (SELECT UtenteID, GruppoID FROM partecipazione AS p WHERE g.ID = p.GruppoID AND p.UtenteID = ?);";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_utente);

        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            Integer id_gruppo = rs.getInt("ID");
            String nome_gruppo = rs.getString("Nome");

            Gruppo nuovo_gruppo = new Gruppo(id_gruppo,nome_gruppo);

            gruppi.add(nuovo_gruppo);

            System.out.println("[AccessProxy - GetPersonalGroups] id_gruppo: " + id_gruppo);
            System.out.println("[AccessProxy - GetPersonalGroups] nome_gruppo: " + nome_gruppo);
        }

        prepStatement.close();
        rs.close();

        return gruppi;
    }

    /*
    Serve per inserire una nuova lista vuota nel DB

    TO DO (serve l'autorizzazione?)
     */
    public void NewList(Integer id_utente, String nome) throws SQLException {

        try {
            // ACCESSO AL DB
            // Costruisco la query SQL
            String sql = "INSERT INTO listadesideri (UtenteID, Nome) VALUES (?, ?);";
            PreparedStatement prepStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prepStatement.setInt(1, id_utente);
            prepStatement.setString(2, nome);

            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                Integer key = rs.getInt(1);
                System.out.println("[AccessProxy - NewList] Lista inserita con id: "+key);
            }

            prepStatement.close();

            System.out.println("[AccessProxy - NewList] Lista inserita");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[AccessProxy - NewList] SQLException");
            throw e;
        }
    }

    /*
    Serve per inserire un nuovo gruppo vuoto nel DB

    TO DO (serve l'autorizzazione?)
     */
    public void NewGroup(Integer id_utente, String nome) throws SQLException {

        connection.setAutoCommit(false);

        try {
            // Creo il nuovo gruppo in base al nome fornito
            String sql = "INSERT INTO gruppo (Nome) VALUES (?);";
            PreparedStatement prepStatement_1 = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prepStatement_1.setString(1,nome);

            prepStatement_1.executeUpdate();

            ResultSet rs_1 = prepStatement_1.getGeneratedKeys();
            Integer id_gruppo = null;
            if (rs_1 != null && rs_1.next()) {
                id_gruppo = rs_1.getInt(1);
            }

            prepStatement_1.close();
            rs_1.close();

            // Creo la partecipazione associata
            String sql_2 = "INSERT INTO partecipazione (UtenteProprietario, UtenteID, GruppoID) VALUES (?,?,?);";
            PreparedStatement prepStatement_2 = connection.prepareStatement(sql_2);
            prepStatement_2.setBoolean(1, true);
            prepStatement_2.setInt(2, id_utente);
            prepStatement_2.setInt(3, id_gruppo);

            prepStatement_2.executeUpdate();

            prepStatement_2.close();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("[AccessProxy - NewGroup] SQLException");
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

    }



    // OPERAZIONI PER LISTA.JSP ------------------------------------------------------------------------------------

    /*
    Serve per ottenere un'oggetto lista per la visualizzazione in Lista.jsp
    (Vengono inizializzati tutti i campi dell'oggetto lista)
    */
    public ArrayList<Lista> GetList(Integer id_utente, Integer id_lista, Integer id_gruppo) throws SQLException {
        // Verifica se l'operazione è consentita
        String result = controller.ViewList(id_utente,id_lista,id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // Inizializzo un ArrayList, inizialmente vuoto
        ArrayList<Lista> liste = new ArrayList<Lista>();

        // La prima lista contiene solo l'esito della valutazione xacml
        Integer id_result = new Integer(0);
        Lista lista_result = new Lista(id_result,result);
        liste.add(lista_result);

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            /*
            // STUB dell'accesso al DB
            String name = new String("Nome lista 1");
            Integer id = id_lista;
            ArrayList<Prodotto> products = new ArrayList<Prodotto>();
            String name_1 = new String("Nome oggetto 1");
            String name_2 = new String("Nome oggetto 2");
            String name_3 = new String("Nome oggetto 3");
            Integer id_1 = new Integer(123);
            Integer id_2 = new Integer(234);
            Integer id_3 = new Integer(456);
            Prodotto prodotto_1 = new Prodotto(id_1,name_1);
            Prodotto prodotto_2 = new Prodotto(id_2,name_2);
            Prodotto prodotto_3 = new Prodotto(id_3,name_3);
            products.add(prodotto_1);
            products.add(prodotto_2);
            products.add(prodotto_3);

            Lista lista = new Lista(id, name, products);

            lists.add(lista);
             */

            // ACCESSO DB
            connection.setAutoCommit(false);

            try {
                // Prendo il nome della lista
                String sql_1 = "SELECT Nome FROM listadesideri AS lista WHERE lista.ID = ?;";
                PreparedStatement prepStatement_1 = connection.prepareStatement(sql_1);
                prepStatement_1.setInt(1, id_lista);

                String nome_lista = new String();

                ResultSet rs_1 = prepStatement_1.executeQuery();
                while (rs_1.next()) {
                    nome_lista = rs_1.getString("Nome");

                    System.out.println("[AccessProxy - GetList] nome_lista: " + nome_lista);
                }

                prepStatement_1.close();
                rs_1.close();

                // Prendo gli oggetti nella lista
                String sql_2 = "SELECT * FROM prodotto AS pr WHERE EXISTS(SELECT * FROM listadesideri AS lista WHERE pr.ListaDesideriID = lista.ID AND pr.ListaDesideriID = ?);";
                PreparedStatement prepStatement_2 = connection.prepareStatement(sql_2);
                prepStatement_2.setInt(1, id_lista);

                ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

                ResultSet rs_2 = prepStatement_2.executeQuery();
                while (rs_2.next()) {
                    Integer id_prodotto = rs_2.getInt("ID");
                    String nome_prodotto = rs_2.getString("Nome");

                    Prodotto nuovo_prodotto = new Prodotto(id_prodotto,nome_prodotto);

                    prodotti.add(nuovo_prodotto);

                    System.out.println("[AccessProxy - GetList] id_prodotto: " + id_prodotto);
                    System.out.println("[AccessProxy - GetList] nome_prodotto: " + nome_prodotto);
                }

                prepStatement_2.close();
                rs_2.close();

                connection.commit();

                Lista lista_get = new Lista(id_lista,nome_lista,prodotti);

                liste.add(lista_get);

            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                System.out.println("[AccessProxy - NewList] SQLException");
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }

        return liste;
    }

    /*
    Serve per rimuovere una lista dal DB
     */
    public String RemoveList(Integer id_utente, Integer id_lista, Integer id_gruppo) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.RemoveList(id_utente,id_lista,id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                // PARAMETRI STUB
                String sql = "DELETE FROM listadesideri AS lista WHERE lista.ID = ?;";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_lista);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - RemoveList] Lista rimossa");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - RemoveList] SQLException");
                throw e;
            }
        }

        return result;
    }

    /*
    Serve per rinominare una lista nel DB
    TO DO: per implementarla completamente ci dovrebbe essere una pagina dove inserire il nuovo nome della lista
     */
    public String RenameList(Integer id_utente, Integer id_lista, Integer id_gruppo, String nuovo_nome) {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyList(id_utente,id_lista, id_gruppo);

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            // TO DO (OPERAZIONE DB)

        }

        return result;
    }

    /*
    Serve per aggiungere un elemento ad una lista nel DB
    TO DO: per implementarla completamente ci dovrebbe essere una pagina dove inserire il nome del nuovo oggetto
     */
    public String AddElement(Integer id_utente, Integer id_lista, Integer id_gruppo, String nome) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyList(id_utente,id_lista, id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                String sql = "INSERT INTO prodotto (ListaDesideriID, Nome) VALUES (?,?);";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_lista);
                prepStatement.setString(2, nome);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - AddElement] Prodotto inserito");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - AddElement] SQLException");
                throw e;
            }

        }

        return result;
    }

    /*
    Serve per rimuovere un elemento da una lista nel DB
     */
    public String RemoveElement(Integer id_utente, Integer id_lista, Integer id_gruppo, Integer id_elemento) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyList(id_utente,id_lista, id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                // PARAMETRI STUB
                String sql = "DELETE FROM prodotto WHERE ID=?";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_elemento);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - RemoveElement] Elemento rimosso");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - RemoveElement] SQLException");
                throw e;
            }
        }

        return result;
    }



    // OPERAZIONI PER GRUPPO.JSP ------------------------------------------------------------------------------------

    /*
    Serve per ottenere un'oggetto lista per la visualizzazione in Lista.jsp
    (Vengono inizializzati tutti i campi dell'oggetto lista)
    */
    public ArrayList<Gruppo> GetGroup(Integer id_utente, Integer id_gruppo) throws SQLException {
        // Verifica se l'operazione è consentita
        String result = controller.ViewGroup(id_utente,id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // Inizializzo un ArrayList, inizialmente vuoto
        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        // La prima lista contiene solo l'esito della valutazione xacml
        Integer id_result = new Integer(0);
        Gruppo gruppo_result = new Gruppo(id_result,result);
        gruppi.add(gruppo_result);

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            /*
            // STUB dell'accesso al DB
            Integer id = id_gruppo;
            String nome = new String("Nome gruppo 1");
            Gruppo gruppo = new Gruppo(id,nome);

            Integer id_1 = new Integer(123);
            Integer id_2 = new Integer(234);
            Integer id_3 = new Integer(456);
            String nome_1 = new String("Mario");
            String nome_2 = new String("Luigi");
            String nome_3 = new String("Cesare");
            String cognome_1 = new String("Monti");
            String cognome_2 = new String("Di Luigi");
            String cognome_3 = new String("Augusto");
            Utente utente_1 = new Utente(id_1,nome_1,cognome_1);
            Utente utente_2 = new Utente(id_2,nome_2,cognome_2);
            Utente utente_3 = new Utente(id_3,nome_3,cognome_3);

            // serve una query per le partecipazioni
            // devo essere sicuro che il proprietario sia impostato correttamente (forse bisogna mettere un'unica megaquery)
            ArrayList<Partecipazione> parts = new ArrayList<Partecipazione>();
            Integer id_1_par = new Integer(123);
            Integer id_2_par = new Integer(234);
            Integer id_3_par = new Integer(456);
            Partecipazione par_1 = new Partecipazione(id_1_par,true,gruppo,utente_1);
            Partecipazione par_2 = new Partecipazione(id_1_par,false,gruppo,utente_2);
            Partecipazione par_3 = new Partecipazione(id_1_par,false,gruppo,utente_3);
            parts.add(par_1);
            parts.add(par_2);
            parts.add(par_3);

            gruppo.setPartecipazione(parts);
            // non mi serve utente.setPartecipazione

            ArrayList<Lista> lists = new ArrayList<Lista>();

            String name_1 = new String("Nome lista 1");
            String name_2 = new String("Nome lista 2");
            String name_3 = new String("Nome lista 3");
            Integer id_1_lis = new Integer(123);
            Integer id_2_lis = new Integer(234);
            Integer id_3_lis = new Integer(456);
            Lista lista_1 = new Lista(id_1_lis,name_1);
            Lista lista_2 = new Lista(id_2_lis,name_2);
            Lista lista_3 = new Lista(id_3_lis,name_3);
            // devo essere sicuro che i proprietari delle liste siano impostati correttamente (forse bisogna mettere un'unica megaquery)
            lista_1.setProprietario(utente_1);
            lista_2.setProprietario(utente_2);
            lista_3.setProprietario(utente_3);

            lists.add(lista_1);
            lists.add(lista_2);
            lists.add(lista_3);

            gruppo.setListeCondivise(lists);

            groups.add(gruppo);
             */

            // ACCESSO DB
            connection.setAutoCommit(false);

            try {
                // Ricavo il nome del gruppo
                String sql_1 = "SELECT Nome FROM gruppo AS g WHERE g.ID = ?;";
                PreparedStatement prepStatement_1 = connection.prepareStatement(sql_1);
                prepStatement_1.setInt(1, id_gruppo);

                String nome_gruppo = new String();

                ResultSet rs_1 = prepStatement_1.executeQuery();
                while (rs_1.next()) {
                    nome_gruppo = rs_1.getString("Nome");

                    System.out.println("[AccessProxy - GetGroup] nome_gruppo: " + nome_gruppo);
                }

                prepStatement_1.close();
                rs_1.close();

                // costruisco l'unico oggetto gruppo
                Gruppo gruppo = new Gruppo(id_gruppo,nome_gruppo);

                // Ricavo tutto il resto
                String sql_2 = "SELECT g.ID AS 'IDgruppo', par.ID AS 'IDpartecipazione', par.UtenteProprietario AS 'isProprietario', u.ID AS 'IDutente', u.Nome, u.Cognome, gl.ListaDesideriID, lista.Nome AS 'NomeLista' FROM utente AS u INNER JOIN partecipazione AS par ON u.ID = par.UtenteID INNER JOIN gruppo AS g ON par.GruppoID = g.ID AND g.ID = ? INNER JOIN gruppo_listadesideri AS gl ON g.ID = gl.GruppoID AND g.ID = ? INNER JOIN listadesideri AS lista ON gl.ListaDesideriID = lista.ID WHERE par.UtenteID = lista.UtenteID;";
                PreparedStatement prepStatement_2 = connection.prepareStatement(sql_2);
                prepStatement_2.setInt(1, id_gruppo);
                prepStatement_2.setInt(2, id_gruppo);

                ArrayList<Lista> liste = new ArrayList<Lista>();
                ArrayList<Partecipazione> partecipazioni = new ArrayList<Partecipazione>();
                ArrayList<Utente> utenti = new ArrayList<Utente>();

                ResultSet rs_2 = prepStatement_2.executeQuery();
                while (rs_2.next()) {
                    Integer id_part = rs_2.getInt(2);
                    Boolean is_prop = rs_2.getBoolean(3);
                    Integer id_utente_part = rs_2.getInt(4);
                    String nome_utente_part = rs_2.getString(5);
                    String cognome_utente_part = rs_2.getString(6);
                    Integer id_lista = rs_2.getInt(7);
                    String nome_lista = rs_2.getString(8);

                    Utente nuovo_utente = new Utente(id_utente_part,nome_utente_part,cognome_utente_part);
                    int index = utenti.indexOf(nuovo_utente);
                    if (index >= 0) {
                        nuovo_utente = utenti.get(index);
                    } else {
                        utenti.add(nuovo_utente);
                    }

                    Partecipazione nuova_partecipazione = new Partecipazione(id_part, is_prop, gruppo, nuovo_utente);
                    partecipazioni.add(nuova_partecipazione);

                    Lista nuova_lista = new Lista(id_lista, nome_lista, nuovo_utente);

                    liste.add(nuova_lista);

                    System.out.println("[AccessProxy - GetGroup] id_part: " + id_part);
                    System.out.println("[AccessProxy - GetGroup] is_prop: " + is_prop);
                    System.out.println("[AccessProxy - GetGroup] id_utente_part: " + id_utente_part);
                    System.out.println("[AccessProxy - GetGroup] nome_utente_part: " + nome_utente_part);
                    System.out.println("[AccessProxy - GetGroup] cognome_utente_part: " + cognome_utente_part);
                    System.out.println("[AccessProxy - GetGroup] id_lista: " + id_lista);
                    System.out.println("[AccessProxy - GetGroup] nome_lista: " + nome_lista);
                }

                prepStatement_2.close();
                rs_2.close();

                connection.commit();

                gruppo.setPartecipazione(partecipazioni);
                gruppo.setListeCondivise(liste);

                gruppi.add(gruppo);

            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                System.out.println("[AccessProxy - GetGroup] SQLException");
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }

        return gruppi;
    }

    /*
    Serve per rinominare un gruppo sul DB
    TO DO: per implementarla completamente ci dovrebbe essere una pagina dove inserire il nuovo nome del gruppo
     */
    public String RenameGroup (Integer id_utente, Integer id_gruppo, String nuovo_nome) {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyGroup(id_utente,id_gruppo);

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            // TO DO (OPERAZIONE DB)

        }

        return result;
    }

    /*
    Serve per eliminare un gruppo sul DB
    */
    public String RemoveGroup (Integer id_utente, Integer id_gruppo_eliminato) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.RemoveGroup(id_utente,id_gruppo_eliminato);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                // PARAMETRI STUB
                String sql = "DELETE FROM gruppo AS g WHERE g.ID = ?;";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_gruppo_eliminato);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - RemoveGroup] Gruppo rimosso");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - RemoveGroup] SQLException");
                throw e;
            }
        }

        return result;
    }

    /*
    Serve per acccoppiare un partecipante a un gruppo sul DB
    TO DO: per implementarla completamente ci dovrebbe essere un modo per l'utente per cercare altri utenti e selezionarli
     */
    public String CouplePartecipant(Integer id_utente, Integer id_gruppo, Integer id_nuovo_utente) {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyGroup(id_utente,id_gruppo);

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            // TO DO (OPERAZIONE DB)

        }

        return result;
    }

    /*
    Serve per disaccoppiare un partecipante da un gruppo sul DB
     */
    public String DecouplePartecipant(Integer id_utente, Integer id_gruppo, Integer id_utente_eliminato) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyGroup(id_utente,id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                // PARAMETRI STUB
                String sql = "DELETE FROM partecipazione AS par WHERE par.GruppoID = ? AND par.UtenteID = ?;";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_gruppo);
                prepStatement.setInt(1, id_utente_eliminato);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - DecouplePartecipant] Partecipante rimosso");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - DecouplePartecipant] SQLException");
                throw e;
            }
        }

        return result;
    }

    /*
    Serve per disaccoppiare una lista condivisa in un gruppo sul DB

    TO DO: il comando SQL è errato
     */
    public String DecoupleList(Integer id_utente, Integer id_gruppo, Integer id_lista) throws SQLException {

        // Verifica se l'operazione è consentita
        String result = controller.ModifyGroup(id_utente,id_gruppo);

        /*
        // STUB AUTORIZZAZIONE
        result = "Permit";
         */

        // -Se l'operazione è consentita, interagisce con AceQL per eseguirla sul DB
        // -Altrimenti restituisce semplicemente il risultato dell'interrogazione XACML
        if (result.equals("Permit")) {

            try {
                // ACCESSO AL DB
                // Costruisco la query SQL
                String sql = "DELETE FROM gruppo_listadesideri AS gl WHERE gl.ListaDesideriID = ?;";
                PreparedStatement prepStatement = connection.prepareStatement(sql);
                prepStatement.setInt(1, id_lista);

                prepStatement.executeUpdate();
                prepStatement.close();

                System.out.println("[AccessProxy - DecoupleList] Lista rimossa");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[AccessProxy - DecoupleList] SQLException");
                throw e;
            }
        }

        return result;
    }

}
