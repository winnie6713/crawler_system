package wuit.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wuit.common.doc.ParseUtils;

public class Words {

	public Map<String,wordInfo> mapWordsAddress = new HashMap<String,wordInfo>();
	
	public Map<String,wordInfo> mapWordsPhone = new HashMap<String,wordInfo>();
	public Map<String,wordInfo> mapWordsPost = new HashMap<String,wordInfo>();
	/*
	 * state = 0		null
	 * 7——5：类别{}
	 * 1——4：词性{名词、动 词、形容词、副词、连词、助词}
	 * 0:词标识=={0:非词；1：词}
	 */
	
	class wordInfo{
		public Map<String,wordInfo> wordMap = new HashMap<String,wordInfo>();
		public String key = "";
		public String value ="";
		public int state = 0;
	}
	
	public void InitFromFiles(String path){
		File filePath = new File(path);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		try{
			for (int i=0;i<files.length;i++){
				String pathFile = path + "\\" +files[i];
				InitFromFile(pathFile);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void InitFromFile(String pathFile){
		BufferedReader br;
		try{			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile),"gb2312"));
			String c = br.readLine();
			while(c != null){
				String str =  new String(c.getBytes(),"utf-8");
				String province = ParseUtils.match(str, "(?<=省份:)[^;]+(?=;)");
				String city = ParseUtils.match(str, "(?<=城市:)[^;]+(?=;)");
				String county = ParseUtils.match(str, "(?<=行政区:)[^;]+(?=;)");
				String district = ParseUtils.match(str, "(?<=地址:)[^;]+(?=;)");
				
				String phonecodeCity = ParseUtils.match(str, "(?<=城市区号:)[^;]+(?=;)");
				String postcodeCity = ParseUtils.match(str, "(?<=城市邮编:)[^;]+(?=;)");
				String postcode = ParseUtils.match(str, "(?<=邮编:)[^;]+(?=;)");
				String phonecode = ParseUtils.match(str, "(?<=区号:)[^;]+");
				
				if (postcode != null || !postcode.equals(""))
					InitAddress(province,city,county,district,phonecode,postcode);
				else
					InitAddress(province,city,county,district,phonecodeCity,postcodeCity);				
				c =br.readLine();
			}
			br.close();	

		}catch(Exception e){
			e.printStackTrace();
		}			
	}
	
	private void InitAddress(String province,String city,String county,String district,String phone,String post){
		int index =-1;
		String key = "";
		String value="";
		if (province!=null && !province.equals("")){
			InsertWord("","",province,mapWordsAddress);
			if (province.indexOf("省")>=0)
				InsertWord("","省:",province.substring(0,province.length()-1),mapWordsAddress);
			if (province.indexOf("市")>=0)
				InsertWord("","市:",province.substring(0,province.length()-1),mapWordsAddress);					
			if (province.indexOf("自治区")>=0){
				key  =province.substring(0,province.length()-3);
				InsertWord("","自治区:",key,mapWordsAddress);
			}
			index = city.indexOf(province);
		}
		if (city!=null && !city.equals("")){
			if (index>=0)
				city = city.substring(city.indexOf(province)+ province.length(),city.length());
			value = province+"..."+phone+"."+post;
			InsertWord("",value,city,mapWordsAddress);
			if (city.indexOf("市")>=0){
				value = "市:"+ value;
				key = city.substring(0,city.length()-1);
				if (key.length()>=2)
				InsertWord("", value, key, mapWordsAddress);
			}
			if (city.indexOf("地区")>=0){
				key = city.substring(0,city.length()-2);
				if (key.length()>=2)
				InsertWord("","地区:" +value,key,mapWordsAddress);
			}else{
				if (city.indexOf("区")>=0){
					key = city.substring(0,city.length()-1);
					if (key.length()>=2)
					InsertWord("","区:" +value,key,mapWordsAddress);
				}					
			}
			if (city.indexOf("自治州")>=0){
				key = city.substring(0,city.length()-3);
				if (key.length()>=2)
				InsertWord("","自治州:"+ value,key,mapWordsAddress);
			}
		}
		index = county.indexOf(city);
		if (county!=null && !county.equals("")){
			if (index>=0)
				county = county.substring(county.indexOf(city) + city.length(),county.length());						
			InsertWord("",province+"." + city+".."+phone+"."+post,county,mapWordsAddress);
			if (county.indexOf("区")>=0){
				key = county.substring(0,county.length()-1);
				value = "区:" + province+"." + city+".."+phone+"."+post;
				if (key.length()>=2)
				InsertWord("",value, key, mapWordsAddress);
			}
			if (county.indexOf("市")>=0){
				key = county.substring(0,county.length()-1);
				value = "市:" + province+"." + city+".."+phone+"."+post;
				if (key.length()>=2)
					InsertWord("",value, key, mapWordsAddress);
			}
			if (county.indexOf("县")>=0){
				key = county.substring(0,county.length()-1);
				value = "县:" + province+"." + city+".."+phone+"."+post;
				if (key.length()>=2)
					InsertWord("",value, key, mapWordsAddress);
			}
		}					
		if (phone !=null && post != null && district!=null && !district.equals("")){
			value = province+"." + city + "." + county+"."+phone+"."+post;
			InsertWord("", value, district, mapWordsAddress);
		}
		
		value = province +"." + city;
		if (!mapWordsPhone.containsKey(phone)){
			wordInfo newWord = new wordInfo();
			newWord.key = phone;
			newWord.value = value;
			newWord.state = 1;
			mapWordsPhone.put(phone, newWord);
		}
		
		if (!mapWordsPost.containsKey(post)){
			wordInfo newWord = new wordInfo();
			newWord.key = post;
			newWord.value = value + ";" + county;
			newWord.state = 1;
			mapWordsPost.put(post, newWord);
		}else{
			if (mapWordsPost.get(post).value.indexOf(county)==-1)
			mapWordsPost.get(post).value = mapWordsPost.get(post).value + ";" + county; 
		}		
	}
	
