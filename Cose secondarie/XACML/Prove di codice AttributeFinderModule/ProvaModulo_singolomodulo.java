// ALCUNE FONTI SU COME SCRIVERE IL MODULO
// AttributeFinderModule (classe astratta da cui partire per il modulo)
// https://github.com/trustathsh/trustathsh-xacml-pdp/blob/6a2c80277dba1eb70aa98f8a48acdcd9760d5b2d/src/com/sun/xacml/finder/AttributeFinderModule.java
// CurrentEnvModule (esempio di modulo, relativo agli attributi di ambiente)
// https://github.com/trustathsh/trustathsh-xacml-pdp/blob/6a2c80277dba1eb70aa98f8a48acdcd9760d5b2d/src/com/sun/xacml/finder/impl/CurrentEnvModule.java
// SampleAttributeFinderModule (esempio su Balana XACML)
// https://github.com/wso2/balana/blob/master/modules/balana-samples/web-page-image-filtering/src/main/java/org/wso2/balana/samples/image/filtering/SampleAttributeFinderModule.java
// esempio su Sun XACML docs
// http://sunxacml.sourceforge.net/guide.html#extending-finder

// il package in cui è inserito il moudlo
package autorizzazione;

// tutti i tipi di attributi necessari
import com.sun.xacml.attr.AttributeDesignator;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.attr.BagAttribute;
import com.sun.xacml.attr.IntegerAttribute;
import com.sun.xacml.attr.StringAttribute;

import com.sun.xacml.cond.EvaluationResult;
import com.sun.xacml.EvaluationCtx;
// per errori
import com.sun.xacml.ctx.Status;
// la classe astratta da cui parto per costruire il modulo
import com.sun.xacml.finder.AttributeFinderModule;

// tipi di dati java standard che potrebbero essere utili
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProvaModulo extends AttributeFinderModule {

  // può essere utile definire qui gli AttributeId
  public static final String ATTRIBUTE_EXAMPLE_1 ="urn:progetto:names:id-proprietario-lista";

  // Returns true if this module supports retrieving attributes based on the
  // data provided in an AttributeDesignatorType. By default this method
  // returns false.
  public boolean isDesignatorSupported() {
      return true;
  }

  /*
  Returns a <code>Set</code> of <code>Integer</code>s that represent
  which AttributeDesignator types are supported (eg, Subject, Resource,
  etc.), or null meaning that no particular types are supported. A
  return value of null can mean that this module doesn't support
  designator retrieval, or that it supports designators of all types.
  If the set is non-null, it should contain the values specified in
  the <code>AttributeDesignator</code> *_TARGET fields.

  -ritorna i tipi di AttributeDesignator supportati
  -in questo caso solo quelli di tipo resource
  */
  public Set getSupportedDesignatorTypes() {
      HashSet set = new HashSet();
      set.add(new Integer(AttributeDesignator.RESOURCE_TARGET));
      return set;
  }

  /*
  Returns a <code>Set</code> of <code>URI</code>s that represent the
  attributeIds handled by this module, or null if this module doesn't
  handle any specific attributeIds. A return value of null means that
  this module will try to resolve attributes of any id.
  */
  public Set getSupportedIds() {
      HashSet set = new HashSet();
      try {
		set.add(new URI(ATTRIBUTE_EXAMPLE_1));
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return set;
    }

  /*
  Tries to find attribute values based on the given designator data.
  The result, if successful, must always contain a
  <code>BagAttribute</code>, even if only one value was found. If no
  values were found, but no other error occurred, an empty bag is
  returned. This method may need to invoke the context data to look
  for other attribute values, so a module writer must take care not
  to create a scenario that loops forever.
  */
  // restituisce gli attributi trovati sulla base delle informazioni fornite dal Designator
  public EvaluationResult findAttribute(URI attributeType, URI attributeId, URI issuer, URI subjectCategory, EvaluationCtx context, int designatorType) {
	  
	  URI uri_type_integer = null;
	  URI uri_id_lista = null;
	try {
		uri_type_integer = new URI("http://www.w3.org/2001/XMLSchema#integer");
		uri_id_lista = new URI("urn:progetto:names:id-lista");
	} catch (Exception e) {
		// non mi importa
	}
	
	
    if (designatorType != AttributeDesignator.RESOURCE_TARGET) {
      // assicuriamoci che l'attributo sia del tipo giusto (in questo esempio è di tipo resource)
      return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));

    } else if (!attributeId.toString().equals(ATTRIBUTE_EXAMPLE_1)) {
      // assicuriamoci che l'attributo richiesto sia proprio quello servito dal modulo
      return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));

    } else {
      // qui estraiamo le informazioni necessarie dai valori passati alla funzione findAttribute
    	
      /*
      // estraggo l'identificativo dell'attributo cercato (sotto forma di URI)
      String attributeid_st = attributeId.toString();

      // -ricavo le informazioni nella richiesta utilizzando il context (principalmente info dalla richiesta)
      // -in pratica viene chiamato l'AttributeFinderModule di base per ricavare gli AttributeValue dalla richiesta
      //  EvaluationResult getResourceAttribute(URI type, URI id, URI issuer)
      EvaluationResult attr_1_res = context.getResourceAttribute(type, id, issuer);
      EvaluationResult attr_2_res = context.getResourceAttribute(type, id, issuer);

      // ricavo gli AttributeValue (StringAttribute o IntegerAttribute) dagli oggetti EvaluationResult
      StringAttribute attr_1_at = (StringAttribute) attr_1_res.getAttributeValue();
      IntegerAttribute attr_2_at = (IntegerAttribute) attr_2_res.getAttributeValue();

      // estraggo stringhe e interi dagli AttributeValue
      String attr_1_st = attr_1_at.getValue();
      int attr_2_int = (int) attr_2_at.getValue();
      */
    	
    	EvaluationResult attr_1_res = context.getResourceAttribute(uri_type_integer, uri_id_lista, null);
    	BagAttribute bag =  (BagAttribute) attr_1_res.getAttributeValue();
    	Iterator<AttributeValue> it = bag.iterator();
    	IntegerAttribute attr_1_at = (IntegerAttribute) it.next();
    	int attr_1_int = (int) attr_1_at.getValue();
    	
    	System.out.println("id-lista dalla richiesta: "+attr_1_int);	
    	
      // -sulla base delle informazioni ricavate, di seguito vanno utilizzate le funzioni di AceQL
      // per ricavare informazioni dal DB
      // -immagino che ritornino degli oggetti che debbano essere poi convertiti
      // in AttributeValue appositi, e poi inseriri in un BagAttribute

      // stub (la collection verrà costruita utilizzando le funzioni di query dal DB di AceQL)
      Collection collection = new HashSet();

      Integer attr_3_int = 123;
      System.out.println("id-proprietario-lista dal DB: "+attr_3_int);
      IntegerAttribute attr_3_at = new IntegerAttribute(attr_3_int);

      collection.add(attr_3_at);

      BagAttribute bag_results = new BagAttribute(uri_type_integer, collection);

      return new EvaluationResult(bag_results);
    }
  }

}
