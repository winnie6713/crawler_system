package wuit.common.crawler.db;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Statement;
import wuit.common.doc.xml.DBConnection;

public class DbConnect {
	public BufferedReader br = null;
	////////////////////////////
	public String dbHost = "localhost"; 
	public String dbPort = "3306";
	public String dbName = "net_search"; 
	public String dbuserName = "root"; 
	public String dbpsw = "zaq12wsx"; 
/////////////////////////////////////////
	public Connection dbconn;
	public Statement dbstate;
	public ResultSet dbresult; 

   private DBConnection connInfo ;
   
	public DbConnect(){
            dbconn = null;
            dbstate = null; 
            dbresult = null; 
            connInfo = new DBConnection();
            connInfo.getConnection();
            
            dbHost = connInfo.host;
            dbPort = connInfo.port;
            dbName = connInfo.dbName;
            dbuserName = connInfo.user;
            dbpsw = connInfo.pwd;
	} 

	///////////////////////////////////////// 
	public void print(String str)
	{ 
	System.out.println(str); 
	} 
	
	/** 
	*
	* @param host 
	* @param port 
	* @param dbaName 
	* @param usName 
	* @param psw 
	* @return bool 
	*/ 
	public boolean dbConnection(String host, String port, String dbaName, String usName, String psw) 
	{ 
		String driverName = "com.mysql.jdbc.Driver";
		String enCoding = "?useUnicode=true&characterEncoding=utf8";
		String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + enCoding; 
		//jdbc:mysql://localhost:3306/database?useUnicode=true&amp;characterEncoding=UTF-8
		//jdbc:mysql://localhost:3306/database?useUnicode=true&amp;characterEncoding=UTF-8
		url = "jdbc:mysql://localhost:3306/net_search?useUnicode=true&characterEncoding=UTF-8";
		try 
		{ 
			Class.forName(driverName).newInstance(); 
			dbconn = DriverManager.getConnection(url, dbuserName, dbpsw); 
			//getConnection(url, userName, Psw)
			//return a connection to the URL 
			dbstate = (Statement) dbconn.createStatement(); 
		}catch(Exception e){ 
			print("url = " + url); //
			print("userName = " + dbuserName); 
			print("Psw" + dbpsw); 
			print("Exception: " + e.getMessage());//
		} 
		if (dbconn != null)//dbconn != null 
			return true; 
		else 
			return false; 
	} 
   
   public boolean Connection(){
      String driverName = "com.mysql.jdbc.Driver";
     	String url = "jdbc:mysql://localhost:3306/net_search?useUnicode=true&characterEncoding=UTF-8";
		try 
		{ 
			Class.forName(driverName).newInstance(); 
			dbconn = DriverManager.getConnection(url, dbuserName, dbpsw); 
			//getConnection(url, userName, Psw)
			//return a connection to the URL 
			dbstate = (Statement) dbconn.createStatement(); 
		}catch(Exception e){ 
			print("url = " + url); //
			print("userName = " + dbuserName); 
			print("Psw" + dbpsw); 
			print("Exception: " + e.getMessage());//
		}  
      
      if (dbconn != null)//dbconn != null 
			return true; 
		else 
			return false; 
   }
   
	/** 
	 * 查询 
	 * @param tableName  
	 * @param fieles  
	 * @param selCondition  
	 * @return  
	 */ 
	public ArrayList dbSelect(String tableName, ArrayList fields, String selCondition){
            ArrayList mapInList = new ArrayList();
            String selFields = ""; 
            for (int i = 0; i<fields.size(); ++i) 
                selFields += fields.get(i) + ", ";
            String selFieldsTem = selFields.substring(0, selFields.length() - 2);
            try{
                dbstate = (Statement) dbconn.createStatement();
                String sql = "select " + selFieldsTem + " from " + tableName + selCondition;
                print("sql = " + sql);
                try{
                    dbresult = dbstate.executeQuery(sql);
                }catch(Exception err){
                    print("Sql = " + sql);
                    print("Exception: " + err.getMessage());
                }
                while(dbresult.next()){
                    Map selResult = new HashMap();
                    for(int i=0;i<fields.size();i++){
                        selResult.put(fields.get(i),dbresult.getString(fields.get(i).toString()));
                    }
                    mapInList.add(selResult);
                }
            }catch(Exception e){
                print("Exception: " + e.getMessage());
            }
            return mapInList; 
	}

	

	/** 
	 * 删除一条记录
	 * @param tableName 
	 * @param condition 
	 * @return bool
	 */ 
	public boolean dbDelete(String tableName, String condition) 
	{
		boolean delResult = false; 
		String sql = "delete from " + tableName + " " + condition; 
		try{ 
			dbstate.executeUpdate(sql);	//return int // int delRe = ?? 
			delResult = true; 
		}catch(Exception e){ 
			print ("sql = " + sql); 
			print ("Exception: " + e.getMessage()); 
		} 
		if (delResult) 
			return true; 
		else 
			return false; 
	}
//end dbDelete(锟斤拷) 

	
	/** 
	 *  
	 * @param tabName 
	 * @param reCount 
	 * @return bool 
	 */ 
	public boolean dbUpdate(String tabName, HashMap reCount, String upCondition) 
	{ 
		boolean updateResult = false; 
		String Values = ""; 
		Iterator keyValues = reCount.entrySet().iterator(); 
		for(int i = 0; i<reCount.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			Values += key + "=" + "'" + value + "'" + ", "; 
		} 
		String updateValues = Values.substring(0, Values.length() - 2); 
		String sql = "update " + tabName + " set " + updateValues + " " + upCondition; 
		try 
		{ 
			dbstate.executeUpdate(sql); 
			updateResult = true; 
		}catch(Exception err){ 
			print("sql = " + sql); 
			print("Exception: " + err.getMessage()); 
		} 
		if (updateResult) 
			return true; 
		else 
			return false; 
	}
//end dbUpdate(锟斤拷) 
	 

