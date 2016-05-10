/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import wuit.common.crawler.search.BaiDuData;
import wuit.common.crawler.search.CrawlerBaidu;
import wuit.common.dictionary.address.AddressDataBase;
import wuit.common.crawler.composite.LocalInfo;
import wuit.common.dictionary.address.ServiceAddress;

/**
 *
 * @author lxl
 */
public class Words {
    class KeyWord{
        String word = "";
        int count = 0;
    }
        
    public int maxSize = 50;
    public List<Map<String,KeyWord>> listWords = new ArrayList<Map<String,KeyWord>>();          //按词长度存储分词结果表
    public List<Map<String,KeyWord>> listNewWords = new ArrayList<Map<String,KeyWord>>();       //发现新词存储表
    
    //符号表
    public String marks = "[&gt;｜ |　|、|.|．|。|…|:|：|,|，|_|＿|-|－|—|/|\\(|\\)|（|）|＂|“|”|【|】|\\[|\\]]";//．/／<>＜＞《》?？;；'＇‘:：";//\"＼、［］【】{}｛｝\\｀·|｜`~～!！@＠";//\\#＃$＄￥%％^︿&＆*＊-－——=＝+＋";
 //   public String marks_cn1 = " [`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}‘；：”“'。，、？]";
    
    public AddressDataBase dbAddress ;//= new ServiceAddress();
    //private static final int countK = 32;
    class NewWords{
        public void addNewWord(String word,List<Map<String,KeyWord>> lib){
            int size = word.length();
            if(size>=lib.size())
                return;
            if(lib.get(size).containsKey(word)){
                lib.get(size).get(word).count++; 
            }else{
                KeyWord newWord = new KeyWord();
                newWord.word = word;
                newWord.count = 1;
                lib.get(size).put(word, newWord);
            }
        }
        public void addNewWordsMap(List<Map<String,KeyWord>> words,List<Map<String,KeyWord>> lib){
            for(int i=0;i<words.size();i++){
                Set set = words.get(i).entrySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                    addNewWord(body.getValue().word,lib);
                }
            }
        }
        public void addNewWordsList(List<String> words,List<Map<String,KeyWord>> lib){
            for(int i=0;i<words.size();i++){
                addNewWord(words.get(i),lib);
            }
        }        
    }
    
    
    public Words(AddressDataBase dbAddress){
        this.dbAddress = dbAddress;
        for(int i=0;i<maxSize;i++){
            Map<String,KeyWord> pamWord = new HashMap<String,KeyWord>();
            listWords.add(pamWord);
        }
    }
    
    public String [] diffPreWords(String words){
        words = words.replaceAll(marks, ".");
        words = words.replaceAll("\\.{2,}", ".");
        words = words.replace("◆", "");
        words = words.replace("--", "");
        words = words.replace("·", "");
        String [] keys = words.split("\\.");  
        return keys;
    }
    
    public void diffWords(String words,int maxLen){
        int len = 0;
        for (int i = 0; i< listWords.size();i++){
            len = words.length();
            int start = 0;
            int size = i+1;                                                     //分词长度
            while(start + size <= len){
                String key = words.substring(start, start + size);
                start ++;
                
                if(listWords.get(i).containsKey(key)){
                    listWords.get(i).get(key).count = listWords.get(i).get(key).count + 1;
                }else{
                    KeyWord word = new KeyWord();
                    word.count = 1;
                    word.word = key;
                    listWords.get(i).put(key, word);    
                }
            }
        }
//        dispWords();
    }
    
    class Statistics{
        private void diffName(String name,List<Map<String,KeyWord>> listMapWords,List<Map<String,KeyWord>> listNames){        
            if(hasSubStr(name,listNames)==1 || name.length()<=1)
                return;
            int len = name.length();
            int count = listMapWords.get(name.length()-1).get(name).count;
            int hasdiff = 0;
            int maxDiffCount = 0;
            int index = 0;

            for(int i=len-1;i>1;i--){
                maxDiffCount = 0;
                for(int j=name.length()-1;j>1;j--){
                    String s = name.substring(0,j);
                    int c = listMapWords.get(s.length()-1).get(s).count;
                    if(Math.abs(c - count) >maxDiffCount && s.length()>1){
                        maxDiffCount = Math.abs(c - count);
                        index = j;
                    }
                }

                String wordStart = name.substring(0,i);
                KeyWord word = listMapWords.get(wordStart.length()-1).get(wordStart);
                int _countS = word.count;
                String wordEnd = name.substring(wordStart.length(), len);
                int _countE = listMapWords.get(wordEnd.length()-1).get(wordEnd).count;
                double dp = 1.0*(_countS - count)/(count + 0);
                dp = 1.0 *dp * count/_countS;
                //dp = Math.abs(dp);
                double dp2 = 1.0*(count * count)/_countE/_countS;
                //dp = 1.0 * count/_countE;
                if(dp > 0.1 ){ 
                    System.out.println(wordStart + "  " + wordEnd);
                    hasdiff = 1;
                    if(wordStart.length()>2 )
                        diffName(wordStart,listMapWords,listNames);
                    if(wordEnd.length()>2 )
                        diffName(wordEnd,listMapWords,listNames);                
                    if(wordStart.length()>1){
                        KeyWord _word = new KeyWord();
                        _word.word = name;
                        _word.count = listMapWords.get(name.length()-1).get(name).count;
                        //listNames.get(name.length()-1).put(name, _word);
                        //System.out.println("put: " + name);
                    }

                    break;
                }
            }
            if(hasdiff == 0 && name.length()>1){
                KeyWord _word = new KeyWord();
                _word.word = name;
                _word.count = listMapWords.get(name.length()-1).get(name).count;
                String word2 = name.substring(0,2);
                if(listMapWords.get(word2.length()-1).get(word2).count>30)
                    if(hasSubStr(name,listNames)==0){
                        listNames.get(name.length()-1).put(name, _word);
                        System.out.println("put: " + name);
                }
            }
        }
        
        public List<Map<String,KeyWord>> extractNames(List<Map<String,KeyWord>> listMapWords){   
            //缓存
            List<Map<String,KeyWord>> listWords = new ArrayList<Map<String,KeyWord>>();
            for(int i=0;i<listMapWords.size();i++){
                Map<String,KeyWord> keywords = new HashMap<String,KeyWord>();
                listWords.add(keywords);
            }        
            //
            for(int i = listMapWords.size()-1;i>0;i--){
                 Set set = listMapWords.get(i).entrySet();
                 Iterator it = set.iterator();
                 while(it.hasNext()){
                     Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                     String vals = body.getValue().word;
                     //diffName(vals,listMapWords,listWords);                 
                     continue;
                 }            
            }


            return listMapWords;
        }    
    
        public int hasSubStr(String name,List<Map<String,KeyWord>> listWords){
            int exist = 0;
            int len = name.length();
            for(int i=listWords.size()-1;i>=1;i--){
                Map<String,KeyWord> words = listWords.get(i);
                if (words.isEmpty())
                    continue;
                Set set = words.entrySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                    String word = body.getValue().word.trim();
                    name = name.trim();
                    if(name.equals(word)){
                        exist = 1;
                        break;
                    }
                    if(name.length() > word.length() && name.indexOf(word)>=0 ){
                        exist = 1;
                        break;
                    }
                    if(word.length() > name.length() && word.indexOf(name)>=0){
                        exist = 1;
                        break;                    
                    }      
                }
                if(exist == 1)
                    break;
            }
            return exist;
        }
    
        public int sumWord(){
            int sum = 0;
            Set set = listWords.get(1).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                sum  = sum + body.getValue().count;
            }
            return sum;
        }
    
        public List<Map<String,KeyWord>> statisticsWords(){
            int sum = 0;
            Set set = listWords.get(1).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                sum  = sum + body.getValue().count;
            }        
            for(int i=1;i<maxSize;i++){
                set = listWords.get(i).entrySet();
                it = set.iterator();
                while(it.hasNext()){
                    Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                    int count_i = body.getValue().count;
                    double pi = 1.00 * count_i/sum;
                    double pi1 = 1.0;
                    if (i==1){
                        int _count2 = body.getValue().count;
                        pi = 1.00 * _count2 /sum;
                    }
                    else{
                        //if(listWords.get(i-1).get(body.getValue().word.substring(0,i)) != null)
                        //pi1 = listWords.get(i-1).get(body.getValue().word.substring(0,i)).v1;
                    }
                    int count_ii = listWords.get(0).get(body.getValue().word.substring(i,i+1)).count;            
                    double pii = 1.00 * count_ii / sum;

                    double v1  = pi * pi1 / pii;

//                    body.getValue().v1 = v1;
//                    body.getValue().v2 = pi;
                }
            }
            return listWords;
        }     
        
    }
    
    /*
    public String extractSymbol(String words,List<String> list){
        String roadNum = match(words,"\\d{1,}-\\d{1,}号|\\d{1,}号|\\d{1,}路");
        if(!roadNum.equals("")){
            list.add(roadNum);
            words = words.replace(roadNum, "");
        }
        String phone = match(words,"\\d{1,}-\\d{1,}-\\d{1,}|\\d{1,}-\\d{1,}|\\d{8,}");
        if(!phone.equals("")){
            list.add(phone);
            words = words.replace(phone, "");
        }
        
        String num = match(words,"\\d{1,}");
        if(!num.equals("")){
            list.add(num);
            words = words.replace(num, "");
        }        
        words = words.replace("-", "");
        return words;
//        String checkemail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";         
//        String checkphone = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$"; 
        
    }
    */
    
    
    public List<Map<String,KeyWord>> extractFilterNames(List<Map<String,KeyWord>> listNames){
        List<List<String>> listNamesRemove =new ArrayList<List<String>>();
          for(int i=0;i<maxSize;i++){
            List<String> pamWord = new ArrayList<String>();
            listNamesRemove.add(pamWord);
        }      
        
        int size = listNames.size();
        for(int i=size-1;i>0;i--){
            Map<String,KeyWord> words = listNames.get(i);
            Set set = words.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String name = body.getValue().word;
                int count = body.getValue().count;
                for(int j = name.length()-1;j>0;j --){
                    String subName = name.substring(0,j);
                    KeyWord subWord = listNames.get(j-1).get(subName);
                    if(subWord!=null && 1.0*(subWord.count - count)/count < 0.01){                        
                        listNamesRemove.get(j-1).add(subName);
                    }
                    
                }
                if(name.replaceAll("[\\d{1,}]", "").isEmpty())
                    listNamesRemove.get(i).add(name);
            }
        }
        for(int i=0;i<listNamesRemove.size();i++){
            for(int j=0;j<listNamesRemove.get(i).size();j++){
                listNames.get(i).remove(listNamesRemove.get(i).get(j));
            }
            listNamesRemove.get(i).clear();
        }
        
        
        
        for(int i=size-1;i>0;i--){
            Map<String,KeyWord> words = listNames.get(i);
            Set set = words.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String name = body.getValue().word;
                int len = name.length();
                int count = body.getValue().count;
                
                //System.out.println(name + " " + count);
                for(int j = 1;j<name.length();j ++){
                    String subName = name.substring(j,name.length());
                    KeyWord subWord = listNames.get(subName.length() -1).get(subName);
                    if(subWord!=null && 1.0*(subWord.count - count)/count < 0.01){
                        listNames.get(subName.length() -1).remove(subName);
                    }
                }
            }            
        }
        
        listNames.get(0).clear();                                               //清除单字
        return listNames;
    }
   
    
    class VerifyAddess{
        
        class Location{
            int count;
            String location = "";
            Administrative address = new Administrative();
            PostCode post = new PostCode();
            Phone phone = new Phone();
        }
        
        class PostCode{
            String post = "";
            String code12 = "";
            String code3 = "";
            String code4 = "";
            String code56 = "";
        }
        
        class Phone{
            String code = "";
            String number = "";
            String mobile = "";
        }
        
        /*
        class KeyWord{
            String word = "";
            int count = 0;
        }
        */
        class Administrative{
            int level = 0;            
            String province = "";           //省、自治区、（直辖）市、特别行政区
            String city = "";               //地区、盟、自治洲、（地级）市
            String county = "";             //县、自治县、旗、自治旗、（县级）市、（市辖）区、林区、特区
            String township = "";           //乡、镇、街道、民族苏木、（县辖）区、（乡级）管理区
            String village = "";            //村、社区（community）
            String villageGroup = "";       //村小组组、社区居民小组
            
            String road = "";               //街路巷
            String roadNo = "";
            
            String building = "";
            String buildingNo = "";
            
            String unit = "";
            String unitNo = "";
            
            String floor = "";
            String floorNo = "";
            
            String Household="";
            String HouseholdNo = "";
        }
        
        List<List<Location>> locations = new ArrayList<List<Location>>();
        
        public String verifyAddressName(List<Map<String,KeyWord>> listMapWords, LocalInfo data,String address){
//            List<String> qWords = new ArrayList<String>();
            List<Map<String,KeyWord>> qWords = new ArrayList<Map<String,KeyWord>>();
            for(int i=0;i<listMapWords.size();i++){
                Map<String,KeyWord> mapWord = new HashMap<String,KeyWord>();
                qWords.add(mapWord);
            }
            
            //以关键字查询地址字典，并将结果存放在qWords中
            for(int i=listMapWords.size()-1;i>=0;i--){
                 Set set = listMapWords.get(i).entrySet();
                 Iterator it = set.iterator();
                 while(it.hasNext()){
                     Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                     String key = body.getKey();
                     List<String> list = dbAddress.queryWords(key);
                     for(int j=0;j<list.size();j++){                         
                         System.out.println(body.getValue().count +"," + list.get(j) + "," + key + "," + address);
                         
                         String[] vals = list.get(j).split(",");
                         int len = vals.length;
                         KeyWord word = new KeyWord();
                         word.word = list.get(j);
                         word.count = body.getValue().count*(5-len)*(5-len);
                         if(qWords.get(len).containsKey(word.word)){
                             qWords.get(len).get(word.word).count = qWords.get(len).get(word.word).count + word.count;
                         }else{
                             qWords.get(len).put(word.word, word);
                         }
                     } 
                 }            
            }
            
            for(int i=qWords.size()-1;i>=0;i--){
                 Set set = qWords.get(i).entrySet();
                 Iterator it = set.iterator();
                 while(it.hasNext()){
                     Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
  //                   System.out.println(body.getValue().count + " " + body.getValue().word + "||" + body.getKey());
                 }            
            }

            
            LocalInfo _data = new LocalInfo();
            
            extractProvince(qWords.get(1),_data);
            extractCity(qWords.get(2),_data);
            extractCounty(qWords.get(3),_data);
            extractCountyRoad(qWords.get(4),_data);
//            extractNameValue(qWords.get(4),_data);
            
//            Location info = location;
//            System.out.print("!!!Address:" + info.location +  ";level:" + info.address.level+  " phone:" + info.phone.code + ";post:" + info.post.post);
//            System.out.print("  province:" + info.address.province + "city:" + info.address.city + ";county:" + info.address.county);
//            System.out.println("  road:" + info.address.road + " township:" + info.address.township + ";village:" + info.address.village);
            
            String _val = _data.province +","+_data.city+"," +_data.county + "," + _data.Road;
            System.out.println(_val  + " !!!!!!!!!!!! Words.VerifyAddress.vaerifyAddressName");
            return _val;
        }        
        
        private void extractProvince(Map<String,KeyWord> mapWord,LocalInfo _data){
             LocalInfo _data_t = new LocalInfo();
            int exist = 0;
            int count = 0;
            
            Set set = mapWord.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                if(body.getValue().count>count){                    
                    _data_t.province = body.getKey().split(",")[0];
                    exist = 1;
                    count = body.getValue().count;
                }
            }
            if(exist == 1)
                _data.province = _data_t.province;
        }
        
        private void extractCity(Map<String,KeyWord> mapWord,LocalInfo _data){
            LocalInfo _data_t = new LocalInfo();
            int count = 0;
            int exist = 0;
            Set set = mapWord.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String[] vals = body.getKey().split(",");
                int _count = body.getValue().count;
                
                if(_data.province.isEmpty()){
                    if(_count>count){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        exist = 1;
                    }
                }else{
                    if(_count>count && _data.province.equals(vals[0])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];                        
                        exist = 1;
                    }
                }
            } 
            if(exist == 1){
                _data.province = _data_t.province;
                _data.city = _data_t.city;
            }
        }

        private void extractCounty(Map<String,KeyWord> mapWord,LocalInfo _data){
            LocalInfo _data_t = new LocalInfo();
            int count = 0;
            int exist = 0;
            Set set = mapWord.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String[] vals = body.getKey().split(",");
                int _count = body.getValue().count;
                if(_data.province.isEmpty() && _data.city.isEmpty()){
                    if(_count>count){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        exist = 1;
                    }
                }
                if(_data.province.isEmpty() && !_data.city.isEmpty()){
                    if(_count>count){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        exist = 1;
                    }
                }
                if(!_data.province.isEmpty() && _data.city.isEmpty()){
                    if(_count>count && _data.province.equals(vals[0])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        exist = 1;
                    }
                }
                if(!_data.province.isEmpty() && !_data.city.isEmpty()){
                    if(_count>count && _data.province.equals(vals[0]) && _data.city.equals(vals[1])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        exist = 1;
                    }
                }
            }
            if(exist == 1){
                _data.province = _data_t.province;
                _data.city = _data_t.city; 
                _data.county = _data_t.county;             
            }
        }        

        private void extractCountyRoad(Map<String,KeyWord> mapWord,LocalInfo _data){
            LocalInfo _data_t = new LocalInfo();
            int count = 0;
            int exist = 0;
            Set set = mapWord.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String[] vals = body.getKey().split(",");
                int _count = body.getValue().count;
                if(_data.province.isEmpty() && _data.city.isEmpty() && _data.county.isEmpty()){
                    if(_count>count){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        _data_t.Road = vals[3];
                        exist = 1;
                    }
                }
                if(!_data.province.isEmpty() && _data.city.isEmpty() && _data.county.isEmpty()){
                    if(_count>count && _data.province.equals(vals[0])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        _data_t.Road = vals[3];
                        exist = 1;
                    }
                }
                if(!_data.province.isEmpty() && !_data.city.isEmpty() && _data.county.isEmpty()){
                    if(_count>count && _data.province.equals(vals[0]) && _data.city.equals(vals[1])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        _data_t.Road = vals[3];
                        exist = 1;
                    }
                }
                if(!_data.province.isEmpty() && !_data.city.isEmpty() && !_data.county.isEmpty()){
                    if(_count>count && _data.province.equals(vals[0]) && _data.city.equals(vals[1])&& _data.county.equals(vals[2])){
                        count = _count;
                        _data_t.province = vals[0];
                        _data_t.city = vals[1];
                        _data_t.county = vals[2];
                        _data_t.Road = vals[3];
                        exist = 1;
                    }
                }
             }
             if(exist == 1){
            _data.province = _data_t.province;
            _data.city = _data_t.city; 
            _data.county = _data_t.county;
            _data.Road = _data_t.Road;
             }
        }        
        
        /*
        private void extractName(List<Location> list,int level,AddressTown data){
            int count = 0;
            for(int i=0;i<list.size();i++){
                String[] vals;
                if(list.get(i).count>count){
                    switch(level){
                        case 0:
                            vals = list.get(i).location.split(",");
                            if(vals.length == 1){
                                data.province = vals[0];// list.get(i).address.province;
                                count = list.get(i).count;
                            }
                            break;
                        case 1:
                            vals = list.get(i).location.split(",");
                            if(vals.length == 2){
                                
                            }
                            if(data.province.isEmpty()) {
                                count = list.get(i).count;
                                data.province = list.get(i).address.province;
                                data.city = list.get(i).address.city;                            
                            }else{
                                if(data.province.equals(list.get(i).address.province)){
                                    count = list.get(i).count;
                                    data.city = list.get(i).address.city;
                                }
                            }
                            break;
                        case 2:
                            if(data.province.isEmpty() || data.city.isEmpty()){
                                count = list.get(i).count;
                                data.province = list.get(i).address.province;
                                data.city = list.get(i).address.city; 
                                data.county = list.get(i).address.county; 
                            }else{
                                if(data.province.equals(list.get(i).address.province) && data.city.equals(list.get(i).address.city)){
                                    count = list.get(i).count;
                                    data.county = list.get(i).address.county;
                                }
                            }
                            break;
                        case 3:
                            if(!data.province.isEmpty() && !data.city.isEmpty() && data.county.isEmpty()){
                                if(data.province.equals(list.get(i).address.province) && data.city.equals(list.get(i).address.city)){
                                    count = list.get(i).count;
                                    data.county = list.get(i).address.county;
                                }
                            }
                            break;
                        case 4:
                            if(!data.province.isEmpty()&&!data.city.isEmpty()&&!data.county.isEmpty()){
                                if(data.province.equals(list.get(i).address.province) && 
                                        data.city.equals(list.get(i).address.city) && 
                                        data.county.equals(list.get(i).address.county)){
                                    count = list.get(i).count;
                                    data.countyRoad = list.get(i).address.road;
                                }
                            }
                    }
                }
            }
        }
        */
        /*
        private int extract(int level,Location location,List<List<Location>> locations){
//            System.out.println(level + " " + " ,"+ location.address.province + " ,"+ location.address.city + " ,"+ location.address.county + " ,");
            
            
            int _level = 0;
            if(locations.get(level).size() == 0){
                return 0;
            }
            if(locations.get(level).size() == 1){
                switch(level){
                    case 0:
                        copyLocation(location,locations.get(level).get(0));
                        _level = level + 1;
                        break;
                    case 1:
                        if(!location.address.province.equals("")){
                            if(locations.get(level).get(0).address.province.equals(location.address.province)){
                                if(location.address.city.equals(""))
                                    location.address.city = locations.get(level).get(0).address.city;
                                else{
                                    if(!location.address.city.equals(locations.get(level).get(0).address.city)){
                                        location.address.city = location.address.city + "," + locations.get(level).get(0).address.city;
                                    }
                                }
                                _level = level + 1;
                            }else{
                                locations.get(level).remove(0);
                            }
                        }else{
                            copyLocation(location,locations.get(level).get(0));
                        }
                        break;
                    case 2:
                        if(!location.address.province.equals("") && !location.address.city.equals("")){
                            if(locations.get(level).get(0).address.province.equals(location.address.province) && 
                                    location.address.city.indexOf(locations.get(level).get(0).address.city)>=0){
                                if(location.address.county.equals("")){
                                    location.address.county = locations.get(level).get(0).address.county;
                                }else{
                                    if(!locations.get(level).get(0).address.county.equals(location.address.county)){
                                        location.address.county = location.address.county + "," + locations.get(level).get(0).address.county;
                                    }
                                }
                                location.phone = locations.get(level).get(0).phone;
                                location.post = locations.get(level).get(0).post;
                                _level = level + 1;
                            }else{
                                locations.get(level).remove(0);
                            }
                        }
                        break;
                }
            }
            if(locations.get(level).size() > 1){
                switch(level){
                    case 0:
                        System.out.println("省级行政区划不能确定！！！" );
                        break;
                    case 1:
                        if(!location.address.province.equals("")){
                            for(int i=locations.get(level).size()-1;i>=0;i--){                            
                                if(!locations.get(level).get(i).address.province.equals(location.address.province)){
                                    locations.get(level).remove(i);
                                }else{
                                    if(location.address.city.equals(""))
                                        location.address.city = locations.get(level).get(i).address.city;
                                    else{
                                        if(!location.address.city.equals(locations.get(level).get(i).address.city))
                                            location.address.city = location.address.city + "," + locations.get(level).get(i).address.city;
                                    }
                                    _level = level + 1;
                                }
                            }
                        }else{
                            System.out.println("省、市级行政区划不能确定！！！" );
                        }
                        break;
                    case 2:
                        if(location.address.province.equals("") || location.address.city.equals("")){
                            System.out.println("省、市、县行政区划不能确定！！！" );
                        }else{
                            for(int i=locations.get(level).size()-1;i>=0;i--){
                                if(!location.address.province.equals(locations.get(level).get(i).address.province)){
                                    locations.get(level).remove(i);
                                }else{
                                    if(!location.address.city.equals(locations.get(level).get(i).address.city)){
                                        locations.get(level).remove(i);
                                    }else{
                                        if(location.address.county.equals("")){
                                            location.address.county = locations.get(level).get(i).address.county;
                                        }else{
                                            if(!locations.get(level).get(i).address.county.equals(location.address.county))
                                                location.address.county = location.address.county + ","+ locations.get(level).get(i).address.county;
                                        }
                                        location.phone = locations.get(level).get(i).phone;
                                        location.post = locations.get(level).get(i).post;
                                        _level = level + 1;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
            return level;
        }
        */
        
        private void copyLocation(Location location1,Location location2){
            location1.address = location2.address;
            location1.location = location2.location;
            location1.phone = location2.phone;
            location1.post = location2.post;
        }   
        
        public Location parseAddress(String address,int count){
            Location info = new Location();
            info.count = count;
            info.location = address;
            String[] addrs = address.split(",");
            if(addrs.length>0){
                info.address.level = 1;
                info.address.province = addrs[0];
            }
            if(addrs.length>1){
                info.address.level = 2;
                info.address.city = addrs[1];
            }
            if(addrs.length>2){
                info.address.level = 3;
                info.address.county = addrs[2];
            }
            if(addrs.length>3){
                info.address.level = 4;
                info.address.township = addrs[3];
            }
            if(addrs.length>4){
                info.address.level = 5;
                info.address.village = addrs[4];
            }
            if(addrs.length>5){
                info.address.level = 6;
                info.address.road = addrs[5];
            }            
            if(addrs.length>6){
                info.address.level = 5;
                info.address.township = addrs[6];
            } 
            return info;
        }
    }

    
    //设置阀值，按统计计算的结果提取词
    public List<Map<String,KeyWord>> extractNameByCount(int minCount){
        List<Map<String,KeyWord>> listWordKeys = new ArrayList<Map<String,KeyWord>>();
        
        for(int i=0;i<maxSize;i++){
            Map<String,KeyWord> pamWord = new HashMap<String,KeyWord>();
            listWordKeys.add(pamWord);
        }
        //复制
        for (int i = listWords.size() -1; i>0 ;i--){
             Set set = listWords.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 if(body.getValue().count < minCount)
                     continue;
                 String _w = body.getValue().word.replaceAll("[\\d{1,}]", "");
                 if(!_w.equals(body.getValue().word)){
                     continue;
                 }                 
                 KeyWord _word = new KeyWord();
                 _word.count = body.getValue().count;
                 _word.word = body.getValue().word;
                 listWordKeys.get(i).put(_word.word, _word);
             }
        }
        
