/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

import wuit.task.WorkTask;
import wuit.thread.ThreadPool;
import wuit.thread.WorkThread;

/**
 * 结束任务事件
 * 
 * @author weiyunyun 2015.5.10
 *
 */
public class EndTaskEvent  extends AbstractEvent{
	/**
	 * 单例
	 */
	ThreadPool pool=ThreadPool.getInstance();   
	
    public EndTaskEvent(WorkThread workthread,Thread nowthread,WorkTask task){   
        this.workthread=workthread;   
        this.nowthread=nowthread;   
        this.nowtask=task;   
    }   
  
    @Override  
    public  void execute() {   
        pool.endTaskRun(this);   
    }   
    
}
