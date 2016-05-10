/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.database;

import com.mysql.jdbc.Statement;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static net.sourceforge.sizeof.test.TestSizeOf.print;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;

/**
 *
 * @author lxl
 */
public class ExtractorDB {
    public Map<String,List<KeyValue>> libRuler = new HashMap<String,List<KeyValue>>();  //位置信息提取规则库
    public List<KeyValue> clearHtml = new ArrayList<KeyValue>();                        //网页清理规则库
    public List<KeyValue> defaultRule = new ArrayList<KeyValue>();    

    public Map<String,KeyValue> mapFilterUrl = new HashMap<String,KeyValue>(); 
    
    //public List<KeyValue> clearHtml1 = new ArrayList<KeyValue>(); 
    
    public ExtractorDB(){
//        ExtractFieldRules rulers = new ExtractFieldRules();
        getPageClear(clearHtml);
        getPageFilters(mapFilterUrl);
        getfaultRule(defaultRule);
        getItemRules(libRuler);

    }   
    
    public synchronized void getPageClear(List<KeyValue> rulers){        
        List<PageLocalInfo> rs = new ArrayList<PageLocalInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageClear(rs);;
        for(int i = 0; i< rs.size();i++){
            String xml = rs.get(i).content;
            Document doc = null;
            try{
                if(xml==null || xml.equals(""))
                    continue;
                StringReader read = new StringReader(xml);
                InputSource source = new InputSource(read);                
                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(source);                
                List list = doc.selectNodes("/items/item");
                for(int j=0; j<list.size();j++){
                    Element ele = (Element)list.get(j);
                    KeyValue vals = new KeyValue();
                    vals.key = ele.attributeValue("key");
                    vals.order = Integer.parseInt(ele.attributeValue("order"));
                    vals.value = ele.getTextTrim();
                    rulers.add(vals);
                }
                //KeyValueSort sort = new KeyValueSort();
                //Collections.sort(rulers, sort);
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            } 
        }
    } 

    public synchronized void getPageFilters(Map<String,KeyValue> rulers){        
        List<PageLocalInfo> rs = new ArrayList<PageLocalInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageFilters(rs);
        for(int i = 0; i< rs.size();i++){
            String xml = rs.get(i).content;
            Document doc = null;
            try{
                if(xml==null || xml.equals(""))
                    continue;
                StringReader read = new StringReader(xml);
                InputSource source = new InputSource(read);
                //SAXBuilder sb = new SAXBuilder();
                
                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(source);                
                List list = doc.selectNodes("/fields/field");
                for(int j=0; j<list.size();j++){
                    Element ele = (Element)list.get(j);
                    KeyValue vals = new KeyValue();
                    vals.key = ele.attributeValue("key");
                    vals.value = ele.getTextTrim();
                    rulers.put(vals.value, vals);
                }
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            } 
        }
    }    
    
    public synchronized void getfaultRule(List<KeyValue> rulers){        
        List<PageLocalInfo> rs = new ArrayList<PageLocalInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageItems(rs);;
        for(int i = 0; i< rs.size();i++){
            String xml = rs.get(i).content;
            Document doc = null;
            try{
                if(xml==null || xml.equals(""))
                    continue;
                StringReader read = new StringReader(xml);
                InputSource source = new InputSource(read);
                //SAXBuilder sb = new SAXBuilder();
                
                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(source);                
                List list = doc.selectNodes("/fields/field");
                for(int j=0; j<list.size();j++){
                    Element ele = (Element)list.get(j);
                    KeyValue vals = new KeyValue();
                    vals.key = ele.attributeValue("key");
                    vals.value = ele.getTextTrim();
                    rulers.add(vals);
                }
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            } 
        }
    }     
    
    public synchronized void getItemRules(Map<String,List<KeyValue>> libRuler){        
        List<PageFeatureInfo> RS = new ArrayList<PageFeatureInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageFeatures(RS);
        for(int i = 0; i< RS.size();i++){
            String xml = RS.get(i).featrue;
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
                      libRuler.put(feature, keyVals);
                }
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            } 
        }
    }    
    
    public static void main(String [] args){
        ExtractorDB db = new ExtractorDB();
        List<KeyValue> rulers = new ArrayList<KeyValue>();
//        db.getPageFilters(rulers);
        
        rulers.size();
        
        for(int i=0; i<db.clearHtml.size();i++){
            System.out.println(db.clearHtml.get(i).key + " : " + db.clearHtml.get(i).value);
        }
        
        for(int i=0; i<rulers.size();i++){
            System.out.println(rulers.get(i).key + " : " + rulers.get(i).value);
        }
        
    }
            
}
