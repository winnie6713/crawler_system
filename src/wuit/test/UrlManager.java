package wuit.test;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UrlManager {
	public static Map<String,String> mapDowned = new HashMap<String,String>();	
	public static List<List<UrlInfo>> lstUrls = new ArrayList<List<UrlInfo>>();
	
	public static List<String> lstInvalidate = new ArrayList<String>();
	
	
	public synchronized static void addInvalidateUrl(String url){
		lstInvalidate.add(url);
	}
	
	public synchronized static void getInvalidateUrl(){
		Collections.sort(lstInvalidate);
		List<String> lst = new ArrayList<String>();
		int count = 0;
		int len = 0;
		for(int i=0;i<lstInvalidate.size();i++){
			
		}
		return ;
	}
	
	public synchronized static UrlInfo getUrl(){
		int size = lstUrls.size();
		for (int i=size-1;i>=0;i--){
			while(lstUrls.get(i).size()>0){
				UrlInfo url = lstUrls.get(i).get(0);
				lstUrls.get(i).remove(0);				
				if (mapDowned.containsKey(url.getUrl()))
					continue;
				else{
					mapDowned.put(url.getUrl(), url.getUrl());
					return url;
				}
			}
		}
		return null;
	}
	
	public synchronized void putUrlList(List<UrlInfo> newList,int deepth,int max){
		try{
			if (deepth <= max){
				lstUrls.get(deepth).addAll(newList);
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}		
	}
	
	public static UrlInfo parseUrl(String url){
		UrlInfo info = null;
		try{
			if(isUrl(url)){
				URL _url = new URL(url);
				info = new UrlInfo();
				info.setPath( _url.getPath());
				info.setDNS(_url.getHost());
				info.setProtocol( _url.getProtocol());
				info.setFile( _url.getFile());
				int index = info.getPath().lastIndexOf("/");
				info.setFile(info.getPath().substring(index+1,info.getPath().length()));
				info.setIP( InetAddress.getByName(info.getDNS()).getHostAddress());
				info.setUrl(url);
				info.setPort( _url.getPort());
				info.setExtend(getFileExtension(_url.getFile()));
			}
		}catch(Exception e){
			System.out.println("parseUrl : " + e.getMessage());
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
	
	public static UrlInfo parsePageUrl(String url,String pageUrl){
		UrlInfo info =null;
		UrlInfo pageInfo ;
		try{
			if (!isUrl(pageUrl))
				return null;
			pageInfo = parseUrl(pageUrl);		
			if (url.indexOf("://")==-1){
				if(url.indexOf("/")!=0)
					url = "http://" + pageInfo.getDNS() + "/" + url;
				else
					url = "http://" + pageInfo.getDNS() + url;
			}
			info = parseUrl(url);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return info;
	}
}
