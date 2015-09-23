package com.ben.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

import com.ben.net.server.util.ByteBufferUtil;

public class Client implements Callable<Boolean> {

	private static final CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Runnable r = new Runnable() {

			public void run() {
				List<Callable<Boolean>> list = new LinkedList<Callable<Boolean>>();
				for (int i = 0; i < 1; i++) {
					list.add(new Client());
				}
				try {
					Executors.newCachedThreadPool().invokeAll(list);
					countDownLatch.await();
					System.out.println("完成");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t = new Thread(r);
//		t.start();
		new Client().T();
	}

	public void T() throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1", 10010);
		OutputStream out = client.getOutputStream();
		out.write(ByteBufferUtil.instance().getBytes("HELLO"));
		out.flush();
		InputStream in = client.getInputStream();
		System.out.println(new String(ByteBufferUtil.instance().getByte(in).toByteArray()));
		out.write(ByteBufferUtil.instance().getBytes("HELLO2"));
		out.flush();
		out.write(ByteBufferUtil.instance().getBytes("Exit"));
		out.flush();
		out.close();
		client.close();
	}

	public Boolean call() throws Exception {
		try {
			T();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println(countDownLatch.getCount());
			countDownLatch.countDown();
		}
		return false;
	}
}
