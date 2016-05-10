package wuit.common.crawler.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wuit.common.crawler.db.KeyValue;

public class DSExtractor {
    public int mode = 0;
    public int crawlerDeepth = 0;                   //搜索深度
    public int task = 1;                            //任务数,即线程数
    public String CrawlerUrl = "";                  //搜索链接
    public String dns = "";                         //
    public String title = "";                       //
    public int crawlerMode = 0;                     //搜索模式: 0:url ； 1:关键字 
    public List<String> crawlerKeyWords = new ArrayList<String>();              //搜索关键字
    
    public String outPath = "";
    
    public List<KeyValue> listExtractFields = new ArrayList<KeyValue>();        //搜索字段集
    public List<KeyValue> listClearHtml = new ArrayList<KeyValue>();            //清理HTML标记
    public Map<String,KeyValue> mapFilterUrl = new HashMap<String,KeyValue>();    //搜索链接过滤
    public Map<String,KeyValue> mapFilterFields = new HashMap<String,KeyValue>();         //搜索字段内容
}
