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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.search.Crawler360;
import wuit.common.crawler.search.CrawlerBaidu;
import wuit.common.crawler.search.CrawlerSogou;
import wuit.common.crawler.search.CrwalerGoogle;
import wuit.common.dictionary.address.ServiceAddress;

/**
 *
 * @author lxl
 */
public class LearnNewWords {
    public List<KeyWord> listLearner = new ArrayList<KeyWord>();
    public String phone = "";
    public String post = "";
    
    private String[] learnorArray = {"baidu","360","sogou","google"};
    
    private void InitializeLearner(){
        for(int i=0;i<learnorArray.length;i++){
            KeyWord learner = new KeyWord();
            learner.value = learnorArray[i];
            learner.count = 0;
            listLearner.add(learner);
        }
    }    
    
    public LearnNewWords(){
        InitializeLearner();
    }
    
    public void netLearning(String words){
        String content = "";
        int _count = 5;
        try {
            while(true){
                int count = listLearner.get(0).count;
                int n = 0;
                for(int j=1;j<listLearner.size();j++){
                    if(listLearner.get(j).count<count){n = j; }
                }
                listLearner.get(n).count ++;
                switch(n){
                    case 0:     content = CrawlerBaidu.getWebPageText(words);       break;
                    case 1:     content = Crawler360.getWebPageText(words);         break;
                    case 2:     content = CrawlerSogou.getWebPageText(words);       break;
                    case 3:     content = CrwalerGoogle.getWebPageText(words);      break;
                }
                if(content.isEmpty() || content.length()<1000){
                    if(_count<0){  break; }
                    _count --;
                    Thread.sleep(2000);
                    continue;
                }
                if(content.isEmpty()){
                    System.out.println("网络不给力 ！！！");
                }else{
                    System.out.println("正在计算中 ...");
                }
                break;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        phone = getPhone(content);
        post = getPost(content);
        
        StatisticsWords statWords = new StatisticsWords();
        listLearner =  statWords.extractWords(content); 
    }
    
    private String getPhone(String content){
//        System.out.println(content);
        List<KeyValue> list_phone = new ArrayList<KeyValue>();
        matchValues(content,"(?<=,)\\d{3},\\d{8}|\\d{4},\\d{8}(?=,)",list_phone);
        Map<String,KeyWord> mapPhone = new HashMap<String,KeyWord>();
        for(int i=0; i<list_phone.size();i++){
//            System.out.println(list_phone.get(i).key);
            String[] val = list_phone.get(i).key.split(",");
            if(val.length>0){
                if(mapPhone.containsKey(val[0])){
                    mapPhone.get(val[0]).count = mapPhone.get(val[0]).count + 1;
                }else{
                    KeyWord word = new KeyWord();
                    word.count = 1;
                    word.value = val[0];
                    mapPhone.put(val[0], word);                    
                }
            }
        }

        int _sum = 0;
        String phone = "";
        Set set = mapPhone.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
            System.out.println(body.getKey() + " : " + body.getValue().count);
            if(_sum<body.getValue().count){
                _sum = body.getValue().count;
                phone = body.getKey();
            }
        }        
        System.out.println(phone);
        if(_sum<4)
            phone = "";
        return phone;
    }
    
    private String getPost(String content){
        List<KeyValue> list_post = new ArrayList<KeyValue>();
        matchValues(content,"(?<=,)\\d{6}(?=,)",list_post);
        Map<String,KeyWord> mapPost = new HashMap<String,KeyWord>();
        for(int i=0; i<list_post.size();i++){
//            System.out.println(list_post.get(i).key);
            String val = list_post.get(i).key;
            if(mapPost.containsKey(val)){
                mapPost.get(val).count = mapPost.get(val).count + 1;
            }else{
                KeyWord word = new KeyWord();
                word.count = 1;
                word.value = val;
                mapPost.put(val, word);
            }
        }        
        
        
        int _sum = 0;
        String post = "";
        Set set = mapPost.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,KeyWord> body = (Entry<String,KeyWord>)it.next();
//            System.out.println(body.getKey() + " : " + body.getValue().count);
            if(_sum<body.getValue().count){
                _sum = body.getValue().count;
                post = body.getKey();
            }
        }
        System.out.println(post);        
        if(_sum <= 6)
            post = "";

        return post;
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
            System.out.println("learnWords  matchValues :" + e.getMessage());
        }
    }     
    
    public static void main(String[] args){
        String word = "我要点评 公交驾乘 江苏移动六合分公司营业厅";
        word ="2人点评 公交驾乘 5. 讯达通信";
        word = "佘山月湖旅游佘山月湖门票预订>>推荐指数:3.5";
        //word = "上海佘山月湖";
        word ="上海南汇起凤台";
        word = "鼓楼湖北路22号";
        LearnNewWords learner = new LearnNewWords();
        learner.netLearning(word);
        //StatisticsWords statWords = new StatisticsWords();
        //statWords.extractWords(content);
        
    }
}
