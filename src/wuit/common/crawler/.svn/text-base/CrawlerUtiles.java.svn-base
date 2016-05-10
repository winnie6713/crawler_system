/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.json.JSONArray;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import wuit.common.crawler.composite.CompositeConvert;
import wuit.common.crawler.composite.CompositeParse;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.DSCrawlerPageInfo;
import wuit.common.crawler.composite.DSCrawlerPageTxt;
import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.crawler.composite.DSExtractor;
import wuit.common.crawler.composite.ExtractFieldRules;
import wuit.common.crawler.db.KeyValue;
import wuit.common.doc.xml.ParseWorkConfigXml;

/**
 *
 * @author lxl
 */
public class CrawlerUtiles {
    CompositeConvert convert = new CompositeConvert();
    CompositeParse parse = new CompositeParse();
    /**
     * 
     * @param url:
     * @param clearList：
     * @param extractFields：
     * @param lstFilterUrl：
     * @param mapUrl：搜索网页中所包含的链接
     * @param cmpInfos： 从网页中按照提取字段的规则提取的项目记录
     */
   public void extractHtmlPage(DSExtractor _jobParams,DSCrawlerUrl pageUrl,Map<String,DSCrawlerUrl> mapUrl,DSCrawlerPageTxt extractPageInfo){
       int mode = _jobParams.crawlerMode;
//       String _keyWords = _jobParams.crawlerKeyWords;
       List<KeyValue> clearList = _jobParams.listClearHtml;
       List<KeyValue> extractFields = _jobParams.listExtractFields;
       Map<String,KeyValue> mapFilterUrl = _jobParams.mapFilterUrl;
       Map<String,KeyValue> mapFilterFields = _jobParams.mapFilterFields;
       
       String valHtml = doGetHttp(pageUrl);
//       System.out.println(valHtml);
//       getHtmlLink(valHtml,pageUrl,mapFilterUrl,mapUrl);
      
       extractHtmlLink(valHtml,pageUrl,mapUrl);
        if(mode == 0){
            pageUrl.dns = match(pageUrl.url,"(?<=http://)[^/]+(?=/)");
            String [] _dns = pageUrl.dns.split("\\.");
            String dns = "";
            for(int i=1;i<_dns.length;i++){
                dns = dns +"." +_dns[i];
            }
            filterLinksMode0(dns,mapUrl);
        }
       if(mode == 1){
            filterLinksMode1(mapFilterUrl,mapUrl);
        }
       extractPageInfo.urlCrawler.dns = pageUrl.dns;
       extractPageInfo.urlCrawler.url = pageUrl.url;
       
//        if(mode == 100 && mapFilterFields.size()>0){
        if(mapFilterFields.size()>0){    
            boolean hasKey = false;
            Set set = mapFilterFields.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,String> body = (Entry<String,String>)it.next();
                if(valHtml.indexOf(body.getValue())>=0)
                    hasKey = true;
            }
            if(!hasKey)
                return;
        }       
       
       if(valHtml.isEmpty()|| valHtml.equals(""))
           return;
       if(match(valHtml,"[\\u4E00-\\u9FA5]").equals(""))
           return;
       String title = getHtmlTitle(valHtml);       
//       System.out.println(CompositeConvert.getHtmlEncode(valHtml));
       String description  = getHtmlDesciption(valHtml);
       String keyWords = getHtmlKeywords(valHtml);
       
       String valText = clearHtml(valHtml, clearList);

       DSCrawlerPageInfo pageInfo = new DSCrawlerPageInfo();
       pageInfo.url = pageUrl.url;
       pageInfo.title = title;
       pageInfo.description = description;
       pageInfo.keywords = keyWords;
       pageInfo.dns = pageUrl.dns;
       pageInfo.IP = pageUrl.IP;
       extractRecordsFromContent(valText,pageInfo, extractFields,extractPageInfo.items);
       
       extractPageInfo.urlCrawler = pageInfo;
       extractPageInfo.content = valText;
       
