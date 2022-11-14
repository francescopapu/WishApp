package Control.Authorization;

//tutti i tipi di attributi necessari
import com.sun.xacml.attr.AttributeDesignator;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.attr.BagAttribute;
import com.sun.xacml.attr.IntegerAttribute;

import com.sun.xacml.cond.EvaluationResult;
import com.sun.xacml.EvaluationCtx;
//per errori
//la classe astratta da cui parto per costruire il modulo
import com.sun.xacml.finder.AttributeFinderModule;

//tipi di dati java standard che potrebbero essere utili
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

/*
    Serve per recuperare dal DB gli attributi non presenti nella richiesta XACML
*/

public class DBAttributeFinder extends AttributeFinderModule {

	// datatypes
	public static final String type_integer_string = "http://www.w3.org/2001/XMLSchema#integer";

	// AttributeId dell'utente
	public static final String id_utente_string = "urn:progetto:names:id-utente";

	// AttributeId per PolicyListe (richiesta)
	public static final String id_lista_string = "urn:progetto:names:id-lista";
	// AttributeId per PolicyListe (DB)
	public static final String id_prop_lista_string = "urn:progetto:names:id-proprietario-lista";
	public static final String id_liste_cond_string = "urn:progetto:names:id-liste-condivise";

	// AttributeId per PolicyGruppi (richiesta)
	public static final String id_gruppo_string = "urn:progetto:names:id-gruppo";
	// AttributeId per PolicyGruppi (DB)
	public static final String id_prop_gruppo_string = "urn:progetto:names:id-proprietario-gruppo";
	public static final String id_part_gruppo_string = "urn:progetto:names:id-partecipanti-gruppo";

	/*
	 * Returns true if this module supports retrieving attributes based on the data
	 * provided in an AttributeDesignatorType. By default this method returns false.
	 */
	public boolean isDesignatorSupported() {
		return true;
	}

	/*
	 * Returns a <code>Set</code> of <code>Integer</code>s that represent which
	 * AttributeDesignator types are supported (eg, Subject, Resource, etc.), or
	 * null meaning that no particular types are supported. A return value of null
	 * can mean that this module doesn't support designator retrieval, or that it
	 * supports designators of all types. If the set is non-null, it should contain
	 * the values specified in the <code>AttributeDesignator</code> *_TARGET fields.
	 */
	public Set getSupportedDesignatorTypes() {
		HashSet set = new HashSet();
		set.add(new Integer(AttributeDesignator.RESOURCE_TARGET));
		return set;
	}

