/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wuit.event;

/**
 * 类描述：任务运行时间
 * 
 * @author weiyunyun 2016.5.10
 *
 */
public class TaskRunTime {
	private long begintime;
	private long endtime;
	private BeginTaskEvent event;

	public TaskRunTime(BeginTaskEvent event) {
		this.event = event;
		this.begintime = System.currentTimeMillis();
		this.endtime = this.begintime;
	}

	public BeginTaskEvent getEvent() {
		return event;
	}

	// 检查是否超时
	public boolean checkRunTimeOut(long maxtime) {
		endtime = System.currentTimeMillis();
		long cha = endtime - begintime;
		return cha >= maxtime;
	}

}
