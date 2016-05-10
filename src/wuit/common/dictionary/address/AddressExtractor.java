/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.LocalInfo;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;
import wuit.common.dictionary.words.KeyWord;

/**
 *
 * @author lxl
 */
public class AddressExtractor {
    private static final String[] keyRefDistance = {"内","外","近"}; 
    private static final String[] keyRefDirection = {"对面"}; 
    private static final String[] keyRefRelation = {"交"};
    private static final String[] keyRefObject = {"路","街"};
    
    
    private final String[][] keys ={{"自治区","省","市"},              //0: 省
        {"市辖区","自治州","区","市","州","盟"},                     //1：市
        {"自治县","市","区","县","旗"},                              //2：县
        {"街道办事处","办事处","开发区","街道","镇","乡","区","里"}, //3：乡
        {"村","庄","山庄","农场","坝","乐园","区","花园"},                  //4：村
        {"大道","路","街","道","弄","巷","同"},                             //5：路
        {"号","弄"},                                                        //6：牌号
        {"办公室","广场","商场","大厦","号楼","中心","花园","百货","楼","栋","馆","场","厦","城","园","店","门"},     //7：建筑物
        {"层","楼","幢","座"},                                                                                        //8：楼层
        {"中心","区","室","号","店"}};         

    List<Map<String,String>> mapKeys = new ArrayList<Map<String,String>>();
        
    public AddressExtractor(){
        for(int i = 0;i < keys.length; i++){
            Map<String,String> map = new HashMap<String,String>();
            for(int j=0; j<keys[i].length;j++){
                map.put(keys[i][j], keys[i][j]);
            }
            mapKeys.add(map);
        }
    }
        
    public void parseAddress(String address,DSComposite info,AddressDataBase db,EvalutionValue mode){
        LocalInfo data = info.local;
        address = extractReference(address,data);
        
//        List<KeyValue> list = new ArrayList<KeyValue>();
//        extractItems(address,list);
                
        List<KeyValue> list_ = new ArrayList<KeyValue>();
        data.other = diffAddress(address,list_);
        setLocalInfo(data,list_);
        
        List<Map<String,KeyWord>> listMap = new ArrayList<Map<String,KeyWord>>();
        for(int i=0;i<3;i++){
            Map<String,KeyWord> map = new HashMap<String,KeyWord>();
            listMap.add(map);
        }
        
        List<String> _list = new ArrayList<String>();
        verifyInfoByDB(data,_list,db);
        
        Evaluation.setListMap(_list, listMap);
        LocalInfo _data1 = new LocalInfo();
        Evaluation.statistics(_data1,listMap);        
        mode.address_1 =  Evaluation.evaluteLocalInfo(_data1); 
        info.stat.parse = 1;
        
        LocalInfo _data2 = new LocalInfo();
        if((mode.address_1 & 0x7) != 0x7){
            List<String> list2 = new ArrayList<String>();            
            mode.address_2 = extractInfo(data,list2,db);
            for(int i=0;i<listMap.size();i++)
                listMap.get(i).clear();            
            Evaluation.setListMap(list2,listMap);
            Evaluation.statistics(_data2,listMap);
            info.stat.parse = 2;
            //mode.address_2 = Evaluation.evaluteLocalInfo(_data2);
        }
        
        dispData(data);
        evalute(_data1,_data2);
        
        data.province = _data1.province;
        data.city = _data1.city;
        data.county = _data1.county;
        dispData(data);
        System.out.println("address extractor parseAddress");
    }
    
