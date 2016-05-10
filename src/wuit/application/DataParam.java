/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.application;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 * param:0110 ——PPP_FFF_CF_P
 * PPP：路径   0：无；1：源、目；2：源、有效目、无效目
 * FFF：文件   0：无；1：源、目；
 * CF：规则    0：无；1：规则文件
 * P：指定省份 0：无；选择省份
 * 
 * （1）2Path
 * （2）2Path + ruler
 * （3）3Path + province
 * （4）3Path
 * 
 * @author lxl
 */
public class DataParam {
   public int mode = 0;
   public List<String> paths = new ArrayList<String>();
   public List<String> files = new ArrayList<String>();
   public List<String> fields = new ArrayList<String>();
   public List<String> keys = new ArrayList<String>();
   public JLabel status = null;
   public String encode = "UTF-8";
}
