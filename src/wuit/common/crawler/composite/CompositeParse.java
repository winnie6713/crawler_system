package wuit.common.crawler.composite;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;
import wuit.common.dictionary.AddressService;
import wuit.common.dictionary.BaseAddress;

/**
 * @description 该类是从每条记录信息中，结合地址字典分拣出详细信息
 * @author weiyunyun
 *
 */
public class CompositeParse {
	private	CompositeConvert convert = new CompositeConvert();
    
    private static AddressService addressDict = new AddressService(); 
    
    private List<String> filterName = new ArrayList<String>();
        
    public CompositeParse(){
        
        filterName.add("公交");
        filterName.add("去看看");
        filterName.add("购买");
        filterName.add("地图");
        filterName.add("公交");
        filterName.add("人均");
        filterName.add("评价");
        filterName.add("团购");
        filterName.add("点评");
        filterName.add("提供");
        filterName.add("指南");
        filterName.add("地址");
        filterName.add("详细");
        filterName.add("优惠");
        filterName.add("怎么走");
        filterName.add("在哪");
        filterName.add("图片");
        
    }
    
    private void extractProvinceCityCountyName(DSComposite info){
        List<BaseAddress> items = new ArrayList<BaseAddress>();
        List<String> itemNames = new ArrayList<String>();
        
        extractCityNameByAddress(info.local.address,items,itemNames);        
        
        info.phone_code = convert.match(info.phone,"0\\d{2,3}(?=\\-)");
        info.phone_number = convert.match(info.phone,"(?<=\\-)\\d{6,10}");
        
        extractCityNameByPhoneCode(info.phone_code,items);        
        //info.province = _DSAddr.district.province;
        
        //确定省份
        Map<String,Integer> mapProvince = new HashMap<String,Integer>();
        for(int n=0;n<items.size();n++){
            if(mapProvince.containsKey(items.get(n).province)){
                int val = mapProvince.get(items.get(n).province) + items.get(n).level;
                mapProvince.put(items.get(n).province, val);
            }else{
                mapProvince.put(items.get(n).province, items.get(n).level);
            }
//            System.out.println(items.get(n).province+"|"+items.get(n).city+"|"+items.get(n).county+"|"+items.get(n).level);
        }
        Set set  = mapProvince.entrySet();
        Iterator it = set.iterator();
        String province = "";
        int level = 0;
        while(it.hasNext()){
            Entry<String,Integer> body = (Entry<String,Integer>)it.next();
            if(body.getValue()>level){
                province = body.getKey();
                level = body.getValue();
            }else{
                if(body.getValue() == level){
                    if(verifyAddressNameByCollectTitle(province,info.collection.title))
                        continue;
                    if(verifyAddressNameByCollectTitle(body.getKey(),info.collection.title)){
                        province = body.getKey();
                        continue;
                    }
                    province = province+ "|"+body.getKey();
                }
            }
        }
        
        
        info.local.province = province;
//        System.out.println(province);
        
        Map<String,Integer> mapCity = new HashMap<String,Integer>();
        for(int n=0;n<items.size();n++){
            if(items.get(n).province.equals(province)){
                if(mapCity.containsKey(items.get(n).city)){
                    int val = mapCity.get(items.get(n).city) + items.get(n).level;
                    mapCity.put(items.get(n).city, val);
                }else{
                    mapCity.put(items.get(n).city, items.get(n).level);
                }            
            }
        }        
        set  = mapCity.entrySet();
        it = set.iterator();
        String city = "";
        level = 0;
        while(it.hasNext()){
            Entry<String,Integer> body = (Entry<String,Integer>)it.next();
            if(body.getValue()>level)
                city = body.getKey();
        }
        info.local.city = city;        
        
        Map<String,Integer> mapCounty = new HashMap<String,Integer>();
        for(int n=0;n<items.size();n++){
            if(items.get(n).province.equals(province)&&items.get(n).city.equals(city)&& !items.get(n).county.isEmpty()){
                if(mapCounty.containsKey(items.get(n).county)){
                    int val = mapCounty.get(items.get(n).county) + items.get(n).level;
                    mapCounty.put(items.get(n).county, val);
                }else{
                    mapCounty.put(items.get(n).county, items.get(n).level);
                }            
            }
        }        
        set  = mapCounty.entrySet();
        it = set.iterator();
        String county = "";
        level = 0;
        while(it.hasNext()){
            Entry<String,Integer> body = (Entry<String,Integer>)it.next();
            if(body.getValue()>level)
                county = body.getKey();
        }
        info.local.county = county;    
        
        
        
        
        String value = info.local.address;
        value = value.replace(info.local.province, "");
        value = value.replace(info.local.city, "");
        value = value.replace(info.local.county, "");
        for(int i=0;i<itemNames.size();i++){
            if(info.local.province.indexOf(itemNames.get(i))>=0){
                value = value.replace(itemNames.get(i), "");
                continue;
            }
            if(info.local.city.indexOf(itemNames.get(i))>=0){
                value = value.replace(itemNames.get(i), "");
                continue;
            }
            if(info.local.county.indexOf(itemNames.get(i))>=0){
                value = value.replace(itemNames.get(i), "");
                continue;
            }
        }
//        info.district = value;
        
//        info.reference = convert.match(value,"(?<=\\_近)[^\\_]+(?=\\_*)");
//        value = value.replace(info.reference, "");
        //		District = match(District,".+(?="+ info.reference +")").replace("_", "");
        
        
        String val = convert.match(value,"标签[^、]+?、");
        if(val.length()>0)
            info.label = val;
        value = value.replace(val, "");
        val = convert.match(value,"简介[^、]+?、");
        if(val.length()>0)
            info.Abstract = val;
        /*
        value = value.replace(val, "");
        val = convert.match(value,"[\\d\\.]+公里|[\\d\\.]+米");
        if(val.length()>0)
            info.distance = val;
        value = value.replace(val, "");
        val = convert.match(value,"\\d{3}-\\d{7,}");
        if(val.length()>0)
            info.phone = val;
        value = value.replace(val, "");
        val = convert.match(value,"(?<=邮编:)\\d{6}|\\d{6}");
        if(val.length()>0)
            info.postcode = val;
        value = value.replace(val, ""); 
        
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.clear();info.zone="";
        for(int i=0;i<addressDict.zoneKeys.size();i++){            
            String filter = addressDict.keyWords + addressDict.zoneKeys.get(i);            
            list.clear();
            convert.matchValues(value,filter,list);
            for(int j=0;j<list.size();j++){
                info.zone = list.get(j).value;
                value = value.replace(info.zone, "");
            }
        }
        list.clear();info.zone_number="";
        convert.matchValues(value,"\\d{1,}号",list);
        for(int i=0; i<list.size();i++){
            if(list.get(i).start == 0){
                info.zone_number = list.get(i).value;
                value = value.replace(info.zone_number, "");
            }
        }
        
        info.road="";
        for(int i=0;i<addressDict.streetKeys.size();i++){
            if(!info.road.isEmpty())
                break;
            String filter = addressDict.keyWords + addressDict.streetKeys.get(i); 
            list.clear();
            convert.matchValues(value,filter,list);
            for(int j=0;j<list.size();j++){
                info.road += list.get(j).value;
                value = value.replace(list.get(j).value, "");
            }
        }
        list.clear();info.road_number="";
        convert.matchValues(value,"\\d{1,}[号弄]",list);
        for(int i=0; i<list.size();i++){
            if(list.get(i).start == 0){
                info.road_number = list.get(i).value;
                value = value.replace(info.road_number, "");
            }
        }
        list.clear();info.house = "";
        for(int i=0;i<addressDict.houseKeys.size();i++){
            if(!info.house.isEmpty())
                break;
            String filter = addressDict.keyWords + addressDict.houseKeys.get(i);            
            list.clear();
            convert.matchValues(value,filter,list);
            for(int j=0;j<list.size();j++){
                info.house = list.get(j).value;
                value = value.replace(list.get(j).value, "");
            }
        }
        list.clear();info.house_number = "";
        convert.matchValues(value,"\\d{1,}号",list);
        for(int i=0; i<list.size();i++){
            if(list.get(i).start == 0){
                info.house_number = list.get(i).value;
                value = value.replace(info.house_number, "");
            }
        }
        list.clear();info.storey = "";
        convert.matchValues(value,"[a-zA-Z]*?\\d{1,}[楼层Ff]",list);
        for(int i=0; i<list.size();i++){
                info.storey = list.get(i).value;
                value = value.replace(info.storey, "");
        }  
        */
    }
    
