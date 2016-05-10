/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.LocalInfo;
import wuit.common.dictionary.BaseAddress;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class TransAddress {
    
    static AddressDataBase db = new AddressDataBase();
    
                   /*
                   String val1 = matchValue(c,"(?<=地址:)[^;].+?(?=;)");
                   val1 = val1.replaceAll("\\(.+?\\)", "");
                   val1 = val1.replaceAll("\\d{1,}\\-\\d{1,}", "");
                   String val2 = matchValue(c,"(?<=省份: )[^;].+");
//                   String val3 = val2.replace("上海市", "");
                   
                   String val = "上海市,上海市辖区,"+ val2 +"," + val1;
                   String post = matchValue(c,"(?<=邮编:)[^;].+?(?=;)");
                   */
    /*
    private static void readTextPathFile(String pathfile){
        Map<String,LocalInfo> map = new HashMap<String,LocalInfo>();
        try{
//            List<String> lstFiles = new ArrayList<String>();
//            List<String> dirs = new ArrayList<String>();
//            FileIO.getFilesAndSubDirFiles(path, lstFiles, dirs);

//            for (int i = 0; i<lstFiles.size(); i++)
            {
//               String pathFile = lstFiles.get(i);
               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathfile),"GB2312"));
               String c = br.readLine();
               while(c != null){
                   String [] vals = c.split(",");
                   String code = vals[0];
                   String address = vals[1];
                   String code1 = code.substring(0,2);
                   String code2 = code.substring(2,4);
                   String code3 = code.substring(4,6);
                   String code4 = code.substring(6,9);
                   String code5 = code.substring(9,12);
                   
                   LocalInfo data = new LocalInfo();
//                   Extractor.tryDistrictNumber(address, data);
                   String _val = address.replace(data.province, "").replace(data.city, "").replace(data.county, "");
                   if(data.township.isEmpty() && !_val.isEmpty())
                       data.township = _val;
                   
                   String val =code1 + ":" + data.province+","+code2+":" + data.city + "," +code3+":" + data.county + "," + data.countyRoad + ","+code4+":"  +data.township + "," + data.townRoad + "," + data.village + ","  + data.villageRoad;
                   System.out.println(c);
                   System.out.println(val);   
                   
//db.dbAddress.InsertBaseDistricCodes(code, address, code1, data.province, code2, data.city, code3, data.county, code4, data.township, code5, data.village);
                   
                   c =br.readLine();
 
                   if(!map.containsKey(val)){
//                       map.put(val, data);
                   }
                   //System.out.println(c);
               }
               br.close();
            }
            Set set = map.entrySet();
            Iterator it = set.iterator();
            int i=0;
            while(it.hasNext()){
                Entry<String,LocalInfo> bd = (Entry<String,LocalInfo>)it.next();
                LocalInfo data = bd.getValue();
                System.out.println(i+ " " +data.province+"," +data.city + "," + data.county + "," + data.countyRoad + ","+ data.township + "," + data.townRoad + "," + data.village + "," + data.villageRoad );
//                db.dbAddress.InsertBasePostCode(data.post, data.province, data.city, data.county, data.countyRoad, data.township, data.townRoad, data.village, data.villageRoad);
                i++;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }
*/
/*
    private static void readTextFile(String path){
        Map<String,LocalInfo> map = new HashMap<String,LocalInfo>();
        try{
            List<String> lstFiles = new ArrayList<String>();
            List<String> dirs = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(path, lstFiles, dirs);

            for (int i = 0; i<lstFiles.size(); i++){
               String pathFile = lstFiles.get(i);
               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
               String c = br.readLine();
               while(c != null){
                   String val1 = matchValue(c,"(?<=地址:)[^;].+?(?=;)");
                   val1 = val1.replaceAll("\\(.+?\\)", "");
                   val1 = val1.replaceAll("\\d{1,}\\-\\d{1,}", "");
                   String val2 = matchValue(c,"(?<=省份: )[^;].+");
//                   String val3 = val2.replace("上海市", "");
                   
                   String val = "上海市,上海市辖区,"+ val2 +"," + val1;
                   String post = matchValue(c,"(?<=邮编:)[^;].+?(?=;)");
                   LocalInfo data = new LocalInfo();
                   Extractor.parseAddress(val2+val1, data, db);
//                   data.province = "市";
//                   data.city = "北京市辖区";
                   data.post = post;
                   val =post + ","+  data.province+"," + data.city + "," + data.county + "," + data.countyRoad + "," +data.township + "," + data.townRoad + "," + data.village + ","  + data.villageRoad;
                   
                   c =br.readLine();
                   System.out.println(val2+val1);
                   if(!map.containsKey(val)){
                       map.put(val, data);
                   }
                   //System.out.println(c);
               }
               br.close();
            }
            Set set = map.entrySet();
            Iterator it = set.iterator();
            int i=0;
            while(it.hasNext()){
                Entry<String,LocalInfo> bd = (Entry<String,LocalInfo>)it.next();
                LocalInfo data = bd.getValue();
                System.out.println(i+ " " +data.province+"," +data.city + "," + data.county + "," + data.countyRoad + ","+ data.township + "," + data.townRoad + "," + data.village + "," + data.villageRoad );
                
//                db.dbAddress.InsertBasePostCode(data.post, data.province, data.city, data.county, data.countyRoad, data.township, data.townRoad, data.village, data.villageRoad);
                i++;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }    
    
    private static String matchValue(String content,String filter){
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if (m.group().isEmpty())
                    continue;
                return m.group();
            }
        }catch(Exception e){
            System.out.println("Service address  matchValues :" + e.getMessage());
        }
        return "";
    }     
*/    
    
    public static void main(String[] args){
//        TransAddress.readTextPathFile("D:\\projects\\CrawlerSystem\\database\\Address\\行政区划乡.txt");
    }
}
