package wuit.test;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;

import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.doc.ParseUtils;

public class CrawlerUrl {
	private Vector<DSCrawlerUrl> Urls =null;
	public CrawlerUrl(){
		Urls = new Vector<DSCrawlerUrl>() ;
	}
	
	public String getSeedsUrl(String url){
		ConnectionManager manager;
		DSCrawlerUrl pageUrl = new DSCrawlerUrl();
		pageUrl = ParseUtils.parseUrl(url);
		try{
			manager = Page.getConnectionManager();
			Parser parser = new Parser(manager.openConnection(url));
			
			NodeFilter filter = new TagNameFilter("a");
			NodeList nodelist = parser.extractAllNodesThatMatch(filter);
			NodeIterator it = nodelist.elements();
			while(it.hasMoreNodes()){
				DSCrawlerUrl _info = new DSCrawlerUrl();				
				LinkTag linkTag = (LinkTag)it.nextNode();				

				String _url = linkTag.getAttribute("href").trim();
				
				_info = ParseUtils.parseUrl(_url);
				if(_info==null)
					continue;
				if(_info.dns.equals("")){
					String t_url = "http://"+   pageUrl.dns +_url;
					System.out.println(t_url);
					_info = ParseUtils.parseUrl(t_url);
					System.out.println(_info.url);
				}
				
				
				String title =linkTag.getLinkText().trim().replaceAll("\r|\n|\t", "");
				_info.title = title;				
			
				if(_info.url != null || _info.url.indexOf("http")>0){
					if(isUrl(_info.url))
						Urls.add(_info);
				}
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean isUrl (String pInput) {
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
	
	public Vector<DSCrawlerUrl> getUrlList(){
		return Urls;
	}
}
