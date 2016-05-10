/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import org.dom4j.Document;/**
 *
 * @author lxl
 * 地址名称关键字
 */
public class AddressKeys {
   private Document doc = null;
   
   public String getXmlAsString(){
      if (doc == null)
         return "";
      return doc.asXML();
   }
   
   /*
   public void Init(){
      String appPath = System.getProperty("user.dir");
      String filePath = appPath + "\\data\\addressKeyWords.xml";
      try{			
         SAXReader saxReader = new SAXReader();
         doc = saxReader.read(new File(filePath));
      }catch(DocumentException e) {
          System.out.println(e.getMessage());
      }
   }
   */ 
}
