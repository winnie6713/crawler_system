/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.task;

/**
 *
 * @author lxl
 */
public interface WorkTask {
    void execute() throws Exception; //执行工作任务   
    void setTaskThreadKey(Object key);//设置任务线程编号  
    
}
