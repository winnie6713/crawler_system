/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.crawler.database;


import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;
import wuit.common.doc.xml.ParseKeyValueXml;

/**
 *
 * @author lxl
 */
public class ExtractFieldRules {
//    private static final String pathFileRuler = "D:\\Projects\\CrawlerSystem\\config\\jobs\\extractFields.xml";
//    private static final String pathFileClear = "D:\\Projects\\CrawlerSystem\\config\\jobs\\clearHtml.xml";
//    private static final String pathFileDefault = "D:\\Projects\\CrawlerSystem\\config\\jobs\\defaultExtractFields.xml";
    public Map<String,List<KeyValue>> mapExtracts = new HashMap<String,List<KeyValue>>();
    public List<KeyValue> clearHtml = new ArrayList<KeyValue>();
    public List<KeyValue> defaultFields = new ArrayList<KeyValue>();
    
    public ExtractFieldRules(){
        //parseExtractLib();
        
//        getExtractItemRules();
        
//        parseExtractDefault();
//        parseClearHtml();
    }
    
    /*
    private void parseExtractLib(){
        Document doc = null;
        try{
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(pathFileRuler));
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
    */

    /*
    private void parseExtractDefault(){
        Document doc = null;
        try{
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(pathFileDefault));
        }catch(DocumentException e) {
            System.out.println(e.getMessage());
        }
        if(doc == null)
            return;
        List list = doc.selectNodes("//root/extractor");
        for(int i=0;i<list.size();i++){
            Element ele = (Element)list.get(i);
            List<?> childlist = ele.selectNodes("./fields/child::*");
            for(int j =0 ;j<childlist.size();j++){
                Element eleChild = (Element)childlist.get(j);
                KeyValue vals = new KeyValue();
                vals.key = eleChild.attributeValue("key");
                vals.value = eleChild.getTextTrim();
                defaultFields.add(vals);
            }
//            mapExtracts.put(key, keyVals);
        }        
    }    
    */
    /*
    public void getExtractItemRules(){        
        List<PageFeatureInfo> RS = new ArrayList<PageFeatureInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageFeatures(RS);
        for(int i = 0; i< RS.size();i++){
            String xml = RS.get(i).featrue;
//            System.out.print(xml);
            Document doc = null;
            try{
                if(xml==null || xml.equals(""))
                    continue;
                StringReader read = new StringReader(xml);
                InputSource source = new InputSource(read);
                //SAXBuilder sb = new SAXBuilder();
                
                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(source);                
                List list = doc.selectNodes("/features/feature");
                for(int j=0; j<list.size();j++){
                    Element ele = (Element)list.get(j);
                     List<?> childlist = ele.selectNodes("./child::*");
                     
                     String feature = ele.attributeValue("value");
                     if(feature == null || feature.equals(""))
                         continue;
                     List<KeyValue> keyVals = new ArrayList<KeyValue>();
                     
                     for(int n=0; n<childlist.size();n++){
                         Element eleChild = (Element)childlist.get(n);
                        KeyValue vals = new KeyValue();
                        vals.key = eleChild.attributeValue("name");
                        vals.value = eleChild.getTextTrim();
                        
                        keyVals.add(vals);

//                        System.out.println(feature + " : " + vals.key + " : " + vals.value);
                     }
                      mapExtracts.put(feature, keyVals);
                     
                }
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            }            
        }
        
    }
    */
    /*
    private void parseClearHtml(){
        if(pathFileClear.isEmpty())
            return;
        
        try {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(new File(pathFileClear));
            
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
               clearHtml.add(valObj);

            }
            KeyValueSort sort = new KeyValueSort();
            Collections.sort(clearHtml,sort);            
        } catch (DocumentException ex) {
            Logger.getLogger(ParseKeyValueXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    */
    public static void main(String[] args){
        ExtractFieldRules rules = new ExtractFieldRules();
//        rules.getExtractItemRules();
        /*
        ExtractFieldRules rules= new ExtractFieldRules();
        Set set = rules.mapExtracts.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,List<KeyValue>> body = (Entry<String,List<KeyValue>>)it.next();
            for (int i=0;i<body.getValue().size();i++){
                System.out.println(body.getKey() + "|" + body.getValue().get(i).key + "|"+ body.getValue().get(i).value);
            }
        }
        */
    }
}
