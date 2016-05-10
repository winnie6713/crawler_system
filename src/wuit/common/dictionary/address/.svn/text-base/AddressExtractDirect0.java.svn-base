/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.LocalInfo;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;

/**
 *
 * @author lxl
 */
public class AddressExtractDirect0 {
    
    class VerifyDataInfo{
        String province = "";
        String city = "";
        String county = "";
        String phone = "";
        String post = "";
    }
    
    AbsoluteKeywords keywords = null;
    ReferenceKeywords refKeywords = null;
    
    public AddressExtractDirect0(){
        refKeywords = new ReferenceKeywords();
        keywords = new  AbsoluteKeywords();
    }
        
    public void parseAddress(String address,DSComposite info,AddressDataBase db,EvalutionValue mode){
        LocalInfo data = info.local;
        address = extractReference(address,data);
        
        List<KeyValue> list = new ArrayList<KeyValue>();
        extractItems(data,address,list,db);                                             //直接解析地址
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).value + ":" + list.get(i).order);
        }
//        verifyDataLevel(data,list,db);
        System.out.println("verifyDataLevel !!!!!!!!!!!!!!!!!" + address);
        dispData(data);    
        mode.address_1 =  Evaluation.evaluteLocalInfo(data); 
        info.stat.parse = 1;
//        dispData(data);
//        System.out.println("address extractor parseAddress");
    }
    
    private void extractItems(LocalInfo data,String address,List<KeyValue> comlist,AddressDataBase db){
        List<KeyValue> list = new ArrayList<KeyValue>();
        KeyValueSort sort = new KeyValueSort();
        matchValues(address,keywords.keysAll, list);                            //按地址关键词分组
        Collections.sort(list,sort);                                            //按出现的先后次序排序
        int start = 0;
        for(int i=0;i<list.size();i++){
            String val = address.substring(start,list.get(i).start + list.get(i).key.length());
            start = list.get(i).start + list.get(i).key.length();
            list.get(i).value = val;
            list.get(i).order = keywords.getMinLevelByKey(list.get(i));                     //匹配关键词的地址级别，取级别值小的级别
            System.out.println(list.get(i).start + ":" + list.get(i).key + ":" + val + " " + list.get(i).order);
        }
        combine(data,list,db);
        /*
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
                        word2.order < level && level <= word3.order ||          //768|727
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
        */
                
        String _val = address;
        for(int i=0;i<comlist.size();i++){
            _val = _val.replace(comlist.get(i).value, "");                       //计算剩余地址字符串
        }                
    }
    
    private void combine(LocalInfo data,List<KeyValue> list,AddressDataBase db){
        for(int i=0;i<list.size();i++){
            KeyValue valKey = new KeyValue();             
            valKey.value = list.get(i).value;
            valKey.key = list.get(i).key;
            valKey.order = list.get(i).order;
            valKey.end = list.get(i).end;
            //如果第一项的值是关键词
//            if(i==0 && valKey.value.equals(valKey.key) && i+1 < list.size()){
//                list.get(i+1).value = valKey.value + list.get(i+1).value;
//                continue;
//            }
            /*
            if(!valKey.value.equals(valKey.key)){                               //值与关键词不等 //valKey.key != valKey.value;                
                oldOrder = combineValue(data,list,valKey,oldOrder,i,db);
            }else{                          // valKey.key = valKey.value
                //LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db
                oldOrder = combineKey(data,list,valKey,oldOrder,i,db);
            }
            */
            ///////////////////////////////////////////////
            /*
            if(!valKey.value.equals(valKey.key)){
                if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                    oldOrder = combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    oldOrder = combineValueCountyNoNull(data,list,valKey,oldOrder,i,db);
                }
            }else{
                if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                    oldOrder = combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    oldOrder = combineKeyCountyNoNull(data,list,valKey,oldOrder,i,db);
                }                
            }
            */
            //////////////////////////////////////////////
            switch(valKey.order){
                case -1:
                    break;
                case 0:
                    combine0(data,list,valKey,i,db);
                    break;
                case 1: 
                    combine1(data,list,valKey,i,db);
                    break;
                case 2:
                    combine2(data,list,valKey,i,db);                    
                    break;
                case 3:
                    combine3(data,list,valKey,i,db);
                    break;
                case 4:
                    combine4(data,list,valKey,i,db);
                    break;
                case 5:
                    combine5(data,list,valKey,i,db);
                    break;                    
                case 6:
                    combine6(data,list,valKey,i,db);
                    break;
                case 7:
                    combine7(data,list,valKey,i,db);
                    break;
                case 8:
                    combine8(data,list,valKey,i,db);
                    break;
                case 9:
                    combine9(data,list,valKey,i,db);
                    break;
            }
        }
            /*
           //如果当前的次序大于前一项的次序
            if(valKey.order>oldOrder){
                comlist.add(valKey);
                oldOrder = valKey.order;
                continue;              
            }
            if(valKey.order == oldOrder){
                if(oldOrder>2){
                    comlist.get(comlist.size()-1).value = comlist.get(comlist.size()-1).value + valKey.value;
                    comlist.get(comlist.size()-1).order = valKey.order;
                }else{
                    int order = getNextLevelByKey(valKey.key,oldOrder);
                    if(order>oldOrder){
                        valKey.order = order;
                        comlist.add(valKey);
                        oldOrder = order;
                    }
                }
            }
            if(valKey.order<oldOrder){
                if(comlist.size()==1){
                    comlist.get(comlist.size()-1).value = comlist.get(comlist.size()-1).value + valKey.value;
                    comlist.get(comlist.size()-1).order = valKey.order;
                    comlist.get(comlist.size()-1).key = valKey.key;
                    oldOrder = valKey.order;
                    continue;
                }
                if(i+1 == list.size()){
                    int orderOldParent = comlist.get(comlist.size()-2).order;
//                        int order = getNextLevelByKey(valKey.key,orderOldParent);
                    if(valKey.order>orderOldParent){
                        comlist.get(comlist.size()-1).value = comlist.get(comlist.size()-1).value + valKey.value;
                        comlist.get(comlist.size()-1).order = valKey.order;
                        comlist.get(comlist.size()-1).key = valKey.key;
                    }else{
                        valKey.order = orderOldParent;
                        comlist.add(valKey);
                    }
                }
                if(i+1<list.size()){ 
                    if(list.get(i+1).order == oldOrder){
                        comlist.get(comlist.size()-1).value = comlist.get(comlist.size()-1).value + valKey.value;
                    }else{
                        if(list.get(i+1).order > oldOrder)
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        else{
                            int orderOldParent = comlist.get(comlist.size()-2).order;
                            int order = getNextLevelByKey(valKey.key,orderOldParent);
                            if(order < list.get(i+1).order){

                            }
                            //if(order >= comlist.get(comlist.size()-1).order){
                            //    list.get(i+1).value = valKey.value + list.get(i+1).value;
                            //    list.get(i+1).order = order;
                            //}
                            valKey.order = order;
                            comlist.get(comlist.size()-1).value = comlist.get(comlist.size()-1).value + valKey.value;
                            oldOrder = valKey.order;
                            comlist.get(comlist.size()-1).key = valKey.key;
                            comlist.get(comlist.size()-1).order = oldOrder;
                        }
//                            System.out.println(valKey.value + ":" + valKey.order);
                    }
                }
            }
            
        }
        
        oldOrder = -1;
        for(int i=0; i<comlist.size();i++){
            if(comlist.get(i).order>oldOrder)
                oldOrder = comlist.get(i).order;
            else{
                int order = getNextLevelByKey(comlist.get(i).key,oldOrder);
                if(order>comlist.get(i).order)
                    comlist.get(i).order = order;
            }
        }
        */
    }
    /*
    private void combineNo(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        switch(oldOrder){
            case -1:
                break;
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;                
        }
    }
    */
    private int Evaluate(LocalInfo data,VerifyDataInfo info,AddressDataBase db){
        int flag = 0;        
        if(data.province.equals("")){
            data.province = info.province;
            if(data.city.equals("")){
                data.city = info.city;
                flag = 1;
                if(data.county.equals("")){
                    data.county = info.county;
                    flag = 1;
                }
            }else if(data.city.equals(info.city)){
                if(data.county.equals("")){
                    data.county = info.county;
                    flag = 1;
                }
            }
        }else if(data.province.equals(info.province)){
            if(data.city.equals("")){
                data.city = info.city;
                if(data.county.equals("")){
                    data.county = info.county;
                    flag = 1;
                }
            }else if(data.city.equals(info.city)){
                if(data.county.equals("")){
                    data.county = info.county;
                    flag = 1;
                }
            }
        }
        
        if(info.province.equals("") && info.city.equals("") && !info.county.equals("")){
            List<String> list = new ArrayList<String>();
            list = db.verifyAddress(info.county);
            if(!list.isEmpty()){
                for(int i=0;i<list.size();i++){
                    String[] vals = list.get(i).split(",");
                    if(!data.city.equals("")){
                        if(vals.length>2 && vals[0].equals(data.province) && vals[1].equals(data.city)){
                            data.county = vals[2];
                            flag = 1;
                        }
                    }else{
                        if(vals.length>2 && vals[0].equals(data.province)){
                            data.city = vals[1];
                            data.county = vals[2];
                            flag = 1;
                        }
                    }
                }
            }
        }
        
        return flag;
    }
    
    private void combine0(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }
                break;
            case 0:
                if(valKey.key.equals(valKey.value)){
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }
                break;
            case 1:
                if(valKey.key.equals(valKey.value)){
//                    if(i+1<list.size())
//                        list.get(i+1).value = valKey.value + list.get(i+1).value;                    
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }
                break;
            case 2: 
                if(valKey.key.equals(valKey.value)){
                    if(data.county.indexOf(valKey.value)>0){
                        
                    }
                }else{
                    
                }
                break;
            case 3:
                break;
            case 4:
                if(valKey.key.equals(valKey.value)){
                    valKey.value = getDataValueByOrder(4,data) + valKey.value;
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    
                }
                break;
            case 5:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }                     
                }else{
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }                     
                }                
                break;
            case 6:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }                     
                }else{
                    
                }
                break;
            case 7:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,4,db);
                }                 
                break;
            case 8:
                break;
            case 9:
                break;                
        }
    }
    
    private void combine1(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                break;
            case 0:
                if(valKey.key.equals(valKey.value)){
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }                
                break;
            case 1:
                if(valKey.key.equals(valKey.value)){
                    //combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    valKey.order = keywords.getNextLevelByKey(valKey.key,2,valKey.order);
                    if(valKey.order>2){
                        setDataByKeyValue(0,valKey,data);
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                }                
                break;
            case 2:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }                
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;                
        }
    }
    
    private void combine2(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(verifyProvinceCityCounty(data,valKey,db)==0){
                    valKey.order = keywords.getNextLevelByKey(valKey.key,valKey.order,-1);
                    setDataByKeyValue(0,valKey,data);
                }
//                combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                break;
            case 0:
                if(valKey.key.equals(valKey.value)){
//                    combineKeyCountyIsNull(data,list,valKey,oldOrder,i,db);
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }                
                break;
            case 1:
                if(valKey.key.equals(valKey.value)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 2:
                if(valKey.value.equals(valKey.key)){
                    String _val = getDataValueByOrder(2,data);
                    if(_val.indexOf(valKey.value)>0){
                        break;
                    }
                    
                    valKey.order = keywords.getNextLevelByKey(valKey.key,2,valKey.order);
                    if(valKey.order>2){
                        setDataByKeyValue(0,valKey,data);
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }                    
                }else{
                    verifyProvinceCityCounty(data,valKey,db);
                    
                    valKey.order = keywords.getNextLevelByKey(valKey.key,2,valKey.order);
                    if(valKey.order>2){                        
                        setDataByKeyValue(1,valKey,data);
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;                        
                    }
                }              
                break;
            case 3:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    valKey.order = keywords.getNextLevelByKey(valKey.key,3,valKey.order);
                    if(valKey.order>3){
                        setDataByKeyValue(0,valKey,data);
                    }
                }                 
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        int order = list.get(i+1).order;
                        if(order == 4){
                            valKey.value = getDataValueByOrder(4,data) + valKey.value;
                            //clearDataValueByOrder(4,data);
                            setDataByKeyValue(0,valKey,data);
                        }else if(order>4){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }
                    }                    
                }
                break;
            case 5:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                        break;
                    }
                    valKey.order = keywords.getNextLevelByKey(valKey.key,5,valKey.order);
                    if(valKey.order>5){
                        setDataByKeyValue(0,valKey,data);
                    }
                }
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                        break;
                    }
                }                
                break;
            case 9:
                break;                
        }
    }
    
    private void combine3(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                }                
                break;
            case 0:
                break;
            case 1:
                if(valKey.key.equals(valKey.value)){

                }else{
                    verifyProvinceCityCounty(data,valKey,db);
//                    combineValueCountyIsNull(data,list,valKey,oldOrder,i,db);
                }                 
                break;
            case 2:
                if(valKey.value.equals(valKey.key)){
                    if(valKey.key.length()>1){
                        setDataByKeyValue(0,valKey,data);
                    }else{
                        
                    }                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 3:
                if(valKey.key.equals(valKey.value)){
                    
                }else{
                    valKey.value = getDataValueByOrder(3,data) + valKey.value;
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size()){
                        valKey.value = getDataValueByOrder(4,data) + valKey.value;
                        clearDataValueByOrder(4,data);
                        setDataByKeyValue(0,valKey,data);                        
                    }else{
                        valKey.value = getDataValueByOrder(4,data) + valKey.value;
                        clearDataValueByOrder(4,data);
                        setDataByKeyValue(0,valKey,data);
                    }                    
                }else{
                    if(i+1<list.size()){
                        
                    }else{
                        valKey.value = getDataValueByOrder(4,data) + valKey.value;
                        clearDataValueByOrder(4,data);
                        setDataByKeyValue(0,valKey,data);
                    }
                    
                }                
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size()){
                        if(list.get(i+1).order >3 && list.get(i+1).order<7){
                            valKey.value = getDataValueByOrder(7,data) + valKey.value;
                            clearDataValueByOrder(7,data);
                            setDataByKeyValue(0,valKey,data);                            
                        }
                    }
                }else{
                    
                }
                break;
            case 8:
                break;
            case 9:
               
                break;                
        }
    }
    
    private void combine4(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.value.length()<3){        //天山 4 :路 5
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    break;
                }
                if(valKey.key.equals(valKey.value)){
                    list.get(i+1).value = valKey.value + list.get(i+1).value;
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,4,db);
                }
                break;
            case 0:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,4,db);
                }                  
                break;
            case 1:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        if(list.get(i+1).order>=2){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                            break;
                        }
                    }
                    verifyProvinceCityCountyByVillage(data,valKey.value,4,db);
                }                 
                break;
            case 2:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                    break;
                }else{
                    
                }
                setDataByKeyValue(0,valKey,data);
                break;
            case 3:
                if(valKey.key.equals(valKey.value)){
                    if(i+1<list.size()){
                        int order = list.get(i+1).order;
                        if(order>4){
                           valKey.value = getDataValueByOrder(3,data) + valKey.value;
                           clearDataValueByOrder(3,data);
                           setDataByKeyValue(0,valKey,data);                             
                        }
                    }
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 4:
               if(valKey.key.equals(valKey.value)){
                   if(i+1<list.size()){
                       int order = list.get(i+1).order;
                       if(order>4){
                           valKey.value = getDataValueByOrder(4,data) + valKey.value;
                           clearDataValueByOrder(4,data);
                           setDataByKeyValue(0,valKey,data);                           
                       }
                   }
               }else{
                   if(i+1<list.size()){
                       int order = list.get(i+1).order;
                       if(order>=4){
                           valKey.value = getDataValueByOrder(4,data) + valKey.value;
                           clearDataValueByOrder(4,data);
                           setDataByKeyValue(0,valKey,data);                           
                       }
                   }else{
                        valKey.value = getDataValueByOrder(4,data) + valKey.value;
                        clearDataValueByOrder(4,data);
                        setDataByKeyValue(0,valKey,data);                       
                   }                   
               }                
                break;
            case 5:
               if(valKey.key.equals(valKey.value)){

               }else{
                   if(i+1<list.size()){
                       list.get(i+1).value = valKey.value + list.get(i+1).value;
                   }                 
               }                 
                break;
            case 6:
               if(valKey.key.equals(valKey.value)){

               }else{
                   if(i+1<list.size()){
                       int order = list.get(i+1).order;
                       if(order>6){
                          list.get(i+1).value = valKey.value + list.get(i+1).value;
                       }
                   }else{
                       int order = keywords.getNextLevelByKey(valKey.key,6,valKey.order);
                       if(order>6){
                           valKey.order = order;
                           setDataByKeyValue(0,valKey,data);
                       }
                   }                  
               }                 
                break;
            case 7:
               if(valKey.key.equals(valKey.value)){
                   
               }else{
                    valKey.value = getDataValueByOrder(7,data) + valKey.value;
                    clearDataValueByOrder(7,data);
                    setDataByKeyValue(0,valKey,data);                   
               }                
                break;
            case 8:
                break;
            case 9:
                break;                
        }
    }
    
    private void combine5(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        if(list.get(i+1).value.equals(list.get(i+1).key)){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }else{
                            verifyProvinceCityCountyByRoad(data,valKey,db);
                        }
                    }else{
                        verifyProvinceCityCountyByRoad(data,valKey,db);
                    }
                }
                break;
            case 0:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByRoad(data,valKey,db);
                }                
                break;
            case 1:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByRoad(data,valKey,db);
                }                
                break;
            case 2:
                if(valKey.value.length()<=2){                   //中山（中山区）2  大道5
                    valKey.value = list.get(i-1).value + valKey.value;
                    clearDataValueByOrder(2,data);
                    setDataByKeyValue(0,valKey,data); 
                    break;
                }                
                if(valKey.value.equals(valKey.key)){
                     
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 3:
                
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 4:                
                if(valKey.value.length()<=2){                   //中山4  西路5
                    valKey.value = getDataValueByOrder(4,data) + valKey.value;
                    clearDataValueByOrder(4,data);
                    setDataByKeyValue(0,valKey,data); 
                    break;
                }
                
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size()){
                        int order = list.get(i+1).order;
                        if(order>=4){
                            valKey.value = getDataValueByOrder(4,data) + valKey.value;
                            clearDataValueByOrder(4,data);
                            setDataByKeyValue(0,valKey,data);                            
                        }
                    }                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 5:
                if(valKey.value.equals(valKey.key)){
                    

                }else{
                    valKey.order = keywords.getNextLevelByKey(valKey.key,5,valKey.order);
                    if(valKey.order>5){
                        setDataByKeyValue(0,valKey,data);
                    }else if(valKey.order == 5){
                        setDataByKeyValue(1,valKey,data);
                    }
                }                
                break;
            case 6:
                break;
            case 7:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size()){
                        int order = list.get(i+1).order;
                        if(order<7){
                            valKey.value = getDataValueByOrder(7,data) + valKey.value;
                            clearDataValueByOrder(7,data);
                            setDataByKeyValue(1,valKey,data);
                        }
                    }                    
                }else{
                    valKey.value = getDataValueByOrder(7,data) + valKey.value;
                    clearDataValueByOrder(7,data);
                    setDataByKeyValue(1,valKey,data);                    
                }
                break;
            case 8:
                break;
            case 9:
                break;                
        }
    }
    
    private void combine6(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                break;
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 5:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 6:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(1,valKey,data);
                }
                /*
                    valKey.order = getNextLevelByKey(valKey.key,6,valKey.order);
                    if(valKey.order>6){
                        setDataByKeyValue(0,valKey,data);
                    }else if(valKey.order == 6){
                        setDataByKeyValue(1,valKey,data);
                    }
                */
                break;
            case 7:
                if(valKey.key.equals(valKey.value)){
                    
                }else{
                    valKey.order = keywords.getNextLevelByKey(valKey.key,7,valKey.order);
                    if(valKey.order>7){
                        setDataByKeyValue(0,valKey,data);
                    }else if(valKey.order == 7){
                        setDataByKeyValue(1,valKey,data);
                    }                    
                }
                break;
            case 8:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                        break;
                    }else{
                        valKey.order = 9;
                        setDataByKeyValue(0,valKey,data);
                    }
                }                
                break;
            case 9:
                break;                
        }
    }
    
    private void combine7(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                }else{
                    if(i+1<list.size()){
                        if(list.get(i+1).order == 6){
                            setDataByKeyValue(0,valKey,data);
                        }else{
                            verifyProvinceCityCountyByVillage(data,valKey.value,7,db);
                        }
                    }
                    
                }              
                break;
            case 0:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,7,db);
                }                  
                break;
            case 1:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    verifyProvinceCityCountyByVillage(data,valKey.value,7,db);
                }                
                break;
            case 2:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size()){
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }else{
                        
                    }
                        
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                 
                break;
            case 3:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size())
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                }else{
                    
                }                
                break;
            case 5:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                
                break;
            case 6:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 7:
                if(valKey.value.equals(valKey.key)){
                    if(i+1<list.size()){
                        if(list.get(i+1).order == 6){
                            data.building = data.building + valKey.value;
                        }
                    }
                }else{
                    valKey.order = keywords.getNextLevelByKey(valKey.key,7,valKey.order);
                    if(valKey.order>7){
                        setDataByKeyValue(0,valKey,data);
                    }else if(valKey.order == 7){
                        setDataByKeyValue(1,valKey,data);
                    } 
                }
                break;
            case 8:
                valKey.order = keywords.getNextLevelByKey(valKey.key,8,valKey.order);
                    if(valKey.order>8){
                        setDataByKeyValue(0,valKey,data);
                    }else if(valKey.order == 8){
                        setDataByKeyValue(1,valKey,data);
                    }else{                    
                        valKey.order = keywords.getNextLevelByKey(valKey.key,valKey.order,valKey.order);
                        if(valKey.order>8){
                            setDataByKeyValue(0,valKey,data);
                        }else if(valKey.order == 8){
                            setDataByKeyValue(1,valKey,data);
                        }
                    }
                
                break;
            case 9:
                break;                
        }
    }
    
    private void combine8(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                 
                break;
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                 
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }
                break;
            case 8:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(1,valKey,data);
                }                
                break;
            case 9:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    valKey.value = getDataValueByOrder(9,data) + valKey.value;
                    clearDataValueByOrder(9,data);
                    setDataByKeyValue(0,valKey,data);                     
                }                 
                break;                
        }
    }
    
    private void combine9(LocalInfo data,List<KeyValue> list, KeyValue valKey,int i,AddressDataBase db){
        int oldOrder = getDataMaxLevel(data);
        switch(oldOrder){
            case -1:
               
                break;
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                 
                break;
            case 4:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                  
                break;
            case 5:
                break;
            case 6:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                
                break;
            case 7:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                  
                break;
            case 8:
                if(valKey.value.equals(valKey.key)){
                    
                }else{
                    setDataByKeyValue(0,valKey,data);
                }                 
                break;
            case 9:
                
                break;                
        }
    }    
