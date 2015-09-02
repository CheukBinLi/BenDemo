package com.ben.net.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

import com.ben.net.server.util.ByteBufferUtil;

/***
 * 返回对象并释放
 * 
 * @author Ben-P
 *
 */
public class WriterHadler implements Callable<Object> {

	public WriterHadler(SelectionKey key) {
		super();
		this.key = key;
	}

	private SelectionKey key;
	private SocketChannel client;
	
	public Object call() throws Exception {
		client.write(ByteBufferUtil.instance().getBuffer("你好吗？".getBytes()));
		client.close();
		key.cancel();
		return key.attachment();// 清空
	}

}
