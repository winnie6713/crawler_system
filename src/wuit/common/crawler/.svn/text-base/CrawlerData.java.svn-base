package wuit.common.crawler;

import java.util.ArrayList;
import java.util.List;
import wuit.common.crawler.composite.CompositeConvert;

import wuit.common.crawler.composite.CompositeParse;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.DSCrawlerPageInfo;
import wuit.common.crawler.composite.DSCrawlerPageTxt;
import wuit.common.doc.FileIO;

public class CrawlerData {
	private CrawlerJob job = null;
	private List<DSCrawlerPageTxt> listContents = new ArrayList<DSCrawlerPageTxt>();
	private CompositeParse parse;
	private String outPath = "";
	CompositeConvert convert = new CompositeConvert();
        
	public CrawlerData(CrawlerJob job,String path,CompositeParse parse){
            this.job = job;
            this.parse = parse;
            this.outPath = path;
	}
	
	public synchronized void putContentList(DSCrawlerPageTxt pageContent){
    //        listContents.add(pageContent);
            saveData(pageContent);
//            System.out.println("putContentList(CrawlerPageContent content) !!!!!!!!!!!!!!!!!!! ");
	}
	
	public synchronized int getListSize(){
            return listContents.size();
	}
	
	public synchronized void saveData(DSCrawlerPageTxt pageContent){
            if (job == null)
                return;
            
            DSCrawlerPageInfo info = new DSCrawlerPageInfo();
            info = pageContent.urlCrawler;
            for (int i=0;i<pageContent.items.size();i++){
                parse.extractRecord(pageContent.items.get(i));
            }
            if (pageContent.items.size()>0)
                saveAsTextFile(info,pageContent.items,pageContent.content);
        }
	
	private synchronized void saveAsTextFile(DSCrawlerPageInfo info,List<DSComposite>items,String allContent){
		String name = info.title;
		if(name == null || name.equals(""))
			return;                
		name = name.replaceAll("[^\\da-zA-Z\u4E00-\u9FA5]", "");
                if(name.length()>10)
                    name = name.substring(0,10);
		String vals = "";
		for (int i=0;i<items.size();i++){
                    String _val = convert.DSCompositeToString(items.get(i));
                    vals = vals + _val + "\r\n";
                    System.out.println(_val);
		}
                
		String content ="";
		if (vals.length()>10 && !outPath.equals("")){
                    content = "url:{" + info.title + ";" + info.dns + ";" + info.url + "}\r\n";
                    content = content + "items:{" + vals + "}\r\n";
                    content = content +  "content:{" + allContent + "}\r\n";
                    FileIO.writeAsTxts(outPath, name ,content);
		}		
	}
}