    private String diffAddress(String address,List<KeyValue> list){
        String[] filter = {"","","[^区]{2,}区","","","","","","","",""};
        filter[0] = "[^省]{2,}省|[^自治区]{2,}自治区|[^城市]{2,}市";
        filter[1] = "[^城市]{2,}市|[^自治州]{2,}自治州||[^地区]{2,}地区||[^盟]{2,}盟";
        filter[2] = "[^县]{1,}县|[^路区|开发]{2,}区|[^旗]{2,}旗";
        filter[3] = "[^镇]{2,}镇|[^乡]{2,}乡|[^开发区]{2,}开发区";
        filter[4] = "[^村]{2,}村|[^庄]{2,}庄|[^园]{2,}园";
        filter[5] = "[^路]{2,}路|[^街]{2,}街|[^道]{2,}道";
        filter[6] = "[^弄|号]{2,}弄|[^号]{2,}号|[^巷]{2,}巷";
        filter[7] = "[^厦]{2,}厦|[^店]{2,}店|[^楼]{1,}楼|[^城]{2,}城|[^园]{2,}园|[^中心]{2,}中心|[^广场]{2,}广场|[^门]{1,}门|[^幢]{2,}幢|[^区]{1,}区";
        filter[8] = "[^层]{1,}层|\\d{1,}楼";
        filter[9] = "[^室]{1,}室|[^号]{1,}号";       
        try{
            for(int i=0;i<filter.length;i++){
                String val = "";
                KeyValue value = new KeyValue();
                value.key =""; 
                value.value = "";
                value.order = -1;
                Matcher m = Pattern.compile(filter[i],Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.CANON_EQ).matcher(address);
                while (m.find()) {
                    if (m.group().isEmpty())
                        continue;
                    value.key = value.key + m.group();
                    value.value = value.value + m.group();
                    value.order = i;
                    System.out.println(value.key + " " + value.start + " " + i);
                    address = address.replace(m.group(), "" );
                }
                System.out.println(value.key);
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("Service address  matchValues :" + e.getMessage());
        }
        return address;
    }
    
    private void extractItems(String address,List<KeyValue> list){
        KeyValueSort sort = new KeyValueSort();
        matchValues(address, "[省|州|市|县|区|路|街|道|镇|乡|村|庄|楼|号|弄|厦|座|心|层|室|门|里|场|城|巷|营|园|宫|馆|幢|店]", list);
        Collections.sort(list,sort);
        int start = 0;
        int level = 0;
        for(int i=0;i<list.size();i++){
            String val = address.substring(start,list.get(i).start + 1);
            start = list.get(i).start + 1;
            list.get(i).value = val;
//            list.get(i).order = getLevelOfKey(list.get(i));
            
//            list.get(i).order = getMoreLevelByKey(list.get(i).key,level);
//            level = list.get(i).order;
            System.out.println(list.get(i).start + ":" + list.get(i).key + ":" + val + " " + list.get(i).order);
        } 
        
        //标记end = -1，为删除项
        level = -1;
        for(int i=0;i<list.size();i++){
            KeyValue word2 ;
            KeyValue word3 ;
            if(i+1<list.size()){
                word2 = list.get(i);
                word3 = list.get(i+1);
                word2.order = getMoreLevelByKey(word2.key,level);
                word3.order = getMoreLevelByKey(word3.key,level);
                if(level == -1 && word2.order >= word3.order){
                    list.get(i+1).value = list.get(i).value + list.get(i+1).value;
                    list.get(i).end = -1;
                    if(word2.order <= 5)
                        level = word2.order;
                    continue;
                }
                if(level < word3.order && word3.order<word2.order ||            //687
                        word2.order < level && level <= word3.order ||           //768|727
                        word2.order < level && word3.order < level  ||          //867|767 
                        level < word2.order && word2.order == word3.order       //677
                        || level == word2.order && word2.order < word3.order    //667
                        ){
                   list.get(i+1).value = list.get(i).value + list.get(i+1).value;
                   list.get(i).end = -1;
                   continue;
                }
                if( level <= word2.order && word2.order < word3.order ||
                        level < word2.order && word3.order < level ||
                        level == word2.order && word2.order < word3.order){
                   level = word2.order;
                   continue;                    
                }
            }else{
                word2 = list.get(i);
                word2.order = getMoreLevelByKey(list.get(i).key,level);
                if(word2.order >= level){
                   level = list.get(i).order;
                   continue;                    
                }                 
            }
        }
        List<KeyValue> _list2 = new ArrayList<KeyValue>();
        for(int i=0; i<list.size();i++){
            if(list.get(i).end != -1){
                _list2.add(list.get(i));
            }
        }
        
        for(int i=0; i<_list2.size();i++){
            if(i<_list2.size()-1 && _list2.get(i+1).order == _list2.get(i).order){
                _list2.get(i+1).value = _list2.get(i).value + _list2.get(i+1).value;
                _list2.get(i).end = -1;
            }
        }        
        
//        for(int i=0; i<_list2.size();i++){
//            if(_list2.get(i).end != -1)
//            System.out.println(_list2.get(i).start + ":" + _list2.get(i).key + ":" + _list2.get(i).value + " " + _list2.get(i).order);
//        }        
//        setLocalInfo(data,_list2);
        
        
 //       List<KeyValue> dst = new ArrayList<KeyValue>();
        String _val = address;
        for(int i=0;i<_list2.size();i++){
            _val = _val.replace(_list2.get(i).value, "");
        }                
    }
    
    //综合评估直接data1和间接data2，    
    private void evalute(LocalInfo data1,LocalInfo data2){
        if(data1.province.isEmpty() && data1.city.isEmpty() && data1.county.isEmpty()){
            data1.province = data2.province;
            data1.city = data2.city;
            data1.county = data2.county;
        }
        if(!data1.province.isEmpty() && data1.city.isEmpty() && data1.county.isEmpty()){
            data1.city = data2.city;
            data1.county = data2.county;
        }
        if(!data1.province.isEmpty() && !data1.city.isEmpty() && data1.county.isEmpty()){
            data1.county = data2.county;
        }
        if(!data1.province.isEmpty() && data1.city.isEmpty() && !data1.county.isEmpty()){
            data1.city = data2.city;
        }
        if(data1.province.isEmpty() && data1.city.isEmpty() && !data1.county.isEmpty()){
            data1.province = data2.province;
            data1.city = data2.city;
        }
        if(data1.province.isEmpty() && !data1.city.isEmpty() && data1.county.isEmpty()){
            data1.province = data2.province;
            data1.county = data2.county;
        }
    }
    
    private void verifyInfoByDB(LocalInfo data,List<String> list,AddressDataBase db){
        List<String> _list = new ArrayList<String>();        
        
        if(!data.province.isEmpty()){
            _list = db.verifyAddress(data.province);
            if(!_list.isEmpty())
                list.addAll(_list);
        }
        if(!data.city.isEmpty()){
            _list = db.verifyAddress(data.city);
            if(!_list.isEmpty())
                list.addAll(_list); 
        }
        if(!data.county.isEmpty()){
            _list = db.verifyAddress(data.county);
            if(!_list.isEmpty())
                list.addAll(_list);
        }
        
        if(!data.township.isEmpty()){
            _list = db.verifyAddress(data.township);
            if(!_list.isEmpty()){
                data.township = "";
                list.addAll(_list);
            }
        }        
  
        if(!data.village.isEmpty()){
            _list = db.verifyAddress(data.village);
            if(!_list.isEmpty()){
                data.village = "";
                list.addAll(_list);
            }
        }
    }
    
    //0x40|0x20|0x10|0x8|0x4|0x2|0x1
    //其它|路  |村  |镇 |县 |地 |省
    private int extractInfo(LocalInfo data,List<String> list,AddressDataBase db){
        List<String> _list = new ArrayList<String>();
        int state = 0;
        if(!data.province.isEmpty() && data.province.length()>4){
            extractInfoFromString(0,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x01;
        }

        if(!data.city.isEmpty() && data.city.length()>4 ){
            extractInfoFromString(1,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x02;
            
        }

        if(!data.county.isEmpty() && data.county.length()>4 ){
            extractInfoFromString(2,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x04;            
        }
        
        if(!data.township.isEmpty() && data.township.length()>4){
            extractInfoFromString(3,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x08;            
        }
        
        if(!data.village.isEmpty() && data.village.length()>4){
            extractInfoFromString(4,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x10;            
        }
        
        if(!data.Road.isEmpty()&& data.Road.length()>4 ){
            extractInfoFromString(5,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x20;            
        }        
        if(data.other !=null && !data.other.isEmpty() && data.other.length()>4){
            extractInfoFromString(20,data,_list,db);
            if(!_list.isEmpty())
                state = state | 0x40;            
        }
        return state;
    }
    
    private boolean verifyExtractInfo(LocalInfo data,List<String> list){
        if(list.size() == 1){
            String[] vals = list.get(0).split(",");
            if(data.city.isEmpty() && data.county.isEmpty() && vals.length>2){
                data.province = vals[0];
                data.city = vals[1];
                data.county = vals[2];
                return true;
            }
            if(data.city.isEmpty() && data.county.isEmpty() && vals.length==2){
                data.province = vals[0];
                data.city = vals[1];
                return true;
            }
        }
        if(list.size()>1){
            for(int i=0;i<list.size();i++){
                String[] vals = list.get(i).split(",");
                if(data.county.isEmpty() && vals.length==3){
                    data.county = vals[2];
                    return true;
                }
                if(data.city.isEmpty() && data.county.isEmpty() && vals.length==2){
                    data.city = vals[1];
                    return true;
                }
                if(data.province.isEmpty() && data.city.isEmpty() && data.county.isEmpty() && vals.length==1){
                    data.city = vals[0];
                    return true;
                }                
            }
        }
        return false;
    
    }
    
    private void extractInfoFromString(int index,LocalInfo data, List<String> list,AddressDataBase db){
        String _val = "";
        switch(index){
            case 0:_val = data.province;break;
            case 1:_val = data.city;break;
            case 2:_val = data.county;break;
            case 3:_val = data.township;break;
            case 4:_val = data.village;break;
            case 5:_val = data.Road;break;
            case 20:_val = data.other;break;
        }
        int size = _val.length();
        int start = 0;
        
        if(index>=0 && index<=2){
            String val = _val.substring(start,size);
            while(start < size){
                List<String> _list = db.verifyAddress(val);
                if(_list.isEmpty()){
                    start ++;
                }else{
                    break;
                }
            }
            if(start < size){
                switch(index){
                    case 0: data.province = val;break;
                    case 1: data.city = val;break;
                    case 2: data.county = val;break;
                }
                _val = _val.replace(val, "");
            }
        }
        
        if(index == 5){
            String val = "";
            while(start < size){
                val = _val.substring(start,size);                
                List<String> _list = db.verifyAddressRoad(val);
                if(_list.isEmpty()){
                    start ++;
                }else{
                    break;
                }
            }
            if(start < size){
                data.Road = val;
                _val = _val.replace(val, "");
                for(int j=0; j<keys[5].length;j++){
                    if(_val.indexOf(keys[5][j])>=0){
                        data.Road = _val + val;
                        _val = "";
                    }
                }
            }
        }        
        
        for(int j=0;j<_val.length();j++){
            size = _val.length();
            start = j;
            while(start<size){
                String county = _val.substring(start,size);
                List<String> _list = db.queryWords(county);

                if(!_list.isEmpty() ){
                    if(verifyExtractInfo(data,_list)){
                        _val = _val.replace(county, "");
                        size = _val.length();
                        start = j;
                        continue;
                    }
                }
                size --;
            }
        }
    }
    
    /*
    private int getLevelOfKey(KeyValue words){
        int level = -1;
        for(int i=0; i<keys.length;i++){
            for(int j=0;j<keys[i].length;j++){
                int index = words.value.indexOf(keys[i][j]);
                if(index != -1){
                    words.key = keys[i][j];
                    return i;
                }
            }
        }
        return level;            
    }
    */
    /*
    private int getNextLevelByKey(String key,int level){
        for(int i = level + 1; i < mapKeys.size(); i++){
            if(mapKeys.get(i).containsKey(key))
                return i;
        }            
        return level;
    }
    */

    
    private int getMoreLevelByKey(String key,int level){
        boolean exist = false;
        for(int i = level + 1; i < mapKeys.size(); i++){
            if(mapKeys.get(i).containsKey(key)){
                level = i;
                exist = true;
                break;
            }
        }
        if(exist == false){
        for(int i = level; i >=0; i--){
            if(mapKeys.get(i).containsKey(key)){
                level = i;
                break;
            }
        }            
        }
        return level;
    }    
    

    /*
    private int getLesserLevelByKey(String key,int level){
        int i = 0;
        for(i = level -1; i >=0; i--){
            if(mapKeys.get(i).containsKey(key)){
                break;
            }
        }
        if(i == -1)
            return level;
        return i;
    }    
    */
    
    /*
    private void adjustAddress(List<KeyValue> src,List<KeyValue> dst,AddressDataBase db){
        int j = 0;
        while(src.size()>j){
            KeyValue key = src.get(j);
            if(j == 0){
                dst.add(key);                                               //it is a first item
            }else{
                if(key.value.equals(key.key)){                              //The value of this item equal it's key
                    extractIsOnlyKey(src,dst,key,db,j);
                }else{                                                      //the key is not equal value.
                    extractIsNotOnlyKey(src,dst,key,db,j);
                }
            }
            j++;
        }        
    }        
    */
    
    /*
    private void extractIsOnlyKey(List<KeyValue> src,List<KeyValue> dst,KeyValue keyValue,AddressDataBase db,int currIndex){
        int dj = dst.size() - 1;
        KeyValue d_key = dst.get(dj);
        if(d_key.order<=2){
             if(!db.verifyAddress(d_key.value).isEmpty()){
                 if(currIndex + 1 <src.size()){
                     src.get(currIndex + 1).value = keyValue.value + src.get(currIndex + 1).value;
                 }
             }else{
                 d_key.value = d_key.value + keyValue.value;
                 d_key.key = keyValue.key;
                 d_key.order = keyValue.order;
             }
             return;
        }
        int level_1 = -1;
        int level_2 = -1;
        if(keyValue.order == d_key.order && currIndex + 1 < src.size() && src.get(currIndex+1).order == d_key.order){
            d_key.value = d_key.value + keyValue.value;
            return;
        }
        if(keyValue.order< d_key.order){
            if(dj-1 >= 0){
                level_2 = getNextLevelByKey(keyValue.key,dst.get(dj-1).order);
            }
            level_1 = getNextLevelByKey(keyValue.key,d_key.order);                
            if(currIndex + 1 <src.size() && keyValue.order<level_1 && level_2 > 2){
                src.get(currIndex + 1).value = keyValue.value + src.get(currIndex + 1).value;
                return;
            }                
            d_key.value = d_key.value + keyValue.value;
            d_key.key = keyValue.key;                
            if(dj-1>=0){
                d_key.order = level_2;
            }else{
                d_key.order = keyValue.order;
            }
            return;
        }
    }
        
    private void extractIsNotOnlyKey(List<KeyValue> src,List<KeyValue> dst,KeyValue keyValue,AddressDataBase db,int currIndex){
        int dj = dst.size() - 1;
        KeyValue d_key = dst.get(dj);
        if(keyValue.order > d_key.order){
            dst.add(keyValue);
            return;
        }
        if(keyValue.order == d_key.order && d_key.order <= 2){
            int level = getNextLevelByKey(keyValue.key,d_key.order);
            if(level>d_key.order){
                keyValue.order = level;
                dst.add(keyValue);
            }
            return;
        }
        if(keyValue.order == d_key.order && currIndex + 1 < src.size() && src.get(currIndex+1).order == d_key.order){
            d_key.value = d_key.value + keyValue.value;
            return;
        }
        if(d_key.order <= 2){
            if(!db.verifyAddress(d_key.value).isEmpty()){
                if(currIndex + 1 < src.size() && d_key.order == 2){
                    src.get(currIndex + 1).value = keyValue.value + src.get(currIndex + 1).value; 
                    return;
                }
            }
        }            

        if(dj-1>=0 && keyValue.order > dst.get(dj-1).order && keyValue.order<=d_key.order){
            d_key.value = d_key.value + keyValue.value;
            d_key.key = keyValue.key;
            d_key.order = keyValue.order;
            return;
        }

        int level = getNextLevelByKey(keyValue.key,d_key.order);
        if(level > d_key.order){
            keyValue.order = level;
            dst.add(keyValue);
            return;
        }

        if(currIndex+1<src.size() && src.get(currIndex+1).order>src.get(currIndex).order && src.get(currIndex+1).order<d_key.order){
            d_key.value = d_key.value + keyValue.value;
            d_key.key = keyValue.key;
            d_key.order = keyValue.order;
            return;
        }
        
        if(dj - 1 >= 0){
            if(keyValue.order > dst.get(dj -1).order){
                dst.get(dj).order = keyValue.order;
                dst.get(dj).value = dst.get(dj).value + keyValue.value;
                dst.get(dj).key = keyValue.key;
            }else{
                level = getNextLevelByKey(keyValue.key,d_key.order);
                if(level > dst.get(dj -1).order){
                    if(level <= dst.get(dj).order){
                        dst.get(dj).order = level;
                        dst.get(dj).value = dst.get(dj).value + keyValue.value;
                        dst.get(dj).key = keyValue.key;
                    }else{
                        keyValue.order  = level;
                        dst.add(keyValue);
                    }
                }else{
                    if(currIndex + 1 < src.size()){
                        src.get(currIndex + 1).value = src.get(currIndex + 1).value + keyValue.value;                            
                    }else{
                        return ;
                    }
                }
            }
        }else{
            level = getNextLevelByKey(keyValue.key,keyValue.order);
            if(level > dst.get(dj).order){
                keyValue.order  = level;
                dst.add(keyValue);
            }else{
                return ;
            }                    
        }
    }
    */
    
    private void setLocalInfo(LocalInfo data,List<KeyValue> list){
        for(int i=0;i < list.size(); i++){
            switch(list.get(i).order){
                case 0: data.province = list.get(i).value;break;
                case 1: data.city = list.get(i).value;break;
                case 2: data.county = list.get(i).value;break;                      
                case 3: data.township = list.get(i).value;break;
                case 4: data.village = list.get(i).value;break;
                case 5: data.Road = list.get(i).value;break;
                case 6: data.RoadNo = list.get(i).value;break;
                case 7: data.building = list.get(i).value;break;
                case 8: data.floor = list.get(i).value;break;
                case 9: data.room = list.get(i).value;break;
            }
        }
    }
        
    private String extractReference(String address,LocalInfo data){
        data.address = address;
        if(address.indexOf("近郊")==0)
            address = address.replace("近郊", "");
        List<KeyValue> list = new ArrayList<KeyValue>();
        matchValues(address,"[\\(|近|附近|（].+?[\\)|）|]|[\\(|近|附近|（].+",list);
        for (int i=0;i<list.size();i++){
            address = address.replace(list.get(i).key, "");
            data.reference = data.reference + list.get(i).key;//.replaceAll("[\\(|\\)|口|近|内|）|（]", "");
        }    

        for(int i=0;i<keyRefObject.length;i++){
            if(data.reference.indexOf(keyRefObject[i])!=-1)
                data.refObject = keyRefObject[i];
        }
        if(!data.refObject.isEmpty())
            data.refRelation = "交";
        for(int i=0;i<keyRefDistance.length;i++){
            if(data.reference.indexOf(keyRefDistance[i])!=-1)
                data.refDistance = keyRefDistance[i];
        }        
        for(int i=0;i<keyRefDirection.length;i++){
            if(data.reference.indexOf(keyRefDirection[i])!=-1)
                data.refDirection = keyRefDirection[i];
        }
       data.reference = data.reference.replaceAll("[\\(|\\)|口|近|内|）|（]", "");
       data.reference = data.reference.replace(data.refDirection, "");
       data.reference = data.reference.replace(data.refDistance, "");
//       data.reference = data.reference.replace(data.refObject, "");
       data.reference = data.reference.replace(data.refRelation, "");
//        System.out.println(data.reference);
        return address;
    }        
        
    private void dispData(LocalInfo _data){
        System.out.println(" 省：" +   _data.province + " 市:" + _data.city + " 县:" + _data.county + " 镇:" +  _data.township + " 村:" +   _data.village); 
        System.out.println("路:" +   _data.Road + " NO:" + _data.RoadNo); 
        System.out.println("楼 B:" +   _data.building  + " FL:" + _data.floor + " ROOM:" + _data.room); 
        System.out.println("参照 R:" +   _data.reference + " : " +_data.flag); 
        System.out.println("不确定:" +   _data.other + "addressExtractor dispData"); 
    }
    
    
    private void matchValues(String content,String filter,List<KeyValue> list){
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.CANON_EQ).matcher(content);
            while (m.find()) {
                if (m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.key = m.group();
                value.start = m.start();
                value.end = m.end();
                value.order = -1;
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("Service address  matchValues :" + e.getMessage());
        }
    }
    

    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        String words = "景德镇人民路";
        list.add(words);
        words = "天津市津南区小站镇米立方海世界水上乐园(津歧路与盛塘路交汇处)";
        list.add(words);
        words = "北京经济技术开发区永昌北路9号西门（北京银行东侧，与可口可乐公司隔街相望）";
        list.add(words);
        words = "南京江宁汤山镇温泉路9号";
        list.add(words);
        words = "坝头洲畔村大山沟永泰路东区";
        list.add(words);
        words = "三北大街机电宿舍";
        list.add(words);
        words = "新泰路六福二巷";
        list.add(words);
        words = "市桥街大罗村东约新村四巷";
        list.add(words);
//        String town = Extractor.diffWords(3,words);
//        words = words.replace(town, "");
        
        
//        Extractor.diffAddress(words, _data);
//        String road = "";
//        road = Extractor.diffWords(3,town);
//        road = Extractor.diffWords(20,words);
//        AddressTown data = new AddressTown();
        words = "真光路1288号百联购物广场";
        list.add(words);
//        words = "解放大道56号景德镇89号青云谱路78号华西村人民路90号";
//         words = "湖南省湘西土家族苗族自治州古丈县景德镇青云谱路2-48号华西村双号";
        words = "天山路900号汇金百货内近娄山关路";//内近娄山关路
        list.add(words);
//        words = "内近娄山关路";
        words = "中山大道江汉路happy站台A079号";
        list.add(words);
//        words = "中山大道happy站台A079号";
//        words = "江汉路happy站台A079号";
        words = "黄浦西藏中路268号来福士广场内";
        list.add(words);
//        words = "锦龙支路金凤大厦北侧";
        words = "启航城18-23号";
        list.add(words);
        words = "大渡河路452号国盛中心B1楼";
        list.add(words);
        words = "柳州路427号近漕宝";
        list.add(words);
        words = "上海南站地下商场南馆地下一层B区南1-B2号";
        list.add(words);
        words = "香花桥街道香大路1379弄16号";
        list.add(words);
        words = "南京西路938号(南京西路泰兴路口)";
        list.add(words);
        
        words = "向阳河路889号";
        list.add(words);
        words = "广元西路317号金山商务楼3楼f座";
        list.add(words);
        words = "顺昌路622号216室";
        list.add(words);
        words = "广元西路317号金山商务楼3楼f座";
        list.add(words);
        words = "浦三路159号三楼近临沂路";
        list.add(words);
        words = "南翔镇古猗园路158弄5号401室";
        list.add(words);
        words = "南京东路800号市百一店东楼17层";
        list.add(words);
        words = "江东北路218号新城市假日广场(龙园南路口)";
        list.add(words);
        words = "白下区中山路55号新华大厦33楼A座(近中山东路)";
        list.add(words);
        words = "江苏南京白下区淮海路68号苏宁电器一楼3C服务中心 ";
        list.add(words);
        words = "南京西路1376号上海商城内(铜仁路)";
        list.add(words);
        words = "罗山路1609号";
        list.add(words);
        words = "南京鼓楼广州路173号";
        list.add(words);
        words = "南京市鼓楼（原下关区）幕府西路118号";
        list.add(words);
        words = "河北省邢台市大曹庄管理区大曹庄乡";
        list.add(words);
        words = "河北省承德市宽城满族自治县";
//        list.add(words);
        words = "河北省承德市宽城满族自治县宽城镇";
        list.add(words);
        words = "河北省保定市涞水县城区社区管理办公室";
        list.add(words);
        words = "河北省保定市南市区南大园乡";
        list.add(words);
        words = "河北省石家庄市灵寿县青同镇";
        list.add(words);
//        words = "北京市市辖区";
//        words = "河北省承德市市辖区";
        words = "北京市朝阳区孙河地区";
        list.add(words);
//        words = "上海黄浦黄浦江";
        words = "鼓楼区新模范马路地铁口200米";
        list.add(words);
        words = "陕西南路145号(近南昌路)";
        list.add(words);
//        words = "新疆维吾尔自治区伊犁哈萨克自治州伊宁市";
        words = "东城区东直门内大街253号";
        list.add(words);
        words = "鼓楼区湖南路27号103室(近八佰伴)";
        list.add(words);
//        words = "东山步行街(近竹山路)";
//        words = "南京南京市区天元西路199号(近静淮路)";
        words = "金沙江西路";
        list.add(words);
        
//        System.out.println(words);
//        LocalInfo _data = new LocalInfo();
        //DSComposite _info = new DSComposite();
        AddressDataBase db = new AddressDataBase(); 
//        Extractor.parseAddress(words, _data, db);
        
        AddressExtractor extractor  = new AddressExtractor();
        EvalutionValue mode = new EvalutionValue();
        for(int i=0;i<list.size();i++){
            DSComposite _info = new DSComposite();
//            System.out.println(list.get(i));
            extractor.parseAddress(list.get(i), _info, db,mode);
        }        
        /*
        String words = "景德镇人民路";
        words = "天津市津南区小站镇米立方海世界水上乐园(津歧路与盛塘路交汇处)";
        words = "北京经济技术开发区永昌北路9号西门（北京银行东侧，与可口可乐公司隔街相望）";
        words = "南京江宁汤山镇温泉路9号";
//        words = "坝头洲畔村大山沟永泰路东区";
//        words = "三北大街机电宿舍";
//        words = "新泰路六福二巷";
//        words = "市桥街大罗村东约新村四巷";
//        String town = Extractor.diffWords(3,words);
//        words = words.replace(town, "");
        
        
//        Extractor.diffAddress(words, _data);
//        String road = "";
//        road = Extractor.diffWords(3,town);
//        road = Extractor.diffWords(20,words);
//        AddressTown data = new AddressTown();
//        words = "真光路1288号百联购物广场";
//        words = "解放大道56号景德镇89号青云谱路78号华西村人民路90号";
//         words = "湖南省湘西土家族苗族自治州古丈县景德镇青云谱路2-48号华西村双号";
//        words = "天山路900号汇金百货内近娄山关路";//内近娄山关路
//        words = "内近娄山关路";
//        words = "中山大道江汉路happy站台A079号";
//        words = "中山大道happy站台A079号";
//        words = "江汉路happy站台A079号";
//        words = "黄浦西藏中路268号来福士广场内";
//        words = "锦龙支路金凤大厦北侧";
//       words = "启航城18-23号";
//        words = "大渡河路452号国盛中心B1楼";
//        words = "柳州路427号近漕宝";
//        words = "上海南站地下商场南馆地下一层B区南1-B2号";
//        words = "香花桥街道香大路1379弄16号";
//        words = "南京西路938号(南京西路泰兴路口)";
        
//        words = "向阳河路889号";
//        words = "广元西路317号金山商务楼3楼f座";
//        words = "顺昌路622号216室";
//        words = "广元西路317号金山商务楼3楼f座";
//        words = "浦三路159号三楼近临沂路";
//       words = "南翔镇古猗园路158弄5号401室";
//       words = "南京东路800号市百一店东楼17层";
//       words = "江东北路218号新城市假日广场(龙园南路口)";
//       words = "白下区中山路55号新华大厦33楼A座(近中山东路)";
//       words = "江苏南京白下区淮海路68号苏宁电器一楼3C服务中心 ";
//       words = "南京西路1376号上海商城内(铜仁路)";
        
//        words = "罗山路1609号";
//        words = "南京鼓楼广州路173号";
//        words = "南京市鼓楼（原下关区）幕府西路118号";
//        words = "河北省邢台市大曹庄管理区大曹庄乡";
//        words = "河北省承德市宽城满族自治县";
//        words = "河北省承德市宽城满族自治县宽城镇";
//        words = "河北省保定市涞水县城区社区管理办公室";
//        words = "河北省保定市南市区南大园乡";
       words = "河北省石家庄市灵寿县青同镇";
//        words = "北京市市辖区";
//        words = "河北省承德市市辖区";
//        words = "北京市朝阳区孙河地区";
//        words = "上海黄浦黄浦江";
//        words = "鼓楼区新模范马路地铁口200米";
//      words = "陕西南路145号(近南昌路)";
//        words = "新疆维吾尔自治区伊犁哈萨克自治州伊宁市";
//        words = "东城区东直门内大街253号";
//        words = "鼓楼区湖南路27号103室(近八佰伴)";
        words = "东山步行街(近竹山路)";
        words = "南京南京市区天元西路199号(近静淮路)";
        words = "金沙江西路";
        
        System.out.println(words);
        LocalInfo _data = new LocalInfo();
        DSComposite _info = new DSComposite();
        AddressDataBase db = new AddressDataBase(); 
//        Extractor.parseAddress(words, _data, db);
        
        AddressExtractor extractor  = new AddressExtractor();
        EvalutionValue mode = new EvalutionValue();
        extractor.parseAddress(words, _info, db,mode);
        
//        Extractor.tryDistrict(words, _data);
        
//        Extractor.tryAddress(words, _data,db);
        */
        /*
        Extractor.extractAddress(words,_data,db);        
        
        List<String> indexs = new ArrayList<String>();
        int queryByRoad = 0;
        if(!_data.county.isEmpty()){
             indexs = db.queryWords(_data.county);
        }else{
            if(!_data.township.isEmpty()){
                indexs = db.queryWords(_data.township);
                queryByRoad = 1;
            }else{
                if(!_data.countyRoad.isEmpty()){
                    indexs = db.queryWords(_data.countyRoad);
                    queryByRoad = 1;
                }
            }
        }
        for(int i=0;i<indexs.size();i++){
            System.out.println(indexs.get(i));
        }
        ServiceAddress addr = new ServiceAddress();
        String baiDU = "";
        if(indexs.size() == 1){
            _data.province = indexs.get(0).split(",")[0];
            if(indexs.size()>1)
                _data.city = indexs.get(0).split(",")[1];
            if(indexs.size()>2)
                _data.county = indexs.get(0).split(",")[2];
            if(queryByRoad == 1){
                baiDU =  addr.parseByBaiDu(words);
            }
        }else{
            List<String> lstAddress = new ArrayList<String>();
            baiDU =  addr.parseByBaiDu(words);
        }
        System.out.println(indexs.size() + "  " + baiDU );
        if(!baiDU.isEmpty()){
            String[] baiduVals = baiDU.split(",");
            if(baiduVals.length>2)
                _data.province = baiduVals[2];
            if(baiduVals.length>3)
                _data.city = baiduVals[3];
            if(baiduVals.length>4)
                _data.county = baiduVals[4];            
        }
        */
        /*
        System.out.println(indexs);
        String [] _indexs = indexs.split(":");
        for(int i=0;i<_indexs.length;i++){
            String[] vals = _indexs[i].split(",");
            if(vals.length == 2)       
                db.getRecordByCode(vals[0], Integer.parseInt(vals[1]));
        }        
        */
        
        
//        String code = db.getCodeByName("黄浦区",2);
//        System.out.print(db.getCodeByName("古丈县",2));
//        db.getRecordByCode(db.getCodeByName(_data.countyRoad,20), 20);
//        db.getRecordByCode("184",3);
        //System.out.println(val);
//        Extractor.diffAddress("景德镇青云谱路2-48号双号",_data);
//        Extractor.diffAddress("枫树巷,0559,245000",_data);
           

//AddressTown data = new AddressTown();
        
       
//        words = "湖南省湘西土家族苗族自治州湘西土家族苗族自治州古丈县景德镇青云谱路2-48号双号";
        //myStr = "新疆维吾尔自治区伊犁哈萨克自治州伊宁市";        
//        _data = new AddressTown();
//        Extractor.diffAddress(words, _data);   
//        Extractor.diffAddress(words,_data);
        /*
        System.out.println(_data.province  + ":" + _data.city + ":" + _data.county + ":" +                
                _data.township + ":" + _data.village + ":" +  
                 _data.Road + ":" + _data.RoadNo +  ":" + 
                ":" +  _data.building + ":" +  _data.buildingNo + ":" +  _data.floor + ":" +  _data.room + ":" +  _data.roomNo);
        */
    }    
    
}
