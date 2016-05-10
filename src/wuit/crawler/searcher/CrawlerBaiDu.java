/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.searcher;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import wuit.common.crawler.CrawlerUtiles;
import wuit.crawler.DSCrawlerUrl;
import wuit.crawler.main.MainServer;

/**
 *
 * @author lxl
 */
public class CrawlerBaiDu {
    public static String getWebPageHtml(String keyWords){
        String valKey ="";
        try {
            valKey = java.net.URLEncoder.encode(keyWords,"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String baseUrl = "http://www.baidu.com/s?wd=" + valKey + "&cl=3&pn=10&tn=site888_pg&rn=100";
        return Crawler.doGetHttp(baseUrl);
    }

    public static void getUrls(String html,DSCrawlerUrl currPageUrl,Map<String,DSCrawlerUrl> mapPageUrls){
        Parser m_parser ;
        if(html == null || html.equals(""))
            return ;
        try{
            m_parser = Parser.createParser(html,"utf-8");
            NodeFilter filter = new TagNameFilter("a");
            NodeList nodelist = m_parser.parse(filter);
            NodeIterator it = nodelist.elements();
            while(it.hasMoreNodes()){
                LinkTag node = (LinkTag)it.nextNode();
                String url = node.getAttribute("href");
                if(url!=null && url.indexOf("#")>=0 || url.toLowerCase().indexOf("script")>=0)
                    continue;
                if((url == null || url.equals("") )){
                    continue;
                }
                //System.out.println(url);
                //解析url
                DSCrawlerUrl _pageUrl = Crawler.parsePageUrl(url,currPageUrl.url);
                if(_pageUrl == null)
                    continue;
                _pageUrl.crawlerDeepth = currPageUrl.crawlerDeepth + 1;
                mapPageUrls.put(url, _pageUrl);
            }
            
        }catch(Exception e){
            System.out.println(" composite Convert extractorUrl :" + e.getMessage());
        }
    }
}
