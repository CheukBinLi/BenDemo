package com.ben.ScanToPackage.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractObjectCacheFactory implements ObjectCacheFactory {

	/***
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public <T> T gets(Object... key) {
		Object result = (Map<Object, Object>) this.get(key[0]);
		if (null == result)
			return null;
		for (int i = 1; i < key.length; i++) {
			if (null == result)
				return null;
			try {
				result = ((Map<Object, Object>) result).get(key[i]);
			} catch (Exception e) {
				System.err.println("层次节目转换失败：key:" + Arrays.asList(key) + "出错节目:" + key[i - 1] + " 节目值:" + result);
				// e.printStackTrace();
				return null;
			}
		}
		return (T) result;
	}

	public void adds(Object value, Object... key) {
		Map container = get(key[0]);// 第一节
		Object node;// 第n个节点
		if (null == container) {
			container = new HashMap<Object, Object>();
			this.add(key[0], container);// 添加第一节
		}
		for (int i = 1; i < key.length - 1; i++) {
			// System.out.println("put:" + key[i]);
			node = container.get(key[i]);
			if (null == node) {
				node = new HashMap<Object, Object>();
				container.put(key[i], node);
			}
			container = (Map) node;// 下一节
		}
		//		System.out.println("key:" + key[key.length - 1]+"   "+value);
		container.put(key[key.length - 1], value);
	}

	public abstract void add(Object key, Object value);

	public abstract <T> T get(Object key);

	public Object arrays(Object... obj) {
		return obj;
	}

}
