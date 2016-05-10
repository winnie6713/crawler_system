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
public class PhonePostCodeDB {
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
    
    public void InsertPhonePost(String code,String province,String city,String county,String post,String phone){
        String sql = "Insert into district_code_phone_post (code,province,city,county,post,phone) values ('"+code +"','"+province+"','"+city+"','"+county+"','"+post+"','"+phone+"')";
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
    
    public void UpdatePhonePost(int id,String code,String province,String city,String county,String post,String phone){
        String sql = "Update district_code_phone_post set code ='"+  code +"',province='"+ province+"',city='"+city+"',county='"+county+"',post='"+post+"',phone='"+phone+"' where id = " + id;
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
    
    public void DeletePhonePost(int districtId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from district_code_phone_post where Id = " + districtId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }
    
    public void SelectProvince(List<PhonePostCodeInfo> rs){
        String sql = "select distinct province from district_code_phone_post order by province ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PhonePostCodeInfo info = new PhonePostCodeInfo();                
                info.province = dbresult.getString("province");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }  
    
    public void SelectPhonePostByProvince(String province,List<PhonePostCodeInfo> rs){
        String sql = "select id,code,province,city,county,post,phone from district_code_phone_post where province='"+province+"' order by province,city ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PhonePostCodeInfo info = new PhonePostCodeInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.phone = dbresult.getString("phone");
                info.post = dbresult.getString("post");
                info.code = dbresult.getString("code");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
    
}
