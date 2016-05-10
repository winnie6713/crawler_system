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
public class DistrictDB {
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
    
    public void InsertProvince(String code,String province){
        String sql = "Insert into districts (code,province) values ('"+code +"','"+province+"')";
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
     }
    
    public void InsertDistritc(String code,String province,String city,String county,String townroad){
        String sql = "Insert into districts (code,province,city,county,townroad) values ('"+code +"','"+province+"','"+city+"','"+county+"','"+townroad+"')";
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
     }    
    
    public void UpdateDistrict(int id,String code,String province,String city,String county,String townroad){
        String sql = "Update districts set code ='"+  code +"',province='"+ province+"',city='"+city+"',county='"+county+"',townroad='"+townroad+"' where id = " + id;
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
    }
    
    public void UpdateProvince(int id,String code,String province){
        String sql = "Update districts set code ='"+  code +"',province='"+ province+"' where id = " + id;
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
    }   
    
    
    public void DeleteDistrict(int districtId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from districts where featureId = " + districtId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }
    
    public void SelectProvince(List<DistrictInfo> rs){
        String sql = "select id,code,province,city from districts where code like '%0000000000' order by code ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                DistrictInfo info = new DistrictInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.code = dbresult.getString("code");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }  
    
    public void SelectDistrictByProvince(String province,List<DistrictInfo> rs){
        String sql = "select id,code,province,city,county,townroad from districts where province='"+province+"' order by code ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                DistrictInfo info = new DistrictInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.townroad = dbresult.getString("townroad");
                info.code = dbresult.getString("code");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }
    
    public void SelectProvinceCityCounty(List<DistrictInfo> rs){
        String sql = "SELECT distinct province,city,county FROM localserver.districts ";
        sql = sql + " where city <>'' and county<>'' ";
        sql = sql + " order by province,city,county ";
         try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                DistrictInfo info = new DistrictInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
//                info.townroad = dbresult.getString("townroad");
//                info.code = dbresult.getString("code");
//                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
}
