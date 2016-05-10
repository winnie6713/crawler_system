/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.WebSit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import wuit.application.wins.WebSitCrawler;
import wuit.common.crawler.composite.CompositeConvert;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.crawler.db.KeyValue;

/**
 *
 * @author lxl
 */
public class QunGuangWH{
    
    static String [] zones = {"B1,1,13,15,18,20",
        "1,21,22,23,24",
        "2,3,27,28",
        "3,30,31,32,33,34",
        "4,35,36,37,38,39,40,41,42,43",
        "5,45,46,47,48,49,50,51",
        "6,52,53,54,55,57,58,59,60,61",
        "7,10,11,12",
        "8,62,63"};
    
    class SearchInfo{
        String[] floor = {"B1","一层","二层","三层","四层","五层","六层","七层"};
        String[] code;
    }
    
    class FloorInfo{
        String name = "";
        String code = "";
    }
    
    class BrandInfo{
        
        String name = "";
    }
    
    class SearchParam{
        String paramName = "";
        String paramValue = "";        
    }

    class SearchMethod{
        String url = "http://www.chiconysquare.com/webserver/Shopping.aspx?do=shopinfo";
        String paramValue = "";        
    }
    
    
    static DSCrawlerUrl pageUrl = new DSCrawlerUrl();
    static CompositeConvert convert = new CompositeConvert();
    
   public static List<String> ListVals= new ArrayList<String>();
   
   public static String[] Vals = null;
   
   public static String extractJQuery(DSCrawlerUrl pageUrl){
       String rsVal = Crawler.doGetHttp(pageUrl);
       return rsVal;
   }
   
   public static DSComposite queryBrand(String id){
        int rd = (int)(Math.random()*10000);
        pageUrl.url = "http://www.chiconysquare.com/webserver/Shopping.aspx?do=shopinfo&id="+ id +"&code="+rd;                
        String rsVal = QunGuangWH.extractJQuery(pageUrl);
        String[] arrVals = rsVal.split("\\|");
        DSComposite info = toDSComposite(arrVals);
        return info;
   }
   
   public static void dispBrands(DSComposite info){
       System.out.println(convert.DSCompositeToString(info));
   }
  
   public static List<KeyValue> queryZone(String zone ){
        int rd = (int)(Math.random()*10000);
        pageUrl.url = "http://www.chiconysquare.com/webserver/DoOperation.aspx?do=getzone&id=" + zone + "&code="+rd;
        String rsVal = QunGuangWH.extractJQuery(pageUrl);
        List<KeyValue> params_method = new ArrayList<KeyValue>();
        List<KeyValue> params_zone = new ArrayList<KeyValue>();

//        System.out.println("zones  : z=: " + zone + "  rd=:" + rd + " " + rsVal);            
        Crawler.matchValues(rsVal,"(?<=\\{\"ID\":\")[^}]+?(?=\"\\}\\,)",params_method);

        for(int i=0;i<params_method.size();i++){                
            KeyValue val = new KeyValue();
//            System.out.println(params_method.get(i).value);
            val.key = Crawler.match(params_method.get(i).value,"[^\"]+?(?=\",)");
            val.value = Crawler.match(params_method.get(i).value,"(?<=\"BrandName\":\").+");
            params_zone.add(val);
            
            System.out.println(val.key + ":" + val.value);
        }
        return params_zone;
   } 
   
   public static void queryLayer(int layer){
       String[] vals = zones[layer].split(",");
       if(vals.length>1){
            for(int i=1;i<vals.length;i++){
                List<KeyValue> list = queryZone(vals[i]);
            }
       }
   }
   
   public static void queryLayerBrands(int layer){
       String[] vals = zones[layer].split(",");
       if(vals.length>1){
            for(int i=1;i<vals.length;i++){
                List<KeyValue> list = queryZone(vals[i]);
                for(int j=0;j<list.size();j++){
                    //dispBrands(queryBrand(list.get(j).key));
                    System.out.println(list.get(i).key + ":" + list.get(i).value);
                }
            }
       }
   }   
   
