/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.task;

/**
 *
 * @author lxl
 */
public class WorkTaskImp implements WorkTask{
    protected String param;   
    protected Object threadkey; //为了显示执行线程编号   
    protected final int TaskExecTime = 500; //任务执行时间   
    public void execute() throws Exception {   
        // TODO Auto-generated method stub   
        System.out.println(param+"工作线程编号"+threadkey.toString());   
        Thread.sleep(TaskExecTime);   
    }   
    public WorkTaskImp(String param){   
        this.param=param;   
    }   
    public void setTaskThreadKey(Object key){   
        this.threadkey=key;   
    }   
    public String toString(){   
        return param+"工作线程编号"+threadkey.toString();   
    }
}
