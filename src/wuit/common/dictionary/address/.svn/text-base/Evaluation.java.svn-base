/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.LocalInfo;
import wuit.common.dictionary.words.KeyWord;

/**
 *
 * @author lxl
 */
public class Evaluation {    
    private final double bl_net = 25.0;
    private final double bl_phone = 25.0;
    private final double bl_direct = 25.0;
    //address : 被解析的地址
    //phone :   电话区号
    //post  :   邮编
    //data_d:   直接解析的结果
    //list  :   网络学习的词集合
    //db    :   地址字典
    
    public static void evaluate(LocalInfo data_r,LocalInfo data_d){
        if(data_r.province.isEmpty() && !data_d.province.isEmpty())
            data_r.province = data_d.province;
        if(data_r.city.isEmpty() && !data_d.city.isEmpty())
            data_r.city = data_d.city;
        if(data_r.county.isEmpty() && !data_d.county.isEmpty())
            data_r.county = data_d.county;
    } 
    
    public static void evelutePhoneAddress(LocalInfo data,String phone,AddressDataBase db){
        if(phone.isEmpty())
            return;
        List<String> list = db.queryAddressByPhone(phone);
        if(!list.isEmpty()){
            data.province = list.get(0).split(",")[0];
            data.city = list.get(0).split(",")[1];
            data.county = list.get(0).split(",")[2];
        }
        
        for(int i = 1; i<list.size();i++){
            data.county = data.county + "," + list.get(i).split(",")[2];
        }
    }    
    
    public static void evelutePostAddress(LocalInfo data,String post,AddressDataBase db){
        if(post.isEmpty())
            return;
        List<String> list = db.queryAddressByPost(post);
        List<Map<String,KeyWord>> listMap = new ArrayList<Map<String,KeyWord>>();
        for(int i=0;i<3;i++){
            Map<String,KeyWord> map = new HashMap<String,KeyWord>();
            listMap.add(map);
        }        
        setListMap(list,listMap);        
        statistics(data,listMap);
    }     
    
    public static void eveluteNetAddress(LocalInfo _data1,LocalInfo _data2,List<KeyWord> list,AddressDataBase db){
        List<KeyWord> rs = new ArrayList<KeyWord>();        
        for(int i=0;i<list.size();i++){            
            String key = list.get(i).value;
            if(_data1.Road.indexOf(key)>=0)
                continue;
            int count = list.get(i).count;
            List<String> _rs = db.queryWords(key);
            for(int j = 0 ;j <_rs.size();j++){
                KeyWord word = new KeyWord();
                word.count = count;
                word.key = key;
                word.value = _rs.get(j);
                rs.add(word);
            }
        }
        
        List<Map<String,KeyWord>> listMap = new ArrayList<Map<String,KeyWord>>();
        for(int i=0;i<3;i++){
            Map<String,KeyWord> map = new HashMap<String,KeyWord>();
            listMap.add(map);
        }
        
        setListMapNet(rs,listMap);
        
        statistics(_data2,listMap);
        
    }
    
    public static int evaluteLocalInfo(LocalInfo data){
        int flag = 0;
        if(!data.province.isEmpty())    flag = flag | 0x1;                     //     100
        if(!data.city.isEmpty())        flag = flag | 0x2;                     //    1000
        if(!data.county.isEmpty())      flag = flag | 0x4;                     //   10000
        if(!data.township.isEmpty())    flag = flag | 0x8;                     //  100000
        if(!data.village.isEmpty())     flag = flag | 0x10;                    // 1000000
        if(!data.Road.isEmpty())        flag = flag | 0x20;                    //10000000
        return flag;
    }    
    
    private void dispInfo(Map<String,KeyWord> province,Map<String,KeyWord> city,Map<String,KeyWord> county){
        Set set = province.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
            System.out.println(body.getKey() + " : " + body.getValue().level + " : " + body.getValue().bl+ " : " + body.getValue().value);
        }
 
