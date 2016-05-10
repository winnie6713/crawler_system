/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.common.dictionary.address;

import wuit.common.crawler.composite.LocalInfo;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.db.KeyValue;
import wuit.common.dictionary.words.LearnNewWords;

/**
 *
 * @author lxl
 */
public class ServiceAddress {
    public AddressDataBase db = new AddressDataBase();
    //public AddressExtractor extractor = new AddressExtractor();
    public AddressExtractDirect0 extractor = new AddressExtractDirect0();
    
    public ServiceAddress(){
        
    }
   
    public void matchValues(String content,String name,String filter,List<KeyValue> list){
        try{
            Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(content);
            while (m.find()) {
                if (m.group().isEmpty())
                    continue;
                KeyValue value = new KeyValue();
                value.key = name;
                value.order = -1;
                value.value = m.group();
                value.start = m.start();
                value.end = m.end();
                list.add(value);
            }
        }catch(Exception e){
            System.out.println("Service address  matchValues :" + e.getMessage());
        }
    }
    
    public void queryAddress(String adress,LocalInfo data){
        
    }
    
    ////////////////////////////////////////
    public int parseAddressOfDSComposite(DSComposite composite){   
        EvalutionValue mode = new EvalutionValue();        
        int state = 0;
        String address = composite.local.address;
//        LocalInfo data_address = new LocalInfo();
        extractor.parseAddress(address, composite, db, mode);
//        composite.local = data_address;
        
        LocalInfo data_phone = new LocalInfo();
        Evaluation.evelutePhoneAddress(data_phone, composite.phone_code, db);
        mode.phone = Evaluation.evaluteLocalInfo(data_phone);
        if(!data_phone.province.isEmpty()){
            composite.local.province = data_phone.province;
        }
        if(!data_phone.city.isEmpty()){
            composite.local.city = data_phone.city;
        }
        if(!data_phone.county.isEmpty() && data_phone.county.indexOf(composite.local.county)== -1){
//            composite.local.county = "";
        }
        
        
        LocalInfo data_post = new LocalInfo();
        Evaluation.evelutePhoneAddress(data_post, composite.postcode, db);        
        mode.net_address = Evaluation.evaluteLocalInfo(data_post);
        Evaluation.evaluate(composite.local, data_post);
        
        if((mode.address_1 & 0x7) == 0x7){
            state = 0;
            composite.stat.parse = 1;
        }else{
            if((mode.address_2 & 0x7) == 0x7){
                state = 1;
                composite.stat.parse = 2;
            }else{
                state = 2 ;
                composite.stat.parse = 3;
            }
        }
        
        LearnNewWords net_word = new LearnNewWords();
        LocalInfo data_net_address = new LocalInfo();
        LocalInfo data_net_phone = new LocalInfo();
        LocalInfo data_net_post = new LocalInfo();
        if(state != 0 ){
            System.out.println("正在查阅信息，请稍后...");            
            net_word.netLearning(address);
            Evaluation.eveluteNetAddress(composite.local,data_net_address, net_word.listLearner, db);
            mode.net_address = Evaluation.evaluteLocalInfo(data_net_address);
            Evaluation.evaluate(composite.local, data_net_address);
            
            Evaluation.evelutePhoneAddress(data_net_phone, net_word.phone, db);
            mode.net_phone = Evaluation.evaluteLocalInfo(data_net_phone);
            //Evaluation.evaluate(composite.local, data_net_phone);

            if(!data_net_phone.province.isEmpty()){
                composite.local.province = data_net_phone.province;
            }
            if(!data_net_phone.city.isEmpty()){
                composite.local.city = data_net_phone.city;
            }
            if(!data_net_phone.county.isEmpty() && data_net_phone.county.indexOf(composite.local.county)== -1){
//                composite.local.county = "";
            }            
            
            Evaluation.evelutePostAddress(data_net_post, net_word.post, db);
            mode.net_post = Evaluation.evaluteLocalInfo(data_net_post);
            Evaluation.evaluate(composite.local, data_net_post);
            
            composite.stat.online = 1;
        }

        int val = Evaluation.evaluteInfo(mode);
        String _val = Evaluation.evaluteStatString(mode);
        System.out.println(val + ":" + _val);
        composite.local.flag = _val + ":" + val;
        DispAddress(composite.local);
//        composite.local.flag = Integer.toBinaryString(composite.local.flag);
        return state;
    }
    
    private void DispAddress(LocalInfo address){
        System.out.print(address.address + ",");
        System.out.print(address.province + ",");
        System.out.print(address.city + ",");
        System.out.print(address.county + ",");
        System.out.print(address.township + ",");
        System.out.print(address.village + ",");
        System.out.print(address.Road + ",");
        System.out.print(address.RoadNo + ",");        
        
        System.out.print(address.building + ",");
        System.out.print(address.floor + "," );
        System.out.println(address.room + "," );
        
        System.out.println(" flag " +  address.flag + " ServiceAddress dispAddress");
    }
    
