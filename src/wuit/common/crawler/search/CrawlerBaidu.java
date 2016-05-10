/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.search;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import wuit.common.crawler.CrawlerUtiles;


/**
 *
 * @author lxl
 */
public class CrawlerBaidu { 
    /*
    public List<BaiDuData> SearchPage(String keys){
        System.out.println(keys);
        
        List<BaiDuData> lst = new ArrayList<BaiDuData>();
//        for(int i=0;i<keys.size();i++)
        {
           String keyWords = keys;
           String valKey ="";
           try {
               valKey = java.net.URLEncoder.encode(keyWords,"utf-8");
           } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
           }
           String baseUrl = "http://www.baidu.com/s?wd=" + valKey + "&cl=3&pn=10&tn=site888_pg&rn=100"; 
//           baseUrl = "http://so.360.cn/s?q=" + keyWords;
//           System.out.println(baseUrl);
           String html = Crawler.doGetHttp(baseUrl);
            try {
               try {
                   Parser parser = new Parser(html);                   
                   NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
                   OrFilter lastFilter = new OrFilter();
                   lastFilter.setPredicates(new NodeFilter[]{ tableFilter});
                   NodeList nodeList = null;
                   nodeList =parser.parse(lastFilter);
                   for(int j = 0; j<= nodeList.size();j++){
                       TableTag tag = (TableTag) nodeList.elementAt(j);
                       if(tag == null || tag.getRows() == null)
                           continue;
                       TableRow[] rows = tag.getRows();
                       for (int k = 0; k < rows.length; k++) {
                           TableRow tr = (TableRow) rows[k];
                           //System.out.println(tr.getAttribute("id"));
                           TableColumn[] td = tr.getColumns();
                           for(int n = 0;n<td.length;n++){
                               if(td[n] == null)
                                   continue;
                               String val = td[n].toPlainTextString().replaceAll("\r|\n|\t", "");
                               String valHtml = td[n].toHtml().replaceAll("\r|\n|\t", "");
                               if(val.equals(""))
                                   continue;                               
                               Parser parserA = new Parser(valHtml);
                               NodeFilter filterA = new TagNameFilter("A");                                
                               NodeList nodesA = parserA.extractAllNodesThatMatch(filterA);
                               BaiDuData rs = new BaiDuData();
                               for(int m=0;m<nodesA.size();m++){
                                   LinkTag link = (LinkTag)nodesA.elementAt(m);
                                   String strA = nodesA.elementAt(m).toPlainTextString();
                                   if(strA.indexOf("百度快照")>=0 || strA.equals("") || link.getLink().indexOf("/s?rn")>=0)
                                       continue; 
                                   rs.title = link.getLinkText();
                                   rs.url = link.getLink();
//                                   System.out.println("url title : " + link.getLinkText());
//                                   System.out.println("url ：" + link.getLink());

                               }
                               Parser parserB = new Parser(valHtml);
                               NodeFilter filterDiv = new TagNameFilter("Div");
                               NodeFilter filterClass = new  HasAttributeFilter("class","c-abstract");
                               AndFilter filter = new AndFilter(filterDiv, filterClass);   
                               NodeList nodesDiv = parserB.extractAllNodesThatMatch(filter);
                               for(int ma=0;ma<nodesDiv.size();ma++){
                                        rs.Abstract = nodesDiv.elementAt(ma).toPlainTextString();
                               }
                               lst.add(rs);                               
                           }
                       }
                   }
               } catch (Exception e) {
                   Logger.getLogger(CrawlerBaidu.class.getName()).log(Level.SEVERE, null, e);
               }
            } catch (Exception e) {
                Logger.getLogger(CrawlerBaidu.class.getName()).log(Level.SEVERE, null, e);
            }
           
        }
        return lst;
    }    
    */
    