        set = city.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
            System.out.println(body.getKey() + " : " + body.getValue().level + " : " + body.getValue().bl+ " : " + body.getValue().value);
        }        
        
        set = county.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
            System.out.println(body.getKey() + " : " + body.getValue().level + " : " + body.getValue().bl+ " : " + body.getValue().value);
        }        
    }

    public static void setListMap(List<String> list, List<Map<String,KeyWord>> listMap){
        for(int i=0;i<list.size();i++){
            String[] vals = list.get(i).split(",");
            KeyWord word = new KeyWord();
            switch(vals.length){
                case 1: 
                    if(listMap.get(0).containsKey(vals[0])){
                        listMap.get(0).get(vals[0]).count = listMap.get(0).get(vals[0]).count + 1;                        
                    }else{
                        word = new KeyWord();
                        word.count = 1;
                        word.key = list.get(i);
                        word.value = list.get(i);
                        listMap.get(0).put(word.key, word);
                    }
                    break;
                case 2: 
                    if(listMap.get(1).containsKey(list.get(i))){
                        listMap.get(1).get(list.get(i)).count = listMap.get(1).get(list.get(i)).count + 1;                        
                    }else{
                        word = new KeyWord();
                        word.count = 1;
                        word.key = list.get(i);
                        word.value = list.get(i);
                        listMap.get(1).put(word.key, word);
                    }                    
                    break;
                case 3: 
                    if(listMap.get(2).containsKey(list.get(i))){
                        listMap.get(2).get(list.get(i)).count = listMap.get(2).get(list.get(i)).count + 1;                        
                    }else{
                        word = new KeyWord();
                        word.count = 1;
                        word.key = list.get(i);
                        word.value = list.get(i);
                        listMap.get(2).put(word.key, word);
                    }                     
                    break;
            }
        }
    }    
    
    public static void setListMapNet(List<KeyWord> list, List<Map<String,KeyWord>> listMap){
        for(int i=0;i<list.size();i++){
            String[] vals = list.get(i).value.split(",");
            KeyWord word = new KeyWord();
            switch(vals.length){
                case 1: 
                    if(listMap.get(0).containsKey(list.get(i).value)){
                        listMap.get(0).get(list.get(i).value).count = listMap.get(0).get(list.get(i).value).count +  + list.get(i).count;
                    }else{
                        word = new KeyWord();
                        word.count = list.get(i).count;
                        word.key = vals[0];
                        word.value = list.get(i).value;
                        listMap.get(0).put(word.value, word);
                    }
                    break;
                case 2: 
                    if(listMap.get(1).containsKey(list.get(i).value)){
                        listMap.get(1).get(list.get(i).value).count = listMap.get(1).get(list.get(i).value).count +  + list.get(i).count;                        
                    }else{
                        word = new KeyWord();
                        word.count = list.get(i).count;
                        word.key = vals[1];
                        word.value = list.get(i).value;
                        listMap.get(1).put(word.value, word);
                    }                    
                    break;
                case 3: 
                    if(listMap.get(2).containsKey(list.get(i).value)){
                        listMap.get(2).get(list.get(i).value).count = listMap.get(2).get(list.get(i).value).count + list.get(i).count;
                    }else{
                        word = new KeyWord();
                        word.count = list.get(i).count;
                        word.key = vals[2];
                        word.value = list.get(i).value;
                        listMap.get(2).put(word.value, word);
                    }                     
                    break;
            }
        }
    }
    
    public static void statistics(LocalInfo data,List<Map<String,KeyWord>> listMap){
        int sum_1 = 0;
        int sum_2 = 0;
        int sum_3 = 0;
        
        //求个级别和
        for(int i=0; i<listMap.size(); i++){
            Set set = listMap.get(i).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
                switch(i){
                    case 0:sum_1 = sum_1 + body.getValue().count ;break;
                    case 1:sum_2 = sum_2 + body.getValue().count ;break;
                    case 2:sum_3 = sum_3 + body.getValue().count ;break;
                }
            }
        }
        
        int sum = sum_1 + sum_2 + sum_3;
        //调整统计值，和 乘 倍率
        for(int i=0; i<listMap.size(); i++){
            Set set = listMap.get(i).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
                switch(i){
                    case 0:body.getValue().count = body.getValue().count * 6 * sum ;break;
                    case 1:body.getValue().count = body.getValue().count * 3 * sum ;break;
                    case 2:body.getValue().count = body.getValue().count * 1 * sum;break;    
                }
            }
        }
        
        //按统计值分别求各个级别的，次序，省级、市级、县级
        //取省级
        Set set = listMap.get(0).entrySet();
        Iterator it = set.iterator();
        int count = 0;
        String name_1 = "";
        String key_1 = "";
        while(it.hasNext()){
            Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
            if(count<body.getValue().count){
                count = body.getValue().count;
                name_1 = body.getKey();
                key_1 = body.getKey();
                continue;
            }
            if(count == body.getValue().count){
                count = body.getValue().count;
                name_1 = name_1 + ":" + body.getKey();
                if(!key_1.equals(body.getValue()))
                    key_1 = key_1 + "," + body.getKey();
                continue;
            }            
        }

        set = listMap.get(1).entrySet();
        it = set.iterator();
        count = 0;
        String name_2 = "";
        String key_2 = "";
        while(it.hasNext()){
            Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
            String[] vals = body.getValue().value.split(",");
            if(count<body.getValue().count){
                if(key_1.isEmpty() || key_1.isEmpty() && key_1.equals(vals[0])){
                    name_2 = body.getValue().value;
                    key_2 = vals[1];
                    key_1 = vals[0];
                    name_1 = vals[0];
                    count = body.getValue().count;
                    continue;
                }
            }
            if(count == body.getValue().count){
                if(key_1.isEmpty() || !key_1.isEmpty() && key_1.equals(vals[0])){
                    name_2 = name_2 + ":" + body.getValue().value;
                    if(!key_2.equals(vals[1]))
                        key_2 = key_2 + "," + vals[1];
                    count = body.getValue().count;
                    continue;
                }
            }            
        }
        
        set = listMap.get(2).entrySet();
        it = set.iterator();
        count = 0;
        String name_3 = "";
        String key_3 = "";
        while(it.hasNext()){
            Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
            String[] vals = body.getValue().value.split(",");
            if(count<body.getValue().count){
                if(key_1.isEmpty() && key_2.isEmpty() ||
                        key_1.isEmpty() && !key_2.isEmpty() && vals[1].equals(key_2) ||
                        !key_1.isEmpty() && key_2.isEmpty() && vals[0].equals(key_1) ||
                        !key_1.isEmpty() && !key_2.isEmpty() && vals[1].equals(key_2) && vals[0].equals(key_1)){
                    name_3 = body.getValue().value;
                    count = body.getValue().count;
                    key_3 = vals[2];
                    continue;
                }
            }
            if(count == body.getValue().count){
                if(key_1.isEmpty() && key_2.isEmpty() ||
                        !key_1.isEmpty() && key_2.isEmpty() && vals[1].equals(key_2) ||
                        key_1.isEmpty() && !key_2.isEmpty() && vals[0].equals(key_1) ||
                        !key_1.isEmpty() && !key_2.isEmpty() && vals[1].equals(key_2) && vals[0].equals(key_1)){
                    if(!key_3.equals(vals[2]))
                        key_3 = key_3 + "," + vals[2];
                    name_3 = name_3 +":" +  body.getKey();
                    count = body.getValue().count;
                    continue; 
                }
            }
        }
