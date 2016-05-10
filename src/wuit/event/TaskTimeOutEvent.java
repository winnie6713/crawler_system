/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

import wuit.thread.ThreadPool;

/**
 *
 * @author lxl
 */
public class TaskTimeOutEvent {
    private AbstractEvent event;   
    public TaskTimeOutEvent(AbstractEvent event){   
        this.event=event;   
    }   
  
  
    public  void execute() {   
        // TODO Auto-generated method stub   
        ThreadPool pool=ThreadPool.getInstance();   
        pool.addWorkThread();   
        pool.removeWorkThread(event.workthread);   
        Object obj=event.workthread.getThreadKey();   
        System.out.println("正在停止工作超时线程...线程编号"+obj);   
        event.nowthread.interrupt();//一般不推荐直接用stop来终止进程，可能会发生意想不到的结果。
    }
}
