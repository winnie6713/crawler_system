/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lxl
 */
public class AddressService {
    public MapNameAddress mapNameAddress =  new MapNameAddress();    // dbPostAddress.getPostAddressMap();
    public MapPhoneAddress mapPhone = new MapPhoneAddress();
    public List<String> KeysCity = new ArrayList<String>();                  //省、市、县三级关键字表
    
//    public List<String> listAddressKeys = new ArrayList<String>();
    
    public List<String> streetKeys = new ArrayList<String>();
    public List<String> zoneKeys = new ArrayList<String>();
    public List<String> houseKeys = new ArrayList<String>();
    public String keyWords = "[^省市盟自治区自治州地区县路街幢场厦道心城馆店号楼室弄巷乡镇村]+?";
    
    public AddressService(){
        mapNameAddress.InitializeChina();
        
        KeysCity.add("省");
        KeysCity.add("市");
        KeysCity.add("自治区");
        KeysCity.add("地区");
        KeysCity.add("区");
        KeysCity.add("自治州");        
        KeysCity.add("自治县");
        KeysCity.add("县");
        
        /*
        listAddressKeys.add("省");
        listAddressKeys.add("市");
        listAddressKeys.add("自治区");
        listAddressKeys.add("地区");
        listAddressKeys.add("区");
        listAddressKeys.add("盟");
        listAddressKeys.add("自治州");
        listAddressKeys.add("县");
        
        listAddressKeys.add("路");
        listAddressKeys.add("街");
        listAddressKeys.add("楼");
        listAddressKeys.add("幢");
        listAddressKeys.add("广场");
        listAddressKeys.add("大厦");
        listAddressKeys.add("商厦");
        listAddressKeys.add("道");
        listAddressKeys.add("大道");
        listAddressKeys.add("中心");
        listAddressKeys.add("号楼");
        listAddressKeys.add("城");
        listAddressKeys.add("馆");
        listAddressKeys.add("店");
        listAddressKeys.add("号");
        listAddressKeys.add("室");
        listAddressKeys.add("弄");
        listAddressKeys.add("巷");
        listAddressKeys.add("乡");
        listAddressKeys.add("镇");
        listAddressKeys.add("村");
        */
//        listCountyKeys.add("区");
//        listCountyKeys.add("自治县");
//        listCountyKeys.add("县");
        
        streetKeys.add("路");
        streetKeys.add("街");
        streetKeys.add("道");
        streetKeys.add("弄");
        
        zoneKeys.add("城");
        zoneKeys.add("镇");
        zoneKeys.add("乡");
        zoneKeys.add("村");

        houseKeys.add("广场");
        houseKeys.add("大厦");
        houseKeys.add("商厦");
        houseKeys.add("中心");
        houseKeys.add("城");
        houseKeys.add("馆");
        houseKeys.add("楼");
        houseKeys.add("幢");        
    }
    
    public static void main(String[] args){
        AddressService service = new AddressService();
        List<String> val = service.mapNameAddress.queryName("虹口区");
        for(int i=0;i<val.size();i++){
            BaseAddress addr = new BaseAddress(val.get(i));
            System.out.println(val.get(i));
        }
    }
}
