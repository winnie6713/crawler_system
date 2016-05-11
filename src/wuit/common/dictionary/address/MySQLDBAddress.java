/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import static net.sourceforge.sizeof.test.TestSizeOf.print;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

/**
 * @description 数据库封装类
 * 
 * @author weiyunyun
 *
 */
public class MySQLDBAddress {
	public String dbHost = "localhost";
	public String dbPort = "3306";
	public String dbName = "localServer";
	public String dbuserName = "root";
	public String dbpsw = "root";

	public Connection dbconn;
	public Statement dbstate;
	public ResultSet dbresult;

	public MySQLDBAddress() {
		
	}

	public boolean dbConnection() {
		String driverName = "com.mysql.jdbc.Driver";
		String enCoding = "?useUnicode=true&characterEncoding=utf8";
		String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
				+ enCoding;
		try {
			Class.forName(driverName).newInstance();
			dbconn = DriverManager.getConnection(url, dbuserName, dbpsw);
			dbstate = (Statement) dbconn.createStatement();
		} catch (Exception e) {
			print("Exception: " + e.getMessage());//
		}
		if (dbconn != null)
			return true;
		else
			return false;
	}

	/**
	 * 从数据库查询地址名称
	 * 
	 * @param RS
	 */
	public void queryAddressNames(List<String> RS) {
		try {
			dbstate = (Statement) dbconn.createStatement();
			String sql = "select code,name,parents from address_name_view";
			try {
				dbresult = dbstate.executeQuery(sql);
			} catch (Exception err) {
				print("Exception: " + err.getMessage());
			}
			while (dbresult.next()) {
				String id = dbresult.getString("code");
				String name = dbresult.getString("name");
				String parents = dbresult.getString("parents");
				String rs = name + ":" + id + ":" + parents;
				RS.add(rs);
			}
		} catch (Exception e) {
			print("Exception: " + e.getMessage());
		}
	}

	public void queryAddressPhone(List<String> RS) {
		try {
			dbstate = (Statement) dbconn.createStatement();
			String sql = "select distinct code,phone from localserver.distric_code_phone_post_view";
			try {
				dbresult = dbstate.executeQuery(sql);
			} catch (Exception err) {
				print("Exception: " + err.getMessage());
			}
			while (dbresult.next()) {
				String code = dbresult.getString("code");
				String phone = dbresult.getString("phone");
				String rs = phone + ":" + code;
				RS.add(rs);
			}
		} catch (Exception e) {
			print("Exception: " + e.getMessage());
		}
	}

	public void queryAddressPost(List<String> RS) {
		try {
			dbstate = (Statement) dbconn.createStatement();
			String sql = "select distinct code,post from localserver.distric_code_phone_post_view";
			try {
				dbresult = dbstate.executeQuery(sql);
			} catch (Exception err) {
				print("Exception: " + err.getMessage());
			}
			while (dbresult.next()) {
				String code = dbresult.getString("code");
				String post = dbresult.getString("post");
				String rs = post + ":" + code;
				RS.add(rs);
			}
		} catch (Exception e) {
			print("Exception: " + e.getMessage());
		}
	}

	/*
	 * SELECT distinct a.province,a.city,a.county,a.township,a.village,
	 * a.road,a.postcode,b.code FROM (select * from localserver.postcodeaddress
	 * where road ='西内街') a left join (select * from
	 * localserver.county_district_code_view where code<>'' )b on a.province =
	 * b.province and a.city = b.city and a.county = b.county
	 */

	/*
	 * SELECT a.road,b.code FROM (select * from localserver.postcodeaddress
	 * where road<>'' ) a left join (select * from
	 * localserver.county_district_code_view where code <>'')b on a.province =
	 * b.province and a.city = b.city and a.county = b.county
	 */

	public void queryAddressRoad(List<String> RS) {
		/*
		 * String sql =
		 * "SELECT distinct a.province,a.city,a.county,a.township,a.village, a.road,a.postcode,b.code "
		 * ; sql = sql +
		 * "FROM (select * from localserver.postcodeaddress  where road ='" +
		 * road + "') a "; sql = sql +
		 * "left join (select * from localserver.county_district_code_view where code<>'' )b "
		 * ; sql = sql +
		 * "on a.province = b.province and a.city = b.city and a.county = b.county"
		 * ;
		 */
		String sql = "SELECT  a.road,b.code ";
		sql = sql
				+ "FROM (select * from localserver.postcodeaddress where road<>'' ) a ";
		sql = sql
				+ "left join (select * from localserver.county_district_code_view where code <>'')b ";
		sql = sql
				+ "on a.province = b.province and a.city = b.city and a.county = b.county ";

		try {
			dbstate = (Statement) dbconn.createStatement();
			try {
				dbresult = dbstate.executeQuery(sql);
			} catch (Exception err) {
				print("Exception: " + err.getMessage());
			}
			while (dbresult.next()) {
				String code = dbresult.getString("code");
				String name = dbresult.getString("road");
				String rs = code + ":" + name;
				RS.add(rs);
			}
		} catch (Exception e) {
			print("Exception: " + e.getMessage());
		}

	}

	/*
	 * public boolean InsertBasePostCode(String postcode,String province,String
	 * city,String county,String countyroad, String township,String
	 * townroad,String village,String villageroad){ boolean insertResult =
	 * false; String sql =
	 * "Insert into basepostcodes (postcode,province,city,county,countyroad,township,townroad,village,villageroad) values ('"
	 * ; sql = sql +
	 * postcode+"','"+province+"','"+city+"','"+county+"','"+countyroad
	 * +"','"+township+"','"+townroad+"','"+village+"','"+villageroad +"')";
	 * try{ if (dbstate == null) dbstate = (Statement) dbconn.createStatement();
	 * dbstate.executeUpdate(sql); insertResult = true; }catch(Exception e){
	 * print("Exception: " + e.getMessage()); e.printStackTrace(); } if
	 * (insertResult) return true; else return false; }
	 */

	public static void main(String[] args) {
		MySQLDBAddress db = new MySQLDBAddress();
		db.dbConnection();

		List<String> list = new ArrayList<String>();
		// db.SelectAll(20,list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println(list.size());

	}
}
