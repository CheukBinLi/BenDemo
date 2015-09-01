package com.ben.net.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueManager {

	private static final LinkedList<String> sequence = new LinkedList<String>();

	private static final Set<String> working = new HashSet<String>();

	private static Thread thread;

	//是否在工作队列,没有则添加到工作队列
	public static final boolean isWorking(Object id) {
		return !working.add(getID());
	}

	//移除工作队列
	public static final boolean releaseWorking(Object id) {
		return working.remove(id);
	}

	//获取ID
	public final static String getID() {
		/***
		 * 影响速度要修改 GenerateID.createID();
		 */
		GenerateID.createID();
		return sequence.removeFirst();
	}

	public static final void start(int poolSize) {
		if (null != thread)
			thread.interrupt();
		sequence.clear();
		thread = new Thread(new GenerateID(poolSize));
		thread.start();
	}

	public static final void stop() {
		if (null != thread)
			thread.interrupt();
		thread = null;
	}

	/***
	 * 生成ID
	 * 
	 * @author Ben-P
	 *
	 */
	static class GenerateID implements Runnable {

		private static BlockingDeque<String> queue = new LinkedBlockingDeque<String>();

		public GenerateID(int initSize) {
			for (int i = 0; i < initSize; i++)
				createID();
		}

		public void run() {
			synchronized (queue) {
				try {
					if (queue.size() > 0) {
						sequence.addAll(queue);
						queue.clear();
					} else
						queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/***
		 * 影响速度要修改
		 */
		public static final synchronized void createID() {
			queue.add(UUID.randomUUID().toString());
			queue.notify();
		}
	}
}
