package com.ben.net.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import com.ben.net.server.util.ByteBufferUtil;

/***
 * 返回对象并释放
 * 
 * @author Ben-P
 *
 */
public class WriterHadler implements Callable<Object> {

	public WriterHadler(ReentrantLock lock, SelectionKey key) {
		super();
		this.key = key;
		this.lock = lock;
	}

	private ReentrantLock lock;
	private SelectionKey key;
	private SocketChannel client;
	private boolean flags;

	public Object call() throws Exception {
		lock = (ReentrantLock) key.attachment();
		try {
			if (flags = lock.tryLock()) {
				client = (SocketChannel) key.channel();
				client.write(ByteBufferUtil.instance().getBuffer("你好吗？".getBytes()));
				client.register(key.selector(), SelectionKey.OP_READ, key.attachment()).selector().wakeup();
				return key.attachment();// 清空
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flags)
				lock.unlock();
			System.out.println("写解锁");
		}
		return null;
	}

}
