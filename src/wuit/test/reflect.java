package wuit.test;

//import java.lang.reflect.*;
import java.awt.*;

//import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class reflect{
	public static int count =0;
	
	public static void main(String[] args) throws Exception {
		Rectangle r = new Rectangle(100, 325);
		printHeight(r);
		printWidth( r);
		
		Class c = Class.forName("wuit.Test");
//		Class partypes[] = new Class[1];
		
		Method m[] = c.getDeclaredMethods();
		for (int i=0;i<m.length;i++){
			System.out.println(m[i].toString());
		}
		
		Object tt = c.newInstance();
		Class partypes = String.class;
//		Test t = new Test();
		Method mm= c.getMethod("print", partypes);
		mm.invoke(tt, "welcome");
	}

	static void printHeight(Rectangle r)throws Exception {
		Field heightField;				//Field������
		Integer heightValue; 			//Integer����ֵ
		Class c = r.getClass();			//����һ��Class����
		heightField = c.getField("height");	//.ͨ��getField ����һ��Field����
		//����Field.getXXX(Object)����(XXX��Int,Float�ȣ����Object��ָʵ��).
		heightValue = (Integer) heightField.get(r);
		System.out.println("Height: " + heightValue.toString());
	}

	static void printWidth(Rectangle r) throws Exception{
		Field widthField;
		Integer widthValue;
		Class c = r.getClass();

		widthField = c.getField("width");
		widthValue = (Integer) widthField.get(r);
		System.out.println("Height: " + widthValue.toString());
	}
}

