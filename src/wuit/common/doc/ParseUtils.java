package wuit.common.doc;

import java.net.InetAddress;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wuit.common.crawler.composite.DSCrawlerUrl;

public class ParseUtils {
	
	public static void main(String[] args){
            String url0 = "http://www.baidu.com/s?tn=site888_pg&wd=%E6%B4%AA%E5%B1%B1%E5%8C%BA+%E7%8F%9E%E7%8B%AE%E5%8D%97%E8%B7%AF%E9%99%84%E8%BF%91&f=1&ie=utf-8&rsv_crq=1";
            String url1 = "/edu/home.html";
            DSCrawlerUrl parsePageUrl = ParseUtils.parsePageUrl(url1,url0);
           
            System.out.println(parsePageUrl.url);
	}
	
	private static String getFileExtension(String fileName){
        String regEx="(?<=\\.)[^\\.]+"; 
       
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(fileName);
        String name="";
        while (m.find()){
        	name = m.group();       		
        }
		return name;
	}
	
	public static DSCrawlerUrl parseUrl(String url){
            DSCrawlerUrl info = null;
            try{
//                if(isUrl(url))
                {
                    URL _url = new URL(url);
                    info = new DSCrawlerUrl();
                    //				info.setPath( _url.getPath());
                    info.dns = _url.getHost();
//				info.setProtocol( _url.getProtocol());
//				info.setFile( _url.getFile());
//				int index = info.getPath().lastIndexOf("/");
//				info.setFile(info.getPath().substring(index+1,info.getPath().length()));
				info.IP = InetAddress.getByName(info.dns).getHostAddress();
				info.url = url;
//				info.setPort( _url.getPort());
//				info.setExtend(getFileExtension(_url.getFile()));
			}
		}catch(Exception e){
			System.out.println("parseUrl : " + e.getMessage());
		}		
		return info;
	}
	
	
	public static DSCrawlerUrl parsePageUrl(String url,String pageUrl){
            DSCrawlerUrl info = new DSCrawlerUrl();
            DSCrawlerUrl pageInfo ;
            try{
                if(url.toLowerCase().indexOf("http:")>=0){
                    URL _url = new URL(url);
                    info.url = url;
                    info.dns = _url.getHost();
                    info.IP = InetAddress.getByName(info.dns).getHostAddress();
                }else{
                    URL _pageUrl = new URL(pageUrl);
                    int index = pageUrl.lastIndexOf("/");
                    url = url.replaceAll("\\.\\.", "");
                    if(url.indexOf("/")==0)
                        info.url = pageUrl.substring(0,index) + url;
                    else
                        info.url = pageUrl.substring(0,index) + "/"+ url;
//                    System.out.println(info.url);                    
                    URL _url = new URL(info.url);
                    info.dns = _url.getHost();
                    info.IP = InetAddress.getByName(info.dns).getHostAddress();
                }
            }catch(Exception e){
                //e.printStackTrace();
                return null;
            }
            return info;
	}

	private static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
            + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
            + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
            + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
            + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
            + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
            + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
            + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
	}
	/////////////////////////////////////////////////////
	////////////////////////
	//取匹配组中的第一个
	public static String match(String content,String filter){
		String val ="";
		try{		
			Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
			while (m.find()) {
			   val =m.group();
			   break;
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	//取匹配所有的
	public static Vector<String> matchs(String content,String filter){
		Vector<String> vlist =new Vector<String>();
		try{		
			Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
			while (m.find()) {
			   String str =m.group().toString();
			   vlist.add(str);
//			   System.out.println(str);
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return vlist;
	}
	
	///////////////////////////////////////////////////////////////////////	
	/*
	public static String match(String source, String element, String attr) {
		   String result = "";
		   String reg = "<" + element +"[^>]*?\\s"+attr+"=(\\w+)[\\W]*?>"; //String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		   Matcher m = Pattern.compile(reg,Pattern.CASE_INSENSITIVE).matcher(source);
		   while (m.find()) {
			   result = m.group(1);
		   }
		   if(result.equals("")){
			   reg ="<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>"; 
			   m = Pattern.compile(reg,Pattern.CASE_INSENSITIVE).matcher(source);
			   while (m.find()) {
				   result = m.group(1);
			   }
		   }
		   return result;
	} 
	*/
	/*
	public static String match2(String source, String element,String attr1,String val1, String attr2) {
		   String result = "";
		   String reg1 = "<" + element + "[^<>]*?\\s" + attr1 + "=['\"]?"+ val1 + "['\"]*\\s.*?" + attr2 + "=['\"]?(.*?)['\"]?\\s.*?>";
		   String reg2 = "<" + element + "[^<>]*?\\s" + attr2 + "=['\"]?(.*?)['\"]?\\s.*?[^<>]*?\\s" +  attr1 + "=['\"]?"+ val1 + "['\"]*\\s.*?>";
		   Matcher m = Pattern.compile(reg1,Pattern.CASE_INSENSITIVE).matcher(source);
		   if (m.find()) {
			   result = m.group(1);
		   }else{
			   m =Pattern.compile(reg2,Pattern.CASE_INSENSITIVE).matcher(source);
			   if (m.find())
				   result = m.group();			   
		   }
		   return result;
	}
	*/ 
}
