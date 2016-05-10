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
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.CrawlerUtiles;
import wuit.crawler.DSCrawlerUrl;

/**
 *
 * @author lxl
 */
public class CrawlerSogou {
    public static String getWebPageHtml(String keys){
//        System.out.println(keys);
        String valKey ="";
        try {
            valKey = java.net.URLEncoder.encode(keys,"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String baseUrl = "http://www.sogou.com/web?query="+keys+"&_asf=www.sogou.com";
        String val = Crawler.doGetHttp(baseUrl);
        DSCrawlerUrl currPageUrl = new DSCrawlerUrl();
        currPageUrl.url = baseUrl;
        currPageUrl.dns = "www.sogou.com";
        Map<String,DSCrawlerUrl> mapPageUrls = new HashMap<String,DSCrawlerUrl>();
        
        Crawler.getUrls(val, currPageUrl, mapPageUrls);
        
        val = Crawler.clearHtml(val);
        Set set = mapPageUrls.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
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
