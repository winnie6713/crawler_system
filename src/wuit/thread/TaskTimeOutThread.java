/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.thread;

import java.util.Vector;
import wuit.event.BeginTaskEvent;
import wuit.event.EndTaskEvent;
import wuit.event.TaskRunTime;
import wuit.event.TaskTimeOutEvent;

/**
 *
 * @author lxl
 */
public class TaskTimeOutThread extends Thread{
    private ThreadPool pool;   
    private boolean shutdown=false;   
    private Vector<TaskRunTime> taskruntimelist=new Vector<TaskRunTime>();//运行任务列表   
    private int pollTime=500; //轮询时间   
    private int TaskOutTime=2000; //任务过时时间   
    public TaskTimeOutThread(ThreadPool pool){   
        this.pool=pool;   
    }   
    @Override  
    public void run() {   
        // TODO Auto-generated method stub   
            while(!shutdown){   
                synchronized(taskruntimelist){   
                    for(int i=0;i<taskruntimelist.size();i++){   
                        TaskRunTime t=(TaskRunTime) taskruntimelist.get(i);   
                        if (t.checkRunTimeOut(TaskOutTime)){   
                            taskruntimelist.remove(i);   
                            new TaskTimeOutEvent(t.getEvent()).execute();   
                            break;   
                        }   
                    }   
                }   
                try {   
                    sleep(pollTime);   
                } catch (InterruptedException e) {   
                    // TODO Auto-generated catch block   
                    e.printStackTrace();   
                }   
            }   
    }   
       
    //任务运行，开始监测   
    public  void beginTaskRun(BeginTaskEvent begin){   
        taskruntimelist.add(new TaskRunTime(begin));   
    }   
    //任务正常结束   
    public  void endTaskRun(EndTaskEvent end){   
        synchronized(taskruntimelist){   
            for(int i=0;i<taskruntimelist.size();i++){   
                BeginTaskEvent begin=((TaskRunTime) taskruntimelist.get(i)).getEvent();   
                if (begin.equals(end)){   
                    taskruntimelist.remove(i);   
                    break;   
                }   
            }   
        }   
    }   
  
       
    //自杀   
    public void kill(){   
        System.out.println("正在关闭超时监测线程...");   
        while(taskruntimelist.size()>0){   
            try {   
                Thread.sleep(pollTime);   
            } catch (InterruptedException e) {   
                // TODO Auto-generated catch block   
                e.printStackTrace();   
            }   
        }   
        shutdown=true;   
    } 
}
