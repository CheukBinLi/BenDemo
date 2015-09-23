package com.ben.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class M {

	private final static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		String x = "1123123";
		ExecutorService exService = Executors.newCachedThreadPool();
		exService.submit(new A(lock, x));
		exService.submit(new B(lock, x));
	}

}
