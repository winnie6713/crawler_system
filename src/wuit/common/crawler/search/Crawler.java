/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.crawler.search;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import wuit.crawler.DSCrawlerUrl;
//import static wuit.common.crawler.search.Crawler360.getEncoding;
//import static wuit.common.crawler.search.Crawler360.getHtmlEncode;

/**
 *
 * @author lxl
 */
public class Crawler {
    public static String doGetHttp(String url){
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 12000);
        HttpConnectionParams.setSoTimeout(params, 9000); 
        HttpClient httpclient = new DefaultHttpClient(params);
        String rs = "";
        try {
//            System.out.println(url);
            HttpGet httpget = new HttpGet(url);
            
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
  
  
   public static String getEncoding(String str){
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
   
    public static String getHtmlEncode(final String s){
        String val = match(s, "(?<=\\<meta http-equiv='content-type' content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html; charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta http-equiv=\"Content-Type\" content=\"text/html;charset=)[^>]+?(?=\")");
        if(val.isEmpty())
            val = match(s, "(?<=\\<meta charset=\")[^>]+?(?=\")");
        return val;
    } 

    public static String match(String content,String filter){
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
    
   public static String clearHtml(String html){       
        String content = html;
        
        content = content.replaceAll("<script[^>]*>[\\d\\D]*?</script>","");
        content = content.replaceAll("<style[^>]*>[\\d\\D]*?</style>","");
  //      content = content.replaceAll("<em>", "");
        content = content.replaceAll("<([^>]*)>", "|");
        
        content = content.replaceAll("\\s*|t|r|n","");

        content = content.replaceAll("[&gt;|&nbsp;|\\(|\\)|\\[|\\]|\\-|“|”|:|_|#|：|、|,| |。|，|【|】|（|）]", ",");
        content = content.replaceAll(",{2,}", ",");
  //      System.out.println(content);
        return content;
    }    
   
    public static void getUrls(String html,DSCrawlerUrl currPageUrl,Map<String,DSCrawlerUrl> mapPageUrls){
        Parser m_parser ;
        if(html == null || html.equals(""))
            return ;
        try{
            m_parser = Parser.createParser(html,"utf-8");
            NodeFilter filter = new TagNameFilter("a");
            NodeList nodelist = m_parser.parse(filter);
            NodeIterator it = nodelist.elements();
            while(it.hasMoreNodes()){
                LinkTag node = (LinkTag)it.nextNode();
                String url = node.getAttribute("href");
                if(url!=null && url.indexOf("#")>=0 || url.toLowerCase().indexOf("script")>=0)
                    continue;
                if((url == null || url.equals("") )){
                    continue;
                }
                //System.out.println(url);
                //解析url
                DSCrawlerUrl _pageUrl = wuit.crawler.searcher.Crawler.parsePageUrl(url,currPageUrl.url);
                if(_pageUrl == null)
                    continue;
                _pageUrl.crawlerDeepth = currPageUrl.crawlerDeepth + 1;
                mapPageUrls.put(url, _pageUrl);
            }
            
        }catch(Exception e){
            System.out.println(" composite Convert extractorUrl :" + e.getMessage());
        }
    }  
}
