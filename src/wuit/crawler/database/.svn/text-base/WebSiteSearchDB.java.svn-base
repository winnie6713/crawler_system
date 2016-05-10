/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.database;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import static net.sourceforge.sizeof.test.TestSizeOf.print;

/**
 *
 * @author lxl
 */
public class WebSiteSearchDB {
    public String dbHost = "localhost"; 
    public String dbPort = "3306";
    public String dbName = "localserver"; 
    public String dbuserName = "admin"; 
    public String dbpsw = "aq12ws";

    public Connection dbconn;
    public Statement dbstate;
    public ResultSet dbresult;
    
    
    public boolean dbConnection(){
        String driverName = "com.mysql.jdbc.Driver";
        String enCoding = "?useUnicode=true&characterEncoding=utf8";
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + enCoding; 
        try{
            Class.forName(driverName).newInstance(); 
            dbconn = DriverManager.getConnection(url, dbuserName, dbpsw); 
            dbstate = (Statement) dbconn.createStatement(); 
        }catch(Exception e){ 
            print("Exception: " + e.getMessage());//
        }
        if (dbconn != null) 
            return true; 
        else
            return false; 
    }    
    
    public void InsertWebSearch(String name,int level, String keywords){
        /*
        String sql = "Insert into keywords0 (name,level,keywords) values ('"+name +"',"+ level+",'" + keywords +"')";
        sql = sql.replace("\\", "&&&");
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        */
     }    

    public void UpdateWebSearch(String keywords,int level,String name,int id){
        /*
        String sql = "Update localsearchinfo set keywords ='"+  keywords +"',level="+level+", name='"+ name +"' where id = " + id;
        sql = sql.replace("\\", "&&&");
        
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        */
    }      

    public void DeleteWebSearch(int keywords0Id){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from localsearchinfo where Id = " + keywords0Id;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }     

    public void SelectWebSearch(List<WebSitSearchInfo> rs){
        String sql = "select * from localsearchinfo order by province,city,county ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                WebSitSearchInfo info = new WebSitSearchInfo();                
                info.id = dbresult.getInt("id")+"";
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");;
                info.township = dbresult.getString("township");;
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadno");
                info.building = dbresult.getString("building");
                info.phone = dbresult.getString("phone");
                info.phonecode = dbresult.getString("phonecode");
                info.postcode = dbresult.getString("postcode");
                info.object = dbresult.getString("object");
                info.method = dbresult.getString("method");
                info.params = dbresult.getString("param");
                 
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    } 

    
    public void SelectLocalInfosByProvince(List<WebSitSearchInfo> RS, String province){
        String sql = "select * "+
                " from localsearchinfo where province ='"+province +"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                WebSitSearchInfo info = new WebSitSearchInfo();                
                info.id = dbresult.getInt("id")+"";
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");;
                info.township = dbresult.getString("township");;
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadno");
                info.building = dbresult.getString("building");
                info.phone = dbresult.getString("phone");
                info.phonecode = dbresult.getString("phonecode");
                info.postcode = dbresult.getString("postcode");
                info.object = dbresult.getString("object");
                info.method = dbresult.getString("method");
                info.params = dbresult.getString("param");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   

    public void SelectLocalInfosByProvinceCity(List<WebSitSearchInfo> RS, String province,String city){
        String sql = "select * "+
                " from localsearchinfo where province ='"+province +"' and city='"+city+"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                WebSitSearchInfo info = new WebSitSearchInfo();                
                info.id = dbresult.getInt("id")+"";
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");;
                info.township = dbresult.getString("township");;
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadno");
                info.building = dbresult.getString("building");
                info.phone = dbresult.getString("phone");
                info.phonecode = dbresult.getString("phonecode");
                info.postcode = dbresult.getString("postcode");
                info.object = dbresult.getString("object");
                info.method = dbresult.getString("method");
                info.params = dbresult.getString("param");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   
    
    public void SelectLocalInfosByProvinceCityCounty(List<WebSitSearchInfo> RS, String province,String city,String county){
        String sql = "select * "+
                " from localsearchinfo where province ='"+province +"' and city='"+city+"' and county='"+ county+"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                WebSitSearchInfo info = new WebSitSearchInfo();                
                info.id = dbresult.getInt("id")+"";
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");;
                info.township = dbresult.getString("township");;
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadno");
                info.building = dbresult.getString("building");
                info.phone = dbresult.getString("phone");
                info.phonecode = dbresult.getString("phonecode");
                info.postcode = dbresult.getString("postcode");
                info.object = dbresult.getString("object");
                info.method = dbresult.getString("method");
                info.params = dbresult.getString("param");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   
     
    
}