   public static void main(String[] args){
       try {
           String addrStr = "真光路1288号百联购物广场";
           addrStr = "长宁天山路900号汇金百货(内近娄山关路)";
           addrStr = "天津市蓟县官庄镇恒大金碧天下（近盘山风景区）";
//           addrStr = "天津市津南区小站镇米立方海世界水上乐园(津歧路与盛塘路交汇处)";
 //          addrStr = "黄浦区西藏中路268号来福士广场内(近福州路)";

//           addrStr = "北京经济技术开发区永昌北路9号西门（北京银行东侧，与可口可乐公司隔街相望）";
//           addrStr = "中山大道江汉路happy站台A079号";          
//           addrStr = "天山路900号汇金百货(内近娄山关路)";
 //         addrStr = "南京鼓楼区广州路173号";
 //          addrStr = "广元西路317号金山商务楼";
 //          addrStr = "真光路1288号百联购物广场1楼第一食品商店";
//           addrStr = "松桥支路43号";
//           addrStr = "锦龙支路金凤大厦北侧(公交驾校北侧)";
//           addrStr = "广州路2号龙世中心B1楼(近中山路)";
//           addrStr = "模范马路芦席营28号";
 //          addrStr = "南京江宁汤山镇温泉路9号";
//           addrStr = "浦东新区南泉路1268号(近蓝村路)";
           
//        addrStr = "真光路1288号百联购物广场";
//        addrStr = "解放大道56号景德镇89号青云谱路78号华西村人民路90号";
//         addrStr = "湖南湘西土家族苗族自治州古丈县景德镇青云谱路2-48号华西村双号";
//        addrStr = "天山路900号汇金百货内近娄山关路";//内近娄山关路
//        words = "内近娄山关路";
//        addrStr = "中山大道江汉路happy站台A079号";
//        addrStr = "中山大道happy站台A079号";
//        addrStr = "江汉路happy站台A079号";
//        addrStr = "黄浦区西藏中路268号来福士广场内";
//        words = "锦龙支路金凤大厦北侧";
//        addrStr = "启航城18-23号";
//        addrStr = "大渡河路452号国盛中心B1楼";
//        addrStr = "柳州路427号近漕宝";
//       addrStr = "上海南站地下商场南馆地下一层B区南1-B2号";
//        addrStr = "香花桥街道香大路1379弄16号";
//       addrStr = "南京西路938号";//(南京西路泰兴路口)";
        
 //       addrStr = "向阳河路889号";
//        addrStr = "广元西路317号金山商务楼3楼f座";
//        addrStr = "顺昌路622号216室";
//        addrStr = "广元西路317号金山商务楼3楼f座";
//        addrStr = "浦三路159号三楼近临沂路";
//       addrStr = "南翔镇古猗园路158弄5号401室";
//        addrStr = "南京西路1376号上海商城内(铜仁路)";
        
//        addrStr = "罗山路1609号";
//        addrStr = "南京鼓楼区广州路173号";           
 //        addrStr = "九龙坡区渝州路" ;
          
//          addrStr = "官庄镇恒大金碧天下别墅区内";
          
//          addrStr = "永乐南路39号";
//        addrStr = "南汇观海商场2层";
//        addrStr = "汉中路158号汉中广场17楼";
 //       addrStr = "江苏南京锦江路69号";
//        addrStr = "锦江路69号";

//        addrStr = "江苏南京白下区淮海路68号苏宁电器一楼3C服务中心 ";
 //       addrStr = "江苏南京鼓楼区（原下关区）幕府西路118号人民商场";
//        addrStr = "南街67号";
//        addrStr = "曹安路3818号";
//        addrStr = "天津路316";
//        addrStr = "白下区中山路55号新华大厦33楼A座(近中山东路)";
//        addrStr = "南京白下区中山东路307号(近太平北路)修改/报";
//        addrStr = "江东北路88号 ";
//          addrStr = "交通路176号04幢";
 //         addrStr = "集合村路103号-4 ";
//          addrStr = "东井村39号13幢108室";
 //        addrStr = "南京玄武区龙蟠中路46号(华山饭店对面)";

//           addrStr = "江东北路218号新城市假日广场(龙园南路口)";
//          addrStr = "东城区东直门内大街253号";
//        addrStr = "南京东路800号市百一店东楼17层";
//        addrStr = "鼓楼区南翔庄镇镇江路800号市百一店东楼17层";
//           addrStr = "南京草场门大街96号（中青大厦3楼）";
//            addrStr = "九龙坡区渝州路79-117号渝福建陶城";           
           
           
 //         addrStr = "光华路海福巷1号(北京华联斜对过,红绿灯路口)";
 //        addrStr = "北京市朝阳区安慧里三区6号楼东门三层";
//        addrStr = "宝山区汶水路1555号红星美凯龙Z8018(近沪太路)";
//        addrStr = "昆明路78号";
//        addrStr = "长宁路770号2楼(近Ashely)";
//        addrStr = "普陀区中山北路3856弄中环大厦1911室(近宁夏路)";
//        addrStr = "鼓楼云南北路49号天星翠琅大厦5楼";
//          addrStr = "江苏省南京市新华路362号";
 //         addrStr = "南京市白下区淮海路68号苏宁电器一楼3C服务中心";
 //       addrStr = "坝头洲畔村大山沟永泰路东区";
 //       addrStr = "北京东路24号";
//        addrStr = "苏果社区(台城花园便利店)台城花园07幢";
//        addrStr = "近郊南京市六合区雄州南路288号(南京化工园管委";
//        addrStr = "玄武区卫岗55号(近前线歌舞团)";
 //       addrStr = "近郊栖霞区华电路(近迈皋桥)";
//        addrStr = "黄浦区南京东路819号百联世茂国际广场1楼(近西";
//        addrStr = "近郊浦口区浦珠路1号金宝眼镜城B区101-102";
//        addrStr = "鼓楼区江苏路老菜市8号";
//        addrStr = "长宁区定西路1328号(地铁2号线中山公园站)";
//        addrStr = "浦东新区东方路1881弄东方城市花园79号(近东方)";
//        addrStr = "鼓楼区汉口西路51号(近南京大学)";
 //       addrStr = "西藏中路268号来福士广场7楼(近福州路)";
//        addrStr = "近郊崇明长兴岛长明村12组678号";
 //       addrStr = "唐城街近洪武路";
//        addrStr = "南京市雨花区雨花西路111号（戴斯国际酒店旁）地铁1号线南延线中华门站800米.";
 //       addrStr = "湛江路69号16幢2单元102,202室";
//        addrStr = "中国江苏省南京市中山南路148号";
 //       addrStr = "上海俄罗斯使领馆";
 //       addrStr = "、我要点评、公交驾乘、11.、江苏移动六合分公司营业厅";
//        addrStr = "上海黄浦黄浦江";
//        addrStr = "上海长宁古北新区";
//        addrStr = "上海黄浦黄浦江";
//        addrStr = "上海南汇起凤台";
//       addrStr = "新建西路558号";
//       addrStr = "云南路3号";
//       addrStr = "中山东路378号口岸大厦11楼";
//       addrStr = "建宁路90号 分店2.中国移动(建宁路指定专营店)";
       
//       addrStr = "闵行区广通路66弄2号绿地集团111室";
 //      addrStr = "东山镇金盛路金盛大厦";
       
//       addrStr ="桂林路37号(桂林路桂林西街) 桂林浴室";
//       addrStr = "金沙江西路";
//       addrStr = "上海市石龙路728号(上海长途客运站售票厅旁)";
       addrStr = "上海上海世博会上汽-通用汽车馆";
//       addrStr ="龙蟠路107号(近玄武湖公园)";
//       addrStr = "鼓楼湖北路22号";
       addrStr = "朝天宫堂子街9号";
//       addrStr = "鼓楼湖北路22号";
 //      addrStr = "上海乌拉圭使领馆";
//       addrStr = "汉中门大街迎春里(南京审计学院东大门对面)";
//       addrStr = "北京市朝阳区安慧里三区6号楼东门三层";
//       addrStr ="南京北京东路38号";
//       addrStr = "鼓楼区湖南路步行街狮子桥2号(近湖北路)";
//       addrStr = "东山镇金盛路金盛大厦";
           ServiceAddress addr = new ServiceAddress();
           DSComposite composite = new DSComposite();
           composite.local.address = addrStr;
           LocalInfo _data = new LocalInfo();           
 //          Extractor.parseAddress(addrStr, _data, addr.db);
           addr.parseAddressOfDSComposite(composite);
           /*
        System.out.println("省：" +   _data.province + " 市:" + _data.city + " 县:" + _data.county + " 镇:" +  _data.township + " 村:" +   _data.village );    
        System.out.println("路:" +   _data.Road + " NO:" + _data.RoadNo); 
        System.out.println("楼 B:" +   _data.building  + " FL:" + _data.floor + " ROOM:" + _data.room); 
        System.out.println("参照 R:" +   _data.reference); 
           */
//           DSAddress dsAddr; 
//           dsAddr = addr.parseAddress(addrStr)
//           addr.dispDSAddress(dsAddr);
/////////////           
 //       LocalInfo _data = new LocalInfo();
//        AddressDataBase db = new AddressDataBase();
//        addr.DispAddress(_data); 
       }catch(Exception e){
           System.out.println(e.getMessage());
       }       
   }
}