//        dispListWords(listWordKeys);
                
        //消去重复可能组合次
        //按最长词为基准，消去比此词长度小，被此次所包含，词频之差小于等于 3 该最长词        
         for (int i = listWordKeys.size() -1; i>0 ;i--){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 int count = body.getValue().count;
                 String _words = body.getValue().word;
                 int len = _words.length();
                 int size = 2;
                 while(size<len){
                    int start = 0;
                    while(len>=start+size){
                        String _w1 = _words.substring(start,start + size);
                        if(listWordKeys.get(size - 1).containsKey(_w1)){
                            if (Math.abs(listWordKeys.get(size - 1).get(_w1).count - count)<=3){
                               listWordKeys.get(size -1).remove(_w1);
                            }
                        }
                        start ++;
                    }
                    size++;
                 }
             } 
        }
        
//          dispListWords(listWordKeys);
         //将词频大于minCount 的统计词转入 listWordKeys 表
         /*
         for (int i = 0; i< listWordKeys.size();i++){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 String _words = body.getValue().word;
                 if(body.getValue().count> minCount ){
                   //  System.out.println(body.getValue().word + " &  " + body.getValue().count);
                     String _w = _words.replaceAll("[\\d{1,}]", "");
                     if(!_w.equals(""))
                        listWordKeys.get(i).remove(body.getKey());
                     //listWordKeys.get(i).put(body.getKey(), body.getValue());
                 }
             }
         }
         */
          /*
         //按最长词为基准，消去比此词长度小，被此词所包含，词频之差与该词之比小于等于 0.1 的词
         for (int i = 0; i< listWordKeys.size();i++){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 int count = body.getValue().count;
                 String _words = body.getValue().word;
                 int len = _words.length();
                 int size = 2;
                 while(size<len){
                    String _w1 = _words.substring(0,size);
                    if(listWordKeys.get(size - 1).containsKey(_w1)){
                        if (Math.abs(listWordKeys.get(size - 1).get(_w1).count - count)* 1.0/count <0.1){
                            count = listWordKeys.get(size - 1).get(_w1).count;
                            System.out.println(_w1);
                            listWordKeys.get(size -1).remove(_w1);
                        }
                    }
                    size++;
                 }
             }
         }
         */
