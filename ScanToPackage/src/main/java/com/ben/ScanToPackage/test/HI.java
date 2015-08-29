package com.ben.ScanToPackage.test;

import java.lang.reflect.Method;

public interface HI {

	public <T>T invokHandler(Object obj, Method method, Object... params) throws Throwable;

}
