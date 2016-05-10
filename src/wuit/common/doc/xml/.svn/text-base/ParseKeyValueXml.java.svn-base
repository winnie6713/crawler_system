/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;

/**
 *
 * @author lxl
 */
public class ParseKeyValueXml {
    
    public static void parseList(String pathFile,List<KeyValue> values){
        if(pathFile.isEmpty())
            return;
        
        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(new File(pathFile));
            
            List list = doc.selectNodes("//root/fields/field");
            Iterator it = list.iterator();
            while(it.hasNext()){
               Element element = (Element) it.next();
               Attribute attr_key = element.attribute("key");
               Attribute attr_order = element.attribute("order");
               
               KeyValue valObj = new KeyValue();
               valObj.key = attr_key.getValue();
               valObj.value = element.getTextTrim();
               valObj.order = Integer.parseInt(attr_order.getStringValue());
               values.add(valObj);

            }
            KeyValueSort sort = new KeyValueSort();
            Collections.sort(values,sort);            
        } catch (DocumentException ex) {
            Logger.getLogger(ParseKeyValueXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void parseMap(String pathFile,Map<String,KeyValue> values){
        if(pathFile.isEmpty())
            return;
        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(new File(pathFile));
            
            List list = doc.selectNodes("//root/fields/field");
            Iterator it = list.iterator();
            while(it.hasNext()){
               Element element = (Element) it.next();
               Attribute attr_key = element.attribute("key");
               Attribute attr_order = element.attribute("order");
               
               KeyValue valObj = new KeyValue();
               valObj.key = attr_key.getValue();
               valObj.value = element.getTextTrim();
               valObj.order = Integer.parseInt(attr_order.getStringValue());
               values.put(valObj.value, valObj);        
            }
        } catch (DocumentException ex) {
            Logger.getLogger(ParseKeyValueXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
