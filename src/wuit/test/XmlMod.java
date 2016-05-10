package wuit.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlMod {
    public void modifyDocument(File inputXml) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputXml);
            List<?> list = document.selectNodes("//students/student/@sn");
            Iterator<?> iter = list.iterator();
            while (iter.hasNext()) {
               Attribute attribute = (Attribute) iter.next();
               if (attribute.getValue().equals("01"))
                   attribute.setValue("001");
            }
  
            list = document.selectNodes("//students/student");
            iter = list.iterator();
            while (iter.hasNext()) {
               Element element = (Element) iter.next();
               Iterator<?> iterator = element.elementIterator("name");
               while (iterator.hasNext()) {
                   Element nameElement = (Element) iterator.next();
                   if (nameElement.getText().equals("sam"))
                      nameElement.setText("jeff");
               }
            }
  
            XMLWriter output = new XMLWriter(new FileWriter(new File(
                   "students-modified.xml")));
            output.write(document);
            output.close();
        }
  
        catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }
  
     public static void main(String[] argv) {
        XmlMod dom4jParser = new XmlMod();
        dom4jParser.modifyDocument(new File("d:\\product\\workconfig.xml"));
     }
}
