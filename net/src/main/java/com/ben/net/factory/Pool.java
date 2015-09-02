package com.ben.net.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {

	private static final Map<Object, ExecutorService> pool = new HashMap<Object, ExecutorService>();

	public static ExecutorService get(Object key) {
		return pool.get(key);
	}

	public static ExecutorService add(Object key, int size) {
		ExecutorService executorService;
		if (size > 0)
			executorService = Executors.newFixedThreadPool(size);
		executorService = Executors.newCachedThreadPool();
		pool.put(key, executorService);
		return executorService;
	}

}
