package wuit.common.crawler.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import wuit.common.doc.FileIO;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.application.DataParam;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValues;

//extends DbConnect 
public class DbComposite {
   private int itemMax = 5000;
   private CompositeParse parse;
   
   private DataParam param = null;
//   private ClassifyByDistrictKeyFromCompositesFiles classify_Key = null;
//   private ClassifyByDistrictKeyFromPageTextFiles classify_Key_pageTxt = null;
//   private ClassifyByDistrictFromCompositesFiles classify = null;
//   private ClassifyByDistrictFromPageTextFiles classify_pageTxt = null;
//   private classifyAndComputeAddressComposite computeAddress = null;
//   private CompositeTxtToXML txtToXml = null;
//   private CompositeTxtToXMLClassify txtToXmlClass = null;
//   private ExtractFromText extractTxt = null;
   private ExtractFromHtml extractHtml = null;
   
//   private  CheckProvince check = null;
   
   public DbComposite(){
//       dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw);
       parse = new CompositeParse();
   }
   
   /*
   public void setStatus(int status){
      switch(param.mode){
         case 214:
            extractTxt.setStatus(status);            
            break;
         case 212:
            extractHtml.setStatus(status);
            break;
         case 221:
//            filterCompositeItems(param);
            break;
         case 222:
             check.setStatus(status);
            break;            
         case 233:
             if(classify_Key != null)
                     classify_Key.setStatus(status);
            break;
         case 241:
            txtToXml.setStatus(status);
            break;
         case 242:
            String tableName = "";
//            pageTxtFileToMySQLDB(param,tableName);
            break;
      }         
   }
   */
   

   /*
   public void CompositeService(DataParam param){
       this.param = param;
      switch(param.mode){
         case 214:
             extractTxt = new ExtractFromText();
             extractTxt.param = param;
             extractTxt.start();
            break;
         case 212:
             extractHtml = new ExtractFromHtml();
             extractHtml.param = param;
             extractHtml.start();
            break;
         case 221:
//            filterCompositeItems(param);
            break;
         case 222:
             check = new CheckProvince();
             check.param = param;
             check.start();
            break;  
         case 231:      //从包含url、items、content文件中提取DSComposite,并按指定的省份分类存储DSComposite记录。
             classify_pageTxt = new ClassifyByDistrictFromPageTextFiles();
             classify_pageTxt.param = param;
             classify_pageTxt.start();
            break;
         case 232:
             classify = new ClassifyByDistrictFromCompositesFiles();
             classify.param = param;
             classify.start();
            break;             
         case 233:      //从包含url、items、content文件中提取DSComposite,并按指定的省份分类存储DSComposite记录。
             classify_Key_pageTxt = new ClassifyByDistrictKeyFromPageTextFiles();
             classify_Key_pageTxt.param = param;
             classify_Key_pageTxt.start();
            break;
         case 234:
             classify_Key = new ClassifyByDistrictKeyFromCompositesFiles();
             classify_Key.param = param;
             classify_Key.start();
            break;
         case 235:
             computeAddress = new classifyAndComputeAddressComposite();
             computeAddress.param = param;
             computeAddress.start();
            break;             
         case 241:
             txtToXml = new CompositeTxtToXML();
             txtToXml.param = param;
             txtToXml.start();
            break;
         case 242:
             txtToXmlClass = new CompositeTxtToXMLClassify();
             txtToXmlClass.param = param;
             txtToXmlClass.start();             
            break;
      }               
   }
   */
   
	/**
	 * 将composite文本文件转换为xml文件
	 * path_s:文本文件目录
	 * path_d：xml文件目录
	 * encode:文本文件的字符编码
	 */
   
   /*
   class CompositeTxtToXML extends RunJob{ 
       
       @Override
       public synchronized void run(){
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();           
           
           
           List<String> list = new ArrayList<String>();
           int count = 1;
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           CompositeXml xml = new CompositeXml();
           for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }               
               List<DSComposite> listComposite = new ArrayList<DSComposite>();
               String Content = FileIO.read(listFiles.get(i), param.encode);
               String[] items = Content.split("\r\n");
               for (int j=0;j<items.length;j++){
                   DSComposite _info = new DSComposite();
                   if(items[j].isEmpty())
                       continue;
                   convert.StringToDSComposite(items[j], _info);
                   if(_info == null || (_info.name.isEmpty() && _info.local.address.isEmpty()) )
                       continue;
                   listComposite.add(_info);
               }
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));               
               xml.DSCompositesListToXml(param.paths.get(1) + "\\", count + "_" + (new Date()).getTime(),listComposite);
               count ++;
           }
       }
   }
   */

