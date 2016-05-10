/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.test;

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
import java.util.logging.Level;
import java.util.logging.Logger;
//import wuit.common.crawler.search.BaiDuData;
//import wuit.common.crawler.search.BaiDuVerify;
import wuit.common.crawler.search.CrawlerBaidu;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class AddressName {
//   String path_base_province = "D:\\product\\lib\\Address\\district\\province\\";
//   String path_base_city = "D:\\product\\lib\\Address\\district\\city\\";   
   static String path_base_china = "D:\\product\\lib\\Address\\phone\\tempPhone\\";
   
   static Map<String,String> mapKeys = new HashMap<String,String>();
   
   public AddressName(){    
       Module2.Initialize();
       
      mapKeys.put("路","路");
      mapKeys.put("街","街");
      mapKeys.put("镇","镇");
      mapKeys.put("村","村");
      mapKeys.put("巷","巷");
      mapKeys.put("区","区");
      mapKeys.put("湾","湾");
      mapKeys.put("弄","弄");
      mapKeys.put("道","道");
      mapKeys.put("号","号");
      mapKeys.put("楼","楼");
      mapKeys.put("室","室");
      mapKeys.put("场","场");
      mapKeys.put("店","店");
      mapKeys.put("_","_");
      
      mapKeys.put("县","县");
      mapKeys.put("区","区");
      mapKeys.put("市","市");
      mapKeys.put("省","省");
   }
   
   class AddressDict{
   
        class Address{
            List<String> pKeys = new ArrayList<String>();
            List<Route> router = new ArrayList<Route>();
        }

        class Route{
            String pKey;
            String cKey; 
        }
        Map<String,Address> mapAddress = new HashMap<String,Address>();
        
        class Phone{
            String phone;
            Map<String,Phone> map = new HashMap<String,Phone>();
        }
        
        Map<String,Phone> mapPhone = new HashMap<String,Phone>();
        
        class Post{
            String post;
            Map<String,Post> map = new HashMap<String,Post>();
        }
        
        Map<String,Post> mapPost = new HashMap<String,Post>();

        public void Initialize(){
            List<String> lstFiles = new ArrayList<String>();
            List<String> dirs = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(path_base_china, lstFiles, dirs);
            System.gc();
            
           long freeMem1 = Runtime.getRuntime().freeMemory();
           long totalMem1 = Runtime.getRuntime().totalMemory();
           long maxMem1 = Runtime.getRuntime().maxMemory();
           
           System.out.println("module1 memory !!!          ");
           System.out.print(freeMem1/1024/1024  + " MB      ");
           System.out.print(totalMem1/1024/1024  + " MB     ");
           System.out.println((totalMem1-freeMem1)/1024/1024 + " MB     ");            
            
            try{
                 for (int i = 0; i<lstFiles.size(); i++){
                       String pathFile = lstFiles.get(i);
                       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
                       String c = br.readLine();
                       while(c != null){
                           putRecodeNode(c);
                           c =br.readLine();
                       }
                 }
            }catch(Exception e){
               System.out.println(e.getMessage());
           }
           System.gc();
           
           long freeMem2 = Runtime.getRuntime().freeMemory();
           long totalMem2 = Runtime.getRuntime().totalMemory();
           long maxMem2 = Runtime.getRuntime().maxMemory();
           
           
           System.out.print(freeMem2/1024/1024  + " MB      ");
           System.out.print(totalMem2/1024/1024  + " MB     ");
           System.out.print((totalMem2-freeMem2)/1024/1024 + " MB     ");   
           
           System.out.println((freeMem1 - freeMem2 + totalMem2 - totalMem1)/1024 + " KB"); 
        }         
        
        private void putRecodeNode(String vals){
            String valArr[] = vals.split(",");
            String post = valArr[5];
            String phone = valArr[4];
            
            List<String> phoneWords = new ArrayList<String>();
            phoneWords.add(valArr[0]);
            if(valArr[1].equals("辖区") || valArr[1].equals("辖县"))
                valArr[1] = valArr[0] + valArr[1];
            phoneWords.add(valArr[1]);
            phoneWords.add(valArr[2]);
            
            putPhone(phoneWords,phone);
            putPost(phoneWords,post);
            
            String word = valArr[0];
            String pWord = "";
            String ppWord = "";
            putAddress(word,pWord,ppWord,phone,post,0);
            if(word.indexOf("省")>=0 || word.indexOf("市")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putAddress(s_word,pWord,ppWord,phone,post,0);
            }
            if( word.indexOf("自治区")>=0){
                String s_word = word.substring(0,word.length()-3);
                putAddress(s_word,pWord,ppWord,phone,post,0);
            }

            ppWord = pWord;
            pWord = word;
            word = valArr[1];

            if(word.indexOf("辖区")>0 || word.indexOf("辖县")>0){                
                putAddress(word,pWord,ppWord,phone,post,1);
            }else{
                putAddress(word,pWord,ppWord,phone,post,1);

                if(word.indexOf("区")>=0 || word.indexOf("市")>=0 ){
                    String s_word = word.substring(0,word.length()-1);
                    if(s_word.length()>1)
                        putAddress(s_word,pWord,ppWord,phone,post,1);
                }
                if( word.indexOf("自治州")>=0){
                    String s_word = word.substring(0,word.length()-3);
                    putAddress(s_word,pWord,ppWord,phone,post,1);
                }
            }

            ppWord = pWord;
            pWord = word;
            word = valArr[2];

            putAddress(word,pWord,ppWord,phone,post,2);
            if(word.indexOf("区")>=0 || word.indexOf("市")>=0 || word.indexOf("县")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putAddress(s_word,pWord,ppWord,phone,post,2);
            }
            if( word.indexOf("自治县")>=0){
                String s_word = word.substring(0,word.length()-3);
                putAddress(s_word,pWord,ppWord,phone,post,2);
            }              


            ppWord = pWord;
            pWord = word;
            word = valArr[3];
            word = word.replace("(", "").replace(")", "");
            //word = "凤山镇下村街";
            int size = word.length();
            int s = 0;
            for(int i=1;i<=size;i++){
                 if(i==size){
                     String _w = word.substring(s,i);
                     int f = 0;
                     Set set = mapKeys.entrySet();
                     Iterator it = set.iterator();
                     while(it.hasNext()){
                         Entry<String,String> _body = (Entry<String,String>)it.next();
                         if(_w.indexOf(_body.getKey())>0)
                             f = 1;
                     }
                     if (f == 1){
                        putAddress(_w,pWord,ppWord,phone,post,3);
                        ppWord =  pWord;
                        pWord = _w;
                     }
                 }else{
                     if(mapKeys.containsKey(word.substring(i-1, i)) && !mapKeys.containsKey(word.substring(i, i+1))){
                        String _w = word.substring(s,i);
                        putAddress(_w,pWord,ppWord,phone,post,3);   
                        ppWord =  pWord;
                        pWord = _w;
                        s = i;
                    }
                }
            }
        }      

        private void putAddress(String key,String pWord,String ppWord,String phone,String post,int level){
            if(!mapAddress.containsKey(key)){
                Address node = new Address();
                node.pKeys.add(pWord);
                mapAddress.put(key, node);
                if(pWord.equals("") || ppWord.equals(""))
                    return;
                Address pNode = mapAddress.get(pWord);
                int f =0;
                for(int i=0;i<pNode.router.size();i++){
                    if(pNode.router.get(i).pKey.equals(ppWord)){
                        if(pNode.router.get(i).cKey.indexOf(key)==-1){
                            pNode.router.get(i).cKey = pNode.router.get(i).cKey  + key;
                        }
                        f = 1;
                        break;
                    }
                }
                if(f == 0){
                      Route route = new Route();
                      route.pKey = ppWord;
                      route.cKey = key;
                      pNode.router.add(route);
                }
            }else{           
                Address node = mapAddress.get(key);
                int size = node.pKeys.size();
                int f = 0;
                for (int i=0;i<size;i++){
                    if(pWord.equals(node.pKeys.get(i))){
                        f = 1;
                        break;
                    }
                }
                if(f == 0){
                    node.pKeys.add(pWord);
                }
                
                Address pNode = mapAddress.get(pWord);
                if(pNode == null || ppWord.equals(""))
                    return;
                size = pNode.router.size();
                f = 0;
                for(int i=0;i<size;i++){
                    String pVal = pNode.router.get(i).pKey;
                    String cVal = pNode.router.get(i).cKey;
                    if(pVal.equals(ppWord) ){
                        if(cVal.indexOf(key) == -1){
                            pNode.router.get(i).cKey = pNode.router.get(i).cKey  + key;
                        }
                        f = 1;
                        break;
                    }
                }
                if(f == 0 ){
                    Route route = new Route();
                    route.pKey = ppWord;
                    route.cKey = key;
                    pNode.router.add(route);
                }
            }       
        }  
   
        private void putPhone(List<String> keys,String phone){
            for(int i = 0; i< keys.size();i++){
                if(i == 0){
                    if(!mapPhone.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i), p);                    
                    }
                }
                if(i == 1){
                    if(!mapPhone.containsKey(keys.get(i-1))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i-1), p);                    
                    }
                    Phone pPhone = mapPhone.get(keys.get(i-1));
                    if(!pPhone.map.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i), p);                                            
                    }
                }
                if(i == 2){
                    if(!mapPhone.containsKey(keys.get(i-2))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i-1), p);                    
                    }
                    Phone pPhone = mapPhone.get(keys.get(i-2));
                    if(!pPhone.map.containsKey(keys.get(i-1))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i-1), p);                                            
                    }                    
                    pPhone = pPhone.map.get(keys.get(i-1));
                    if(!pPhone.map.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i), p);                                            
                    }
                }                
            }
        }
        
        private void putPost(List<String> keys,String post){
            for(int i = 0; i< keys.size();i++){
                if(i == 0){
                    if(!mapPost.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i), p);                    
                    }
                }
                if(i == 1){
                    if(!mapPost.containsKey(keys.get(i-1))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i-1), p);                    
                    }
                    Post pPost = mapPost.get(keys.get(i-1));
                    if(!pPost.map.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i), p);                                            
                    }
                }
                if(i == 2){
                    if(!mapPost.containsKey(keys.get(i-2))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i-1), p);                    
                    }
                    Post pPost = mapPost.get(keys.get(i-2));
                    if(!pPost.map.containsKey(keys.get(i-1))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i-1), p);                                            
                    }                    
                    pPost = pPost.map.get(keys.get(i-1));
                    if(!pPost.map.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i), p);                                            
                    }
                }                
            }
        }
        
        private String getParentNode(String pKey){
            Address pNode = mapAddress.get(pKey);
            if(pNode.router.size()>0){
                for(int i=0;i<pNode.router.size();i++){
                        Address _pNode = mapAddress.get(pNode.router.get(i).pKey);
                        if(_pNode == null || pNode.router.isEmpty()){
                            return pKey;
                        }else{
                            return getParentNode(pNode.router.get(i).pKey) + ";" + pKey;
                        }
                }
            }else{
                for(int i=0;i<pNode.pKeys.size();i++){
                    Address _pNode = mapAddress.get(pNode.pKeys.get(i));
                    if(_pNode == null || pNode.pKeys.get(i).equals("")){
                        return pKey;
                    }else{
                        return getParentNode(pNode.pKeys.get(i)) + ";" + pKey;
                    }
                }
            }
            return "";
        }
        
        private List<String> getKey(String key){
           String val = "";
           List<String> address = new ArrayList<String>();
           if(mapAddress.containsKey(key)){
               Address node = mapAddress.get(key);
               for(int i=0;i<node.pKeys.size();i++){
                    Address pNode = mapAddress.get(node.pKeys.get(i));
                    val = getParentNode(node.pKeys.get(i)) + ";" + key;
                    String val1 = getPhone(val) + ";" + val;
                    String val2 = getPost(val) + ";" + val1;
                    System.out.println("module1: " + val2);
                    address.add(val2);
               }
           }
           return address;
        }  
        
        private String getPhone(String address){
           String[] vals = address.split(";");
           int size = vals.length;
           String phone = "";
           Phone p0 = new Phone();
           Phone p1 = new Phone();
           Phone p2 = new Phone();
           if(vals.length>0){
                p0 = mapPhone.get(vals[0]);
                if(p0 != null && phone.indexOf(p0.phone)== -1)
                    phone = p0.phone;
           }
           if(vals.length>1 && p0 != null){               
               p1 = p0.map.get(vals[1]);
               if(p1 != null && phone.indexOf(p1.phone) == -1)
                   phone =  p1.phone;
           }
           if(vals.length>2 && p1 != null){
                p2 = p1.map.get(vals[2]);
               if(p2 != null && phone.indexOf(p2.phone) == -1)
                   phone =  p2.phone;                
           }
           return phone;
        }
        
        private String getPost(String address){
           String[] vals = address.split(";");
           int size = vals.length;
           String post = "";
           Post p0 = new Post();
           Post p1 = new Post();
           Post p2 = new Post();
           Post p3 = new Post();
           if(vals.length>0){
                p0 = mapPost.get(vals[0]);
                if(p0 != null && post.indexOf(p0.post)== -1)
                    post = p0.post;
           }
           if(vals.length>1 && p0 != null){               
               p1 = p0.map.get(vals[1]);
               if(p1 != null && post.indexOf(p1.post) == -1)
                   post =  p1.post;
           }
           if(vals.length>2 && p1 != null){
                p2 = p1.map.get(vals[2]);
               if(p2 != null && post.indexOf(p2.post) == -1)
                   post =  p2.post;                
           }
           if(vals.length>3 && p2 != null){
                p3 = p1.map.get(vals[3]);
               if(p3 != null && post.indexOf(p3.post) == -1)
                   post =  p3.post;                
           }           
           return post;
        }
        
   }
   ///////////////////////////////////////////////
   static class Module2{
        static class Address{           
            List<String> pKeys = new ArrayList<String>();           //指向父到根地址
        }   

        static Map<String,Address> mapAddress = new HashMap<String,Address>();
        
        static class Phone{
            String phone;
            Map<String,Phone> map = new HashMap<String,Phone>();
        }
        
        static Map<String,Phone> mapPhone = new HashMap<String,Phone>();
        
        static class Post{
            String post;
            Map<String,Post> map = new HashMap<String,Post>();
        }
        
        static Map<String,Post> mapPost = new HashMap<String,Post>();        

        public static void Initialize(){
            List<String> lstFiles = new ArrayList<String>();
            List<String> dirs = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(path_base_china, lstFiles, dirs);
            System.gc();
            
           long freeMem1 = Runtime.getRuntime().freeMemory();
           long totalMem1 = Runtime.getRuntime().totalMemory();
           long maxMem1 = Runtime.getRuntime().maxMemory();
           
           System.out.println("module2 memory !!!          ");
           System.out.print(freeMem1/1024/1024  + " MB      ");
           System.out.print(totalMem1/1024/1024  + " MB     ");
           System.out.println((totalMem1-freeMem1)/1024/1024 + " MB     ");            
            
            try{
                 for (int i = 0; i<lstFiles.size(); i++){
                       String pathFile = lstFiles.get(i);
                       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
                       String c = br.readLine();
                       while(c != null){
                           putRecode(c);
                           c =br.readLine();
                       }
                 }
            }catch(Exception e){
               System.out.println(e.getMessage());
           }
           System.gc();
           
           long freeMem2 = Runtime.getRuntime().freeMemory();
           long totalMem2 = Runtime.getRuntime().totalMemory();
           long maxMem2 = Runtime.getRuntime().maxMemory();
           
           
           System.out.print(freeMem2/1024/1024  + " MB      ");
           System.out.print(totalMem2/1024/1024  + " MB     ");
           System.out.print((totalMem2-freeMem2)/1024/1024 + " MB     ");   
           System.out.println((freeMem1 - freeMem2 + totalMem2 - totalMem1)/1024 + " KB"); 

        }
        
        private static void putRecode(String vals){
            String valArr[] = vals.split(",");
            String post = valArr[5];
            String phone = valArr[4];
            
            List<String> phoneWords = new ArrayList<String>();
            phoneWords.add(valArr[0]);
            if(valArr[1].equals("辖区") || valArr[1].equals("辖县"))
                valArr[1] = valArr[0] + valArr[1];
            phoneWords.add(valArr[1]);
            phoneWords.add(valArr[2]);
            
            putPhone(phoneWords,phone);
            putPost(phoneWords,post);            

            String word = valArr[0];           
            String pWord="";
            putAddress(word,"",phone,post,0);
            if(word.indexOf("省")>=0 || word.indexOf("市")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putAddress(s_word,word,phone,post,0);
            }
            if( word.indexOf("自治区")>=0){
                String s_word = word.substring(0,word.length()-3);
                putAddress(s_word,word,phone,post,0);
            }
            
            pWord =  word;
            word = valArr[1];
            
            if(word.indexOf("辖区")>0 || word.indexOf("辖县")>0){                
                putAddress(word,pWord,phone,post,1);
            }else{
                putAddress(word,pWord,phone,post,1);

                if((word.indexOf("区")>=0 && word.length()>2)|| word.indexOf("市")>=0 ){
                    String s_word = word.substring(0,word.length()-1);
                    if(s_word.length()>1)
                        putAddress(s_word,pWord,phone,post,1);
                }
                if( word.indexOf("自治州")>=0){
                    String s_word = word.substring(0,word.length()-3);
                    putAddress(s_word,pWord,phone,post,1);
                }
            }
            pWord = pWord +  ";" + word;
            word = valArr[2];
            putAddress(word,pWord,phone,post,2);
            if(word.indexOf("区")>=0 || word.indexOf("市")>=0 || word.indexOf("县")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putAddress(s_word,pWord,phone,post,2);
            }
            if( word.indexOf("自治县")>=0){
                String s_word = word.substring(0,word.length()-3);
                putAddress(s_word,pWord,phone,post,2);
            }

            
            pWord = pWord +  ";" + word;
            word = valArr[3];
            word = word.replace("(", "").replace(")", "");
            //word = "凤山镇下村街";
            int size = word.length();
            int s = 0;
            for(int i=1;i<=size;i++){
                 if(i==size){
                     String _w = word.substring(s,i);                     
                     int f = 0;
                     Set set = mapKeys.entrySet();
                     Iterator it = set.iterator();
                     while(it.hasNext()){
                         Entry<String,String> _body = (Entry<String,String>)it.next();
                         if(_w.indexOf(_body.getKey())>0)
                             f = 1;
                     }
                     if(f == 1){
                        
                        putAddress(_w,pWord,phone,post,3);
                        pWord = pWord +  ";" + _w;
                     }
                 }else{
                     if(mapKeys.containsKey(word.substring(i-1, i)) && !mapKeys.containsKey(word.substring(i, i+1))){
                        String _w = word.substring(s,i);
                        
                        putAddress(_w,pWord,phone,post,3);   
                        pWord = pWord +  ";" + _w;
                        s = i;
                    }
                }
            }
        }   

        private static void putAddress(String key,String link,String phone,String post,int level){
            if(!mapAddress.containsKey(key)){
                Address node = new Address();
                if(!link.equals("")){
                     node.pKeys.add(link);
                }
                mapAddress.put(key, node);
            }else{
                Address node = mapAddress.get(key);
                int size = node.pKeys.size();
                int f = 0;
                for (int i=0;i<size;i++){
                    String val1 = node.pKeys.get(i);
                    if(link.equals(val1)){
                        f = 1;
                        break;
                    }
                }
                if(f==0 && !link.equals("")){
                    node.pKeys.add(link);
                }
            }       
        }

        private static void putPhone(List<String> keys,String phone){
            for(int i = 0; i< keys.size();i++){
                if(i == 0){
                    if(!mapPhone.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i), p);                    
                    }
                }
                if(i == 1){
                    if(!mapPhone.containsKey(keys.get(i-1))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i-1), p);                    
                    }
                    Phone pPhone = mapPhone.get(keys.get(i-1));
                    if(!pPhone.map.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i), p);                                            
                    }
                }
                if(i == 2){
                    if(!mapPhone.containsKey(keys.get(i-2))){
                        Phone p = new Phone();
                        p.phone = phone;
                        mapPhone.put(keys.get(i-1), p);                    
                    }
                    Phone pPhone = mapPhone.get(keys.get(i-2));
                    if(!pPhone.map.containsKey(keys.get(i-1))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i-1), p);                                            
                    }                    
                    pPhone = pPhone.map.get(keys.get(i-1));
                    if(!pPhone.map.containsKey(keys.get(i))){
                        Phone p = new Phone();
                        p.phone = phone;
                        pPhone.map.put(keys.get(i), p);                                            
                    }
                }                
            }
        }
        
        private static void putPost(List<String> keys,String post){
            for(int i = 0; i< keys.size();i++){
                if(i == 0){
                    if(!mapPost.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i), p);                    
                    }
                }
                if(i == 1){
                    if(!mapPost.containsKey(keys.get(i-1))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i-1), p);                    
                    }
                    Post pPost = mapPost.get(keys.get(i-1));
                    if(!pPost.map.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i), p);                                            
                    }
                }
                if(i == 2){
                    if(!mapPost.containsKey(keys.get(i-2))){
                        Post p = new Post();
                        p.post = post;
                        mapPost.put(keys.get(i-1), p);                    
                    }
                    Post pPost = mapPost.get(keys.get(i-2));
                    if(!pPost.map.containsKey(keys.get(i-1))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i-1), p);                                            
                    }                    
                    pPost = pPost.map.get(keys.get(i-1));
                    if(!pPost.map.containsKey(keys.get(i))){
                        Post p = new Post();
                        p.post = post;
                        pPost.map.put(keys.get(i), p);                                            
                    }
                }                
            }
        }        
        
        private static List<String> getParent(Address node,String val){
            String vals = val;
            List<String> rs = new ArrayList<String>();
            int size = node.pKeys.size();
            if(size == 0){
                rs.add(val);
                return rs;
            }
            for(int i=0;i<size;i++){
                vals =  node.pKeys.get(i)  + ";" + val;
                //System.out.println("module2: " + vals);
                String val1 = getPhone(vals) + ";" + vals;
                String val2 = getPost(vals) + ";" + val1;
//                System.out.println("module2: " + val2);
                rs.add(val2);
            }
            return rs;
        }
        
        private static String getPhone(String address){
           String[] vals = address.split(";");
           int size = vals.length;
           String phone = "";
           Phone p0 = new Phone();
           Phone p1 = new Phone();
           Phone p2 = new Phone();
           if(vals.length>0){
                p0 = mapPhone.get(vals[0]);
                if(p0 != null && phone.indexOf(p0.phone)== -1)
                    phone = p0.phone;
           }
           if(vals.length>1 && p0 != null){               
               p1 = p0.map.get(vals[1]);
               if(p1 != null && phone.indexOf(p1.phone) == -1)
                   phone =  p1.phone;
           }
           if(vals.length>2 && p1 != null){
                p2 = p1.map.get(vals[2]);
               if(p2 != null && phone.indexOf(p2.phone) == -1)
                   phone =  p2.phone;                
           }
           return phone;
        }
        
        private static String getPost(String address){
           String[] vals = address.split(";");
           int size = vals.length;
           String post = "";
           Post p0 = new Post();
           Post p1 = new Post();
           Post p2 = new Post();
           Post p3 = new Post();
           if(vals.length>0){
                p0 = mapPost.get(vals[0]);
                if(p0 != null && post.indexOf(p0.post)== -1)
                    post = p0.post;
           }
           if(vals.length>1 && p0 != null){               
               p1 = p0.map.get(vals[1]);
               if(p1 != null && post.indexOf(p1.post) == -1)
                   post =  p1.post;
           }
           if(vals.length>2 && p1 != null){
                p2 = p1.map.get(vals[2]);
               if(p2 != null && post.indexOf(p2.post) == -1)
                   post =  p2.post;                
           }
           if(vals.length>3 && p2 != null){
                p3 = p1.map.get(vals[3]);
               if(p3 != null && post.indexOf(p3.post) == -1)
                   post =  p3.post;                
           }           
           return post;
        }        
        
        private static List<String> getKey(String aName){
            List<String> rs = new ArrayList<String>();
           if(mapAddress.containsKey(aName)){
                Address node = mapAddress.get(aName);
                rs =  getParent(node, aName);
           }
           /*
           for(int i=0;i<rs.size();i++){
               System.out.println("module2 : " + rs.get(i));
           }
           */
           return rs;
        }        
   }
   
   /////////////
   class Module3{
        class HashName{
//           int post ;
//           int phone ; 
           List<String> pKeys = new ArrayList<String>();        //指向父地址
        }

        Map<String,HashName> mapName = new HashMap<String,HashName>();        
        public void Initialize(){
            List<String> lstFiles = new ArrayList<String>();
            List<String> dirs = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(path_base_china, lstFiles, dirs);
            System.gc();
            
           long freeMem1 = Runtime.getRuntime().freeMemory();
           long totalMem1 = Runtime.getRuntime().totalMemory();
           long maxMem1 = Runtime.getRuntime().maxMemory();
           
           System.out.println("module3 memory !!!          ");
           System.out.print(freeMem1/1024/1024  + " MB      ");
           System.out.print(totalMem1/1024/1024  + " MB     ");
           System.out.println((totalMem1-freeMem1)/1024/1024 + " MB     ");            
            
            try{
                 for (int i = 0; i<lstFiles.size(); i++){
                       String pathFile = lstFiles.get(i);
                       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"UTF-8"));
                       String c = br.readLine();
                       while(c != null){
                           putRecode(c);
                           c =br.readLine();
                       }
                 }
            }catch(Exception e){
               System.out.println(e.getMessage());
           }
           System.gc();
           
           long freeMem2 = Runtime.getRuntime().freeMemory();
           long totalMem2 = Runtime.getRuntime().totalMemory();
           long maxMem2 = Runtime.getRuntime().maxMemory();
           
           
           System.out.print(freeMem2/1024/1024  + " MB      ");
           System.out.print(totalMem2/1024/1024  + " MB     ");
           System.out.print((totalMem2-freeMem2)/1024/1024 + " MB     ");              
           System.out.println((freeMem1 - freeMem2 + totalMem2 - totalMem1)/1024 + " KB"); 
        }         

        private void putRecode(String vals){
            String valArr[] = vals.split(",");

            String word = valArr[0];
            String pWord = "";
            putName(word,pWord,1);
            if(word.indexOf("省")>=0 || word.indexOf("市")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putName(s_word,pWord,1);
            }
            if( word.indexOf("自治区")>=0){
                String s_word = word.substring(0,word.length()-3);
                putName(s_word,pWord,1);
            }

            pWord = word;
            word = valArr[1];
            
            putName(word,pWord,2);
            if((word.indexOf("区")>=0 && word.length()>2)|| word.indexOf("市")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                if(s_word.length()>1)
                    putName(s_word,pWord,2);
            }
            if( word.indexOf("自治州")>=0){
                String s_word = word.substring(0,word.length()-3);
                putName(s_word,pWord,2);
            }

            pWord = word;
            word = valArr[2];
            putName(word,pWord,3);
            if(word.indexOf("区")>=0 || word.indexOf("市")>=0 || word.indexOf("县")>=0 ){
                String s_word = word.substring(0,word.length()-1);
                putName(s_word,pWord,3);
            }
            if( word.indexOf("自治县")>=0){
                String s_word = word.substring(0,word.length()-3);
                putName(s_word,pWord,3);
            }

            pWord = word;
            word = valArr[3];
            word = word.replace("(", "").replace(")", "");
            //word = "凤山镇下村街";
            int size = word.length();
            int s = 0;
            for(int i=1;i<=size;i++){
                 if(i==size){
                     String _w = word.substring(s,i);
                     int f = 0;
                     Set set = mapKeys.entrySet();
                     Iterator it = set.iterator();
                     while(it.hasNext()){
                         Entry<String,String> _body = (Entry<String,String>)it.next();
                         if(_w.indexOf(_body.getKey())>0)
                             f = 1;
                     }                     
                     if(f == 1){
                        putName(_w,pWord,4);
                        pWord = _w;
                     }
                 }else{
                     if(mapKeys.containsKey(word.substring(i-1, i)) && !mapKeys.containsKey(word.substring(i, i+1))){
                        String _w = word.substring(s,i);
                        putName(_w,pWord,4);
                        pWord = _w;                
                        s = i;
                    }
                }
            }

        }

        private void putName(String key,String pKey,int level){
            if(mapName.containsKey(key) && level>=4){
                 HashName node = mapName.get(key);            
                 int f = 0;
                 for(int i=0;i<node.pKeys.size();i++){
                     if(node.pKeys.get(i).equals(pKey)){
                         f = 1;
                         break;
                     }
                 }
                 if(f == 0){
                     node.pKeys.add(pKey);
                 }            
            }else{
                HashName node = new HashName();
                if(pKey !=null )
                     node.pKeys.add(pKey);
                mapName.put(key, node);
            }
        }

        private void getParent(HashName node,String val){       
            if(node.pKeys.isEmpty()){
                System.out.println("module3: " + val);
                return;       
            }
            for(int i=0; i<node.pKeys.size();i++){
                String _val = val;
                if(node.pKeys.get(i) == null || node.pKeys.isEmpty() || node.pKeys.get(i).equals(""))
                     System.out.println("module3: " + _val);
                else{
                     _val = _val +  "," +  node.pKeys.get(i);
                     if(!node.pKeys.get(i).equals(""))
                         getParent(mapName.get(node.pKeys.get(i)),_val);
                }
            }       
        }
 
        private void getKey(String aName){
           if(mapName.containsKey(aName)){
                HashName n = mapName.get(aName);
                getParent(n,aName);
           }           
        }
        
        private void getSize(){
            try{
            Set set = mapName.entrySet();
            Iterator it = set.iterator();
            long size = 0;
            while(it.hasNext()){
                Entry<String, HashName> eNode = (Entry<String, HashName>)it.next();
                HashName node = eNode.getValue();
                size = size + node.pKeys.toString().getBytes().length;
            }
            System.out.println("module3 size : " + size);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
   }
   
   AddressDict module1 = new AddressDict();
   static Module2 module2 = new Module2();
   Module3 module3 = new Module3();   
   
   
   private List<String> diffAddress(String address){
       int size = address.length();
       List<String> lstAddress = new ArrayList<String>();
       int start = 0;
       for(int i= 0; i<size-1;i++){
           String key = address.substring(i,i+1);
           if(mapKeys.containsKey(key)){
               lstAddress.add(address.substring(start,i+1));
               start = i + 1;
           }
       }
       if(start<size){
           lstAddress.add(address.substring(start,size));
       }
       List<String> _address = new ArrayList<String>();
       for(int i=0; i<lstAddress.size(); i++){
           List<String> list = module2.getKey(lstAddress.get(i));
           if(list.size()>0)
               _address.addAll(list);
           else
               _address.add(lstAddress.get(i));
       }
       
       
       /*
       List<String> lstNew = new ArrayList<String>();
       diffWords(address,2,lstAddress,lstNew);
       diffWords(address,3,lstAddress,lstNew);
       */
       
       System.out.println("11 address");
       for(int i=0;i<lstAddress.size();i++){
           System.out.println(lstAddress.get(i));
       }
       
       System.out.println("22 address");
       for(int i=0;i<_address.size();i++){
           System.out.println(_address.get(i));
       }
       return _address;
   }
   
   private void diffWords(String address,int len,List<String> lstAddress,List<String> lstNew){
       String words = address;       
       int start = 0;
       while(words.length()>1){
           String key = words.substring(start,start + len);
           List<String> lst2 = module1.getKey(key);
           if(lst2.size() > 0){
               lstAddress.addAll(lst2);
               words = words.substring(start + len,words.length()-(start + len));
               if(start>0)
                   lstNew.add(words.substring(0,start));               
           }else{
               if(words.length()>start + len+1)
                   words = words.substring(len,words.length()-len);
           }
       }
       
       
   }
   
   
   public void Initialize(){
      Module2.Initialize();
//      module1.Initialize();
//      module3.Initialize();
   }   
   
   public static List<String> getKey(String val){
//       module1.getKey(val);
       return Module2.getKey(val);
//       module3.getKey(val);
   }
   
   class CountKey{
       String key;
       int count = 0;
   }
   
   Map<String,CountKey> mapWords = new HashMap<String,CountKey>();
   
   public void diffWord(String words,int lenMax){       
       int start = 0;
       int wordLen = words.length();
       int len = lenMax;
       int k = 1;
       if(wordLen<lenMax)
           len = wordLen;
       while(k<len){
            for(int i=0;i<wordLen - k + 1;i++){
                String w = words.substring(i,k+i);
                if(!mapWords.containsKey(w)){
                    CountKey keys = new CountKey();
                    keys.key = w;
                    keys.count = 1;
                    mapWords.put(w, keys);                    
                }else{
                    mapWords.get(w).count = mapWords.get(w).count + 1;
                }
            }
            k ++;
       }
       /*
       
       
       if(wordLen == len){
           if(!mapWords.containsKey(words)){
               CountKey keys = new CountKey();
               keys.key = words;
               keys.count = 1;
               mapWords.put(words, keys);
           }else{
               mapWords.get(words).count = mapWords.get(words).count + 1; 
           }
           return;
       }
       
       String w ="";
       while(wordLen - start>=len){
           w = words.substring(start,len+start);
           if(mapWords.containsKey(w)){
               mapWords.get(w).count = mapWords.get(w).count +1;
           }else{
               CountKey keys = new CountKey();
               keys.key = w;
               keys.count = 1;
               mapWords.put(w, keys);
           }
           start++;
       }
       try{
            w = words.substring(start,wordLen -1);
            if(mapWords.containsKey(w)){
                mapWords.get(w).count = mapWords.get(w).count +1;
            }else{
                CountKey keys = new CountKey();
                keys.key = w;
                keys.count = 1;
                mapWords.put(w, keys);
            }
       }catch(Exception e){
           System.out.println(words + " " + wordLen + " " + start + " " + len ); 
       }
       */
   }
   String [] sangle = {".","_","(",")","/","|",",",":","–","-"," ","]","[","!","、","?","”","“","。","’","—","‘","…","=","&",";","："};
   Map<String,CountKey> _keyCount = new HashMap<String,CountKey>();
   
   public void countAddressKeys(String key,String content){
         content = content.replaceAll("\r|\t|\n",",");
//         System.out.println("word !!! " + content);
         if(content == null)
             return;
         for(int lw = 0 ;lw<sangle.length;lw++){
             content = content.replace(sangle[lw], ",");
         }
        content = content.replaceAll("】|【", ",");//("[\\d]+?", ",").replaceAll.replaceAll("[a-z|A-Z]+?", ",");
//               word = word.replaceAll(",{2,}", ",");
        content = content.replaceAll(",{1,}", ",");
        String [] words = content.split(",");         
        for(int l = 0; l<words.length;l++){                   
            if(words[l].length()>=2){
                String w = words[l].substring(0,2);
                if(w.indexOf("天山")==-1 && w.indexOf("号")==-1 && w.indexOf("商场")==-1){
                       
                    List<String> list = module2.getKey(w);          //2  words address
                    /* 
                    for(int i=0;i<list.size();i++){
                            if(!_keyCount.containsKey(list.get(i))){
                                CountKey wk = new CountKey();
                                wk.key = list.get(i);
                                wk.count = 1;
                                _keyCount.put(list.get(i), wk);
                            }else{
                                _keyCount.get(list.get(i)).count = _keyCount.get(list.get(i)).count + 1;
                            }
                        }
                      */  
                        if(words[l].length()>2){
                            w = words[l].substring(0,3);                //3  words address
                            list = module2.getKey(w);
                            for(int i=0;i<list.size();i++){
                                if(!_keyCount.containsKey(list.get(i))){
                                    CountKey wk = new CountKey();
                                    wk.key = list.get(i);
                                    wk.count = 1;
                                    _keyCount.put(list.get(i), wk);
                                }else{
                                    _keyCount.get(list.get(i)).count = _keyCount.get(list.get(i)).count + 1;
                                }
                            }                            
                        }
                }
            }
        }
   }
   
   public void dispKeyCount(){
       List<CountKey> list = new ArrayList<CountKey>();
       Set set = _keyCount.entrySet();
       Iterator it = set.iterator();
       int count = 0;
       while(it.hasNext()){
           Entry<String,CountKey> body = (Entry<String,CountKey>)it.next();
           list.add(body.getValue());
           count = body.getValue().count + count;
       }
       
       for(int i=0;i<list.size()-1; i++){
           for(int j= i+1;j<list.size();j++){
               if(list.get(i).count<list.get(j).count){
                   CountKey val = new CountKey();
                   val.count =  list.get(i).count;
                   val.key =  list.get(i).key;
                   list.get(i).count = list.get(j).count;
                   list.get(i).key = list.get(j).key;
                   list.get(j).count = val.count;
                   list.get(j).key = val.key;
               }
           }
       }
       
       
       for(int i=0;i<list.size(); i++){
           double f = 1.0 * list.get(i).count/count;
           String sf = new java.text.DecimalFormat("0.000").format(f);
           System.out.println(list.get(i).key + " " + list.get(i).count +  " " + sf);           
       }
       
       Map<String,CountKey> keys1 = new HashMap<String,CountKey>();
       List<CountKey> list1 = new ArrayList<CountKey>();
       
       for(int i=0;i<list.size();i++){
           String key = "";
           if(list.get(i).key.split(";").length>1){
               key = list.get(i).key.split(";")[2];
           }else{
               key = list.get(i).key;
           }
           int exist = 0;
           for(int j=0;j<list1.size();j++){
               if(list1.get(j).key.equals(key)){
                   list1.get(j).count = list1.get(j).count + list.get(i).count;
                   exist = 1;
                   break;
               }
           }
           if(exist == 0){
               CountKey cKey = new CountKey();
               cKey.count = list.get(i).count;
               cKey.key = key;               
               list1.add(cKey);
           }
       }

       int sum1 = 0;
       for(int i=0;i<list1.size()-1; i++){
           for(int j= i+1;j<list1.size();j++){
               if(list1.get(i).count<list1.get(j).count){
                   CountKey val = new CountKey();
                   val.count =  list1.get(i).count;
                   val.key =  list1.get(i).key;
                   list1.get(i).count = list1.get(j).count;
                   list1.get(i).key = list1.get(j).key;
                   list1.get(j).count = val.count;
                   list1.get(j).key = val.key;
               }
           }
       }       
       for(int i=0;i<list1.size();i++){
           sum1 = sum1 + list1.get(i).count;
       }
       
       for(int i=0;i<list1.size();i++){
           double f = 1.0 * list1.get(i).count/sum1;
           String sf = new java.text.DecimalFormat("0.000").format(f);           
           System.out.println(list1.get(i).key + " " + list1.get(i).count + " " + sf);
       }
       
       String vKey1 = "";
       if(1.0* list1.get(0).count / list1.get(1).count > 1.5){
           vKey1 = list1.get(0).key;
           System.out.println(vKey1);
       }
       
       String sKey1 = "";
       int sLen1 = 0;
       for(int i=0;i<list.size();i++){
           if(list.get(i).key.indexOf(vKey1)>=0){
               if(list.get(i).count>sLen1){
                   sKey1 = list.get(i).key;
                   sLen1 = list.get(i).count;
               }
           }
       }
       System.out.println(sKey1);
   }
   
   
   public void dispWords(){
       Set set = mapWords.entrySet();
       Iterator it = set.iterator();
//       System.out.println("w      1      2      3     12     23     123     f123  f12   f23  f12/23  f123/f12");
       while(it.hasNext()){
           Entry<String,CountKey> body = (Entry<String,CountKey>)it.next();           
           String _w = body.getKey();
           int pc = body.getValue().count;
           int p1 = 0;
           int p2 = 0;
           try{
                if(_w.length() == 5){                    
                    extractWords(_w,5);
                }
           }catch(Exception e){
               System.out.println("!!!!!" + _w);
           }
       }
       set = Wordkeys.entrySet();
       it = set.iterator();
       while(it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)it.next();
           if(body.getKey().length()==2){
                //if(Wordkeys.containsKey(body.getKey().subSequence(0, 2))&&Wordkeys.containsKey(body.getKey().subSequence(1, 3)))
                    System.out.print(body.getKey() + body.getValue()+ "|");
           }
       }
       
       set = Wordkeys.entrySet();
       it = set.iterator();
       while(it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)it.next();
           if(body.getKey().length()==3){
                if(Wordkeys.containsKey(body.getKey().subSequence(0, 2))&&Wordkeys.containsKey(body.getKey().subSequence(1, 3)))
                    System.out.print(body.getKey() + body.getValue()+ "|");
           }
       }
       set = Wordkeys.entrySet();
       it = set.iterator();
       while(it.hasNext()){
           Entry<String,String> body = (Entry<String,String>)it.next();
           if(body.getKey().length()==4){
//                if(Wordkeys.containsKey(body.getKey().subSequence(0, 3))&&Wordkeys.containsKey(body.getKey().subSequence(1, 4)))
                    System.out.print(body.getKey() + body.getValue()+ "|");
           }
       }        
   }
   
   class Words{
       String w;
       int count;
       double st;
   }
   
   Map<String,String> Wordkeys = new HashMap<String,String>();
   public void extractWords(String word,int len){
//       List<CountKey> ws = new ArrayList<CountKey>();
       if(word.length()!=len)
           return; 
       int k =1;
       CountKey[][] ws = new CountKey[len][len];
       while(len - k > 0 ){
            for(int i=0;i<len - k + 1;i++){
                CountKey wk = new CountKey();
                wk.key = word.substring(i,i+k);
                wk.count = mapWords.get(wk.key).count;
                ws[k-1][i] = wk;
            }
            k++;
       }
       CountKey wk = new CountKey();
       wk.key = word;
       wk.count = mapWords.get(wk.key).count;
       ws[k-1][0] = wk;
       
       System.out.print(word + " ");
       double[][] fs2 = new double[len][len];
 
       double k1 = 0.999;
       double k2 = 0.90;       
       
       for(int row = 0;row<len-1 ;row++){
            for(int col=0;col<len-row-1;col++){
                int val1 = ws[row+1][col].count*ws[row+1][col].count;
                int val2 = ws[row][col].count*ws[row][col+1].count;
                fs2[row][col] = 1.0 * val1/(val2);                
                /*
                int val = 1;
                for(int iCol=0;iCol<= row + 1;iCol++){
                    val = val * val1;
                }
                */

                /*
                for(int iCol=col;iCol<= col + 1 ;iCol++)
                    val2 = val2 * ws[row][iCol].count;
                */
                /*
                for(int iCol=col;iCol<= row + 1 + col;iCol++)
                    val2 = val2 * ws[0][iCol].count;
                */
                /*
                if(row>1)
                    val2 = val2 * ws[0][col+1].count * ws[row-1][col+1].count;
                else
                    val2 = val2 * ws[0][col+1].count * ws[row][col].count;
                */
            }
       }

       for(int row = 0;row<len-2;row++){
            for(int col=0;col<len-row-1;col++){
                System.out.print(new java.text.DecimalFormat("0.000").format(fs2[row][col]) + "   " );
           }
           for(int col = 0; col< len  - row -1;col++){
               double fv = 0.0;
               double f2 = fs2[row ][col+1];
               double f1 = fs2[row][col];
               
               if(f1>0.30 && ws[row + 1][col].key.length() == 2){
                   System.out.print(ws[row + 1][col].key + " " +
                           new java.text.DecimalFormat("0.000").format(f1)  + " ");
                   if(!Wordkeys.containsKey(ws[row + 1][col].key))
                       Wordkeys.put(ws[row + 1][col].key, new java.text.DecimalFormat("0.000").format(f1));                    
                }
               if(f1>0.30 && ws[row + 1][col].key.length() == 3){
                   System.out.print(ws[row + 1][col].key + " " +
                           new java.text.DecimalFormat("0.000").format(f1)  + " ");
                   if(!Wordkeys.containsKey(ws[row + 1][col].key))
                       Wordkeys.put(ws[row + 1][col].key, new java.text.DecimalFormat("0.000").format(f1));                    
                }
               
               //else{

               /*
               fv = Math.abs(f2 - f1)/f1;
                if((fv>k2 && fv<k1 )){
                   System.out.print(ws[row + 1][col].key + " " +
                           new java.text.DecimalFormat("0.000").format(fv)  + " ");
                   if(!Wordkeys.containsKey(ws[row + 1][col].key))
                       Wordkeys.put(ws[row + 1][col].key, new java.text.DecimalFormat("0.000").format(fv));
                }
                */
               //}
           }
       }
      
       
       
       
       /*
       for(int row = 0;row<len-2;row++){
            for(int col=0;col<len-row-1;col++){
                System.out.print(new java.text.DecimalFormat("0.000").format(fs2[row][col]) + "   " );
           }
           System.out.println("___");
           for(int col = 0; col< len  - row -1;col++){
               double fv = 0.0;
               double f2 = fs2[row ][col];
               if(col == 0){
                   double f3 = fs2[row ][col+1];
                   fv = Math.abs(f2 - f3)/f2;
               }else{
                    if(col == len-row-2){
                        double f1 = fs2[row][col-1];
                        fv = Math.abs(f2 - f1)/f2;
                    }else{               
                         double f1 = fs2[row][col-1];
                         double f3 = fs2[row][col+1];
                         //fv = (Math.abs(f2 - f1)/f2 + Math.abs(f2 - f3)/f2)/2;
                         double fv1 = Math.abs(f2 - f3)/f2;
                         
                         double fv2 = Math.abs(f2 - f1)/f2;
                         if((fv1>k2 && fv1<k1) || (fv2>k2 && fv2<k1))
                            System.out.print(ws[row + 1][col].key + " " +
                                    new java.text.DecimalFormat("0.000").format(fv1) + " " +
                                    new java.text.DecimalFormat("0.000").format(fv2)  + " ");
                    }
               }
               if(fv>k2 && fv<k1)
                   System.out.print(ws[row + 1][col].key + " ");
               System.out.print(new java.text.DecimalFormat("0.000").format(fv) + " |");
           }
           System.out.println("$");
       }
       */
       /*
        for(int row = 0;row<len-1 ;row++){
            System.out.print("||");
            for(int col=0;col<len-row-1;col++){
                System.out.print(new java.text.DecimalFormat("0.000").format(fs2[row][col]) + "   " );
            }
        }
       */
       /*
       for(int row =0;row < len-1; row ++){
            int index = 0;
            double mf = fs2[row][0];
            for(int i = 1;i<len-1;i++){
                if(fs2[row][i]>mf){mf = fs2[row][i];index = i;}
            }
            //if(mf>0.1)
                System.out.print( ws[row+1][index].key + " " );
            //else
            //    System.out.print( "XXXXXX" + " " );
            for(int i = 0;i<len - 1 - row; i++){ System.out.print(new java.text.DecimalFormat("0.000").format(fs2[row][i]) + "   " );}
       }
       */
       
       for(int i =0; i<len;i++){
           for(int j=0;j<len-i;j++)
                System.out.print(" " + ws[i][j].key + "  " + ws[i][j].count + "  ");
//           System.out.println();
       }   
       System.out.println();
   }
   
   
   public static void main(String[] args){
       try {
           //System.gc();          
           long startMili=System.currentTimeMillis();
           System.out.println("开始 s "+startMili);
           System.gc();
           AddressName _name = new AddressName();
           _name.Initialize();
          
           String addr = "普陀区真光路1288号百联购物广场1楼第一食品商";
           addr = "天山路900号汇金百货(内近娄山关路)";
//           addr = "长宁区长宁路999号多媒体生活广场B1楼";
           List<String> lstAddress =  _name.diffAddress(addr);
           String keyAddr = "";
           if(lstAddress.size()>0){
              keyAddr = lstAddress.get(0).split(";")[lstAddress.get(0).split(";").length-1];
           }
           
//           BaiDuVerify verify = new BaiDuVerify();
//           String verifyAddr = verify.verifyAddress(addr, _name); 
           
//           System.out.println(verifyAddr + " " + addr);
           
           /*
           for(int i = 0; i<lstAddress.size();i++){
               System.out.print(lstAddress.get(i));
           }
           */
           
           long endMili=System.currentTimeMillis();
           System.out.println("结束 s "+endMili);
           System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
           
           
           String aName = "洪山区";
           aName = "凤山镇";
//       aName = "解放路";
//       aName = "下村街";
//       aName = "塘沽区"; 
       aName = "长宁";            
//           aName = "999号";
           startMili=System.currentTimeMillis();
           _name.getKey(aName);
//           System.out.println(aName.getBytes().length);
//           _name.getSize();

           endMili=System.currentTimeMillis();
           System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
           System.out.println("");
       } catch (Exception ex) {
           Logger.getLogger(AddressName.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
