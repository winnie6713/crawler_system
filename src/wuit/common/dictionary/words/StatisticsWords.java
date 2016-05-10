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

/**
 *
 * @author lxl
 */
public class StatisticsWords {
    public String marks = "[&gt;｜ |　|、|.|．|。|…|:|：|,|，|_|＿|-|－|—|/|\\(|\\)|（|）|＂|“|”|【|】|\\[|\\]]";//．/／<>＜＞《》?？;；'＇‘:：";//\"＼、［］【】{}｛｝\\｀·|｜`~～!！@＠";//\\#＃$＄￥%％^︿&＆*＊-－——=＝+＋";    
    public List<Map<String,KeyWord>> listWords = new ArrayList<Map<String,KeyWord>>();          //按词长度存储分词结果表
    public int maxSize= 50;
    
    public StatisticsWords(){
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
        String _word = words;
            
        int len = 0;
        for (int i = 0; i< listWords.size();i++){
            len = _word.length();
            int start = 0;
            int size = i+1;                                                     //分词长度
            while(start + size <= len){
                String key = _word.substring(start, start + size);
                start ++;
                
                if(listWords.get(i).containsKey(key)){
                    listWords.get(i).get(key).count = listWords.get(i).get(key).count + 1;
                }else{
                    KeyWord word = new KeyWord();
                    word.count = 1;
                    word.value = key;
                    listWords.get(i).put(key, word);    
                }
            }
        }
    }   
    
    
    
    public  List<KeyWord> extractWords(String sentance){
        String[] words = diffPreWords(sentance);        
        //计算可能词的组合
        for(int i=0;i<words.length;i++){
            diffWords(words[i],maxSize);
        }
        //词频统计，计算有效新词
        List<Map<String,KeyWord>> listWordKeys = extractNameByCount(20);
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
//        dispListWords(listWordKeys);
        
        //////////////////////////////////////////////////////
        List<KeyWord> list = new ArrayList<KeyWord>();
        for(int i = 0; i<listWordKeys.size();i++){
            Set set = listWordKeys.get(i).entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
                String _w = body.getValue().value;
                _w = _w.replaceAll("[a-z{1,}|0-9{1,}]", "");
                if(body.getValue().value.equals(_w)){
                    KeyWord _word = new KeyWord();
                    _word.count = body.getValue().count;
                    _word.value = body.getValue().value;
                    list.add(_word);
                }
            }
        }
        return list;
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
                 String _w = body.getValue().value.replaceAll("[\\d{1,}]", "");
                 if(!_w.equals(body.getValue().value)){
                     continue;
                 }                 
                 KeyWord _word = new KeyWord();
                 _word.count = body.getValue().count;
                 _word.value = body.getValue().value;
                 listWordKeys.get(i).put(_word.value, _word);
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
                 String _words = body.getValue().value;
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
                String _words = body.getValue().value;
                String _w = _words.replaceAll("[a-z]{1,}", "");
                if(_w.equals(_words)){
                    listNewWord.get(i).put(body.getKey(), body.getValue());
                }
             }
         }
//         dispListWords(listNewWord);
         String val = transToString(listNewWord);
//         System.out.println("new words!!!!!!!!!!!" + "  " + val);
        return listNewWord;
    }    

    
    private void verifyWords2(List<Map<String,KeyWord>> list,List<Map<String,KeyWord>> listNew){
        int wordLen = 2;
        Set _set = list.get(wordLen -1).entrySet();
        Iterator _it = _set.iterator();
        while(_it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)_it.next();
            String _word = body.getValue().value;
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
            String _word = body.getValue().value;
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
            String _word = body.getValue().value;
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
                 if(!body.getValue().value.isEmpty()){
                     val = val + body.getValue().value + ",";
                 }
             }
         }
         return val;
    }    
    
    public void dispListWords(List<Map<String,KeyWord>> listWordKeys){
        for(int i=0;i<listWordKeys.size();i++){
             Set set = listWordKeys.get(i).entrySet();
             Iterator it = set.iterator();
             while(it.hasNext()){
                 Map.Entry<String,KeyWord> body = (Map.Entry<String,KeyWord>)it.next();
                 
                 if(body.getValue().count>5)
                 
                 System.out.println(body.getValue().value + " | " + body.getValue().count );
             }            
        }
    }    
}