    /**
     *  通过网页的标题来验证省市县的信息
     * @param name
     * @param title
     * @return 
     */
    
    
    private static boolean verifyAddressNameByCollectTitle(String name,String title){
        int index = title.indexOf(name);
        if(index>=0)
            return true;
        for(int i=0;i<addressDict.KeysCity.size();i++){
            name = name.replace(addressDict.KeysCity.get(i), "");
        }
        index = title.indexOf(name);
        if(index>=0)
            return true;
        return false;
    }
    

    //分拣数据
    public void extractRecord(DSComposite info){
        if(info == null)
            return;
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        info.collection.date = sdf.format(stamp);
        
        BaseAddress _info = new BaseAddress("");
        //info.address = info.address.replace("(", "_").replace(")", "_");
        
        int index = -1;
//        if(info.address.indexOf(":")==0)
//            info.address = info.address.substring(1,info.address.length());
        
//        parseFieldName(info);                                                        //对名称域进行清理，并提取标签，简介，参考物距离等信息
        
//        parsePreAddress(info);
        extractProvinceCityCountyName(info);
//        extractAddress(info);

//        parseDistrict(info.district,info);
//        parseFieldName(info);
        
    }
    
    
    public void extractCityNameByPhoneCode(String phoneCode,List<BaseAddress> items){
        List<String> vals = addressDict.mapPhone.queryName(phoneCode);
        for(int i=0;i<vals.size();i++){
            BaseAddress base = new BaseAddress(vals.get(i));
            base.level = 6;
            items.add(base);
        }
    }
    
    
    /**
     * 
     * @param address
     * @param items 
     */
    
