/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.crawler.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.CrawlerUtiles;
import wuit.common.crawler.db.KeyValue;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class CompositeConvert {
  /////////////////////////////////////////////////////////////
	//将composite对象转换为格式化字符串 —— 字段名:值;
    public String DSCompositeToString(DSComposite info){
        String val ="";
        if(info == null)
            return val;
        val  = val +  "label:" + info.label ;
        val  = val +  ";name:" + info.name ;
        val  = val +  ";phone:"  + info.phone ;
        val  = val +  ";phone_code:" + info.phone_code ;
        val  = val +  ";phone_number:" + info.phone_number;
        val  = val +  ";postcode:" + info.postcode;
        val  = val +  ";province:" + info.local.province ;
        val  = val +  ";city:" + info.local.city ;
        val  = val +  ";county:" + info.local.county;
        val  = val +  ";address:" + info.local.address ;
//        val  = val +  ";district:" + info.district ;
//        val  = val +  ";zone:" + info.zone ;
//        val  = val +  ";zone_number:" + info.zone_number ;
//        val  = val +  ";road:" + info.road ;
//        val  = val +  ";road_number:" + info.road_number ;
        val  = val +  ";house:" + info.local.building ;
//        val  = val +  ";house_number:" + info.local.buildingNo;
        val  = val +  ";storey:" + info.local.floor;
        val  = val +  ";room:" + info.local.room ;
//        val  = val +  ";position:" + info.position ;
//        val  = val +  ";reference:" + info.reference ;
//        val  = val +  ";distance:" + info.distance ;
//        val  = val +  ";direction:" + info.direction ;
        val  = val +  ";Abstract:" + info.Abstract ;
        val  = val +  ";remark:" + info.remark;
        val  = val +  ";content:" + info.content;
        
        val  = val +  ";collect_date:" + info.collection.date;
        val  = val +  ";collect_url:" + info.collection.url;
        val  = val +  ";collect_dns:" + info.collection.dns;
        val  = val +  ";collect_ip:" + info.collection.IP;
        val  = val +  ";collect_title:" + info.collection.title;
        val  = val +  ";collect_keywords:" + info.collection.keywords;
        val  = val +  ";collect_description:" + info.collection.description;
        
        val  = val +  ";date_publish:" + info.date_publish;
        val  = val +  ";time_service:" + info.time_service;        
        val  = val +  ";active_title:" + info.active.title;
        val  = val +  ";active_date_begin:" + info.active.date_begin;
        val  = val +  ";active_date_end:" + info.active.date_end;
        val  = val +  ";active_time:" + info.active.active_time;
        val  = val +  ";active_items:" + info.active.content + ";";
        return val;
    }

    public void setDSCompositeValue(String key,String value,DSComposite info){
        if(key.equals("label")) info.label = value;
        if(key.equals("name")) info.name = value;
        if(key.equals("phone")) info.phone = value;
        if(key.equals("phone_code")) info.phone_code = value;
        if(key.equals("phone_number")) info.phone_number = value;
        if(key.equals("postcode")) info.postcode = value;
        if(key.equals("province")) info.local.province = value;
        if(key.equals("city")) info.local.city = value;
        if(key.equals("county")) info.local.county = value;
        if(key.equals("address")) info.local.address = value;
//        if(key.equals("district")) info.district = value;
//        if(key.equals("zone")) info.zone = value;
//        if(key.equals("zone_number")) info.zone_number = value;
//        if(key.equals("road")) info.road = value;
//        if(key.equals("road_number")) info.road_number = value;
        if(key.equals("house")) info.local.building = value;
//        if(key.equals("house_number")) info.local.buildingNo = value;
        if(key.equals("storey")) info.local.floor = value;
        if(key.equals("room")) info.local.room = value;
//        if(key.equals("position")) info.position = value;
//        if(key.equals("reference")) info.reference = value;
//        if(key.equals("distance")) info.distance = value;
//        if(key.equals("direction")) info.direction = value;
        if(key.equals("Abstract")) info.Abstract = value;
        if(key.equals("remark")) info.remark = value;
        if(key.equals("content")) info.content = value;
        if(key.equals("collect_date")) info.collection.date = value;
        if(key.equals("collect_url")) info.collection.url = value;
        if(key.equals("collect_dns")) info.collection.dns = value;
        if(key.equals("collect_ip")) info.collection.IP = value;
        if(key.equals("collect_title")) info.collection.title = value;
        if(key.equals("collect_keywords")) info.collection.keywords = value;
        if(key.equals("collect_description")) info.collection.description = value;
        if(key.equals("date_publish")) info.date_publish = value;
        if(key.equals("time_service")) info.time_service = value;
        if(key.equals("active_title")) info.active.title = value;
        if(key.equals("active_date_begin")) info.active.date_begin = value;
        if(key.equals("active_date_end")) info.active.date_end = value;
        if(key.equals("active_time")) info.active.active_time = value;
        if(key.equals("active_items")) info.active.content = value;
    }
   //////////////////////
   /*
   public void setComposite(int index,String val,List<KeyValue> fields){
      fields.get(index).value = val;
   }

   public void setCompositeValue(String name,String val,List<KeyValue> fields){
      for (int i=0;i<fields.size();i++){
         if(fields.get(i).key.equals(name)){
            fields.get(i).value = val;
            break;
         }
      }
   }
   */ 

   public void getCompositeListFromPageTxtFile(String filePath, String encode, List<DSComposite> fields){
       String pageTxt = FileIO.read(filePath, encode);
       String itemsStr = match(pageTxt,"(?<=items:\\{)[^{}]+?(?=\\})");
       String[] items = itemsStr.split("\r\n");
       for(int i=0;i<items.length;i++){
           DSComposite info = new DSComposite();
           items[i] = items[i].replace("items:{", "");
           StringToDSComposite(items[i],info);
           fields.add(info);
       }
//       matchValues(valPage,"(?<=item:)",fields);
   }
   
   
   public String getCompositeValue(String name,List<KeyValue> fields){
      for (int i=0;i<fields.size();i++){
         if(fields.get(i).key.equals(name)){
            return fields.get(i).value;
         }
      }
      return "";
   }
   
   public String CompositeToStringByOrder(List<KeyValue> fields){
      String vals = "";
      for(int j=0;j<fields.size();j++){
         vals = vals +  fields.get(j).order + ":" + fields.get(j).value + ";";
      }
      return vals;
   }
   public String CompositeToStringByName(List<KeyValue> fields){
      String vals = "";
      for(int j=0;j<fields.size();j++){
         vals = vals +  fields.get(j).key + ":" + fields.get(j).value + ";";
      }
      return vals;
   }   
   public void StringToCompositeByOrder(String vals,List<KeyValue> fields,List<KeyValue> compositeFields){
      for (int j=0;j<compositeFields.size();j++){
           KeyValue _val = new KeyValue();
           String key = compositeFields.get(j).order+"";
           String filter = "(?<="+ key+":)[^\\;]*?(?=\\;)";         
           _val.key = compositeFields.get(j).key;
           _val.value = match(vals,filter);
           _val.order = compositeFields.get(j).order;
           fields.add(_val);
        }      
   }

   public void StringToCompositeByName(String vals,List<KeyValue> fields,List<KeyValue> compositeFields){
      for (int j=0;j<compositeFields.size();j++){
           KeyValue _val = new KeyValue();
           String key = compositeFields.get(j).key +"";
           String filter = "(?<="+ key+":)[^\\;]*?(?=\\;)";         
           _val.key = compositeFields.get(j).key;
           _val.value = match(vals,filter);
           _val.order = compositeFields.get(j).order;
           fields.add(_val);
        }      
   }   
   
    public void DSCompositeToArrayFields(DSComposite _info,List<KeyValue> listFields,List<KeyValue> compositeFields){
        String vals = DSCompositeToString(_info);
        StringToCompositeByOrder(vals,listFields,compositeFields);
    }
    
    /*
    public static void getHtmlLink(final String s,DSCrawlerUrl pageUrl,Map<String,Integer> mapFilterUrl,Map<String,DSCrawlerUrl> mapUrl){
        Parser m_parser ;
        if(s==null || s.equals(""))
            return ;
        try{
            m_parser = Parser.createParser(s,"utf-8");
            NodeFilter filter = new TagNameFilter("a");
            NodeList nodelist = m_parser.parse(filter);
            NodeIterator it = nodelist.elements();
            while(it.hasMoreNodes()){
                LinkTag node = (LinkTag)it.nextNode();
                
                DSCrawlerUrl _info = new DSCrawlerUrl();
                String url = node.getAttribute("href");
                
                if(url!=null && url.indexOf("#")>=0)
                    continue;
                if((url == null || url.equals("") || 
                        url.toLowerCase().indexOf("script")>=0)){
                    continue;
                }
                //System.out.println(url);
                //解析url
                _info = ParseUtils.parsePageUrl(url,pageUrl.url);
                if(_info == null)
                    continue;                
                //
                if(node.toPlainTextString() == null || node.toPlainTextString().equals(""))
                    _info.title = node.getAttribute("title");
                else
                    _info.title = node.toPlainTextString().replaceAll("\\s+?", "");
                if(_info.title == null ||_info.title.equals(""))
                    _info.title = "YYYY";
                //
                _info.crawlerDeepth = pageUrl.crawlerDeepth + 1;
                if(!mapUrl.containsKey(_info.url)){
                    //验证url是否有效（即是否包含关键字），如果是有效的，则添加
                    Set _set = mapFilterUrl.entrySet();
                    Iterator _it = _set.iterator();
                    boolean out = false;
                    boolean in = true;
                    while(_it.hasNext()){
                        Entry<String,Integer> body = (Entry<String,Integer>)_it.next();
                        if(body.getValue() == 0 && _info.url.indexOf(body.getKey())>0){
                            out = true;
                        }
                        if(body.getValue() == 1)
                            in = false;
                    }
                    _set = mapFilterUrl.entrySet();
                    _it = _set.iterator();                    
                    while(_it.hasNext()){
                        Entry<String,Integer> body = (Entry<String,Integer>)_it.next();
                        if(body.getValue() == 1 && _info.url.indexOf(body.getKey())>=0){
                            in = true;
                        }
                    }
                    if( in && !out && !_info.dns.isEmpty()){
                         mapUrl.put(_info.url,_info);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(" composite Convert extractorUrl :" + e.getMessage());
        }
    }     
    */
 /** 
  *  
  * @param s 
  * @return 获得脚本代码 
  */
    public List<String> getHtmlScript(final String s){
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<script.*?</script>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()){
            list.add(ma.group());
        }
        return list;
    }
    /**
     *
     * @param s
     * @return 获得网页标题
     */  




    /*
    public static String getHtmlEncode(final String s){
        String val = match(s, "(?<=\\<meta http-equiv='content-type' content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html; charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta charset=\")[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s,"(?<=\\<meta content=\"text/html; charset=)[^>]+?(?=\")");
        return val;
    }
    */
    
    
    //DSExtractor params
    /*
    public static String clearHtml(String html,List<KeyValue> lstClear){
        String content = html;
        for (int i=0;i<lstClear.size();i++){
            content = content.replaceAll(lstClear.get(i).key,lstClear.get(i).value);
        }
        return content;
    }
    */
    

    
    /**
     *
     * @param s
     * @return 获得网页标题
     */  
    /*
    public static String getHtmlContent(final String s){
         final List<KeyValue> list = new ArrayList<KeyValue>();
        matchValues(s,"(?<=\\<meta name\\=\"keywords\" content\\=\")[^>]+?(?=/>)",list);
        String vals ="";
        for (int i = 0; i < list.size(); i++){
            vals = vals + list.get(i).value + "\r\n";
        }
        return vals;
    */            
        /*
        String regex;
        String title = "";
        final List<String> list = new ArrayList<String>();
        regex = "(?<=\\<head\\>)[^head]+?(?=\\<\\/head\\>)";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ|Pattern.CASE_INSENSITIVE);
        final Matcher ma = pa.matcher(s);
        while (ma.find()){
            list.add(ma.group());
        }
        for (int i = 0; i < list.size(); i++){
            title = title + list.get(i);
        }
        return outTag(title);
        */ 
    //}
    
    /**
     *
     * @param s
     * @return 去掉标记
     */
    /*
    public static String outTag(final String s){
        return s.replaceAll("<.*?>", "");
    }     
    */
    
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
   
    public void matchValues(String content,String filter,List<KeyValue> list){
        if (list == null)
            list = new ArrayList<KeyValue>();
        try{		
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if(m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.value = m.group();
                value.start = m.start();
                value.end = m.end();
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("composite parse  matchValues :" + e.getMessage());
        }
    }
   
    public void matchValues(String content,String name,String filter,List<KeyValue> list){
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if (m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.key = name;
                value.value = m.group();
                value.start = m.start();
                value.end = m.end();
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("composite parse  matchValues :" + e.getMessage());
        }
    }
    
    public void CrawlerPageTxtToDSCrawlerPageTxt(String pageTxt,DSCrawlerPageTxt page){
        String url = match(pageTxt, "(?<=url:\\{)[^}]+(?=})");
        String title = match(pageTxt, "(?<=title:\\{)[^}]+(?=})"); 
        String dns = match(pageTxt, "(?<=dns:\\{)[^}]+(?=})"); 
        String extract = match(pageTxt, "(?<=items:\\{)[^}]+(?=})");
        List<DSComposite> listItems = new ArrayList<DSComposite>();
        
        String[] items = extract.split("\r\n");
        for (int i=0;i<items.length;i++){
            DSComposite info = new DSComposite() ;
            info.collection.url = url;
            info.collection.dns = dns;
            info.collection.title = title;
            
            StringToDSComposite(items[i], info);
            listItems.add(info);
        }
        
        if(page == null)
            page = new DSCrawlerPageTxt();
        page.content = match(pageTxt, "(?<=content:\\{)[^}]+(?=})");
        page.items = listItems;
        String[] vals = url.split(";");
        if(vals.length == 3){
            page.urlCrawler.title = vals[0];
            page.urlCrawler.dns = vals[1];
            page.urlCrawler.url = vals[2];
        }
    }
   
   public void StringToDSComposite(String val,DSComposite _info){
       if(val == null)
           return;
       _info.label = match(val,"(?<=label:)[^;]*(?=;)");
       _info.name = match(val,"(?<=name:)[^;]*(?=;)");
       _info.local.address = match(val,"(?<=address:)[^;]*(?=;)");
       _info.local.province = match(val,"(?<=province:)[^;]*(?=;)");
       _info.local.city = match(val,"(?<=city:)[^;]*(?=;)");
       _info.local.county = match(val,"(?<=county:)[^;]*(?=;)");
//       _info.local.district = match(val,"(?<=district:)[^;]*(?=;)");
//       _info.zone = match(val,"(?<=zone:)[^;]*(?=;)");
       _info.local.building = match(val,"(?<=house:)[^;]*(?=;)");
//       _info.local.buildingNo = match(val,"(?<=house_number:)[^;]*(?=;)");
       _info.local.floor = match(val,"(?<=storey:)[^;]*(?=;)");
       _info.local.room = match(val,"(?<=room:)[^;]*(?=;)");
//       _info.position = match(val,"(?<=position:)[^;]*(?=;)");
       
       _info.postcode = match(val,"(?<=postcode:)[^;]*(?=;)");
       _info.phone = match(val,"(?<=phone:)[^;]+(?=;)");
       _info.phone_code = match(val,"(?<=phone_code:)[^;]*(?=;)");
       _info.phone_number = match(val,"(?<=phone_number:)[^;]*(?=;)");
       
//       _info.reference = match(val,"(?<=reference:)[^;]*(?=;)");
//       _info.distance = match(val,"(?<=distance:)[^;]*(?=;)");
//       _info.direction = match(val,"(?<=direction:)[^;]*(?=;)");
       
       _info.date_publish = match(val,"(?<=date_publish:)[^;]*(?=;)");
       _info.time_service = match(val,"(?<=time_service:)[^;]*(?=;)");
       
       _info.collection.date = match(val,"(?<=collect_date:)[^;]*(?=;)");
       _info.collection.url = match(val,"(?<=collect_url:)[^;]*(?=;)");
       _info.collection.title = match(val,"(?<=collect_title:)[^;]*(?=;)");
       _info.collection.description = match(val,"(?<=collect_description:)[^;]*(?=;)");
       _info.collection.keywords = match(val,"(?<=collect_keywords:)[^;]*(?=;)");
       _info.collection.dns = match(val,"(?<=collect_dns:)[^;]*(?=;)");
       _info.collection.IP = match(val,"(?<=collect_IP:)[^;]*(?=;)");
       
       _info.active.date_begin = match(val,"(?<=active_date_begin:)[^;]*(?=;)");
       _info.active.date_end = match(val,"(?<=active_date_end:)[^;]*(?=;)");
       _info.active.content = match(val,"(?<=active_items:)[^;]*(?=;)");
       _info.active.active_time = match(val,"(?<=active_time:)[^;]*(?=;)");
       _info.active.title = match(val,"(?<=active_title:)[^;]*(?=;)");
       
       _info.Abstract = match(val,"(?<=Abstract:)[^;]*(?=;)");
       _info.remark = match(val,"(?<=remark:)[^;]*(?=;)");
       _info.content = match(val,"(?<=content:)[^;]*(?=;)");
       
       if(_info.local.address.isEmpty() && _info.name.isEmpty())
           _info = null;
   }  
   

   
   
   public static void main(String[] args){
       CompositeConvert convert = new CompositeConvert();
       String sVal = "biptbdsw   ã*3¢                             P  é     é                 äì B      B  Å8   ";
       String mVal = convert.match(sVal,"[\\u4E00-\\u9FA5]");
       
       List<DSComposite> listComposite = new ArrayList<DSComposite>();
       convert.getCompositeListFromPageTxtFile("D:\\product\\aibang\\wuhan\\content\\百度文库搜索群光广场武汉洪山区珞喻路6号1376231819578.txt", "utf-8", listComposite);
       
       
       
       DSExtractor param = new DSExtractor();
       String val = "items:{label:;name:上海市上海百联世茂国际广场;phone:;phone_code:;phone_number:;postcode:;province:江苏省;city:南京市;county:;address::上海市南京东路819号百联世茂国际广场;district:南京东路;zone:;house::上海市;house_number:819号;storey:;room:;position:;reference:;distance:;direction:;Abstract:;remark:;content:;collect_date:2013/08/09 07:16:31;collect_url:http://pp.ppsj.com.cn/Ama/;collect_dns:;collect_title:Ama_Ama品牌专区;collect_keywords:Ama,,Ama品牌专区\";collect_description:AmaAma，一个起源于180多年前的意大利手工饰品家族品牌，于1998年进入中国。在AMA的珠宝世界里，没有肤浅的模仿者，也没有潮流的盲从者；摒弃了繁复驳杂的设计语言，也摒弃了粗糙劣质的工美主义，只有抽象而浪漫的雅致形象，只有精致而清新的简洁线条。“理性与情感的交融”，“时尚与经典的......品牌世家网您提供详细的Ama资讯\";date_publish:;time_service:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";
       DSComposite _info = new DSComposite();
       convert.StringToDSComposite(val, _info);
       System.out.println( convert.DSCompositeToString(_info));
       String url = "http://mall.ppsj.com.cn/6B666C497FA451495E7F573A/";
       url = "http://www.aibang.com/?area=bizsearch2&cmd=bigmap&city=%E4%B8%8A%E6%B5%B7&ufcate=%E5%B0%8F%E5%90%83%E5%BF%AB%E9%A4%90&a=%E5%B4%87%E6%98%8E%E5%8E%BF&q=&rc=1&as=5000&apr=0%7C0&zone=1";
       url = "http://mall.ppsj.com.cn/6B666C497FA451495E7F573A/";
//       url = "http://so.360.cn/s?q=%E7%88%B1%E5%B8%AE%E7%BD%91&pq=%E6%AD%A6%E6%B1%89%E4%B8%80%E6%AF%8D%E9%B8%A1%E7%9B%B4%E6%8E%A5%E4%B8%8B%E5%87%BA%E7%86%9F%E9%B8%A1%E8%9B%8B&src=srp";
//       url ="http://so.360.cn/s?ie=utf-8&src=hao_search&q=%E6%AD%A6%E6%B1%89%E4%B8%80%E7%BE%A4%E5%85%89%E5%B9%BF%E5%9C%BA";
//       url = "http://q.chinasspp.com/2-19761.html";
//       url = "http://pp.ppsj.com.cn/basto/";
//       url = "http://taobao.mizhe.com/shop/%E7%99%BE%E6%80%9D%E5%9B%BE%E5%AE%98%E6%96%B9%E6%97%97%E8%88%B0%E5%BA%97.html";
//       url = "http://bj.city8.com/canyinfuwu/2248446_070OR.html";
//       url = "http://bj.city8.com/canyinfuwu/2031627_6K0UD_address.html";
//       url = "http://bj.city8.com/canyinfuwu/2009015_6F91H.html";
//       url = "http://lj.city8.com/";
//       url = "http://www.aibang.com/detail/1953044342-409607691";
//       url = "http://www.dianping.com/shop/8068337/review_more";
//       url = "http://www.dianping.com/shop/8068337";
//       url = "http://bj.city8.com/canyinfuwu/3909858_QIJV_address.html";
//       url = "http://www.ddmap.com/map/10----%C3%8C%C3%B0%C6%B7%C3%8C%C3%B0%C2%B5%C3%A3-dFFleB-dcfacdii--/";
//       url = "http://bj.xiaomishu.com/shop/E31K12B58293/";
//       url = "http://so.360.cn/s?q=%E9%9B%85%E7%AD%91%E7%94%9C%E5%93%81%E4%B9%A6%E5%90%A7+%E8%82%B2%E6%85%A7%E5%8D%97%E8%B7%AF&pn=1&j=0";
//       url = "http://www.baidu.com/s?wd=%E9%9B%85%E7%AD%91%E7%94%9C%E5%93%81%E4%B9%A6%E5%90%A7+%E8%82%B2%E6%85%A7%E5%8D%97%E8%B7%AF&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&rsv_n=2&rsv_sug3=1&rsv_sug1=1&rsv_sug4=100&inputT=1435";
       
//       url = "http://www.aibang.com/bizsearch/%E5%8C%97%E4%BA%AC_%E6%9C%9B%E4%BA%AC%E8%8A%B1%E5%9B%AD%E8%A5%BF%E5%8C%BA%20_%E7%94%9C%E5%93%81";
       
//      url = "http://www.aibang.com/detail/1365803642-448516163";       
//       url = "http://www.aibang.com/bizsearch/%E5%8C%97%E4%BA%AC_%E5%85%A8%E5%B8%82_%E6%A6%B4%E8%8E%B2%E5%BF%98%E8%BF%94";
       
       url = "http://meishi.qq.com/beijing/s/d110105-a1000007";
       
       String key = "武汉群光广场欧珀莱";
//       url = "http://www.baidu.com/s?wd=" + key ;//+ "&pn=100&tn=site888_pg&rn=10";
//       url ="http://www.baidu.com/baidu?myselectvalue=0&tn=baidu&ct=&lm=&cl=&rn=&myselect=0&word="+ "武汉群光广场 七楼";
//       url = "http://cache.baiducontent.com/c?m=9d78d513d9d430a54f9de7690c66c0161243f7632bd6a0020fdf843f92732b405015e0ac27530774d4d20a6d16d94d4b9af52235775d2feddd8eca5ddcc88f356ad23034074ddd0759d90eafbc17789e3dc109b5e442bbeeb777d4b3d2d7d85054cc44020ef083fb5e1714bd35b64b6f&p=8a3edf16d9c107ff57ee937a4a4392&newp=8b2a971bc7d018e440bd9b7d0a12cb231610db2151d3db1721c58011d87c&user=baidu&fm=sc&query=%C8%BA%B9%E2%B9%E3%B3%A1+%B5%D8%D6%B7&qid=&p1=109";
       
//       url = "http://www.baidu.com/link?url=o0Yd9aoeJtwyxmti03m3hyzvIxYT-_ccNI2FCAl3LLxw9Plq9zwy2O7BOEX7pmoi";
 //      url = "http://shopping.ppsj.com.cn/577/whqggctittot5779/";
       
//       url = "http://pp.ppsj.com.cn/Aupres/";
       
//       url = "http://pp.ppsj.com.cn/FURLA/";
       
//       url ="http://www.pinpaihudong.com/aupres/shop1919021.html";
//       url = "http://so.360.cn/s?ie=utf-8&src=hao_search&q=%E6%AD%A6%E6%B1%89%E7%BE%A4%E5%85%89%E5%B9%BF%E5%9C%BA%E6%AC%A7%E7%8F%80%E8%8E%B1";
       
//       url = "http://mall.ppsj.com.cn/6B666C497FA451495E7F573A/";
       
//       url = "http://pp.ppsj.com.cn/ChristianDior/";
       /*
       String val = CrawlerUtiles.doGetHttp(url);
       
       CompositeConvert.getHtmlTitle(val);
       String title = CompositeConvert.getHtmlTitle(val);
       System.out.println(title);
       
       System.out.println(CompositeConvert.getHtmlEncode(val));
       String description  = CompositeConvert.getHtmlDesciption(val);
       System.out.println(description);
       String keyWords = CompositeConvert.getHtmlKeywords(val);
       System.out.println(keyWords);
       */
       
       /*
                <replace order="1" key="&lt;script[^&gt;]*&gt;[\d\D]*?&lt;/script&gt;|&lt;style[^&gt;]*&gt;[\d\D]*?&lt;/style&gt;" value=""></replace>
                <replace order="2" key="：" value=":"></replace>
                <replace order="4" key="、" value=","></replace>
                <replace order="3" key="&lt;([^&gt;]*)&gt;" value="、"></replace>
                <replace order="4" key="\s*|t|r|n" value=""></replace>
                <replace order="5" key="\、{2,}" value="、"></replace>
                <replace order="6" key=":、" value=":"></replace>       
      */
       List<KeyValue> clearList = new ArrayList<KeyValue>();
       KeyValue _val = new KeyValue();
       _val.key = "<script[^>]*>[\\d\\D]*?</script>|<style[^>]*>[\\d\\D]*?</style>";
       _val.value = "";
       clearList.add(_val);       
       _val = new KeyValue();       _val.key = "[：|　]";                _val.value = "";      clearList.add(_val);
       _val = new KeyValue();       _val.key = "、";                _val.value = ",";      clearList.add(_val);               
       _val = new KeyValue();       _val.key = "<([^>]*)>";         _val.value = "、";     clearList.add(_val);       
       _val = new KeyValue();       _val.key = "[\\s*|t|r|n|　]";     _val.value = "";       clearList.add(_val); 
       _val = new KeyValue();       _val.key = "[\\[|\\]]";         _val.value = "、";      clearList.add(_val);
       _val = new KeyValue();       _val.key = "\\、{2,}";          _val.value = "、";     clearList.add(_val);        
       _val = new KeyValue();       _val.key = ":\\、";               _val.value = ":";      clearList.add(_val);
       
       
       //String hval = CompositeConvert.clearHtml(val, clearList);       
       //System.out.println(hval);
       
//       List<DSComposite> cmpInfos = new ArrayList<DSComposite>();       
//       CompositeParse parse = new CompositeParse();
       List<KeyValue> fieldRules = new ArrayList<KeyValue>();
       /*
        *       <name>([^、]+、){1}[^、]+(?=、地址)</name>
                <address>(?&lt;=地址:)[^、]+?(?=、)</address>
                <label>(?&lt;=标签:)[^、,]+?(?=、|,)|(?&lt;=区域:)[^、]+?(?=、)</label>
                <phone>\d{8,13}|\d{3,4}-\d{7,8}|\d{3}-\d{3}-\d{4}</phone>
                <phone_code>\d{3,4}(?=-)</phone_code>
                <phone_number>(?&lt;=-)\d{7,8}</phone_number>
                <postcode>\d{6}\s</postcode>
                <remark>(?&lt;=点评)[^、]+?(?=、)|(?&lt;=评分)[^、]+、[^、]+?(?=、)</remark>
        */
       _val = new KeyValue();       _val.key = "name";       _val.value = "([^、]+、){5}[^、]+(?=、地址)";       fieldRules.add(_val);
       _val = new KeyValue();       _val.key = "address";    _val.value = "(?<=地址)[^、]+?(?=、)";       fieldRules.add(_val);
       _val = new KeyValue();       _val.key = "remark";     _val.value = "简介、*([^、]+?、){3}";       fieldRules.add(_val);       
       _val = new KeyValue();       _val.key = "label";      _val.value = "(?<=标签:)[^、,]+?(?=、|,)|(?<=区域:)[^、]+?(?=、)";       fieldRules.add(_val);
       _val = new KeyValue();       _val.key = "phone";      _val.value = "\\d{8,13}|\\d{3,4}-\\d{7,8}|\\d{3}-\\d{3}-\\d{4}";       fieldRules.add(_val);              
       /*
       DSCrawlerPageInfo urlObj = new DSCrawlerPageInfo();
       urlObj.url = url;
       urlObj.title = title;
       urlObj.description = description;
       urlObj.keyWords = keyWords;
       CompositeConvert.extractRecordsFromContent(hval,urlObj, fieldRules,cmpInfos);
       for (int i=0;i<cmpInfos.size();i++){
           CompositeParse.extractRecord(cmpInfos.get(i));
           System.out.println(CompositeConvert.DSCompositeToString(cmpInfos.get(i)));
       }
       */
       Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
       Map<String,Integer> mapFilterUrl = new HashMap<String,Integer>();
//       lstFilter.add("163.com/");
//       lstFilter.add("blog");
       DSCrawlerUrl pageUrl = new DSCrawlerUrl();
       pageUrl.url = url;
       
       //CompositeConvert.getHtmlLink(val,pageUrl,lstFilter,mapUrl);

       DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt(); 
       CrawlerUtiles utiles = new CrawlerUtiles();
  /*     
       utiles.extractHtmlPage(0,pageUrl, clearList, fieldRules, mapFilterUrl, mapUrl, extractPageInfo);
       Map<String,String> mapRs = new HashMap<String,String>();
       String keyWord = "群光广场欧珀莱";
       keyWord = "武昌珞喻路6号";
//      utiles.CrawlerKeyWordsBaiDu(keyWord, clearList, fieldRules, mapFilterUrl, mapUrl, extractPageInfo);
       
      for(int i=0;i<extractPageInfo.items.size();i++){
//          String rs = CompositeConvert.DSCompositeToString(extractPageInfo.items.get(i));
          if(extractPageInfo.items.get(i) == null)
              continue;
//          String _rs = extractPageInfo.items.get(i).address +"|" +extractPageInfo.items.get(i).collect_keywords;//

          String rs = CompositeConvert.DSCompositeToString(extractPageInfo.items.get(i));
          System.out.println(rs);
          if(!mapRs.containsKey(rs))
              mapRs.put(rs, "");
//          System.out.println(CompositeConvert.DSCompositeToString(extractPageInfo.items.get(i)));
      }
*/
/*       
       Set set = mapUrl.entrySet();
       Iterator it = set.iterator();
       while(it.hasNext()){
           Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
//           System.out.println(body.getValue().IP + "|" + body.getValue().dns +  "|" + body.getValue().url + "|" + body.getValue().title);
           
           extractPageInfo = new DSCrawlerPageTxt(); 
           Map<String,DSCrawlerUrl> _mapUrl = new HashMap<String,DSCrawlerUrl>();
           utiles.extractHtmlPage(0,body.getValue(), clearList, fieldRules, mapFilterUrl, _mapUrl, extractPageInfo);           
            for(int i=0;i<extractPageInfo.items.size();i++){
                if(extractPageInfo.items.get(i) == null)
                    continue;
//                String rs = extractPageInfo.items.get(i).address +"|" +extractPageInfo.items.get(i).collect_keywords;//CompositeConvert.DSCompositeToString(extractPageInfo.items.get(i));
                String rs = CompositeConvert.DSCompositeToString(extractPageInfo.items.get(i));
                System.out.println(rs);
                if(!mapRs.containsKey(rs))
                    mapRs.put(rs, "");
            }
       }
       
       List<String> _rs = new ArrayList<String>();
       Set _set =  mapRs.entrySet();
       Iterator _it = _set.iterator();
       while(_it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)_it.next();
           _rs.add(body.getKey());
       }
       
       Collections.sort(_rs);
       for(int i=0;i<_rs.size();i++){
           if(_rs.get(i).indexOf(keyWord)>=0)
            System.out.println(_rs.get(i));
       }
      */   
   }
   
}
