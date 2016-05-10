/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.main;

/**
 *
 * @author lxl
 */
public class CrawlerState {
    public int crawlers = 0;                //爬虫数
    public int linkDownloaded = 0;          //已爬链接数
    public int linkPageSum = 0;             //从网页中提取的链接总数
    public int linkFilters= 0;              //按搜索链接模式，被过滤链接总数数
    public int linkRepeat = 0;              //删除的重复链接总数数
    
    public int itemSum = 0;                 //提取项总数
    
    public int pageSaveCount=0;             //
    
    public int pageMacthNum = 0;            //网页特征库匹配成功数
    public int pageNoMacthNum = 0;          //网页特征库匹配不成功数
    public int pageContaintItems = 0;       //网页可提取服务项
    public int pageExtractNum = 0;          //提取内容，处理网页总数
    
    public int itemCity = 0;                //匹配省、市、县数
    public int itemAmbiguity = 0;           //地址歧义数
    public int itemCityNull = 0;            //无法匹配省、市、县数
    
    public synchronized void setLinkRepeat(int val){
        linkRepeat = linkRepeat + val;
    }

    public synchronized int getLinkRepeat(){
        return linkRepeat ;
    }    
    
    /*
    public synchronized void setLinkDowns(int val){
        linkDown = val;
    }
    */
    
    public synchronized int getLinkDowns(){
        return linkDownloaded ;
    }

    public synchronized void setLinkPageSum(int val){
        linkPageSum = linkPageSum + val;
    }
    
    public synchronized int getLinkPageSum(){
        return linkPageSum ;
    }    
    
    public synchronized void setLinkFilters(int val){
        linkFilters = linkFilters + val;
    }
    
    public synchronized int getLinkFilters(){
        return linkFilters ;
    }    
    
}


