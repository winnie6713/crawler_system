package wuit.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public Test(){
		
	}
	
	public void print(String name){
		System.out.println(name);
	}
	
	public void execute(){
		System.out.println("execute!!!");
	}
	
	public void rename(File file){
		int ind = file.getName().indexOf(".");
		String newName = file.getAbsolutePath()+file.getName().substring(0,ind);
		file.renameTo(new File(newName));
	}
	
	public void getPageTime() throws IOException{
		DataInputStream is;
		URL url = new URL("http://www.aibang.com/cities-www");
		URLConnection connection = url.openConnection();
		 
		is = new DataInputStream(connection.getInputStream());
		String inputline;
		Pattern p = Pattern
		        .compile("[\\d]+-[\\d]+-[\\d]+");
		Matcher m;
		String datetime = new String();
		 
		while ((inputline = is.readLine()) != null) {
		    m = p.matcher(inputline);
		    if (m.find()) {
		        datetime = m.group();
		    }
		 
		}
		System.out.println("时间:" + datetime);
	}
	
    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("2", 5);
        map.put("2", 9);
        map.put("2", 10);
    }
}
