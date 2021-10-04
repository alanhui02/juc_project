package com.y.juc.look;

/**
 * @Author yao
 * @Date 2021/10/4
 *
 * 死锁demo
 *  如何验证死锁
 *      1.使用java 命令
 *          1.1.首先使用jps -l命令 查询相关死锁代码进程号
 *          1.2.在使用jstack 加进程号 查询，会打印相关信息，在信息中会发现有关死锁内容
 *              例如:Found 1 deadlock.
 *      2.使用java控制台命令查询
 *          1.1.在cmd中输入jconsole会出现相关的图形化界面，在线程导航栏中会显示检测死锁，会把相关死锁线程名称列举出来
 */
public class DeadLookDemo {
	public static void main(String[] args) {
		final Object looka = new Object();
		final Object lookb = new Object();
		new Thread(()->{
			synchronized (looka){
				System.out.println("自己的a，想获得B");
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lookb) {
					System.out.println("自己的b，想获取A");
				}
			}
		}).start();

		new Thread(() ->{
			synchronized (lookb){
				System.out.println("自己的b，想获取a");
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (looka) {
					System.out.println("自己的a，想获取b");
				}
			}
		}).start();

	}

}
