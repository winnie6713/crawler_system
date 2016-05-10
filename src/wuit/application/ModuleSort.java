/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.application;

import java.util.Comparator;
/**
 *
 * @author lxl
 */
public class ModuleSort  implements Comparator<Module>{
 	 @Override
	 public int compare(Module o1, Module o2) {
		 if(o1.id>o2.id)
			 return 1;
		 else
			 return -1;
	 }    
}