//         dispListWords(listWordKeys);
         
        List<Map<String,KeyWord>> listNewWord = new ArrayList<Map<String,KeyWord>>();
        
        for(int i=0;i<maxSize;i++){
            Map<String,KeyWord> pamWord = new HashMap<String,KeyWord>();
            listNewWord.add(pamWord);
        }
        
        //清除纯数字高频词
        //假设2字词是合法，添加到新词表中；
        //以新词表为基准，计算取出合法的3字词添加到新词表；
        //以新词表为基准，计算取出合法的4字词添加到新词表；
        
        //取2字词        
        verifyWords2(listWordKeys,listNewWord);
        //取3字词
        verifyWords3(listWordKeys,listNewWord);        
        verifyWords4(listWordKeys,listNewWord);
        
         for (int i = 4; i< listWordKeys.size();i++){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String _words = body.getValue().word;
                String _w = _words.replaceAll("[a-z]{1,}", "");
                if(_w.equals(_words)){
                    listNewWord.get(i).put(body.getKey(), body.getValue());
                }
             }
         }        
//         System.out.println("new words!!!!!!!!!!!");
//         dispListWords(listNewWord);
         String val = transToString(listNewWord);
         System.out.println("new words!!!!!!!!!!!" + "  " + val);
        return listWordKeys;
    }
    
    private void verifyWords2(List<Map<String,KeyWord>> list,List<Map<String,KeyWord>> listNew){
        int wordLen = 2;
        Set _set = list.get(wordLen -1).entrySet();
        Iterator _it = _set.iterator();
        while(_it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)_it.next();
            String _word = body.getValue().word;
            String _w = _word.replaceAll("[\\d{1,}|a-z{1,}|A-Z{1,}]", "");
            if(!_w.equals(_word))
                continue;            
            String _w11 = _word.substring(0,1);
            int _l11 = _w11.length();
            int _c11 = listWords.get(_l11-1).get(_w11).count;
            
            String _w12 = _word.substring(1,2);
            int _l12 = _w12.length();
            int _c12 = listWords.get(_l12-1).get(_w12).count;
            
            String _w31 = _w11 + _w12;
            int  _l31 = _l11+_l12;
            int _c31 = listWords.get(_l31-1).get(_w31).count;
            
            
            double _f = 1.0 * _c31 * _c31/(_c11 * _c12) ;    
//            System.out.println(_word + " " + _c11 +";"+_c12+ ":" + _c31+ "||" +  _f);            
            if(_f >0.2){
                listNew.get(wordLen-1).put(body.getKey(), body.getValue());
            }
        }
    }    
    
    
    private void verifyWords3(List<Map<String,KeyWord>> list,List<Map<String,KeyWord>> listNew){
        int wordLen = 3;
        Set _set = list.get(wordLen -1).entrySet();
        Iterator _it = _set.iterator();
        while(_it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)_it.next();
            String _word = body.getValue().word;
            String _w = _word.replaceAll("[\\d{1,}|a-z{1,}|A-Z{1,}]", "");
            if(!_w.equals(_word))
                continue;            
            String _w11 = _word.substring(0,1);
            int _l11 = _w11.length();
            int _c11 = listWords.get(_l11-1).get(_w11).count;
            
            String _w12 = _word.substring(1,2);
            int _l12 = _w12.length();
            int _c12 = listWords.get(_l12-1).get(_w12).count;
            
            String _w31 = _w11 + _w12;
            int  _l31 = _l11+_l12;
            int _c31 = listWords.get(_l31-1).get(_w31).count;
            
            String _w21 = _word.substring(0,2);
            int _l21 = _w21.length();
            int _c21 = listWords.get(_l21-1).get(_w21).count;
            
            String _w22 = _word.substring(2,3);
            int _l22 = _w22.length();
            int _c22 = listWords.get(_l22-1).get(_w22).count;
            
            String _w32 = _w21 + _w22;
            int _l32 = _l21+_l22;
            int _c32 = listWords.get(_l32-1).get(_w32).count;
            
            double _f = 1.0 * _c31 * _c31/(_c11 * _c12) ;
            _f = _f * _c32 * _c32 /(_c21 * _c22);             
//            System.out.println(_word + " " + _c11 +";"+_c12+ ":" + _c31+ "||" + _c21+ " " + _c22 + " " + _c32 + " || " + _f);            
            if(_f >0.1){
                listNew.get(wordLen-1).put(body.getKey(), body.getValue());
            }
        }
    }
    

    private void verifyWords4(List<Map<String,KeyWord>> list,List<Map<String,KeyWord>> listNew){
        int wordLen = 4;
        Set _set = list.get(wordLen -1).entrySet();
        Iterator _it = _set.iterator();
        while(_it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)_it.next();
            String _word = body.getValue().word;
            String _w = _word.replaceAll("[\\d{1,}|a-z{1,}|A-Z{1,}]", "");
            if(!_w.equals(_word))
                continue;      
            
            String _w11 = _word.substring(0,1);
            int _l11 = _w11.length();
            int _c11 = listWords.get(_l11-1).get(_w11).count;
            
            String _w12 = _word.substring(1,2);
            int _l12 = _w12.length();
            int _c12 = listWords.get(_l12-1).get(_w12).count;
            
            String _w1 = _w11 + _w12;
            int  _l1 = _l11+_l12;
            int _c1 = listWords.get(_l1-1).get(_w1).count;
            
            String _w21 = _word.substring(0,2);
            int _l21 = _w21.length();
            int _c21 = listWords.get(_l21-1).get(_w21).count;
            
            String _w22 = _word.substring(2,3);
            int _l22 = _w22.length();
            int _c22 = listWords.get(_l22-1).get(_w22).count;
            
            String _w2 = _w21 + _w22;
            int _l2 = _l21+_l22;
            int _c2 = listWords.get(_l2-1).get(_w2).count;

            String _w31 = _word.substring(0,3);
            int _l31 = _w31.length();
            int _c31 = listWords.get(_l31-1).get(_w31).count;
            
            String _w32 = _word.substring(3,4);
            int _l32 = _w32.length();
            int _c32 = listWords.get(_l32-1).get(_w32).count;
            
            String _w3 = _w31 + _w32;
            int _l3 = _l31+_l32;
            int _c3 = listWords.get(_l3-1).get(_w3).count;            
            
            
            
            double _f = 1.0 * _c1 * _c1/(_c11 * _c12) ;
            _f = _f * _c2 * _c2 /(_c21 * _c22); 
            _f = _f * _c3 * _c3 /(_c31 * _c32); 
            
//            System.out.println(_word + " " + _c11 +";"+_c12+ ":" + _c1+ "||" + _c21+ " " + _c22 + " " + _c2 + " || " + _f);
            
            if(_f >0.05){
                listNew.get(wordLen-1).put(body.getKey(), body.getValue());
            }
        }
    }    
    
    private String transToString(List<Map<String,KeyWord>> listMap){
        String val = "";
         for (int i = 1; i< listMap.size();i++){
             Set set = listMap.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 if(!body.getValue().word.isEmpty()){
                     val = val + body.getValue().word + ",";
                 }
             }
         }
         return val;
    }
    
    private void dispWords(){
         for (int i = listWords.size() -1; i>0 ;i--){
             Set set = listWords.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 int count = body.getValue().count;
                 String _words = body.getValue().word;
//                 if(count>20)
                    System.out.println(count + "  " + _words);

             } 
        }         
    }
    
    
    public String extractWords(String sentance, LocalInfo data,String address){
        String[] words = diffPreWords(sentance);        
        //计算可能词的组合
        for(int i=0;i<words.length;i++){
            diffWords(words[i],maxSize);
        }
        //词频统计，计算有效新词
        List<Map<String,KeyWord>> listWordKeys = extractNameByCount(10);
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
        dispListWords(listWordKeys);
//        List<Map<String,KeyWord>> list = extractFilterNames(listWordKeys);
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Filter");
//        dispListWords(list);        
        VerifyAddess verify = new VerifyAddess();       
        String val = verify.verifyAddressName(listWordKeys,data,address);    
        
        return val;
    }
    
    public void dispListWords(List<Map<String,KeyWord>> listWordKeys){
        for(int i=0;i<listWordKeys.size();i++){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                 System.out.println(body.getValue().word + " | " + body.getValue().count );
             }            
        }
    }
    
   public String match(String content,String filter){
        String val ="";
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                val =m.group();
                break;
            }
        }catch(Exception e){
            System.out.println(" Word Parse match " + e.getMessage());
        }
        return val;
    }
    
    public static void main(String[] args){
        List<String> keys = new ArrayList<String>();
        CrawlerBaidu baiDu = new CrawlerBaidu();
        
      String addr = "上海徐汇区肇嘉浜路446弄伊泰利大厦1号楼14楼";
      keys.add(addr);
//      addr = "锦龙支路金凤大厦北侧(公交驾校北侧)";keys.add(addr);
//      addr = "松桥支路43号";keys.add(addr);
//      addr = "五红路附近";keys.add(addr);
//     addr = "江北区建新东路万丰花园北侧(金泰成灯饰市场北";keys.add(addr);
      addr = "渝州路79-117号渝福建陶城附114号(彩电";keys.add(addr);
      addr = "九龙坡区渝州路79-117号渝福建陶城E-86号";keys.add(addr);
      addr = "九龙坡区渝州路79-117号渝福建陶城";keys.add(addr);
//      addr= "重庆市九龙坡区";keys.add(addr);
//      addr= "福建省九龙坡区";keys.add(addr);
//      addr= "九龙坡区渝州路";keys.add(addr);
//        addr = "真光路1288号百联购物广场";keys.add(addr);
//           addr = "长宁天山路900号汇金百货(内近娄山关路)";keys.add(addr);
//           addr = "天津市蓟县官庄镇恒大金碧天下（近盘山风景区）";keys.add(addr);
//           addr = "官庄镇恒大金碧天下别墅区内";keys.add(addr);
//           addr = "天津市津南区小站镇米立方海世界水上乐园(津歧路与盛塘路交汇处)";keys.add(addr);
//           addr = "黄浦区西藏中路268号来福士广场内(近福州路)";keys.add(addr);
//           addr = "南京草场门大街96号（中青大厦3楼）";keys.add(addr);
//           addr = "北京经济技术开发区永昌北路9号西门（北京银行东侧，与可口可乐公司隔街相望）;district:经济技术开发区永昌北路9号西门（银行东侧，与可口可乐公司隔街相望）";keys.add(addr);
           addr = "中山大道江汉路happy站台A079号";keys.add(addr);
  //         addr = "中国江苏省南京市中山南路148号";
      ServiceAddress srvAddress = new ServiceAddress();
      Words words = new Words(srvAddress.db);
      
      
      
      words.diffWords(addr, 20);
      words.extractNameByCount(1);
//      String val = "九龙坡区渝州路79-117号渝福建陶城E-86号";
//      String mVal = words.match(val, "\\d{1,}-\\d{1,}号|\\d{1,}号");       
//      List<BaiDuData> listBaiDu = baiDu.SearchPage(addr);
      String key = "";
//      for(int i=0;i<listBaiDu.size();i++){
//            key = key + "," + listBaiDu.get(i).title + "," +listBaiDu.get(i).Abstract;
//      }
 //     String val = words.extractWords(key,data);
      
//      System.out.println(" rs : " + val);
      
      
//      List<Map<String,KeyWord>> listWordKeys = words.extractNameByCount(50);
//      List<Map<String,KeyWord>> list = words.extractFilterNames(listWordKeys);      
//      System.out.println("extractNames");
//      words.dispListWords(list);
//      System.out.println("verifyAddressName ");
//      words.verifyAddressName(list);
    }
}
