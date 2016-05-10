package wuit.common.doc;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParseUtils {
	 public static HashMap getXmlStyle(String filePath,String styleName)
	 {
	  SAXReader saxReader = new SAXReader(); 
	  HashMap map=new HashMap();
	  try {
	   Document document = saxReader.read(new File(filePath));
	//找到节点属性是"name"的路径
	   List list = document.selectNodes("/styles/style/@name" );
	         Iterator it = list.iterator();
	         List list2=null;
	       
	         while(it.hasNext())
	         {
	          Attribute attribute = (Attribute)it.next();
	      //节点属性是"name"=特定值
	          if(attribute.getValue().equalsIgnoreCase(styleName))
	          {
	      //通过属性获取其element类
	           Element e=attribute.getParent(); 
	           Iterator it2=null;
	           it2=e.elementIterator();
	           while(it2.hasNext())
	           {
	            Element element=(Element)it2.next(); 
	          //  System.out.println(element);
	      //通过element获取其属性或者他下节点
	            Attribute attr=element.attribute("name");   
	            map.put(getMethodName(attr.getValue()), element.getText());
	            
	           } 
	          }
	         }
	         return map;
	  } catch (DocumentException e) {
	   // TODO 自动生成 catch 块
	   e.printStackTrace();
	   return null;
	  }
	 }
	 
	 public static String getMethodName(String field)
	 {
	  String methodName=field.substring(1);
	  String upperCase=field.substring(0,1).toUpperCase();
	  methodName="set"+upperCase+methodName;
	  return methodName;
	 }
/*
	 public static void main(String arg[])
	 {
	  getMethodName("fontFamily");
	 }
	 */
}
