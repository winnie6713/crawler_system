package wuit.common.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import wuit.common.crawler.composite.CompositeConvert;

import wuit.common.crawler.composite.DSCrawlerPageTxt;
import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.doc.FileIO;

public class CrawlerJob extends Thread {
    private static int queueDeep = 10;
    private ThreadPoolExecutor pool;
    private int jobState = 1;
    public Map<String,DSCrawlerUrl> mapDownload ;          //已下载的url链接
    public Map<String,DSCrawlerUrl> mapHostCount ;
    public List<List<DSCrawlerUrl>> listJobUrls = new ArrayList<List<DSCrawlerUrl>>();
    
    //public Map<String,Integer> mapTasks;
    private int dnsMax = 1;
    
    private int filterDNS = 0;
    private int diffCount = 0;
    private DSExtractor params;

    private javax.swing.JLabel _statusLabel = null;             //输出状态，标签控件
        
    public void setDispLabel(javax.swing.JLabel label){
        this._statusLabel = label;
    }

    public CrawlerJob(DSExtractor extractInfo){
        mapDownload = new HashMap<String,DSCrawlerUrl>();
        mapHostCount = new HashMap<String,DSCrawlerUrl>();
        this.params = extractInfo;
        
//        mapTasks = new HashMap<String,Integer>();
    }
    
    /*
    public synchronized void removeThread(String dns){
        if(mapTasks.containsKey(dns)){
            if(mapTasks.get(dns)==1)                
                mapTasks.remove(dns);
            else                
                mapTasks.put(dns, mapTasks.get(dns).intValue()-1);
            System.out.println("remove dns thread " + dns + "  " +"mapTasks :" + mapTasks.size());
        }
    }
    */ 
  
    
    private void Init(){
//        crawlerData = new CrawlerData(this,params.outPath,parse);
        FileIO.createDir(params.outPath);
        //初始化 URL 数组
        List<DSCrawlerUrl> ls = new ArrayList<DSCrawlerUrl>();
        listJobUrls.add(ls);
        for (int i=0;i<params.crawlerDeepth; i++){
            ls = new ArrayList<DSCrawlerUrl>();
            listJobUrls.add(ls);
        }
        
        if ( params.crawlerMode == 0){                                          //url搜索 + 搜索深度
            ls = new ArrayList<DSCrawlerUrl>();
            DSCrawlerUrl info = new DSCrawlerUrl();
            info.url = params.CrawlerUrl;
            info.title = "start crawler";
            System.out.println(info.url);
            dispInfo();
            
            Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
            CrawlerUtiles utiles = new CrawlerUtiles();
            DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt();            
            utiles.extractHtmlPage(params,info, mapUrl, extractPageInfo);
//            getTaskResult(mapUrl,info,extractPageInfo);
            setCrawlerUrl(mapUrl,info,extractPageInfo.items.size());
//            putUrlList(mapUrl,info.crawlerDeepth);
        }
        
        if (params.crawlerMode==1){         //关键字模式搜索
            ls = new ArrayList<DSCrawlerUrl>();            
            DSCrawlerUrl info = new DSCrawlerUrl();
            info.url = params.CrawlerUrl;
            info.title = "start crawler";
            info.crawlerDeepth = 0 ;
            
            CrawlerUtiles utiles = new CrawlerUtiles();
            Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
            
            for(int i=0;i<params.crawlerKeyWords.size();i++){
                DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt();
                utiles.CrawlerKeyWordsBaiDu( params, mapUrl, extractPageInfo);
//                getTaskResult(mapUrl,info,extractPageInfo);
                setCrawlerUrl(mapUrl,info,extractPageInfo.items.size());
//                putUrlList(mapUrl,info.crawlerDeepth);
                saveRs(extractPageInfo);
            }
        }
    }
    
    /*
    public synchronized void getTaskResult(Map<String,DSCrawlerUrl> mapUrl,DSCrawlerUrl pageUrl,DSCrawlerPageTxt extractPageInfo){
        putUrlList(mapUrl, pageUrl.crawlerDeepth);
/*            synchronized(this)
            {
                if (pageUrl.crawlerDeepth <= params.crawlerDeepth){
                    Set set = mapUrl.entrySet();
                    Iterator it = set.iterator();
                    while(it.hasNext()){
                        Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
                        listJobUrls.get(pageUrl.crawlerDeepth).add(body.getValue());
                    }
                }
            }        
*/        
//        removeThread(pageUrl.dns);
/*        synchronized(mapTasks)
        //{
            if(mapTasks.containsKey(pageUrl.dns)){
                if(mapTasks.get(pageUrl.dns)==1)                
                    mapTasks.remove(pageUrl.dns);
                else                
                    mapTasks.put(pageUrl.dns, mapTasks.get(pageUrl.dns).intValue()-1);
                System.out.println("remove dns thread " + pageUrl.dns + "  " +"mapTasks :" + mapTasks.size());
            }
        //}            
*/        
//        putHostCount(extractPageInfo);
/*        synchronized(mapHostCount)
        //{
            /*
            String dns = extractPageInfo.urlCrawler.dns;        
            if(mapHostCount.containsKey(dns)){
                DSCrawlerUrl down = mapHostCount.get(dns);
                down.count_down = down.count_down + 1;
                if(extractPageInfo.count>0)
                    down.count_item += 1;
                else
                    down.count_item += 0;
                mapHostCount.put(dns, down);
            }else{
                DSCrawlerUrl down = new DSCrawlerUrl();
                down.path = dns;
                down.count_down = 1;
                if(extractPageInfo.count>0)
                    down.count_item = 1;
                else
                    down.count_item = 0;
                mapHostCount.put(dns, down);
            }
             
        }*/
    //}
    
