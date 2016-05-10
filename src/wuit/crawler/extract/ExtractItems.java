/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.extract;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.CompositeConvert;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.ExtractFieldRules;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.CrawlerHtmlData;
import wuit.crawler.DSCrawlerUrl;
import wuit.crawler.main.MainServer;
/**
 *
 * @author lxl
 */
public class ExtractItems extends Thread{
    public List<KeyValue> defaultRules;                                         //配置默认提取信息
    public List<KeyValue> clearHtml;                                            //配置默认清理HTML规则
    ExtractFieldRules defRules = new ExtractFieldRules();                       //网页提取特征库
    Parser parser = new Parser();
    
    
//    private String pageTitle = "";
    private String pageKwywords = "";
    private String pageDesription = "";    
    
    public int state = 1;
    
    public ExtractItems(List<KeyValue> defaultRules,List<KeyValue> clearHtml){
        this.defaultRules = defaultRules;
        this.clearHtml = clearHtml;
    }
    
    public void run(){
        try {
            while(state == 1){
                CrawlerHtmlData data = MainServer.DBCrawler.getHtmlPage();
                if(data == null){
                    Thread.sleep(1000);
                    continue;
                }
                extractPageBaseInfo(data.html);                                         //提取网页中的基本信息
                String conetnt = clearHtml(data.html);
                List<DSComposite> list = extractContent(conetnt,data.pageInfo);       //提取网页中包含的信息项

                //统计提取到信息的网页数
                if(list.size()>0){
//                    MainServer.state.pageContaintItems = MainServer.state.pageContaintItems + 1;
                    synchronized(this){
                        MainServer.DBCrawler.listDSComposites.addAll(list);
                    }
                    parseAddress(list);
                }
                Thread.sleep(100);
            }
            state = 0;
        } catch (InterruptedException ex) {
            Logger.getLogger(ExtractItems.class.getName()).log(Level.SEVERE, null, ex);
            state = 0;
        }
    }
    
    public void parseAddress(List<DSComposite> list){
       for(int i=0;i<list.size();i++){
           parser.parseAddress(list.get(i));
       }
   }
    
    public String clearHtml(String html){
        String content = html;
        for (int i=clearHtml.size()-1;i>0;i--){            
            content = content.replaceAll(clearHtml.get(i).key,clearHtml.get(i).value);            
        }
        content = content.replaceAll("&gt;|&nbsp;", "");
        System.out.println(content);
        return content;
    }    
    
    public List<DSComposite> extractContent(String content,DSCrawlerUrl url){
        List<DSComposite> listItems = new ArrayList<DSComposite>();
        boolean hasRule = false;
        if(defRules.mapExtracts == null){
            extractItems(content,defaultRules,listItems);
        }else{    
            Set set = defRules.mapExtracts.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,List<KeyValue>> body = (Map.Entry<String,List<KeyValue>>)it.next();
                if(!match(url.url,body.getKey()).isEmpty()){        //  匹配url特征字符串，如果匹配上了
                    hasRule = true;
                    extractItems(content,body.getValue(),listItems);
                    break;
                }
            }
            if(!hasRule){
                extractItems(content,defaultRules,listItems); 
            }
        }
        
        ////
        for(int i=0;i<listItems.size();i++){
            setPageBaseInfo(listItems.get(i),url);
        }
        
