package wuit.common.doc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wuit.common.doc.JsonDateValueProcessor;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.JavaIdentifierTransformer;

public class JsonUtils {
	/** 
	* ��һ��JSON �����ַ��ʽ�еõ�һ��java�������磺 
	* {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}} 
	* @param object 
	* @param clazz 
	* @return 
	*/ 
	public static Object getJavaOBFromString(String jsonString, Class clazz){
		Object object = null;
		try{ 
			JSONObject jsonObject = null;
			JsonConfig config = setJsonToJava();
			config.setRootClass(clazz);
			setDataFormat2JAVA();
			
			jsonObject = JSONObject.fromObject(jsonString); 
			object = JSONObject.toBean(jsonObject, config); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
		return  object;
	} 

	public static Object getJavaOBFromJsonOB(JSONObject jsonObject, Class clazz){
		Object object = null;
		try{ 
			JsonConfig config = setJsonToJava();
			config.setRootClass(clazz);
			setDataFormat2JAVA();
			object = JSONObject.toBean(jsonObject, config); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
		return  object;
	} 
	
	public static JSONObject getJsonOBFromJavaOB(Object object, Class clazz){ 
		JSONObject jsonObject = null;
		try{ 
			JsonConfig config = setJsonToJava();
			config.setRootClass(clazz);
			setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(object);			
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
		return jsonObject;
	} 	
	
	/** 
	* ��һ��JSON �����ַ��ʽ�еõ�һ��java��������beansList��һ��ļ��ϣ����磺 
	* {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, 
	* beansList:[{}, {}, ...]} 
	* @param jsonString 
	* @param clazz 
	* @param map �������Ե����� (key : ����������, value : ������������class) eg: ("beansList" : Bean.class) 
	* @return 
	*/ 
	public static Object getJavaOBFromString(String jsonString, Class clazz, Map map){ 
		Object object = null;
		try{
			JSONObject jsonObject = null; 
			setDataFormat2JAVA(); 
			jsonObject = JSONObject.fromObject(jsonString); 
			object = JSONObject.toBean(jsonObject, clazz, map); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
		return object;
	} 

	/** 
	* ��һ��JSON����õ�һ��java�������飬���磺 
	* [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
	* @param object 
	* @param clazz 
	* @return 
	*/ 
	public static Object[] getDTOArray(String jsonString, Class clazz){ 
		setDataFormat2JAVA(); 
		JSONArray array = JSONArray.fromObject(jsonString); 
		Object[] obj = new Object[array.size()]; 
		for(int i = 0; i < array.size(); i++){ 
			JSONObject jsonObject = array.getJSONObject(i); 
			obj[i] = JSONObject.toBean(jsonObject, clazz); 
		} 
		return obj; 
	} 

	/** 
	* ��һ��JSON����õ�һ��java�������飬���磺 
	* [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
	* @param object 
	* @param clazz 
	* @param map 
	* @return 
	*/ 
	public static Object[] getDTOArray(String jsonString, Class clazz, Map map){ 
		setDataFormat2JAVA(); 
		JSONArray array = JSONArray.fromObject(jsonString); 
		Object[] obj = new Object[array.size()]; 
		for(int i = 0; i < array.size(); i++){ 
			JSONObject jsonObject = array.getJSONObject(i); 
			obj[i] = JSONObject.toBean(jsonObject, clazz, map); 
		} 
		return obj; 
	} 

	/** 
	* ��һ��JSON����õ�һ��java���󼯺� 
	* @param object 
	* @param clazz 
	* @return 
	*/ 
	public static List getDTOList(String jsonString, Class clazz){ 
		setDataFormat2JAVA(); 
		JSONArray array = JSONArray.fromObject(jsonString); 
		List list = new ArrayList(); 
		for(Iterator iter = array.iterator(); iter.hasNext();){ 
			JSONObject jsonObject = (JSONObject)iter.next(); 
			list.add(JSONObject.toBean(jsonObject, clazz)); 
		} 
		return list; 
	} 

	/** 
	* ��һ��JSON����õ�һ��java���󼯺ϣ����ж����а��м������� 
	* @param object 
	* @param clazz 
	* @param map �������Ե����� 
	* @return 
	*/ 
	public static List getDTOList(String jsonString, Class clazz, Map map){ 
		setDataFormat2JAVA(); 
		JSONArray array = JSONArray.fromObject(jsonString); 
		List list = new ArrayList(); 
		for(Iterator iter = array.iterator(); iter.hasNext();){ 
			JSONObject jsonObject = (JSONObject)iter.next(); 
			list.add(JSONObject.toBean(jsonObject, clazz, map)); 
		} 
		return list; 
	} 

	/** 
	* ��json HASH���ʽ�л�ȡһ��map����map֧��Ƕ�׹��� 
	* ���磺{"id" : "johncon", "name" : "Сǿ"} 
	* ע��commons-collections�汾�������org.apache.commons.collections.map.MultiKeyMap 
	* @param object 
	* @return 
	*/ 
	public static Map getMapFromJson(String jsonString) { 
	setDataFormat2JAVA(); 
	        JSONObject jsonObject = JSONObject.fromObject(jsonString); 
	        Map map = new HashMap(); 
	        for(Iterator iter = jsonObject.keys(); iter.hasNext();){ 
	            String key = (String)iter.next(); 
	            map.put(key, jsonObject.get(key)); 
	        } 
	        return map; 
	    } 

	/** 
     * ��json�����еõ���Ӧjava���� 
     * json���磺["123", "456"] 
     * @param jsonString 
     * @return 
     */ 
    public static Object[] getObjectArrayFromJson(String jsonString) { 
        JSONArray jsonArray = JSONArray.fromObject(jsonString); 
        return jsonArray.toArray(); 
    } 

	/** 
	* ����ݶ���ת����json�ַ� 
	* DTO�������磺{"id" : idValue, "name" : nameValue, ...} 
	* ����������磺[{}, {}, {}, ...] 
	* map�������磺{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...} 
	* @param object 
	* @return 
	*/ 
	public static String getJSONString(Object object) throws Exception{ 
		String jsonString = null; 
		//����ֵ������ 
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
		if(object != null){ 
			if(object instanceof Collection || object instanceof Object[]){ 
				jsonString = JSONArray.fromObject(object, jsonConfig).toString(); 
			}else{ 
				jsonString = JSONObject.fromObject(object, jsonConfig).toString(); 
			} 
		} 
		return jsonString == null ? "{}" : jsonString; 
	} 

	private static void setDataFormat2JAVA(){ 
		//�趨����ת����ʽ 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})); 
	
	} 

	private static JsonConfig setJsonToJava(){
		JsonConfig config = new JsonConfig();
	    config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
		      @Override
		      public String transformToJavaIdentifier(String str) {
		        char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
		      }
		    });
	    return config;
	}
	
	/*
	public static void main(String[] arg) throws Exception{ 
		String s = "{Status:'success',Name:'welcome',age:20,users:[{name:'ww',pwd:'135'},{name:'hh',pwd:'246'}]}";
		try{
			Info info = new Info();
			
			info = (Info)JsonUtils.getJavaOBFromString(s, Info.class);
			
			System.out.println(" object : " + info.toString());
			
			JSONObject json = JsonUtils.getJsonOBFromJavaOB(info, Info.class);
	
			info = (Info)JsonUtils.getJavaOBFromJsonOB(json, Info.class);
			System.out.println(" object : " + json.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
}
