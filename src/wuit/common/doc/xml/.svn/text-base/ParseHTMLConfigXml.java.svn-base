/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import wuit.common.crawler.db.KeyValue;

/**
 *
 * @author lxl
 */
public class ParseHTMLConfigXml {
   public List<KeyValue> clearRules = new ArrayList<KeyValue>();
   public List<KeyValue> fieldRules = new ArrayList<KeyValue>(); 
   
   public ParseHTMLConfigXml(String pathFile){
     try{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(pathFile));
        
        List list = doc.selectNodes("//root/replaces/replace");
        Iterator it = list.iterator();
        while(it.hasNext()){
           Element element = (Element) it.next();
           Attribute attr_key = element.attribute("key");
           Attribute attr_order = element.attribute("order");
           
           KeyValue valObj = new KeyValue();
           valObj.key = attr_key.getValue();
           valObj.value = element.getTextTrim();
           valObj.order = Integer.parseInt(attr_order.getStringValue());
           clearRules.add(valObj);         
        }

        list = doc.selectNodes("//root/fields/field");	
        it = list.iterator();
        while(it.hasNext()){
           Element element = (Element) it.next();
           Attribute attr_key = element.attribute("key");
           KeyValue valObj = new KeyValue();
           valObj.key = attr_key.getValue();
           valObj.value = element.getTextTrim();
           valObj.order = -1;
           fieldRules.add(valObj);         
        }
      }catch(DocumentException e) {
         System.out.println(e.getMessage());
     }      
   }

    ParseHTMLConfigXml() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