        return listItems;
    }
    
    public void extractItems(String content,  List<KeyValue> fieldRules,List<DSComposite> listItems){
        CompositeConvert convert = new CompositeConvert();
        //List<DSComposite> listDSComposite = new ArrayList<DSComposite>();
        List<KeyValue> list1 =  new ArrayList<KeyValue>();
        List<List<KeyValue>> list =  new ArrayList<List<KeyValue>>();
        for(int i=0;i<fieldRules.size();i++){
            list.add(new ArrayList<KeyValue>());
        }
        
        convert.matchValues(content,fieldRules.get(0).key,fieldRules.get(0).value, list1); //匹配提取第一字段
        for(int i=0;i<list1.size();i++){
            DSComposite info = new DSComposite();
            convert.setDSCompositeValue(list1.get(i).key, list1.get(i).value, info);
            listItems.add(info);
        }
        for(int i=1;i<fieldRules.size();i++){      //  匹配第二个，以及后续提取字段规则
            convert.matchValues(content,fieldRules.get(i).key,fieldRules.get(i).value, list.get(i-1));// 匹配2到n个字段
        }
        
        for(int n=0;n<list1.size();n++){
            KeyValue valKey = list1.get(n);
            String[] arrVal1 = valKey.value.split("、");
            if(arrVal1.length<=1)
                continue;
            for(int m=0;m<arrVal1.length;m++){
                for(int i=0; i<fieldRules.size()-1; i++){
                    for(int j=0;j<list.get(i).size();j++){
                        KeyValue val = list.get(i).get(j);
                        if(arrVal1[m].indexOf(val.value) >= 0){        //获得与其他字段不重复的部分
                           int index = valKey.value.indexOf(arrVal1[m]);
                           if(index == -1)
                               continue;
                           System.out.println(valKey.value + " | " + arrVal1[m] + " | " + val.value + " | " + index + " | " + arrVal1[m].length());
                           if(index == 0){
                               valKey.value = valKey.value.substring(arrVal1[m].length(), valKey.value.length());
                               valKey.start = valKey.start + arrVal1[m].length();
                           }else{
                                if(valKey.start + index + arrVal1[m].length() - valKey.end<=1){
                                    valKey.value = valKey.value.substring(0,index-1);
                                    valKey.end = valKey.start + index-1;
                                }
                           }
                           System.out.println(valKey.value + " | " + arrVal1[m] + " | " + index + " | " + arrVal1[m].length());
                        }
                    }
                }
            }
            list1.set(n, valKey);         //
        }                    

        for(int i=0;i<list1.size();i++){
            KeyValue valKey = list1.get(i);
            convert.setDSCompositeValue(valKey.key, valKey.value, listItems.get(i));
            for(int j=0;j<list.size();j++){
                for(int n=0;n<list.get(j).size();n++){
                    KeyValue val = list.get(j).get(n);
                    if(i+1<list1.size()){
                        if(val.start>=valKey.end && val.end<= list1.get(i+1).start){
                            convert.setDSCompositeValue(val.key, val.value, listItems.get(i)); 
                        }
                    }else{
                        if(val.start>=valKey.end)
                            convert.setDSCompositeValue(val.key, val.value, listItems.get(i)); 
                    }
                }
            }
        }
    }
    
    

    
    private void extractPageBaseInfo(String html){
       pageKwywords = match(html, "(?<=\\<meta name\\=\"keywords\" content\\=\")[^>]+?(?=>)");
//       pageTitle = match(html, "(?<=\\<title\\>)[^>]+?(?=\\</title\\>)");
       pageDesription = match(html, "(?<=\\<meta name\\=\"Description\" content\\=\")[^>]+?(?=>)");       
    }
    
    
    private void delieveItems(List<DSComposite> listDSComposite){
        for(int i=0;i<listDSComposite.size();i++){
            if(!listDSComposite.get(i).local.address.equals(""))                 
                MainServer.DBCrawler.listDSComposites.add(listDSComposite.get(i));
        }
      
    }
    
    
    public void setPageBaseInfo(DSComposite item, DSCrawlerUrl url){
        item.collection.url = url.url;
        item.collection.IP = url.IP;
        item.collection.dns = url.dns;
        item.collection.title = url.title;
        item.collection.keywords = pageKwywords;
        item.collection.description = pageDesription;
        
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        item.collection.date = sdf.format(stamp);        
    }
    
    public String match(String content,String filter){
        String val ="";
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                val =m.group();
                break;
            }
        }catch(Exception e){
            System.out.println("Composite Parse match " + e.getMessage());
        }
        return val;
    }     
}
