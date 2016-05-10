package wuit.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wuit.common.crawler.db.KeyValue;

public class ExtractPageContent {
    
    public String read(String path,String fileName,String encode){
        String rs = "";
        BufferedReader br = null;
        if(path == null)
            return rs;
        try{
            if(fileName == null || fileName.equals(""))
                return rs;
            fileName= path + fileName;
            File file = new File(fileName);
            if (!encode.equals(""))
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
            else
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String c = br.readLine();
            while(c != null){
                rs= rs + c +"\r\n";
                c = br.readLine();
            }
            br.close();
            return rs;
        }catch(IOException e){
            e.printStackTrace();
        }
        return rs;
    }
	
	////////////////////////
	//取匹配组中的第一个
    private String matchValues(String content,String filter,List<KeyValue> list){
        String val ="";
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if (list == null)
                    list = new ArrayList<KeyValue>();
                KeyValue value = new KeyValue();
                value.value = m.group();
                value.start = m.start();
                value.end = m.end();
                list.add(value);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }
	//取匹配组中的第一个
    public String match(String content,String filter){
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
}