    /*
    public synchronized void putHostCount(DSCrawlerPageTxt extractPageInfo){
        return;
        
        synchronized(mapHostCount){
            String dns = extractPageInfo.urlCrawler.dns;        
            if(mapHostCount.containsKey(dns)){
                DSCrawlerUrl down = mapHostCount.get(dns);
                down.count_down = down.count_down + 1;
                if(extractPageInfo.count>0)
                    down.count_item += 1;
                else
                    down.count_item += 0;
                mapHostCount.put(dns, down);
            }else{
                DSCrawlerUrl down = new DSCrawlerUrl();
                down.path = dns;
                down.count_down = 1;
                if(extractPageInfo.count>0)
                    down.count_item = 1;
                else
                    down.count_item = 0;
                mapHostCount.put(dns, down);
            }  
        }
    }
    */ 

    /*
    public synchronized DSCrawlerUrl getHostCount(DSCrawlerUrl url){
        String dns = url.dns;
//        synchronized(mapHostCount)
        {
            if(mapHostCount.containsKey(dns)){
                return mapHostCount.get(dns);
            }else{
                return null;
            }
        }
    }    
    */
    
    /*
    public synchronized void putUrlList(Map<String,DSCrawlerUrl> newList,int deepth){
        try{
//            synchronized(listJobUrls){
                if (deepth <= params.crawlerDeepth){
                    Set set = newList.entrySet();
                    Iterator it = set.iterator();
                    while(it.hasNext()){
                        Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
                        listJobUrls.get(deepth).add(body.getValue());
                    }
                }
//            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
    */ 
	
    public synchronized void setStatus(int val){
        jobState = val;
    }
    
    public synchronized int getStatus(){
        return jobState ;
    }
    /*
    public synchronized int getFilterDNS(){
        return filterDNS ;
    }    
    
    public synchronized void setFilterDNS(){
        filterDNS++ ;
    }
    */ 

