package Control.Authorization;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.attr.IntegerAttribute;
import com.sun.xacml.attr.StringAttribute;
import com.sun.xacml.ctx.*;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.*;

/*
    Inizializza il PDP e lo interroga, mediante delle funzioni che vengono invocate da un AccessProxy
*/
public class AuthorizationController {

    private PDP pdp;
    final private String hash_liste = "90b529791f9a4e6113c42c099a804fff5ecfff56e52ee4ba0d6ef0bc1d057b63";
    final private String hash_gruppi = "8c37366fdd20a6e17e00e4f46425b2a56ee1a76dc36985daa3d992bd08cf29b3";

    /*
    Serve per inizializzare il PDP
     */
    public AuthorizationController() {

        ArrayList<String> hashes = new ArrayList<String>();
        hashes.add(hash_gruppi);
        hashes.add(hash_liste);

        File[] listaFile;
        File f;
        String policyfile;
        FilePolicyModule policyModule = new FilePolicyModule();
        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();

        String PATH_POLICY = "/Users/frapiet/Desktop/progetto_gradle_esempio/src/main/resources/Policies"; //path della cartella contenente le policy
        File path_directory = new File(PATH_POLICY);
        listaFile = path_directory.listFiles(new FilenameFilter() {
            public boolean accept(File path_directory, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        for(int i=0;i<listaFile.length;i++)
        {
            f=listaFile[i];

            try {
                MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
                String shaChecksum = getFileChecksum(shaDigest, f);
                System.out.println("[AuthorizationController] hash:"+shaChecksum);
                System.out.println("[AuthorizationController] hash:"+hashes.get(i));
                if (hashes.get(i).equals(shaChecksum)) {
                    System.out.println("[AuthorizationController] hash uguali");

                    policyfile = f.getAbsolutePath();
                    policyModule.addPolicy(policyfile);
                    policyModules.add(policyModule);
                    policyFinder.setModules(policyModules);
                } else {
                    System.out.println("[AuthorizationController] hash diversi");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AttributeFinder attrFinder = new AttributeFinder();

        CurrentEnvModule envModule = new CurrentEnvModule();
        DBAttributeFinder dbAttributeFinder = new DBAttributeFinder();

        List attrModules = new ArrayList();
        attrModules.add(envModule);
        attrModules.add(dbAttributeFinder);

        attrFinder.setModules(attrModules);

        pdp = new PDP(new PDPConfig(attrFinder, policyFinder, null));
    }

    /*
    Serve per verificare il checksum del file xml
    */
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }

    /*
    Verifica l'autorizzazione alla visualizzazione di una lista da parte di un utente
     */
    public String ViewList(Integer id_utente, Integer id_lista, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_lista_attr_val = new IntegerAttribute(id_lista);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_lista_attr_val);
            resources.add(resource_id_attr);
            URI id_lista_uri = new URI("urn:progetto:names:id-lista");
            Attribute id_lista_attr = new Attribute(id_lista_uri,null,null,id_lista_attr_val);
            resources.add(id_lista_attr);

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Lista");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Visualizza");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

    /*
    Verifica l'autorizzazione all'eliminazione di una lista da parte di un utente
     */
    public String RemoveList(Integer id_utente, Integer id_lista, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_lista_attr_val = new IntegerAttribute(id_lista);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_lista_attr_val);
            resources.add(resource_id_attr);
            URI id_lista_uri = new URI("urn:progetto:names:id-lista");
            Attribute id_lista_attr = new Attribute(id_lista_uri,null,null,id_lista_attr_val);
            resources.add(id_lista_attr);

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Lista");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Elimina");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

    /*
    Verifica l'autorizzazione alla modifica (elimina, rinomina, aggiungi e rimuovi elementi)
    di una lista da parte di un utente
     */
    public String ModifyList(Integer id_utente, Integer id_lista, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_lista_attr_val = new IntegerAttribute(id_lista);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_lista_attr_val);
            resources.add(resource_id_attr);
            URI id_lista_uri = new URI("urn:progetto:names:id-lista");
            Attribute id_lista_attr = new Attribute(id_lista_uri,null,null,id_lista_attr_val);
            resources.add(id_lista_attr);

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Lista");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Modifica");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

    /*
    Verifica l'autorizzazione alla visualizzazione di un gruppo da parte di un utente
     */
    public String ViewGroup(Integer id_utente, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_gruppo_attr_val);
            resources.add(resource_id_attr);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Gruppo");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Visualizza");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

    /*
    Verifica l'autorizzazione all'eliminazione di un gruppo da parte di un utente
     */
    public String RemoveGroup(Integer id_utente, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_gruppo_attr_val);
            resources.add(resource_id_attr);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Gruppo");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Elimina");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

    /*
    Verifica l'autorizzazione alla modifica (aggiungi e rimuovi partecipanti, rinomina)
    di un gruppo da parte di un utente
     */
    public String ModifyGroup(Integer id_utente, Integer id_gruppo) {
        try {
            // costruisco i Subjects
            HashSet subjects = new HashSet();
            HashSet attributes = new HashSet();

            IntegerAttribute id_utente_attr_val = new IntegerAttribute(id_utente);
            URI id_utente_uri = new URI("urn:progetto:names:id-utente");
            Attribute id_utente_attr = new Attribute(id_utente_uri,null,null,id_utente_attr_val);
            attributes.add(id_utente_attr);

            subjects.add(new Subject(attributes));

            //Resources
            HashSet resources = new HashSet();

            IntegerAttribute id_gruppo_attr_val = new IntegerAttribute(id_gruppo);
            URI resource_id_uri = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
            Attribute resource_id_attr = new Attribute(resource_id_uri,null,null,id_gruppo_attr_val);
            resources.add(resource_id_attr);
            URI id_gruppo_uri = new URI("urn:progetto:names:id-gruppo");
            Attribute id_gruppo_attr = new Attribute(id_gruppo_uri,null,null,id_gruppo_attr_val);
            resources.add(id_gruppo_attr);

            StringAttribute resource_type_attr_val = new StringAttribute("Gruppo");
            URI resource_type_uri = new URI("urn:progetto:names:resource-type");
            Attribute resource_type_attr = new Attribute(resource_type_uri,null,null,resource_type_attr_val);
            resources.add(resource_type_attr);

            //Actions
            HashSet actions = new HashSet();

            StringAttribute action_type_attr_val = new StringAttribute("Modifica");
            URI action_type_uri = new URI("urn:progetto:names:action-type");
            Attribute action_type_attr = new Attribute(action_type_uri,null,null,action_type_attr_val);
            actions.add(action_type_attr);

            // Costruisce la richiesta
            RequestCtx XACMLrequest = new RequestCtx(subjects, resources, actions, new HashSet());

            // Interroga il pdp
            ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);

            // Elabora la decisione
            Set ris_set = XACMLresponse.getResults();
            Result ris = null;
            Iterator it = ris_set.iterator();

            while (it.hasNext()) {
                ris = (Result) it.next();
            }
            int dec = ris.getDecision();

            if (dec == 0) {//permit
                return "Permit";
            } else if (dec == 1) {//deny
                return "Deny";
            } else if (dec == 2) {//indeterminate
                return "Indeterminate";
            } else if (dec==3) {//not applicable
                return "NotApplicable";
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("[AuthorizationController] URISyntaxException");
            return null;
        }

        return null;
    }

}
