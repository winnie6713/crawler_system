/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler;

import wuit.crawler.main.MainServer;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.searcher.Crawler;

/**
 *
 * @author lxl
 */
public class CrawlerTask  implements Runnable {
    private DSCrawlerUrl  crawlerUrl;
    private int crawlerMode;
    private int maxDeepth;
    private Map<String,KeyValue> mapFilterUrl;
    
    public CrawlerTask(DSCrawlerUrl  crawlerUrl,int crawlerMode,int maxDeepth,Map<String,KeyValue> mapFilterUrl){
        this.crawlerUrl = crawlerUrl;
        this.crawlerMode = crawlerMode;
        this.maxDeepth = maxDeepth;
        this.mapFilterUrl = mapFilterUrl;
    }
    
    public void run(){
        try {
            Thread.sleep(10);
            String html = Crawler.doGetHttp(crawlerUrl.url);
            DSCrawlerUrl pageUrl = new DSCrawlerUrl();
            pageUrl.url = crawlerUrl.url;                                   
            Crawler.getUrlInfo(pageUrl);                                        //获取该网页链接信息
            MainServer.DBCrawler.putHtmlPage(pageUrl,html);                      //将网页链接信息和网页html文档推送到缓存表中，以供网页内容提取线程使用                                             

            System.out.println(crawlerUrl.url);
            
            if(crawlerUrl.crawlerDeepth< maxDeepth){                                            
                Map<String,DSCrawlerUrl> mapPageUrls = Crawler.extractLinks(html, crawlerUrl);          //从网页中提取链接
                MainServer.DBCrawler.filterUrls(crawlerMode, mapPageUrls, pageUrl, mapFilterUrl);       //按搜索模式过滤无效的链接
                MainServer.DBCrawler.putCrawlerUrls(mapPageUrls,crawlerUrl.crawlerDeepth + 1);          //将提取链接添加到缓存表
            }
            
            //MainServer.crawlerState.setLinkFilters(maxDeepth);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
