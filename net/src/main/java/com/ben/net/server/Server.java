package com.ben.net.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import com.ben.net.factory.Pool;

public final class Server implements Runnable {

	protected static Selector SELECTOR;
	protected static ServerSocketChannel SERVER;
	protected Properties config;
	protected static final ReentrantLock lock = new ReentrantLock();

	private static Thread thread;

	private static final ExecutorService EXECUTOR_SERVICE = Pool.add(Server.class.getName() + "MainPool", 100);

	private final ReentrantLock getLock() {
		final ReentrantLock keyLock = new ReentrantLock();
		return keyLock;
	}

	private void init() throws Exception {
		if (thread != null)
			thread.interrupt();
		SELECTOR = Selector.open();
		SERVER = ServerSocketChannel.open();
		SERVER.socket().setSoTimeout(10000);// 10秒
		SERVER.bind(new InetSocketAddress(10010));
		SERVER.configureBlocking(false);
		SERVER.register(SELECTOR, SelectionKey.OP_ACCEPT);
		thread = new Thread(this);
		thread.start();
		// 队列管理
		QueueManager.start(10);
	}

	public boolean start() {
		try {
			init();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void run() {
		System.err.println("启动");
		SocketChannel client;
		Set<SelectionKey> keys;
		SelectionKey key;
		Iterator<SelectionKey> it;
		while (!Thread.interrupted()) {
			try {
				if (SELECTOR.select(50) == 0) {
					continue;
				}
				keys = SELECTOR.selectedKeys();
				it = keys.iterator();
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					Object o = key.attachment();
					if (null != key.attachment() && !((ReentrantLock) key.attachment()).tryLock()) {
						System.out.println(((ReentrantLock) key.attachment()).isLocked());
						continue;
					}
					else if (null != key.attachment())
						((ReentrantLock) key.attachment()).unlock();
					// 分解
					if (key.isValid() && key.channel().isOpen()) {
						try {
							if (key.isAcceptable()) {
								client = ((ServerSocketChannel) key.channel()).accept();
								client.configureBlocking(false);
								client.finishConnect();
								client.register(key.selector(), SelectionKey.OP_READ, getLock()).selector().wakeup();
								// client.register(key.selector(), SelectionKey.OP_READ, QueueManager.getID()).selector().wakeup();
								// QueueManager.releaseWorking(key.attachment());
							}
							// else if (QueueManager.isWorking(key.attachment())) {
							// // keys.clear();
							// continue;
							// }
							else if (key.isReadable()) {
								/* QueueManager.releaseWorking( */EXECUTOR_SERVICE.submit(new ReadHadler(lock, key))/* ) */;
							}
							else if (key.isWritable()) {
								/* QueueManager.releaseWorking( */EXECUTOR_SERVICE.submit(new WriterHadler(lock, key))/* ) */;
							}
						} catch (Exception e) {
							// if (null != key.attachment())
							// QueueManager.releaseWorking(key.attachment());
							e.printStackTrace();
						}
					}
					else {
						key.channel().close();
					}
					// keys.clear();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

}
