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
public class ReadHadler implements Callable<Object> {

	public ReadHadler(ReentrantLock lock, SelectionKey key) {
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
		System.out.println("读:"+lock.isLocked());
		try {
			if (flags = lock.tryLock()) {
				client = (SocketChannel) key.channel();
				client.configureBlocking(false);

				// 读取
				String str = new String(ByteBufferUtil.instance().getByte(client).toByteArray());
				System.out.println(str);
				if ("exit".equalsIgnoreCase(str)) {
					key.channel().close();
					key.cancel();
					// client.close();
				}
				else
					client.register(key.selector(), SelectionKey.OP_WRITE, key.attachment()).selector().wakeup();
				return key.attachment();// 清空
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flags)
				lock.unlock();
			System.out.println("读解锁");
		}
		return null;
	}
}
