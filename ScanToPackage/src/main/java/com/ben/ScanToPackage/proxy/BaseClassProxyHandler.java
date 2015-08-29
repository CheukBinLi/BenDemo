package com.ben.ScanToPackage.proxy;

import java.lang.reflect.Method;

/***
 * 动态代理接口
 * 
 * @author Ben-P
 *
 * @param <T>
 */
public interface BaseClassProxyHandler {

	// public <T> T invokHandler(Object obj, Method method, Object... params);
	public Object invokHandler(Object obj, Method method, Object... params);

}
