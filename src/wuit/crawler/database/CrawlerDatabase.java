/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.CrawlerHtmlData;
import wuit.crawler.DSCrawlerUrl;
import wuit.crawler.main.MainServer;

/**
 *
 * @author lxl
 */
public class CrawlerDatabase {
    public Map<String,DSCrawlerUrl> mapDownload = new HashMap<String,DSCrawlerUrl>();        //已下载的url链接
    public List<List<DSCrawlerUrl>> listCrawlerUrls = new ArrayList<List<DSCrawlerUrl>>();   //按搜索深度记录待下载的链接
    public List<DSCrawlerUrl> listCacheUrls = new ArrayList<DSCrawlerUrl>();                 //待下载的链接缓存表  
    
    public List<DSComposite> listDSComposites = new ArrayList<DSComposite>();                //缓存提取的位置信息记录
                                                               //缓存提取的位置信息记录
    public List<DSComposite> listDSCompositesDrct = new ArrayList<DSComposite>();            //缓存提取的位置信息记录

    //public List<DSComposite> listDSCompositesNull = new ArrayList<DSComposite>();            //缓存提取的位置信息记录
//    public int countItemsParseNull = 0;
    //public List<DSComposite> listDSCompositesAmb = new ArrayList<DSComposite>();             //缓存提取的位置信息记录

    //public List<DSComposite> listDSCompositesValidate = new ArrayList<DSComposite>();             //缓存提取的位置信息记录
//    public int countRsValidate = 0;
    //public List<DSComposite> listDSCompositesInvalidate = new ArrayList<DSComposite>();             //缓存提取的位置信息记录

    public List<CrawlerHtmlData> listHtmlPages = new ArrayList<CrawlerHtmlData>();           //缓存下载网页的基本信息和html文档    
//    public List<ExtractItems> listExtractItems = new ArrayList<ExtractItems>();            //提取位置信息记录表
    
    public CrawlerStatInfo statInfo = new CrawlerStatInfo();
    
    
    
    
    public CrawlerDatabase(int maxDeepth){
        for(int i=0;i<=maxDeepth;i++){
            List<DSCrawlerUrl> list = new ArrayList<DSCrawlerUrl>();
            listCrawlerUrls.add(list);
        }
    }
    
    public synchronized void putHtmlPage(DSCrawlerUrl pageUrl,String html){
        if(html==null || html.equals(""))
            return;
        if(pageUrl.url == null || pageUrl.url.equals(""))
            return;
        CrawlerHtmlData data = new CrawlerHtmlData();
        data.html = html;
        data.pageInfo = pageUrl;
        listHtmlPages.add(data);
    }
    
    public synchronized CrawlerHtmlData getHtmlPage(){
        CrawlerHtmlData data = null;
        if(listHtmlPages.size()>0){
            data = listHtmlPages.get(0);
            listHtmlPages.remove(0);
//            MainServer.state.pageExtractNum = MainServer.state.pageExtractNum +1;
        }
        return data;
    } 
    
    
    public synchronized void putCrawlerUrls(Map<String,DSCrawlerUrl> urls,int currDeepth){
        Set set = urls.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
            if(!body.getValue().url.equals(""))
                listCrawlerUrls.get(currDeepth).add(body.getValue());
        }
    }

    public synchronized DSCrawlerUrl getCrawlerUrl(){
        DSCrawlerUrl crawlerUrl = new DSCrawlerUrl();
        int delCount=0;
        for (int i = listCrawlerUrls.size() - 1;i>=0;i--){
            int j=0;
            int selected = 0;
            while(listCrawlerUrls.get(i).size()>0 && j<listCrawlerUrls.get(i).size()){
                crawlerUrl = listCrawlerUrls.get(i).get(j);
                if (mapDownload.containsKey(crawlerUrl.url)){
                    mapDownload.get(crawlerUrl.url).count_down = mapDownload.get(crawlerUrl.url).count_down + 1;
                    
                    listCrawlerUrls.get(i).remove(j);
                    delCount = delCount + 1;
                    j++;                    
                }else{
                    crawlerUrl.count_down = 1;
                    mapDownload.put(crawlerUrl.url,crawlerUrl);
                    listCrawlerUrls.get(i).remove(j);
                    selected = 1;
                    
                    break;
                }
            }
            if(selected == 1)
                break;
        }        
        
//        MainServer.state.setLinkRepeat(delCount);                               //重复链接计数
//        MainServer.state.setLinkDowns(mapDownload.size());                      //重置已经下载链接总数

        return crawlerUrl;
    }    
    
    public synchronized void filterUrls(int mode,Map<String,DSCrawlerUrl> urls,DSCrawlerUrl pageUrl,Map<String,KeyValue> mapFilterUrl){
        Map<String,DSCrawlerUrl> mapDelUrl = new HashMap<String,DSCrawlerUrl>();
        if(mode == 0 ) {                              //垂直搜索，即固定在指定的网站(域名)上搜索
            String dns = MainServer.dns;
            Set set = urls.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
                DSCrawlerUrl _info = body.getValue();
                if(_info.url.indexOf(dns) == -1){
                    mapDelUrl.put(_info.url,_info);
                }
            }
        }
//        if(mode == 1) {                                                         //关键字搜索，过滤已知的不需要搜索的网站，保存在文件中。在mapFilterUrl对象集中指定
            Set set = urls.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
                DSCrawlerUrl _info = body.getValue();
                //验证url是否有效（即是否包含关键字），如果是无效效的，则添加到删除表
                if(mapFilterUrl == null)
                    continue;
                
                Set _set = mapFilterUrl.entrySet();
                Iterator _it = _set.iterator();
                while(_it.hasNext()){
                    Map.Entry<String,KeyValue> _body = (Map.Entry<String,KeyValue>)_it.next();
                    if((Integer.parseInt(_body.getValue().key) == 0 && _info.url.indexOf(_body.getValue().value)>=0)||_info.dns.isEmpty()){
                        mapDelUrl.put(_info.url,_info);                         //需要删除的链接
                    }
                }
            }
        //}        
        
//        MainServer.state.setLinkRepeat(mapDelUrl.size());                       //更新链接重复数
            //重置过滤链接数
//       MainServer.state.setLinkFilters(mapDelUrl.size());        
        //删除无效的链接
        set = mapDelUrl.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Map.Entry<String,DSCrawlerUrl> body = (Map.Entry<String,DSCrawlerUrl>)it.next();
            urls.remove(body.getKey());
        }        
    }
    
    
   
    /*
    public synchronized void putItems(List<ExtractItems> items){
        listExtractItems.addAll(items);
    } 
    */
}