//        System.out.println(name_3 + ":" + count + " : " + "statistics"); 
        if(!name_1.isEmpty()){
            if(key_1.split(",").length==1){
                data.province = key_1;
            }
        }
        
        if(!name_2.isEmpty()){
            data.city = key_2;
            if(key_2.split(",").length == 1){
                String[] vals = name_2.split(",");
                if(data.province.isEmpty()){
                    data.province = vals[0];
                    data.city = key_2;                    
                }
                if(!data.province.isEmpty() && vals[0].equals(data.province)){
                    data.city = key_2;
                }
            }
            if(key_2.split(",").length > 1 && !data.province.isEmpty()){
                String[] vals1 = name_2.split(":");
                for(int i=0;i<vals1.length;i++){
                    if(vals1[i].indexOf(data.province)>=0){
                        String[] vals2 = vals1[i].split(",");
                        data.city = vals2[1];
                    }
                }
            }
        }
        
        if(!name_3.isEmpty()){
            data.county = key_3;
            if(name_3.split(":").length == 1){
                String[] vals = name_3.split(",");
                if(data.province.isEmpty() && data.city.isEmpty() || 
                        data.province.isEmpty() && !data.city.isEmpty() && data.city.equals(vals[1]) ||
                        !data.province.isEmpty() && data.city.isEmpty()&& data.province.equals(vals[0]) ||
                        !data.province.isEmpty() && !data.city.isEmpty()&& data.province.equals(vals[0]) && data.city.equals(vals[1])){
                    data.county = key_3;
                    data.province = vals[0];
                    data.city = vals[1];
                }
            }
            if( name_3.split(":").length >1  && !data.province.isEmpty()  && !data.city.isEmpty() ){
                String[] vals1 = name_3.split(":");
                for(int i=0;i<vals1.length;i++){
                    String[] vals2 = vals1[i].split(",");
                    if(vals2[0].equals(data.province) && vals2[1].equals(data.city)){
                        data.county = vals2[2]; 
                    }
                }                
            }
        }
