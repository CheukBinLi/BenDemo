package com.ben.net;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class A implements Callable<Boolean> {

	private ReentrantLock lock;
	private String a;

	public Boolean call() throws Exception {
		if (lock.tryLock()) {
			System.out.println("A上锁成功");
			System.out.println(a);
			this.wait();
		}
		else
			System.out.println("A上锁失败");
		return true;
	}

	public A(ReentrantLock lock, String a) {
		super();
		this.lock = lock;
		this.a = a;
	}

}
