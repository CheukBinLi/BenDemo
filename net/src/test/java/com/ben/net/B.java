package com.ben.net;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class B implements Callable<Boolean> {

	private ReentrantLock lock;
	private String b;

	public Boolean call() throws Exception {
		if (lock.tryLock()) {
			System.out.println("上锁成功");
			System.out.println(b);
		}
		else
			System.out.println("上锁失败");
		return true;
	}

	public B(ReentrantLock lock, String b) {
		super();
		this.lock = lock;
		this.b = b;
	}

}
