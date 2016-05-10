/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.crawler.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
public class ExtractFieldRules {
    private String pathFile = "D:\\Projects\\CrawlerSystem\\config\\jobs\\extractFields.xml";
    public Map<String,List<KeyValue>> mapExtracts = new HashMap<String,List<KeyValue>>();
    
    public ExtractFieldRules(){
        Document doc = null;
        try{
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(pathFile));
        }catch(DocumentException e) {
            System.out.println(e.getMessage());
        }
        if(doc == null)
            return;
        List list = doc.selectNodes("//root/extract");
        for(int i=0;i<list.size();i++){
            Element ele = (Element)list.get(i);
            Attribute attr = ele.attribute("key");
            String key = attr.getValue();
            attr = ele.attribute("id");
            String id = attr.getValue();
           
            List<?> childlist = ele.selectNodes("./fields/child::*");
            List<KeyValue> keyVals = new ArrayList<KeyValue>();
            for(int j =0 ;j<childlist.size();j++){
                Element eleChild = (Element)childlist.get(j);
                KeyValue vals = new KeyValue();
                vals.key = eleChild.attributeValue("key");
                vals.value = eleChild.getTextTrim();
                keyVals.add(vals);
            }
            mapExtracts.put(key, keyVals);
        }       
    }
    
    public void parseExtract(){
        
    }
    
    public static void main(String[] args){
        ExtractFieldRules rules= new ExtractFieldRules();
        Set set = rules.mapExtracts.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,List<KeyValue>> body = (Entry<String,List<KeyValue>>)it.next();
            for (int i=0;i<body.getValue().size();i++){
                System.out.println(body.getKey() + "|" + body.getValue().get(i).key + "|"+ body.getValue().get(i).value);
            }
        }
    }
}
