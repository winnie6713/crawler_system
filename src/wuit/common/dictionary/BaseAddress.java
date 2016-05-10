/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

/**
 *
 * @author lxl
 */
public class BaseAddress {
   public String key = "";
   public String province="";
   public String city="";
   public String county="";
   public String district="";
   public String number = "";
   public String phone ="";
   public String post ="";
   public int level = 0;
   
   
   public void StringToObject(String value){
      String[] vals = value.split(",");
      if(vals.length >= 6)
      {
         province = vals[0];
         city = vals[1];
         county = vals[2];
         district = vals[3];
         phone = vals[4];
         post = vals[5];         
      }
      if(vals.length == 7)
         level = Integer.parseInt(vals[6]);
      if(vals.length == 8){         
         level = Integer.parseInt(vals[6]);
         number = vals[7];
      }      
   }
   
   public BaseAddress(String value){
      if(value == null || value.equals(""))
         return;      
      String[] vals = value.split(",");
      if(vals.length >= 6){
         province = vals[0];
         city = vals[1];
         county = vals[2];
         district = vals[3];
         phone = vals[4];
         post = vals[5];         
      }
      if(vals.length == 7)
         level = Integer.parseInt(vals[6]);
      if(vals.length == 8){
         if(vals[6] != null && !vals[6].equals(""))
            level = Integer.parseInt(vals[6]);
         number = vals[7];
      }
   }   
   
   public static String ObjectToString(BaseAddress info){
      String val="";
      if (info != null)
         val = info.province +"," + info.city +"," + info.county + "," + info.district + "," + info.phone +"," + info.post+"," + info.level +"," + info.number;
      return val;      
   }
   
   
   public String ToString(BaseAddress info){
      String val = province +"," + city +"," + county + "," + district + "," + phone +"," + post +"," + level+","+ number;   
      return val;      
   }
   
   
   public void convertByLevel(){
      switch(level){
         case 1:city="";county="";break;
         case 2:county="";break;
      }
   }   
}