	/** 
	 *  
	 * @param tabName 
	 * @param hm 
	 * @return bool 
	 */ 
	public boolean dbInsert(String tabName, HashMap values) 
	{ 
		String sql = ""; 
		String insertFields = "", temFields = ""; 
		String insertValues = "", temValues = ""; 
		boolean insertResult = false; 
		Iterator keyValues = values.entrySet().iterator(); 
		for(int i = 0; i<values.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			temFields += key + ", "; 
			temValues += "'" + value + "'" + ", "; 
		} 
		insertFields = temFields.substring(0, temFields.length() - 2); 
		insertValues = temValues.substring(0, temValues.length() - 2); 
		sql += "insert into " + tabName + " (" + insertFields + ") values" + "(" + insertValues + ")";
		try 
		{ 
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement();
			dbstate.executeUpdate(sql); 
			insertResult = true; 
		}catch(Exception e){ 
			print("Sql = " + sql); 
			print("Exception: " + e.getMessage()); 
			e.printStackTrace();
		} 
		if (insertResult) 
			return true; 
		else 
			return false; 
	}
	 
	/** 
	 * 
	 * @return bool 
	 */ 
	public boolean dbClose() 
	{ 
		boolean closeResult = false; 
		try 
		{ 
			dbconn.close(); 
			closeResult = true; 
		}catch(Exception e){ 
			print("Exception: " + e.getMessage()); 
		} 
		if (closeResult) 
			return true; 
		else 
			return false; 
	}//end dbClose() 
	
	///////////////////////
	public String match(String content,String filter){
		String val ="";
		try{		
			Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
			while (m.find()) {
			   val =m.group();
			   break;
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	
/////////////////////////////////////////
/*
 * 
	public static void main(String[] args) throws UnsupportedEncodingException 
	{ 
		DbConnect dc = new DbConnect();

		String dbHost = "localhost"; 
		String dbPort = "3306"; 
		String dbName = "net_search"; 
		String dbuserName = "root"; 
		String dbpsw = "zaq12wsx"; 
		 
		boolean con = dc.dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw); //
		if (con) 
		{ 
			ArrayList fields = new ArrayList(); 
			fields.add("privnce"); 
			fields.add("postcode2"); 
			Map lmap = new HashMap(); 
			 
//			String selCondition = " where message_type = 1 limit 2"; 
			String selCondition = "";// where message_type = 1 limit 2"; 
			ArrayList str = dc.dbSelect("postcode", fields, selCondition); //锟斤拷>>>选锟斤拷锟铰�
			if (str.size() != 0){ 
				dc.print("select OK!"); 
				dc.print("strings size = " + str.size()); 
				for(int i = 0; i<str.size(); ++i){ 
					lmap = (HashMap)str.get(i); 
					dc.print("lmap = " + lmap); 
				} 
			} 
		} 
		else 
			dc.print("fail"); 
		 
		String tableName = "postcodew"; 
		String condition = "";//where message_type = 5"; 
		boolean del = dc.dbDelete(tableName, condition);//锟斤拷>>>删锟斤拷锟斤拷锟�
		if ( del) 
			dc.print("delete ok"); 
		else 
			dc.print("delete error!"); 
 
 
 
		HashMap<String, String> m = new HashMap<String, String>(); 
		m.put("message_content", "ookkk"); 
		m.put("message_number", "9"); 
		String upCondition = "where message_type = 4"; 
		boolean ur = dc.dbUpdate("feast", m, upCondition); //锟斤拷>>>锟斤拷锟斤拷锟斤拷锟�
		if ( ur) 
			dc.print("update OK"); 
		else 
			dc.print("update error!"); 
		 
		HashMap<String, String> mm = new HashMap<String, String>(); 
		mm.put("privnce", "湖北省"); 
		mm.put("city", "武汉");
		mm.put("county", "洪山区"); 
		mm.put("distric", "街道口"); 
		mm.put("postcode1", "430000");
		mm.put("postcode2", "430072");
		mm.put("phonecode1", "027"); 
		mm.put("phonecode2", new String("027".getBytes("utf8"),"utf8"));
		
		
		boolean in = dc.dbInsert("postcode", mm); //锟斤拷>>>锟斤拷锟斤拷 
		if (in) 
			dc.print("insert OK"); 
		else 
			dc.print("insert error!"); 
		 
		boolean close = dc.dbClose();//锟斤拷锟紺>锟较匡拷锟斤拷菘锟�
		if (close) 
			dc.print("close OK"); 
		else 
			dc.print("close fail"); 
	}//end main() 
*/	 
} 

