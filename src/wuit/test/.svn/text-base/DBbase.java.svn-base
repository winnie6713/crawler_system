/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author lxl
 */
public class DBbase {
    static class KeyName{
        String key;
        String id;
    }
    public static void addList(String key,String id){
        KeyName name = new KeyName();
        name.key = key;
        name.id = id;
        list.add(name);
    }
    public static void delList(int index){
        if(index<list.size())
            list.remove(index);
    }

    public static void delList(KeyName obj){
            list.remove(obj);
    }    
    public static List<KeyName> list = new ArrayList<KeyName>();
    
    
    public static Map<String,String> map = new HashMap<String,String>();
    
    public static AddressName address = new AddressName();
    
    public static void main(String[] args){
        DBbase.addList("1", "qqq");
        DBbase.addList("2", "www");
        DBbase.addList("3", "aaa");
        for(int i=0;i<DBbase.list.size();i++){
            System.out.println(DBbase.list.get(i).key + " " + DBbase.list.get(i).id);
        }
        
        DBbase.delList(DBbase.list.get(0));
        for(int i=0;i<DBbase.list.size();i++){
            System.out.println(DBbase.list.get(i).key + " " + DBbase.list.get(i).id);
        }
        
        DBbase.map.put("1", "1");
        DBbase.map.put("2", "2");
        DBbase.map.put("3", "3");
        
        Set set = DBbase.map.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,String> body = (Entry<String,String>)it.next();
            System.out.println(body.getKey() + " " + body.getValue());
        }
        
        List<String> list = address.getKey("武汉市");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
