/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.db.KeyValue;
/**
 *
 * @author lxl
 */
public class TestRegex {
    public static void main(String[] args){
//        String filter = ".{2,}省+|.{2,}州+|[^镇路]{2,}市+|.{2,}县+|.{2,}区+|[^镇]{2,}镇+|乡|村|.{2,}庄+|.{1,}路+|街|道|楼|\\d{1,}号|弄|厦|座|心|层|室|门|里|场|城|巷|营|园|宫|馆|幢|.{2,}店";
        String[] filter = {"","","[^区]{2,}区","","","","","","","",""};
        filter[0] = "[^省]{2,}省|[^自治区]{2,}自治区|[^城市]{2,}市";
        filter[1] = "[^城市]{2,}市|[^自治州]{2,}自治州||[^地区]{2,}地区||[^盟]{2,}盟";
        filter[2] = "[^县]{2,}县|[^路区]{2,}区|[^旗]{2,}旗";
        filter[3] = "[^镇]{2,}镇|[^乡]{2,}乡";
        filter[4] = "[^村]{2,}村|[^庄]{2,}庄";
        filter[5] = "[^路]{2,}路|[^街]{2,}街|[^道]{2,}道";
        filter[6] = "[^弄|号]{2,}弄|[^号]{2,}号|[^巷]{2,}巷";
        filter[7] = "[^厦]{2,}厦|[^店]{2,}店|[^楼]{1,}楼|[^城]{2,}城|[^园]{2,}园|[^中心]{2,}中心|[^广场]{2,}广场|[^门]{1,}门|[^幢]{2,}幢|[^区]{1,}区";
        filter[8] = "[^层]{1,}层|\\d{1,}楼";
        filter[9] = "[^室]{1,}室|[^号]{1,}号";
        
        String content = "鼓楼区南翔庄镇镇江路800号市百一店东楼17层";
        content = "江东北路218号新城市假日广场";
        content = "东城区东直门内大街253号";
        content = "南京草场门大街96号";
        content = "九龙坡区渝州路79-117号渝福建陶城";
        
        content = "北京市朝阳区安慧里三区6号楼东门三层";
        content = "普陀区中山北路3856弄中环大厦1911室";
        content = "鼓楼云南北路49号天星翠琅大厦5楼";
        content = "南京市白下区淮海路68号苏宁电器一楼3C服务中心";
        
        content = "苏果社区台城花园07幢";//(台城花园便利店)
        content ="近郊南京市六合区雄州南路288号";
        content = "浦东新区东方路1881弄东方城市花园79号";
        
        content = "天津市津南区小站镇米立方海世界水上乐园";
        
        content = "坝头洲畔村大山沟永泰路东区";
        
        try{
            for(int i=0;i<filter.length;i++){
                String val = "";
            Matcher m = Pattern.compile(filter[i],Pattern.CASE_INSENSITIVE|Pattern.MULTILINE|Pattern.CANON_EQ).matcher(content);
            while (m.find()) {
                if (m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.key = m.group();
                value.start = m.start();
                value.end = m.end();
                value.order = -1;
                System.out.println(value.key + " " + value.start + " " + i);
                val = val + value.key;
                content = content.replace(value.key, "" );
                //list.add(value);
            }
            System.out.println(val);
            }
        }catch(Exception e){
            System.out.println("Service address  matchValues :" + e.getMessage());
        }        
        
        
        //"\\w+ "的意思是"一个或多个单词字符"
        /*
        Matcher m = Pattern.compile("\\w+").matcher("Evening is full of the linnet's wings");
        while(m.find())
            System.out.println(m.group());
        int i = 0;
        while(m.find(i)) {
            System.out.print(m.group() + " " + i);
            i++;
        }
        */
//    monitor.expect(new String[] {"Evening", "is", "full", "of","the","linnet", "s","wings","Evening vening ening ning ing ng g is is s full " + "full ull ll l of of f the the he e linnet linnet " +"innet nnet net et t s s wings wings ings ngs gs s " });

    }
}