    public static String getWebPageHtml(String keyWords){
        String valKey ="";
        try {
            valKey = java.net.URLEncoder.encode(keyWords,"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        String baseUrl = "http://www.baidu.com/s?wd=" + valKey + "&cl=3&pn=10&tn=site888_pg&rn=50"; 
        return Crawler.doGetHttp(baseUrl);
    }
    
    public static String getWebPageText(String keyWords){
        return Crawler.clearHtml(getWebPageHtml(keyWords));
    }
    
    /*
    public List<BaiDuData> SearchPage(List<String> keys){
        List<BaiDuData> lst = new ArrayList<BaiDuData>();
        for(int i=0;i<keys.size();i++){
           String keyWords = keys.get(i);
           String valKey ="";
           try {
               valKey = java.net.URLEncoder.encode(keyWords,"utf-8");
           } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
           }
           String baseUrl = "http://www.baidu.com/s?wd=" + valKey + "&cl=3&pn=10&tn=site888_pg&rn=100"; 
           
           String html = doGetHttp(baseUrl);
            try {
               try {
                   Parser parser = new Parser(html);                   
                   NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
                   OrFilter lastFilter = new OrFilter();
                   lastFilter.setPredicates(new NodeFilter[]{ tableFilter});
                   NodeList nodeList = null;
                   nodeList = parser.parse(lastFilter);
                   for(int j = 0; j<= nodeList.size();j++){
                       TableTag tag = (TableTag) nodeList.elementAt(j);
                       if(tag == null || tag.getRows() == null)
                           continue;
                       TableRow[] rows = tag.getRows();
                       for (int k = 0; k < rows.length; k++) {
                           TableRow tr = (TableRow) rows[k];
                           //System.out.println(tr.getAttribute("id"));
                           TableColumn[] td = tr.getColumns();
                           for(int n = 0;n<td.length;n++){
                               if(td[n] == null)
                                   continue;
                               String val = td[n].toPlainTextString().replaceAll("\r|\n|\t", "");
                               String valHtml = td[n].toHtml().replaceAll("\r|\n|\t", "");
                               if(val.equals(""))
                                   continue;                               
                               Parser parserA = new Parser(valHtml);
                               NodeFilter filterA = new TagNameFilter("A");                                
                               NodeList nodesA = parserA.extractAllNodesThatMatch(filterA);
                               BaiDuData rs = new BaiDuData();
                               for(int m=0;m<nodesA.size();m++){
                                   LinkTag link = (LinkTag)nodesA.elementAt(m);
                                   String strA = nodesA.elementAt(m).toPlainTextString();
                                   if(strA.indexOf("百度快照")>=0 || strA.equals("") || link.getLink().indexOf("/s?rn")>=0)
                                       continue; 
                                   rs.title = link.getLinkText();
                                   rs.url = link.getLink();
//                                   System.out.println("url title : " + link.getLinkText());
//                                   System.out.println("url ：" + link.getLink());

                               }
                               Parser parserB = new Parser(valHtml);
                               NodeFilter filterDiv = new TagNameFilter("Div");
                               NodeFilter filterClass = new  HasAttributeFilter("class","c-abstract");
                               AndFilter filter = new AndFilter(filterDiv, filterClass);   
                               NodeList nodesDiv = parserB.extractAllNodesThatMatch(filter);
                               for(int ma=0;ma<nodesDiv.size();ma++){
                                        rs.Abstract = nodesDiv.elementAt(ma).toPlainTextString();
                               }
                               lst.add(rs);                               
                           }
                       }
                   }
               } catch (Exception e) {
                   Logger.getLogger(CrawlerBaidu.class.getName()).log(Level.SEVERE, null, e);
               }
            } catch (Exception e) {
                Logger.getLogger(CrawlerBaidu.class.getName()).log(Level.SEVERE, null, e);
            }
           
        }
        return lst;
    }
    */
    
    /*
  public String doGetHttp(String url){
       HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 12000);
        HttpConnectionParams.setSoTimeout(params, 9000); 
        HttpClient httpclient = new DefaultHttpClient(params);
        String rs = "";
        try {
            HttpGet httpget = new HttpGet(url);
//            System.out.println(url);
            HttpContext httpContext = new BasicHttpContext();            
//            httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
            
            HttpResponse response = httpclient.execute(httpget,httpContext);
            HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            url = targetHost.toString() +  realRequest.getURI();
            int resStatu = response.getStatusLine().getStatusCode();//返回码 
            if(resStatu==200){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity,"iso-8859-1");
                    String in_code = getEncoding(rs);
                    String encode = getHtmlEncode(rs) ;
                    if(encode.isEmpty()){
                        httpclient.getConnectionManager().shutdown();
                        return "";
                    }else{
                        if(!in_code.toLowerCase().equals("utf-8") && !in_code.toLowerCase().equals(encode.toLowerCase())){
                            if(!in_code.toLowerCase().equals("iso-8859-1"))
                                rs = new String(rs.getBytes("iso-8859-1"),in_code);
                            if(!encode.toLowerCase().equals(in_code.toLowerCase()))
                                rs = new String(rs.getBytes(in_code),encode);
                        }
                    }
                    try {
                    } catch (RuntimeException ex) {
                        httpget.abort();
                        throw ex;
                    } finally {
                        // Closing the input stream will trigger connection release
    //                    try { instream.close(); } catch (Exception ignore) {}
                    }
                }
            }
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            return rs;
        }       
   } 
  */
  /*
   public String getEncoding(String str){
       String encode = "";
       try{
           encode="ISO-8859-1";
           if(str.equals(new String(str.getBytes(encode),encode))){
               String s1=encode;
               return s1;
           }           
           encode="UTF-8";
           if(str.equals(new String(str.getBytes(encode),encode))){
               String s2 = encode;
               return s2;
           }
           encode="GB2312";
           if(str.equals(new String(str.getBytes(encode),encode))){
               String s=encode;
               return s;
           }

           encode="GBK";
           if(str.equals(new String(str.getBytes(encode),encode))){
               String s3=encode;
               return s3;
           }
           encode="BIG5";
           if(str.equals(new String(str.getBytes(encode),encode))){
               String s3=encode;
               return s3;
           }           
       }catch(Exception e){
           e.printStackTrace();
       }
       return"";
   }    
    public String getHtmlEncode(final String s){
        String val = match(s, "(?<=\\<meta http-equiv='content-type' content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html; charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta charset=\")[^>]+?(?=\")");
        return val;
    } 
  */

    /*
    public String match(String content,String filter){
        String val ="";
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                val =m.group();
                break;
            }
        }catch(Exception e){
            System.out.println("Composite Parse match " + e.getMessage());
        }
        return val;
    }   
    */
    
    public static void main(String[] args){
        CrawlerBaidu baiDu = new CrawlerBaidu();
        List<String> keys = new ArrayList<String>();
        keys.add("天山路900号汇金百货");
//        baiDu.SearchPage(keys);
        
    }
}
