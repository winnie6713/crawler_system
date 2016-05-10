/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.search;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.CrawlerUtiles;
import wuit.crawler.DSCrawlerUrl;

/**
 *
 * @author lxl
 */
public class Crawler360 {
    
    public static String getWebPageHtml(String keys){
//        System.out.println(keys);
        String valKey ="";
        try {
            valKey = java.net.URLEncoder.encode(keys,"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String baseUrl = "http://www.sogou.com/web?query="+keys+"&_asf=www.sogou.com";//&_ast=1389021529&w=01015002&p=40040108&ie=utf8&oq=&ri=3&sourceid=sugg&sut=0&sst0=1389021529342&lkt=0%2C0%2C0";
        baseUrl = "http://so.360.cn/s?ie=utf-8&src=hao_search&q=" + keys;
        String val = Crawler.doGetHttp(baseUrl);
        DSCrawlerUrl currPageUrl = new DSCrawlerUrl();
        currPageUrl.url = baseUrl;
        currPageUrl.dns = "so.360.cn";
        Map<String,DSCrawlerUrl> mapPageUrls = new HashMap<String,DSCrawlerUrl>();
        
        Crawler.getUrls(val, currPageUrl, mapPageUrls);
        
        val = Crawler.clearHtml(val);
        Set set = mapPageUrls.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
            if(body.getValue().dns.equals(currPageUrl.dns)){
                String _val = Crawler.doGetHttp(body.getValue().url);
                val = val + "," + Crawler.clearHtml(_val);
            }
        }        
        return val;
    }     
    
    
    
    public static String getWebPageText(String keys){
        return Crawler.clearHtml(getWebPageHtml(keys));
    }    
}
