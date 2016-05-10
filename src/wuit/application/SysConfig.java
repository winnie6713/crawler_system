/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wuit.common.doc.FileIO;

/**
 *
 * @author lxl
 */
public class SysConfig {
   public Map<String,Module> mapFuns = new HashMap<String,Module>();
   public List<Module> listFuns = new ArrayList<Module>();
   private final String sysFile = "sysconfig.ini";
   
   
   public SysConfig(){
       String appPath = System.getProperty("user.dir");
       String file = appPath+ "\\config\\" + sysFile;
       List<String> list = new ArrayList<String>();
       FileIO.readLines(file, "", list);
       for(int i=0;i<list.size();i++){
           String vals = list.get(i);
           if(vals == null || vals.isEmpty())
               continue;
           Module module = new Module();
           module.id = Integer.parseInt( FileIO.match(vals, "(?<=id:)[^;]+?(?=;)"));
           module.pid =Integer.parseInt( FileIO.match(vals, "(?<=pid:)[^;]+?(?=;)"));
           module.name = FileIO.match(vals, "(?<=name:)[^;]+?(?=;)");
           module.remark = FileIO.match(vals, "(?<=remark:)[^;]+?(?=;)");
         
           module.param = FileIO.match(vals, "(?<=param:)[^;]+?(?=;)");
           module.mode = FileIO.match(vals, "(?<=mode:)[^;]+?(?=;)");
           mapFuns.put(module.id+"", module);
           listFuns.add(module);
      }          
   }
   
   /*
   public static void main(String[] args){
       SysConfig config = new SysConfig();
       for(int i=0;i<config.listFuns.size();i++){
           System.out.println(config.listFuns.get(i).name);
       }
   }
   */ 
}
