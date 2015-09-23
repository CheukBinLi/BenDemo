package com.ben.net.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import com.ben.net.factory.Pool;

public class QueueManager {

	private static final LinkedList<Object> sequence = new LinkedList<Object>();

	// private static final Set<Object> working = new HashSet<Object>();
	private static final ConcurrentSkipListSet<Object> working = new ConcurrentSkipListSet<Object>();

	private static final BlockingDeque<Future<Object>> finishWork = new LinkedBlockingDeque<Future<Object>>();

	private static ExecutorService executorService;

	// 是否在工作队列,没有则添加到工作队列
	public static final boolean isWorking(Object id) {
		// boolean result = !working.add(id);
		// System.out.println(id+" : "+result+"  con:"+working.contains(id));
		// return result;
		return !working.add(id);
	}

	// 移除工作队列
	public static final void releaseWorking(Future<Object> id) {
		if (null != id)
			finishWork.addLast(id);
	}

	// 移除工作队列
	public static final void releaseWorking(Object id) {
		if (null != id)
			working.remove(id);
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
		stop();
		sequence.clear();
		executorService.execute(new GenerateID(poolSize));
		executorService.execute(new finishWork());
		// executorService.execute(new X());
	}

	public static final void stop() {
		if (null != executorService && !executorService.isShutdown())
			executorService.shutdownNow();
		executorService = Pool.add(QueueManager.class.getName() + "Pool", 5);
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
			// queue.notify();
		}
	}

	static class finishWork implements Runnable {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					Object o = finishWork.takeFirst().get();
					System.err.println("关闭:" + o);
					working.remove(o);
					// working.remove(finishWork.takeFirst().get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class X implements Runnable {
		public void run() {
			Iterator<Object> it;
			while (!Thread.interrupted()) {
				try {
					Thread.sleep(10000);

					it = working.iterator();
					while (it.hasNext()) {
						System.out.print(it.next() + "  ");
					}
					System.out.println();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
