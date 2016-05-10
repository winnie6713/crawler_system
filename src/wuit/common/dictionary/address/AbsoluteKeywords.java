/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.database.PageLocalInfo;
import wuit.crawler.database.SemanticDB;

/**
 *
 * @author lxl
 */
public class AbsoluteKeywords {
    public List<Map<String,String>> mapKeys = new ArrayList<Map<String,String>>();    
    public String keysAll = "";
    
    public AbsoluteKeywords(){
        SemanticDB db = new SemanticDB();
        List<PageLocalInfo> rs = new ArrayList<PageLocalInfo>();
        db.SelectKeywords0(rs);
        
        int maxKeyLen = 0;
        Map<String,String> mapKey = new HashMap<String,String>();
        for(int i=0; i<rs.size();i++){
            String[] vals = rs.get(i).content.split(",");
            int len = vals.length;
            Map<String,String> map = new HashMap<String,String>();
            
            for(int j=0; j<len; j++){
                map.put(vals[j], vals[j]);
                
                if(!mapKey.containsKey(vals[j])){
                    mapKey.put(vals[j], vals[j]);
                }
                if(maxKeyLen<vals[j].length()) 
                    maxKeyLen = vals[j].length();
            }
            mapKeys.add(map);
        }
        
        for(int j= maxKeyLen;j>0;j--){
            Set set = mapKey.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,String> bd = (Map.Entry<String,String>) it.next();
                if(bd.getValue().length() == j){
                    if(bd.getValue().length()==1){
                        keysAll = keysAll + bd.getValue() + "|";
                    }
                    else{
                        keysAll = keysAll + "("+ bd.getValue() + ")|";
                    }
                }
            }
        }        
    }
    
    public int getMinLevelByKey(KeyValue words){
        int level = -1;
        int len = 0;
        //计算关键词的最大长度
        for(int i=0; i<mapKeys.size(); i++){
            Set set = mapKeys.get(i).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,String> bd = (Map.Entry<String,String>) it.next();
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
                    Map.Entry<String,String> bd = (Map.Entry<String,String>) it.next();
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
    
    public int getNextLevelByKey(String key,int level,int oldLevel){
        for(int i = level + 1; i < mapKeys.size(); i++){
            if(mapKeys.get(i).containsKey(key))
                return i;
        }
        return oldLevel;
    }    
    
    
    public int getMoreLevelByKey(String key,int level){
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
}
