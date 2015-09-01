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
		if (size > 0)
			return pool.put(key, Executors.newFixedThreadPool(size));
		return pool.put(key, Executors.newCachedThreadPool());
	}

}
