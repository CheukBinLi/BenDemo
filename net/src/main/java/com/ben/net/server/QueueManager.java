package com.ben.net.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import com.ben.net.factory.Pool;

public class QueueManager {

	private static final LinkedList<Object> sequence = new LinkedList<Object>();

	private static final Set<Object> working = new HashSet<Object>();

	private static final BlockingDeque<Future<Object>> finishWork = new LinkedBlockingDeque<Future<Object>>();

	private static ExecutorService executorService = Pool.add(QueueManager.class.getName() + "Pool", 5);

	// 是否在工作队列,没有则添加到工作队列
	public static final boolean isWorking(Object id) {
		return !working.add(getID());
	}

	// 移除工作队列
	public static final void releaseWorking(Future<Object> id) {
		finishWork.addLast(id);
	}

	// 获取ID
	public final static Object getID() {
		/***
		 * 影响速度要修改 GenerateID.createID();
		 */
		GenerateID.createID();
		return sequence.removeFirst();
	}

	public static final void start(int poolSize) {
		if (null != executorService && !executorService.isShutdown())
			stop();
		sequence.clear();
		executorService.execute(new GenerateID(poolSize));
		executorService.execute(new finishWork());
	}

	public static final void stop() {
		executorService.shutdownNow();
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
			// synchronized (queue) {
			// try {
			// while (!Thread.interrupted()) {
			// if (queue.size() > 0) {
			// sequence.addAll(queue);
			// queue.clear();
			// }
			// else
			// queue.wait();
			// }
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			while (!Thread.interrupted()) {
				try {
					sequence.add(queue.takeFirst());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// }
		}

		/***
		 * 影响速度要修改
		 */
		public static final synchronized void createID() {
			queue.add(UUID.randomUUID().toString());
//			queue.notify();
		}
	}

	static class finishWork implements Runnable {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					working.remove(finishWork.takeFirst().get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
