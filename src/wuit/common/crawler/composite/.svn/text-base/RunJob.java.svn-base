/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.common.crawler.composite;

import wuit.application.DataParam;

/**
 *
 * @author lxl
 */
public class RunJob extends Thread{
    DataParam param;
    int status = 1;
    CompositeConvert convert = new CompositeConvert();
    
    public synchronized void setStatus(int status){
        this.status = status;
    }
    
    public synchronized int getStatus(){
        return status;
    }
    
    public void setParam(DataParam param){
        this.param = param;
    }
}
