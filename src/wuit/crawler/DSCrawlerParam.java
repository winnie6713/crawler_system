/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.crawler;

import java.util.Map;
import wuit.common.crawler.db.KeyValue;

/**
 *
 * @author lxl
 */
public class DSCrawlerParam {
    public String keyUrl = "";
    public int mode = -1;
    public int tasks = 0;
    public int deepth = 0;
    public String dns = "";
    public Map<String,KeyValue> mapFilterUrl;    
}
