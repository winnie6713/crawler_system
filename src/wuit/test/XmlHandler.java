package wuit.test;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

public class XmlHandler {
    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        File file = new File("students.xml");
        try {
            // 添加一个ElementHandler实例。
            saxReader.addHandler("/students/student", new StudentHandler());
            saxReader.read(file);
  
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
     }
  
     /**
      * 定义StudentHandler处理器类，对<student>元素进行处理。
      */
     private static class StudentHandler implements ElementHandler {
        public void Start(ElementPath path) {
            Element elt = path.getCurrent();
//            System.out.println("Found student: " + elt.attribut.ue("sn"));
            System.out.println("Found student: " + elt.attributeValue("sn"));
            // 添加对子元素<name>的处理器。
            path.addHandler("name", new NameHandler());
        }
  
        public void End(ElementPath path) {
            // 移除对子元素<name>的处理器。
            path.removeHandler("name");
        }

		@Override
		public void onEnd(ElementPath arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ElementPath arg0) {
			// TODO Auto-generated method stub
			
		}
     }
  
     /**
      * 定义NameHandler处理器类，对<student>的<name>子元素进行处理。
      */
     private static class NameHandler implements ElementHandler {
        public void Start(ElementPath path) {
            System.out.println("path : " + path.getPath());
        }
  
        public void End(ElementPath path) {
            Element elt = path.getCurrent();
            // 输出<name>元素的名字和它的文本内容。
            System.out.println(elt.getName() + " : " + elt.getText());
        }

		@Override
		public void onEnd(ElementPath arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ElementPath arg0) {
			// TODO Auto-generated method stub
			
		}
     }
     
     public Document styleDocument(Document document, String stylesheet)
             throws Exception {
   
         // load the transformer using JAXP
         TransformerFactory factory = TransformerFactory.newInstance();
         Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));
   
         // now lets style the given document
         DocumentSource source = new DocumentSource(document);
         DocumentResult result = new DocumentResult();
         transformer.transform(source, result);
   
         // return the transformed document
         Document transformedDoc = result.getDocument();
         return transformedDoc;
     }
}