   /*
   class CompositeTxtToXMLClassify extends RunJob{
       @Override
       public synchronized void run(){
            Map<String,Map<String,DSComposite>> mapItems = new HashMap<String,Map<String,DSComposite>>();
            Map<String,DSComposite> mapNulls = new HashMap<String,DSComposite>(); 
           
           List<String> list = new ArrayList<String>();
           int count = 1;
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           CompositeXml xml = new CompositeXml();
           
               String key = "";
               String val = "";           
           for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }               
    //           List<DSComposite> listComposite = new ArrayList<DSComposite>();
               String Content = FileIO.read(listFiles.get(i), param.encode);
               String[] items = Content.split("\r\n");

               for (int j=0;j<items.length;j++){
                   DSComposite _info = new DSComposite();
                   if(items[j].isEmpty())
                       continue;
                   val = items[j];
                   convert.StringToDSComposite(items[j], _info);
                   
                   if(!_info.local.province.equals("") && _info.local.province.indexOf("|")==-1){
                       if(!_info.local.city.equals("") && _info.local.city.indexOf("|")==-1){
                           if(!_info.local.county.equals("") && _info.local.county.indexOf("|")==-1){
                               key = _info.local.province + "\\"+ _info.local.city +"\\"+ _info.local.county + "\\";
                           }else{key = _info.local.province + "\\"+ _info.local.city +"\\";}
                       }else{key = _info.local.province + "\\"; }
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, _info);
                       }else{
                           Map<String,DSComposite> mapVal = new HashMap<String,DSComposite>();
                           mapVal.put(val, _info);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, _info);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,DSComposite>> body = (Entry<String,Map<String,DSComposite>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                            xml.DSCompositesMapToXml(param.paths.get(1) + "\\" + key, fileName + "_"+ (new Date()).getTime(),body.getValue());
//                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
//                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       xml.DSCompositesMapToXml(param.paths.get(1) + "\\", count + "_" + (new Date()).getTime(),mapNulls);
                       mapNulls.clear();
                   }
               }
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,DSComposite>> body = (Entry<String,Map<String,DSComposite>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
               // FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
                xml.DSCompositesMapToXml(param.paths.get(1) + "\\" + key, file + "_"+ (new Date()).getTime(),body.getValue());
            }
            if(mapNulls.size()>0)
                xml.DSCompositesMapToXml(param.paths.get(1) + "\\", count + "_" + (new Date()).getTime(),mapNulls);
//                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls); 
              /* 
               * 
                      if(param.status != null)
            param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
              * 
              * 
              * xml.DSCompositesToXml(param.paths.get(1) + "\\", count + "_" + (new Date()).getTime(),listComposite);
               count ++;
                
           }
   }
    */
   
   class ExtractFromHtml extends RunJob{       
       @Override
       public synchronized void run(){
           KeyValues listFields = new KeyValues(param.files.get(1));
           List<KeyValue> fieldRules = listFields.getListKeyValues();
           KeyValues listItems = new KeyValues(param.files.get(0));
           List<KeyValue> listClears = listItems.getListKeyValues();
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);           
       }
   }
   
    /**
    * 从文本文件中提取Composites
    * (1)分离出url，composite，content
    * (2)从content中提取composite，规则由fieldRules指定
    * @param path_s
    * @param path_d
    * @param encode
    * @param fieldRules 
    */
   
   /*
   class ExtractFromText extends RunJob{
       @Override
       public synchronized void run(){
           List<String> list = new ArrayList<String>();
           KeyValues listFields = new KeyValues(param.fields.get(0));
           List<KeyValue> fieldRules = listFields.getListKeyValues();
           int count = 1;
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }               
               String pageContent = FileIO.read(listFiles.get(i), param.encode);
               DSCrawlerPageTxt pageTxt = new DSCrawlerPageTxt(); 
               convert.CrawlerPageTxtToDSCrawlerPageTxt(pageContent, pageTxt);
               
               List<DSComposite> listItems = new ArrayList<DSComposite>();
               parse.extractRecordsFromContent(pageTxt.content, pageTxt.urlCrawler ,fieldRules, listItems);
               for (int j=0;j<listItems.size();j++){
                   parse.extractRecord(listItems.get(j));
                   String val = convert.DSCompositeToString(listItems.get(j));
                   list.add(val);
               }
               if (list.size()>itemMax){
                   FileIO.writeAsTxtList(param.paths.get(1) +"\\", count + "_", list);
                   list.clear();
               }
               if(param.status != null)
                   param.status.setText("总计：" + listFiles.size() + "  完成 ：" + (i + 1));
           }
           FileIO.writeAsTxtList(param.paths.get(1)+"\\", count + "_", list);
           list.clear();
       }
   }
   */

   /*
   public void extractCompositeFromPageTxtFile(DataParam param){      
       List<String> list = new ArrayList<String>();
       try{
           KeyValues listFields = new KeyValues(param.fields.get(0));
           List<DSKeyValue> fieldRules = listFields.getListKeyValues();
           int count = 1;
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir); 
           for (int i=0;i<listFiles.size();i++){
               String pageContent = FileIO.read(listFiles.get(i), param.encode);
               DSCrawlerPageTxt pageTxt = new DSCrawlerPageTxt(); 
               CompositeConvert.CrawlerPageTxtToDSCrawlerPageTxt(pageContent, pageTxt);
               
               List<DSComposite> listItems = new ArrayList<DSComposite>();
               parse.extractRecordsFromContent(pageTxt.content, fieldRules, listItems);
               for (int j=0;j<listItems.size();j++){
                   parse.extractRecord(listItems.get(j));
                   String val = CompositeConvert.DSCompositeToString(listItems.get(j));
                   list.add(val);
               }
               if (list.size()>itemMax){
                   FileIO.writeAsTxtList(param.paths.get(1) +"\\", count + "_", list);
                   list.clear();
               }
               if(param.status != null)
                   param.status.setText("总计：" + listFiles.size() + "  完成 ：" + (i + 1));
           }
           FileIO.writeAsTxtList(param.paths.get(1)+"\\", count + "_", list);
           list.clear();       
       }catch(Exception e){
           System.out.println("dbcomposite pageTxtFileToCompositeFIle :" + e.getMessage());
           e.printStackTrace();
       }      
   }
   */
	