    public void extractCityNameByAddress(String address,List<BaseAddress> items,List<String> itemNames){
//        DSAddress dsAddr = srvAddress.parseAddress(address);
//        srvAddress.dispDSAddress(dsAddr);
        
        //通过关键字从地址信息中提取省市县名称
        
        int level = -1;
        for(int i=0;i<addressDict.KeysCity.size();i++){
            List<KeyValue> list = new ArrayList<KeyValue>();
            String filter = addressDict.keyWords + addressDict.KeysCity.get(i);
            convert.matchValues(address,filter,list);
            for(int j=0;j<list.size();j++){
                String name = list.get(j).value;
                if(name.length()>4){
                    String value = name;
                    while(name.length()>4){
                        name = value.substring(0,2);
                        List<String> val = addressDict.mapNameAddress.queryName(name);
                        for(int n=0;n<val.size();n++){
                            itemNames.add(name);
                            BaseAddress base = new BaseAddress(val.get(n));
                            base.level = 4 - base.level;
                            if(level == -1){
                                level = base.level;
                                items.add(base);
                            }else{
                                if(base.level <level){
                                    level = base.level;
                                    items.add(base);
                                }
                            }
                        }
                        value = value.replace(name, "");
                    }
                    List<String> val = addressDict.mapNameAddress.queryName(value);
                    for(int n=0;n<val.size();n++){
                        BaseAddress base = new BaseAddress(val.get(n));
                        base.level = 4 - base.level;
                        itemNames.add(value);
                        if(level == -1){
                            level = base.level;
                            items.add(base);
                        }else{
                            if(base.level <level){
                                level = base.level;
                                items.add(base);
                            }
                        }
                    }                    
                }else{
                    List<String> val = addressDict.mapNameAddress.queryName(name);
                    for(int n=0;n<val.size();n++){
                        itemNames.add(name);
                        BaseAddress base = new BaseAddress(val.get(n));
                        base.level = 4-base.level;
                        list.get(j).order = base.level;
                        if(level == -1){
                            level = base.level;
                            items.add(base);
                        }else{
                            if(base.level <level){
                                level = base.level;
                                items.add(base);
                            }
                        }
                    }
                 }
            }
        }
        //清理地址信息中已匹配出来的省市县
        String _val = address;
        String value = address;
        
        for(int i=0;i<itemNames.size();i++){
            _val = _val.replace(itemNames.get(i), "");
        }
        
 //       _val = value;
        //在地址信息中寻找可能存在的省市县名信息
        while(_val.length() >4){
            String name = _val.substring(0,2);
            List<String> val = addressDict.mapNameAddress.queryName(name);
            for(int n=0;n<val.size();n++){
                itemNames.add(name);
                BaseAddress base = new BaseAddress(val.get(n));
                base.level = 4 - base.level;
                if(level == -1){
                    level = base.level;
                    items.add(base);
                }else{
                    if(base.level <level){
                        level = base.level;
                        items.add(base);
                    }
                } 
            }
            _val = _val.replace(name, "");
        }
    }
    

    
    /**
     * （1）提取参照物信息
     * 		(a) 提取 —— 标签信息
     * 		(b) 提取 —— 简介信息
     * 		(c) 提取 —— 参考位置距离信息
     * 		(d) 提取 —— 电话号码
     * 		(e) 提取 —— 邮编
     * （2）提取县级信息
     * （3）提取区域信息
     * （4）提取建筑物信息
     * （5）提取门牌号
     * （6）提取楼层
     * （7）提取楼层房间
     * (8)1:province;2:city;3:county;4:district;5:zone;6:zone_number;7:house;8:house_number;9:story;10:room;11:position
     */
    
