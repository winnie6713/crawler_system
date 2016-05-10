package wuit.common.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import wuit.common.crawler.db.DbConnect;

public class Markov extends DbConnect{

	public int wordCount = 0;			//统计总字数
	private final int wordMax = 10;			//预设地址词最大长度
	public Map<String,WordInfo> mapKeyWord = new HashMap<String,WordInfo>();
	public List<String> listWord = new ArrayList<String>();	
	public List<String> keyWords = new ArrayList<String>();
	
	class WordInfo{
		public String key = "";
//		public String province = "";
//		public String city = "";
//		public String county = "";
		public int count = 0;
		public int word_len = 0 ;
//		public double p = 0.0;
	}
	
	class Word{
		public String key ="";
		
	}
	
	public Markov(){
		dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw);
		InitKeyWords();
	}
	/////////////////////////////////////////////////////////////////////////////
	public void addValueToMySQL(String name,int count,String tabelName){
		HashMap<String, String> values = new HashMap<String, String>();		
		values.put("name", name);
		values.put("count", count+"");	
		dbInsert(tabelName, values);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////
	private String clearSymbol(String content){
		content = content.replaceAll("\\[|\\]|\\(|\\)|、|，|！|\\.|。|\\�|\\【|\'|\"", ",");
		content = content.replace("\\", "").replaceAll("[\\d|\\-]+号|[\\d|\\-]+", "");
		content = content.replaceAll(":|_", ",").replaceAll("\\d+", ",");
		content = content.replaceAll("位于", ",").replaceAll("近", ",").replaceAll("附近", ",").replaceAll("与", ",");		
		content = content.replaceAll("\\,{2,}", ",");
//		System.out.println(content);
		return content;
	}
	
	//将地址按1到最大地址词长分词组合
	public void diffWord(int maxLen,String address,Map<String,WordInfo> mapKeyWord){
		address = clearSymbol(address);
		int size = address.length();
		wordCount = wordCount + size;
		String key ="";
		address = diffByKeyWord(address);
		System.out.println(address);
		String[] items = address.split(",");
		
		int wordSize = 0;
		for(int n=0;n<items.length;n++){
			address = items[n];
			wordSize = address.length();
			if(wordSize==0)
				continue;
			int _size = 0;
			if(maxLen>wordSize)
				_size = wordSize;
			else
				_size = maxLen;
			
		for (int i=1;i<= _size;i++){
			int pos = 0;
			for(pos =0;pos<wordSize-i+1;pos++){
				key = address.substring(pos,i+pos);
				if (!mapKeyWord.containsKey(key)){
					WordInfo info = new WordInfo();
					info.key = key;
					info.count = 1;
					info.word_len = key.length();
					mapKeyWord.put(key, info);
				}
				else{
					mapKeyWord.get(key).count++;
				}
			}
		}
		}
	}
	
	public void diffWords(List<String> listAddress){
		for (int i=0; i<listAddress.size(); i++){
//			DSAddress info = null;
			diffWord(wordMax,listAddress.get(i),mapKeyWord);
		}
	}
	
	public void getAddressFromTxtFile(String path,String file){
		
	}
	
	public void InitKeyWords(){
		keyWords.add("省");
		keyWords.add("市");
		keyWords.add("区");
		keyWords.add("县");
		keyWords.add("镇");
		keyWords.add("乡");
		keyWords.add("路");
		keyWords.add("街");
		keyWords.add("号");
		keyWords.add("诊所");
		keyWords.add("酒店");
		keyWords.add("大道");
		keyWords.add("大厦");
		keyWords.add("中心");
		keyWords.add("广场");
		keyWords.add("公园");
		keyWords.add("酒楼");
		keyWords.add("宾馆");
		keyWords.add("菜馆");
		keyWords.add("公司");
		keyWords.add("旅行社");
		keyWords.add("农家乐");
		keyWords.add("体育馆");
	}
	
	private String diffByKeyWord(String item){
		String key="";
		int len  = item.length();
		if (len>1){
			for (int i=0;i<keyWords.size();i++){
				key = keyWords.get(i);
				if(key.length()>0)
					item = item.replaceAll(key, key+",");
			}
		}
		return item;
	}
	
	public static void main(String [] args){
		Markov markov = new Markov();
/*
		DSAddress address = new DSAddress();
		
		String addrStr = "东北大学南门查看地图公交驾乘网友点评:5条这里环境特好.如果办成会员的话也不贵,里面还有吃的...[更多]西塔人和网吧..中山大道佳丽广场_往民众乐园方向走的天桥,2楼和3楼_查看地图公交驾乘网友点评:7条可是大家那次不知道怎么回事,都不怎么吃,菜都还是很.....";
		addrStr = addrStr + ",杨家坪步行街简介:大概有接近200个平方，分了好几个区，根据区的不同，电脑的配置不同，价钱也不同，全是沙发坐椅...查看地图公交驾乘网友点评:62条额！机器..";
		addrStr = addrStr + ",鼓楼区模范马路130号简介:零距离网吧鼓楼区模范马路130号83478122查看地图公交驾乘网友点评:9条在网吧上网最希望的就是网速快病毒少..";
		addrStr = addrStr + ",深圳市福田区华强南路3016号赛格苑二楼简介:龙珠网吧本网吧配置高，环境优雅！100兆光纤极速上网！查看地图公交驾乘网友点评:3条这..";
		addrStr = addrStr + ",九里堤中路301号_欧美科技学院以北50米_简介:动感地带网吧九里堤中路西南交通大学欧美科技学院以北50米028-87612900..";
		addrStr = addrStr + ",西湖区文三路48号简介:杭州大排大快餐有限公司成立于1993年，是当时较早经营中式快餐的公司且具有一定的规模和正规性...查看地图公..";
		addrStr = addrStr + ",番禺市桥繁华路3号简介:豪华的装修优雅的环境全新装修，空气清新，分为游戏区,卡座区,视频区和vip房等。全新顶级...查看地图公交驾乘..";
		addrStr = addrStr + ",天河区体育东路78号_电力局大厦后面_简介:george，高级调酒师职称兼培训导师。1986年入职于中国大酒店，通过不懈的努力钻研和学习...查..";
		addrStr = addrStr + ",徐汇区宝庆路1号3楼简介:感觉还不错，不是想象中那种吵闹的酒吧。乐队很好，歌都唱得不错，那里的乐队真不错，虽然都是上海...查看地图公..";
		
		address.address = addrStr;
  
		
		markov.diffWord(markov.wordMax, address, markov.mapKeyWord);
		
		Set<?> set = markov.mapKeyWord.entrySet();
		Iterator<?> it = set.iterator();
		while(it.hasNext()){
			Entry<String,WordInfo> body = (Entry<String,WordInfo>)it.next();
			System.out.println(body.getKey() + " " + body.getValue().count);
		}
		System.out.println(markov.wordCount + " " + markov.mapKeyWord.size());
		
*/		
		
		DBAddress dbAddress = new DBAddress();
		List<String> listAddress = new ArrayList<String>();
		dbAddress.txtItemsToAddressList("D:\\product\\aibang\\lib\\",  "UTF-8", listAddress);
		
		for (int i=0;i<listAddress.size();i++){
			markov.diffWord(markov.wordMax, listAddress.get(i), markov.mapKeyWord);
		}
		
		Set<?> set = markov.mapKeyWord.entrySet();
		Iterator<?> it = set.iterator();
		int count = 0;
		while(it.hasNext()){
			@SuppressWarnings("unchecked")
			Entry<String,WordInfo> body = (Entry<String,WordInfo>)it.next();
			if (body.getKey().length() >= 3 && body.getKey().indexOf("路") >= 0){
				System.out.println(body.getKey() + " " + body.getValue().count);
				count ++;
			}
			markov.addValueToMySQL(body.getKey(), body.getValue().count, "markov");
		}
		System.out.println(markov.wordCount + " " + markov.mapKeyWord.size() + "  count:" + count);
		
	}
}
