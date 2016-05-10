package wuit.common.crawler.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Statement;

public class DbPostCode extends DbConnect{	
//	private Map<String,String> mapPhones = new HashMap<String,String>();
	
	public DbPostCode(){
		dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw);
	}
	
	public String getPrivnceCityByPhone(String phone){
		String val ="";
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
			String sql = "select distinct privnce,city,phonecode1,postcode1 from postcode where phonecode1 like '"+ phone +"%'"; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			}
			while(dbresult.next()){				
				val =  dbresult.getString("privnce");
				val = val + ";" + dbresult.getString("city");
				val = val + ";" + dbresult.getString("postcode1");
				System.out.println(val);
			}
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
		return val;
	}
	
	public String getPostCode(String privnce,String city,String distric){
		String val ="";
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
			String sql = "select distinct privnce,city,county,distric,postcode2 from postcode";
			sql = sql + " where privnce like '" + privnce +"%' and city like '"+city+"'  and distric like '"+distric+"'"; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			}
			while(dbresult.next()){				
				val =  dbresult.getString("privnce") + ";" + dbresult.getString("city");
				val = val  + ";" + dbresult.getString("county")+ ";" + dbresult.getString("distric")+ ";" + dbresult.getString("postcode2");
				System.out.println(val);
			}
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
		return val;		
	}
	
	
	public String getPostCode(String city,String distric){
		String val ="";
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
			String sql = "select distinct privnce,city,county,distric,postcode2 from postcode";
			sql = sql + " where city like '"+city+"'  and distric like '"+distric+"%'"; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			}
			while(dbresult.next()){				
				val =  dbresult.getString("privnce") + ";" + dbresult.getString("city");
				val = val  + ";" + dbresult.getString("county")+ ";" + dbresult.getString("distric")+ ";" + dbresult.getString("postcode2");
				System.out.println(val);
			}
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
		return val;		
	}
	public String getPostCodeByCounty(String county,String distric){
		String val ="";
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
			String sql = "select distinct privnce,city,county,distric,postcode2 from postcode";
			sql = sql + " where county like '"+county+"'  and distric like '"+distric+"%'"; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			}
			while(dbresult.next()){				
				val =  dbresult.getString("privnce") + ";" + dbresult.getString("city");
				val = val  + ";" + dbresult.getString("county")+ ";" + dbresult.getString("distric")+ ";" + dbresult.getString("postcode2");
				System.out.println(val);
			}
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
		return val;		
	}	
	
	
	
	public String getBySQL(){
		String val ="";
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
			String sql = "select distinct privnce,city,county,postcode1,phonecode1 from phonecode order by privnce,city,county";
//			sql = sql + " where privnce like '" + privnce +"%' and city like '"+city+"'  and distric like '"+distric+"'"; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			}
			while(dbresult.next()){				
				val =  dbresult.getString("privnce") + ";" + dbresult.getString("city");
				val = val  + ";" + dbresult.getString("county") ;
//				val = val  + ";" + dbresult.getString("distric") ;
				val = val  + ";" + dbresult.getString("postcode1");
				val = val  + ";" + dbresult.getString("phonecode1");
				System.out.println(val);
			}
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
		return val;		
	}

	public void addItem(PostInfo _info){
		HashMap<String, String> values = new HashMap<String, String>();
		
		if (_info.address.length()>50)
			values.put("district", _info.address.substring(0,49));
		else
			values.put("district", _info.address);
		values.put("city", _info.city);
		values.put("postcode1", _info.cityCode);
		values.put("phonecode1", _info.cityphone);
		values.put("postcode2", _info.code);
		values.put("county", _info.county);
		values.put("phonecode2", _info.phone);
		values.put("province", _info.province);
		
		dbInsert("phonecode", values);
	}
	/////////////////////////////////////////////
	//
	public void gatherRecord(String path){
		List<PostInfo> list = new ArrayList<PostInfo>();
		File filePath = new File(path);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}		
		try{
			for (int i=0;i<files.length;i++){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" +files[i]),"gb2312"));
				String c = br.readLine();
				while(c != null){
					String str =  new String(c.getBytes(),"utf-8");
					String[] r = str.split(";");
					PostInfo _info = new PostInfo();
					for(int j=0;j<r.length;j++){
						if(!match(r[j],"(?<=地址:).*").equals(""))
							_info.address = match(r[j],"(?<=地址:).*");
						if(!match(r[j],"(?<=城市:).*") .equals(""))
							_info.city = match(r[j],"(?<=城市:).*");
						if(!match(r[j],"(?<=城市邮编:).*").equals(""))
							_info.cityCode = match(r[j],"(?<=城市邮编:).*");
						if(!match(r[j],"(?<=城市区号:).*").equals(""))
							_info.cityphone = match(r[j],"(?<=城市区号:).*");
						if(!match(r[j],"(?<=邮编:).*").equals(""))
							_info.code = match(r[j],"(?<=邮编:).*");
						if(!match(r[j],"(?<=行政区:).*").equals(""))
							_info.county = match(r[j],"(?<=行政区:).*");
						if(!match(r[j],"(?<=区号:).*").equals(""))
							_info.phone = match(r[j],"(?<=区号:).*");
						if(!match(r[j],"(?<=省份:).*").equals(""))
							_info.province = match(r[j],"(?<=省份:).*");
					}
					
					int size = list.size();
					int j;
					for (j=0;j<size;j++){
						if (list.get(j).province.equals(_info.province) && list.get(j).city.equals(_info.city) && list.get(j).county.equals(_info.county))
							break;
					}
					
					if (j == size){
						_info.address = "";
						_info.code = "";
						_info.distric = "";
						_info.phone = "";
						
						list.add(_info);
						addItem(_info);
					}
					
	//				addItem(_info);
					c =br.readLine();
				}
				br.close();	
			}
		
			for (int i=0;i<list.size();i++){
				System.out.println(i + ": " + list.get(i).province +";"+  list.get(i).city +";"+  list.get(i).county +";"+  list.get(i).cityCode +";" +  list.get(i).cityphone);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void addItemYoubianku(PostInfo _info){
		HashMap<String, String> values = new HashMap<String, String>();
		
		if (_info.address.length()>50)
			values.put("district", _info.address.substring(0,49));
		else
			values.put("district", _info.address);
		values.put("city", _info.city);
		values.put("postcode", _info.code);
		values.put("county", _info.county);
		values.put("province", _info.province);
		
		dbInsert("postcode", values);
	}
	
	public void gatherYoubiankuRecord(String path){
		List<String> list = new ArrayList<String>();
		File filePath = new File(path);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}		
		try{
			for (int i=0;i<files.length;i++){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" +files[i]),"utf-8"));
				String c = br.readLine();
				while(c != null){
					String str =  new String(c.getBytes(),"utf-8");
					str = str.replaceAll(": ", ":");
					String[] r = str.split(";");
					PostInfo _info = new PostInfo();
					for(int j=0;j<r.length;j++){
						if(!match(r[j],"(?<=地址:).*").equals(""))
							_info.address = match(r[j],"(?<=地址:).*");
						if(!match(r[j],"(?<=邮编:).*").equals(""))
							_info.code = match(r[j],"(?<=邮编:).*");
						
						if(!match(r[j],"(?<=省份:).*").equals("")){
							String province = match(r[j],"(?<=省份:).*");
							_info.province =match(province,"[^省|市|区]+[省|市|区]");
							String sub = province.substring(_info.province.length(),province.length());
							if(_info.province.indexOf("市")==-1)
								_info.city =match(sub,"[^州|市|区]+[州|市|区]");
							sub = sub.substring(_info.city.length(),sub.length());
							_info.county = sub;
						}
					}
					String val = "privnce: " + _info.province + ";city:" + _info.city + ";county:"+_info.county + ";address" +_info.address + ";code:" + _info.code;
					
//					String val = "privnce: " + _info.privnce + ";city:" + _info.city + ";county:"+_info.county + ";address" +_info.address + ";code:" + _info.code;
					
					System.out.println(val);
					int j;
					for (j=0;j<list.size();j++){
						if(list.get(j).equals(val))
							break;
					}
					if (j==list.size()){
						list.add(val);
						addItemYoubianku(_info);
					}
					c =br.readLine();
				}
				br.close();	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		DbPostCode code = new DbPostCode();		
		code.gatherYoubiankuRecord("d:\\product\\lib\\youbianku");

	}
}