    /*
    public void parsePreAddress(DSComposite info){
        String Address = info.address;
        List<KeyValue> items = new ArrayList<KeyValue>();
        info.reference = convert.match(Address,"(?<=\\_近)[^\\_]+(?=\\_*)");
        Address = Address.replace(info.reference, "");
        //		District = match(District,".+(?="+ info.reference +")").replace("_", "");
        
        String val = convert.match(Address,"标签[^、]+?、");
        if(val.length()>0)
            info.label = val;
        Address = Address.replace(val, "");
        val = convert.match(Address,"简介[^、]+?、");
        if(val.length()>0)
            info.Abstract = val;
        Address = Address.replace(val, "");
        val = convert.match(Address,"[\\d\\.]+公里|[\\d\\.]+米");
        if(val.length()>0)
            info.distance = val;
        Address = Address.replace(val, "");
        val = convert.match(Address,"\\d{3}-\\d{7,}");
        if(val.length()>0)
            info.phone = val;
        Address = Address.replace(val, "");
        val = convert.match(Address,"(?<=邮编:)\\d{6}|\\d{6}");
        if(val.length()>0)
            info.postcode = val;
        Address = Address.replace(val, "");
        info.address = Address;
        //*
        for(int i=0;i<addressDict.streetKeys.size();i++){
            String filter = addressDict.keyWords + addressDict.streetKeys.get(i);
            val = CompositeConvert.match(District,filter);
            if(!val.equals(""))
                break;
        }
        info.district = val;
        
        District = District.replace(val, "");
        val = CompositeConvert.match(District,"\\d{1,}号|\\d{1,}弄");
        if(val.length()>0 && District.indexOf(val)==0){
            info.zone = val;
            District = District.replace(val, "");
        }
        //info.house = District;
        val = CompositeConvert.match(District,"\\d{1,}号");
        if(val.length()>0 ){
            if(District.indexOf(val)>=0){
                info.house = District.substring(0,District.indexOf(val));
                District = District.replace(info.house, "");
            }
            info.house_number = val;
            District = District.replace(val, "");
        }
        
        val = CompositeConvert.match(District,"\\d{1,}楼|\\d{1,}\\-\\d{1,}楼|\\d{1,}\\-\\d{1,}层|\\d{1,}层");	
        if(val.length()>0 ){
            if(District.indexOf(val)>=0 && info.house.equals("")){
                info.house = District.substring(0,District.indexOf(val));
                District = District.replace(info.house, "");
            }
            info.storey = val;
            District = District.replace(val, "");
        }
        
        val = CompositeConvert.match(District,"\\d{1,}号|\\d{1,}室");
        if(val.length()>0 ){
            info.room = val;
            District = District.replace(val, "");
        }
        /// 
    }
    */

    /////////////////////////////////////
    /**
     * (1) 清理名称域
     * (2) 提取 —— 标签信息
     * (3) 提取 —— 简介信息
     * (4) 提取 —— 参考位置距离信息
     * (5) 提取 —— 电话号码
     * (6) 提取 —— 邮编
     */
    
