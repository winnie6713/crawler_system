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
import wuit.common.crawler.composite.DSComposite;

/**
 *
 * @author lxl
 */
public class LocalInfoDB {
    
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
        
        
//       public boolean InsertLocalInfo(String name,String label,String address,String province,String city,String county,String road,String roadNo,
//                String township,String village,String building,String floor,String room,String postcode,String phonecode,String phone,
//                String date,String url,String flag){
       public synchronized boolean InsertLocalInfo(DSComposite info){
        boolean insertResult = false; 
        String sql = "Insert into localserviceinfo ";
        sql = sql + "(name,label,address,";
        sql = sql + "province,city,county,road,roadNo,";
        sql = sql + "township,village,building,floor,room,";
        sql = sql + "postcode,phonecode,phone,date,url,flag,statfeature,statparse,statrs,statRsConflict) values ('";
        sql = sql + info.name+"','"+info.label+"','"+ info.local.address+"','";
        sql = sql + info.local.province+"','"+info.local.city+"','"+info.local.county+"','"+ info.local.Road+"','"+info.local.RoadNo + "','" ;
        sql = sql + info.local.township+"','" + info.local.village+"','";
        sql = sql + info.local.building + "','" + info.local.floor +"','"+info.local.room +"','";
        sql = sql + info.postcode +"','"+info.phone_code +"','"+info.phone +"','";
        sql = sql + info.collection.date +"','" + info.collection.url + "','" + info.local.flag +"','";
        sql = sql + info.stat.pageFeature +"','" + info.stat.parse + "','" + info.stat.Result + "','" + info.stat.RsConflict +"')";
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            System.out.println(sql);
            dbstate.executeUpdate(sql);
            insertResult = true;
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        if (insertResult)
            return true;
        else
            return false;            
        } 
       
