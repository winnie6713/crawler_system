/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class DSCompositeMapXml {
   public List<KeyValue> listFields = new ArrayList<KeyValue>();
   public Map<String,KeyValue> mapFields = new HashMap<String,KeyValue>();
   
   public DSCompositeMapXml(){
     String path = System.getProperty("user.dir");
     String pathFile = path + "\\config\\composite.xml";
     try{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(pathFile));
        List list = doc.selectNodes("//root/fields/field");
        Iterator it = list.iterator();
        while(it.hasNext()){
           Element element = (Element) it.next();
           Attribute attr_key = element.attribute("key");
           Attribute attr_order = element.attribute("id");
           
           KeyValue valObj = new KeyValue();
           valObj.key = attr_key.getValue();
//           valObj.value = element.getTextTrim();
           valObj.order = Integer.parseInt(attr_order.getValue());
           listFields.add(valObj);
           mapFields.put(valObj.key, valObj);
        }
     }catch(DocumentException e) {
         System.out.println(e.getMessage());
     }
   }
}