//////////////////////////////////////////////////////////////////////    
//valKey.key <> valKey.value
    /*
    private int combineValue(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        if(oldOrder < valKey.order){
            if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                switch(valKey.order){
                    case 0:case 1:case 2:
                        if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                            verifyProvinceCityCounty(data,valKey.value,db);
                            if(!data.province.isEmpty())    oldOrder = 0;
                            if(!data.city.isEmpty())        oldOrder = 1;
                            if(!data.county.isEmpty())      oldOrder = 2;
                            oldOrder = valKey.order;
                        }else{
                            if(oldOrder <= valKey.order)        valKey.order = getNextLevelByKey(valKey.key,valKey.order,valKey.order);
                            else                                valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }
                        break;
                    case 3:case 4:
                        verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                        oldOrder = valKey.order;
                        break;
                    case 5:
                        verifyProvinceCityCountyByRoad(data,valKey.value,db);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(oldOrder>valKey.order){
                            String val = getDataValueByOrder(oldOrder,data);
                            valKey.value = val + valKey.value ;
                            clearDataValueByOrder(oldOrder,data);                         
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }                        
                        break;
                    case 6:case 7:case 8:case 9:
                        verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                        oldOrder = valKey.order;
                        break;
                }
            }else{
                switch(valKey.order){
                    case 0: case 1:case 2:
                    break;
                    case 3:case 4:
//                    break;
                    case 5:
//                    break;
                    case 6:case 7: case 8:case 9:
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                        break;
                }
            }
            
           /*
           if(valKey.order <= 2){                                      //如果次序小于等于2，则验证是否是，省、地、县三级
               if(data.province.isEmpty() || data.county.isEmpty() || data.county.isEmpty()){
                   verifyProvinceCityCounty(data,valKey.value,db);
                   if(!data.province.isEmpty())    oldOrder = 0;
                   if(!data.city.isEmpty())        oldOrder = 1;
                   if(!data.county.isEmpty())      oldOrder = 2;
                   oldOrder = valKey.order;
               }else{
                   if(oldOrder <= valKey.order)        valKey.order = getNextLevelByKey(valKey.key,valKey.order,valKey.order);
                   else                                valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                   setDataByKeyValue(0,valKey,data);
                   oldOrder = valKey.order;
               }
           }else{
               if(data.province.isEmpty() || data.county.isEmpty() || data.county.isEmpty()){
                   if(valKey.order == 5){
                       verifyProvinceCityCountyByRoad(data,valKey.value,db);
                       if(!data.province.isEmpty())    oldOrder = 0;
                       if(!data.city.isEmpty())        oldOrder = 1;
                       if(!data.county.isEmpty())      oldOrder = 2;
                       if(oldOrder>valKey.order){
                            String val = getDataValueByOrder(oldOrder,data);
                            valKey.value = val + valKey.value ;
                            clearDataValueByOrder(oldOrder,data);                         
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                       }else{
                           setDataByKeyValue(0,valKey,data);
                           oldOrder = valKey.order;
                       }
                   }else{
                       verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                       oldOrder = valKey.order;
                   }                            
               }else{
                   setDataByKeyValue(0,valKey,data);
                   oldOrder = valKey.order;                            
               }
           }
           */
    /*
    }else if(oldOrder == valKey.order){
           if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                switch(valKey.order){
                    case 0:case 1:case 2:
                        valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                        if(oldOrder >valKey.order){
                            if(i+1<list.size()){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                                setDataByKeyValue(0,valKey,data);
                                oldOrder = valKey.order;
                            }else
                                valKey.order = valKey.order + 1;
                        }else if(oldOrder == valKey.order){
                            int flag = verifyProvinceCityCounty(data,valKey.value,db);
                            if(!data.province.isEmpty())    oldOrder = 0;
                            if(!data.city.isEmpty())        oldOrder = 1;
                            if(!data.county.isEmpty())      oldOrder = 2;
                            if(flag == 0){
                                 if(i+1<list.size()){
                                     list.get(i+1).value = valKey.value + list.get(i+1).value;
                                 }else
                                     valKey.order = valKey.order + 1;
                            }
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;                   
                        }
                        break;
                    case 3:case 4:
                    break;
                    case 5:
                    break;
                    case 6:case 7:case 8:case 9:
                }
           }else{
                switch(valKey.order){
                    case 0:case 1:case 2:
                        valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                        if(oldOrder >valKey.order){
                            if(i+1<list.size()){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                                setDataByKeyValue(0,valKey,data);
                                oldOrder = valKey.order;
                            }else
                                valKey.order = valKey.order + 1;
                        }else if(oldOrder == valKey.order){
                            int flag = verifyProvinceCityCounty(data,valKey.value,db);
                            if(!data.province.isEmpty())    oldOrder = 0;
                            if(!data.city.isEmpty())        oldOrder = 1;
                            if(!data.county.isEmpty())      oldOrder = 2;
                            if(flag == 0){
                                 if(i+1<list.size()){
                                     list.get(i+1).value = valKey.value + list.get(i+1).value;
                                 }else
                                     valKey.order = valKey.order + 1;
                            }
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;                   
                        }                        
                    break;
                    case 3:case 4:
                        /*
                        if(data.village.isEmpty()){
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;
                        }else{
                             setDataByKeyValue(1,valKey,data);
                             oldOrder = valKey.order;                            
                        }
                        */
    //                break;
    //                case 5:case 6:case 7:case 8:case 9:
    //            }               
    //       }
           
           /*
           if(valKey.order <= 2){
               valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
               if(oldOrder >valKey.order){
                   if(i+1<list.size()){
                       list.get(i+1).value = valKey.value + list.get(i+1).value;
                       setDataByKeyValue(0,valKey,data);
                       oldOrder = valKey.order;
                   }else                       valKey.order = valKey.order + 1;
               }else if(oldOrder == valKey.order){int flag = verifyProvinceCityCounty(data,valKey.value,db);
                   if(!data.province.isEmpty())    oldOrder = 0;
                   if(!data.city.isEmpty())        oldOrder = 1;
                   if(!data.county.isEmpty())      oldOrder = 2;
                   if(flag == 0){
                        if(i+1<list.size()){       list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }else                      valKey.order = valKey.order + 1;}
               }else{
                   setDataByKeyValue(0,valKey,data);
                   oldOrder = valKey.order;                   
               }
           }else{
               setDataByKeyValue(1,valKey,data);
               oldOrder = valKey.order;                         
           }
           */
