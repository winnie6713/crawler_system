/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.extract;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.CrawlerHtmlData;
import wuit.crawler.main.MainServer;

/**
 *
 * @author lxl
 */
public class ExtractItemtTask implements Runnable{
    Parser parser = new Parser();    
    
    CrawlerHtmlData data;
    List<KeyValue> defaultRules;
    
    public ExtractItemtTask(CrawlerHtmlData data){
        this.data = data;
 //       this.defaultRules = defaultRules;
    }
    
    public void run(){
        try {
            if(data == null)
                return;
            ExtractContent.extractPageBaseInfo(data.html);                                         //提取网页中的基本信息
            String conetnt = ExtractContent.clearHtml(data.html);           
            List<DSComposite> list = ExtractContent.extractContent(conetnt,data.pageInfo);       //提取网页中包含的信息项

            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).local.address);
            }
            
            
            //统计提取到信息的网页数
            if(list.size()>0){
//                MainServer.state.pageContaintItems = MainServer.state.pageContaintItems + 1;
                
                MainServer.DBCrawler.listDSComposites.addAll(list);
                parseAddress(list);
            }            
            Thread.sleep(10);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ExtractItemtTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public void parseAddress(List<DSComposite> list){
       for(int i=0;i<list.size();i++){
           parser.parseAddress(list.get(i));
       }
   }     
    
   

}