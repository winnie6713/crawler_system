/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.main;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.crawler.db.KeyValue;
import wuit.common.dictionary.address.ServiceAddress;
import wuit.crawler.CrawlerManager;
import wuit.crawler.DSCrawlerParam;
import wuit.crawler.database.CrawlerDatabase;
import wuit.crawler.database.ExtractorDB;
import wuit.crawler.database.LocalInfoDB;
import wuit.crawler.extract.ExtractContentManager;

/**
 *
 * @author lxl
 */
public class MainServerKeyword  extends Thread {
    public static CrawlerDatabase DBCrawler;
    public static ExtractorDB DBExtractor;
    public static CrawlerState state;
    public static ServiceAddress srvAddress = new ServiceAddress();
    
    public static  LocalInfoDB localDB = new LocalInfoDB();
    
    public static CrawlerState crawlerState = new CrawlerState();
    public int running = 1;
    private static DSExtractor params;
    public static String dns = "";
    
    //网络爬虫运行参数
    public int crawlerMaxTasks = 0;                                             //最大任务数
    public int crawlerDeepths = 0;                                              //最大爬深度
    public int crawlerMode = -1;                                                //0:关键词;1:垂直;2:直接请求
    public Map<String,KeyValue> mapFilterUrl;                                   //默认网页过滤
    public String keysUrl = "";    
                                    
    //网页信息处理参数
    public int taskNum = 0;                                                     //处理网页任务数
    
    public DSCrawlerParam paramCrawler = null;
    
    public ExtractContentManager extractManager = null;
    public CrawlerManager crawlerManager = null;
    
    public MainServerKeyword( ){
    }
    
    public synchronized static void refreshExtractRuler(){
 //       synchronized(DBExtractor){
            DBExtractor = new ExtractorDB();
 //       }
    }
    
    
    public void setCrawlerParams(DSCrawlerParam crawlerParam){
        crawlerDeepths = crawlerParam.deepth;
        keysUrl = crawlerParam.keyUrl;
        crawlerMode = crawlerParam.mode;
        crawlerMaxTasks = crawlerParam.tasks;
        mapFilterUrl = crawlerParam.mapFilterUrl;
        crawlerManager = new CrawlerManager(crawlerParam);
    }    
    
    
//    public void Initialize(DSExtractor params) {
    public void Initialize(int extractors,DSCrawlerParam paramCrawler) {
        DBCrawler = new CrawlerDatabase(paramCrawler.deepth);
        DBExtractor = new ExtractorDB();
        paramCrawler.mapFilterUrl = DBExtractor.mapFilterUrl;
        this.paramCrawler = paramCrawler;
        setCrawlerParams(paramCrawler);
        this.paramCrawler.mapFilterUrl = DBExtractor.mapFilterUrl;
        crawlerManager.start();

        extractManager = new ExtractContentManager(extractors);
        extractManager.start();
        
        state = new CrawlerState();
    }
    
    public synchronized void stopCrawling(){
        running = 0;
        extractManager.running = 0;
        crawlerManager.running = 0;
    }
    
    public synchronized void continueCrawling(){
        running = 1;
    }
    
    public void run(){
        try {
            Thread.sleep(1000);
            System.out.println(crawlerManager.getTaskCount());
            Thread.sleep(1000);
            System.out.println(extractManager.getTaskCount());
            Thread.sleep(5000);
            /*
            for(int i=0;i<listCrawlers.size();i++){
                listCrawlers.get(i).start();
                Thread.sleep(1000);
            }            
            while(running == 1){
                Thread.sleep(1000);
                checkCrawlersState();
            }

            for(int i=0;i<listCrawlers.size();i++){
                listCrawlers.get(i).setState(0);
                Thread.sleep(1000);
            }
            
            for(int i=0;i<listExtractors.size();i++){
                listExtractors.get(i).state = 0;
                Thread.sleep(1000);
            }
            */
        } catch (InterruptedException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void main(String[] args){
        MainServer server = new MainServer();
        int extractors = 2;
        DSCrawlerParam paramCrawler = new DSCrawlerParam();
        paramCrawler.deepth = 2;
        paramCrawler.keyUrl = "http://www.aibang.com/shanghai/";
        paramCrawler.mapFilterUrl = null;
        paramCrawler.mode = 0;
        paramCrawler.tasks = 2;
        
        
        server.Initialize(extractors, paramCrawler);
        server.start();
    }
    
}
