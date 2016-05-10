package wuit.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wuit.common.crawler.composite.DSCrawlerUrl;


public class CrawlerJobs {
	private Map<String,String> downloadList = new HashMap<String,String>();	
	private List<List<DSCrawlerUrl>> list = new ArrayList<List<DSCrawlerUrl>>();
	
//	private CrawlerServer server = null;
	
	private long _count = 0;
	
//	public JobParams jobParams = new JobParams();
/*
	public CrawlerJobs(JobParams params, CrawlerServer server){
		this.server = server;
		this.jobParams =params;
		for (int i=0;i<=this.jobParams.deepth;i++){
			List<CrawlerUrlInfo> lst = new ArrayList<CrawlerUrlInfo>();
			list.add(lst);
		}
	}
	
	public void Crawle(){
		int mode = this.jobParams.mode;
		if (mode == 0)
			CrawleKey100();
		if (mode == 1)
			CrawleUrl();
		if (mode == 2)
			CrawleUrls();
	}
	
*/	
	private void CrawleUrls(){
		/*
		Extractor extractor = new Extractor();
		extractor.extractorFiles();
		try{
			if(server == null)
				return ;
			//CrawlBaiDu10 baidu = new CrawlBaiDu10();
			this.jobParams.deepth = 0;
			for (int i=0;i<extractor.rsAddr.size();i++){
				CrawleKey10(extractor.rsAddr.get(i));
			}
			for (int i=0;i<extractor.rsCompany.size();i++){
				CrawleKey10(extractor.rsCompany.get(i));
			}
			for (int i=0;i<extractor.rs.size();i++){
				CrawleKey10(extractor.rs.get(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}
	
/*	
	private void CrawleUrl(){
		CrawlerUrlInfo info ;
		List<CrawlerUrlInfo> _urls = new ArrayList<CrawlerUrlInfo>();
		for (int i=0;i<this.jobParams.urls.size();i++){
			info = new CrawlerUrlInfo();
			info.crawlerDeepth = 0;
			info.title = i+"";
			info.url = jobParams.urls.get(i);
			_urls.add(info);
		}
		
		putUrlList(_urls,0);
		
//		info.setUrl(this.jobParams.url);
//		info.setTitle("welcome");
		
//		List<UrlInfo> list0 = new ArrayList<UrlInfo>();
//		list0.add(info);
//		putUrlList(list0,0);

		try{
			if(server == null)
				return ;			
//			CrawlerUrl crawler = new CrawlerUrl();
//			crawler.getSeedsUrl(this.jobParams.url);
//			list0 = crawler.getUrlList();			
//			putUrlList(list0,0);
			execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void CrawleKey100(){
		try{
			if(server == null)
				return ;
			CrawlBaiDu100 baidu = new CrawlBaiDu100();
			baidu.getSeedsUrlByBD(jobParams.Searchkey);
			List<CrawlerUrlInfo> list0 = baidu.getUrlList();
			putUrlList(list0,0);
			execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void CrawleKey10(String key){
		try{
			if(server == null)
				return ;
			CrawlBaiDu10 baidu = new CrawlBaiDu10();
			baidu.getSeedsUrlByBD(key);
			List<CrawlerUrlInfo> list0 = baidu.getUrlList();
			putUrlList(list0,0);
			execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void execute(){
		int count = 10;
		try{
			Thread.sleep(2000);
			while(true){
				CrawlerUrlInfo info = getUrl();
				if(info == null){
					if (getUrlSize() != 0)
						continue;
					else{
						if(server.getPools()==0){
							if (count == 0)
								break;
							else{
								count --;
								Thread.sleep(2000);
								continue;
							}
						}
					}
				}
				dispInfo();
//				CrawlerTask task = new CrawlerTask(this,info);
//				server.addNewJob(task);			
				_count ++;
				System.gc();
			}
			System.out.println("this work is finished!!! count:" + getUrlSize());			
		}catch(InterruptedException e){
			
		}

	}
	
	private synchronized CrawlerUrlInfo getUrl(){
		synchronized(this){
			int size = list.size();
			for (int i=size-1;i>=0;i--){
				while(list.get(i).size()>0){
					CrawlerUrlInfo url = list.get(i).get(0);
					list.get(i).remove(0);				
					if (downloadList.containsKey(url.url))
						continue;
					else{
						downloadList.put(url.url, url.url);
						return url;
					}
				}
			}
		}
		return null;
	}
	
	private synchronized int getUrlSize(){
		int count=0;
		synchronized (this){
			int size = list.size();			
			for (int i=size-1;i>=0;i--){
				count = count + list.get(i).size();
			}
		}
		return count;
	}
	
	private synchronized void dispInfo(){
		String msg="";
		synchronized(this){
			for (int i=0;i<list.size();i++){
				msg = msg + i + "：" + list.get(i).size()+"； " ;
			}
		}
		msg = "总数: " + _count + "； 待完成的任务" + msg + " 当前线程数" + server.getPools();
		System.out.println(msg);		
	}
	
	public synchronized void putUrlList(List<CrawlerUrlInfo> newList,int deepth){
		try{
			synchronized(this){
				if (deepth <= jobParams.deepth){
					list.get(deepth).addAll(newList);
				}
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}		
	}
	*/
}