   public static void query(String url){
       WebSitCrawler.listInfo.clear();
       
        for(int z=0;z<=100;z++){
            int rd = (int)(Math.random()*10000);
            pageUrl.url = "http://www.chiconysquare.com/webserver/DoOperation.aspx?do=getzone&id="+z+"&code="+rd;
            
            String rsVal = QunGuangWH.extractJQuery(pageUrl);
            List<KeyValue> params_method = new ArrayList<KeyValue>();
            List<KeyValue> params_zone = new ArrayList<KeyValue>();
//            System.out.println("zones  : z=: " + z + "  rd=:" + rd + " " + rsVal);            
            Crawler.matchValues(rsVal,"(?<=\\{\"ID\":\")[^}]+?(?=\"\\}\\,)",params_method);
            
            for(int i=0;i<params_method.size();i++){                
                KeyValue val = new KeyValue();
                val.key = Crawler.match(params_method.get(i).value,"[^\"]+?(?=\",)");
                val.value = Crawler.match(params_method.get(i).value,"(?<=\"BrandName\":\").+");
                params_zone.add(val);
            }
            for(int i=0;i<params_zone.size();i++){
                rd = (int)(Math.random()*10000);
                String id = params_zone.get(i).key;
                
                pageUrl.url = "http://www.chiconysquare.com/webserver/Shopping.aspx?do=shopinfo&id="+ id +"&code="+rd;                
                rsVal = QunGuangWH.extractJQuery(pageUrl);
                String[] arrVals = rsVal.split("\\|");
                
//                System.out.println(arrVals.length+ " " + rsVal);
                DSComposite info = toDSComposite(arrVals);
                //ListVals.add(convert.DSCompositeToString(info));
                WebSitCrawler.listInfo.add(info);
            }
        }
        
        //Vals = new String[ListVals.size()];
        //for(int i=0;i<ListVals.size();i++){
        //    //System.out.println(i + " " + ListVals.get(i));
        //    Vals[i] = ListVals.get(i);
        //}
        
   }
   
   public String[] getVals(){
       return Vals;
   }
   
   public static DSComposite toDSComposite(String[] arrVals){
        DSComposite info = new DSComposite();
        info.name = arrVals[0];
        info.local.address = "湖北省武汉市洪山区珞瑜路6号";
        info.local.province = "湖北省";
        info.local.city = "武汉市";
        info.local.county = "洪山区";
        info.local.Road = "珞瑜路";
        info.local.RoadNo = "6号";
        info.local.building = "武汉群光广场";
        info.phone_code = "027";
        info.postcode = "430070";
        info.collection.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        info.collection.dns = "www.chiconysquare.com";
        if(arrVals.length>3)
            info.Abstract = arrVals[3];
        if(arrVals.length>4)
            info.label = arrVals[4];
        if(arrVals.length>5)
            info.local.floor = arrVals[5];
//        info.zone = arrVals[6];

        if(arrVals.length>3)
            info.phone = Crawler.match(arrVals[3],"(?<=电话：)[0-|\\-]{3,}|(?<=热线：)[0-9|\\-]{3,}");
        return info;
   }
   
   
    public static void main(String[] args){
        QunGuangWH.query("");
//        QunGuangWH.queryBrand("351");
        
//        QunGuangWH.listVals.clear();
//        QunGuangWH.queryBrand("203");
        
//        QunGuangWH.dispBrands(QunGuangWH.queryBrand("351"));
//        QunGuangWH.dispBrands(QunGuangWH.queryBrand("203"));
//        QunGuangWH.queryZone("1");
        
//        QunGuangWH.queryLayerBrands(1);
 //       queryLayerBrands(1);
//        DSCrawlerUrl url = new DSCrawlerUrl();
//        url.url = "http://map.dmw.gov.cn/index.php/area/dataList/?pid=0";
//        String val = Crawler.doGetHttp(url);
//        System.out.println(val);
    }
  
}
