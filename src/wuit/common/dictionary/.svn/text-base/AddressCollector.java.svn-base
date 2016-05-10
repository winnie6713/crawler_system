/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import wuit.application.DataParam;
import wuit.common.crawler.composite.CompositeConvert;
import wuit.common.crawler.composite.CompositeParse;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.composite.DbComposite;
import wuit.common.crawler.composite.RunJob;
import wuit.common.crawler.db.KeyValue;
import wuit.common.crawler.db.KeyValueSort;
import wuit.common.doc.FileIO;
import wuit.common.crawler.db.KeyValues;

/**
 *
 * @author lxl
 */
public class AddressCollector {
    CompositeParse parse = new CompositeParse();
//    Map<String,String> map = new HashMap<String,String>();
    int count = 0;
    int maxItem = 2000;
    DataParam param;
   
//    private ExtractFromComposite fromComposite = null;
    
   public void setStatus(int status){
       
   }
   
   /*
   public void AddresssService(DataParam param){
       this.param = param;
      switch(param.mode){
         case 301:            //从含有composite文本数据中收集地址信息
             fromComposite = new ExtractFromComposite();
             fromComposite.setParam(param);
             fromComposite.start();
//            collectAddressByCompositeFiles(1,param.status,param.paths.get(0),param.paths.get(1));
            break;
//         case 302:            //从含有composite文本数据中收集地址信息
//            collectAddressByCompositeFiles(2,param.status,param.paths.get(0),param.paths.get(1));
//            break;
         case 311:
            KeyValues clearKeys = new KeyValues(param.files.get(0));
            ClearRaodBaseAddress(param.status,param.paths.get(0),param.encode, param.paths.get(1), clearKeys.getMapKeyValues ());
            break;
         case 321:            //province,city, , ,county,district,post,phone
            collectPhoneAddressFiles(param.paths.get(0), param.paths.get(1), param.files.get(0), param.encode);
            break;
         case 322:             //从province|city|county中分离出province,city,county
            collectPostAddressFiles(param.paths.get(0), param.paths.get(1), param.files.get(0), param.encode);
            break;
         case 331:
            classfyAddress(1, param.paths.get(0), param.paths.get(1), param.encode);
            break;
         case 332:
            classfyAddress(2, param.paths.get(0), param.paths.get(1), param.encode);
            break;
         case 333:
            classfyAddress(3, param.paths.get(0), param.paths.get(1), param.encode);
            break;
      }      
   }
   */
   /*
   class ExtractFromComposite extends RunJob{
       @Override
       public synchronized void run(){
           List<String> lstFiles = new ArrayList<String>();
           List<String> lstDirs = new ArrayList<String>();
           FileIO.getFilesAndSubDirFiles(param.paths.get(0), lstFiles, lstDirs);
           
           Map<String,Map<String,String>> mapBase = new HashMap<String,Map<String,String>>();
           try {
               for (int i=0;i<lstFiles.size();i++){
                   Thread.sleep(200);
                   if( getStatus() == 0)
                       break;
                   while(getStatus()== 2){
                       Thread.sleep(2000);
                       continue;
                   }
                   collectAddressFromCompositeFile(lstFiles.get(i),"UTF-8",mapBase);
                   if(param.status != null){
                       param.status.setText("总数：" + lstFiles.size() + " 完成：" + (i+1));
                   }
               }
               Set set = mapBase.entrySet();
               Iterator it = set.iterator();
               while(it.hasNext()){
                   Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                   String key = body.getKey();
                   String[] keys = key.split("\\\\");
                   String _file = keys[0];
                   FileIO.writeAsTxtMap(param.paths.get(1) + "\\" + key, _file, body.getValue());
               }
           } catch (InterruptedException ex) {
               Logger.getLogger(DbComposite.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
 
          /**
    * 从网页收集的原始数据中提取
    * 源数据结构：DSComposite
    * 
    * @param path
    * @param file
    * @param encode 
    *  * 目标数据格式：
    * province,city,county,district,phone,post
    */
       
