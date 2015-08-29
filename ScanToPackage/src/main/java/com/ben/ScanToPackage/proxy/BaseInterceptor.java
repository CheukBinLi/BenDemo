package com.ben.ScanToPackage.proxy;

import java.lang.reflect.Method;

/***
 * 拦截器接口
 * 
 * @author Ben-P
 *
 * @param <T>
 */
public interface BaseInterceptor<T> {

	public boolean interceptor(T object, Method method, Object... params);

}
