/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.doc.xml;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author lxl
 */
public class DBConnection {
   public String host="";
   public String port="";
   public String dbName="";
   public String user = "";
   public String pwd = "";
   
   private Document doc = null;
   
   public DBConnection(){
      
   }
   
   public void getConnection(){      
      String appPath = System.getProperty("user.dir");
      String filePath = appPath + "\\data\\dbConnection.xml";
      try{			
         SAXReader saxReader = new SAXReader();
         doc = saxReader.read(new File(filePath)); 
          
         Node node= doc.selectSingleNode("//root/host");
         host = node.getText();
         node= doc.selectSingleNode("//root/port");
         port = node.getText();
         node= doc.selectSingleNode("//root/dbName");
         dbName = node.getText();
         node= doc.selectSingleNode("//root/user");
         user = node.getText();
         node= doc.selectSingleNode("//root/password");
         pwd = node.getText();          
          
      }catch(DocumentException e) {
          System.out.println(e.getMessage());
      }

   }
   

}
