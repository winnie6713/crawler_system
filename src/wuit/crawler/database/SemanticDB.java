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
public class SemanticDB {
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
    
    public void InsertKeywords0(String name,int level, String keywords){
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
     }    

    public void UpdateKeywords0(String keywords,int level,String name,int id){
        String sql = "Update keywords0 set keywords ='"+  keywords +"',level="+level+", name='"+ name +"' where id = " + id;
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
    }      

    public void DeleteKeywords0(int keywords0Id){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from keywords0 where Id = " + keywords0Id;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }     

    public void SelectKeywords0(List<PageLocalInfo> rs){
        String sql = "select id,name,level,keywords from keywords0 order by level ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PageLocalInfo info = new PageLocalInfo();                
                info.name = dbresult.getString("name");
                info.content = dbresult.getString("keywords");
                info.content = info.content.replace("&&&", "\\");
                info.id = dbresult.getInt("id");
                info.level = dbresult.getInt("level"); 
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
}