	private void InsertWord(String key,String value,String word, Map<String,wordInfo> map){
		try{
		if (word.length()==0)
			return;
		if (word.length()>1){
			String _key = word.substring(0,1);
			if (map.containsKey(_key)){
				InsertWord(key + _key,value,word.substring(1,word.length()),map.get(_key).wordMap);				
			}else{
				wordInfo newWord = new wordInfo();
				newWord.state = 0;
				newWord.key = key + _key;
				newWord.value = value;
				map.put(_key, newWord);
				_key = word.substring(0,1); 
				String newKey =word.substring(1,word.length()); 
				InsertWord(key + _key,value,newKey,newWord.wordMap);
			}
		}else{
			if (map.containsKey(word)){
				map.get(word).state = 1;
				if (map.get(word).value.indexOf(value)==-1 && value.length()>10)
					map.get(word).value =map.get(word).value + ";" + value;
				else
					map.get(word).value =value;
			}else{
				wordInfo newWord = new wordInfo();
				newWord.state = 1;
				newWord.key = key + word;
				newWord.value = value;
				map.put(word, newWord);
				return ;
			}			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<String> queryWordAddress(String section){
		List<String> list = new ArrayList<String>();
		findWord(section,mapWordsAddress,list);		
		
		return list;
	}
	
	public String queryWordPhone(String phone){
		if (mapWordsPhone.containsKey(phone)){
			return mapWordsPhone.get(phone).value;
		}
		return "";
	}
	
	public String queryWordPost(String post){
		if (mapWordsPost.containsKey(post)){
			return mapWordsPost.get(post).value;
		}
		return "";
	}
	
	private void findWord(String word,Map<String,wordInfo> map,List<String> listWord){
		if (word.length()==0)
			return;
		if (word.length()==1){
			if(map.containsKey(word)){
				if (map.get(word).state != 0){
					listWord.add(map.get(word).key+ ";" + map.get(word).value);
				}
			}
			return;
		}else{
			String _key = word.substring(0,1);
			if (map.containsKey(_key)){
				if(map.get(_key).state != 0){
					listWord.add(map.get(_key).key + ";" + map.get(_key).value);
					findWord(word.substring(1,word.length()),map.get(_key).wordMap,listWord);
				}else{
					findWord(word.substring(1,word.length()),map.get(_key).wordMap,listWord);
				}
			}else{
				return;
			}
		}
	}

	public static void main(String[] args){
		Words word = new Words();
		word.InitFromFiles("d:\\product\\lib\\postcode\\");
//		word.InitFromFile("d:\\product\\lib\\postcode\\紫阳县1370006567563.txt");
//		word.InitFromFile("d:\\product\\lib\\postcode\\辖区1370005026252.txt");
		List<String> list = word.queryWordAddress("朝阳街");	
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		list = word.queryWordAddress("人民南路");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));		
		list = word.queryWordAddress("重庆市巫溪县");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		list = word.queryWordAddress("长乐路");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		list = word.queryWordAddress("解放路");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		list = word.queryWordAddress("朝阳街17巷");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		list = word.queryWordAddress("解放街");
		for (int i=0;i<list.size();i++)
			System.out.println(list.get(i));
		System.out.println("0915" + ":" + word.queryWordPhone("0915"));
		System.out.println("622100" + ":" + word.queryWordPost("622100"));
		System.out.println("725600" + ":" + word.queryWordPost("725600"));
	}
}
