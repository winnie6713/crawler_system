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
//import static wuit.common.crawler.search.Crawler360.doGetHttp;

/**
 *
 * @author lxl
 */
public class CrwalerGoogle {
    public static String getWebPageHtml(String keys){
        String valKey ="";
        try {
            valKey = java.net.URLEncoder.encode(keys,"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String baseUrl ;//= "http://so.360.cn/s?q=" + valKey;
        //baseUrl = "http://www.google.com.hk/search?hl=zh-CN&client=aff-360daohang#affdom=360.cn&newwindow=1&q="+ valKey + "&safe=strict";
        //baseUrl = "http://www.google.com.hk/webhp?hl=zh-CN&num=100&q=" + valKey ;//+ "&safe=strict";
//        baseUrl = "https://www.google.com/search?newwindow=1&nfpr=1&filter=1&pws=0&hl=en&safe=off&num=30&start=0&as_qdr=&as_filetype=&q=" + valKey;
        baseUrl = "https://www.google.com.hk/search?hl=zh-CN&newwindow=1&q=" + valKey;
        
//        baseUrl = "http://www.google.com.hk/search?hl=zh-CN&as_q="+ valKey +"&as_epq=&as_oq=&as_eq=&as_nlo=&as_nhi=&lr=lang_zh-CN&cr=countryCN&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&as_filetype=&as_rights=";
//       baseUrl = "http://www.sogou.com/web?query="+keys+"&_asf=www.sogou.com";//&_ast=1389021529&w=01015002&p=40040108&ie=utf8&oq=&ri=3&sourceid=sugg&sut=0&sst0=1389021529342&lkt=0%2C0%2C0";
        System.out.println(baseUrl);
        String val = Crawler.doGetHttp(baseUrl);
 
        DSCrawlerUrl currPageUrl = new DSCrawlerUrl();
        currPageUrl.url = baseUrl;
        currPageUrl.dns = "www.google.com.hk";
        Map<String,DSCrawlerUrl> mapPageUrls = new HashMap<String,DSCrawlerUrl>();
        
        Crawler.getUrls(val, currPageUrl, mapPageUrls);
        
        val = Crawler.clearHtml(val);
        Set set = mapPageUrls.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
            if(body.getValue().dns.equals(currPageUrl.dns)){
                System.out.println(currPageUrl.url);
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