    /*
    public void collectAddressFromCompositeFile(String pathFile,String encode,Map<String,Map<String,String>> mapBase){
        if(mapBase == null)
            return;
        if (parse == null)
            parse = new CompositeParse();
        DSComposite info;
        List<String> listItems = new ArrayList<String>();
        FileIO.readLines(pathFile, encode, listItems);
        CompositeConvert convert = new CompositeConvert();
        String val,_val;
        for (int i=0;i<listItems.size();i++){
            info = new DSComposite();
            convert.StringToDSComposite(listItems.get(i), info);
            BaseAddress _base = new BaseAddress("");
            _base.province = info.local.province;
            _base.city = info.local.city;
            _base.county = info.local.county;
            _base.district = info.local.village;
            _base.number = info.local.villageNo;
            val = _base.ToString(_base);
            if (_base.province.isEmpty() || _base.city.isEmpty())
                continue;
            String key = "";
            key = _base.province + "\\" + _base.city + "\\";
            if(!mapBase.containsKey(key)){
                Map<String,String> _base_1 = new HashMap<String,String>();
                _base_1.put(val, val);
                mapBase.put(key, _base_1);
            }else{
                if(!mapBase.get(key).containsKey(val)){
                    mapBase.get(key).put(val, val);
                }
            }
        }
    }
	
       
   }
*/	

   /**
    * 源数据格式
    * 省份,城市,城市邮编,城市区号,行政区,;地址,邮编:,区号,
    * province,city, , ,county,district,post,phone
    * 目标：
    * province,city,county,district,phone,post
    * @param path_src
    * @param path_dist
    * @param fileName
    * @param encode
    */
   
