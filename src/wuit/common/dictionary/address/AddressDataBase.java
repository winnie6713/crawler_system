/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lxl
 */
public class AddressDataBase {
    private final AddressNameCode addressNameCode;    
    public MySQLDBAddress dbAddress = new MySQLDBAddress();    
    private final AddressNameCodeIndex addressNameCodeIndex = new AddressNameCodeIndex();
    private final PhoneCodeAddress addressPhone ;
    private final PostCodeAddress addressPost;
    private final RoadCodeAddress addressRoad;
    
    public AddressDataBase(){
        dbAddress.dbConnection();        
        addressNameCode = new AddressNameCode();
        addressPhone = new PhoneCodeAddress();
        addressPost = new PostCodeAddress();
        addressRoad = new RoadCodeAddress();
    }
    
    class AddressNameCode{
        private Map<String,String> nameCode ;
        
        public AddressNameCode(){
            nameCode = new HashMap<String,String>();
            List<String> list = new ArrayList<String>();
            dbAddress.queryAddressNames(list);
            for(int i=0;i<list.size();i++){
                String[] vals = list.get(i).split(":");
                AddressWord word = new AddressWord();
                word.code = vals[1]; 
                word.parents = vals[2];
                nameCode.put(word.code, vals[0]);                               //{key:code;value:name}
                addressNameCodeIndex.addWords(vals[0], word);                             //{key:name;value:{code,parents}}
            }
        }
        
        public String getNameByCode(String code){
            return nameCode.get(code);
        }
    }
  
    public String getNameByCode(String code){        
        String name = addressNameCode.getNameByCode(code);
        return name;
    }
    
    class AddressNameCodeIndex{
        class ParentNode{
            String parents = "";
        }
        private Map<String,ParentNode> mapIndexWords = new HashMap<String,ParentNode>();
        
        public void addWords(String name,AddressWord word){
            if(mapIndexWords.containsKey(name)){
                mapIndexWords.get(name).parents = mapIndexWords.get(name).parents + ":" + word.parents;
            }else{
                ParentNode node = new ParentNode();
                node.parents = word.parents;
                mapIndexWords.put(name, node);
            }
        } 
        
        public String getWordsCodes(String words){
            ParentNode   node = mapIndexWords.get(words);
            if(node != null){
                return mapIndexWords.get(words).parents;
            }else{
                return "null";
            }
        }
    }
    
    private static final String[] keyDistricts = {"省","市","县","区","市辖区","自治州","自治区","盟","自治县","旗"};
    public List<String> queryWords(String words){     
        List<String> list = new ArrayList<String>();
        if(words.isEmpty())
            return list;
        list = getAddressWords(words);
        while(list.isEmpty()){
            for(int i=0 ;i<keyDistricts.length;i++){
                List<String> _list = getAddressWords(words + keyDistricts[i]);
                if(!_list.isEmpty())
                    list.addAll(_list);
            }
            break;
        }
        return list;
    }
    
    public List<String> verifyAddress(String words){     
        List<String> list = new ArrayList<String>();
        if(words.isEmpty())
            return list;
        list = getAddressWords(words);
        return list;
    }

    public List<String> verifyAddressRoad(String words){     
        List<String> list = new ArrayList<String>();
        if(words.isEmpty())
            return list;
        list = queryAddressByRoad(words);
        return list;
    }    
    
    private  List<String> getAddressWords(String words){
        List<String> list = new ArrayList<String>();
       String rs = addressNameCodeIndex.getWordsCodes(words);
       
       if(rs.equals("null"))
           return list; 
       
       if(rs.isEmpty()){
           list.add(words);
           return list;
       }
       
       String [] arrayParaens = rs.split(":"); 
       
        for(int j=0;j<arrayParaens.length;j++){            
            String[] parents = arrayParaens[j].split(",");
            String val = "";
            for(int i=0;i<parents.length;i++){
                String _name = getNameByCode(parents[i]);
                if(_name != null){
                    val = val + _name + ",";
                }
                String _code = parents[i];
                while(_code.length()>1){
                    _code = _code.substring(0,_code.length()-2);
                    _name = getNameByCode(_code);
                    if(_name != null){
                        val = _name  + "," + val;
                    }                    
                }
            }
            val = val + words ;                
            list.add(val);
        }
        return list;
    }
    
    public List<String> queryAddressByPhone(String phonecode){
        List<String> list = new ArrayList<String>();
        if(phonecode.indexOf("0")==0){
            phonecode = phonecode.substring(1,phonecode.length());
        }
        String val = addressPhone.getAddressCodeByPhoneCode(phonecode);
        if(val.isEmpty())
            return list;
        String[] vals = val.split(",");
        for(int i =0; i< vals.length; i++){
            String name = getNameByCode(vals[i].substring(0,2));                //省
            name = name + "," + getNameByCode(vals[i].substring(0,4));          //地市
            name = name + "," + getNameByCode(vals[i]);                         //县区
//            System.out.println(name);
            list.add(name);
        }
        return list;
    }
    