//	public void pageTxtFileToCompositeFile(String path_s,String path_d,String encode,List<DSKeyValue> fieldRules){
   /*
   public void pageTxtFileToCompositeFile(DataParam param){      
       List<String> list = new ArrayList<String>();
       try{
           int count = 1;
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           for (int i=0;i<listFiles.size();i++){
               String pageContent = FileIO.read(listFiles.get(i), param.encode);
               DSCrawlerPageTxt pageTxt = new DSCrawlerPageTxt(); 
               CompositeConvert.CrawlerPageTxtToDSCrawlerPageTxt(pageContent, pageTxt);
               for (int j=0;j<pageTxt.items.size();j++){
                   String val = CompositeConvert.DSCompositeToString(pageTxt.items.get(j));
                   list.add(val);
               }
               if (list.size()>itemMax){
                   FileIO.writeAsTxtList(param.paths.get(1)+"\\", count + "_", list);
                   list.clear();
               }
           }
            FileIO.writeAsTxtList(param.paths.get(1)+"\\", count + "_", list);
            list.clear();           
       }catch(Exception e){
           System.out.println("dbcomposite pageTxtFileToCompositeFIle :" + e.getMessage());
       }
   }
   */ 
	
   //DataParam param
   //public void filterCompositeItems(String path,String encode){
   /*
   public void filterCompositeItems(DataParam param){
       Map<String,String> mapItems = new HashMap<String,String>();
       try{
           List<String> listFiles = new ArrayList<String>();
           FileIO.getPathFiles(param.paths.get(0), listFiles);
           for (int i=0;i<listFiles.size();i++){
               String items = FileIO.read(listFiles.get(i), param.encode);
               String[] itemArray = items.split("\r\n");
               for (int j=0;j<itemArray.length;j++){
                   if (!mapItems.containsKey(itemArray[j]))
                       mapItems.put(itemArray[j], itemArray[j]);
               }
           }
           for (int i=0;i<listFiles.size();i++){
               File f= new File(listFiles.get(i)); 
               f.delete();           
           }
           int index = param.files.get(0).lastIndexOf("\\");
           String fileName = param.files.get(0).substring(index, param.files.get(0).length());
           FileIO.writeAsTxtMap(param.paths.get(0) + "\\", fileName, mapItems);
       }catch(Exception e){
           System.out.print("db composite filterCompositeItems :" + e.getMessage());
       }
   }
   */
   