    public void SelectLocalInfosByProvince(List<LocalInfo> RS, String province){
        String sql = "select id,label,name,postcode,phonecode,phone,address,province,city,county,township,village,road,roadNo,building,floor,room,date,url,flag "+
                " from localserviceinfo where province ='"+province +"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();
                info.id = dbresult.getString("id");
                info.name = dbresult.getString("name");
                info.label = dbresult.getString("label");
                info.address = dbresult.getString("address");
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.township = dbresult.getString("township");
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadNo");
                info.building = dbresult.getString("building");
                info.floor = dbresult.getString("floor");
                info.room = dbresult.getString("room");
                info.postcode = dbresult.getString("postcode");
                info.phonecode = dbresult.getString("phonecode");
                info.phone = dbresult.getString("phone");
                info.date = dbresult.getString("date");
                info.srcFrom = dbresult.getString("url");
                info.flag = dbresult.getString("flag");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   

    public void SelectLocalInfosByProvinceCity(List<LocalInfo> RS, String province,String city){
        String sql = "select id,label,name,postcode,phonecode,phone,address,province,city,county,township,village,road,roadNo,building,floor,room,date,url,flag "+
                " from localserviceinfo where province ='"+province +"' and city='"+city+"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();
                info.id = dbresult.getString("id");
                info.name = dbresult.getString("name");
                info.label = dbresult.getString("label");
                info.address = dbresult.getString("address");
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.township = dbresult.getString("township");
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadNo");
                info.building = dbresult.getString("building");
                info.floor = dbresult.getString("floor");
                info.room = dbresult.getString("room");
                info.postcode = dbresult.getString("postcode");
                info.phonecode = dbresult.getString("phonecode");
                info.phone = dbresult.getString("phone");
                info.date = dbresult.getString("date");
                info.srcFrom = dbresult.getString("url");
                info.flag = dbresult.getString("flag");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   
    
    public void SelectLocalInfosByProvinceCityCounty(List<LocalInfo> RS, String province,String city,String county){
        String sql = "select id,label,name,postcode,phonecode,phone,address,province,city,county,township,village,road,roadNo,building,floor,room,date,url,flag "+
                " from localserviceinfo where province ='"+province +"' and city='"+city+"' and county='"+ county+"' order by province,city,county,township,village,road";
        
        System.out.println(sql);
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();
                info.id = dbresult.getString("id");
                info.name = dbresult.getString("name");
                info.label = dbresult.getString("label");
                info.address = dbresult.getString("address");
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.township = dbresult.getString("township");
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadNo");
                info.building = dbresult.getString("building");
                info.floor = dbresult.getString("floor");
                info.room = dbresult.getString("room");
                info.postcode = dbresult.getString("postcode");
                info.phonecode = dbresult.getString("phonecode");
                info.phone = dbresult.getString("phone");
                info.date = dbresult.getString("date");
                info.srcFrom = dbresult.getString("url");
                info.flag = dbresult.getString("flag");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   
    
    public void SelectProvinceCityCounty(List<LocalInfo> rs){
        String sql = "select distinct province,city,county from localserviceinfo order by province,city,county ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
    
    public void SelectProvince(List<LocalInfo> rs){
        String sql = "select distinct province from localserviceinfo order by province";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();                
                info.province = dbresult.getString("province");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }    
    
    public void SelectProvinceCity(List<LocalInfo> rs){
        String sql = "select distinct province,city from localserviceinfo order by province,city ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();                
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }     

    public void SelectLocalInfosByLable(List<LocalInfo> RS, String label){
        String sql = "select id,label,name,postcode,phonecode,phone,address,province,city,county,township,village,road,roadNo,building,floor,room,date,url,flag "+
                " from localserviceinfo where label ='"+label +"' order by province,city,county,township,village,road";
        try{
            dbstate = (Statement) dbconn.createStatement();
            try{
                dbresult = dbstate.executeQuery(sql);
            }catch(Exception err){
                print("Exception: " + err.getMessage());
            }
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();
                info.id = dbresult.getString("id");
                info.name = dbresult.getString("name");
                info.label = dbresult.getString("label");
                info.address = dbresult.getString("address");
                info.province = dbresult.getString("province");
                info.city = dbresult.getString("city");
                info.county = dbresult.getString("county");
                info.township = dbresult.getString("township");
                info.village = dbresult.getString("village");
                info.road = dbresult.getString("road");
                info.roadNo = dbresult.getString("roadNo");
                info.building = dbresult.getString("building");
                info.floor = dbresult.getString("floor");
                info.room = dbresult.getString("room");
                info.postcode = dbresult.getString("postcode");
                info.phonecode = dbresult.getString("phonecode");
                info.phone = dbresult.getString("phone");
                info.date = dbresult.getString("date");
                info.srcFrom = dbresult.getString("url");
                info.flag = dbresult.getString("flag");
                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }   
    
    public void SelectLable(List<LocalInfo> rs){
        String sql = "select distinct Label from localserviceinfo order by Label ";
        try{
            if(dbconn == null)
                dbConnection();
            dbstate = (Statement) dbconn.createStatement();
            dbresult = dbstate.executeQuery(sql);
            while(dbresult.next()){
                LocalInfo info = new LocalInfo();                
                info.province = dbresult.getString("Label");
                rs.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }     
    
    public void queryStatByDate(List<DSComposite> RS, int feature,int parse,int rs,int rsConflict,String start,String end){
        String sql = "select id,label,name,postcode,phonecode,phone,address,province,city,county,township,village,road,roadNo,building,floor,room,date,url,flag "+
                " from localserviceinfo ";
        sql = sql + "where unix_timestamp(date) > unix_timestamp('"+ start+"') and unix_timestamp(date) < unix_timestamp('"+ end+"')";
        if(feature != -1)
            sql = sql + " and statfeature = " + feature ;
        if(parse != -1)
            sql = sql + " and statparse = " + parse;
        if(rs != -1){
            if(rs == 1){
                sql = sql + " and province <>'' and  city<>'' and county <> '' ";
            }
            if(rs == 2){
                sql = sql + " and (statrs = 1 or statrs =2) ";
            }
            if(rs == 3) sql = sql + " and statrs = 3 ";
        }
        if(rsConflict != -1)
            sql = sql + " and statrsconflict = " + rsConflict;  
        sql = sql + " order by province,city,county,township,village,road";
        
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
                DSComposite info = new DSComposite();
                info.id = dbresult.getString("id");
                info.name = dbresult.getString("name");
                info.label = dbresult.getString("label");
                info.local.address = dbresult.getString("address");
                info.local.province = dbresult.getString("province");
                info.local.city = dbresult.getString("city");
                info.local.county = dbresult.getString("county");
                info.local.township = dbresult.getString("township");
                info.local.village = dbresult.getString("village");
                info.local.Road = dbresult.getString("road");
                info.local.RoadNo = dbresult.getString("roadNo");
                info.local.building = dbresult.getString("building");
                info.local.floor = dbresult.getString("floor");
                info.local.room = dbresult.getString("room");
                info.postcode = dbresult.getString("postcode");
                info.phone_code = dbresult.getString("phonecode");
                info.phone = dbresult.getString("phone");
                info.collection.date = dbresult.getString("date");
                info.collection.url = dbresult.getString("url");
                info.local.flag = dbresult.getString("flag");
//                info.stat = 0;
//                String rs =  id + "," + name + "," + province + "," + city+ "," + county+ "," + township+ "," + village+ "," + road+ "," + roadNo +
//                        "," + building+ "," + floor+ "," + room+ "," + postcode+ "," + phonecode+ "," + phone+ "," + date+ "," + srcFrom+ "," + flag;
                RS.add(info);
            }
        }catch(Exception e){
            print("Exception: " + e.getMessage());
        }
    }     
    
    
    public void DeleteLocalInfo(int localInfoId){
        try{
            if(dbconn == null)
                dbConnection();
            if (dbstate == null)
                dbstate = (Statement) dbconn.createStatement();
            String sql = "delete from localserviceinfo where Id = " + localInfoId;
            dbstate.executeUpdate(sql);
        }catch(Exception e){
            print("Exception: " + e.getMessage());
            e.printStackTrace();
        }         
    }    
    
    //id,name,label,address,province,city,county,road,roadNo,township,village,building,floor,room,postcode,phonecode,phone,date,url,flag
    public void UpdateLocalInfo(LocalInfo info){
        String sql = "Update localserviceinfo ";
        sql = sql + " set name ='"+  info.name +"',label='"+ info.label ;
        sql = sql +"',province='" + info.province + "',city='"+info.city+"',county='"+info.county ;
        sql = sql +"',road='"+info.road+"',roadNo='"+info.roadNo +"',township='"+info.township+"',village='"+info.village;
        sql = sql +"',building='"+info.building +"',floor='"+info.floor +"',room='"+info.room ;
        sql = sql +"',postcode='"+info.postcode +"',phonecode='"+info.phonecode+"',phone='"+info.phone ;
        sql = sql +"',date='"+info.date +"',url='"+info.srcFrom +"',flag='"+info.flag + "' where id = " + info.id;
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
}
       