/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.application;

import wuit.common.crawler.composite.DbComposite;
import wuit.common.dictionary.AddressCollector;

/**
 *extends Thread
 * @author lxl
 */
public class CrawlerDataService {
   public DataParam param = new DataParam();
   
   private DbComposite dbComposite = null;
   private AddressCollector collector = null;
   
   public void setParam(DataParam param){
      this.param = param;
   }
   
   public void setStatus(int status){
      if(param.mode>300 && param.mode<400){
          if(collector == null)
              return;
          collector.setStatus(status);
      }
      if(param.mode>200 && param.mode<300){
          if(dbComposite == null)
              return;
//        dbComposite.setStatus(status);
      }
   }
   
   public void start(){
      if(param.mode>300 && param.mode<400){
         if (collector == null)
             collector = new AddressCollector();
//         collector.AddresssService(param);
      }
      if(param.mode>200 && param.mode<300){
         if(dbComposite == null)
             dbComposite = new DbComposite();
//         dbComposite.CompositeService(param);
      }
   }
}
