/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

import wuit.thread.ThreadPool;

/**
 * 工作任务超时的事件
 * 
 * @author weiyunyun 2016.5.10
 *
 */
public class TaskTimeOutEvent {
	/**
	 * 单例获取线程池
	 */
	ThreadPool pool=ThreadPool.getInstance();   
	
    private AbstractEvent event;   
    public TaskTimeOutEvent(AbstractEvent event){   
        this.event=event;   
    }   
  
  
    public  void execute() {   
        pool.addWorkThread();   
        pool.removeWorkThread(event.workthread);   
        Object obj=event.workthread.getThreadKey();   
        System.out.println("正在停止工作超时线程...线程编号"+obj);   
        event.nowthread.interrupt();//一般不推荐直接用stop来终止进程，可能会发生意想不到的结果。
    }
}
