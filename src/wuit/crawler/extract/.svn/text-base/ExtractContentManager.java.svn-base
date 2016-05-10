/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.extract;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import wuit.crawler.CrawlerHtmlData;
import wuit.crawler.main.MainServer;

/**
 *
 * @author lxl
 */
public class ExtractContentManager extends Thread {
    private ThreadPoolExecutor pool;                                            //线程池
    private static int queueDeep = 4;
    
    CrawlerHtmlData data;   
    
    public int taskNum = 0;
    public int success = 0;
    
    public int running = 1;
    
    public ExtractContentManager(int tasks){
        this.taskNum = tasks;        
        Initialize();
    }
    
    public void Initialize(){
        if(taskNum<=0 ){
            JOptionPane.showMessageDialog(null, " 搜索引擎参数设置？，请检查清理网页，网页匹配库等！！！");
            success = 0;
            return;
        }
        if(pool == null){
            pool = new ThreadPoolExecutor(taskNum,taskNum + queueDeep, 30, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(queueDeep),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        }
        success = 1;
    }
    
    
    public void run(){
        try {
            while(running == 1){
                Thread.sleep(10);
                CrawlerHtmlData data = MainServer.DBCrawler.getHtmlPage();
                if(data == null){
                    Thread.sleep(5000);
                    continue;
                }
                MainServer.state.pageExtractNum ++;
                ExtractItemtTask task = new ExtractItemtTask(data);
                addNewTask(task);
                Thread.sleep(1000);                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ExtractContentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addNewTask(ExtractItemtTask task){
        try{
            while (getQueueSize(pool.getQueue()) >= queueDeep){ // 
                System.out.println("线程池已满，延时10秒再试！！！" + getQueueSize(pool.getQueue()));
                Thread.sleep(10000);
            }
            if (running == 1){
                pool.execute(task);
                //System.out.println("pool size :"  + getQueueSize(pool.getQueue()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public synchronized int getQueueSize(Queue queue){
        return queue.size();// pool.getPoolSize();
    }  
    
    public synchronized long getTaskCount(){
        return pool.getQueue().size();
    }    
}