    /*
    public void parseFieldName(DSComposite info){
        info.title = info.name;
        String val = "";//convert.match(info.name,"(?<=\\d{1,}).*|(?<=\\d{1,}\\.、).*|(?<=\\d{1,}、).*");//convert.match(info.name,"(?<=\\d{1,}\\.、).*|(?<=\\d{1,}、).*");
        if (val.length()>0){
            info.name = val;
        }
        int index = -1;
        val = convert.match(info.name,"标签[^、]+?、");
        if (val.length()>0){
            info.label = val;
            index = info.name.indexOf(val);
            info.name = info.name.substring(0,index) + info.name.substring(index + val.length(),info.name.length());
        }

        String strVal = info.name;
        val = convert.match(strVal,"营业时间:\\d{2}:\\d{2}\\-\\d{2}:\\d{2}");
        if (val.length()>0){
            info.time_service = val;
            info.name = info.name.replace(val, "");
        }
        
        val = convert.match(info.name,"简介[^、]+?、");
        if (val.length()>0){
            info.Abstract = val;
            index = info.name.indexOf(val);
            info.name = info.name.substring(0,index) + info.name.substring(index + val.length(),info.name.length());
        }
        val = convert.match(info.name,"[\\d\\.]+公里|[\\d\\.]+米");
        if (val.length()>0){
            info.distance = val;
            index = info.name.indexOf(val);
            info.name = info.name.substring(0,index) + info.name.substring(index + val.length(),info.name.length());
        }
        val = convert.match(info.name,"\\d{3}-\\d{1,}");
        if (val.length()>0){
            info.phone = val;
            index = info.name.indexOf(val);
            info.phone_code = convert.match(val,"\\d{3}(?=\\-)");
            info.name = info.name.substring(0,index) + info.name.substring(index + val.length(),info.name.length());
        }
        val = convert.match(info.name,"(?<=邮编:)\\d{6}|\\d{6}");
        if (val.length()>0){
            info.postcode = val;
            if (!info.name.equals("")){
                index = info.name.indexOf(val);
                if (index>=0)
                    info.name = info.name.substring(0,index) + info.name.substring(index + val.length(),info.name.length());
            }
        }
        String[] vals = info.name.split("、");
        if(vals.length>0){
            for(int i=0;i<vals.length;i++){
                for(int j=0;j<filterName.size();j++){
                  if(vals[i].indexOf(filterName.get(j))>=0)
                      info.name = info.name.replace(vals[i],"");
                }
            }
        }
        vals = info.name.split(",");
        if(vals.length>0){
            for(int i=0;i<vals.length;i++){
                for(int j=0;j<filterName.size();j++){
                  if(vals[i].indexOf(filterName.get(j))>=0)
                      info.name = info.name.replace(vals[i],"");
                }
            }
        }        
//        info.name = info.name.replaceAll("\\、{1,}|:|\\d{1,}.", "");

    }
    */
    ///////////////////////
    /**
     *从给定的文本数据中，按给定的字段提取规则，提取composite对象的数据
     *content:页面的文本数据
     *mapFields:从文本数据中提取每个字段信息的规则集
     *listItems：提取compositeInfo对象数组
     */
    
