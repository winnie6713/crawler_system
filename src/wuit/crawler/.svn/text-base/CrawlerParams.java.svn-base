/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler;

import java.util.List;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.doc.xml.ParseWorkConfigXml;

/**
 *
 * @author lxl
 */
public class CrawlerParams {    
    public static List<DSExtractor> readParams(String file){
        List<DSExtractor> lstParams;
        ParseWorkConfigXml config ;
        config = new ParseWorkConfigXml();
        config.parseWorks(file);
        lstParams = config.getWorksParams();  
        return lstParams;
    }
}
