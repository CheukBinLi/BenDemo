package com.ben.ScanToPackage.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ben.ScanToPackage.proxy.BaseClassProxyHandler;

public class H implements BaseClassProxyHandler {

	public H() {
		super();
	}

	public Object invokHandler(Object obj, Method method, Object... params) {
		System.out.println("start");
		Object o = 0;
		try {
			o = method.invoke(obj, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("finish");
		System.out.println("shit");
		return o;
	}
}
