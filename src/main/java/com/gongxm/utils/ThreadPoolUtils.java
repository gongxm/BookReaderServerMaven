package com.gongxm.utils;

import java.util.LinkedList;

/**
 * 线程池工具类
 * 
 * @author simple
 *
 */
public class ThreadPoolUtils {

	private static LinkedList<Runnable> tasks = new LinkedList<>();

	private static int taskCount = 0; // 运行中的线程数量
	private static int max = 10; // 最大线程数量
	private static int interval = 100;// 间隔时间扫描任务集合

	/**
	 * 开启一个线程执行这个任务
	 * 
	 * @param task
	 */
	public static void executeOnNewThread(Runnable task) {
		tasks.add(task);
	}

	static {
		new Thread() {
			public void run() {
				while (true) {
					if (tasks.size() > 0) {
						taskCount++;
						Runnable task = tasks.removeFirst();
						new Thread(task) {
							@Override
							public void run() {
								super.run();
								taskCount--;
							}
						}.start();

						while (taskCount > max) {
							try {
								Thread.sleep(interval);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}

					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

}
