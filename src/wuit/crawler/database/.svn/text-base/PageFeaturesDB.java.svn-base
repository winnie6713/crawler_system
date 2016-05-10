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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import static net.sourceforge.sizeof.test.TestSizeOf.print;

/**
 *
 * @author lxl
 */
public class PageFeaturesDB {
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

    public void InsertFeature(PageFeatureInfo feature){
        String sql = "Insert into pagefeatures (name,mode,feature) values ('"+feature.name +"',"+feature.mode+",'"+feature.featrue+"')";
        sql = sql.replace("\\","&&&");
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

    public void InsertPageClear(String name, String xml){
        String sql = "Insert into pageclear (name,xml) values ('"+name +"','" + xml +"')";
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

    public void InsertPageFilters(String name, String xml){
        String sql = "Insert into pageFilters (name,xml) values ('"+name +"','" + xml +"')";
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
    public void InsertPageItems(String name, String xml){
        String sql = "Insert into pageItems (name,xml) values ('"+name +"','" + xml +"')";
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

     
    
    /*
    public void InsertFields(PageFeatureField field){
        String sql = "Insert into pagefeatureslib (fieldname,fieldvalue,featureid) values ('"+field.name +"','"+field.vlaue+"',"+ field.featureId +")";
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
    */
    
    public void UpdateFeature(PageFeatureInfo feature){
        //feature.featrue = feature.featrue.replaceAll("\\d", "\\\\");
        String sql = "Update pagefeatures set name ='"+  feature.name +"',mode="+ feature.mode+",feature='"+feature.featrue+"' where id = " + feature.id;
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
    
    
    public void UpdatePageClear(String xmlClear,String name,int id){
        String sql = "Update pageclear set xml ='"+  xmlClear +"',name='"+ name +"' where id = " + id;
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

    public void UpdatePageFilters(String xmlFilters,String name,int id){
        String sql = "Update pageFilters set xml ='"+  xmlFilters +"',name='"+ name +"' where id = " + id;
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
    
    public void UpdatePageItems(String xmlItem,String name,int id){
        String sql = "Update pageItems set xml ='"+  xmlItem +"',name='"+ name +"' where id = " + id;
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

  
    
    public void DeleteFeature(PageFeatureInfo feature){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from pagefeatureslib where featureId = " + feature.id;
            dbstate.executeUpdate(sql);
            sql = "delete from pagefeatures where id = " + feature.id;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }    

    public void DeletePageClear(int pageClearId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from pageClear where Id = " + pageClearId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }     

    public void DeletePageFilters(int pageFilterId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from pageFilters where Id = " + pageFilterId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }    
    
    public void DeletePageItems(int pageItemId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from pageClear where Id = " + pageItemId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }     
    
    
    /*
    public void DeleteField(PageFeatureField field){
        String sql = "delete from pagefeatureslib where id = " + field.id;
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
    */
    
    public void SelectPageFeatures(List<PageFeatureInfo> RS){
        String sql = "select id,name,mode,feature from pagefeatures order by name ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PageFeatureInfo info = new PageFeatureInfo();                
                info.name = dbresult.getString("name");
                info.featrue = dbresult.getString("feature");
                info.featrue = info.featrue.replace("&&&", "\\");
                info.id = dbresult.getInt("id");
                info.mode = dbresult.getInt("mode");
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
    
    public void SelectPageClear(List<PageLocalInfo> rs){
        String sql = "select id,name,xml from pageclear order by name ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PageLocalInfo info = new PageLocalInfo();                
                info.name = dbresult.getString("name");
                info.content = dbresult.getString("xml");
                info.content = info.content.replace("&&&", "\\");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    

    public void SelectPageItems(List<PageLocalInfo> rs){
        String sql = "select id,name,xml from pageitems order by name ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PageLocalInfo info = new PageLocalInfo();                
                info.name = dbresult.getString("name");
                info.content = dbresult.getString("xml");
                info.content = info.content.replace("&&&", "\\");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
    
    public void SelectPageFilters(List<PageLocalInfo> rs){
        String sql = "select id,name,xml from pageFilters order by name ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                PageLocalInfo info = new PageLocalInfo();                
                info.name = dbresult.getString("name");
                info.content = dbresult.getString("xml");
                info.content = info.content.replace("&&&", "\\");
                info.id = dbresult.getInt("id");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }     
    
    public static void main(String[] args){
        List<PageFeatureInfo> RS = new ArrayList<PageFeatureInfo>();
        PageFeaturesDB db = new PageFeaturesDB();
        db.SelectPageFeatures(RS);
        for(int i=0;i<RS.size();i++){
            for(int j=0; j<RS.get(i).fields.size();j++){
                System.out.println(RS.get(i).fields.get(j).featureId + ":" + RS.get(i).fields.get(j).name + ":" + RS.get(i).fields.get(j).vlaue);
            }
        }
    }
}
