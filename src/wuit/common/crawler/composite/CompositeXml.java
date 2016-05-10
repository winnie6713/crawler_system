package wuit.common.crawler.composite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import wuit.common.doc.FileIO;

public class CompositeXml {
    class Collect{
            public String title="";
            public String url = "";
            public String dns = "";
            public String IP = "";
            public String date = "";
            public String keyword="";
            public String description="";
    }
    class Address{
            public String value="";
            public String province="";
            public String city="";
            public String county="";
            public String district="";
            public String zone="";
            public String zone_no = "";
            public String house="";
            public String house_no="";
            public String storey="";
            public String room = "";
            public String position = "";
    }
	
    class Phone{
            public String value="";
            public String code="";
            public String number;
    }
	
    class Date{
            public String publish = "";
            public String service = "";
    }
	
    class Reference{
            public String body="";		
            public String distance ="";

            public String direction="";
    }
	
    class Active{
            public String title="";
            public String date_begin="";
            public String date_end="";
            public String time_service="";
            public String content;
    }
	
    class Composite{
            public String _name = "";
            public String _label = "";
            public String _postcode = "";
            public String _abstract = "";
            public String _time_service = "";
            public Collect _collect = new Collect();
            public Address _address = new Address();
            public Phone _phone = new Phone();
            public Date _date = new Date();
            public Reference _reference = new Reference();
            public Active _active = new Active();
            public String _content;
            public String _remark;
    }
	
    private void DSCompositeToCompositeXml(DSComposite DsComposite,Composite composite){
            composite._name = DsComposite.name;
            composite._label = DsComposite.label;
            composite._postcode = DsComposite.postcode;
            composite._content = DsComposite.content;
            composite._remark = DsComposite.remark;

            composite._address.value = DsComposite.local.address;
            composite._address.province = DsComposite.local.province;
            composite._address.city= DsComposite.local.city;
            composite._address.county = DsComposite.local.county;
//            composite._address.district = DsComposite.local.village;
//            composite._address.zone = DsComposite.local.villageNo;
//            composite._address.zone_no = DsComposite.local.zone_number;
//            composite._address.house = DsComposite.local.house;
//            composite._address.house_no = DsComposite.local.house_number;
//            composite._address.storey = DsComposite.local.storey;
//            composite._address.room = DsComposite.local.room;
//            composite._address.position = DsComposite.local.position;

//            composite._reference.distance = DsComposite.distance;
//            composite._reference.body = DsComposite.reference;
//            composite._reference.direction = DsComposite.direction;			

            composite._collect.date = DsComposite.collection.date;
            composite._collect.url = DsComposite.collection.url;
            composite._collect.dns = DsComposite.collection.dns;
            composite._collect.IP = DsComposite.collection.IP;
            composite._collect.title = DsComposite.collection.title;
            composite._collect.keyword = DsComposite.collection.keywords;
            composite._collect.description = DsComposite.collection.description;
            

            composite._date.publish = DsComposite.date_publish;			

            composite._phone.value = DsComposite.phone;
            composite._phone.code = DsComposite.phone_code;
            composite._phone.number = DsComposite.phone_number;

            composite._active.title = DsComposite.active.title;
            composite._active.date_begin = DsComposite.active.date_begin;
            composite._active.date_end = DsComposite.active.date_end;
            composite._active.time_service = DsComposite.active.active_time;
            composite._active.content = DsComposite.active.content;		
    }
	
	//将DSComposite数据对象集转换成Composite对象集
    /*
    private void DSCompositesToCompositeXml(List<DSComposite> listDSComposite,List<Composite> listComposite){
        for (int i=0;i<listDSComposite.size();i++){
            Composite composite = new Composite();
            DSCompositeToCompositeXml(listDSComposite.get(i),composite);
            listComposite.add(composite);
        }
    }
*/
    public void DSCompositesMapToXml(String savePath,String saveFile,Map<String,DSComposite> mapDSComposite){
        if(mapDSComposite.size() == 0)
            return;
        List<Composite> listComposite = new ArrayList<Composite>();
        Set set = mapDSComposite.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Entry<String,DSComposite> body = (Entry<String,DSComposite>)it.next();
            Composite composite = new Composite();
            DSCompositeToCompositeXml(body.getValue(),composite);
            listComposite.add(composite);            
        }

