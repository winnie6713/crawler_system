package wuit.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlGen {
    public Document generateDocumentByMethod() {
        Document document = DocumentHelper.createDocument();
        // ProcessingInstruction
        Map<String, String> inMap = new HashMap<String, String>();
        inMap.put("type", "text/xsl");
        inMap.put("href", "students.xsl");
        document.addProcessingInstruction("xml-stylesheet", inMap);
        // root element
        Element studentsElement = document.addElement("students");
        studentsElement.addComment("An Student Catalog");
        // son element
        Element stuElement = studentsElement.addElement("student");
        stuElement.addAttribute("sn", "01");
        Element nameElement = stuElement.addElement("name");
        nameElement.setText("sam");
        Element ageElement = stuElement.addElement("age");
        ageElement.setText("18");
        // son element
        Element anotherStuElement = studentsElement.addElement("student");
        anotherStuElement.addAttribute("sn", "02");
        Element anotherNameElement = anotherStuElement.addElement("name");
        anotherNameElement.setText("lin");
        Element anotherAgeElement = anotherStuElement.addElement("age");
        anotherAgeElement.setText("20");
  
        return document;
     }
  
     public Document generateDocumentByString() {
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
               "<?xml-stylesheet type=\"text/xsl\" href=\"students.xsl\"?>" +
               "<students><!--An Student Catalog-->   <student sn=\"01\">" +
               "<name>sam</name><age>18</age></student><student sn=\"02\">" +
               "<name>lin</name><age>20</age></student></students>";
        Document document = null;
        try {
            document = DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
     }
  
     public void saveDocument(Document document, File outputXml) {
        try {
            // 美化格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            /*// 缩减格式
            OutputFormat format = OutputFormat.createCompactFormat();*/
            /*// 指定XML编码
             format.setEncoding("GBK");*/
            XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
            output.write(document);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }
  
     public static void main(String[] argv) {
        XmlGen dom4j = new XmlGen();
        Document document = null;
        // document=dom4j.generateDocumentByMethod();
        document = dom4j.generateDocumentByString();
        dom4j.saveDocument(document, new File("output.xml"));
     }
}
