/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.composite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
//import wuit.common.dictionary.address.DSAddress;
import wuit.common.dictionary.address.ServiceAddress;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class CategoryComposite extends Thread {
    Map<String,Map<String,String>> mapItems = new HashMap<String,Map<String,String>>();
    Map<String,String> mapNulls = new HashMap<String,String>();
    CompositeConvert convert = new CompositeConvert();
    int itemMax = 1000;
    String path ="";
    String pathNull ="";
    ServiceAddress srvAddress;
    
    public int running  = 1;
    public static List<DSComposite> listItems;
    
    public synchronized void addItems(List<DSComposite> items){
        listItems.addAll(items);
    }
    
    public synchronized DSComposite getItem(){
        DSComposite val = null;
        if(!listItems.isEmpty()){
            val = listItems.get(0);
            listItems.remove(0);
        }        
        return val;
    }
    
    public synchronized void setRunning(int runState){
        running = runState;
    }
    
    public CategoryComposite(String savePath,int countMax){
        path = savePath;
        pathNull = path + "\\Null\\";
        itemMax = countMax;
        srvAddress = new ServiceAddress();
    }   
    
    public synchronized void saveItems(){
        String key ="";
        Set set = mapItems.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,Map<String,String>> body = (Map.Entry<String,Map<String,String>>)it.next();
            if (body.getValue().size()>itemMax){
                key = body.getKey();
                String[] keys = key.split("\\\\");
                String fileName = keys[keys.length-1];
                FileIO.writeAsTxtMap(path + "\\" + key, fileName + "_" , body.getValue());
                body.getValue().clear();
            }
        }
        if(mapNulls.size()>itemMax){
            FileIO.writeAsTxtMap(pathNull + "\\", "null" + "_", mapNulls);
            mapNulls.clear();
        }
   }    

    private void extractDistric(DSComposite item){
        
  //      DSAddress addr = srvAddress.parseAddress(item.address);
  //      item.province = addr.district.province;
  //      item.city = addr.district.city;
  //      item.county = addr.district.county;
  //      item.phone_code = addr.phone;
  //      item.postcode = addr.post;
    }
    
    /*
    public void CategoryItem(DSComposite item){
        String key = "";
        if(item.address.isEmpty())
            return;       
        extractDistric(item);
        
        String val = convert.DSCompositeToString(item);
        if(item.province.equals("")){
            if(!item.city.equals("")){
                if (item.city.indexOf("|") == -1){
                    if(!item.county.equals("")){
                        if(item.county.indexOf("|") == -1){
                            key = item.province + "\\"+ item.city +"\\"+ item.county + "\\";
                        }else{key = item.province + "\\"+ item.city +"\\";}
                    }else{key = item.province + "\\"+ item.city +"\\";}
                }else{key = item.province + "\\"; }
            }else{key = item.province + "\\";}
            if (mapItems.containsKey(key)){
                if(!mapItems.get(key).containsKey(val))
                    mapItems.get(key).put(val, val);
            }else{
                Map<String,String> mapVal = new HashMap<String,String>();
                mapVal.put(val, val);
                mapItems.put(key, mapVal);
            }
        }else{
            if(!mapNulls.containsKey(val) && val!=null){mapNulls.put(val, val);}
        }
   }     
   */
    
    /*
    public void run(){
        try {
            while(running == 1){
                DSComposite val = getItem();
                if(val != null){
                    CategoryItem(val);
                    saveItems();
                }
                Thread.sleep(10);
            }
            String key = "";
            Set set = mapItems.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,Map<String,String>> body = (Map.Entry<String,Map<String,String>>)it.next();
                if (body.getValue().size()>itemMax){
                    key = body.getKey();
                    String[] keys = key.split("\\\\");
                    String fileName = keys[keys.length-1];
                    FileIO.writeAsTxtMap(path + "\\" + key, fileName + "_" , body.getValue());
                    body.getValue().clear();
                }
            }
            if(mapNulls.size()>itemMax){
                FileIO.writeAsTxtMap(pathNull + "\\", "null" + "_", mapNulls);
                mapNulls.clear();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CategoryComposite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
}
    
