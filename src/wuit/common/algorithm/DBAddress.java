package wuit.common.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Statement;
import wuit.common.crawler.composite.CompositeConvert;
import wuit.common.crawler.composite.CompositeParse;

import wuit.common.crawler.composite.DSComposite;

import wuit.common.crawler.db.DbConnect;
import wuit.common.doc.FileIO;

public class DBAddress extends DbConnect{
	private CompositeParse parse;
	CompositeConvert convert = new CompositeConvert();
	public DBAddress(){
		dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw);
		parse = new CompositeParse();
	}
	
	
	public void addAddressToMySQL(DSAddress _info,String tabelName){
		HashMap<String, String> values = new HashMap<String, String>();
		
		values.put("address", _info.address);
		values.put("province", _info.province);	
		values.put("city", _info.city);
		values.put("county", _info.county);
		values.put("district", _info.district);
		values.put("zone", _info.zone);
		values.put("house", _info.house);
		values.put("house_number", _info.house_number);
		values.put("storey", _info.storey);
		values.put("room", _info.room);
		values.put("position", _info.position);
	
		dbInsert(tabelName, values);
	}
	
	
	public void getAddressFromMySQL(String sql){
		try{
			if (dbstate == null)
				dbstate = (Statement) dbconn.createStatement(); 
//			String sql = "select distinct * from composites"; 
//			sql ="select distinct name,address,label,province,city,county,district,building,buildingno,phone,phone1,phone2,postcode,remark,zone,storey,position,reference,distance,date,content from composites order by county";
//			sql = sql + " order by province,city,county,phone1,postcode";
			
			sql ="select distinct address,province,city,county,district,zone,house,house_number,storey,room,position from address order by county";
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage());
			}
			int count = 0;
			int size = 0;
			BufferedWriter bw = null;
			while(dbresult.next()){
				String fileName= count+ "_" + (new Date()).getTime();
				if (bw == null)
					bw = new BufferedWriter(new FileWriter(new File("d:\\product\\lib\\extract\\aibang\\shanghai\\20130616\\" + fileName + ".txt")));
				
				String val = "";
				val  = val + "address:" + dbresult.getString("address");			
				val  = val + ";province:" +dbresult.getString("province");
				val  = val + ";city:" + dbresult.getString("city");
				val  = val + ";county:" + dbresult.getString("county");
				val  = val + ";district:" +dbresult.getString("district");				
				val  = val + ";zone:" + dbresult.getString("zone");
				val  = val + ";storey:" + dbresult.getString("storey");
				val  = val + ";storey:" + dbresult.getString("room");
				val  = val + ";position:" + dbresult.getString("position");
				System.out.println(val);
				bw.write(val + "\r\n");
				size ++;
				if (size>=10000){
					bw.flush();
					bw.close();
					count++;
					size = 0;
					bw = null;
				}
			} 
			System.out.println(count);
		}catch(Exception e){ 
			print("Exception: " + e.getMessage());
		} 
	}
	
	public void txtItemsToDSCompositeList(String path_s,String encode,List<DSComposite> listDsComposite){
//		List<String> list = new ArrayList<String>();
		File filePath = new File(path_s);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		try{
			for (int i=0;i<files.length;i++){
				List<String> listItems =new ArrayList<String>();				
				FileIO.readLines(path_s, files[i], encode, listItems);
				for (int j=0;j<listItems.size();j++){
					if(listItems.get(j)!=null){
						DSComposite _info = new DSComposite();
						convert.StringToDSComposite(listItems.get(j), _info);
						listDsComposite.add(_info);
					}
				}
				System.out.println("完成 ： " + i + " size " + listDsComposite.size());
				System.gc();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////
	public void txtItemsToAddressList(String path_s,String encode,List<String> listAddress){
		File filePath = new File(path_s);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		try{
			for (int i=200;i<files.length;i++){ //files.length
				List<String> listItems =new ArrayList<String>();				
				FileIO.readLines(path_s, files[i], encode,listItems);
				for (int j=0;j<listItems.size();j++){
					if(listItems.get(j)!=null){
						DSComposite _info = new DSComposite();
						convert.StringToDSComposite(listItems.get(j), _info);
						listAddress.add(_info.local.address);
					}
				}
				System.out.println("完成 ： " + i + " size " + listAddress.size());
				System.gc();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