    /*
    public synchronized void setDiffCount(){
        diffCount++ ;
    }     
    
    public synchronized int getDiffCount(){
        return diffCount ;
    }    
    */
    /*
    public synchronized int getMapDownSize(){
        return mapDownload.size();
    } 
    
    public synchronized void setMapDownload(DSCrawlerUrl urlInfo){
        if (mapDownload.containsKey(urlInfo.url)){
            mapDownload.get(urlInfo.url).count_down = mapDownload.get(urlInfo.url).count_down + 1;
            mapDownload.put(urlInfo.url,mapDownload.get(urlInfo.url));
            //listJobUrls.get(i).remove(0);
        }
    } 
    */
    
    
    public void run(){
        Init();
        if(pool == null){
            pool = new ThreadPoolExecutor(params.task, params.task + queueDeep, 30, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(queueDeep),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        }
        
        try{
            int count = 20;
            Thread.sleep(1000);
            while(true){
                if (getStatus() == 0)
                    break;
                DSCrawlerUrl info = getCrawlerUrl();
                if(info == null){
                    System.out.println("url is null  " + getQueueSize(pool.getQueue()));
                    dispInfo();
                    Thread.sleep(1000);
                    int queue = getQueueSize(pool.getQueue());
                    if (queue != 0){
                        continue;
                    }else{
                        if(queue==0){
                            if (count == 0){ 
                                setStatus(1);break;
                            }else{
//                                count --;
                                Thread.sleep(10000); 
                                continue;}
                        }
                    }
                }else{
//                    Thread.sleep(100);
                    /*
                    DSCrawlerUrl dnsUrl = getHostCount(info);
                    if(dnsUrl !=null && dnsUrl.count_down>=20 && dnsUrl.count_item == 0){
                        setFilterDNS();
//                        Thread.sleep(1000);
                        continue;
                    }*/
                    
                    CrawlerTask task = new CrawlerTask(this,info,params);
                    addNewTask(task);
                    count = 30;
                  Thread.sleep(10);
                }
                System.gc();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public void addNewTask(CrawlerTask task){
        try{
            while (getQueueSize(pool.getQueue()) >= queueDeep){ // 
                System.out.println("线程池已满，延时10秒再试！！！" + getQueueSize(pool.getQueue()));
                Thread.sleep(10000);
            }
            if (jobState == 1){
                pool.execute(task);
                System.out.println("pool size :"  + getQueueSize(pool.getQueue()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /*
    private synchronized boolean setMapTasks(DSCrawlerUrl urlInfo){
        int dnsCount = 1;
        boolean hasUrl = false;
        if(urlInfo.url.indexOf("baidu.com")==-1){
            if(!mapTasks.containsKey(urlInfo.dns)){
                dnsCount = 1;
                hasUrl = true;
            }else{
                if(mapTasks.get(urlInfo.dns)<dnsMax){
                    //Thread.sleep(100);
                    dnsCount =  mapTasks.get(urlInfo.dns) +1;
                    hasUrl = true;
                }
            }
        }else{
            if(mapTasks.size()<dnsMax/2){
                hasUrl = true;
            }            
        }
        if(hasUrl)
            mapTasks.put(urlInfo.dns, dnsCount);
        return hasUrl;
    }
    */
    
    /*
    private synchronized List<DSCrawlerUrl> getListUrl(int i){
        List<DSCrawlerUrl> listUrls = new ArrayList<DSCrawlerUrl>();
        listUrls = listJobUrls.get(i);
        return listUrls;
    }
    
    private synchronized int getListJobUrlSize(){
        return listJobUrls.size();
    }    
    */
    
    private synchronized int getMaxDeepth(){
        return params.crawlerDeepth;
    }
    
    public synchronized void setCrawlerUrl(Map<String,DSCrawlerUrl> mapUrl,DSCrawlerUrl pageUrl,int itemCount){
        synchronized(this){
        try{
            if (pageUrl.crawlerDeepth <= getMaxDeepth()){
                Set set = mapUrl.entrySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
                    listJobUrls.get(pageUrl.crawlerDeepth).add(body.getValue());
                }
            }
            
//            if(mapTasks.containsKey(pageUrl.dns)){
//                mapTasks.remove(pageUrl.dns);
//            }
            /*
            if(itemCount>0){
                mapHostCount.put(pageUrl.dns, pageUrl);
            }
            */ 
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        }
    }
    
    public synchronized DSCrawlerUrl getCrawlerUrl() throws InterruptedException{
        synchronized(this){
        int size = listJobUrls.size();
        DSCrawlerUrl urlInfo = new DSCrawlerUrl();
        boolean hasUrl = false;
        int dnsCount = 0;
        for (int i=size-1;i>=0;i--){
            int j=0;
            while(listJobUrls.get(i).size()>0 && j<listJobUrls.get(i).size()){
                urlInfo = listJobUrls.get(i).get(j);
                if (mapDownload.containsKey(urlInfo.url)){
                    mapDownload.get(urlInfo.url).count_down = mapDownload.get(urlInfo.url).count_down + 1;
                    mapDownload.put(urlInfo.url,mapDownload.get(urlInfo.url));
                    listJobUrls.get(i).remove(0);
                    
                    continue;
                }else{
                    hasUrl = true;
                    break;
                }
            }
            if(hasUrl)
                break;
        }
        int count = 1;
        if(mapDownload.size()>1000){
            while(mapDownload.size()>600){
                Set<?> set = mapDownload.entrySet();
                Iterator<?> it = set.iterator();
                while(it.hasNext()){
                    Entry<String,DSCrawlerUrl> vals= (Entry<String,DSCrawlerUrl>)it.next();
                    int index = vals.getValue().count_down;
                    if (index<=count){
                        it.remove();
                        diffCount++ ;
                    }
                }
            }
        }
        if(hasUrl){
            mapDownload.put(urlInfo.url, urlInfo);
            dispInfo();
            return urlInfo;
        }else{
            return null;    
        }
        }
    }
	 
    public synchronized int getQueueSize(Queue queue){
        return queue.size();// pool.getPoolSize();
    }
    
    public synchronized String dispInfo(){
        String msg="";
//        synchronized(listJobUrls)
        {
            for (int i=0;i<listJobUrls.size();i++){
                msg = msg + i + "_" + listJobUrls.get(i).size()+"; " ;
            }
        }
//        synchronized(mapDownload)
        {
            int sum = mapDownload.size();
            int sum0 = sum + diffCount;
            if (pool != null)
                msg = "完成总数:" + sum0 + "_" + sum + "_" + diffCount + "; 待完成的任务" + msg  + " 无效 ";// + filterDNS;
        }
        if(_statusLabel != null)
            _statusLabel.setText(msg);
        else
            System.out.println(msg);
        return msg;
    }
    
    private void saveRs(DSCrawlerPageTxt pageContent){        
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
            if (vals.length()>10 && !params.outPath.equals("")){
                content = "url:{" + pageContent.urlCrawler.title + ";" + pageContent.urlCrawler.dns + ";" + pageContent.urlCrawler.url + "}\r\n";
                content = content + "items:{" + vals + "}\r\n";
                content = content +  "content:{" + pageContent.content + "}\r\n";
                FileIO.writeAsTxts(params.outPath, name ,content);
            }
        }
    }    
    
}