        Document document = generateDocumentByMethod(listComposite);
        try {
            // 美化格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            /// 缩减格式
            //OutputFormat format = OutputFormat.createCompactFormat();
            // 指定XML编码
            // format.setEncoding("GBK");
            FileIO.createDir(savePath);
            XMLWriter output = new XMLWriter(new FileWriter(savePath + saveFile+".xml"));
            
            output.write(document);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    
    
	//将DSComposite数据对象集转换成xml文件
    
    public void DSCompositesListToXml(String savePath,String saveFile,List<DSComposite> listDSComposite){
        if(listDSComposite.size() == 0)
            return;
        List<Composite> listComposite = new ArrayList<Composite>();
        for (int i=0;i<listDSComposite.size();i++){
            Composite composite = new Composite();
            DSCompositeToCompositeXml(listDSComposite.get(i),composite);
            listComposite.add(composite);
        }
        Document document = generateDocumentByMethod(listComposite);
        try {
            // 美化格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            /// 缩减格式
            //OutputFormat format = OutputFormat.createCompactFormat();
            // 指定XML编码
            // format.setEncoding("GBK");
            XMLWriter output = new XMLWriter(new FileWriter(savePath + saveFile+".xml"), format);
            output.write(document);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//将Composite对象集转换为xml 文档
    private Document generateDocumentByMethod(List<Composite> listComposite) {
        Document document = DocumentHelper.createDocument();
        Map<String, String> inMap = new HashMap<String, String>();
        inMap.put("type", "text/xsl");
        // root element
        Element root = document.addElement("composites");
        for (int i=0;i<listComposite.size();i++){
            addNodeCompositesByComopsites(root,listComposite.get(i),i+"");
        }
        return document;
    }
  
    private void addNodeCompositesByComopsites(Element root,Composite composite,String id){
        Element EleComposite = root.addElement("COMPOSITE");
        EleComposite.addAttribute("id",id);
        EleComposite.addAttribute("name",composite._name);
        EleComposite.addAttribute("label",composite._label);        
        EleComposite.addAttribute("service",composite._time_service);
        EleComposite.addAttribute("publish",composite._date.publish);
        
        Element ElePhone = EleComposite.addElement("PHONE");
        ElePhone.setText(composite._phone.value);
        ElePhone.addAttribute("code",composite._phone.code);
        ElePhone.addAttribute("no",composite._phone.number);         
        
        Element EleAddress = EleComposite.addElement("ADDRESS");
        EleAddress.addAttribute("province",composite._address.province);
        EleAddress.addAttribute("city",composite._address.city);
        EleAddress.addAttribute("county",composite._address.county);
        EleAddress.addAttribute("post",composite._postcode);
        
        EleAddress.addElement("DISTRICT").setText(composite._address.district);
        Element EleZone = EleAddress.addElement("ZONE");        
        EleZone.setText(composite._address.zone);
        EleZone.addAttribute("no",composite._address.zone_no);
        
        Element EleHouse = EleAddress.addElement("HOUSE");        
        EleHouse.setText(composite._address.house);
        EleHouse.addAttribute("no",composite._address.house_no);
 
        Element EleStorey = EleAddress.addElement("STOREY");        
        EleStorey.setText(composite._address.storey);
        EleStorey.addAttribute("room",composite._address.room);        
        EleStorey.addAttribute("position",composite._address.position); 
        
        Element EleReference = EleAddress.addElement("REFERENCE");
        EleReference.setText(composite._reference.body);
        EleReference.addAttribute("direction",composite._reference.direction);
        EleReference.addAttribute("distince",composite._reference.distance);
        
        EleComposite.addElement("ABSTRACT").setText(composite._abstract);        
        EleComposite.addElement("CONTENT").setText(composite._content);
        
        Element EleCollect = EleComposite.addElement("COLLECT");
        EleCollect.addAttribute("title",composite._collect.title);
        EleCollect.addAttribute("keyword",composite._collect.keyword);
        EleCollect.addAttribute("dns",composite._collect.dns);
        EleCollect.addAttribute("IP",composite._collect.IP);
        EleCollect.addAttribute("date",composite._collect.date);
        EleCollect.addElement("URL").setText(composite._collect.url);
        EleCollect.addElement("DESCRIPTION").setText(composite._collect.description);        

        Element EleActive = EleComposite.addElement("ACTIVE");
        EleActive.addAttribute("title",composite._active.title);
        EleActive.addAttribute("begin",composite._active.date_begin);
        EleActive.addAttribute("end",composite._active.date_end);
        EleActive.addAttribute("time",composite._active.time_service);
        EleActive.setText(composite._active.content);
        
        EleComposite.addElement("REMARK").setText(composite._remark);
    }
}
