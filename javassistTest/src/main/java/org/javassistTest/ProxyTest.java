package org.javassistTest;

import java.lang.reflect.Proxy;

public class ProxyTest {

	public static void main(String[] args) {
		ProxyHandler ph = new ProxyHandler();

		AA a = (AA) Proxy.newProxyInstance(AAX.class.getClassLoader(), AAX.class.getInterfaces(), ph);

		System.out.println(a.x());
	}
}
