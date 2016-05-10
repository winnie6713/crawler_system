/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lxl
 */
public class MapPhoneAddress {
   String path_base = "D:\\product\\lib\\Address\\phone\\tempPhone\\";
   Map<String,String> mapPhone = new HashMap<String,String>();
   Map<String,String> mapName = new HashMap<String,String>();
   
   public MapPhoneAddress(){
      Iitialize();
   }
   
   private void Iitialize(){
      String path = path_base ;
      File filePath = new File(path);
      String[] files = null;
      if(filePath.isDirectory()){
         files = filePath.list();
      }
      try{
         if (files == null)
            return;
         for (int i=0;i<files.length;i++){
            String pathFile = path +files[i];
            
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
            String c = br.readLine();
            while(c != null){
               BaseAddress base = new BaseAddress(c);
               String phone = base.phone;
               
               base.city = base.city.replace(base.province, "");
               base.county = "";
               base.district = "";
               base.post = "";
               base.phone = "";
               String val = base.ToString(base);
               
               if(!mapName.containsKey(val) && !phone.equals(""))
                  mapName.put(val, phone);
               if(!mapPhone.containsKey(phone)&& !phone.equals("")){
                  mapPhone.put(phone, val);
               }
               c =br.readLine();
            }
            br.close();            
         }
      }catch(Exception e){
         System.out.println(e.getMessage());
      }        
   }

   public String queryPhone(BaseAddress base){
      String val = base.ToString(base);
      val = val.replace(base.county, "");
      val = val.replace(base.district, "");
      val = val.replace(base.post, ""); 
      val = val.replace(base.phone, ""); 
      return mapName.get(val) ;
   }
   
   public List<String> queryName(String phone){
      List<String> list = new ArrayList<String>();
      String _vals =  mapPhone.get(phone);
      if(_vals != null && !_vals.equals("")){
         String[] arrVals =  _vals.split(";");
         for (int i=0;i<arrVals.length;i++)
            list.add(arrVals[i]);
      }
      return list;
   }
   public static void main(String[] args){
      MapPhoneAddress phone = new MapPhoneAddress();
      BaseAddress base = new BaseAddress("广东省,湛江市,赤坎区,教育路,,524031");
      
      System.out.println(phone.queryPhone(base));
      
      System.out.println(phone.queryName("021"));
   }
}