/*      
}else{//oldOrder > valKey.order
           String val = "";
           if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                switch(valKey.order){
                    case 0:case 1:case 2:
                        //valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
//                        if(oldOrder<=5)
                        {
//                            valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
//                                setDataByKeyValue(0,valKey,data);
//                                oldOrder = valKey.order;                            
//                        }else{
                            if(oldOrder>2 ){
                                String _val = getDataValueByOrder(oldOrder,data);
                                val = _val + valKey.value;
                            }else{
                                val = valKey.value;
                            }
                            int flag = verifyProvinceCityCounty(data,val,db);
                            if(flag == 1 && oldOrder>2)
                                clearDataValueByOrder(oldOrder,data);
                            if(!data.province.isEmpty())    oldOrder = 0;
                            if(!data.city.isEmpty())        oldOrder = 1;
                            if(!data.county.isEmpty())      oldOrder = 2; 
                            if(valKey.order >= getDataMaxLevel(data)){
                                valKey.value = val;
                                setDataByKeyValue(0,valKey,data);
                                oldOrder = valKey.order;
                            }else{
                                if(flag == 0){
                                     if(i+1<list.size()){
                                         list.get(i+1).value = valKey.value + list.get(i+1).value;
                                     }else
                                         valKey.order = valKey.order + 1;
                                }
                            }
                        }
                        /*
                        
                        if(oldOrder >valKey.order){
                            if(i+1<list.size()){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                                setDataByKeyValue(0,valKey,data);
                                oldOrder = valKey.order;
                            }else
                                valKey.order = valKey.order + 1;
                        }else if(oldOrder == valKey.order){
                            flag = verifyProvinceCityCounty(data,valKey.value,db);
                            if(!data.province.isEmpty())    oldOrder = 0;
                            if(!data.city.isEmpty())        oldOrder = 1;
                            if(!data.county.isEmpty())      oldOrder = 2;
                            if(flag == 0){
                                 if(i+1<list.size()){
                                     list.get(i+1).value = valKey.value + list.get(i+1).value;
                                 }else
                                     valKey.order = valKey.order + 1;
                            }
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;                   
                        }
                    */
    /*
                        break;
                    case 3:
                        if(data.township.isEmpty()){
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;                            
                        }
                    break;
                    case 4:
                        if(data.village.isEmpty()){
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;                            
                        }else{
                         
                        }                     
                    break;
                    case 5:
                        val = getDataValueByOrder(oldOrder,data) + valKey.value;
                        verifyProvinceCityCountyByRoad(data,val,db);
                        int _old = oldOrder;
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(_old>valKey.order){
                             valKey.value = val + valKey.value ;
                             clearDataValueByOrder(_old,data);                         
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }                         
                    break;
                    case 6:case 7:case 8:case 9:
                        break;
                }
           }else{
                int order = -1;
                if(i+1<list.size()){
                    order = list.get(i+1).order;
                }
                switch(valKey.order){
                    case 0:case 1:case 2:
                        val = getDataValueByOrder(oldOrder,data) + valKey.value;
                        int flag = verifyProvinceCityCounty(data,val,db);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(flag == 0){
                             if(i+1<list.size()){
                                 list.get(i+1).value = valKey.value + list.get(i+1).value;
                             }else
                                 valKey.order = valKey.order + 1;
                        }                    
                        break;
                    case 3:case 4:
                        if(order != -1){
                            if(order>oldOrder){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                            }else if(order <= oldOrder){
                                 val = getDataValueByOrder(oldOrder,data);
                                 valKey.value = val + valKey.value;
                                 clearDataValueByOrder(oldOrder,data);
                                 setDataByKeyValue(1,valKey,data);
                                 oldOrder = valKey.order;
                            }              
                        }else{
                            valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                             if(oldOrder < valKey.order){
                                 setDataByKeyValue(0,valKey,data);
                                 oldOrder = valKey.order;                          
                             }
                        }                    
                        break;
                    case 5:
                        val = getDataValueByOrder(oldOrder,data) + valKey.value;
                        verifyProvinceCityCountyByRoad(data,val,db);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(oldOrder>valKey.order){
                             valKey.value = val + valKey.value ;
                             clearDataValueByOrder(oldOrder,data);                         
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;
                        }else{
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }                     
                        break;
                    case 6:case 7:case 8:case 9:
                        if(order != -1){
                            if(order>oldOrder){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                            }else if(order <= oldOrder){
                                 val = getDataValueByOrder(oldOrder,data);
                                 valKey.value = val + valKey.value;
                                 clearDataValueByOrder(oldOrder,data);
                                 setDataByKeyValue(1,valKey,data);
                                 oldOrder = valKey.order;
                            }              
                        }else{
                            valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                             if(oldOrder < valKey.order){
                                 setDataByKeyValue(0,valKey,data);
                                 oldOrder = valKey.order;                          
                             }
                        }                        
                        break;
                }           
           }
    */
           /*
           if(valKey.order<=2){
               String val = getDataValueByOrder(oldOrder,data) + valKey.value;
                int flag = verifyProvinceCityCounty(data,val,db);
                if(!data.province.isEmpty())    oldOrder = 0;
                if(!data.city.isEmpty())        oldOrder = 1;
                if(!data.county.isEmpty())      oldOrder = 2;
                if(flag == 0){
                     if(i+1<list.size()){
                         list.get(i+1).value = valKey.value + list.get(i+1).value;
                     }else
                         valKey.order = valKey.order + 1;
                }               
           }else{
               if(valKey.order == 5){
                   String val = getDataValueByOrder(oldOrder,data) + valKey.value;
                    verifyProvinceCityCountyByRoad(data,val,db);
                    if(!data.province.isEmpty())    oldOrder = 0;
                    if(!data.city.isEmpty())        oldOrder = 1;
                    if(!data.county.isEmpty())      oldOrder = 2;
                    if(oldOrder>valKey.order){
                         
                         valKey.value = val + valKey.value ;
                         clearDataValueByOrder(oldOrder,data);                         
                         setDataByKeyValue(0,valKey,data);
                         oldOrder = valKey.order;
                    }else{
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                    }                   
               }else{
                    int order = -1;
                    if(i+1<list.size()){
                        order = list.get(i+1).order;
                    }
                    if(order != -1){
                        if(order>oldOrder){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }else if(order <= oldOrder){
                             String val = getDataValueByOrder(oldOrder,data);
                             valKey.value = val + valKey.value;
                             clearDataValueByOrder(oldOrder,data);
                             setDataByKeyValue(1,valKey,data);
                             oldOrder = valKey.order;
                        }              
                    }else{
                        valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                         if(oldOrder < valKey.order){
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;                          
                         }
                    }
               }
           }
           */
           /*
           valKey.order = getNextLevelByKey(valKey.key,oldOrder,valKey.order);
           if(oldOrder < valKey.order){
               setDataByKeyValue(0,valKey,data);
               oldOrder = valKey.order;                          
           }else if(oldOrder == valKey.order){
               setDataByKeyValue(1,valKey,data);
               oldOrder = valKey.order;  
           }else{
               if(i+1<list.size()){
                   if(list.get(i+1).order<= oldOrder){
                       String val = getDataValueByOrder(oldOrder,data);
                       valKey.value = val + valKey.value;
                       clearDataValueByOrder(oldOrder,data);
                       setDataByKeyValue(1,valKey,data);
                       oldOrder = valKey.order;                                 
                   }else
                       list.get(i+1).value = valKey.value + list.get(i+1).value;
               }
           }
           */
   //    }       
   //     return oldOrder;
   // }
    
    /*
    private int combineValueCountyIsNull(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        VerifyDataInfo _data = new VerifyDataInfo();
        if(oldOrder < valKey.order){
            switch(valKey.order){
                case 0:case 1:case 2:
                    verifyProvinceCityCounty(_data,valKey.value,db);
                    if(!_data.province.equals("")||!_data.city.equals("")||!_data.county.equals("")){
                        Evaluate(data,_data,db); 
                        break;
                    }
                    if(data.county.isEmpty()){
                        if(verifyProvinceCityCounty(_data,valKey.value,db)==0){
                            valKey.order++;
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }else{
                        }
                    }else{
                        verifyProvinceCityCounty(_data,valKey.value,db);
                        if(!_data.province.equals("")||!_data.city.equals("")||!_data.county.equals("")){
                            Evaluate(data,_data,db);                    
                        }                        
                    }
                    
                    break;
                case 3:case 4:
                    verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                    oldOrder = valKey.order;
                    break;
                case 5:
                    if(!data.county.isEmpty()){
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                        break;
                    }
                    int flag = verifyProvinceCityCountyByRoad(data,valKey,db);
//                    if(flag == 0)
//                        flag = verifyProvinceCityCountyByRoadFwd(data,valKey.value,db);
//                    if(!data.province.isEmpty())    oldOrder = 0;
//                    if(!data.city.isEmpty())        oldOrder = 1;
//                    if(!data.county.isEmpty())      oldOrder = 2;
                    if(flag == 1){
                        if(oldOrder>valKey.order){
                            String val = getDataValueByOrder(oldOrder,data);
                            valKey.value = val + valKey.value ;
                            clearDataValueByOrder(oldOrder,data);                         
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }
                    }else{
//                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;                        
                    }
                    break;
                case 6:case 7:case 8:case 9:
                    if(oldOrder<5){                    
                        verifyProvinceCityCountyByVillage(data,valKey.value,valKey.order,db);
                        if(data.county.isEmpty()){
                            
                        }
                        //oldOrder = valKey.order;
                        oldOrder = getDataMaxLevel(data);
                    }else{
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                    }
                    
                    break;
            }
       }else if(oldOrder == valKey.order){
            switch(valKey.order){
                case 0:case 1:case 2:
                    valKey.order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(oldOrder >valKey.order){
                        if(i+1<list.size()){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }else
                            valKey.order = valKey.order + 1;
                    }else if(oldOrder == valKey.order){
                        int flag = verifyProvinceCityCounty(_data,valKey.value,db);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(flag == 0){
                             if(i+1<list.size()){
                                 list.get(i+1).value = valKey.value + list.get(i+1).value;
                             }else
                                 valKey.order = valKey.order + 1;
                        }
                    }else{
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                    }
                    break;
                case 3:case 4:
                         setDataByKeyValue(1,valKey,data);
                         oldOrder = valKey.order;                    
                break;
                case 5:
                    int order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(order>valKey.order){
                        valKey.order = order;
                        setDataByKeyValue(0,valKey,data);
                    }else{
                        setDataByKeyValue(1,valKey,data);
                    }
                    valKey.order = order;
                    oldOrder = valKey.order;                     
                break;
                case 6:case 7:case 8:case 9:
                    order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(order > oldOrder){
                        valKey.order = order;
                        setDataByKeyValue(1,valKey,data);
                        valKey.order = order;
                        oldOrder = valKey.order;
                    }else{
                        setDataByKeyValue(1,valKey,data);
                    }
                    
            }
       }else{//oldOrder > valKey.order
           String val = "";
            switch(valKey.order){
                case 0:case 1:case 2:
                    if(oldOrder == 5){
                        valKey.order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                        break;
                    }
                    int _order = oldOrder;
                    if(oldOrder>2 ){
                        String _val = getDataValueByOrder(oldOrder,data);
                        val = _val + valKey.value;
                    }else{
                        val = valKey.value;
                    }
                    if(data.county.isEmpty()){
                        int flag = verifyProvinceCityCounty(_data,val,db);
                        if(flag == 1 && oldOrder>2)
                            clearDataValueByOrder(_order,data);
                        Evaluate(data,_data,db);
                        if(valKey.order >= getDataMaxLevel(data)){
                            valKey.value = val;
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }else{
                            if(flag == 0){
                                 if(i+1<list.size()){
                                     list.get(i+1).value = valKey.value + list.get(i+1).value;
                                     oldOrder = _order;
                                 }else
                                     valKey.order = valKey.order + 1;
                                 oldOrder = _order;
                            }
                        }
                    }else{
                        if(i+1<list.size()){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                            oldOrder = _order;
                        }else
                            valKey.order = valKey.order + 1;
                        oldOrder = _order;                        
                    }
                    break;
                case 3:
                    if(data.township.isEmpty()){
                         setDataByKeyValue(0,valKey,data);
                         oldOrder = valKey.order;                            
                    }
                break;
                case 4:
                    int order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(order>oldOrder){
                        valKey.order = order;
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;                         
                    }else{
                    if(data.village.isEmpty()){
                         setDataByKeyValue(0,valKey,data);
                         oldOrder = valKey.order;                            
                    }else{

                    }
                    }
                break;
                case 5:
                    valKey.value = getDataValueByOrder(oldOrder,data) + valKey.value;
                    int flag = verifyProvinceCityCountyByRoad(data,valKey,db);
                    int _old = oldOrder;
                    if(!data.province.isEmpty())    oldOrder = 0;
                    if(!data.city.isEmpty())        oldOrder = 1;
                    if(!data.county.isEmpty())      oldOrder = 2;
                    if(flag == 1){
                        if(_old>valKey.order){
                            clearDataValueByOrder(_old,data);
                        }
                    }else{
                        valKey.value = val ;
                         clearDataValueByOrder(_old,data);                         
                         setDataByKeyValue(0,valKey,data);
                         oldOrder = valKey.order;
//                        setDataByKeyValue(0,valKey,data);
//                        oldOrder = valKey.order;
                    }
                break;
                case 6:case 7:case 8:case 9:
                    valKey.order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(valKey.order > oldOrder){
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;  
                    }else if(valKey.order == oldOrder){
                        setDataByKeyValue(1,valKey,data);
                        oldOrder = valKey.order;                         
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        else{
                            valKey.order = oldOrder + 1;
                            setDataByKeyValue(0,valKey,data);
                        }
                    }
                    break;
            }

       }       
        return oldOrder;
    }
    */

    /*
    private int combineValueCountyNoNull(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        if(oldOrder < valKey.order){
            switch(valKey.order){
                case 0: case 1:case 2:
                break;
                case 3:case 4:
//                    break;
                case 5:
//                    break;
                case 6:case 7: case 8:case 9:
                    setDataByKeyValue(0,valKey,data);
                    oldOrder = valKey.order;
                    break;
            }
       }else if(oldOrder == valKey.order){
            switch(valKey.order){
                case 0:case 1:case 2:
                    valKey.order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(oldOrder >valKey.order){
                        if(i+1<list.size()){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;
                        }else
                            valKey.order = valKey.order + 1;
                    }else if(oldOrder == valKey.order){
                        int flag = verifyProvinceCityCounty(data,valKey.value,db);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;
                        if(flag == 0){
                             if(i+1<list.size()){
                                 list.get(i+1).value = valKey.value + list.get(i+1).value;
                             }else
                                 valKey.order = valKey.order + 1;
                        }
                    }else{
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;                   
                    }                        
                break;
                case 3:case 4:
                break;
                case 5:
                break;
                case 6:case 7:case 8:case 9:
                    int order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                    if(order>oldOrder){
                        valKey.order = order;
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;                         
                    }else{
                        setDataByKeyValue(1,valKey,data);
                    }
            }
       }else{//oldOrder > valKey.order
           String val = "";
            int order = -1;
            if(i+1<list.size()){
                order = list.get(i+1).order;
            }
            switch(valKey.order){
                case 0:case 1:case 2:
                    val = getDataValueByOrder(oldOrder,data) + valKey.value;
                    int old = oldOrder;
                    int flag = verifyProvinceCityCounty(data,val,db);
                    if(!data.province.isEmpty())    oldOrder = 0;
                    if(!data.city.isEmpty())        oldOrder = 1;
                    if(!data.county.isEmpty())      oldOrder = 2;
                    if(flag == 0){
                        order = keywords.getNextLevelByKey(valKey.key,old,valKey.order);
                        if(order>old){
                            valKey.order = order;
                            setDataByKeyValue(1,valKey,data);
                            oldOrder = valKey.order;                            
                        }else{
                            if(i+1<list.size()){
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                            }else
                                valKey.order = valKey.order + 1;
                        }
                    }                   
                    break;
                case 3:case 4:
                    if(order != -1){
                        if(order>oldOrder){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }else if(order <= oldOrder){
                             val = getDataValueByOrder(oldOrder,data);
                             valKey.value = val + valKey.value;
                             clearDataValueByOrder(oldOrder,data);
                             setDataByKeyValue(1,valKey,data);
                             oldOrder = valKey.order;
                        }              
                    }else{
                        valKey.order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                         if(oldOrder < valKey.order){
                             setDataByKeyValue(0,valKey,data);
                             oldOrder = valKey.order;                          
                         }
                    }                    
                    break;
                case 5:
                    val = getDataValueByOrder(oldOrder,data) + valKey.value;
                    flag = verifyProvinceCityCountyByRoad(data,val,db);
                    int _order = oldOrder;
                    if(!data.province.isEmpty())    oldOrder = 0;
                    if(!data.city.isEmpty())        oldOrder = 1;
                    if(!data.county.isEmpty())      oldOrder = 2;
                    if(flag == 0){
                        valKey.value = val;
                        clearDataValueByOrder(_order,data); 
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;                        
                    }else{
                        if(oldOrder>valKey.order){
                         clearDataValueByOrder(oldOrder,data);                         
                         setDataByKeyValue(0,valKey,data);
                         oldOrder = valKey.order; 
                        }
                    }                 
                    break;
                case 6:case 7:case 8:case 9:
                    if(order != -1){
                        if(order>oldOrder){
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }else if(order <= oldOrder){
                             val = getDataValueByOrder(oldOrder,data);
                             valKey.value = val + valKey.value;
                             clearDataValueByOrder(oldOrder,data);
                             setDataByKeyValue(1,valKey,data);
                             oldOrder = valKey.order;
                        }              
                    }else{
                        order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order);
                        if(oldOrder < order){
                            valKey.order = order;
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order;                          
                         }else{
                            valKey.order = oldOrder;
                            setDataByKeyValue(1,valKey,data);
                        }
                    }                        
                    break;
            }
       }       
        return oldOrder;
    }    
    */
    
    //valKey.key == valKey.vale
    /*
    private int combineKey(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        int order = -1;                
        if(i+1<list.size()){
            order = list.get(i+1).order;
        }
        String val = getDataValueByOrder(oldOrder,data);
        if(oldOrder < valKey.order){
            if(valKey.order<=2){
                if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                    clearDataValueByOrder(oldOrder,data); 
                    verifyProvinceCityCounty(data,val,db);
                    if(!data.province.isEmpty())    oldOrder = 0;
                    if(!data.city.isEmpty())        oldOrder = 1;
                    if(!data.county.isEmpty())      oldOrder = 2; 
                }
            }else{
                if(order >= valKey.order){
                    valKey.value = val + valKey.value;
                    clearDataValueByOrder(oldOrder,data);
                    setDataByKeyValue(0,valKey,data);
                    oldOrder = valKey.order;
                }else{
                    list.get(i+1).value = valKey.value + list.get(i+1).value;
                }
            }
        }else{ //oldOrder>= valKey.order;
            if(valKey.order<=2){
                if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                    val = getDataValueByOrder(oldOrder,data);
                    val = val + valKey.value;
                     
                    int flag = verifyProvinceCityCounty(data,val,db);
                    if(flag==1){
                        clearDataValueByOrder(oldOrder,data);
                        if(!data.province.isEmpty())    oldOrder = 0;
                        if(!data.city.isEmpty())        oldOrder = 1;
                        if(!data.county.isEmpty())      oldOrder = 2;                        
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                }else{
                    if(order != -1){
                        if(order <= oldOrder){
                            valKey.value = val + valKey.value;
                            clearDataValueByOrder(oldOrder,data);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order; 
                        }else{
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }
                    }else{
                        order = getNextLevelByKey(valKey.key,oldOrder,valKey.order); 
                        clearDataValueByOrder(order,data);
                        oldOrder = order;
                    }                     
                }               
            }else{
                if(oldOrder == valKey.order){
                    valKey.value = val + valKey.value;
                    setDataByKeyValue(0,valKey,data);
                }else{
                    if(order != -1){
                        if(order <= oldOrder){
                            valKey.value = val + valKey.value;
                            clearDataValueByOrder(oldOrder,data);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order; 
                        }else{
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }
                    }else{
//                        order = getNextLevelByKey(valKey.key,oldOrder,valKey.order); 
//                        if(oldOrder<valKey.order){
                            valKey.value = val + valKey.value;
                            clearDataValueByOrder(oldOrder,data);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = order;
//                        }
                    }            
                }
            }
        }
    */    
        /*
        if(valKey.order<=2){
            if(data.province.isEmpty() || data.city.isEmpty() || data.county.isEmpty()){
                String val = getDataValueByOrder(oldOrder,data);
                clearDataValueByOrder(oldOrder,data); 
                verifyProvinceCityCounty(data,val,db);
                if(!data.province.isEmpty())    oldOrder = 0;
                if(!data.city.isEmpty())        oldOrder = 1;
                if(!data.county.isEmpty())      oldOrder = 2; 
            }
        }else{
            if(oldOrder <= valKey.order){
                if(i+1<list.size()){
                    if(list.get(i+1).order >= oldOrder){
                        String val = getDataValueByOrder(oldOrder,data);
                        valKey.value = val + valKey.value ;
                        clearDataValueByOrder(oldOrder,data);                         
                        setDataByKeyValue(1,valKey,data);
                        oldOrder = valKey.order;                                  
                    }else 
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
                }
            }else {   //oldOrder > valKey.order
                int order = -1;                
                if(i+1<list.size()){
                    order = list.get(i+1).order;
                }
                if(order != -1){
                    if(order <= oldOrder){
                        String val = getDataValueByOrder(oldOrder,data);
                        valKey.value = val + valKey.value;
                        clearDataValueByOrder(oldOrder,data);
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order; 
                    }else{
                        list.get(i+1).value = valKey.value + list.get(i+1).value;
//                        setDataByKeyValue(1,valKey,data);
//                        oldOrder = valKey.order;
                    }
                }else{
                    order = getNextLevelByKey(valKey.key,oldOrder,valKey.order); 
                    clearDataValueByOrder(order,data);
                    oldOrder = order;
                }
            }
        }
        */
    //    return oldOrder;
   // }

    /*
    private int combineKeyCountyIsNull(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        VerifyDataInfo _data = new VerifyDataInfo();
        String val = getDataValueByOrder(oldOrder,data);
        if(oldOrder < valKey.order){
            switch(valKey.order){
                case 0:case 1:case 2:
                    verifyProvinceCityCounty(_data,valKey.value,db);
                    Evaluate(data,_data,db);
                    break;
                case 3:case 4:
                case 5:
                    valKey.value = val + valKey.value; 
                    clearDataValueByOrder(oldOrder,data);
                    setDataByKeyValue(0,valKey,data);
                    oldOrder = valKey.order;
                break;
                case 6:
               
                    break;
                case 7:case 8:case 9:
            }
        }else if(oldOrder == valKey.order){ //oldOrder>= valKey.order;
            switch(valKey.order){
                case 0:case 1:case 2:
                    val = getDataValueByOrder(oldOrder,data);
                    val = val + valKey.value;                     
                    verifyProvinceCityCounty(_data,val,db);
                    if(!_data.province.equals("")||!_data.city.equals("")||!_data.county.equals("")){
                        Evaluate(data,_data,db);
                        clearDataValueByOrder(oldOrder,data);                    
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                    break;
                case 3:case 4:case 5:case 6:
                break;
                case 7:case 8:case 9:
                    val = getDataValueByOrder(oldOrder,data);
                    val = val + valKey.value;
                    valKey.value = val;
                    setDataByKeyValue(0,valKey,data);
                    oldOrder = valKey.order;                    
                break;
            }
        }else{
            switch(valKey.order){
                case 0:case 1:case 2:
                    val = getDataValueByOrder(oldOrder,data);
                    val = val + valKey.value;                     
                    verifyProvinceCityCounty(_data,val,db);
                    if(!_data.province.equals("")||!_data.city.equals("")||!_data.county.equals("")){
                        Evaluate(data,_data,db);
                        clearDataValueByOrder(oldOrder,data);                    
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                    break;
                case 3:case 4:
                
                    break;
                case 5:
                    val = getDataValueByOrder(oldOrder,data);
                    valKey.value = val + valKey.value;
                    clearDataValueByOrder(oldOrder,data);
                    setDataByKeyValue(0,valKey,data);
                    oldOrder = valKey.order;                    
                    break;
                case 6:case 7:case 8:case 9:
                    val = getDataValueByOrder(oldOrder,data);
                    val = val + valKey.value;                     
                    verifyProvinceCityCounty(_data,val,db);
                    if(!_data.province.equals("")||!_data.city.equals("")||!_data.county.equals("")){
                        Evaluate(data,_data,db);
                        clearDataValueByOrder(oldOrder,data);                   
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                    }
                break;
            }            
        }
        return oldOrder;
    }
    */
    /*
    private int combineKeyCountyNoNull(LocalInfo data,List<KeyValue> list, KeyValue valKey,int oldOrder,int i,AddressDataBase db){
        int order = -1;                
        if(i+1<list.size()){
            order = list.get(i+1).order;
        }
        String val = getDataValueByOrder(oldOrder,data);
        if(oldOrder < valKey.order){
            switch(valKey.order){
                case 0:case 1:case 2:
                    break;
                case 3:case 4:case 5:case 6:case 7:case 8:case 9:
                    if(oldOrder<=2){
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                        break;
                    }                     
                    if(order >= valKey.order){
                        valKey.value = val + valKey.value;
                        clearDataValueByOrder(oldOrder,data);
                        setDataByKeyValue(0,valKey,data);
                        oldOrder = valKey.order;
                    }else{
                        if(i+1<list.size())
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        else{
                            setDataByKeyValue(1,valKey,data);
                        }
                    }                    
            }
        }else{ //oldOrder>= valKey.order;
            switch(valKey.order){
                case 0:case 1:case 2:
                    if(val.indexOf(valKey.key)>=0){
                        break;
                    }
                    
                    if(order != -1){
                        if(order <= oldOrder){
                            valKey.value = val + valKey.value;
                            clearDataValueByOrder(oldOrder,data);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = valKey.order; 
                        }else{
                            list.get(i+1).value = valKey.value + list.get(i+1).value;
                        }
                    }else{
                        order = keywords.getNextLevelByKey(valKey.key,oldOrder,valKey.order); 
                        clearDataValueByOrder(order,data);
                        oldOrder = order;
                    }                  
                    break;
                case 3:
                    if(oldOrder == 4){
                        valKey.value = val + valKey.value;
                        setDataByKeyValue(0,valKey,data);
                        clearDataValueByOrder(oldOrder,data);
                        oldOrder = valKey.order;
                    }
                    break;
                case 4:
                    valKey.value = val + valKey.value;
                
                    break;
                case 5:case 6:case 7:case 8:case 9:
                    if(oldOrder == valKey.order){
                        valKey.value = val + valKey.value;
                        setDataByKeyValue(0,valKey,data);
                    }else{
                        if(order != -1){
                            if(order <= oldOrder){
                                valKey.value = val + valKey.value;
                                clearDataValueByOrder(oldOrder,data);
                                setDataByKeyValue(0,valKey,data);
                                oldOrder = valKey.order; 
                            }else{
                                list.get(i+1).value = valKey.value + list.get(i+1).value;
                            }
                        }else{
                            valKey.value = val + valKey.value;
                            clearDataValueByOrder(oldOrder,data);
                            setDataByKeyValue(0,valKey,data);
                            oldOrder = order;
                        }            
                    }                    
            }
        }
        return oldOrder;
    }
    */
    
    //mode = 0;1;2
    private void setDataByKeyValue(int mode,KeyValue valKey,LocalInfo data){
        if(mode == 0){
            if(valKey.order == 0) data.province = valKey.value;
            if(valKey.order == 1) data.city = valKey.value;
            if(valKey.order == 2) data.county = valKey.value;
            if(valKey.order == 3) data.township = valKey.value;
            if(valKey.order == 4) data.village = valKey.value;
            if(valKey.order == 5) data.Road = valKey.value;
            if(valKey.order == 6) data.RoadNo = valKey.value;
            if(valKey.order == 7) data.building = valKey.value;
            if(valKey.order == 8) data.floor = valKey.value;
            if(valKey.order == 9) data.room = valKey.value;
        }
        if(mode == 1){
//            if(valKey.order == 0) data.province = data.province + valKey.value;
//            if(valKey.order == 1) data.city = data.city + valKey.value;
//            if(valKey.order == 2) data.county = data.county + valKey.value;
            if(valKey.order == 3) data.township = data.township + valKey.value;
            if(valKey.order == 4) data.village = data.village + valKey.value;
            if(valKey.order == 5) data.Road = data.Road + valKey.value;
            if(valKey.order == 6) data.RoadNo = data.RoadNo + valKey.value;
            if(valKey.order == 7) data.building = data.building + valKey.value;
            if(valKey.order == 8) data.floor = data.floor + valKey.value;
            if(valKey.order == 9) data.room = data.room + valKey.value;            
        }
        if(mode == 2){
            if(valKey.order == 3) data.township = valKey.value + data.township;
            if(valKey.order == 4) data.village = valKey.value + data.village;
            if(valKey.order == 5) data.Road = valKey.value + data.Road;
            if(valKey.order == 6) data.RoadNo = valKey.value + data.RoadNo;
            if(valKey.order == 7) data.building = valKey.value + data.building;
            if(valKey.order == 8) data.floor = valKey.value + data.floor;
            if(valKey.order == 9) data.room = valKey.value + data.room;
        }
    }
    
    private String getDataValueByOrder(int order,LocalInfo data){
        if(order == 0) return data.province;
        if(order == 1) return data.city;
        if(order == 2) return data.county;
        if(order == 3) return data.township;
        if(order == 4) return data.village;
        if(order == 5) return data.Road;
        if(order == 6) return data.RoadNo;
        if(order == 7) return data.building;
        if(order == 8) return data.floor;
        if(order == 9) return data.room;
        return "";
    }

    private int getDataMaxLevel(LocalInfo data){
        int level = -1;
        if(!data.province.isEmpty()) level = 0;
        if(!data.city.isEmpty()) level = 1;
        if(!data.county.isEmpty()) level = 2;
        if(!data.township.isEmpty()) level = 3;
        if(!data.village.isEmpty()) level = 4;
        if(!data.Road.isEmpty()) level = 5;
        if(!data.RoadNo.isEmpty()) level = 6;
        if(!data.building.isEmpty()) level = 7;
        if(!data.floor.isEmpty()) level = 8;
        if(!data.room.isEmpty()) level = 9;
        return level;
    }
    
    
    private void clearDataValueByOrder(int order,LocalInfo data){
        if(order == 0) data.province = "";
        if(order == 1) data.city= "";
        if(order == 2) data.county="";
        if(order == 3) data.township="";
        if(order == 4) data.village="";
        if(order == 5) data.Road="";
        if(order == 6) data.RoadNo="";
        if(order == 7) data.building="";
        if(order == 8) data.floor="";
        if(order == 9) data.room="";        
    }
    
    /*
    private int getMinOrder(List<KeyValue> list,int min,int index){
        int _min = 10;
        int _minIndex = index;
        for(int i=index; i<list.size(); i++){
            if(list.get(i).order < min && list.get(i).order >=0){
                min = list.get(i).order;
                _minIndex = i;
            }
        }        
        return _minIndex;
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
//            verifyDataLevel(data,data.province,0,_list,db);
//            _list = db.verifyAddress(data.province);
            if(!_list.isEmpty())
                list.addAll(_list);
        }
        if(!data.city.isEmpty()){
//            verifyDataLevel(data,data.city,1,_list,db);            
            //_list = db.verifyAddress(data.city);
            if(!_list.isEmpty()){
//                verifyExtractInfo(data,_list);
                list.addAll(_list);
            }
        }
        if(!data.county.isEmpty()){
//            verifyDataLevel(data,data.county,2,_list,db);
//            _list = db.verifyAddress(data.county);
            if(!_list.isEmpty())
                list.addAll(_list);
        }
        
        if(!data.township.isEmpty()){
//            verifyDataLevel(data,data.township,3,_list,db);
//            _list = db.verifyAddress(data.township);
            if(!_list.isEmpty()){
//                data.township = "";
                list.addAll(_list);
            }
        }
  
        if(!data.village.isEmpty()){
//            verifyDataLevel(data,data.village,4,_list,db);
//            _list = db.verifyAddress(data.village);
            if(!_list.isEmpty()){
//                data.village = "";
                list.addAll(_list);
            }
        }
    }
    */
    /*
    正向检验简称、或非地址字符串
    */
    /*
    private void verifyDataFarwd(LocalInfo data,int level,String val,AddressDataBase db){
        int size = val.length();
        int start = 0;        
        List<String> _list = new ArrayList<String>();
        int flag = 0;
        if(level <= 2){
            while(size > start){
                String _val = val.substring(start,size);
                if(flag == 0)
                    _list = db.verifyAddress(_val);
                else
                    _list = db.queryWords(_val);
                if(!_list.isEmpty()){                        
                    if(_list.size() == 1){
                        verifyDataLevelOne(data,level,_list);
                    }else{
                        verifyDataLevelMut(data,level,_list);
                    }
                    val = val.replace(_val, "");
                    start = 0;
                    size = val.length();
                    flag = 1;
                }else{
                    start ++;
                }
            }
            if(flag == 0){
                int len = 2;
                while(size >= start + len){
                    String _val = val.substring(start,start + len);
                    _list = db.queryWords(_val);
                    if(!_list.isEmpty()){
                        if(_list.size() == 1){
                            verifyDataLevelOne(data,level,_list);
                        }else{
                            verifyDataLevelMut(data,level,_list);
                        }
                        val = val.replace(_val, "");
                        len = 2;
                        size = val.length();
                    }else{
                        len ++;
                    }
                }                
            }
        }else{
            int len = 2;
            if(isNullOfParents(data,level)== 0){
                while(size >= start + len){
                    String _val = val.substring(start,start + len);
                    _list = db.queryWords(_val);
                    if(!_list.isEmpty()){
                        if(_list.size() == 1){
                            verifyDataLevelOne(data,level,_list);
                        }else{
                            verifyDataLevelMut(data,level,_list);
                        }
                        val = val.replace(_val, "");
                        len = 2;
                        size = val.length();
                    }else{
                        len ++;
                    }
                }
            }
        }
        if(level == 3) data.township = val;
        if(level == 4) data.village = val;
        if(level == 6) data.RoadNo = val;
        if(level == 7) data.building = val;
        if(level == 8) data.floor = val;
        if(level == 9) data.room = val;
    }
    */
    
    /*
    private int isNullOfParents(LocalInfo data,int level){
        int val = 0;
        int _level = level - 1;
        switch(_level){
            case 9: if(!data.room.equals("")) val = val | 0x1;
            case 8: if(!data.floor.equals("")) val = val | 0x2;
            case 7: if(!data.building.equals("")) val = val | 0x4;
            case 6: if(!data.RoadNo.equals("")) val = val | 0x8;
            case 5: if(!data.Road.equals("")) val = val | 0x10;
            case 4: if(!data.village.equals("")) val = val | 0x20;
            case 3: if(!data.township.equals("")) val = val | 0x40;
            case 2: if(!data.county.equals("")) val = val | 0x80;
            case 1: if(!data.city.equals("")) val = val | 0x100;
            case 0: if(!data.province.equals("")) val = val | 0x200;
        }
        return val;
    }
    */
    /*
    private String verifyDataRoadFarwd(LocalInfo data,int level, String val,AddressDataBase db){
        int size = val.length();
        int start = 0;
        int len = 2;
        String rs = "";
        List<String> _list = new ArrayList<String>();
        int flag = 0;
        String _val="";
        while(size >= len + start){
            _val = val.substring(start,size);
            _list = db.queryAddressByRoad(_val);
            if(!_list.isEmpty()){
                flag = 1;
                val = val.replace(_val, "");
                start = 0;
                size = val.length();
                data.Road = _val + data.Road;         
            }else{
                start ++;
            }
        }
        if(val.length()>0){
            start = 0;
            len = 2;
            size = val.length();
            while(size >= len + start){
                _val = val.substring(start,start + len);
                _list = db.queryWords(_val);
                if(len <size && !_list.isEmpty()){
                    String _val1 = val.substring(0,len+1);
                    List<String> _list1 = db.queryAddressByRoad(_val1);
                    if(!_list1.isEmpty())
                        break;
                }
                if(!_list.isEmpty()){
                    val = val.replace(_val, "");
                    start = 0;
                    size = val.length();
                    if(_list.size() == 1){
                        verifyDataLevelOne(data,level, _list);
                    }else{
                        verifyDataLevelMut(data, level, _list);
                    } 
                }else{
                    len ++;
                }
            }
        } 
//        if(data.Road.isEmpty())
            data.Road = val + data.Road;
        return rs;
    }    
    */
    /*
    private void verifyDataLevelOne(LocalInfo data,int level,List<String> _list){
        String[] vals;
        int stat = getLocalInfoStat(data);
        if(_list.size() == 1){
            vals = _list.get(0).split(",");
            switch(stat){
                case 0:
                    if(vals.length>=1) data.province = vals[0];
                    if(vals.length>=2) data.city = vals[1];
                    if(vals.length>=3) data.county = vals[2];
                    break;
                case 1:
                    if(vals.length>=3 && vals[2].equals(data.county)){
                        data.province = vals[0];
                        data.city = vals[1];
                    }
                    break;
                case 2:
                    if(vals.length == 2 && vals[1].equals(data.city)){
                        data.province = vals[0];
                    }
                    if(vals.length == 3 && vals[1].equals(data.city)){
                        data.province = vals[0];
                        data.county = vals[2];
                    }                    
                    break;
                case 3:
                    if(vals.length == 2 && vals[1].equals(data.city)){
                        data.province = vals[0];
                    }
                    if(vals.length == 3 && vals[1].equals(data.city) && vals[2].equals(data.county)){
                        data.province = vals[0];
                    }
                    break;
                case 4:
                    if(vals.length == 2 && vals[0].equals(data.province)){
                        data.city = vals[1];
                    }
                    if(vals.length == 3 && vals[0].equals(data.province)){
                        data.city = vals[1];
                        data.county = vals[2];
                    }                    
                    break;
                case 5:
                    if(vals.length == 2 && vals[0].equals(data.province)){
                        data.city = vals[1];
                    }
                    if(vals.length == 3 && vals[0].equals(data.province) && vals[2].equals(data.county)){
                        data.city = vals[1];
                    }                      
                    break;
                case 6:
                    if(vals.length == 3 && vals[0].equals(data.province) && vals[1].equals(data.city)){
                        data.county = vals[2];
                    }                       
                    break;
                case 7:break;
            }
        }       
    }
    */
    
    private int verifyProvinceCityCounty(LocalInfo data,KeyValue value,AddressDataBase db){
        VerifyDataInfo _data = new VerifyDataInfo();
        String val = value.value;
        List<String> list = db.verifyAddress(val);
        int flag = 0;
        if(list.isEmpty()){
            int start = 0;
            int vlen = val.length();
            if(val.length() > 2){                                               //检验是否包含简称省、地、县
                while(start < vlen - 2){
                    String _val = val.substring(start,vlen);
                    list = db.verifyAddress(_val);
                    if(!list.isEmpty()){
                        if(flag == 0)
                            flag = getDistrictDataInfo(0,_data,list);
                        else
                            getDistrictDataInfo(0,_data,list);
                        val = val.replace(_val, "");
                        start = 0;
                        vlen = val.length();
                        break;
                    }
                    start ++;
                }
            }
            if(val.length()>1){                                                 //检验是否存在简称
                vlen = val.length();
                start = 0;
                while(vlen - start > 0 ){
                    String _val = val.substring(start,vlen);
                    list = db.queryWords(_val);
                    if(!list.isEmpty()){
                        if(flag == 0)
                            flag = getDistrictDataInfo(0,_data,list);
                        else
                            getDistrictDataInfo(0,_data,list);
                        val = val.replace(_val, "");
                        start = 0;
                        vlen = val.length();
                        continue;
                    }
                    start ++;
                }
            }
        }else{
            flag = getDistrictDataInfo(0,_data,list);
        }
        Evaluate(data,_data,db);
        return flag;
    }
    
    /*
    private int verifyProvinceCityCounty(LocalInfo data,String val,AddressDataBase db){        
        List<String> list = db.verifyAddress(val);
        int flag = 0;
        if(list.isEmpty()){
            int start = 0;
            int vlen = val.length();
            if(val.length() > 2){                                               //检验是否包含简称省、地、县
                while(start < vlen - 2){
                    String _val = val.substring(start,vlen);
                    list = db.verifyAddress(_val);
                    if(!list.isEmpty()){
                        if(flag == 0)
                            flag = verifyProvinceCityCountyValue(0,data,list);
                        else
                            verifyProvinceCityCountyValue(0,data,list);
                        val = val.replace(_val, "");
                        start = 0;
                        vlen = val.length();
                        break;
                    }
                    start ++;
                }
            }
            if(val.length()>1){                                                 //检验是否存在简称
                vlen = val.length();
                start = 0;
                while(vlen - start >= 0 ){
                    String _val = val.substring(start,vlen);
                    list = db.queryWords(_val);
                    if(!list.isEmpty()){
                        if(flag == 0)
                            flag = verifyProvinceCityCountyValue(0,data,list);
                        else
                            verifyProvinceCityCountyValue(0,data,list);
                        val = val.replace(_val, "");
                        start = 0;
                        vlen = val.length();
                        continue;
                    }
                    start ++;
                }
            }
        }else{
            flag = verifyProvinceCityCountyValue(0,data,list);
        }
        return flag;
    }    
    */
    private int getDistrictDataInfo(int mode,VerifyDataInfo data,List<String> list){
        int flag = 0;
        if(list.size() == 1){                                               //只有一项
            String[] vals = list.get(0).split(",");
            if(vals.length>0 && data.province.isEmpty()){
                data.province = vals[0];
                flag = 1;
            }
            if(vals.length>1){
                if(data.province.isEmpty()){
                    data.province = vals[0];
                    data.city = vals[1];
                    flag = 1;
                }else if(data.province.equals(vals[0])){
                    data.city = vals[1];
                    flag = 1;
                }
            }
            if(vals.length>2){
                if(data.province.isEmpty()){
                    data.province = vals[0];
                    flag = 1;
                    if(data.city.isEmpty()){
                        data.city = vals[1];
                        data.county = vals[2];
                        flag = 1;
                    }else if(data.city.equals(vals[1])){
                        data.county = vals[3];
                        flag = 1;
                    }
                }else if(data.province.equals(vals[0])){
                    if(data.city.isEmpty()){
                        data.city = vals[1];
                        data.county = vals[2];
                        flag = 1;
                    }else if(data.city.equals(vals[1])){
                        data.county = vals[2];
                        flag = 1;
                    }
                }
            }
        }else{//当前有多项
            int exist0 = 0,exist1 = 0,exist2 = 0;
            int flag0 = 0,flag1 = 0,flag2 = 0;
            String province = "",city = "",county = "";
            String[] _vals = {};
            for(int c = 0; c < list.size(); c ++){
                _vals = list.get(c).split(","); 
                if(_vals.length >= 1){
                    exist0 = 1;
                    if(province.isEmpty())
                        province = _vals[0];
                    else if(!province.equals(_vals[0])){
                        flag0 = 1;
                    }
                }
                if(_vals.length >= 2){
                    exist1 = 1;
                    if(city.isEmpty())
                        city = _vals[1];
                    else if(!city.equals(_vals[1])){
                        flag1 = 1;
                    }
                }
                if(_vals.length >= 3){
                    exist2 = 1;
                    if(county.isEmpty())
                        county = _vals[2];
                    else if(!county.equals(_vals[2])){
                        flag2 = 1;
                    }
                }
            }

            if(flag0 ==1 || exist0 == 0) province = "";
            if(flag1 ==1 || exist1 == 0) city = "";
            if(flag2 ==1 || exist2 == 0) county = "";               
            if(mode == 0){
                if(data.province.isEmpty() && !province.isEmpty()){
                    data.province = province;
                    flag = 1;
                }
                if(!city.isEmpty()){
                    if(data.province.isEmpty()){
                        if(data.city.isEmpty()){
                            data.city = city;
                            flag = 1;
                        }
                    }else{
                        for(int p=0;p<list.size();p++){
                            if(list.get(p).indexOf(data.province)>=0 && list.get(p).indexOf(city)>=0){
                                data.city = city;
                                flag = 1;
                                break;
                            }
                        }                    
                    }
                }
                if(!county.isEmpty()){
                    if(data.province.isEmpty()){
                        if(data.city.isEmpty()){
                            data.county = county;
                            flag = 1;
                        }else{
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.city)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }
                        }
                    }else{
                        if(data.city.isEmpty()){
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.province)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }
                        }else{
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.province)>=0 && list.get(p).indexOf(data.city)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }                        
                        }
                    }
                }
            }else{
                flag = 1;
                data.province = province;
                data.city = city;
                data.county = county;
            }
        }
        if(data.county.equals(data.city + "辖区") )
            data.county = "";
        return flag;
    }
    
    
    
    /*
    private int verifyProvinceCityCountyValue(int mode,LocalInfo data,List<String> list){
        int flag = 0;
        if(list.size() == 1){                                               //只有一项
            String[] vals = list.get(0).split(",");
            if(vals.length>0 && data.province.isEmpty()){
                data.province = vals[0];
                flag = 1;
            }
            if(vals.length>1){
                if(data.province.isEmpty()){
                    data.province = vals[0];
                    data.city = vals[1];
                    flag = 1;
                }else if(data.province.equals(vals[0])){
                    data.city = vals[1];
                    flag = 1;
                }
            }
            if(vals.length>2){
                if(data.province.isEmpty()){
                    data.province = vals[0];
                    flag = 1;
                    if(data.city.isEmpty()){
                        data.city = vals[1];
                        data.county = vals[2];
                        flag = 1;
                    }else if(data.city.equals(vals[1])){
                        data.county = vals[3];
                        flag = 1;
                    }
                }else if(data.province.equals(vals[0])){
                    if(data.city.isEmpty()){
                        data.city = vals[1];
                        data.county = vals[2];
                        flag = 1;
                    }else if(data.city.equals(vals[1])){
                        data.county = vals[2];
                        flag = 1;
                    }
                }
            }
        }else{//当前有多项
            int exist0 = 0,exist1 = 0,exist2 = 0;
            int flag0 = 0,flag1 = 0,flag2 = 0;
            String province = "",city = "",county = "";
            String[] _vals = {};
            for(int c = 0; c < list.size(); c ++){
                _vals = list.get(c).split(","); 
                if(_vals.length >= 1){
                    exist0 = 1;
                    if(province.isEmpty())
                        province = _vals[0];
                    else if(!province.equals(_vals[0])){
                        flag0 = 1;
                    }
                }
                if(_vals.length >= 2){
                    exist1 = 1;
                    if(city.isEmpty())
                        city = _vals[1];
                    else if(!city.equals(_vals[1])){
                        flag1 = 1;
                    }
                }
                if(_vals.length >= 3){
                    exist2 = 1;
                    if(county.isEmpty())
                        county = _vals[2];
                    else if(!county.equals(_vals[2])){
                        flag2 = 1;
                    }
                }
            }

            if(flag0 ==1 || exist0 == 0) province = "";
            if(flag1 ==1 || exist1 == 0) city = "";
            if(flag2 ==1 || exist2 == 0) county = "";               
            if(mode == 0){
                if(data.province.isEmpty() && !province.isEmpty()){
                    data.province = province;
                    flag = 1;
                }
                if(!city.isEmpty()){
                    if(data.province.isEmpty()){
                        if(data.city.isEmpty()){
                            data.city = city;
                            flag = 1;
                        }
                    }else{
                        for(int p=0;p<list.size();p++){
                            if(list.get(p).indexOf(data.province)>=0 && list.get(p).indexOf(city)>=0){
                                data.city = city;
                                flag = 1;
                                break;
                            }
                        }                    
                    }
                }
                if(!county.isEmpty()){
                    if(data.province.isEmpty()){
                        if(data.city.isEmpty()){
                            data.county = county;
                            flag = 1;
                        }else{
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.city)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }
                        }
                    }else{
                        if(data.city.isEmpty()){
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.province)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }
                        }else{
                            for(int p=0;p<list.size();p++){
                                if(list.get(p).indexOf(data.province)>=0 && list.get(p).indexOf(data.city)>=0){
                                    data.county = county;
                                    flag = 1;
                                    break;
                                }
                            }                        
                        }
                    }
                }
            }else{
                flag = 1;
                data.province = province;
                data.city = city;
                data.county = county;
            }
        }
        if(data.county.equals(data.city + "辖区") )
            data.county = "";
        return flag;
    }
    */

    private int verifyProvinceCityCountyByRoad(LocalInfo data,KeyValue val,AddressDataBase db){
        VerifyDataInfo _data = new VerifyDataInfo();
        List<String> _list = new ArrayList<String>();
        int flag = 0;
        String _val = val.value;
        String t_val = val.value;
        int start = 0;
        int size = val.value.length();
        int len = 2;
        while(size - val.key.length()>len + start){
            _val = val.value.substring(start,size);
            _list = db.queryAddressByRoad(_val);
            if(!_list.isEmpty()){
                val.value = val.value.replace(_val,"");
                data.Road = data.Road + _val;
                flag = 1;
                break;
            }else{
                start ++;
            }
        }
        size = val.value.length();
        if(size > 1){
            start = 0;
            while(size - start >= val.key.length() ){
                _val = val.value.substring(start,size);
                _list = db.queryWords(_val);
                if(!_list.isEmpty()){
                    if(flag == 0){
                        flag = getDistrictDataInfo(1,_data,_list);
                    }else{
                        getDistrictDataInfo(1,_data,_list);
                    }
                    val.value = val.value.replace(_val, "");
                    start = 0;
                    size = val.value.length();
                    continue;
                }
                start ++;
            }            
        }
        Evaluate(data,_data,db);
        verifyCounty(data,t_val,5);
        //呼和浩特锡林北路   
        if(flag == 0){
            verifyProvinceCityCountyByRoadFwd(_data,data,t_val,db);
            Evaluate(data,_data,db);
            verifyCounty(data,t_val,5);
        }
        
        return flag;
    }
    
    private int verifyProvinceCityCountyByRoadFwd(VerifyDataInfo data,LocalInfo data2,String val,AddressDataBase db){
//        VerifyDataInfo _data = new VerifyDataInfo();
        List<String> _list = new ArrayList<String>();
        int flag = 0;
        String _val = val;
        String t_val = val;
        int start = 0;
        int size = val.length();
        int len = 2;
        while(size >= len + start){            
            _val = val.substring(start,len);
            _list = db.queryWords(_val);
            if(!_list.isEmpty() && (val.length()- _val.length())>2){
                flag = getDistrictDataInfo(1,data,_list);
                val = val.replace(_val,"");
                data2.Road =  val;
                len=2;
                size = val.length();
            }else{
                len ++;
            }
        }
        
        verifyCounty(data2,t_val,5);
        return flag;
    }    
    
    
    private void verifyCounty(LocalInfo data,String _county,int level){
        String province = data.province;
        String city = data.city;
        String county = data.county;
        int start = 0;
        int len = province.length();        
        if(level > 0){
            start = 0;
            len = province.length();
            while(start<len){
                province = province.substring(start,len);
                if(_county.indexOf(province)>=0){
                    _county = _county.replace(province, "");
                    break;
                }
                len --;
            }   
        }
        if(level>1){
            start = 0;
            len = city.length();
            while(start<len){
                city = city.substring(start,len);
                if(_county.indexOf(city)>=0){
                    _county = _county.replace(city, "");
                    break;
                }
                len --;
            }                
        }
        if(level>2){
            start = 0;
            len = county.length();
            while(start<len){
                county = county.substring(start,len);
                if(_county.indexOf(county)>=0){
                    _county = _county.replace(county, "");
                    break;
                }
                len --;
            }
        }
        if(level == 0) data.province = _county;
        if(level == 1) data.city = _county;
        if(level == 2) data.county = _county;
        if(level == 3) data.township = _county;
        if(level == 4) data.village = _county;
        if(level == 5) data.Road = _county;
    }
    
    private void verifyProvinceCityCountyByVillage(LocalInfo data,String val,int order,AddressDataBase db){
        VerifyDataInfo _data = new VerifyDataInfo();
        int size = val.length();
        int start = 0;
        String _val= "";
        List<String> _list = new ArrayList<String>();
        if(size > 1){
            start = 0;
            int len = 2;
            while(size - len >= 0 ){
                _val = val.substring(start,len);
                _list = db.queryWords(_val);
                if(!_list.isEmpty()){
                    if(getDistrictDataInfo(0,_data,_list)==1){
                        val = val.replace(_val, "");
                        start = 0;
                        if(val.length()>0){
                            if(_data.county.indexOf(val) == -1 && val.length()<2){
                                _data.province = "";_data.city = "";_data.county = "";
                                val = _val + val;
                                break;
                            }
                            if(_data.county.indexOf(val)>=0){
//                                _data.province = ""; _data.city = "";_data.county = ""; 河北省0藁城市7市区3西部9
                                val = _val + val;
                                break;                            
                        }else{
                                //val = "";
                            }                            
                        }
                        size = val.length();
                        continue;
                    }
                }
                len ++;
            }            
        }
        if(Evaluate(data,_data,db)==0){
        
            if(order == 3) data.township = val;
            if(order == 4) data.village = val;
            if(order == 6) data.RoadNo = val;
            if(order == 7) data.building = val;
            if(order == 8) data.floor = val;
            if(order == 9) data.room = val;
        }
    }
    
    /*
    private void verifyDataLevelMut(LocalInfo data,int level, List<String> _list){
        int stat = getLocalInfoStat(data);
        LocalInfo temp_data = new LocalInfo();
        String[] vals;
//        if(_list.size() == 1){
        vals = _list.get(0).split(",");
        if(vals.length >=1)                    temp_data.province = vals[0];
        if(vals.length >=2)                    temp_data.province = vals[1];
        if(vals.length >=3)                    temp_data.province = vals[2];
        boolean province = true;
        boolean city = true;
        boolean county = true;

        switch(stat){
            case 0:
                for(int i=1; i<_list.size(); i++){
                    vals = _list.get(i).split(",");
                    if(vals.length>=1 && !vals[0].equals(temp_data.province))                               
                        province = false;
                    if(vals.length>=2 ){
                        if(temp_data.city.equals(""))
                            temp_data.city = vals[1];
                        else if(!vals[1].equals(temp_data.city))
                            city = false;
                    }
                    if(vals.length>=3 ){
                        if(temp_data.county.equals(""))
                            temp_data.county = vals[2];
                        else if( !vals[2].equals(temp_data.county))
                            county = false;
                    }
                }
                if(province)    data.province = temp_data.province;
                if(city)    data.city = temp_data.city;
                if(county)    data.county = temp_data.county;
                break;
            case 1:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length>=3 && vals[2].equals(data.county)){
                        if(temp_data.province.equals(""))           temp_data.province = vals[0];
                        else if(!temp_data.province.equals(vals[0]))province = false;
                        if(temp_data.city.equals(""))               temp_data.city = vals[1];
                        else if(!temp_data.city.equals(vals[1]))    city = false;
                    }
                }
                if(province)    data.province = temp_data.province;
                if(city)    data.city = temp_data.city;                    
                break;
            case 2:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length == 2 && vals[1].equals(data.city)){
                        if(temp_data.province.equals(""))           temp_data.province = vals[0];
                        else if(!temp_data.province.equals(vals[0]))province = false;                            
                    }
                    if(vals.length == 3 && vals[1].equals(data.city)){
                        if(temp_data.province.equals(""))           temp_data.province = vals[0];
                        else if(!temp_data.province.equals(vals[0]))province = false;
                        if(temp_data.county.equals(""))             temp_data.county = vals[0];
                        else if(!temp_data.county.equals(vals[2]))  county = false;
                    }                        
                }
                if(province)    data.province = temp_data.province;
                if(county)    data.county = temp_data.county;
                break;
            case 3:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length == 2 && vals[1].equals(data.city)){
                        if(temp_data.province.equals(""))           temp_data.province = vals[0];
                        else if(!temp_data.province.equals(vals[0]))province = false;                            
                    }
                    if(vals.length == 3 && vals[1].equals(data.city) && vals[2].equals(data.county)){
                        if(temp_data.province.equals(""))           temp_data.province = vals[0];
                        else if(!temp_data.province.equals(vals[0]))province = false;
                    }
                }
                if(province)    data.province = temp_data.province;
                break;
            case 4:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length == 2 && vals[0].equals(data.province)){
                        if(temp_data.city.equals(""))               temp_data.city = vals[1];
                        else if(!temp_data.city.equals(vals[1]))    province = false;  
                    }
                    if(vals.length == 3 && vals[0].equals(data.province)){
                        if(temp_data.city.equals(""))               temp_data.city = vals[1];
                        else if(!temp_data.city.equals(vals[1]))    city = false;
                        if(temp_data.county.equals(""))             temp_data.county = vals[2];
                        else if(!temp_data.county.equals(vals[2]))  county = false;                            
                    }                        
                }
                if(city)    data.city = temp_data.city;
                if(county)    data.county = temp_data.county;
                break;
            case 5:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length == 2 && vals[0].equals(data.province)){
                        if(temp_data.city.equals(""))                          temp_data.city = vals[1];
                        else if(!temp_data.city.equals(vals[1]))                city = false;                            
                    }
                    if(vals.length == 3 && vals[0].equals(data.province) && vals[2].equals(data.county)){
                        if(temp_data.city.equals(""))                                    temp_data.city = vals[1];
                        else if(!temp_data.city.equals(vals[1]))                         city = false;                            
                    }
                }
                if(city)    data.city = temp_data.city;
                break;
            case 6:
                for(int i=0; i<_list.size();i++){
                    vals = _list.get(i).split(",");
                    if(vals.length>=3 && vals[0].equals(data.province) && vals[1].equals(data.city)){
                        if(temp_data.county.equals(""))                             temp_data.county = vals[2];
                        else if(!temp_data.county.equals(vals[2]))                  county = false;                            
                    }
                }
                if(county)    data.county = temp_data.county;
                break;
            case 7:break;
        }    
    }    
    */
    /*
    private void verifyDataLevel(LocalInfo data,List<KeyValue> list,AddressDataBase db){
        if(list.isEmpty())
            return;
        for(int m=0;m<list.size();m++){
            if(list.get(m).order < 5  ){
                verifyDataFarwd(data,list.get(m).order,list.get(m).value,db);
            }else{
                if(list.get(m).order == 5){
                    //if( isNullOfParents(data,5) == 0)
                    {
                        verifyDataRoadFarwd(data,5,list.get(m).value,db);
                    //}else{
                    //    data.Road = list.get(m).value;
                    }
                }
                if(list.get(m).order>5){
                    switch(list.get(m).order){
                        case 6: data.RoadNo = list.get(m).value;break;
                        case 7: data.building = list.get(m).value;break;
                        case 8: data.floor = list.get(m).value;break;
                        case 9: data.room = list.get(m).value;break;
                    }
                }
            }
        }
    }
    */
    
    /*
    private int getLocalInfoStat(LocalInfo data){
        int stat = 0;
        if(!data.province.isEmpty()) stat = stat |0x4;
        if(!data.city.isEmpty()) stat = stat |0x2;
        if(!data.county.isEmpty()) stat = stat |0x1;
        return stat;
    }
    */
    //0x40|0x20|0x10|0x8|0x4|0x2|0x1
    //其它|路  |村  |镇 |县 |地 |省
    /*
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
    */
    
    /*
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
    */
    
    /*
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
        
        if(index >= 0 && index <= 2){                                           //即省、地、县三级区划
            String val = _val.substring(start,size);
            List<String> _list = new ArrayList<String>();
            while(start < size){
                val = _val.substring(start,size);
                _list = db.verifyAddress(val);
                if(_list.isEmpty()){
                    start ++;
                }else{
                    break;
                }
            }
            if(start < size){
                if(_list.size() == 1){
                    String[] vals = _list.get(0).split(",");
                    if(vals.length>=1){
                        data.province = vals[0];
                    }
                    if(vals.length>=2){
                        data.city = vals[1];
                    }
                    if(vals.length>=3){
                        data.county = vals[2];
                    }
                    
                }
                
                //switch(index){
                //    case 0: data.province = val;break;
                //    case 1: data.city = val;break;
                //    case 2: data.county = val;break;
                //}
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
                Set set = keywords.mapKeys.get(5).entrySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    Entry<String,String> bd = (Entry<String,String>)it.next();
                    if(_val.indexOf(bd.getValue()) >= 0){
                        data.Road = _val + val;
                        _val = "";
                    }
                }
            }
        }
        
        for(int j=0; j<_val.length(); j++){
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
    */
    /*
    private int getLevelOfKey(KeyValue words){
        int level = -1;
        int len = 0;
        //计算关键词的最大长度
        for(int i=0; i<mapKeys.size(); i++){
            Set set = mapKeys.get(i).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,String> bd = (Entry<String,String>) it.next();
                int _len = bd.getValue().length();
                if(_len>len)
                    len = _len;
            }
        }        
        for(int j=len;j>0;j--){        
            for(int i=0; i<mapKeys.size(); i++){
                Set set = mapKeys.get(i).entrySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    Entry<String,String> bd = (Entry<String,String>) it.next();
                    if(bd.getValue().length() == j){
                        int index = words.value.indexOf(bd.getValue());
                        if(index != -1){
                            words.key = bd.getValue();
                            return i;
                        }
                    }
                }
            }
        }
        return level;            
    }
    */
    
    /*
    private int getNextLevelByKey(String key,int level,int oldLevel){
        for(int i = level + 1; i < mapKeys.size(); i++){
            if(mapKeys.get(i).containsKey(key))
                return i;
        }
        return oldLevel;
    }
    */

    /*
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
    */

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
    
    /*
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
    */
    
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

        for(int i=0;i<refKeywords.keyRefObject.length;i++){
            if(data.reference.indexOf(refKeywords.keyRefObject[i])!=-1)
                data.refObject = refKeywords.keyRefObject[i];
        }
        if(!data.refObject.isEmpty())
            data.refRelation = "交";
        for(int i=0;i<refKeywords.keyRefDistance.length;i++){
            if(data.reference.indexOf(refKeywords.keyRefDistance[i])!=-1)
                data.refDistance = refKeywords.keyRefDistance[i];
        }        
        for(int i=0;i<refKeywords.keyRefDirection.length;i++){
            if(data.reference.indexOf(refKeywords.keyRefDirection[i])!=-1)
                data.refDirection = refKeywords.keyRefDirection[i];
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
        System.out.print(" 省：" +   _data.province + " - 市:" + _data.city + " - 县:" + _data.county + " - 镇:" +  _data.township + " - 村:" +   _data.village); 
        System.out.print(" - 路:" +   _data.Road + " - NO:" + _data.RoadNo); 
        System.out.println(" - 楼 B:" +   _data.building  + " - FL:" + _data.floor + " - ROOM:" + _data.room); 
//        System.out.print(" 参照 R:" +   _data.reference + " : " +_data.flag); 
//        System.out.println(" -不确定:" +   _data.other + "addressExtractor dispData"); 
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
        String words= "";

        words = "牌楼巷47-1号";                                                 list.add(words);
        words = "景德镇人民路";                                                          list.add(words);

        words = "天津市津南区小站镇米立方海世界水上乐园(津歧路与盛塘路交汇处)";                 list.add(words);
        words = "北京经济技术开发区永昌北路9号西门（北京银行东侧，与可口可乐公司隔街相望）";    list.add(words);
        words = "南京江宁汤山镇温泉路9号";                                                      list.add(words);
        words = "坝头洲畔村大山沟永泰路东区";                                                   list.add(words);
//        words = "三北大街机电宿舍";                                                             list.add(words);
        words = "新泰路六福二巷";                                                               list.add(words);
        words = "市桥街大罗村东约新村四巷";                         list.add(words);
//        words = "真光路1288号百联购物广场";                         list.add(words);

        words = "天山路900号汇金百货内近娄山关路";                  list.add(words);
  
        words = "中山大道江汉路happy站台A079号";                    list.add(words);
       
        words = "黄浦西藏中路268号来福士广场内";                    list.add(words);
        words = "锦龙支路金凤大厦北侧";                             list.add(words);
//        words = "启航城18-23号";                                    list.add(words);
        words = "大渡河路452号国盛中心B1楼";                        list.add(words);
//        words = "柳州路427号近漕宝";                                list.add(words);
        words = "上海南站地下商场南馆地下一层B区南1-B2号";          list.add(words);
        words = "香花桥街道香大路1379弄16号";                       list.add(words);
//        words = "南京西路938号(南京西路泰兴路口)";                  list.add(words);        
//        words = "向阳河路889号";                                    list.add(words);
        words = "广元西路317号金山商务楼3楼f座";                    list.add(words);
        words = "顺昌路622号216室";                                 list.add(words);
        words = "浦三路159号三楼近临沂路";                          list.add(words);

        words = "南翔镇古猗园路158弄5号401室";                      list.add(words);
        words = "南京东路800号市百一店东楼17层";                    list.add(words);
        words = "江东北路218号新城市假日广场(龙园南路口)";          list.add(words);

        words = "白下区中山路55号新华大厦33楼A座(近中山东路)";      list.add(words);
        words = "江苏南京白下区淮海路68号苏宁电器一楼3C服务中心 ";  list.add(words);
        words = "南京西路1376号上海商城内(铜仁路)";                 list.add(words);
//        words = "罗山路1609号";                                     list.add(words);
 
        words = "南京鼓楼广州路173号";                              list.add(words);
        
        words = "南京市鼓楼（原下关区）幕府西路118号";              list.add(words);

        words = "河北省邢台市大曹庄管理区大曹庄乡";                 list.add(words);

        words = "河北省承德市宽城满族自治县宽城镇";                 list.add(words);
       
        words = "河北省保定市涞水县城区社区管理办公室";             list.add(words);
        words = "河北省保定市南市区南大园乡";                       list.add(words);
        words = "河北省石家庄市灵寿县青同镇";                       list.add(words);
          
        words = "河北省承德市市辖区";                               list.add(words);
        
        words = "北京市朝阳区孙河地区";                             list.add(words);
        words = "上海黄浦黄浦江";                                   list.add(words);
        words = "鼓楼区新模范马路地铁口200米";                      list.add(words);
        words = "陕西南路145号(近南昌路)";                          list.add(words);
        words = "新疆维吾尔自治区伊犁哈萨克自治州伊宁市";           list.add(words);
         
        words = "东城区东直门内大街253号";                          list.add(words);
        words = "鼓楼区湖南路27号103室(近八佰伴)";                  list.add(words);
        words = "东山步行街(近竹山路)";                             list.add(words);
        words = "南京南京市区天元西路199号(近静淮路)";              list.add(words);
        
        words = "金沙江西路";                                       list.add(words);
        
        words = "玄武区中山陵四方城8号(钟山风景名胜区内)";          list.add(words);
        words = "高楼门62号(近玄武门)";                             list.add(words);
        words = "白下区蓝旗街创意东8区(蓝旗街与光华东街)";          list.add(words);        
        words = "鼓楼区大方巷(近苏果便利)";                         list.add(words);
        
        words = "近郊栖霞区中山门外马群狮子坝村168号(近文澜路";     list.add(words);        
        words = "右安门外大街2号院迦南大厦10楼(右安门桥东南侧";     list.add(words);
        words = "昌平区小汤山环岛东(大东留村北)";                   list.add(words);          
        words = "鼓楼地铁站L20商铺(3号出口斜对面)";                 list.add(words);    
        words = "江苏省南京市紫金山";                               list.add(words); 
        words = "南京市丹凤街丹凤新寓4栋14楼1409室";                list.add(words);
        
        words = "江苏省南京市红山路百通公寓1层";                    list.add(words); 
        
        words = "近郊平谷镇罗营玻璃台新村21号";                     list.add(words);  
        words = "朝阳区建国门外大街乙12号LG双子座大厦4层";          list.add(words); 
        words = "朝阳区三里屯西五街5号C座一层";                     list.add(words);
        words = "朝阳区白家庄东里23号锦湖园公寓会所2层";            list.add(words);    
        words = "长安区中山东路与广安大街交叉口西行20米路北(";      list.add(words);
        
        words = "中山东港新区金广东海岸滨城国际俱乐部";             list.add(words);
      
        words = "呼和浩特乌兰察布东路附近";                         list.add(words);
        words = "呼和浩特新城区兴安北路附近";                       list.add(words);
        words = "乌鲁木齐经济技术开发区中亚北路134号";              list.add(words);
        words = "乌鲁木齐克拉玛依西路20号附2号";                    list.add(words);
        words = "南昌高新开发区京东大道浙大科技园B区-2栋";          list.add(words);
        //新华区和平西路与中华北大街口交叉口西侧(省文
        //桥西区和平西路与友谊北大街交叉口东行50米路南
        //龙华新区大浪龙华新区和平西路与新区大道汇处（地铁龙胜站附近）
        //石家庄和平西路与友谊大街家口建设银行三楼321
  
        words = "朝阳区和平东街青年沟东路13区1号院内";              list.add(words);
        //近郊石家庄平山温塘镇
       
        words = "滨海新区泰达MSD滨海新区开发区新城东路48号（广达街和广场西路交汇处）";list.add(words);
        //新城区新华东街与长乐宫十字路口东北角(近国美
        words = "昆明高新开发区昌源中路47号";                       list.add(words);

        words = "长安区桥西中华南大街北角269号4楼(中国移动东";      list.add(words);
        words = "高新天府新城高新区益州大道中段盛邦街（铁像寺水街旁）";             list.add(words);
        words = "云岩贵阳市延安中路48号";                                   list.add(words);
        
        words = "石家庄市中华北大街保龙仓超市对面化纤路9号";                list.add(words);
        words = "中华南大街保龙仓超市1楼(槐安路口)";                        list.add(words);
        words = "新城区大南街光彩市场";                                     list.add(words);
        words = "内蒙古呼和浩特市内蒙古统";                                 list.add(words);
        words = "内蒙古呼和浩特市中山西路25号工人文化宫3楼";                list.add(words);
        words = "内蒙古师范大学生物楼";                                     list.add(words);
        
        words = "呼和浩特锡林北路97号内蒙古群艺馆二楼";                     list.add(words);     
  
        words = "山西太原市小店区汾东北路185号(太原";                       list.add(words);
        words = "广西南宁市北际路5号同济医院";                              list.add(words);        
        words = "新市区河南西路270号(近铁路局23街)";                        list.add(words);
        
        words = "新疆省乌鲁木齐市克拉玛依东路9号中国城大厦8楼";             list.add(words);
        words = "昆山市周庄镇大桥路497号";             list.add(words);
        words = "近郊晋州市东胜街152号";             list.add(words); 
       
        words = "河北省石家庄市鹿泉市玉石路";             list.add(words);
        words = "河北省石家庄市鹿泉市011县道";              list.add(words); 
         
        words = "河北省藁城市市区西部";                     list.add(words);
        words = "包河区芜湖路97号省体育局南村21栋1101号铺(近";list.add(words);
        words = "乌鲁木齐市长江路乌鲁木齐市商业银行(碾子沟)";list.add(words);
        words = "三孝口永红路小学以及市内各教学点报名";         list.add(words);
        words = "河北省石家庄市新乐市京新街";                   list.add(words);
        words = "城关区娘热路拉萨市环保局商品房C座1号";         list.add(words);
        words = "五一路新屋菜市129号伍越商贸城";                list.add(words);
        words = "河北省石家庄市石家庄市石家庄市和平东路689号";  list.add(words);
        words = "胜利路长春都市豪庭大门口东侧";                 list.add(words);
        words = "胜利大街东侧,省胸科医院对面";                  list.add(words);
       
        words = "双乐园105幢106室";                             list.add(words);
        words = "南京市北门桥10号";                             list.add(words);
         
        words = "东鼎大厦2号五层";                             list.add(words);

        words = "近郊东八区创意园蓝旗街";                      list.add(words);
        words = "黄浦区山西南路99号";                      list.add(words);
        words = "南京市江宁区汤山温泉路5号(沪宁高速汤山出口)";                      list.add(words);
        words = "浦口区黄山岭老山国家森林公园";                      list.add(words);
        words = "玄武区中山陵景区内";                      list.add(words);

        words = "溧水区经济开发区机场路7号";                      list.add(words);
        words = "雨花台区铁心桥春江新城秦河坊11幢102.103室";                      list.add(words);
        
        
        words = "御道街29号";                      list.add(words);
        words = "龙眠大道639号";                      list.add(words);
        words = "大石桥28号";                      list.add(words);

        words = "鼓楼大街5-1";                      list.add(words);
        words = "车市巷12号(第二十中学)";                      list.add(words);
        words = "乌市河南西路2号";                      list.add(words);
         
        words = "乌鲁木齐乌市红山路132号";                      list.add(words);
        words = "肥市蜀山区望江西路世纪新城小区5栋2单元603室";                      list.add(words);

        words = "乌鲁木市沙区长江路556号新奇广场对面";                      list.add(words);
        words = "乌市七道湾村八道湾5队";                      list.add(words);
        words = "乌市西后街81号";                      list.add(words);
        //雨花台区铁心桥春江新城秦河坊11幢102.103室
        //肥市蜀山区望江西路世纪新城小区5栋2单元603室
        
//        System.out.println(words);昌平区小汤山环岛东(大东留村北)
//        LocalInfo _data = new LocalInfo();
        //DSComposite _info = new DSComposite();
        AddressDataBase db = new AddressDataBase(); 
//        Extractor.parseAddress(words, _data, db);
        
        AddressExtractDirect0 extractor  = new AddressExtractDirect0();
        EvalutionValue mode = new EvalutionValue();
        for(int i=0;i<list.size();i++){
            DSComposite _info = new DSComposite();
//            System.out.println(list.get(i));
            extractor.parseAddress(list.get(i), _info, db,mode);
        }

    }    
       
}
