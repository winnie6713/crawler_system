/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler;

import wuit.crawler.main.MainServer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.searcher.CrawlerBaiDu;

/**
 *
 * @author lxl
 */
public class CrawlerManager extends Thread {
    private ThreadPoolExecutor pool;                                            //线程池
    private static int queueDeep = 2;
    public int crawlerMaxTasks = 0;                                             //最大任务数
    public int crawlerDeepths = 0;                                              //最大爬深度
    public int crawlerMode = -1;                                                //0:关键词;1:垂直;2:直接请求
    public Map<String,KeyValue> mapFilterUrl;                                   //默认网页过滤
    public String keysUrl = "";
    public int success = 0;
    public int running = 1;
    
    public CrawlerManager(DSCrawlerParam crawlerParam){
        crawlerDeepths = crawlerParam.deepth;
        keysUrl = crawlerParam.keyUrl;
        crawlerMode = crawlerParam.mode;
        crawlerMaxTasks = crawlerParam.tasks; 
        mapFilterUrl = crawlerParam.mapFilterUrl;
        Initialize();
    }
    
    public void Initialize(){
        if(crawlerMaxTasks<=0 || crawlerMode <0 || crawlerMode >2){
            JOptionPane.showMessageDialog(null, " 搜索引擎参数设置？，请检查引擎模式，任务数，关键词或链接等！！！");
            success = 0;
            return;
        }
        if(pool == null){
            pool = new ThreadPoolExecutor(crawlerMaxTasks,crawlerMaxTasks + queueDeep, 30, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(queueDeep),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        }
        success = 1;
    }
    
    public void run(){
        if(crawlerMode == 1){
            String html = CrawlerBaiDu.getWebPageHtml(keysUrl);
            
            DSCrawlerUrl currPageUrl = new DSCrawlerUrl();
            currPageUrl.crawlerDeepth = 0;            
            Map<String,DSCrawlerUrl> mapPageUrls = new HashMap<String,DSCrawlerUrl>();
            
            CrawlerBaiDu.getUrls(html, currPageUrl, mapPageUrls);
            MainServer.DBCrawler.putCrawlerUrls(mapPageUrls,0);        //链接写入缓存中
        }
        if(crawlerMode == 0){
            Map<String,DSCrawlerUrl> mapPageUrls = new HashMap<String,DSCrawlerUrl>();
            DSCrawlerUrl currPageUrl = new DSCrawlerUrl();
            currPageUrl.crawlerDeepth = 0; 
            currPageUrl.url = keysUrl;
            mapPageUrls.put(keysUrl, currPageUrl);
            
            MainServer.DBCrawler.putCrawlerUrls(mapPageUrls,0);
        }
        if(crawlerMode == 2){
            
        }
        try {
            while(running==1){
                DSCrawlerUrl  crawlerUrl = MainServer.DBCrawler.getCrawlerUrl();
                if(crawlerUrl == null){
                    Thread.sleep(5000);
                    continue;
                }
                CrawlerTask task = new CrawlerTask(crawlerUrl,crawlerMode,crawlerDeepths,mapFilterUrl);
                addNewTask(task);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void addNewTask(CrawlerTask task){
        try{
            while (getQueueSize(pool.getQueue()) >= queueDeep){ // 
                System.out.println("线程池已满，延时10秒再试！！！" + getQueueSize(pool.getQueue()));
                Thread.sleep(10000);
            }
            if (running == 1){
                pool.execute(task);
//                System.out.println("pool size :"  + getQueueSize(pool.getQueue()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    
    
    public synchronized void setStop(){
        running = 0;
    }
    public synchronized int getQueueSize(Queue queue){
        return queue.size();// pool.getPoolSize();
    } 
    
    public synchronized long getTaskCount(){
        return pool.getQueue().size();
    }
}
