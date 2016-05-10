/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wuit.common.crawler.db.DbConnect;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class ManagerDictionary {
   private MapNameAddress mapName = null;
   private MapPhoneAddress mapPhone = null;
   
   private DbConnect connect = null;
   
   public ManagerDictionary(){
      mapName = new MapNameAddress();
      mapName.InitializeChina();
      mapPhone = new MapPhoneAddress();
//      connect = new  DbConnect();
//      connect.Connection();
   }
   
   public Map<String,Map<String,String>> getProvinceCityName(){
      return mapName.mapProvinceCity;
   }
   
   
   /**
    * mode:
    * [DSComposite,
    * {province,city, , ,county,district,post,phone},
    * {district,postcode,province|city|county}]
    * path_src:源数据文件目录
    * path_dist：目标文件目录
    * 
    * 目标格式：
    * province,city,county,district,phone,post
    */
   
   /*
   public void collectAddress(int mode,JLabel status,String path_src,String file_src,String encode_src,String path_dist,String file_dist){
      AddressCollector db = new AddressCollector();
      switch(mode){
         case 11:
            //从含有composite文本数据中收集地址信息
            db.collectAddressByCompositeFiles(1,status,path_src,path_dist);
            break;
         case 12:
            //从含有composite文本数据中收集地址信息
            db.collectAddressByCompositeFiles(2,status,path_src,path_dist);
            break;            
         case 2:
            //province,city, , ,county,district,post,phone
            db.collectPhoneAddressFiles(path_src, path_dist, file_dist, encode_src);
            break;
         case 3: 
            //从province|city|county中分离出province,city,county
            db.collectPostAddressFiles(path_src, path_dist, file_dist, encode_src);
            break;
         case 41:
            db.classfyAddress(1, path_src, path_dist, encode_src);
            break;
         case 42:
            db.classfyAddress(2, path_src, path_dist, encode_src);
            break;
         case 43:
            db.classfyAddress(3, path_src, path_dist, encode_src);
            break;
         case 51:
            //上海市,辖区,卢湾区,嵩山路,021, ;添加到postcode数据库
            AddressInfoToMySQL(path_src);
            break;
      }
   }

   
   
   //上海市,辖区,卢湾区,嵩山路,021, ;添加到postcode数据库
   private void AddressInfoToMySQL(String path){
      try{
         List<String> lstFiles = new ArrayList<String>();
         List<String> dirs = new ArrayList<String>();
         FileIO.getFilesAndSubDirFiles(path, lstFiles, dirs);
         for (int i=0;i<lstFiles.size();i++){
            String pathFile = lstFiles.get(i);

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
            String c = br.readLine();
            while(c != null){
               String[] arrVal =  c.split(",");
               if(arrVal.length!=6){
                       c =br.readLine();
                       continue;
               }
               String province = arrVal[0];
               String city = arrVal[1];
               String county = arrVal[2];
               String district = arrVal[3];				
               String phone = arrVal[4];
               String post = arrVal[5];

               int index = city.indexOf(province);
               if (index>=0)
                  city = city.substring(index+province.length(),city.length());
               index=county.indexOf(city);
               if (index>=0)
                  county = county.substring(index+city.length(),county.length());

               boolean insertResult = false; 
               String sql = "insert into postcode (province,city,county,district,postcode) values" + "(" ;
               sql = sql + "'"+ province +"','" + city +"','" + county + "','" + district +"','"+ post + "')";
               try 
               { 
                  if(connect == null){
                     connect = new  DbConnect();
                     connect.Connection();
                  }
                  if (connect.dbstate == null)
                     connect.dbstate = (Statement) connect.dbconn.createStatement();
                  connect.dbstate.executeUpdate(sql); 
                  insertResult = true; 
               }catch(Exception e){ 
                  System.out.println("Sql = " + sql); 
                  System.out.println("Exception: " + e.getMessage()); 
                  e.printStackTrace();
               } 
               c =br.readLine();
             }
             br.close();
         }
       }catch(Exception e){
         e.printStackTrace();
       }
   }
   
   /*
   public MapNameAddress getMapDictionary(){         
      return mapName;
   }
   */
   /**
    * 
    * @param name:省、市、县、区
    * @return 
    */
   public List<String> queryAddressName(String name){
      return mapName.queryName(name);
   }
   
   public List<String> queryPhone(String phonecode){
      return mapPhone.queryName(phonecode);
   }
   
   /*
   public List<String> queryPost(String postcode){
      return map.queryAddressByPost(postcode);
   }
   */
   /**
    * 从数据库中查询
    * @param name 路，街道，乡镇等名称
    */
   public List<String> queryName(String name){
      List<String> list = new ArrayList<String>();	
      try{
         if(connect == null){
            connect = new  DbConnect();
            connect.Connection();
         }         
         if (connect.dbstate == null)               
            connect.dbstate = (Statement) connect.dbconn.createStatement(); 
         String sql = "select distinct province,city,county,postcode,district from postcode where district like '%"+ name + "' order by province,city"; 
         System.out.println("sql = " + sql); 
         try{ 
            connect.dbresult = connect.dbstate.executeQuery(sql); 
         }catch(Exception err){ 
            System.out.println("Sql = " + sql); 
            System.out.println("Exception: " + err.getMessage()); 
         } 
         while(connect.dbresult.next()){ 
            Map selResult = new HashMap();
            String val = connect.dbresult.getString("province");
            val = val +","+ connect.dbresult.getString("city");
            val = val +","+ connect.dbresult.getString("county");
            val = val +","+ connect.dbresult.getString("district");
//            val = val +","+ dbresult.getString("phone");
           val = val +","+ connect.dbresult.getString("postcode");
            list.add(val); 
         } 
      }catch(Exception e){ 
         System.out.println("Exception: " + e.getMessage()); 
      } 
      return list;   
   }
  
   public List<String> queryByCountyDistrict(String county,String district){
      List<String> list = new ArrayList<String>();	
      try{ 
         if (connect.dbstate == null)               
            connect.dbstate = (Statement) connect.dbconn.createStatement(); 
         String sql = "select distinct province,city,county,postcode,district from postcode where county like '%"+ county + "' and " + "district like '%"+ district + "'"; 
         System.out.println("sql = " + sql); 
         try{ 
            connect.dbresult = connect.dbstate.executeQuery(sql); 
         }catch(Exception err){ 
            System.out.println("Sql = " + sql); 
            System.out.println("Exception: " + err.getMessage()); 
         } 
         while(connect.dbresult.next()){ 
            Map selResult = new HashMap();
            String val = connect.dbresult.getString("province");
            val = val +","+ connect.dbresult.getString("city");
            val = val +","+ connect.dbresult.getString("county");
            val = val +","+ connect.dbresult.getString("district");
//            val = val +","+ dbresult.getString("phone");
           val = val +","+ connect.dbresult.getString("postcode");
            list.add(val); 
         } 
      }catch(Exception e){ 
         System.out.println("Exception: " + e.getMessage()); 
      } 
      return list;   
   }
   
   /*
   public Map<String,DSPostAddress> getProvince(){
      return map.mapDSTNames;    
   }
   */
   
   public static void main(String [] args){
      ManagerDictionary mdb = new ManagerDictionary();
//      List<String> list = mdb.queryName("勤丰小区");
//      for (int i=0;i<list.size();i++){
//         System.out.println(list.get(i));
//      }
//      mdb.collectAddress(1, "D:\\product\\aibang\\pagecontent\\", "", "UTF-8", "D:\\product\\aibang\\newAddress\\", "newAddress");
//      mdb.AddressInfoToMySQL("D:\\product\\aibang\\newAddress\\");
   }
}
