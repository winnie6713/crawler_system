/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

import wuit.task.WorkTask;
import wuit.thread.WorkThread;

/**
 * 抽象事件类
 * @author weiyunyun 2016.5.10
 *
 */
public abstract class AbstractEvent {
	/**工作线程*/
    protected WorkThread workthread;   
    /**
     * 当前线程
     */
    protected Thread nowthread;   
    /**
     * 工作任务
     */
    protected WorkTask nowtask;   
    //事件触发   
    public synchronized void execute(){};   
    
    @Override  
    public boolean equals(Object obj) {   
    	
        AbstractEvent other=(AbstractEvent)obj;   
        if(this.workthread == other.workthread && this.nowtask == other.nowtask){
        	return true;
        }
        return false;
    };    
}