//        System.out.println(data.province + ":" + data.city + ":" + data.county + " : " + "statistics");
    }      
 
    public static int evaluteInfo(EvalutionValue mode){
        int val = 0;
        if(mode.address_1 != 0){
            val = val+ ((mode.address_1 & 0x1))*30;
            val = val+ ((mode.address_1 & 0x2)>>1)*20;
            val = val+ ((mode.address_1 & 0x4)>>2)*10;
        }
        if(mode.address_2 != 0){
            val = val+ ((mode.address_2 & 0x1))*30;
            val = val+ ((mode.address_2 & 0x2)>>1)*20;
            val = val+ ((mode.address_2 & 0x4)>>2)*10;
        }        
        if(mode.phone != 0){
            val = val+ ((mode.phone & 0x1))*30;
            val = val+ ((mode.phone & 0x2)>>1)*20;
            val = val+ ((mode.phone & 0x4)>>2)*10;
        }        
        if(mode.post != 0){
            val = val+ ((mode.post & 0x1))*30;
            val = val+ ((mode.post & 0x2)>>1)*20;
            val = val+ ((mode.post & 0x4)>>2)*10;
        }        
        if(mode.net_address != 0){
            val = val+ ((mode.net_address & 0x1))*30;
            val = val+ ((mode.net_address & 0x2)>>1)*20;
            val = val+ ((mode.net_address & 0x4)>>2)*10;
        }        
        if(mode.net_phone != 0){
            val = val+ ((mode.net_phone & 0x1))*30;
            val = val+ ((mode.net_phone & 0x2)>>1)*20;
            val = val+ ((mode.net_phone & 0x4)>>2)*10;
        }        
        if(mode.net_post != 0){
            val = val+ ((mode.net_post & 0x1))*30;
            val = val+ ((mode.net_post & 0x2)>>1)*20;
            val = val+ ((mode.net_post & 0x4)>>2)*10;
        }
        return val;
    }
    
    public static int evaluteStatInt(EvalutionValue mode){
        int val = 0;
        if(mode.address_1 != 0){
            val = val | 0x1;
        }
        if(mode.address_2 != 0){
            val = val | 0x2;
        }        
        if(mode.phone != 0){
            val = val | 0x4;
        }        
        if(mode.post != 0){
            val = val | 0x8;
        }        
        if(mode.net_address != 0){
            val = val | 0x10;
        }        
        if(mode.net_phone != 0){
            val = val | 0x20;
        }        
        if(mode.net_post != 0){
            val = val | 0x40;
        }
        String _val = Integer.toBinaryString(val);
        return val;
    }
    public static String evaluteStatString(EvalutionValue mode){
        String val = "";
        if(mode.address_1 != 0){
            val = "1";
        }else{
            val = "0";
        }
        if(mode.address_2 != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }
        if(mode.phone != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }       
        if(mode.post != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }        
        if(mode.net_address != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }       
        if(mode.net_phone != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }       
        if(mode.net_post != 0){
            val = "1" + val;
        }else{
            val = "0" + val;
        }
        return val;
    }    
    
    public static int verifyConflictPhoneAndAddress(DSComposite composite,AddressDataBase db){
        List<String> list = db.queryAddressByPhone(composite.phone_code);
        String val = composite.local.province+","+ composite.local.city;
        for(int i=0; i<list.size();i++){
            if(list.get(i).indexOf(val)>=0)
                return 1;
        }
        return 0;
    }
    

    
    /*
    public static int evaluteLocalInfo(LocalInfo data,int mode){
        int flag = 0;
        if(!data.province.isEmpty())    flag = flag | 0x1;                      //     100
        if(!data.city.isEmpty())        flag = flag | 0x2;                      //    1000
        if(!data.county.isEmpty())      flag = flag | 0x4;                     //   10000
        if(!data.township.isEmpty())    flag = flag | 0x8;                     //  100000
        if(!data.village.isEmpty())     flag = flag | 0x10;                     // 1000000
        if(!data.Road.isEmpty())        flag = flag | 0x20;                     //10000000
        
        if(flag != 0){
            if(mode == 1)                   flag = flag | 0x1;
            if(mode == 2)                   flag = flag | 0x2;            
        }
        return flag;
    }
    */
}
