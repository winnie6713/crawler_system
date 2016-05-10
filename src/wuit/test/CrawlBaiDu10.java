package wuit.test;

import java.util.List;
import java.util.Vector;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import wuit.common.crawler.composite.DSCrawlerUrl;
import wuit.common.doc.ParseUtils;

public class CrawlBaiDu10 {
	private Vector<DSCrawlerUrl> Urls =null;
	public CrawlBaiDu10(){
		Urls = new Vector<DSCrawlerUrl>() ;
	}

	public String getSeedsUrlByBD(String key){		
		ConnectionManager manager;
		try{
			manager = Page.getConnectionManager();
			String path="http://www.baidu.com/s?wd=" + key + "&pn=100&tn=site888_pg&rn=10";
			Parser parser = new Parser(manager.openConnection(path));
			
			NodeFilter filter = new AndFilter(new TagNameFilter("a"), new HasParentFilter(new TagNameFilter("h3")));
			NodeList nodelist = parser.extractAllNodesThatMatch(filter);
			NodeIterator it = nodelist.elements();
			while(it.hasMoreNodes()){				
				DSCrawlerUrl _info = new DSCrawlerUrl();				
				LinkTag linkTag = (LinkTag)it.nextNode();				

				String url = linkTag.getAttribute("href").trim();
				
				_info = ParseUtils.parseUrl(url);
				String title =linkTag.getLinkText().trim().replaceAll("\r|\n|\t", "");
				_info.title = title;				
			
				if(_info.url != null || _info.url.indexOf("http")>0){
					Urls.add(_info);
				}
			}
			return toJSON(Urls);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public Vector<DSCrawlerUrl> getUrlList(){
		return Urls;
	}
	
	public String getJSONUrls(){
		return toJSON(Urls);
	}
	
	private String toJSON(List<DSCrawlerUrl> urls){
		String rs = "[";
		if (urls.size()==0)
			return "";
		try{
			for (int i=0;i<urls.size();i++){
				String title = new String(urls.get(i).title.getBytes(),"GB2312");
				rs = rs + "{\"title\":\"" + title +"\",\"url\":\""+ urls.get(i).url + "\"},";
			}
			rs = rs.substring(0,rs.length()-1) + "]";
//			System.out.println("toJSON: " + rs + " end json");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return rs;
	}
}
