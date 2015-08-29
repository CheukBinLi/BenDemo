package org.ScanPath;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.ben.CarManager.core.entity.a;

public class t1 {

	public static void main(String[] args) throws IOException {
		a v = new a();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		System.out.println(cl.getResource(""));
		Enumeration<URL> us = cl.getResources("com/ben/");
		while (us.hasMoreElements())
			System.out.println(us.nextElement().toString());
		us = cl.getSystemResources("com/ben/");
		while (us.hasMoreElements())
			System.out.println(us.nextElement().toString());

	}
}
