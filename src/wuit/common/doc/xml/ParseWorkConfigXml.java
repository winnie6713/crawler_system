/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.dom4j.io.XMLWriter;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.crawler.db.KeyValue;

/**
 *
 * @author lxl
 */
public class ParseWorkConfigXml {
   private Document doc = null;
    public Document getDoc(){
       return doc;
    }
    
    public void parseWorks(String filePath){
        try{
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(filePath));
        }catch(DocumentException e) {
            System.out.println(e.getMessage());
        }
        List list = doc.selectNodes("//works/work");
        //初始化工作参数数组
        while(lstWorks.size()<list.size()){
            WorkInfo info = new WorkInfo();
            lstWorks.add(info);
        }
        //分别读取每个工作参数
        Iterator iter = list.iterator();
        int index = 0;
        while (iter.hasNext()) {
        	//读取工作名称和标识ID值
        	Element element = (Element) iter.next();
        	Attribute attribute = element.attribute("name");
        	lstWorks.get(index).workName = attribute.getValue();
        	attribute = element.attribute("id");
        	lstWorks.get(index).workId = attribute.getValue();
        	attribute = element.attribute("mode");
        	lstWorks.get(index).type = attribute.getValue();                
        	index++;
        }
        
        for(int i=0;i<lstWorks.size();i++){
        	parseWork(lstWorks.get(i).workId,lstWorks.get(i));
        }
    }
    
    public void parseWork(String id,WorkInfo info){
    	List<?> worklist = doc.selectNodes("//work[@id='"+id+"']");
    	Iterator<?> iterMap = worklist.iterator();
        while (iterMap.hasNext()) {
        	Element eleWork = (Element) iterMap.next();      	
        	List<?> childlist = eleWork.selectNodes("./mode/child::*");	            	
        	Iterator<?> iterChild = childlist.iterator();            	
        	while(iterChild.hasNext()){
        		Element eleChild = (Element)iterChild.next();
        		if (eleChild.getName().toLowerCase().equals("deepth"))
        			info.modeParam.deepth = eleChild.getText();
        		if (eleChild.getName().toLowerCase().equals("path_save"))
        			info.modeParam.path_save = eleChild.getText();
        		if (eleChild.getName().toLowerCase().equals("filters_url"))
        			info.modeParam.path_filter = eleChild.getText();
        		if (eleChild.getName().toLowerCase().equals("clearhtml"))
        			info.modeParam.path_clear = eleChild.getText();
                        if (eleChild.getName().toLowerCase().equals("url"))
        			info.modeParam.url = eleChild.getText();
        		if (eleChild.getName().toLowerCase().equals("task"))
        			info.modeParam.task = eleChild.getText();
        	}                
        	childlist = eleWork.selectNodes("./mode/keywords/child::*");	            	
        	iterChild = childlist.iterator(); 
        	while(iterChild.hasNext()){
        		Element eleChild = (Element)iterChild.next();
                        info.modeParam.keyWords.add(eleChild.getText());
        	}       	
                
                ParseKeyValueXml.parseMap(info.modeParam.path_filter, info.modeParam.mapFilterUrls);
                /*
        	childlist = eleWork.selectNodes("./mode/filters/child::*");	            	
        	iterChild = childlist.iterator(); 
        	while(iterChild.hasNext()){
        		Element eleChild = (Element)iterChild.next();
        		if (eleChild.getName().toLowerCase().equals("filter")){
                            Attribute attribute = eleChild.attribute("value");
                            info.modeParam.mapFilterUrls.put(eleChild.getText(),Integer.parseInt(attribute.getValue()));
                        }
        	} 
                

        	childlist = eleWork.selectNodes("./mode/fields/child::*");	            	
        	iterChild = childlist.iterator(); 
        	while(iterChild.hasNext()){
        		Element eleChild = (Element)iterChild.next();
        		if (eleChild.getName().toLowerCase().equals("field")){
                            Attribute attr_key = eleChild.attribute("key");
                            Attribute attr_order = eleChild.attribute("order");
                            
                            KeyValue valObj = new KeyValue();
                            valObj.key = attr_key.getValue();
                            valObj.value = eleChild.getTextTrim();
                            valObj.order = Integer.parseInt(attr_order.getStringValue());
                            
                            info.modeParam.mapFilterFields.put(attr_key.getValue(),valObj);
                        }
        	}*/
                
                
                ParseKeyValueXml.parseList(info.modeParam.path_clear, info.modeParam.listClearHtml);
                /*
        	childlist = eleWork.selectNodes("./extractor/replaces/replace");	            	
        	iterChild = childlist.iterator();            	
        	while(iterChild.hasNext()){
        		Element eleChild = (Element)iterChild.next();
        		KeyValue replace = new KeyValue();
        		replace.order =  Integer.parseInt(eleChild.attribute("order").getValue());
        		replace.key =  eleChild.attribute("key").getValue();
        		replace.value =  eleChild.attribute("value").getValue();        		
        		info.extract.lstReplaces.add(replace);
        	}
        	*/
        	childlist = eleWork.selectNodes("./extractor/fields/child::*");	            	
        	iterChild = childlist.iterator();            	
        	while(iterChild.hasNext()){
                    Element eleChild = (Element)iterChild.next();
                    KeyValue field = new KeyValue();
                    field.key = eleChild.getName();
                    field.value = eleChild.getText();
                    info.extract.mapFields.add(field);
                }
        }
    }
	
	class UrlInfo{
		public String title = "";
		public String url = "";
	}
	
	class WorkPath{
		public String content ="";
		public String pages = "";
		public String urls = "";
		
	}
	
	class WorkMode{
		public String task = "1";
		public String path_save = "";
                public String path_filter = "";
                public String path_clear = "";
		public String url = "";
		public String deepth = "";
		public List<String> keyWords = new ArrayList<String>();
		public Map<String,KeyValue> mapFilterUrls = new HashMap<String,KeyValue>();
                public List<KeyValue> listClearHtml = new ArrayList<KeyValue>();
//                public Map<String,KeyValue> mapFilterFields = new HashMap<String,KeyValue>();;
	}
	
	class WorkExtractor{
		//public List<KeyValue> lstReplaces = new ArrayList<KeyValue>();
		//public String section = "";
		public List<KeyValue> mapFields = new ArrayList<KeyValue>();	
	}
	
	public List<WorkInfo> lstWorks = new ArrayList<WorkInfo>();
	
	public WorkInfo workConfig = new WorkInfo();
	
	class WorkInfo{
		public String workName = "";
                public String type = "";
		public String workId = "";
//		public String path_save = "";
		public WorkMode modeParam = new WorkMode();
		public WorkExtractor extract = new WorkExtractor();
	}
	
	public DSExtractor getWorkParams(){
		DSExtractor info = new DSExtractor();
		info.listExtractFields = workConfig.extract.mapFields;				

		info.crawlerDeepth = Integer.parseInt(workConfig.modeParam.deepth);
		if (!workConfig.modeParam.task.equals(""))
			info.task = Integer.parseInt(workConfig.modeParam.task);
		else
			info.task = 1;
		info.title = workConfig.workName;
		info.CrawlerUrl = workConfig.modeParam.url;
		info.outPath = workConfig.modeParam.path_save;
		info.mapFilterUrl = workConfig.modeParam.mapFilterUrls;
//                info.mapFilterFields = workConfig.modeParam.mapFilterFields;
		info.crawlerMode = Integer.parseInt(workConfig.type);
		info.crawlerKeyWords = workConfig.modeParam.keyWords;
		info.listClearHtml = workConfig.modeParam.listClearHtml;
                /*
		for (int j=0;j<workConfig.extract.lstReplaces.size();j++){
			info.listClearHtml.add(workConfig.extract.lstReplaces.get(j));
		}
                */ 
                return info;
	}
	
	public List<DSExtractor> getWorksParams(){
		List<DSExtractor> _infos = new ArrayList<DSExtractor>();
		
		for (int i=0;i<lstWorks.size();i++){		
			DSExtractor info = new DSExtractor();
			info.listExtractFields = lstWorks.get(i).extract.mapFields;				

			info.crawlerDeepth = Integer.parseInt(lstWorks.get(i).modeParam.deepth);
			if (!workConfig.modeParam.task.equals(""))
				info.task = Integer.parseInt(lstWorks.get(i).modeParam.task);
			else
				info.task = 1;
			info.title = lstWorks.get(i).workName;
			info.CrawlerUrl = lstWorks.get(i).modeParam.url;
			info.outPath = lstWorks.get(i).modeParam.path_save;
			info.mapFilterUrl = lstWorks.get(i).modeParam.mapFilterUrls;
//                        info.mapFilterFields = lstWorks.get(i).modeParam.mapFilterFields;
			info.crawlerMode = Integer.parseInt(lstWorks.get(i).type);
			info.crawlerKeyWords = lstWorks.get(i).modeParam.keyWords;
			info.listClearHtml = lstWorks.get(i).modeParam.listClearHtml;
                        /*
			for (int j=0;j<lstWorks.get(i).extract.lstReplaces.size();j++){
				info.listClearHtml.add(lstWorks.get(i).extract.lstReplaces.get(j));
			}
                        */ 
			_infos.add(info);
		}		
		return _infos;
	}
	
	//////////////////////////////////////////////
    public void modifyDocument(File inputXml) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputXml);
            List<?> list = document.selectNodes("//students/student/@sn");
            Iterator<?> iter = list.iterator();
            while (iter.hasNext()) {
               Attribute attribute = (Attribute) iter.next();
               if (attribute.getValue().equals("01"))
                   attribute.setValue("001");
            }
  
            list = document.selectNodes("//students/student");
            iter = list.iterator();
            while (iter.hasNext()) {
               Element element = (Element) iter.next();
               Iterator<?> iterator = element.elementIterator("name");
               while (iterator.hasNext()) {
                   Element nameElement = (Element) iterator.next();
                   if (nameElement.getText().equals("sam"))
                      nameElement.setText("jeff");
               }
            }
  
            XMLWriter output = new XMLWriter(new FileWriter(new File(
                   "students-modified.xml")));
            output.write(document);
            output.close();
        }
  
        catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }
    
    public static void main(String[] argv) {
    	ParseWorkConfigXml parser = new ParseWorkConfigXml();
    	parser.parseWorks("d:\\product\\java\\jobs\\workconfig.xml");    	
    	System.out.println(parser.lstWorks.get(0).workName);
//    	System.out.println(parser.lstWorks.get(0).extract.lstReplaces.get(2).key);
    }
        
}
