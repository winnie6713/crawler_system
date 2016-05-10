/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.WebSit;

//import org.apache.commons;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.db.KeyValue;

/**
 *
 * @author lxl
 */
public class CrawlerAPIBaiDu {
    private static Log log = LogFactory.getLog(CrawlerAPIBaiDu.class);
    
    
   
	/**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url
     *            请求的URL地址
     * @param queryString
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset,
            boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString))
                // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (URIException e) {
            log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerAPIBaiDu.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    /*public static String doPost(String url, Map<String, String> params,
            String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        // 设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }*/

    public static void matchValues(String content,String filter,List<KeyValue> list){
        if (list == null)
            list = new ArrayList<KeyValue>();
        try{		
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if(m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.value = m.group();
                value.start = m.start();
                value.end = m.end();
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("Crawler Utitles  matchValues :" + e.getMessage());
        }
    }
    
    
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {  
        
        String url_m = "www.aibang.com/[^/].+?/";
        String url = "http://www.aibang.com/beijing/xiangcai/";
        url = "www.aibang.com/shanghai/|www.aibang.com/beijing/|www.aibang.com/nanjing/|www.aibang.com/huhehaote/|http://www.aibang.com/haerbin/";
        List<KeyValue> list2 = new ArrayList<KeyValue>();

         CrawlerAPIBaiDu.matchValues(url, url_m, list2);        
        
        
        
        
        String city ="上海";
        String key = "1号航站楼一层";

        String results = doGet(" http://api.map.baidu.com/place/search?&query="
                + java.net.URLEncoder.encode(key, "utf-8") + "&region="
                + java.net.URLEncoder.encode(city, "utf-8")
                + "&output=json&key=bcb9b248df88de9cb49ff5ceab7c784e", null,
                "utf-8", true);
        
         List<KeyValue> list = new ArrayList<KeyValue>();
         List<DSComposite> _list = new ArrayList<DSComposite>();
         CrawlerAPIBaiDu.matchValues(results, "(?<=name\":\")[^\"].+?(?=\")", list);
         for(int i=0;i<list.size();i++){
//             System.out.println(list.get(i).value);
             DSComposite info = new DSComposite();
             info.name = list.get(i).value;
             _list.add(info);
         }
         list.clear();
         CrawlerAPIBaiDu.matchValues(results, "(?<=address\":\")[^}].+?(?=\")", list);
         for(int i=0;i<list.size();i++){
//             System.out.println(list.get(i).value);
             _list.get(i).local.address = list.get(i).value;
         }         
         list.clear();
         CrawlerAPIBaiDu.matchValues(results, "(?<=\"lat\":)\\d{1,}\\.\\d{1,}", list);
         for(int i=0;i<list.size();i++){
             _list.get(i).lat = list.get(i).value;
         }
         list.clear();
         CrawlerAPIBaiDu.matchValues(results, "(?<=\"lng\":)\\d{1,}\\.\\d{1,}", list);
         for(int i=0;i<list.size();i++){
//             System.out.println(list.get(i).value);
             _list.get(i).lng = list.get(i).value;
         }
         list.clear();
         CrawlerAPIBaiDu.matchValues(results, "(?<=telephone\":\")[^}].+?(?=\",)", list);
         for(int i=0;i<list.size();i++){
             _list.get(i).phone = list.get(i).value;
         }  

         list.clear();
         CrawlerAPIBaiDu.matchValues(results, "(?<=\"detail_url\":\")[^\"].+?(?=\")", list);
         for(int i=0;i<list.size();i++){
             _list.get(i).collection.url = list.get(i).value;
         } 
         
         
         for(int i=0;i<_list.size();i++){
             System.out.println(_list.get(i).name + ":" + _list.get(i).local.address + 
                     ":" + _list.get(i).phone+ ":" + _list.get(i).lat + ":" + _list.get(i).lng 
                     + ":" + _list.get(i).collection.url);
         }  
         
         System.out.println(results);
        
        /*
    	CrawlerAPIBaiDu httpTookit = new CrawlerAPIBaiDu();
    	System.out.println("输入城市");
    	Scanner sc = new Scanner(System.in);
//    	String city = sc.next();
    	System.out.println("输入地点");
//    	String key = sc.next();
    	String result = httpTookit.query(city,key);
    	System.out.println(result);
        */

    }
    /*
     * 返回检索结果
     * @param city
     *      城市
     * @param key
     *      关键字
     * @return 返回结果
     * */
    public String query(String city,String key) throws UnsupportedEncodingException{
    	
    	 String results = doGet(" http://api.map.baidu.com/place/search?&query="
                 + java.net.URLEncoder.encode(key, "utf-8") + "&region="
                 + java.net.URLEncoder.encode(city, "utf-8")
                 + "&output=json&key=bcb9b248df88de9cb49ff5ceab7c784e", null,
                 "utf-8", true);

    	return results;
    }
    
    
}
