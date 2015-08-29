package com.ben.ScanToPackage.pool;

import java.util.HashMap;
import java.util.Map;

public final class DefaultCacheFactory extends AbstractObjectCacheFactory {

	static final DefaultCacheFactory newInstance = new DefaultCacheFactory();

	final Map<Object, Object> cache = new HashMap<Object, Object>();

	private DefaultCacheFactory() {
	}

	protected static DefaultCacheFactory instance() {
		return newInstance;
	}

	public <T> T get(Object key) {
		Object o = cache.get(key);
		return null == o ? null : (T) o;
	}

	public void add(Object key, Object value) {
		cache.put(key, value);
	}

	public static void main(String[] args) throws Exception {
		DefaultCacheFactory f = new DefaultCacheFactory();
		f.adds("123", "a", "b", "c", "d", "e");
		// System.out.println();
		f.adds("1234", "a", "b", "c", "d", "f");
		// System.out.println();
		f.adds(new HashMap<String, String>(), "a", "b", "c", "d", "e");
		f.adds("123123", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e");
		// System.out.println();
		System.out.println(f.gets("a", "b", "c", "d", "e"));
		System.out.println(f.gets("a", "b", "c", "d", "f"));
		System.out.println(f.gets("a", "b", "c", "n", "j", "g"));
		System.out.println(f.gets("a", "b", "c", "d", "e", "a", "b", "c", "d", "e"));
	}
}
