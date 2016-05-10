/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.db.KeyValue;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class MapNameAddress {
   String path_base_province = "D:\\product\\lib\\Address\\district\\province\\";
   String path_base_city = "D:\\product\\lib\\Address\\district\\city\\";   
   String path_base_china = "D:\\product\\lib\\Address\\phone\\tempPhone\\";
   
   Map<String,String> mapName = new HashMap<String,String>();
   Map<String,Map<String,String>> mapProvinceCity = new HashMap<String,Map<String,String>>();  
   
   List<String> keys = new ArrayList<String>();
   
   public MapNameAddress(){
      keys.add("路");
      keys.add("街");
      keys.add("镇");
      keys.add("村");
      keys.add("巷");
      keys.add("区");
      keys.add("湾");   
   }
   
   public List<String> queryName(String name){
      List<String> list = new ArrayList<String>();
      String _vals =  mapName.get(name);
      if(_vals == null || _vals.equals(""))
         return list;
      String[] arrVals =  _vals.split(";");
      for(int i=0;i<arrVals.length;i++)
         list.add(arrVals[i]);
      return list;
   }
   
   public void InitializeChina(){
       Initialize(path_base_china,0);
   }
   
   public void InitializeProvince(String province){
       String path = path_base_province + province + "\\";
       Initialize(path,1);
   }

   public void InitializeCity(String city){
       String path = path_base_city + city + "\\";
       Initialize(path,1);
   }
   
   private void Initialize(String path,int mode){
      try{
          List<String> lstFiles = new ArrayList<String>();
          List<String> dirs = new ArrayList<String>();
          FileIO.getFilesAndSubDirFiles(path, lstFiles, dirs);

          for (int i = 0; i<lstFiles.size(); i++){
             String pathFile = lstFiles.get(i);
             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
             String c = br.readLine();
             while(c != null){
                BaseAddress base = new BaseAddress(c);            
                base.city = base.city.replace(base.province, "");
                base.county = base.county.replace(base.city, "");
                
                if(mode == 0)
                   base.district = "";
                putNameValue(base,mode);

                putMapProvinceCity(base.province,base.city);
                c =br.readLine();
             }
             br.close();
          }
      }catch(Exception e){
          System.out.println(e.getMessage());
      }       
   }
   
   
   private void putNameValue(BaseAddress base,int mode){
       String key ="";
       String value;
       
       if (base.province!=null && !base.province.equals("")){
           base.level = 1;
           base.key = base.province;
           value = base.ToString(base);
           value = value.replace(base.city, "");
           value = value.replace(base.county, "");
           value = value.replace(base.district, "");
           value = value.replace(base.post, "");
           value = value.replace(base.phone, "");
           
           putKeyName(base.key,value);
           if (base.key.indexOf("省")>=0){base.key = base.key.replace("省", "");}
           if (base.key.indexOf("市")>=0){base.key = base.key.replace("市", "");}
           if (base.key.indexOf("自治区")>=0){base.key = base.key.replace("自治区", "");}
           putKeyName(base.key,value);
       }
       if (base.city!=null && !base.city.equals("")){
           base.level = 2;
           value = base.ToString(null);
           value = value.replace(base.county, "");
           value = value.replace(base.district, "");
           value = value.replace(base.post, "");
           base.key = base.city;
           putKeyName(base.key,value);
           
           if (base.city.indexOf("市")>=0){base.key = base.city.substring(0,base.city.length()-1);}
           if (base.city.indexOf("地区")>=0){base.key = base.city.substring(0,base.city.length()-2);}
           else{if (base.city.indexOf("区")>=0){base.key = base.city.substring(0,base.city.length()-1);}}
           if (base.city.indexOf("自治州")>=0){base.key = base.city.substring(0,base.city.length()-3);}
           
           putKeyName(base.key,value);
       }
       if (base.county != null && !base.county.equals("")){
           base.level = 3;
           value = base.ToString(null);
           value = value.replace(base.district, "");
           value = value.replace(base.post, "");
           base.key = base.county;
           putKeyName(base.key,value);
           
           if (base.county.indexOf("区")>=0){base.key = base.county.substring(0,base.county.length()-1);}
           if (base.county.indexOf("市")>=0){base.key = base.county.substring(0,base.county.length()-1);}
           if (base.county.indexOf("县")>=0){base.key = base.county.substring(0,base.county.length()-1);}
           if (base.county.indexOf("自治县")>=0){base.key = base.county.substring(0,base.county.length()-3);}
           putKeyName(base.key,value);
       }
       if(mode == 1){
           base.level = 4;
           value = base.ToString(null);
           String val = base.district;
           for(int k=0;k<keys.size();k++){
               List<KeyValue> list = new ArrayList<KeyValue>();
               String filter = "[^路街巷区镇乡村号湾]+?" + keys.get(k);
               matchValues(val,filter,list);
               for(int i=0;i<list.size();i++){
                   base.key = list.get(i).value;
                   putKeyName(base.key,value);
               }
           }
       }
   }
   
    private void putKeyName(String key,String value){
        if(!mapName.containsKey(key))
            mapName.put(key, value);
        else{
            if(mapName.get(key).indexOf(value)==-1){
                String val = mapName.get(key) + ";" + value;
                mapName.remove(key);
                mapName.put(key, val);
            }
        }
    }
   
    private void putMapProvinceCity(String province,String city){
        if(!mapProvinceCity.containsKey(province)){
            Map<String,String> _city = new HashMap<String,String>();
            _city.put(city, city);
            mapProvinceCity.put(province, _city);
        }else{
            if(!mapProvinceCity.get(province).containsKey(city)){
                mapProvinceCity.get(province).put(city, city);
            }
        }
    }
   
    private void matchValues(String content,String filter,List<KeyValue> list){
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if (list == null)
                    list = new ArrayList<KeyValue>();
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
   
    public static void main(String[] args){
        
       //System.gc();
       long freeMem1 = Runtime.getRuntime().freeMemory();
       long totalMem1 = Runtime.getRuntime().totalMemory();
       long maxMem1 = Runtime.getRuntime().maxMemory();
       
       System.out.println(Runtime.getRuntime().freeMemory()); 
       System.out.println(Runtime.getRuntime().totalMemory()); 
       System.out.println(Runtime.getRuntime().maxMemory());        
        
        
       long startMili=System.currentTimeMillis();
       System.out.println("开始 s "+startMili);
       
        MapNameAddress map = new MapNameAddress();
        map.InitializeChina();
        
	long endMili=System.currentTimeMillis();	
        System.out.println("结束 s "+endMili);
	System.out.println("总耗时为："+(endMili-startMili)+"毫秒");        
        
        
        
       System.gc();
       long freeMem2 = Runtime.getRuntime().freeMemory();
       long totalMem2 = Runtime.getRuntime().totalMemory();
       long maxMem2 = Runtime.getRuntime().maxMemory();
       
       System.out.println((freeMem1-freeMem2)/1024/1024); 
       System.out.println((totalMem2-totalMem1)/1024/1024); 
       System.out.println(maxMem2/1024/1024);        
        
        
        System.out.println(map.queryName("洪山"));
        /*
        Set set = map.mapProvinceCity.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,Map> body = (Entry<String,Map>)it.next();
            System.out.println(body.getKey());
            Set _set = body.getValue().entrySet();
            Iterator _it = _set.iterator();
            while(_it.hasNext()){
                Entry<String,String> _body = (Entry<String,String>)_it.next();
            }
        }
        */ 
    }
}