//	public void pageTxtFileToMySQLDB(String path,String tableName,String encode){
   /*
   public void pageTxtFileToMySQLDB(DataParam param,String tableName){
       List<String> list = new ArrayList<String>();
       try{
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           
           for (int i=0;i<listFiles.size();i++){
               String pageContent = FileIO.read(listFiles.get(i), param.encode);
               DSCrawlerPageTxt pageTxt = new DSCrawlerPageTxt(); 
               CompositeConvert.CrawlerPageTxtToDSCrawlerPageTxt(pageContent, pageTxt);
               
               for (int j=0;j<pageTxt.items.size();j++){
                   String val = CompositeConvert.DSCompositeToString(pageTxt.items.get(j));
                   addCompositeToMySQL(pageTxt.items.get(j),tableName);
                   System.out.println(val);
               }
           }
           System.out.println(list.size());
       }catch(Exception e){
           System.out.print("db composite pageTxtFileToMySQLDB :" + e.getMessage());
       }
   }
   */
   /*
   public void addCompositeToMySQL(DSComposite _info,String tabelName){
       HashMap<String, String> values = new HashMap<String, String>();
       values.put("name", _info.name);
       values.put("label", _info.label);
       values.put("remark", _info.remark);
       values.put("content", _info.content);
       values.put("Abstract", _info.Abstract);
		
        values.put("address", _info.address);
        values.put("province", _info.province);	
        values.put("city", _info.city);
        values.put("county", _info.county);
        values.put("district", _info.district);
        values.put("zone", _info.zone);
        values.put("house", _info.house);
        values.put("house_number", _info.house_number);
        values.put("storey", _info.storey);
        values.put("room", _info.room);
        values.put("position", _info.position);

        values.put("phone", _info.phone);		
        values.put("phone_code", _info.phone_code);		
        values.put("phone_number", _info.phone_number);
        values.put("postcode", _info.postcode);

        values.put("reference", _info.reference);
        values.put("distance", _info.distance);
        values.put("direction", _info.direction);

        values.put("date_collect", _info.date_collect);
        values.put("date_publish", _info.date_publish);
        values.put("time_service", _info.time_service);

        values.put("active_date_begin", _info.active_date_begin);
        values.put("active_date_end", _info.active_date_end);
        values.put("active_items", _info.active_items);
        values.put("active_time", _info.active_time);
        values.put("active_title", _info.active_title);

        dbInsert(tabelName, values);
    }
   */
   /*
   public void getCompositeFromMySQL(){
       try{
           if (dbstate == null)
               dbstate = (Statement) dbconn.createStatement(); 
           String sql = "select distinct * from composites"; 
           sql ="select distinct name,address,label,province,city,county,district,building,buildingno,phone,phone1,phone2,postcode,remark,zone,storey,position,reference,distance,date,content from composites order by county";
           //			sql = sql + " order by province,city,county,phone1,postcode";
           print("sql = " + sql); 
           try{
               dbresult = dbstate.executeQuery(sql);
           }catch(Exception err){
               print("Sql = " + sql);
               print("Exception: " + err.getMessage());
           }
           int count = 0;
           int size = 0;
           BufferedWriter bw = null;
           while(dbresult.next()){
               String fileName= count+ "_" + (new Date()).getTime();
               if (bw == null)
                   bw = new BufferedWriter(new FileWriter(new File("d:\\product\\lib\\extract\\aibang\\shanghai\\20130616\\" + fileName + ".txt")));
               String val = "";
               val  = val + "name:"+ dbresult.getString("name");
               val  = val + ";address:" + dbresult.getString("address");
               val  = val + ";label:" + dbresult.getString("label");
               val  = val + ";province:" +dbresult.getString("province");
               val  = val + ";city:" + dbresult.getString("city");
               val  = val + ";county:" + dbresult.getString("county");
               val  = val + ";district:" +dbresult.getString("district");
               val  = val + ";phone:" + dbresult.getString("phone");
               val  = val + ";phone1:" + dbresult.getString("phone1");
               val  = val + ";phone2:" + dbresult.getString("phone2");
               val  = val + ";postcode:" + dbresult.getString("postcode");
               val  = val + ";building:" + dbresult.getString("building");
               val  = val + ";buildingNo:" + dbresult.getString("buildingNo");
               val  = val + ";remark:" + dbresult.getString("remark");
               
               val  = val + ";zone:" + dbresult.getString("zone");
               val  = val + ";storey:" + dbresult.getString("storey");
               val  = val + ";position:" + dbresult.getString("position");
               val  = val + ";reference:" + dbresult.getString("reference");
               val  = val + ";distance:" + dbresult.getString("distance");
               val  = val + ";date:" + dbresult.getString("date");
               val  = val + ";content:" + dbresult.getString("content");
               
               System.out.println(val);
               //				parseRecord(val);
               bw.write(val + "\r\n");
               size ++;
               if (size>=itemMax){
                   bw.flush();
                   bw.close();
                   count++;
                   size = 0;
                   bw = null;
               }
           }
           System.out.println(count);
       }catch(Exception e){
           print("Exception: " + e.getMessage());
       }
   }
   */ 
	
   /**
    * 将从网页中提取的数据转储
    * 
    * @param path_src：数据源目录；文件数据格式：{URL；items{item,item...}；content}
    * @param district：目标数据目录；有效数据目录..\\txt\\省\\市\\县\\；无效数据 \\nullprovince\\；URL目录
    * 有效数据格式composites list;
    * 无效效数据格式composites list;是指没有提取出省、市、县信息的记录
    * urls:{title,url,itemcount（该链接下的记录数）,host}
    * @param file 
    */
   
   /*
   class Values{
      Map<String,String> map = new HashMap<String,String>();
   }
   */
   /**
    * 信息分类，按省，市，县
    * @param path_src
    * @param path_dist
    * @param file
    * @param encode 
    */
   //public void classifyComposite(String path_src,String path_dist,String path_null,String encode){
   /*
   public void extractByUrl(String path,String keyAddress){
       CompositeConvert convert = new CompositeConvert();
       String val = "";
       String key = "";
       Map<String,String> mapKeyAddress = new HashMap<String,String>();
       List<String> listFiles = new ArrayList<String>();
       List<String> listDir = new ArrayList<String>();
       FileIO.getFilesAndSubDirFiles(path, listFiles, listDir);
       
       for (int i=0;i<listFiles.size();i++){
           List<String> listItems = new ArrayList<String>();
           FileIO.readLines(listFiles.get(i),"utf-8",listItems);
           for (int j=0;j<listItems.size();j++){
               val = listItems.get(j);
               if(val == null)
                   continue;
               val = val.replace("</title>", "");
               val = val.replace("&nbsp", "");
               val = val .replace(">", "");
               val = val .replaceAll("\\([^\\)]+?\\)", "");
               if(val.indexOf(keyAddress)>=0 ){
                   DSComposite info =new DSComposite();
                   convert.StringToDSComposite(val, info);
                   key = info.collect_url;
                   val = info.name  + "|" + info.collect_title + "|" + info.collect_keywords + "|" + info.local.address;
                   if(!mapKeyAddress.containsKey(key)&& info.local.address.indexOf(keyAddress)>=0)
                       mapKeyAddress.put(key, val);
               }
           }
           System.out.println("文件总数：" + listFiles.size() + "  完成：" + i);
       }
       Set set = mapKeyAddress.entrySet();
       Iterator it = set.iterator();
       List<String> list = new ArrayList<String>();
       while(it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)it.next();
           list.add(body.getKey() );
       }
       //FileIO.writeAsTxtList(path+"\\", "群光广场", list);
       Collections.sort(list);
       for(int i =0; i<list.size();i++){
           System.out.println(list.get(i));
       }
       System.out.println(list.size());
   }   
   */
   
   /*
   public void classidyByAddress(String path,String keyAddress){
       CompositeConvert convert = new CompositeConvert();
       String val = "";
       String key = "";
       Map<String,String> mapKeyAddress = new HashMap<String,String>();
       List<String> listFiles = new ArrayList<String>();
       List<String> listDir = new ArrayList<String>();
       FileIO.getFilesAndSubDirFiles(path, listFiles, listDir);
       
       for (int i=0;i<listFiles.size();i++){
           List<String> listItems = new ArrayList<String>();
           FileIO.readLines(listFiles.get(i),"utf-8",listItems);
           for (int j=0;j<listItems.size();j++){
               val = listItems.get(j);
               
               if(val == null)
                   continue;
               val = val.replace("</title>", "");
               val = val.replace("&nbsp", "");
               val = val .replace(">", "");
               val = val .replaceAll("\\([^\\)]+?\\)", "");
               val = val.replace("...", "");
               val = val.replace("(", "");
               val = val.replace(")", "");
               val = val.replace("-", "");
               val = val.replace("\"", "");
               val = val.replace("/", "");
//               val = val.replaceAll("丁丁地图| |丁丁网|地图位置_|_百度搜索|武汉地图", "");
               
               val = val.replace("地址_电话", "");
               if(val.indexOf(keyAddress)>=0 && val.indexOf("楼")>0){
                   DSComposite info =new DSComposite();
                   convert.StringToDSComposite(val, info);
                   parse.parseFieldName(info);
                   if(info.local.address.indexOf(keyAddress)>=0){
                    key = info.local.floor + "楼|name:"  + info.name  +"|";
                    val = "title:" + info.collect_title + "|key:" + info.collect_keywords + "|address:" + info.local.address + "|url:" + info.collect_url;
                    //val = "title:" + info.collect_title  + "|address:" + info.address;
                    if(!mapKeyAddress.containsKey(key))
                        mapKeyAddress.put(key, val);
                   }
               }
           }
           System.out.println("文件总数：" + listFiles.size() + "  完成：" + i);
       }
       Set set = mapKeyAddress.entrySet();
       Iterator it = set.iterator();
       List<String> list = new ArrayList<String>();
       while(it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)it.next();
           list.add(body.getKey() + "| " + body.getValue() );
       }
       
       Collections.sort(list);
       for(int i =0; i<list.size();i++){
           System.out.println(list.get(i));
       }
       
   }
   */
   
   
   /*
   
   class classifyAndComputeAddressComposite extends RunJob{       
       @Override
       public synchronized void run(){
            if(parse == null)
                parse = new CompositeParse();      
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();
            String val ="";
            String key = ""; 
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
            
            for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               List<String> listItems = new ArrayList<String>();
               FileIO.readLines(listFiles.get(i), param.encode, listItems);
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = new DSComposite();
                   val = listItems.get(j);
                   if(val == null)
                       continue;
                   convert.StringToDSComposite(val, item);
                   parse.extractRecord(item);
                   val = convert.DSCompositeToString(item);
                   if(!item.local.province.equals("")){
                       if(!item.local.city.equals("")){
                           if(!item.local.county.equals("")){
                               key = item.local.province + "\\"+ item.local.city +"\\"+ item.local.county + "\\";
                           }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                       }else{key = item.local.province + "\\"; }
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       mapNulls.clear();
                   }
               }
               
               File f=new File(listFiles.get(i));
               f.delete();
               
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
                FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
            }
            if(mapNulls.size()>0)
                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
       }
   }
   */
   
   /*
   public void classifyComposite(DataParam param){
      String key = "";      
      if(parse == null)
         parse = new CompositeParse();      
      Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();      
      Map<String,String> mapNulls = new HashMap<String,String>();
      
      try{
         String val ="";
         List<String> listFiles = new ArrayList<String>();
         List<String> listDir = new ArrayList<String>();
         FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir); 
         
         for (int i=0;i<listFiles.size();i++){
            List<String> listItems = new ArrayList<String>();
            FileIO.readLines(listFiles.get(i),param.encode,listItems);
            for (int j=0;j<listItems.size();j++){
               DSComposite item = new DSComposite();
               val = listItems.get(j);
               CompositeConvert.StringToDSComposite(val, item);
               
               if(item.province.indexOf("|")>=0|| item.city.indexOf("|")>=0|| item.county.indexOf("|")>=0){
                   if(!mapNulls.containsKey(key)){
                       mapNulls.put(val, val);
                   }
               }else{
                   if(!item.province.isEmpty()&& !item.city.isEmpty()){
                       if(!item.county.isEmpty()){
                           key = item.province+"\\"+item.city+"\\"+item.county+"\\";
                       }else{
                           key = item.province+"\\"+item.city+"\\"+ item.city +"\\";
                       }
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(key)){
                           mapNulls.put(val, val);
                       }
                   }
               }
               Set set = mapItems.entrySet();
               Iterator it = set.iterator();
               while(it.hasNext()){
                  Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                  if (body.getValue().size()>itemMax){
                     key = body.getKey();
                     key = key.replace(item.county+"\\\\", "");                 
                     FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, item.county + "_" , body.getValue());
                     body.getValue().clear();
                  }
                 if(mapNulls.size()>itemMax){
                     FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                     mapNulls.clear();
                 }
               }
            }
            File f=new File(listFiles.get(i)); 
            f.delete();
            System.out.println("文件总数：" + listFiles.size() + "  完成：" + i);
         }
         Set set = mapItems.entrySet();
         Iterator it = set.iterator();
         while(it.hasNext()){
            Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
            key = body.getKey();
            String file = key.split("\\\\")[2];
            key = key.replace(file+"\\\\", "");            
            FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
         }
         FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
      }catch(Exception e){
         System.out.print("db composite classifyTxtPageToTxtComposite :" + e.getMessage());
         e.printStackTrace();
      }      
   }
   */
   
   /*
   class ClassifyByDistrictKeyFromPageTextFiles extends RunJob{
        @Override
        public synchronized void run(){
            if(parse == null)
                parse = new CompositeParse();      
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();
            String val ="";
            String key = ""; 
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
            
            for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               List<DSComposite> listItems = new ArrayList<DSComposite>();
               //FileIO.readLines(listFiles.get(i), param.encode, listItems);
               convert.getCompositeListFromPageTxtFile(listFiles.get(i), param.encode, listItems);
               
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = listItems.get(j);
                   if(item.local.address.isEmpty())
                       continue;
                   val = convert.DSCompositeToString(item);
                   if(item.local.province.equals(param.keys.get(0))){
                       if(!item.local.city.equals("")){
                           if (item.local.city.indexOf("|") == -1){
                               if(!item.local.county.equals("")){
                                   if(item.local.county.indexOf("|") == -1){
                                       key = item.local.province + "\\"+ item.local.city +"\\"+ item.local.county + "\\";
                                   }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                               }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                           }else{key = item.local.province + "\\"; }
                       }else{key = item.local.province + "\\";}
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       mapNulls.clear();
                   }
               }
               File f=new File(listFiles.get(i));
               f.delete();
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
                FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
            }
            if(mapNulls.size()>0)
                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
        }
   }   
   */
   
   /*
   class ClassifyByDistrictFromPageTextFiles extends RunJob{
        @Override
        public synchronized void run(){
            if(parse == null)
                parse = new CompositeParse();      
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();
            String val ="";
            String key = ""; 
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
            
            for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               List<DSComposite> listItems = new ArrayList<DSComposite>();
               convert.getCompositeListFromPageTxtFile(listFiles.get(i), param.encode, listItems);
               
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = listItems.get(j);
                   if(item.local.address.isEmpty())
                       continue;
                   val = convert.DSCompositeToString(item);
                   if(val.isEmpty())
                       continue;
                   if(!item.local.province.equals("") && item.local.province.indexOf("|")==-1){
                       if(!item.local.city.equals("")  && item.local.city.indexOf("|")==-1){
                           if(!item.local.county.equals("")  && item.local.county.indexOf("|")==-1){
                               key = item.local.province + "\\"+ item.local.city +"\\"+ item.local.county + "\\";
                           }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                       }else{key = item.local.province + "\\"; }
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       mapNulls.clear();
                   }
               }
               File f=new File(listFiles.get(i));
               f.delete();
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
                FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
            }
            if(mapNulls.size()>0)
                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
        }
   }
   */
   
   /*
   class ClassifyByDistrictKeyFromCompositesFiles extends RunJob{
        @Override
        public synchronized void run(){
            if(parse == null)
                parse = new CompositeParse();      
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();
            String val ="";
            String key = ""; 
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
            
            for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               List<String> listItems = new ArrayList<String>();
               FileIO.readLines(listFiles.get(i), param.encode, listItems);
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = new DSComposite();
                   if(item.local.address.isEmpty())
                       continue;
                   val = listItems.get(j);
                   if(val == null)
                       continue;
                   convert.StringToDSComposite(val, item);
                   if(item.local.province.equals(param.keys.get(0))){
                       if(!item.local.city.equals("")){
                           if (item.local.city.indexOf("|") == -1){
                               if(!item.local.county.equals("")){
                                   if(item.local.county.indexOf("|") == -1){
                                       key = item.local.province + "\\"+ item.local.city +"\\"+ item.local.county + "\\";
                                   }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                               }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                           }else{key = item.local.province + "\\"; }
                       }else{key = item.local.province + "\\";}
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       mapNulls.clear();
                   }
               }
               File f=new File(listFiles.get(i));
               f.delete();
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
                FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
            }
            if(mapNulls.size()>0)
                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
        }
   }
   */
   
   
   /*
   class ClassifyByDistrictFromCompositesFiles extends RunJob{
        @Override
        public synchronized void run(){
            if(parse == null)
                parse = new CompositeParse();      
            Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
            Map<String,String> mapNulls = new HashMap<String,String>();
            String val ="";
            String key = ""; 
            List<String> listFiles = new ArrayList<String>();
            List<String> listDir = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
            
            for (int i=0;i<listFiles.size();i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                }
               if(status == 0)
                   break;
               while(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               
               List<String> listItems = new ArrayList<String>();
               FileIO.readLines(listFiles.get(i), param.encode, listItems);
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = new DSComposite();
                   if(item.local.address.isEmpty())
                       continue;                   
                   val = listItems.get(j);
                   if(val == null)
                       continue;
                   convert.StringToDSComposite(val, item);
                   if(!item.local.province.equals("") && item.local.province.indexOf("|")==-1){
                       if(!item.local.city.equals("") && item.local.city.indexOf("|")==-1){
                           if(!item.local.county.equals("") && item.local.county.indexOf("|")==-1){
                               key = item.local.province + "\\"+ item.local.city +"\\"+ item.local.county + "\\";
                           }else{key = item.local.province + "\\"+ item.local.city +"\\";}
                       }else{key = item.local.province + "\\"; }
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
                   }
                   Set set = mapItems.entrySet();
                   Iterator it = set.iterator();
                   while(it.hasNext()){
                       Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                       if (body.getValue().size()>itemMax){
                           key = body.getKey();
                           String[] keys = key.split("\\\\");
                           String fileName = keys[keys.length-1];
                           FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                           body.getValue().clear();
                       }
                   }
                   if(mapNulls.size()>itemMax){
                       FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                       mapNulls.clear();
                   }
               }
               
               File f=new File(listFiles.get(i));
               f.delete();
               
               if(param.status != null)
                   param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
            }
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String file = keys[keys.length-1];
                FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
            }
            if(mapNulls.size()>0)
                FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
        }
   }   
   */
   
   //JLabel status,String province,String path_src,String path_dist,String path_null,String encode
   /*
   public void classifyCompositeProvince(DataParam param){
      if(parse == null)
         parse = new CompositeParse();      
      Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();      
      Map<String,String> mapNulls = new HashMap<String,String>();
      
      try{
         String val ="";
         String key = ""; 
         List<String> listFiles = new ArrayList<String>();
         List<String> listDir = new ArrayList<String>();
         FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
         
         for (int i=0;i<listFiles.size();i++){
            List<String> listItems = new ArrayList<String>();
            FileIO.readLines(listFiles.get(i), param.encode, listItems);
            for (int j=0;j<listItems.size();j++){
               DSComposite item = new DSComposite();
               val = listItems.get(j);
               if(val == null)
                  continue;
               CompositeConvert.StringToDSComposite(val, item);
               if(item.province.equals(param.keys.get(0))){
                  if(!item.city.equals("")){
                     if (item.city.indexOf("|") == -1){
                        if(!item.county.equals("")){
                           if(item.county.indexOf("|") == -1){
                              key = item.province + "\\"+ item.city +"\\"+ item.county + "\\";
                           }else{key = item.province + "\\"+ item.city +"\\";}
                        }else{key = item.province + "\\"+ item.city +"\\";}                        
                     }else{key = item.province + "\\"; }
                  }else{key = item.province + "\\";}
                  if (mapItems.containsKey(key)){
                     if(!mapItems.get(key).containsKey(val))
                        mapItems.get(key).put(val, val);                     
                  }else{
                     Map<String,String> mapVal = new HashMap<String,String>();
                     mapVal.put(val, val);
                     mapItems.put(key, mapVal);
                  }                  
               }else{
                  if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}                  
               }
               Set set = mapItems.entrySet();
               Iterator it = set.iterator();
               while(it.hasNext()){
                  Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                  if (body.getValue().size()>itemMax){
                     key = body.getKey();
                     String[] keys = key.split("\\\\");
                     String fileName = keys[keys.length-1];
                     FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, fileName + "_" , body.getValue());
                     body.getValue().clear();
                  }
               }
               if(mapNulls.size()>itemMax){
                   FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
                   mapNulls.clear();
               }               
            }
            File f=new File(listFiles.get(i)); 
            f.delete();
            if(param.status != null)
               param.status.setText("文件总数：" + listFiles.size() + "  完成：" + (i+1));
         }
         Set set = mapItems.entrySet();
         Iterator it = set.iterator();
         while(it.hasNext()){
            Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
            key = body.getKey();
            String[] keys = key.split("\\\\");
            String file = keys[keys.length-1];            
            FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, file + "_" , body.getValue());
         }
         if(mapNulls.size()>0)
            FileIO.writeAsTxtMap(param.paths.get(2) + "\\", "null" + "_", mapNulls);
      }catch(Exception e){
         System.out.print("db composite classifyTxtPageToTxtComposite :" + e.getMessage());
         e.printStackTrace();
      }      
   }
   */
   /**
    * 
    * @param path_src      数据源，保存通过验证的项目
    * @param path_dist     验证有疑问的目录
    * @param encode 
    */
   
   /*
   class CheckProvince extends RunJob{
       @Override
       public synchronized void run(){
           String key = "";      
           if(parse == null)
               parse = new CompositeParse();
           Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
           Map<String,String> mapNulls = new HashMap<String,String>();
           int count = 1;
           String val ="";
           List<String> listFiles = new ArrayList<String>();
           List<String> listDir = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
           for (int i=0;i<listFiles.size();i++){
               if(status == 0)
                   break;
               if(status == 2){
                   try {
                       Thread.sleep(2000);
                       continue;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               List<String> listItems = new ArrayList<String>();
               FileIO.readLines(listFiles.get(i),param.encode,listItems);
               for (int j=0;j<listItems.size();j++){
                   DSComposite item = new DSComposite();
                   val = listItems.get(j);
                   convert.StringToDSComposite(val, item);
                   item.local.province = "";
                   item.local.city = "";
                   item.local.county = "";
                   parse.extractRecord(item);
                   
                   val = convert.DSCompositeToString(item);
                   if (!item.local.province.equals("") &&!item.local.city.equals("")){
                       if(!item.local.county.equals(""))
                           key = item.local.province+"\\"+item.local.city+"\\"+item.local.county+"\\";
                       else
                           key = item.local.province+"\\"+item.local.city+"\\";
                       if (mapItems.containsKey(key)){
                           if(!mapItems.get(key).containsKey(val))
                               mapItems.get(key).put(val, val);
                       }else{
                           Map<String,String> mapVal = new HashMap<String,String>();
                           mapVal.put(val, val);
                           mapItems.put(key, mapVal);
                       }
                   }else{
                       val = convert.DSCompositeToString(item);
                       if(!mapNulls.containsKey(key))
                           mapNulls.put(val, val);
                   }
               }
               File f = new File(listFiles.get(i));
               f.delete();
               System.out.println("文件总数：" + listFiles.size() + "  完成：" + i);
           }
           Set set = mapItems.entrySet();
           Iterator it = set.iterator();
           val = "";
           while(it.hasNext()){
               Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
               key = body.getKey();
               String[] str = key.split("\\\\");
               String file = str[str.length-1];
               Set _set = body.getValue().entrySet();
               Iterator _it = _set.iterator();
               val = "";
               count = 0;
               while(_it.hasNext()){
                   Entry<String,String> _body = (Entry<String,String>)_it.next();
                   val = val + _body.getValue() +"\r\n";
                   count ++;
                   if(count>2000){
                       FileIO.writeAsTxts(param.paths.get(0) +"\\" + key, file + "_" ,val);
                       count = 0;
                   }
               }
               FileIO.writeAsTxts(param.paths.get(0) +"\\" + key, file + "_" ,val);
           }
           Set set_null = mapNulls.entrySet();
           Iterator it_null = set_null.iterator();
           val = "";
           count = 0;
           while(it_null.hasNext()){
               Entry<String,String> _body = (Entry<String,String>)it_null.next();
               val = val + _body.getValue()+"\r\n";
               if(count>2000){
                   FileIO.writeAsTxts(param.paths.get(1) + "\\", "null" + "_" ,val);
                   count = 0;
               }
           }
           FileIO.writeAsTxts(param.paths.get(1) + "\\", "null" + "_" ,val);
           mapNulls.clear();
       }
   }
   */
   
   
