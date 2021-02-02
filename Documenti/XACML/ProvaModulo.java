// ALCUNE FONTI SU COME SCRIVERE IL MODULO
// AttributeFinderModule
// https://github.com/trustathsh/trustathsh-xacml-pdp/blob/6a2c80277dba1eb70aa98f8a48acdcd9760d5b2d/src/com/sun/xacml/finder/AttributeFinderModule.java
// CurrentEnvModule (esempio di modulo, relativo percò agli attributi di ambiente)
// https://github.com/trustathsh/trustathsh-xacml-pdp/blob/6a2c80277dba1eb70aa98f8a48acdcd9760d5b2d/src/com/sun/xacml/finder/impl/CurrentEnvModule.java
// esempio su Sun XACML docs
// http://sunxacml.sourceforge.net/guide.html#extending-finder

package ...;

import com.sun.xacml.EvaluationCtx;

// tutti i tipi di attributi necessari
import com.sun.xacml.attr.AttributeDesignator;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.attr.BagAttribute;

import com.sun.xacml.cond.EvaluationResult;

// per errori
import com.sun.xacml.ctx.Status;

// la classe astratta da cui parto per costruire il modulo
import com.sun.xacml.finder.AttributeFinderModule;

// per il formato URI
import java.net.URI;

// tipi di dati java standard che potrebbero essere utili
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProvaModulo extends AttributeFinderModule {

  // può essere utile definire qui gli AttributeId
  public static final String ATTRIBUTE_EXAMPLE ="urn:oasis:names:tc:xacml:1.0:environment:current-time";

  // ritorna sempre vero perché questo modulo sfrutta gli AttributeDesignator
  public boolean isDesignatorSupported() {
      return true;
  }

  /*
  // -forse non devo specificare perché gli attributi per cui scrivo il modulo non sono mai contenuti nella richiesta
  // e mi sembra di capire che questa funzione indica solo in quale tag cercare nella richiesta
  // -la funzione nella classe astratta fa return null, che indica di cercare ovunque

  // ritorna i tipi di AttributeDesignator supportati
  // in questo caso solo quelli di tipo resource
  public Set getSupportedDesignatorTypes() {
      HashSet set = new HashSet();
      set.add(new Integer(AttributeDesignator.RESOURCE_TARGET));
      return set;
  }
  */

  // restituisce gli attributi trovati sulla base delle informazioni fornite dal Designator
  public EvaluationResult findAttribute(URI attributeType, URI attributeId, URI issuer, URI subjectCategory, EvaluationCtx context,int designatorType) {

    // assicuriamoci che l'attributo sia riferito all'elemento corretto (subject,resource,action o environment)
    if (designatorType != AttributeDesignator.RESOURCE_TARGET)
      return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));

    // assicuriamoci che l'attributo richiesto sia proprio quello servito dal modulo
    if (!attributeId.toString().equals(ATTRIBUTE_EXAMPLE))
      return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));
    else {
      // estraiamo le informazioni necessarie dai valori passati alla funzione findAttribute
      String attrName = attributeId.toString();

      // qui ci sarà il codice per prendere il valore (o i valori) per quell'AttributeId per esempio dal database (con le funzioni di AceQL)
      // una volta ricavate le informazioni farò una return di un oggetto EvaluationResult

    }

    // se arrivo if we got here, then it's an attribute that we don't know
    return new EvaluationResult(BagAttribute.createEmptyBag(attributeType));
  }

}
