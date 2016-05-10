/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.extract;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.dictionary.address.Evaluation;
import wuit.crawler.database.LocalInfoDB;
import wuit.crawler.main.MainServer;

/**
 *
 * @author lxl
 * 主要解析地址、户名、电话、邮编等
 */
public class Parser {
//    private ServiceAddress srvAddress = MainServer.srvAddress; 
//    private LocalInfoDB localDB = new LocalInfoDB();
    
    public void parseAddress(DSComposite composite){
        parseName(composite);
        parsePhone(composite);
        
        String address = composite.local.address.trim();
        
        if(address.isEmpty() ){                       //记录缺地址或名称，无效，
            MainServer.DBCrawler.statInfo.parseInv ++;
            return;
        }
        
        int mode = 0;        
        mode = MainServer.srvAddress.parseAddressOfDSComposite(composite);
        mode = composite.stat.parse;
        switch(mode){
            case 1:
                synchronized(this){
//                    MainServer.DBCrawler.listDSCompositesDrct.add(composite);
                    MainServer.DBCrawler.statInfo.parseDirect ++;
                }
                break;            
            case 2:
                synchronized(this){
                    MainServer.DBCrawler.statInfo.parseIndirect ++;
                }
                break;
            case 3:
                synchronized(this){
                    MainServer.DBCrawler.statInfo.parseAmb ++ ;
                }
                break;
        }
        
        //与区号冲突
        if(!composite.phone_code.equals("") && Evaluation.verifyConflictPhoneAndAddress(composite, MainServer.srvAddress.db)== 0){
            MainServer.DBCrawler.statInfo.rsConflict ++;
            composite.stat.RsConflict = 1;
        }
        
        if(composite.local.province.isEmpty()|| composite.local.city.isEmpty()
                ||(!composite.local.province.isEmpty() && !composite.local.city.isEmpty())
                && (composite.local.county.isEmpty() && composite.local.township.isEmpty() 
                && composite.local.village.isEmpty() && composite.local.Road.isEmpty()
                && composite.local.RoadNo.isEmpty())){
            synchronized(this){
                composite.stat.Result = 3;
                MainServer.DBCrawler.statInfo.rsInvalidate ++ ;
            } 
        }else{
            if(!composite.local.county.isEmpty() && !composite.local.building.isEmpty()){
                composite.stat.Result = 1;
                MainServer.DBCrawler.statInfo.rsFull ++;
            }else{                
                composite.stat.Result = 2;
                MainServer.DBCrawler.statInfo.rsPart ++ ;
            }            
        }
        /*
        if(!composite.local.province.isEmpty()&& !composite.local.city.isEmpty() && !composite.name.isEmpty() 
                && (!composite.local.RoadNo.isEmpty() && !composite.local.Road.isEmpty())
                || (!composite.local.RoadNo.isEmpty() || !composite.local.township.isEmpty() || !composite.local.village.isEmpty())){
            if(!composite.local.county.isEmpty() && !composite.local.building.isEmpty()){
                composite.stat.Result = 1;
                MainServer.DBCrawler.statInfo.rsFull ++;
            }else{                
                composite.stat.Result = 2;
                MainServer.DBCrawler.statInfo.rsPart ++ ;
            }
        }else{
            synchronized(this){
                composite.stat.Result = 3;
                MainServer.DBCrawler.statInfo.rsInvalidate ++ ;
            }            
        }
        */
        MainServer.localDB.InsertLocalInfo(composite);
        return ;
    }

    public void parseName(DSComposite composite){
        return;
//        String name = composite.name;
/*        
        if(composite.name.isEmpty())
            return;
        String[] names = composite.name.split("、");
        String[] clearName = {"点评","公交"};
        
        List<String> list = new ArrayList<String>();
        
        for(int i=0;i<names.length;i++){
            int exist = -1;
            for(int j=0;j<clearName.length;j++){
                if(names[i].indexOf(clearName[j])>=0){
                    exist = 0;
                    break;
                }
                String _w = names[i].replaceAll("[0-9{1,}]", ""); 
//                System.out.println(_w + "," + names[i]);
                if(_w.equals("") || _w.length()<=2){
                    exist = 0;
                    break;                    
                }
            }
            if(exist == -1){
                list.add(names[i]);
            }
        }
        
        String val = "";
                 
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
            val = val + list.get(i);
        }
        composite.name = val;
        */
    }
    
    private void parsePhone(DSComposite composite){
        String _phone = composite.phone;
        int index = _phone.indexOf("-");
        String _phone_code = "";
        String _phone_Num = "";
        if(index>0){
            _phone_code = _phone.substring(0,index);
            _phone_Num = _phone.substring(index+1,_phone.length());
        }
        if(_phone_code.replaceAll("[\\d{1,}]","").isEmpty() && composite.phone_code.isEmpty()){
            composite.phone_code = _phone_code;
            composite.phone_number = _phone_Num;
        }
    }
    
    public static void main(String[] args){
        try {
            Parser parse = new Parser();
            String word = "我要点评、公交驾乘、江苏移动六合分公司营业厅";
//        word ="2人点评、公交驾乘、5、讯达通信";
            word = "2人点评、分店、公交驾乘、4.、江宁区广电局";
            word = "、查看全部房型、(5)";
            DSComposite composite = new DSComposite();
            composite.name = word;
//            parse.parseName(composite);
            
            
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String val = sdf.format(stamp);
            Date date = sdf.parse(val);
            System.out.println(date);
            Date date2 = date;
            try {
                Thread.sleep(6000);
                stamp = new Timestamp(System.currentTimeMillis());
//            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                date2 = sdf.parse(sdf.format(stamp));
                 System.out.println(date2);
                 System.out.println(sdf.format(stamp));
//                date2 = sdf.format(stamp);
                Thread.sleep(6000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
            stamp = new Timestamp(System.currentTimeMillis());
//            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date1 = sdf.parse(sdf.format(stamp));
            
            System.out.println(date+ " : " +date1);
            
            System.out.println(date2.compareTo(date));
            System.out.println(date2.compareTo(date1));
            if(date2.compareTo(date)>=0 && date2.compareTo(date1)<=0)
                System.out.println("welcome");
            
        } catch (ParseException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
