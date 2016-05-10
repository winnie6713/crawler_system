/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
此类用于将行政区划、邮政编码、电话区号等文本数据转换后写入数据库
 */

package wuit.common.dictionary.address;

import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.sourceforge.sizeof.test.TestSizeOf.print;

/**
 *
 * @author lxl
 */
public class TextDB {
    Database db = new Database();
    public TextDistrictInfo districtText = new TextDistrictInfo();
    public TextPostCodeInfo postText = new TextPostCodeInfo();
    
    public TextDB(){
        db.dbConnection();
    }
    
    class Database{
	public String dbHost = "localhost"; 
	public String dbPort = "3306";
	public String dbName = "localServer"; 
	public String dbuserName = "root"; 
	public String dbpsw = "aq12ws";
    
    
        public Connection dbconn;
        public Statement dbstate;
        public ResultSet dbresult;
        
        
        
        
        public Database(){
            
        }
        
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
        
        public boolean InsertBaseDistricCodes(String code,String province,String city,String county,String townroad){
            boolean insertResult = false; 
            String sql = "Insert into districts (code,province,city,county,townroad) values ('"+ 
                code+"','"+province+"','"+city+"','"+county+"','"+townroad+"')";
            try{
                if (dbstate == null)
                    dbstate = (Statement) dbconn.createStatement();
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

        public boolean InsertPostCodes(String code,String province,String city,String county,String township,String village,String road){
            boolean insertResult = false; 
            String sql = "Insert into postcodeaddress (postcode,province,city,county,township,village,road) values ('"+ 
                code+"','"+province+"','"+city+"','"+county+"','"+township+"','"+village+"','"+road+"')";
            try{
                if (dbstate == null)
                    dbstate = (Statement) dbconn.createStatement();
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
    
    }
    
    //行政区划类：将指定的文本文档，依据文本记录的格式将其内容写入数据库
    
    class TextDistrictInfo{

        public void readDistrictInfoText(String file){
            try {
                BufferedReader br = null;
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
                    String c = br.readLine();
                    while(c != null){
                            String[] cs = c.split(",");
                            //System.out.println(c);
                            if(cs.length>1){
                               // System.out.println(cs[0] + "|" + cs[1]);
                                String _c1 =  getProvinceName(cs[1]);
                                String _val = cs[1].replace(_c1, "");
                                String _c2 = getCityName(_val);
                                _val = _val.replace(_c2, "");
                                String _c3 = getCountyName(_val);
                                _val = _val.replace(_c3, "");
                                System.out.println(cs[0] + "," + _c1 + ","+_c2+ "," + _c3 + "," + _val);

                                db.InsertBaseDistricCodes(cs[0], _c1, _c2, _c3, _val);
                            }

                            c = br.readLine();
                    }
                    br.close();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private String getProvinceName(String value){
            String val = value;
            int index = value.indexOf("省");
            if(index>0){
                val = value.substring(0,index+1);
            }else{
                index =value.indexOf("自治区");
                if(index>0){
                    val = value.substring(0,index+3);                               
                }else{
                    index = value.indexOf("市");
                    if(index>0){
                        val = value.substring(0,index+1);                              
                    }else{
                        index = value.indexOf("兵团");
                        if(index>0){
                            val = value.substring(0,index+2);                                
                        }else{

                        }
                    }                                    
                }
            }        
            return val;
        }

        private String getCityName(String value){
            String val = value;
            int index = value.indexOf("市");
            if(index>0){
                val = value.substring(0,index+1);
            }else{
                index =value.indexOf("自治州");
                if(index>0){
                    val = value.substring(0,index+3);                               
                }else{
                    index = value.indexOf("地区");
                    if(index>0){
                        val = value.substring(0,index+2);                              
                    }else{
                        index = value.indexOf("师");
                        if(index>0){
                            val = value.substring(0,index+1);                                
                        }else{
                            index = value.indexOf("团");
                            if(index>0){
                                val = value.substring(0,index+1);                                
                            }else{
                                index = value.indexOf("州");
                                if(index>0){
                                    val = value.substring(0,index+1);                                
                                }else{
                                    index = value.indexOf("区");
                                    if(index>0){
                                        val = value.substring(0,index+1);                                
                                    }else{
                                        val="";

                                    }
                                }

                            }
                        }
                    }                                    
                }
            }        
            return val;
        }    

        private String getCountyName(String value){
            String val = value;
            int index = value.indexOf("县");
            if(index>0){
                val = value.substring(0,index+1);
            }else{
                index =value.indexOf("委员会");
                if(index>0){
                    val = value.substring(0,index+3);                               
                }else{
                    index = value.indexOf("自治县");
                    if(index>0){
                        val = value.substring(0,index+3);                              
                    }else{
                        index = value.indexOf("区");
                        if(index>0){
                            val = value.substring(0,index+1);                                
                        }else{
                            index = value.indexOf("市");
                            if(index>0){
                                val = value.substring(0,index+1);                                
                            }else{
                                index = value.indexOf("旗");
                                if(index>0){
                                    val = value.substring(0,index+1);                                
                                }else{
                                    index = value.indexOf("团");
                                    if(index>0){
                                        val = value.substring(0,index+1);                                
                                    }else{
                                        val = "";

                                    }

                                }

                            }
                        }
                    }                                    
                }
            }        
            return val;
        }     

    }
    
    
    class TextPostCodeInfo{
        private String file = "D:\\projects\\CrawlerSystem\\database\\Address\\postcodes.txt";
        public void readText(){
            try {
                BufferedReader br = null;
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
                    String c = br.readLine();
                    while(c != null){
                            String[] cs = c.split(":");
                            //System.out.println(c);
                            if(cs.length>1){
                               // System.out.println(cs[0] + "|" + cs[1]);
                                String _code = cs[0];                                
                                String _province = cs[1];
                                String _city = "";
                                String _county = "";
                                if(_province.indexOf("北京")>=0||_province.indexOf("天津")>=0||
                                        _province.indexOf("上海")>=0||_province.indexOf("重庆")>=0){
                                    _city = _province + "辖区";
                                    _county = cs[2];                                    
                                }else{
                                    _city = cs[2];
                                    _county = cs[3];
                                }
                                String _township = cs[4].trim();
                                String _village = cs[6].trim();
                                String _road = cs[8].trim();
                                System.out.println(_code + "," + _province + ","+_city+ "," + _county + "," + _township+ "," + _village+ "," + _road);

                                db.InsertPostCodes(_code, _province, _city, _county, _township, _village, _road);
                            }

                            c = br.readLine();
                    }
                    br.close();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TextDB.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
    
    
    public static void main(String[] args){
        TextDB txtDB = new TextDB();
        txtDB.postText.readText();
//txtDB.districtText.readDistrictInfoText("D:\\projects\\CrawlerSystem\\database\\Address\\行政区划乡.txt");
    }
    
}
