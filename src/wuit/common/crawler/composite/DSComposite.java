package wuit.common.crawler.composite;

public class DSComposite{
    public String id = "";
    public String name ="";
//    public String title ="";
    

    public DSCompositeStat stat = new DSCompositeStat();
    
    public LocalInfo local = new LocalInfo();
    
    public String lat = "";
    public String lng = "";

//    public String reference="";                         //参照物
//    public String distance="";                          //距离
//    public String direction="";                         //方位
    
    public String phone ="";
    public String phone_code ="";
    public String phone_number ="";
    
    public String postcode ="";
    
    public String label ="";
    public String Abstract ="";
    public String remark ="";
    public String content ="";

    public String date_publish ="";
    public String time_service ="";    
    
    public DSCrawlerPageInfo collection = new DSCrawlerPageInfo();
    public DSCrawlerActive active = new DSCrawlerActive();
   
}
