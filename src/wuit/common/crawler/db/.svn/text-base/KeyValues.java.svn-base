/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.crawler.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import wuit.common.crawler.db.KeyValueSort;

/**
 *
 * @author lxl
 */
public class KeyValues {
   Map<String,String> mapFields = new HashMap<String,String>();
   public List<KeyValue> listRules = new ArrayList<KeyValue>();
   
   public KeyValues(String xmlPathFile){
      try{			
         SAXReader saxReader = new SAXReader();
         Document doc = saxReader.read(new File(xmlPathFile));
         
        List list = doc.selectNodes("//root/fields/field");
        Iterator it = list.iterator();
        String key = "";
        String value = "";
        String order = "";
        while(it.hasNext()){
           Element element = (Element) it.next();
           Attribute attr_key = element.attribute("key");           
           key = attr_key.getStringValue();
           value = element.getText();
           Attribute attr_order = element.attribute("order");           
           order = attr_order.getStringValue();
           mapFields.put(key, value);
           
           KeyValue dsVal = new KeyValue();
           dsVal.key = key;
           if(!order.equals(""))
              dsVal.order = Integer.parseInt(order);
           dsVal.value = value;
           listRules.add(dsVal);
        }
        KeyValueSort _order = new KeyValueSort();
        Collections.sort(listRules,_order);
      }catch(DocumentException e) {
          System.out.println(e.getMessage());
      }      
   }
   
   public Map<String,String> getMapKeyValues(){
      return mapFields;
   }
   
   public List<KeyValue> getListKeyValues(){
      return listRules;
   }
}