//   public void checkProvinceCityCounty(String path_src,String path_dist,String encode){
   /*
   public void checkProvinceCityCounty(DataParam param){
      String key = "";      
      if(parse == null)
         parse = new CompositeParse();      
      Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();      
      Map<String,String> mapNulls = new HashMap<String,String>();
      
      try{
          int count = 1;
         String val ="";
         List<String> listFiles = new ArrayList<String>();
         List<String> listDir = new ArrayList<String>();
         FileIO.getFilesAndSubDirFiles(param.paths.get(0), listFiles, listDir);
         
         for (int i=0;i<listFiles.size();i++){
            List<String> listItems = new ArrayList<String>();
            FileIO.readLines(listFiles.get(i),param.encode,listItems);
            for (int j=0;j<listItems.size();j++){
               DSComposite item = new DSComposite();
               val = listItems.get(j);
               CompositeConvert.StringToDSComposite(val, item);
               item.province = "";
               item.city = "";
               item.county = "";
               parse.extractRecord(item);
               
               val = CompositeConvert.DSCompositeToString(item);
               if (!item.province.equals("") &&!item.city.equals("")){
                  if(!item.county.equals(""))
                     key = item.province+"\\"+item.city+"\\"+item.county+"\\";
                  else
                     key = item.province+"\\"+item.city+"\\";
                  if (mapItems.containsKey(key)){
                     if(!mapItems.get(key).containsKey(val))
                        mapItems.get(key).put(val, val);                     
                  }else{
                     Map<String,String> mapVal = new HashMap<String,String>();
                     mapVal.put(val, val);
                     mapItems.put(key, mapVal);
                  }                  
               }else{
                  val = CompositeConvert.DSCompositeToString(item);
                  if(!mapNulls.containsKey(key))
                     mapNulls.put(val, val);
               }
            }
            File f = new File(listFiles.get(i)); 
            f.delete();
            System.out.println("文件总数：" + listFiles.size() + "  完成：" + i);
         }
         Set set = mapItems.entrySet();
         Iterator it = set.iterator();
         val = "";
         while(it.hasNext()){
            Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
            key = body.getKey();
            String[] str = key.split("\\\\");
            String file = str[str.length-1];
            Set _set = body.getValue().entrySet();
            Iterator _it = _set.iterator();
            val = "";
            count = 0;
            while(_it.hasNext()){
               Entry<String,String> _body = (Entry<String,String>)_it.next();
               val = val + _body.getValue() +"\r\n";
               count ++;
               if(count>2000){
                  FileIO.writeAsTxts(param.paths.get(0) +"\\" + key, file + "_" ,val);
                  count = 0;
               }
            }
            FileIO.writeAsTxts(param.paths.get(0) +"\\" + key, file + "_" ,val);
         }
         Set set_null = mapNulls.entrySet();
         Iterator it_null = set_null.iterator();
         val = "";
         count = 0;
         while(it_null.hasNext()){ 
               Entry<String,String> _body = (Entry<String,String>)it_null.next();
               val = val + _body.getValue()+"\r\n";
               if(count>2000){
                  FileIO.writeAsTxts(param.paths.get(1) + "\\", "null" + "_" ,val);
                  count = 0;
               }
         }
         FileIO.writeAsTxts(param.paths.get(1) + "\\", "null" + "_" ,val);
         mapNulls.clear();
      }catch(Exception e){
         System.out.print("db composite classifyTxtPageToTxtComposite :" + e.getMessage());
         e.printStackTrace();
      }      
   }
   */
   /*
    public static void main(String[] args){		
        DbComposite parse = new DbComposite();
//	parse.gatherRecord("d:\\product\\lib\\extract\\aibang\\shanghai\\20130615\\");
        //parse.getCompositeFromMySQL();gatCompositeFromPageFiles
//	parse.pageTxtFileToMySQLDB("d:\\product\\aibang\\pagecontent\\","composites_shanghai", "GB2312");
//	parse.pageTxtFileToCompositeFIle("d:\\product\\aibang\\pagecontent\\", "d:\\product\\aibang\\lib\\", "GB2312");
//	parse.CompositeTxtToXml("d:\\product\\aibang\\lib\\","d:\\product\\aibang\\xml\\","UTF-8");
    }
    */ 
   
   public static void main(String[] args){
       DbComposite composite = new DbComposite();
 //      composite.extractByUrl("D:\\Projects\\search engine\\CrawlerSystem\\database\\page3\\bjjc\\0_g", "首都机场");
   }
}