    /*
    public void extractRecordsFromContent(String content, DSCrawlerPageInfo url, List<KeyValue> fieldRules,List<DSComposite> listItems){
        //缓存所有匹配的词，二维，hash表以词的名称作为关键字，hash值为所匹配的数组
        Map<String,List<KeyValue>> mapList = new HashMap<String,List<KeyValue>>();
        List<KeyValue> list =  new ArrayList<KeyValue>();
        List<KeyValue> listItem =  new ArrayList<KeyValue>();
        //以设置的匹配关键字段集合，在页面中匹配所有的词条数，放入数组，并按词条的开始位置排序
        for(int i=0;i<fieldRules.size();i++){
            list = new ArrayList<KeyValue>();
            convert.matchValues(content,fieldRules.get(i).key,fieldRules.get(i).value, list);
            if(list.size()>0){
                mapList.put(fieldRules.get(i).key, list);
                listItem.addAll(list);
            }
        }
        
        KeyValueSort sort = new KeyValueSort();
        Collections.sort(listItem,sort);
        
        int begin = -1;
        DSComposite _item = null;
        for(int i=0;i<listItem.size();i++){
            if(listItem.get(i).key.equals("name")){
                if (begin == -1)
                    begin =1;
                else
                    listItems.add(_item);
                _item = new DSComposite();
                _item.collect_url = url.url;
                _item.collect_title = url.title;
                _item.collect_dns = url.dns;
                _item.collect_description = url.description;
                _item.collect_keywords = url.keyWords;
                
                _item.name =  listItem.get(i).value;
            }
            if(begin == 1){
                if(listItem.get(i).key.equals("address")){
                    _item.local.address = listItem.get(i).value;
                }
                if(listItem.get(i).key.equals("label")){
                    _item.label = listItem.get(i).value;
                }
                if(listItem.get(i).key.equals("phone")){
                    _item.phone = listItem.get(i).value;
                }
                if(listItem.get(i).key.equals("phone_code")){
                    _item.phone_code = listItem.get(i).value;
                }
                if(listItem.get(i).key.equals("remark")){
                    _item.remark = listItem.get(i).value;
                }
            }
        }
        if(!_item.name.isEmpty()&& !_item.address.isEmpty())
            listItems.add(_item);
    }
    */
   /////////////////////////////////////////////////////////////////
   public static void main(String[] args){   
      CompositeConvert convert = new CompositeConvert();
      CompositeParse parse = new CompositeParse();
      
      String str = "name:上海永达汽车萨博销售服务有限公司(浦西店);address:上海徐汇区肇嘉浜路446弄伊泰利大厦1号楼14楼;province:;city:;county:;district:;zone:;nomber:;phone:021-1234567;phonecode:;content:;label:;url:;date:;";
//      str = "label:;name:福倍乐母婴生活馆、;phone:;phone_code:;phone_number:;postcode:;province:上海市;city:辖区;county:宝山区;address:宝山区泰和西路3499弄62号;district:泰和西路;zone:3499弄;house:;house_number:62号;storey:;room:;position:;reference:;distance:3.5公里;direction:;Abstract:;remark:;content:;time_service:;date_collect:2013/07/17 13:11:24;date_publish:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items"; 
//      str = "abel:;name:可博电脑;phone:021-59794319;phone_code:0552;phone_number:59794319;postcode:;province:安徽省;city:蚌埠市;county:固镇县;address:青浦区青重固镇赵重公路2935弄54号_近泉中路_;district:青重赵重公路;zone:2935弄;house:;house_number:54号;storey:;room:;position:;reference:泉中路;distance:;direction:;Abstract:;remark:;content:;time_service:;date_collect:2013/07/20 00:09:53;date_publish:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:";
//      str = "label:;name:南新雅大酒店;phone:400-067-6128;phone_code:020;phone_number:;postcode:;province:广东省;city:广州市;county:黄埔区;address:黄埔区九江路700号_广西路口_;district:九江路;zone:700号;house:;house_number:;storey:;room:;position:;reference:;distance:3.9公里;direction:;Abstract:;remark:;content:;time_service:;date_collect:2013/07/21 01:16:48;date_publish:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";

 //     str = "label:;name:北京顺通腾达司机技术学校有限公司;phone:;phone_code:;phone_number:;postcode:;province:北京市;city:;county:;address:北京顺义海龙商业街7号楼14号;district:北京顺义海龙商业街;zone:7号;house:楼;house_number:14号;storey:;room:;position:;reference:;distance:;direction:;Abstract:;remark:;content:;collect_date:2013/08/05 23:27:58;collect_url:http://bj.city8.com/canyinfuwu/2248446_070OR.html;collect_dns:;collect_title:思加图(复兴门百盛店)，北京思加图(复兴门百盛店)的电话_地址_北京地图 - 城市吧街景地图;collect_keywords:;collect_description:;date_publish:;time_service:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";
 //           str = "label:;name:郑州市河南金博大购物中心;phone:;phone_code:;phone_number:;postcode:;province:河南省;city:郑州市;county:二七区;address::郑州市二七路200号河南金博大购物中心;district::二七路;zone:200号;house:;house_number:;storey:;room:;position:;reference:;distance:;direction:;Abstract:;remark:;content:;collect_date:2013/08/09 08:17:20;collect_url:http://pp.ppsj.com.cn/Ama/;collect_dns:;collect_title:Ama_Ama品牌专区;collect_keywords:Ama,,Ama品牌专区;collect_description:AmaAma，一个起源于180多年前的意大利手工饰品家族品牌，于1998年进入中国。在AMA的珠宝世界里，没有肤浅的模仿者，也没有潮流的盲从者；摒弃了繁复驳杂的设计语言，也摒弃了粗糙劣质的工美主义，只有抽象而浪漫的雅致形象，只有精致而清新的简洁线条。“理性与情感的交融”，“时尚与经典的......品牌世家网您提供详细的Ama资讯;date_publish:;time_service:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";
    
  //    str = "label:;name:上海市上海百联世茂国际广场;phone:;phone_code:;phone_number:;postcode:;province:江苏省;city:南京市;county:;address:上海市南京东路819号百联世茂国际广场;district:南京东路;zone:;house:;house_number:819号;storey:;room:;position:;reference:;distance:;direction:;Abstract:;remark:;content:;collect_date:2013/08/09 08:17:20;collect_url:http://pp.ppsj.com.cn/Ama/;collect_dns:;collect_title:Ama_Ama品牌专区;collect_keywords:Ama,,Ama品牌专区\";collect_description:AmaAma，一个起源于180多年前的意大利手工饰品家族品牌，于1998年进入中国。在AMA的珠宝世界里，没有肤浅的模仿者，也没有潮流的盲从者；摒弃了繁复驳杂的设计语言，也摒弃了粗糙劣质的工美主义，只有抽象而浪漫的雅致形象，只有精致而清新的简洁线条。“理性与情感的交融”，“时尚与经典的......品牌世家网您提供详细的Ama资讯\";date_publish:;time_service:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";
 //     str = "label:;name:西安市西安世纪金花;phone:;phone_code:;phone_number:;postcode:;province:江苏省;city:南京市;county:鼓楼区;address::西安市西大街1号钟鼓楼广场世纪金花;district:;zone:;house:;house_number:;storey:;room:;position:;reference:;distance:;direction:;Abstract:;remark:;content:;collect_date:2013/08/09 08:17:20;collect_url:http://pp.ppsj.com.cn/Ama/;collect_dns:;collect_title:Ama_Ama品牌专区;collect_keywords:Ama,,Ama品牌专区\";collect_description:AmaAma，一个起源于180多年前的意大利手工饰品家族品牌，于1998年进入中国。在AMA的珠宝世界里，没有肤浅的模仿者，也没有潮流的盲从者；摒弃了繁复驳杂的设计语言，也摒弃了粗糙劣质的工美主义，只有抽象而浪漫的雅致形象，只有精致而清新的简洁线条。“理性与情感的交融”，“时尚与经典的......品牌世家网您提供详细的Ama资讯\";date_publish:;time_service:;active_title:;active_date_begin:;active_date_end:;active_time:;active_items:;";
//      List<BaseAddress> items = new ArrayList<BaseAddress>();
//      parse.extractCityNameByAddress("青浦区青重固镇赵重公路",items);
//      parse.extractCityNameByPhoneCode("027",items);
      
//      List<BaseAddress> items = new ArrayList<BaseAddress>();
//      CompositeParse.extractCityNameByAddress(str, items);
      String addr = "上海徐汇区肇嘉浜路446弄伊泰利大厦1号楼14楼";
      addr = "锦龙支路金凤大厦北侧(公交驾校北侧)";
      addr = "松桥支路43号";
      addr = "五红路附近";
      addr = "江北区建新东路万丰花园北侧(金泰成灯饰市场北";
      addr= "渝州路79-117号渝福建陶城附114号(彩电";
      parse.extractCityNameByAddress(addr, null, null);
      
      str = str.replace("::", ":");
      
      DSComposite info = new DSComposite();
//      convert.StringToDSComposite(str, info);
      parse.extractRecord(info);
      
//      str = convert.DSCompositeToString( info);
      System.out.println(str);
//      for(int i=0;i<items.size();i++){
//          System.out.println(items.get(i).province+"|"+items.get(i).city+"|"+items.get(i).county+"|"+ items.get(i).level);
//      }
      
      /*
      DSComposite info = new DSComposite();
      CompositeConvert.StringToDSComposite(str, info);
      parse.extractRecord(info);
      */
      //System.out.println(CompositeConvert.DSCompositeToString(info));
   }
}
