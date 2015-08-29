package com.ben.ScanToPackage.pool;

public class PoolFactory {

	protected static ObjectCacheFactory cacheFactory;

	public static ObjectCacheFactory instance() {
		return cacheFactory;
	}
}
