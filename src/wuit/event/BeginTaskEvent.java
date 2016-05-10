/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

import wuit.task.WorkTask;
import wuit.thread.ThreadPool;
import wuit.thread.WorkThread;

/**
 * 开始任务事件
 * 
 * @author weiyunyun 2016.5.10
 *
 */
public class BeginTaskEvent extends AbstractEvent{
	/**
	 * 获取单例
	 */
	ThreadPool pool=ThreadPool.getInstance();   
	
    public BeginTaskEvent(WorkThread workthread,Thread nowthread,WorkTask task){   
        this.workthread=workthread;   
        this.nowthread=nowthread;   
        this.nowtask=task;   
    }   
    @Override  
    public  void execute() {   
        pool.beginTaskRun(this);   
    }
    
}
