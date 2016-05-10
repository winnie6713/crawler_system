package wuit.common.doc;


/*
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

import java.io.File;
import java.net.MalformedURLException;
import java.util.Properties; 

import javax.xml.parsers.SAXParser;
*/

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Attribute;

public class ParseXml {
	
    public Document read(String fileName) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = (Document)reader.read(new File(fileName));
        return document;
     }
	
    //重载，通过xml文件内容得到DOM
    public Document getDocument(String xmlContent, boolean b) throws
            DocumentException {
        Document d = DocumentHelper.parseText(xmlContent);
        return d;
    }
	
	
    //输出字符串
    public String transformDOM(Document d) {
        String xmlContent = "";
        xmlContent = d.asXML();
        return xmlContent;
    }
    
    //得到节点
    public Element getNode(Document d, String elePath, String eleValue) {
        Element ele = null;
        List<?> l = d.selectNodes(elePath);
        Iterator<?> iter = l.iterator();
        while (iter.hasNext()) {
            Element tmp = (Element) iter.next();
            if (tmp.getText().equals(eleValue)) {
                ele = tmp;
            }
        }
        return ele;
    }
    //重载，得到节点
    public Element getNode(Document d, String eleName) {
        Element ele = (Element) d.selectSingleNode(eleName);
        return ele;
    }
    
    //增加节点
    public void addNode(Element parentEle, String eleName, String eleValue) {
        Element newEle = parentEle.addElement(eleName);
        newEle.setText(eleValue);
    }

    //增加属性节点
    public void addAttribute(Element ele, String attributeName,
                             String attributeValue) {
        ele.addAttribute(attributeName, attributeValue);
    }

    //删除节点
    public void removeNode(Element parentEle, String eleName, String eleValue) {
        Iterator<?> iter = parentEle.elementIterator();
        Element delEle = null;
        while (iter.hasNext()) {
            Element tmp = (Element) iter.next();
            if (tmp.getName().equals(eleName) && tmp.getText().equals(eleValue)) {
                delEle = tmp;
            }
        }
        if (delEle != null) {
            parentEle.remove(delEle);
        }
    }

    //删除属性
    public void removeAttr(Element ele, String attributeName) {
        Attribute att = ele.attribute(attributeName);
        ele.remove(att);
    }

    //修改节点值
    public void setNodeText(Element ele, String newValue) {
        ele.setText(newValue);
    }

    //修改属性值
    public void setAttribute(Element ele, String attributeName,
                             String attributeValue) {
        Attribute att = ele.attribute(attributeName);
        att.setText(attributeValue);
    }
    
    
	public static void main(String args[]){ 
		try{
			ParseXml r = new ParseXml();
			Document document = r.read("d:\\product\\WorkConfig.xml");			
			Element ele =r.getNode(document, "servername");
			System.out.println(ele.getText());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
