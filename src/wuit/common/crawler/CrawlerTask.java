package wuit.common.crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.composite.CompositeConvert;

import wuit.common.crawler.composite.DSCrawlerPageTxt;
import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.doc.FileIO;

public class CrawlerTask implements Runnable{
    private CrawlerJob _job = null;
    private DSExtractor _jobParams = null;
    DSCrawlerUrl pageUrl;

    public CrawlerTask(CrawlerJob job,DSCrawlerUrl pageUrl,DSExtractor jobParams){         
        this._jobParams = jobParams;//new DSExtractor(); 
        /*
        for(int i=0;i<_jobParams.listClearHtml.size();i++){
            this._jobParams.listClearHtml.add(_jobParams.listClearHtml.get(i));
        }        
        for(int i=0;i<_jobParams.listExtractFields.size();i++){
            this._jobParams.listExtractFields.add(_jobParams.listExtractFields.get(i));
        }        
        Set set = _jobParams.mapFilterUrl.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,Integer> body = (Entry<String,Integer>)it.next();
            this._jobParams.mapFilterUrl.put(body.getKey(), body.getValue());
        }

        set = _jobParams.mapFilterUrl.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Entry<String,String> body = (Entry<String,String>)it.next();
            this._jobParams.mapFilterFields.put(body.getKey(), body.getValue());
        }
        */
        this._job = job;
        
        this.pageUrl = pageUrl; 
        /*
        this.pageUrl.IP = pageUrl.IP;
        this.pageUrl.dns = pageUrl.dns;
        this.pageUrl.title = pageUrl.title;
        this.pageUrl.url = pageUrl.url;
        this.pageUrl.crawlerDeepth = pageUrl.crawlerDeepth;
        */
    }
 
    public void run(){
        try {
            Thread.sleep(0);
            CrawlerUtiles utiles = new CrawlerUtiles();
            Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
            DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt();
            
            utiles.extractHtmlPage(_jobParams,pageUrl, mapUrl, extractPageInfo);
            
            saveRs(extractPageInfo);
            synchronized(_job){
                _job.setCrawlerUrl(mapUrl, pageUrl, extractPageInfo.items.size());
            }
    //        _job.removeThread(pageUrl.dns);
            System.out.println("finish task !!!!");
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void saveRs(DSCrawlerPageTxt pageContent){
        if(_job == null)
            return;        
        CompositeConvert convert = new CompositeConvert();
        if (pageContent.items.size()>0){
            String name = pageContent.urlCrawler.title;
            if(name == null || name.equals(""))
                return;
            name = name.replaceAll("[^\\da-zA-Z\u4E00-\u9FA5]", "");
            if(name.length()>10)
                name = name.substring(0,10);
            String vals = "";
            for (int i=0;i<pageContent.items.size();i++){
                String _val = convert.DSCompositeToString(pageContent.items.get(i));
                if(!_val.isEmpty()){
                    vals = vals + _val + "\r\n";
                    System.out.println(_val);
                }
            }
            String content ="";
            if (vals.length()>10 && !_jobParams.outPath.equals("")){
                content = "url:{" + pageContent.urlCrawler.title + ";" + pageContent.urlCrawler.dns + ";" + pageContent.urlCrawler.url + "}\r\n";
                content = content + "items:{" + vals + "}\r\n";
                content = content +  "content:{" + pageContent.content + "}\r\n";
                FileIO.writeAsTxts(_jobParams.outPath, name ,content);
            }
        }
    }
  
    /*
    public void crawlerHtml(DSCrawlerUrl urlTask){
        CrawlerUtiles utiles = new CrawlerUtiles();        
        Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
        DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt();
        utiles.extractHtmlPage(_jobParams,urlTask, mapUrl, extractPageInfo);
//        if(extractPageInfo.items.size() > 0 )
//            _job.putContentList(extractPageInfo);
        _job.putUrlList(mapUrl, urlTask.crawlerDeepth);
    }
    */ 
}
