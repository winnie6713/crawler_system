/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler.database;

/**
 *
 * @author lxl
 */
public class CrawlerStatInfo {
    public int featureYes = 0;
    public int featureNo = 0;
    
    public int parseInv = 0; 
    public int parseDirect = 0;
    public int parseIndirect = 0;
    public int parseAmb = 0;
    
    public int rsInvalidate = 0;    
    public int rsFull = 0;
    public int rsPart = 0;
    
    public int rsConflict = 0;
    
    public int getSumRs(){
        return rsInvalidate + rsPart + rsFull;
    }
    
    public int getSumParse(){
        return parseInv + parseDirect + parseIndirect + parseAmb ;
    }
    
    public int getSumFeature(){
        return featureYes + featureNo;
    }
}
