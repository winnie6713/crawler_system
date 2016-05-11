/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler;

import java.util.ArrayList;
import java.util.List;

import wuit.common.crawler.composite.DSExtractor;

/**
 *
 * @author lxl
 */
public class CrawlerMain {
    private List<DSExtractor> lstParams = new ArrayList<DSExtractor>();		//提取信息的项目集合
    private List<CrawlerJob> listJobs = new ArrayList<CrawlerJob>();            //
    
   // private String workFile = "D:\\Projects\\CrawlerSystem\\config\\jobs\\WorkConfig.xml";
    
    public void Init(){
//        lstParams = CrawlerParams.readParams(workFile);
        for(int i=0;i<lstParams.size();i++){
            System.out.println(lstParams.get(i).crawlerDeepth);
        }
    }
    
    public static void main(String[] args){
        CrawlerMain mainApp = new CrawlerMain();
        mainApp.Init();
    }
}