    public void collectPhoneAddressFiles(String path_src,String path_dist,String fileName,String encode){
        if(encode.equals(""))
            return;
        File filePath = new File(path_src);
        String[] files = null;
        if(filePath.isDirectory()){
            files = filePath.list();
        }
        List<String> list = new ArrayList<String>();
        try{
            for (int i=0;i<files.length;i++){
                String file= path_src + "\\"+ files[i];
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
                String c = br.readLine();
                while(c != null){
                    String[] arrVal = c.split(";");
                    for(int j=0;j<arrVal.length;j++){
                        arrVal[j] = arrVal[j].replaceAll("[^\\:]+?\\:", "");
                    }
                    arrVal[4]=arrVal[4].replace(arrVal[1], "");
                    String val = "";
                    if (!arrVal[7].equals("")&& !arrVal[1].equals("")){
                        val = arrVal[0] + "," + arrVal[1] + "," + arrVal[4] + "," + arrVal[5].replace(",", "|") + ","+  arrVal[7] + "," +arrVal[6];
                        list.add(val);
                    }
                    c = br.readLine();
                }
                br.close();
            }
            Collections.sort(list);
            FileIO.writeAsTxtList(path_src, fileName, list);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
	
	/**
    * 从province|city|county中分离出province,city,county
    * 
    * 源数据格式
    * district,postcode,province|city|county
    * 目标数据格式
    * province,city,county,district,phone,post; 
    */
    public void collectPostAddressFiles(String path_src,String path_dist,String fileName,String encode){
        File filePath = new File(path_src);
        String[] files = null;
        if(filePath.isDirectory()){
            files = filePath.list();
        }
        if (encode.equals(""))
            return;
        List<String> list = new ArrayList<String>();
        try{
            count = 0;
            for (int i=0;i<files.length;i++){
                String file= path_src + "\\"+ files[i];
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
                String c = br.readLine();
                while(c != null){
                    String[] arrVal = c.split(";");
                    String district = arrVal[0].replaceAll("[^\\:]+?\\:", "");
                    String post = arrVal[1].replaceAll("[^\\:]+?\\:", "");
                    String province = arrVal[2].replaceAll("[^\\:]+?\\:", "");
                    String city ="";
                    String county = "";
                    district = district.replace(",", "|");
                    List<KeyValue> items = new ArrayList<KeyValue>();
//                    parse.diffProvinceCityCounty(province.trim(), items);
                    KeyValueSort compare = new KeyValueSort();
                    Collections.sort(items,compare);
                    province = items.get(0).value;
                    if (items.size()>=2){
                        if (items.size() == 2)
                            if (province.indexOf("上海")>=0 || province.indexOf("天津")>=0 || 
                                    province.indexOf("北京")>=0 || province.indexOf("重庆")>=0){
                                city = "辖区";
                                county = items.get(1).value;
                                county = county.replace(",", "|");
                                String val = province+","+ city +"," + county + ","+ district+", ,"+post;
                                list.add(val);
                                System.out.println(val);
                                count++;
                            }
                        if(items.size()==3){
                            city = items.get(1).value;
                            county = items.get(2).value;
                            county = county.replace(",", "|");
                            String val = province+","+city+","+county+","+district+", ,"+post;
                            list.add(val);
                            System.out.println(val);
                            count++;
                        }
                        if (count>=maxItem){
                            FileIO.writeAsTxtList(city, fileName, list);
                            count = 0;
                            list.clear();
                        }
                    }
                    c = br.readLine();
                }
                br.close();
            }
            Collections.sort(list);
            FileIO.writeAsTxtList(path_src, fileName, list);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
	
   /**
    * 从Composite记录中，分离地址信息
    * 
    * 过滤相同的记录，排序后保存到指定的目录中
    * @param mode       模式
    * 1:composite to baseAddress;
    * 2:baseAddress to baseAddress no number
    * @param path_src   数据源目录
    * @param path_dist  数据存储目录
    * @param fileName   保存文件名
    */
    /*
    public void collectAddressByCompositeFiles(int type,JLabel status,String path_src,String path_dist){
        List<String> lstFiles = new ArrayList<String>();
        List<String> lstDirs = new ArrayList<String>();
        FileIO.getFilesAndSubDirFiles(path_src, lstFiles, lstDirs);
        try{
            Map<String,Map<String,String>> mapBase = new HashMap<String,Map<String,String>>();
            for (int i=0;i<lstFiles.size();i++){
                if(type == 1)
                    collectAddressFromCompositeFile(lstFiles.get(i),"UTF-8",mapBase);
                if (type == 2)
                    collectRoadFromBaseAddress(lstFiles.get(i),"UTF-8",mapBase);
                if(status != null){
                    status.setText("总数：" + lstFiles.size() + " 完成：" + (i+1));
                }
            }
            Set set = mapBase.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
                String key = body.getKey();
                String[] keys = key.split("\\\\");
                String _file = keys[0];
                FileIO.writeAsTxtMap(path_dist + "\\" + key, _file, body.getValue());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    */ 

    class ExtractRoad extends RunJob{
        public void run(){
//            KeyValues mapKeys = new KeyValues(param.files.get(0));
            Map<String,Map<String,String>> mapBase = new HashMap<String,Map<String,String>>();
            
            DSComposite info ;
            List<String> listItems = new ArrayList<String>();
            
            List<String> lstFiles = new ArrayList<String>();
            List<String> lstDirs = new ArrayList<String>();
            FileIO.getFilesAndSubDirFiles(param.paths.get(0), lstFiles, lstDirs);
            
            for (int i=0;i<lstFiles.size();i++){
                try {
                    Thread.sleep(200);
                    if( getStatus() == 0)
                       break;
                    while(getStatus()== 2){
                        Thread.sleep(2000);
                        continue;
                    }
                    FileIO.readLines(lstFiles.get(i), param.encode, listItems);
                    String val,_val;
                    
                    for (int j=0;j<listItems.size();j++){
                        info = new DSComposite();
                        BaseAddress _base = new BaseAddress(listItems.get(i));
                        if(_base.district.indexOf("路")==-1&&_base.district.indexOf("街")==-1)
                            continue;
                        _base.number = "";
                        if(_base.district.length()<3)
                            continue;
                        val = _base.ToString(_base);
                        if (_base.province.isEmpty() || _base.city.isEmpty())
                            continue;
                        String key = "";
                        key = _base.province + "\\" + _base.city + "\\"; 
                        if(!mapBase.containsKey(key)){
                            Map<String,String> _base_1 = new HashMap<String,String>();
                            _base_1.put(val, val);
                            mapBase.put(key, _base_1);
                        }else{
                            if(!mapBase.get(key).containsKey(val)){
                                mapBase.get(key).put(val, val);
                            }
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(AddressCollector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
   /**
    * 
    * @param pathFile   路径文件名
    * @param encode     源文件字符编码
    * @param mapBase    路、街结果集 BaseAddress 字符串表示
    */
    public void collectRoadFromBaseAddress(String pathFile,String encode,Map<String,Map<String,String>> mapBase){
        if(mapBase == null)
            return;
        DSComposite info ;
        List<String> listItems = new ArrayList<String>();
        FileIO.readLines(pathFile, encode, listItems);
        String val,_val;
        for (int i=0;i<listItems.size();i++){
            info = new DSComposite();
            BaseAddress _base = new BaseAddress(listItems.get(i));
            if(_base.district.indexOf("路")==-1&&_base.district.indexOf("街")==-1)
                continue;
            _base.number = "";
            if(_base.district.length()<3)
                continue;
            val = _base.ToString(_base);
            if (_base.province.isEmpty() || _base.city.isEmpty())
                continue;
            String key = "";
            key = _base.province + "\\" + _base.city + "\\"; 
            if(!mapBase.containsKey(key)){
                Map<String,String> _base_1 = new HashMap<String,String>();
                _base_1.put(val, val);
                mapBase.put(key, _base_1);
            }else{
                if(!mapBase.get(key).containsKey(val)){
                    mapBase.get(key).put(val, val);
                }
            }
        }
    }
   
   /**
    * 
    * @param status
    * @param path_src
    * @param encode
    * @param path_dist
    * @param mapClear 
    */
   public void ClearRaodBaseAddress(JLabel status,String path_src,String encode,String path_dist,Map<String,String> mapClear){
      List<String> lstFiles = new ArrayList<String>();
      List<String> lstDirs = new ArrayList<String>();
      FileIO.getFilesAndSubDirFiles(path_src, lstFiles, lstDirs);      
		try{
         Map<String,Map<String,String>> mapBase = new HashMap<String,Map<String,String>>();         
			for (int i=0;i<lstFiles.size();i++){
               ClearRoad(lstFiles.get(i),"UTF-8",mapClear,mapBase);
            if(status != null){
               status.setText("总数：" + lstFiles.size() + " 完成：" + (i+1));
            }
			}
         Set set = mapBase.entrySet();
         Iterator it = set.iterator();
         while(it.hasNext()){
            Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
            String key = body.getKey();
            String[] keys = key.split("\\\\");
            String _file = keys[0];
            FileIO.writeAsTxtMap(path_dist + "\\" + key, _file, body.getValue());
//            writesMap(path_dist + "\\" + key,_file,body.getValue());
         }
		}catch(Exception e){
			System.out.println(e.getMessage());
         e.printStackTrace();
		}      
   }
   
   /**
    * 
    * @param pathFile
    * @param encode
    * @param mapClear
    * @param mapBase 
    */
   public void ClearRoad(String pathFile,String encode,Map<String,String> mapClear,Map<String,Map<String,String>> mapBase){
       if(mapBase == null)
         return;
       List<String> listItems = new ArrayList<String>();
       FileIO.readLines(pathFile, encode, listItems);
       String val,_val;
       for (int i=0;i<listItems.size();i++){
           BaseAddress _base = new BaseAddress(listItems.get(i));
           _base.number = "";
           Set set  = mapClear.entrySet();
           Iterator it = set.iterator();
           while(it.hasNext()){
               Entry<String,String> body = (Entry<String,String>)it.next();
               _base.district = _base.district.replaceAll(body.getKey(),body.getValue());
           }
           _base.county = "";
           if(_base.district.length()<3)
               continue;
           val = _base.ToString(_base);
           if (_base.province.isEmpty() || _base.city.isEmpty())
               continue;
           String key = "";
           key = _base.province + "\\" + _base.city + "\\"; 
           if(!mapBase.containsKey(key)){
               Map<String,String> _base_1 = new HashMap<String,String>();
               _base_1.put(val, val);
               mapBase.put(key, _base_1);
           }else{
               if(!mapBase.get(key).containsKey(val)){
                   mapBase.get(key).put(val, val);
               }
           }
       }
   }   
   
   /**
    * 
    * @param path       保存文件目录
    * @param fileName   保存文件名
    * @param lstMap     数据集（字符串）
    */
   /*
	public void write(String path,String fileName,List<String> lstMap){
		FileIO io = new FileIO();
		io.createDir(path);		
		if(path == null)
			return;
		try{
			if(fileName == null || fileName.equals("")){
				return;
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + fileName + ".dct")));			
			for (int i=0;i<lstMap.size();i++)
				bw.write(lstMap.get(i) + "\r\n");
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	*/
   
   /*
   public void writesList(String path,String fileName,List<String> lstMap){
		FileIO io = new FileIO();
      File _file = new File(path);
      if(!_file.isDirectory())
         io.createDir(path);		
		if(path == null)
			return;
		try{
			if(fileName == null || fileName.equals("")){
				return;
			}
         fileName= fileName + (new Date()).getTime();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + fileName + ".txt")));			
			for (int i=0;i<lstMap.size();i++)
				bw.write(lstMap.get(i) + "\r\n");
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
   */
   
   /*
   public void writesMap(String path,String fileName,Map<String,String> mapString){
		FileIO io = new FileIO();
		io.createDir(path);		
		if(path == null)
			return;
		try{
			if(fileName == null || fileName.equals("")){
				return;
			}
         fileName= fileName + (new Date()).getTime();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + fileName + ".txt")));
         Set set = mapString.entrySet();
         Iterator it = set.iterator();
         while(it.hasNext()){
            Entry<String,String> body = (Entry<String,String>)it.next();
            bw.write(body.getValue() + "\r\n");
         }
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
   */ 
   /**
    * 数据记录格式转换，过滤district中的省市县三级信息
    * 
    * 数据源格式
    * province,city,county,district,phonecode
    * 数据输出格式
    * province,city,county,district,phonecode,postcode
    * @param path:数据源目录
    * @param file：数据源文件
    * @param encode：字符编码
    */
    public void getAddress(String path,String file,String encode){
        List<BaseAddress> list = new ArrayList<BaseAddress>();
        if(path == null)
            return ;
        if(encode.equals(""))
            return;
        try{
            if(file == null || file.equals(""))
                return ;
            file= path + file;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
            String c = br.readLine();
            while(c != null){
                BaseAddress info = new BaseAddress(c);
                info.district = info.district.replaceAll("郊|�|）|（|[^内]*?内|[^与]*?与|[^近]*?近|\\d{1,}|[a-zA-Z]|\\-", "");
                info.district = info.district.replaceAll("[^县]+?县", "");
                String val = info.county.substring(0,info.county.length()-1);
                String _val = info.district.replaceAll(val, "");
                if (_val.length()>2)
                    info.district = _val;
                list.add(info);
                c = br.readLine();
            }
            count = 1;
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
            if (bw != null){
                for (int i=0; i<list.size();i++){
                    if (list.get(i).district.length()>2 && list.get(i).district.length()< 5){
                        String vals = BaseAddress.ObjectToString(list.get(i));
                        bw.write(vals + "\r\n");
                        count ++;
                    }
                }
                System.out.println(count);
                bw.flush();
                bw.close();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
	
   /**
    * 按行政区县分类存储
    * 
    * @param path_src 数据源 记录集 {山东省,临沂市,苍山县,横山乡孙楼村, ,277729}
    * @param path_dist
    * @param encode 
    */
   public void classfyAddress(int mode ,String path_src,String path_dist,String encode){
      File filePath = new File(path_src);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		try{
         Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
         
//         List<AddressInfo> list = new ArrayList<AddressInfo>();
         String key = "";
         String key1 = "";
         for (int i=0;i<files.length;i++){         
				String file= path_src + "\\"+ files[i];
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
				String c = br.readLine();			
				while(c != null){
               String value = c;
               BaseAddress info = new BaseAddress(value);
               if(info == null)
                  continue;
               if(info.city.equals("") && info.county.equals(""))
                  key = info.province + "\\";
               if(info.county.equals(""))
                  key = info.province  + "\\" + info.city + "\\";
               if(!info.city.equals("") && !info.county.equals(""))   
                  key = info.province + "\\" + info.city + "\\" + info.county + "\\";  
               
               if(mode == 1)
                  key1 = info.province + "\\";
               if(mode == 2)
                  key1 = info.province  + "\\" + info.city + "\\";
               if(mode == 3)
                  key1 = key;
               
               if(map.containsKey(key1)){
                  if(!map.get(key1).containsKey(value)){
                     map.get(key1).put(value, value);
                  }
               }else{
                  Map<String,String> _map = new HashMap<String,String>();
                  _map.put(value, value);
                  map.put(key1, _map);
               }
               c = br.readLine();
            }
            br.close();
            Set set = map.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
               Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
               if(body.getValue().size()>2000){
                  String[] file_name = body.getKey().split("\\\\");
                  String   _file = file_name[file_name.length - 1];  
                  FileIO.writeAsTxtMap(path_dist+body.getKey(), _file, body.getValue());
//                  writesMap(path_dist+body.getKey(),_file, body.getValue()); 
                  body.getValue().clear();
               }
            }
            br.close();
            System.out.println(files.length + "  " + i);
         }
         
         Set set = map.entrySet();
         Iterator it = set.iterator();
         while(it.hasNext()){
            Entry<String,Map<String,String>> body = (Entry<String,Map<String,String>>)it.next();
            String[] file_name = body.getKey().split("\\\\");
            String   _file = file_name[file_name.length - 1];
            FileIO.writeAsTxtMap(path_dist+body.getKey(), _file, body.getValue());
//            writesMap(path_dist+body.getKey(),_file,body.getValue()); 
         }         
      }catch(Exception e){
         e.printStackTrace();
      }
   }
    
	public static void main(String [] args){ 
      String val = "2010";
      int v = Integer.parseInt(val.substring(0,1));
      //\u4e00-\u9fa5
      /*
      String val = "上海市,辖区,,�川路,,,0,・1555�号";
      String _val = new String(val.getBytes());
      val = val.replaceAll("\ufffd", "");
      byte[] aVal = val.getBytes();
      */
		AddressCollector collector = new AddressCollector();
       
//		learn.getAddress("d:\\product\\aibang\\newAddress\\","district1373549139362.txt", "UTF-8");
      String path_src = "D:\\product\\lib\\Address\\postcode\\";
      String path_dist = "d:\\product\\lib\\Address\\tempPhone\\";

      collector.collectPhoneAddressFiles(path_src, path_dist, "post", "GB2312");
      
      path_src = "D:\\product\\lib\\Address\\tempPhone\\";
      path_dist = "D:\\product\\lib\\Address\\county\\";
      collector.classfyAddress(1,path_src, path_dist,"UTF-8");
		//learn.getAddress("d:\\product\\aibang\\lib\\newAddress\\","district1373523511322.txt", "UTF-8");
	}   
}
