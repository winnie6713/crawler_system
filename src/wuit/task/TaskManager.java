/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.task;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lxl
 */
public class TaskManager {
    private  static List<WorkTask> taskQueue=new ArrayList<WorkTask>(); //任务队列    
    private TaskManager(){   
           
    }   
    //添加任务                                               
    public synchronized static  void addTask(WorkTask task){   
        taskQueue.add(task);   
    }   
    //判断是否有任务未执行   
    public synchronized static WorkTask getWorkTask(){   
        if (taskQueue.size()>0){   
            return (WorkTask)taskQueue.remove(0);   
        }else  
            return null;   
    }
}