       List<DSComposite> _items = new ArrayList<DSComposite>();
       extractPageInfo.count = extractPageInfo.items.size();
       for(int i=0;i<extractPageInfo.items.size();i++){
           parse.extractRecord(extractPageInfo.items.get(i));
           extractPageInfo.items.get(i).collection.url = pageInfo.url;
           extractPageInfo.items.get(i).collection.description = pageInfo.description;
           extractPageInfo.items.get(i).collection.IP = pageInfo.IP;
           extractPageInfo.items.get(i).collection.dns = pageInfo.dns;
           extractPageInfo.items.get(i).collection.keywords = pageInfo.keywords;
           extractPageInfo.items.get(i).collection.title = pageInfo.title;
           
           if(extractPageInfo.items.get(i) == null || extractPageInfo.items.get(i).local.address.isEmpty() )
               continue;
           
           if(mode == 1){
               Set set = mapFilterFields.entrySet();
               Iterator it = set.iterator();
               boolean hasKey = false;
               while(it.hasNext()){
                   Entry<String,String> body = (Entry<String,String>)it.next();
                   if(body.getKey().equals("address")){
                        if(extractPageInfo.items.get(i).local.address.indexOf(body.getValue())>=0){
                            hasKey = true;
                        }
                   }
                   if(body.getKey().equals("name")){
                        if(extractPageInfo.items.get(i).name.indexOf(body.getValue())>=0){
                            hasKey = true;
                        }
                   }
               }
               if(hasKey)
                   _items.add(extractPageInfo.items.get(i));
           }
       }
       /*
       if(mode == 1 && mapFilterFields.size()>0){
           extractPageInfo.items = _items;
           Set set = mapUrl.entrySet();
           Iterator it = set.iterator();
           while(it.hasNext()){
               Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
 //              body.getValue().crawlerDeepth = body.getValue().crawlerDeepth -1;
           }
       }
       */
//       if(pageUrl.crawlerDeepth<=_jobParams.crawlerDeepth)       
   }
   
   
   public void CrawlerByKeyWords(String keyWords,DSExtractor _jobParams,Map<String,DSCrawlerUrl> mapUrl,DSCrawlerPageTxt extractPageInfo){  
       int mode = _jobParams.crawlerMode;
//       String _keyWords = _jobParams.crawlerKeyWords;
       String val1 =""; 
       try {
           val1 = java.net.URLEncoder.encode(keyWords,"utf-8");
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       DSCrawlerUrl pageUrl = new DSCrawlerUrl();
       pageUrl.url ="http://www.baidu.com/s?wd=" + val1 + "&cl=3&pn=10&tn=site888_pg&rn=100";
       pageUrl.crawlerDeepth = 0;
       pageUrl.title = "start Baidu";
       Map<String,DSCrawlerUrl> _mapUrl = new HashMap<String,DSCrawlerUrl>();
       extractHtmlPage(_jobParams,pageUrl,_mapUrl,extractPageInfo);        
   }  
  
   public void CrawlerKeyWordsBaiDu(DSExtractor _jobParams,Map<String,DSCrawlerUrl> mapUrl,DSCrawlerPageTxt extractPageInfo){      
       int mode = _jobParams.crawlerMode;
       
       for(int i=0;i<_jobParams.crawlerKeyWords.size();i++){
           String keyWords = _jobParams.crawlerKeyWords.get(i);
           String valKey ="";
           try {
               valKey = java.net.URLEncoder.encode(keyWords,"utf-8");
           } catch (UnsupportedEncodingException ex) {
               Logger.getLogger(CrawlerUtiles.class.getName()).log(Level.SEVERE, null, ex);
           }
           String baseUrl = "http://www.baidu.com/s?wd=" + valKey + "&cl=3&pn=10&tn=site888_pg&rn=100";
            DSCrawlerUrl pageUrl = new DSCrawlerUrl();
            pageUrl.url = baseUrl;
            pageUrl.crawlerDeepth = 0;
            pageUrl.title = "start Baidu";
            Map<String,DSCrawlerUrl> _mapUrl = new HashMap<String,DSCrawlerUrl>();
            extractHtmlPage(_jobParams,pageUrl,_mapUrl,extractPageInfo);
            
            Set set = _mapUrl.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
                mapUrl.put(body.getKey(), body.getValue());
            }
            
            pageUrl = new DSCrawlerUrl();
            baseUrl = "http://so.360.cn/s?q=" + valKey;
            pageUrl.url = baseUrl;
            pageUrl.title = "start 360";
            pageUrl.crawlerDeepth = 0;
            _mapUrl = new HashMap<String,DSCrawlerUrl>();
            extractHtmlPage(_jobParams,pageUrl,_mapUrl,extractPageInfo);
            
            set = _mapUrl.entrySet();
            it = set.iterator();
            while(it.hasNext()){
                Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
                mapUrl.put(body.getKey(), body.getValue());
            }
       }
   }

   /*
   public String extractJQuery(DSCrawlerUrl pageUrl){
       List<KeyValue> params_method = new ArrayList<KeyValue>();
       List<KeyValue> params_rs = new ArrayList<KeyValue>();
       String rsVal = doGetHttp(pageUrl);

        return rsVal;
   }
   */
   
   public String doGetHttp(DSCrawlerUrl pageUrl){
       HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 12000);
        HttpConnectionParams.setSoTimeout(params, 9000);
        HttpClient httpclient = new DefaultHttpClient(params);
        String rs = "";
        try {
            HttpGet httpget = new HttpGet(pageUrl.url);
            System.out.println("executing request " + pageUrl.url);
            HttpContext httpContext = new BasicHttpContext();            
//            httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
            
            HttpResponse response = httpclient.execute(httpget,httpContext);
            HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            pageUrl.url = targetHost.toString() +  realRequest.getURI();
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
   
   
  /* 
  public String doGetHttp(String url){
       String rs ="";
       HttpClient client = new HttpClient();
       client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
       
       List<Header> headers = new ArrayList<Header>();
//       client.getHostConfiguration().setProxy("58.62.43.131",9999); proxycn2.huawei.com
//        client.getHostConfiguration().setProxy("localhost",8080);                
       

       String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 GTB6 (.NET CLR 3.5.30729)"; 
       USER_AGENT = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 5.1)";
       headers.add(new Header("User-Agent", USER_AGENT));
       client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);       
       
//       client.getParams().setAuthenticationPreemptive(true);
       
       //HttpMethod getMethod=new GetMethod(url);
       GetMethod getMethod = new GetMethod();
       client.getHttpConnectionManager().getParams().setSoTimeout(18000);
       getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
       
       System.out.println(url);
       
       URI strUri = null;
       try{
           strUri = new URI(url,true,"UTF-8");
           getMethod.setURI(strUri);
       }catch(URIException e){
           e.printStackTrace();
           try{
               strUri = new URI(url,false,"UTF-8");
               getMethod.setURI(strUri);
               
           }catch(URIException ee){
               ee.printStackTrace();
           }
       }
        
       try {
           int statusCode = client.executeMethod(getMethod);
           if (statusCode != HttpStatus.SC_OK) {
               System.err.println("Method failed: " + getMethod.getStatusLine());
               getMethod.releaseConnection();
               return rs;
           }
           String encode_in = getMethod.getResponseCharSet();
           InputStream is = getMethod.getResponseBodyAsStream();
           
           rs = convertStreamToString(is,encode_in);
           String pageEncode = getHtmlEncode(rs).replace("\"", "");
           if(pageEncode.isEmpty())
               pageEncode = getEncoding(rs);
           if(pageEncode.toLowerCase().equals("utf-8"))
               rs = new String (rs.getBytes(encode_in),pageEncode);
           else{
               rs = new String (rs.getBytes(encode_in),pageEncode);
//               rs = new String (rs.getBytes("UTF-8"),"UTF-8");
           }
           Header locationHeader = getMethod.getResponseHeader("location");
           if (locationHeader != null) {
               String redirectUrl = locationHeader.getValue();
               redirectUrl.toString();
           }
       }catch(ProtocolException pe){
           getMethod.releaseConnection();
       }catch(Exception e){
           e.printStackTrace();
           getMethod.releaseConnection();
       }finally{
           getMethod.releaseConnection();
       }
       getMethod.releaseConnection();
       return rs;
   }
  */ 
   private String convertStreamToString(InputStream is,String encode) {
       StringBuilder sb = new StringBuilder();
       String line = null;
       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(is,encode));
           

           while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               is.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return sb.toString();
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

    public String getHtmlKeywords(final String s){
        return match(s, "(?<=\\<meta name\\=\"keywords\" content\\=\")[^>]+?(?=>)");
    }

    public String getHtmlDesciption(final String s){
        return match(s, "(?<=\\<meta name\\=\"Description\" content\\=\")[^>]+?(?=>)");
        
    }

    public void extractHtmlLink(String html,DSCrawlerUrl pageUrl,Map<String,DSCrawlerUrl> mapUrl){
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
                DSCrawlerUrl _info = new DSCrawlerUrl();
                String url = node.getAttribute("href");
                
                if(url!=null && url.indexOf("#")>=0)
                    continue;
                if((url == null || url.equals("") || 
                        url.toLowerCase().indexOf("script")>=0)){
                    continue;
                }
                //System.out.println(url);
                //解析url
                _info = parsePageUrl(url,pageUrl.url);
                if(_info == null)
                    continue;                
                //
                if(node.toPlainTextString() == null || node.toPlainTextString().equals(""))
                    _info.title = node.getAttribute("title");
                else
                    _info.title = node.toPlainTextString().replaceAll("\\s+?", "");
                if(_info.title == null ||_info.title.equals(""))
                    _info.title = "YYYY";
                //
                _info.crawlerDeepth = pageUrl.crawlerDeepth + 1;
                if(!mapUrl.containsKey(_info.url)){
                    mapUrl.put(_info.url,_info);
                }
            }
        }catch(Exception e){
            System.out.println(" composite Convert extractorUrl :" + e.getMessage());
        }        
    }
    
    /*
    垂直搜索，即固定在指定的网站上搜索
    */
    public void filterLinksMode0(String webDNS,Map<String,DSCrawlerUrl> mapUrl){
        Map<String,DSCrawlerUrl> mapDelUrl = new HashMap<String,DSCrawlerUrl>(); 
        Set set = mapUrl.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
            DSCrawlerUrl _info = body.getValue();
            if(_info.url.indexOf(webDNS)==-1){
                mapDelUrl.put(_info.url,_info);
            }
        }
        set = mapDelUrl.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
            mapUrl.remove(body.getKey());
        }
    }    
    
    /*
    关键字搜索，过滤已知的不需要搜索的网站，保存在文件中。在mapFilterUrl对象集中指定
    */
    public void filterLinksMode1(Map<String,KeyValue> mapFilterUrl,Map<String,DSCrawlerUrl> mapUrl){
        Map<String,DSCrawlerUrl> mapDelUrl = new HashMap<String,DSCrawlerUrl>(); 
        Set set = mapUrl.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
            DSCrawlerUrl _info = body.getValue();
            if(!mapUrl.containsKey(_info.url)){
                //验证url是否有效（即是否包含关键字），如果是有效的，则添加
                Set _set = mapFilterUrl.entrySet();
                Iterator _it = _set.iterator();
                while(_it.hasNext()){
                    Entry<String,KeyValue> _body = (Entry<String,KeyValue>)_it.next();
                    if((Integer.parseInt(_body.getValue().key) == 0 && _info.url.indexOf(_body.getValue().value)>0)||_info.dns.isEmpty()){
                        mapDelUrl.put(_info.url,_info);
                        continue;
                    }
                    mapUrl.put(_info.url,_info);
                }
            }
        }
        set = mapDelUrl.entrySet();
        it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl> body = (Entry<String,DSCrawlerUrl>)it.next();
            mapUrl.remove(body);
        }
    }
    
    
    
    public void getHtmlLink(final String s,DSCrawlerUrl pageUrl,Map<String,KeyValue> mapFilterUrl,Map<String,DSCrawlerUrl> mapUrl){
        Parser m_parser ;
        if(s == null || s.equals(""))
            return ;
        try{
            m_parser = Parser.createParser(s,"utf-8");
            NodeFilter filter = new TagNameFilter("a");
            NodeList nodelist = m_parser.parse(filter);
            NodeIterator it = nodelist.elements();
            while(it.hasMoreNodes()){
                LinkTag node = (LinkTag)it.nextNode();
                
                DSCrawlerUrl _info = new DSCrawlerUrl();
                String url = node.getAttribute("href");
                
                if(url!=null && url.indexOf("#")>=0)
                    continue;
                if((url == null || url.equals("") || 
                        url.toLowerCase().indexOf("script")>=0)){
                    continue;
                }
                //System.out.println(url);
                //解析url
                _info = parsePageUrl(url,pageUrl.url);
                if(_info == null)
                    continue;                
                //
                if(node.toPlainTextString() == null || node.toPlainTextString().equals(""))
                    _info.title = node.getAttribute("title");
                else
                    _info.title = node.toPlainTextString().replaceAll("\\s+?", "");
                if(_info.title == null ||_info.title.equals(""))
                    _info.title = "YYYY";
                //
                _info.crawlerDeepth = pageUrl.crawlerDeepth + 1;
                if(!mapUrl.containsKey(_info.url)){
                    //验证url是否有效（即是否包含关键字），如果是有效的，则添加
                    Set _set = mapFilterUrl.entrySet();
                    Iterator _it = _set.iterator();
                    boolean out = false;
                    boolean in = true;
                    while(_it.hasNext()){
                        Entry<String,KeyValue> body = (Entry<String,KeyValue>)_it.next();
                        if((Integer.parseInt(body.getValue().key) == 0 && _info.url.indexOf(body.getValue().value)>0)||_info.dns.isEmpty()){
                            continue;
                        }
                        mapUrl.put(_info.url,_info);
                        /*
                        if(Integer.parseInt(body.getValue().key) == 0 && _info.url.indexOf(body.getValue().value)>0){
                            out = true;
                        }
                        if(Integer.parseInt(body.getValue().key) == 1)
                            in = false;
                        */ 
                    }
                    /*
                    _set = mapFilterUrl.entrySet();
                    _it = _set.iterator();                    
                    while(_it.hasNext()){
                        Entry<String,KeyValue> body = (Entry<String,KeyValue>)_it.next();
                        if(Integer.parseInt(body.getValue().key) == 1 && _info.url.indexOf(body.getValue().value)>=0){
                            in = true;
                        }
                    }
                    if( in && !out && !_info.dns.isEmpty()){
                         mapUrl.put(_info.url,_info);
                    }
                    */ 
                }
            }
        }catch(Exception e){
            System.out.println(" composite Convert extractorUrl :" + e.getMessage());
        }
    }     

    public static DSCrawlerUrl parsePageUrl(String url,String pageUrl){
        DSCrawlerUrl info = new DSCrawlerUrl();
        DSCrawlerUrl pageInfo ;
        try{
            if(url.toLowerCase().indexOf("http:")>=0){
                URL _url = new URL(url);
                info.url = url;
                info.dns = _url.getHost();
                info.path = _url.getPath();
                info.file = _url.getProtocol();
                info.IP = InetAddress.getByName(info.dns).getHostAddress();
            }else{
                URL _pageUrl = new URL(pageUrl);
                int index = pageUrl.lastIndexOf("/");
                //url = url.replaceAll("\\.\\.", "");
                while(url.indexOf(".")==0){
                    url = url.substring(1,url.length());
                }
                if(url.indexOf("/")==0)
                    info.url = _pageUrl.getProtocol()+ "://" + _pageUrl.getHost() + url;
                else
                    info.url = _pageUrl.getProtocol()+ "://" + _pageUrl.getHost() + "/"+ url;
//                    System.out.println(info.url);                    
                URL _url = new URL(info.url);
                info.dns = _url.getHost();
                info.path = _url.getPath();
                info.file = _url.getProtocol();
                info.IP = InetAddress.getByName(info.dns).getHostAddress();
            }
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
        return info;
    }
    
    public void extractItemsFromPage(String content,  List<KeyValue> fieldRules,List<DSComposite> listItems){
        CompositeConvert convert = new CompositeConvert();
        List<DSComposite> listDSComposite = new ArrayList<DSComposite>();
        List<KeyValue> list1 =  new ArrayList<KeyValue>();
        List<List<KeyValue>> list =  new ArrayList<List<KeyValue>>();
        for(int i=0;i<fieldRules.size();i++){
            list.add(new ArrayList<KeyValue>());
        }
        
        convert.matchValues(content,fieldRules.get(0).key,fieldRules.get(0).value, list1); //匹配提取第一字段
        for(int i=0;i<list1.size();i++){
            DSComposite info = new DSComposite();
            convert.setDSCompositeValue(list1.get(i).key, list1.get(i).value, info);
            listDSComposite.add(info);
        }
        for(int i=1;i<fieldRules.size();i++){      //  匹配第二个，以及后续提取字段规则
            convert.matchValues(content,fieldRules.get(i).key,fieldRules.get(i).value, list.get(i-1));// 匹配2到n个字段
        }
        for(int n=0;n<list1.size();n++){
            KeyValue valKey = list1.get(n);
            String[] arrVal1 = valKey.value.split("、");
            if(arrVal1.length<=1)
                continue;
            for(int m=0;m<arrVal1.length;m++){
                for(int i=0; i<fieldRules.size()-1; i++){
                    for(int j=0;j<list.get(i).size();j++){
                        KeyValue val = list.get(i).get(j);
                        if(arrVal1[m].indexOf(val.value) >= 0){        //获得与其他字段不重复的部分
                           int index = valKey.value.indexOf(arrVal1[m]);
                           if(index == -1)
                               continue;
                           System.out.println(valKey.value + " | " + arrVal1[m] + " | " + val.value + " | " + index + " | " + arrVal1[m].length());
                           if(index == 0){
                               valKey.value = valKey.value.substring(arrVal1[m].length(), valKey.value.length());
                               valKey.start = valKey.start + arrVal1[m].length();
                           }else{
                                if(valKey.start + index + arrVal1[m].length() - valKey.end<=1){
                                    valKey.value = valKey.value.substring(0,index-1);
                                    valKey.end = valKey.start + index-1;
                                }
                           }
                           System.out.println(valKey.value + " | " + arrVal1[m] + " | " + index + " | " + arrVal1[m].length());
                        }
                    }
                }
            }
            list1.set(n, valKey);         //
        }                    

        for(int i=0;i<list1.size();i++){
            KeyValue valKey = list1.get(i);
            convert.setDSCompositeValue(valKey.key, valKey.value, listDSComposite.get(i));
            for(int j=0;j<list.size();j++){
                for(int n=0;n<list.get(j).size();n++){
                    KeyValue val = list.get(j).get(n);
                    if(i+1<list1.size()){
                        if(val.start>=valKey.end && val.end<= list1.get(i+1).start){
                            convert.setDSCompositeValue(val.key, val.value, listDSComposite.get(i)); 
                        }
                    }else{
                        if(val.start>=valKey.end)
                            convert.setDSCompositeValue(val.key, val.value, listDSComposite.get(i)); 
                    }
                }
            }
        }


        for(int i=0;i<listDSComposite.size();i++){
            if(!listDSComposite.get(i).local.address.equals(""))
                listItems.add(listDSComposite.get(i));
        }    
    }
    
    public void extractRecordsFromContent(String content, DSCrawlerPageInfo url, List<KeyValue> fieldRules,List<DSComposite> listItems){
        boolean hasRule = false;
        ExtractFieldRules rules = new ExtractFieldRules();              //网页匹配特征库
        
        if(rules.mapExtracts == null){
            extractItemsFromPage(content,fieldRules,listItems);
        }else{         
            Set set = rules.mapExtracts.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,List<KeyValue>> body = (Entry<String,List<KeyValue>>)it.next();
                if(!match(url.url,body.getKey()).isEmpty()){        //  匹配url特征字符串，如果匹配上了
                    hasRule = true;
                    extractItemsFromPage(content,body.getValue(),listItems);
                    break;
                    //
                }
            }
            if(!hasRule)
              extractItemsFromPage(content,fieldRules,listItems);  
        }
    }    
    
    
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
    
    public void matchValues(String content,String filter,List<KeyValue> list){
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
   
   public String clearHtml(String html,List<KeyValue> lstClear){
        String content = html;
        for (int i=lstClear.size()-1;i>0;i--){
            
            content = content.replaceAll(lstClear.get(i).key,lstClear.get(i).value);
            
        }
        content = content.replaceAll("&gt;|&nbsp;", "");
        System.out.println(content);
        return content;
    }
   
    public String getHtmlTitle(final String s){
        String regex;
        String title = "";
        final List<String> list = new ArrayList<String>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find()){
            list.add(ma.group().replace("<title>", "").replace("</title>", ""));
        }
        for (int i = 0; i < list.size(); i++){
            title = title + list.get(i);
        }
        return title;
    }
    
    /*
     * 
     */
    private String replaceChiness(String line){
        Map<String,String> map = new HashMap<String,String>();
        map.put("，",",");
        map.put("。",".");
        map.put("》","<");
        map.put("《",">");
        map.put("|","|");
        map.put("？","?");
        map.put("？","?");
        map.put("、","\"");
        map.put("＼","\"");
        map.put("：",":");
        map.put("‘","'");
        map.put("（","(");
        map.put("）",")");
        map.put("【","[");
        map.put("】","]");
        map.put("－","-");
        map.put("～","~");
        map.put("！","!");
        map.put("’","'");
        map.put("１","1");
        map.put("２","2");
        map.put("３","3");
        map.put("４","4");
        map.put("５","5");
        map.put("６","6");
        map.put("７","7");
        map.put("８","8");
        map.put("９","9");
        map.put("◆","");
        //		map.put("　"," ");
        
        Set<?> set = map.entrySet();
        Iterator<?> it = set.iterator();
        while(it.hasNext()){
            Entry<String,String> body = (Entry<String,String>)it.next();
            line = line.replaceAll(body.getKey(),body.getValue());
        }
        return line;
    } 
    
 
    
    public static void main(String[] args){
//        DSCrawlerUrl url = CrawlerUtiles.parsePageUrl("../myspace/index.do","http://www.lvmama.com/myspace/index.do");
        
        ParseWorkConfigXml config =new ParseWorkConfigXml();
        config.parseWorks("D:\\Projects\\search engine\\CrawlerSystem\\config\\jobs\\WorkConfig.xml");
        List<DSExtractor> params = config.getWorksParams();        
        DSExtractor jobParams = params.get(params.size() -1);       
//        DSExtractor jobParams = new DSExtractor();
        
        Map<String,DSCrawlerUrl> mapUrl = new HashMap<String,DSCrawlerUrl>();
        DSCrawlerPageTxt extractPageInfo = new DSCrawlerPageTxt();
        
        CrawlerUtiles tool = new CrawlerUtiles();
//        tool.CrawlerByKeyWords("群光广场", jobParams, mapUrl, extractPageInfo);
        DSCrawlerUrl pageUrl = new DSCrawlerUrl();
        pageUrl.url = "http://www.aibang.com/bizsearch/%E6%AD%A6%E6%B1%89_%E7%BE%A4%E5%85%89%E5%B9%BF%E5%9C%BA_%E5%8C%96%E5%A6%86%E5%93%81";
        pageUrl.url = "http://www.aibang.com/detail/2029701983-421418310";
        pageUrl.url = "http://www.aibang.com/meishi/shanghai-hongqiaoshijiahuayuan-chuancai-bviao0api2ft8rbl/";
//        pageUrl.url = "http://www.aibang.com/detail/1824010149-1269530117";
//        pageUrl.url = "http://www.aibang.com/?area=bizsearch2&cmd=bigmap&city=%E5%A4%A9%E6%B4%A5&ufcate=%E7%BB%8F%E6%B5%8E%E5%9E%8B%E9%85%92%E5%BA%97&a=%E5%85%A8%E5%B8%82&q=&rc=1&as=5000&apr=0%7C0&hotel=1";
//        pageUrl.url = "http://www.aibang.com/tianjin/qingnianlvshe/";
//        pageUrl.url = "http://www.aibang.com/wuhan/zizhucan/";
//        pageUrl.url = "http://bus.aibang.com/beijing/busxid-1096120208-1495663450";
//        pageUrl.url = "http://www.aibang.com/jiudian-beijing-shoudujichang-fujin-jiudianyuding1/";
//        pageUrl.url = "http://tuan.aibang.com/wuhan/new_703549.html";

//        pageUrl.url = "http://www.baidu.com/link?url=gyrDdk_mlqn9sn3129n1Li97KDgTR-oht2uSx061aKVn0fuUxV6isI63-RKG9nPigVC2VtINrBXNDqN7QtPpQq";
//        pageUrl.url = "http://wuhan.edushi.com/hy/3-109262.shtml";
//        pageUrl.url = "http://mall.ppsj.com.cn/6B666C497FA451495E7F573A/";
//        pageUrl.url = "http://brand.ppsj.com.cn/index155.html";
//        pageUrl.url = "http://shopping.ppsj.com.cn/590/whqggckappabkb5900/";
//        pageUrl.url = "http://shopping.ppsj.com.cn/577/whqggcGieves&Hawkesjfks5776/";
//        pageUrl.url = "http://pp.ppsj.com.cn/Avenir/";
//        pageUrl.url = "http://wh.nuomi.com/deal/ewinqhxm.html";
//        pageUrl.url = "http://wh.nuomi.com/deal/3w600awa.html?feedback=15305_399_0__0&utm_source=dm&utm_medium=cps&utm_campaign=union&cid=007501";
//        pageUrl.url = "http://wh.nuomi.com/deal/zj5nltbw.html";
//     
//        pageUrl.url = "http://www.hao224.com/pro_9810222.html";
//        pageUrl.url = "http://www.hao224.com/wuhan/zizhucan/";
//        pageUrl.url = "http://www.55tuan.com/goods-0535f04cf78885f0.html?from=daohang-hao224-jcxg&piaoguo=1";
//        pageUrl.url = "http://shenzhen.55tuan.com/biz-788ee56f3a9569a7.html";
//        pageUrl.url = "http://shenzhen.55tuan.com/goods-8b8e82b2dc1992d9.html";
//        pageUrl.url = "http://www.55tuan.com/goods-9ae8687dbf452691.html?entryCityId=5";
//        pageUrl.url = "http://wh.haodou.com/shop/23396714.html";
//        pageUrl.url = "http://sh.haodou.com/shop/c5s2/";
//        pageUrl.url = "http://www.baidu.com/s?ie=utf-8&bs=site%3Appsj.com.cn+%E5%85%89%E8%B0%B7%E5%A4%A9%E5%9C%B0&f=8&rsv_bp=1&wd=site%3Addmap.com+%E5%85%89%E8%B0%B7%E5%A4%A9%E5%9C%B0&rsv_n=2&rsv_sug3=1&rsv_sug1=1&rsv_sug4=251&inputT=1540";
//        pageUrl.url = "http://www.baidu.com/s?ie=utf-8&bs=site%3Addmap.com+%E5%85%89%E8%B0%B7%E5%A4%A9%E5%9C%B0&f=8&rsv_bp=1&wd=site%3Addmap.com+%E7%BE%A4%E5%85%89%E5%B9%BF%E5%9C%BA";
        
//        pageUrl.url = "http://meishi.qq.com/beijing/s/d110105-a1000007";
//        pageUrl.url = "http://bj.meituan.com/movie/78203";
//        pageUrl.url = "http://shopping.ppsj.com.cn/577/whqggcqrdcerruti18815775/";
//        pageUrl.url = "http://pp.ppsj.com.cn/Aupres/";
//        pageUrl.url = "http://www.5yi.com/nearby/shop/reg_7--sort_huanjing_sort--sortway_descending";
//        pageUrl.url = "http://www.5yi.com/nearby/shop/reg_7--sort_huanjing_sort--sortway_descending?page=2";
//       pageUrl.url = "http://www.5yi.com/more_place/beijing.p2_14?district=%E9%80%9A%E5%B7%9E%E5%8C%BA";
        
//        pageUrl.url = "http://bj.city8.com/qichefuwu/poi4-1.html";
//        pageUrl.url = "http://sh.city8.com/bianminfuwu/poi8-3184.html";
//        pageUrl.url = "http://wh.city8.com/city8-ps/canyinfuwu/shaokao/";
//        pageUrl.url = "http://wh.city8.com/city8-ps/gouwuguangchang/shangchang/";
//        pageUrl.url = "http://hotel.city8.com/hlc1601/";        
//        pageUrl.url = "http://www.huitongke.com/hotel/Shanghai/8047/#rbt=RBT5";
//        pageUrl.url = "http://www.huitongke.com/hotel/Beijing/10834/#checkin=2013-08-22&checkout=2013-08-23&rbt=&worldwide=domestic";
        
//        pageUrl.url = "http://www.jifencity.com/search-36-161.html";        
//        pageUrl.url = "http://ditu.ddmap.com/21/city/--%BB%A6%C7%E0%C6%BD%B9%AB%C2%B7-1";
//        pageUrl.url = "http://nj.ddmap.com/755/city/-2844--1";
        pageUrl.url = "http://www.ddmap.com/21/listpv/k%D0%C2%CF%E7%D7%A1%CB%DE%B7%D1%B7%A2%C6%B1Q1005525232%B4%FA%BF%AA%B5%C7%B7%E2%B6%A8%B6%EE%B7%A2%C6%B1dc%B6%AF%CE%EF%D4%B0@L@%BA%E7%C7%C5%BB%FA%B3%A1/1";
//        pageUrl.url = "http://hotel.qunar.com/hbmap/index.jsp?tab=position&seq=beijing_city_20443&q=&haspoi=0";
//        pageUrl.url = "http://tj.58.com/shangpu/14508080569987x.shtml";
//        pageUrl.url = "http://meishi.quna.com/search/list.html?city=beijing&from=%E9%A6%96%E9%83%BD%E5%9B%BD%E9%99%85%E6%9C%BA%E5%9C%BA&keyword=&sortby=&page=4";
        
//        pageUrl.url = "http://www.gaopeng.com/beijing/deal/show/590313?us=duomai";
//        pageUrl.url = "http://www.zentuan.com/tuangou/view/7093188";
//        pageUrl.url = "http://www.zentuan.com/tuangou/view/6900408";
//        pageUrl.url = "http://www.maigoo.com/shop/";
        
//        pageUrl.url = "http://www.9tour.cn/place/0101_ZhongGuoJianSheYinXingBeiJingShouDuJiChangSanHaoHangZhanLouZhiXing/";
//        pageUrl.url = "http://store.tuan800.com/shop_1350659";
//        pageUrl.url = "http://store.tuan800.com/beijing/shoudujichang?deal=live";
//        pageUrl.url = "http://beijing.lashou.com/deal/7692745.html";
//        pageUrl.url = "http://beijing.lashou.com/deal/7699598.html";
//        pageUrl.url = "http://www.baidu.com/link?url=SuxrkPfZQD5IylXYLQqm5aq5EmtE2fMSR5h-39QwZsCWBAeQkknPySi9XCVTUyCn34UIlwEzmwNqWs2AcgVZX_";
        
//        pageUrl.url = "http://www.dianping.com/shop/3179956/review_all";
//        pageUrl.url = "http://www.17u.cn/SearchList.aspx?txtCityId=226&lableid=12883";
//        pageUrl.url = "http://www.dianping.com/search/keyword/2/10_%E5%A4%96%E5%8D%96%E9%80%81%E9%A4%90/g10r2585";
//        pageUrl.url = "http://www.dianping.com/search/category/2/10/g115r2585";
//        pageUrl.url = "http://map.sogou.com/poi/1_100046301074.htm";
//        pageUrl.url = "http://map.sogou.com/index/beijing/12_7.html";
//        pageUrl.url = "http://sz.ddmap.com/map/27-%C2%BA%C3%A9%C9%BD%C3%87%C3%B8---%C2%B9%C2%BA%C3%8E%C3%AF---7-1/";
        
//        pageUrl.url = "http://go.qq.com/hotel/list/wuhan-city_1801-poi_4315277172159855906.html";
//        pageUrl.url = "http://go.qq.com/static/hotel/detail/0180/01801604.html#checkin=2013-08-27&checkout=2013-08-28";        
 //       pageUrl.url = "http://www.chiconysquare.com/webserver/DoOperation.aspx?do=getzone&id=10&code=5965";
//        pageUrl.url = "http://www.chiconysquare.com/webserver/Shopping.aspx?do=shopinfo&id=376&code=8665";        
 //       pageUrl.url = "http://www.shairport.com/chn201107180336411/";
        
//       pageUrl.url = "http://film.spider.com.cn/sh-cinema/";
//        pageUrl.url = "http://film.spider.com.cn/";
        
//        pageUrl.url="http://www.ddmap.com/mstmap50007/g_search.jsp?mapno=21&keyname=%B4%A2%B4%E6&g_addr=&g_env_cx=&g_env_cy=&g_scope=1000&porder=1&curpage=1&services=%C5%AE%D0%D4%D7%EE%B0%AE&model=mlist&rzsh=1&yh=0&yy=0&pz=0";
        //
//        pageUrl.url = "http://tuan.360.cn/";
        
        
        tool.extractHtmlPage(jobParams, pageUrl, mapUrl, extractPageInfo);        
        System.out.println(extractPageInfo.content);        
        for(int i=0;i<extractPageInfo.items.size();i++){
            String val =i + ": "+ extractPageInfo.items.get(i).label + "|"
                    + extractPageInfo.items.get(i).name + "|" 
                    + extractPageInfo.items.get(i).local.address + "|" 
                    + extractPageInfo.items.get(i).phone + "|" 
                    + extractPageInfo.items.get(i).collection.title+ "|" 
                    + extractPageInfo.items.get(i).collection.keywords;
            val = val + "|电话:" + extractPageInfo.items.get(i).phone+ "|时间:" 
                    + extractPageInfo.items.get(i).time_service;
            System.out.println(val);
        }
        /*
        Set set = mapUrl.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSCrawlerUrl>body = (Entry<String,DSCrawlerUrl>)it.next();
//            System.out.println(body.getValue().dns + "|" + body.getValue().url + "|" + body.getValue().title);
        } 
        */
    }
}