	/*
	 * Tries to find attribute values based on the given designator data. The
	 * result, if successful, must always contain a <code>BagAttribute</code>, even
	 * if only one value was found. If no values were found, but no other error
	 * occurred, an empty bag is returned. This method may need to invoke the
	 * context data to look for other attribute values, so a module writer must take
	 * care not to create a scenario that loops forever.
	 */
	public EvaluationResult findAttribute(URI attributeType, URI attributeId, URI issuer, URI subjectCategory,
			EvaluationCtx context, int designatorType) {
		// ricavo l'attributeId dell'attributo cercato
		String attrId = attributeId.toString();

		// creo la connessione
		Connection connection = null;
		try {
			connection = ConnectionBuilder();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			System.out.println("[DBAttributeFinder] SQLException nella creazione della connessione");
			return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));
		}

		// chiamo la funzione che si occupa dell'attributo che cerco
		try {
			if (attrId.equals(id_prop_lista_string)) {
				return function_id_prop_lista(attributeType, attributeId, context, connection);
			} else if (attrId.equals(id_liste_cond_string)) {
				return function_id_liste_cond(attributeType, attributeId, context, connection);
			} else if (attrId.equals(id_prop_gruppo_string)) {
				return function_id_prop_gruppo(attributeType, attributeId, context, connection);
			} else if (attrId.equals(id_part_gruppo_string)) {
				return function_id_part_gruppo(attributeType, attributeId, context, connection);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			System.out.println("[DBAttributeFinder] SQLException nell'esecuzione delle query");
			return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));
		}

		// se arrivo qui significa che il modulo non conosce l'attributo cercato
		return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));
	}

	private Connection ConnectionBuilder () throws SQLException {

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
			throw throwables;
		}

		return conn;
	}

	/*
	 * Si occupa di recuperare id-proprietario-lista
	 */
	private EvaluationResult function_id_prop_lista(URI attributeType, URI attributeId, EvaluationCtx context, Connection connection) throws SQLException {
		// definisco gli URI che mi servono
		URI uri_type_integer = null;
		URI uri_id_lista = null;
		try {
			uri_type_integer = new URI(type_integer_string);
			uri_id_lista = new URI(id_lista_string);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// prelevo l'id-lista dalla richiesta
		int id_lista = getIntFromRequest(uri_type_integer, uri_id_lista, context);

		// inizializzo la Collection
		Collection collection = new HashSet();

		/*
		// STUB ACCESSO AL DB
		Integer id_prop_lista = 123;
		 */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT UtenteID FROM listadesideri AS lista WHERE lista.ID = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_lista);

        Integer id_prop_lista = null;

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            id_prop_lista = rs.getInt("UtenteID");

            System.out.println("[DBAttributeFinder - function_id_prop_lista] id_prop_lista: " + id_prop_lista);
        }

        prepStatement.close();
        rs.close();

		IntegerAttribute id_prop_lista_at = new IntegerAttribute(id_prop_lista);

		collection.add(id_prop_lista_at);

		BagAttribute bag_results = new BagAttribute(uri_type_integer, collection);

		return new EvaluationResult(bag_results);
	}

	/*
	 * Si occupa di recuperare id-liste-condivise (gli id delle liste condivise con
	 * un gruppo)
	 */
	private EvaluationResult function_id_liste_cond(URI attributeType, URI attributeId, EvaluationCtx context, Connection connection) throws SQLException {
		// definisco gli URI che mi servono
		URI uri_type_integer = null;
		URI uri_id_gruppo = null;
		try {
			uri_type_integer = new URI(type_integer_string);
			uri_id_gruppo = new URI(id_gruppo_string);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// prelevo l'id-gruppo dalla richiesta
		int id_gruppo = getIntFromRequest(uri_type_integer, uri_id_gruppo, context);

		// inizializzo la Collection
		Collection collection = new HashSet();

		/*
		// STUB ACCESSO AL DB
		Integer id_lista_1 = 234;
		Integer id_lista_2 = 456;
		Integer id_lista_3 = 678;

		IntegerAttribute id_lista_1_at = new IntegerAttribute(id_lista_1);
		IntegerAttribute id_lista_2_at = new IntegerAttribute(id_lista_2);
		IntegerAttribute id_lista_3_at = new IntegerAttribute(id_lista_3);

		collection.add(id_lista_1_at);
		collection.add(id_lista_2_at);
		collection.add(id_lista_3_at);
		 */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT ListaDesideriID FROM gruppo_listadesideri AS gl WHERE gl.GruppoID = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_gruppo);

		ArrayList<Integer> id_liste_cond = new ArrayList<Integer>();

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            Integer id_lista_cond = rs.getInt("ListaDesideriID");

            id_liste_cond.add(id_lista_cond);

            System.out.println("[DBAttributeFinder - function_id_liste_cond] id_lista_cond: " + id_lista_cond);
        }

        prepStatement.close();
        rs.close();

        for (Integer id_lista : id_liste_cond) {
			IntegerAttribute id_lista_at = new IntegerAttribute(id_lista);
			collection.add(id_lista_at);
        }

		BagAttribute bag_results = new BagAttribute(uri_type_integer, collection);

		return new EvaluationResult(bag_results);
	}

	/*
	 * Si occupa di recuperare id-proprietario-gruppo
	 */
	private EvaluationResult function_id_prop_gruppo(URI attributeType, URI attributeId, EvaluationCtx context, Connection connection) throws SQLException {
		// definisco gli URI che mi servono
		URI uri_type_integer = null;
		URI uri_id_gruppo = null;
		try {
			uri_type_integer = new URI(type_integer_string);
			uri_id_gruppo = new URI(id_gruppo_string);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// prelevo l'id-gruppo dalla richiesta
		int id_gruppo = getIntFromRequest(uri_type_integer, uri_id_gruppo, context);

		// inizializzo la Collection
		Collection collection = new HashSet();

		/*
		// STUB ACCESSO AL DB
		Integer id_prop_gruppo = 123;
		 */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT UtenteID FROM partecipazione AS p WHERE p.GruppoID = ? and p.UtenteProprietario = '1';";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_gruppo);

        Integer id_prop_gruppo = null;

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            id_prop_gruppo = rs.getInt("UtenteID");

            System.out.println("[DBAttributeFinder - function_id_prop_gruppo] id_prop_gruppo: " + id_prop_gruppo);
        }

        prepStatement.close();
        rs.close();

		IntegerAttribute id_prop_gruppo_at = new IntegerAttribute(id_prop_gruppo);

		collection.add(id_prop_gruppo_at);

		BagAttribute bag_results = new BagAttribute(uri_type_integer, collection);

		return new EvaluationResult(bag_results);
	}

	/*
	 * Si occupa di recuperare id-partecipanti-gruppo (gli id degli utenti
	 * partecipanti a un gruppo)
	 */
	private EvaluationResult function_id_part_gruppo(URI attributeType, URI attributeId, EvaluationCtx context, Connection connection) throws SQLException {
		// definisco gli URI che mi servono
		URI uri_type_integer = null;
		URI uri_id_gruppo = null;
		try {
			uri_type_integer = new URI(type_integer_string);
			uri_id_gruppo = new URI(id_gruppo_string);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// prelevo l'id-gruppo dalla richiesta
		int id_gruppo = getIntFromRequest(uri_type_integer, uri_id_gruppo, context);

		// inizializzo la Collection
		Collection collection = new HashSet();

		/*
		// STUB ACCESSO AL DB
		Integer id_utente_1 = 234;
		Integer id_utente_2 = 456;
		Integer id_utente_3 = 678;

		IntegerAttribute id_utente_1_at = new IntegerAttribute(id_utente_1);
		IntegerAttribute id_utente_2_at = new IntegerAttribute(id_utente_2);
		IntegerAttribute id_utente_3_at = new IntegerAttribute(id_utente_3);

		collection.add(id_utente_1_at);
		collection.add(id_utente_2_at);
		collection.add(id_utente_3_at);
		 */

        // ACCESSO AL DB
        // Costruisco la query SQL
        String sql = "SELECT UtenteID FROM partecipazione AS p WHERE p.GruppoID = ?;";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setInt(1, id_gruppo);

		ArrayList<Integer> id_utenti_part = new ArrayList<Integer>();

        ResultSet rs = prepStatement.executeQuery();
        while (rs.next()) {
            Integer id_utente_part = rs.getInt("UtenteID");

            id_utenti_part.add(id_utente_part);

            System.out.println("[DBAttributeFinder - function_id_part_gruppo] id_utente_part: " + id_utente_part);
        }

        prepStatement.close();
        rs.close();

        for (Integer id_utente : id_utenti_part) {
			IntegerAttribute id_utente_at = new IntegerAttribute(id_utente);
			collection.add(id_utente_at);
        }

		BagAttribute bag_results = new BagAttribute(uri_type_integer, collection);

		return new EvaluationResult(bag_results);
	}

	/*
	 * Funzione di supporto per prelevare il valore di un attributo intero nella
	 * richiesta
	 */
	private int getIntFromRequest(URI attributeType, URI attributeId, EvaluationCtx context) {
		EvaluationResult attr_res = context.getResourceAttribute(attributeType, attributeId, null);
		BagAttribute bag = (BagAttribute) attr_res.getAttributeValue();
		Iterator<AttributeValue> it = bag.iterator();
		IntegerAttribute attr_at = (IntegerAttribute) it.next();
		int attr_int = (int) attr_at.getValue();
		System.out.println("[DBAttributeFinder - getIntFromRequest] Attributo con Id "+attributeId.toString() + " dalla richiesta: " + attr_int);
		return attr_int;
	}
}