    public List<String> queryAddressByRoad(String road){
        List<String> list = new ArrayList<String>();
        String val = addressRoad.getAddressCodeByRoad(road);
        if(val.isEmpty())
            return list;
        String[] vals = val.split(",");
        for(int i =0; i< vals.length; i++){
            String name = getNameByCode(vals[i].substring(0,2));
            name = name + "," + getNameByCode(vals[i].substring(0,4));
            name = name + "," + getNameByCode(vals[i]);
//            System.out.println(name);
            list.add(name);
        }
        return list;
    }
    
    
    public List<String> queryAddressByPost(String postcode){
        List<String> list = new ArrayList<String>();
        if(postcode.length()>4){
            postcode = postcode.substring(0,4);
        }

        String val = addressPost.getAddressCodeByPostCode(postcode);
        if(val.isEmpty())
            return list;
        String[] vals = val.split(",");
        for(int i =0; i< vals.length; i++){
            String name = getNameByCode(vals[i].substring(0,2));
            name = name + "," + getNameByCode(vals[i].substring(0,4));
            name = name + "," + getNameByCode(vals[i]);
//            System.out.println(name);
            list.add(name);
        }
        return list;
    }    
    
    
    class PhoneCodeAddress{
        Map<String,AddressWord> mapPhone = new HashMap<String,AddressWord>();
        public PhoneCodeAddress(){
            List<String> list = new ArrayList<String>();
            dbAddress.queryAddressPhone(list);
            for(int i=0;i<list.size();i++){
                String[] vals = list.get(i).split(":");
                AddressWord word = new AddressWord();
                word.code = vals[0];
                word.parents = vals[1]; 
                if(mapPhone.containsKey(word.code)){
                    mapPhone.get(word.code).parents = mapPhone.get(word.code).parents + "," + word.parents; 
                }else{
                    mapPhone.put(word.code, word);                                  //{key:phone;value:code,code}
                }                               
            }
        }
        
        public String getAddressCodeByPhoneCode(String phonecode){
            AddressWord word = mapPhone.get(phonecode);
            if(word == null)
                return "";
            return word.parents;
        }
    }

    class PostCodeAddress{
        Map<String,AddressWord> mapPost = new HashMap<String,AddressWord>();
        public PostCodeAddress(){
            List<String> list = new ArrayList<String>();
            dbAddress.queryAddressPost(list);
            for(int i=0;i<list.size();i++){
                String[] vals = list.get(i).split(":");
                AddressWord word = new AddressWord();
                word.code = vals[0];
                word.parents = vals[1]; 
                if(mapPost.containsKey(word.code)){
                    mapPost.get(word.code).parents = mapPost.get(word.code).parents + "," + word.parents; 
                }else{
                    mapPost.put(word.code, word);                                  //{key:phone;value:code,code}
                }                               
            }
        }
        
        public String getAddressCodeByPostCode(String postcode){
            AddressWord word = mapPost.get(postcode);
            if(word == null)
                return "";
            return word.parents;
        }
    }    
    
    
    class RoadCodeAddress{
        Map<String,AddressWord> mapRoad = new HashMap<String,AddressWord>();
        public RoadCodeAddress(){
            List<String> list = new ArrayList<String>();
            dbAddress.queryAddressRoad(list);
            for(int i=0;i<list.size();i++){
                String[] vals = list.get(i).split(":");
                AddressWord word = new AddressWord();
                word.code = vals[1];
                word.parents = vals[0]; 
                if(mapRoad.containsKey(word.code)){
                    mapRoad.get(word.code).parents = mapRoad.get(word.code).parents + "," + word.parents; 
                }else{
                    mapRoad.put(word.code, word);                                  //{key:phone;value:code,code}
                }                               
            }
        }
        
        public String getAddressCodeByRoad(String road){
            AddressWord word = mapRoad.get(road);
            if(word == null)
                return "";
            return word.parents;
        }
    }    
    
    public static void main(String[] args) {
        AddressDataBase db = new AddressDataBase();
        //String file = "D:\\projects\\CrawlerSystem\\database\\Address\\postcodes.txt";
        //db.InputBasePostCodeToMySQL(file);

 //       String file = "D:\\projects\\CrawlerSystem\\database\\Address\\行政区划乡.txt";
 //       db.InputBaseDistricCodeToMySQL(file);


//        String file = "D:\\projects\\CrawlerSystem\\database\\Address\\post.txt";
//        db.InputToMySQL(file, 30);
//        List<String> indexs = db.queryAddress("江苏省");
        List<String> indexs = db.queryWords("仙桃");
        for(int i=0;i<indexs.size();i++){
            System.out.println(indexs.get(i));
        }
        
        indexs = db.queryAddressByPhone("0712");
        for(int i=0;i<indexs.size();i++){
            System.out.println(indexs.get(i));
        }        

        indexs = db.queryAddressByPost("432200");
        for(int i=0;i<indexs.size();i++){
            System.out.println(indexs.get(i));
        }
        
        indexs = db.queryAddressByRoad("金沙江西路");
        System.out.println("解放路");
        for(int i=0;i<indexs.size();i++){
            System.out.println(indexs.get(i) + " : 金沙江西路");
        }        
        
    }
}
