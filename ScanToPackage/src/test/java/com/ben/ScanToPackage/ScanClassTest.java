package com.ben.ScanToPackage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

public class ScanClassTest {

	public static void main(String[] args) throws Exception {
//		new ScanClassTest().s();
		
		String a="1111111111uuuuppppppppppppppppp...2123123";
		System.out.println(a.substring(a.indexOf("u"), a.lastIndexOf(".")));
		
	}

	public void s() throws Exception {

		System.out.println(ClassLoader.getSystemClassLoader().getResource(""));
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getSystemResources("com/ben/CarManager/model");
		while (urls.hasMoreElements()) {
			System.err.println(urls.nextElement().getPath());
		}
		// JarFile jarFile = new JarFile("D:/Repository/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar");
		// Enumeration<JarEntry> zipen = jarFile.entries();
		// String name = null;
		// while (zipen.hasMoreElements()) {
		// name = zipen.nextElement().getName();
		// if (name.endsWith(".class"))
		// System.out.println(name);
		// }

		Map<String, Object> clazz = new HashMap<String, Object>();
		File f = new File("/E:/JavaProject/Eclipse/CarManager/model/target/classes/com/ben/CarManager/model");

		getClassFile(f, clazz);

		/** javassist start */
		Map<String, Class> ctMap = new HashMap<String, Class>();

		ClassPool classPool = ClassPool.getDefault();

		CtClass ctClass = classPool.get("com.ben.ScanToPackage.XTest2");
		ctClass.defrost();
		CtConstructor[] ctConstructor = ctClass.getConstructors();
		System.out.println(ctConstructor.length);
		ctConstructor[0].setBody("{System.out.println(\"javassist修改！\");}");
		ctMap.put("com.ben.ScanToPackage.ScanClass", ctClass.toClass());
		// ctClass.toClass();
		ctClass.freeze();
		new XTest2();
		// //修改
		//
		// //新建
		// CtClass ctClass2=classPool.get("com.ben.ScanToPackage.XTest2");
		// ctClass2.

		/** javassist end */
		for (Entry<String, Object> en : clazz.entrySet())
			System.out.println(en.getKey());

		Field field = this.getClass().getDeclaredField("a");
		System.err.println(field.getType());
		System.err.println(field.getType().getName());

	}

	public Map<String, Object> getClassFile(File f, Map<String, Object> data) {
		File[] fl = f.listFiles();
		for (File tempFile : fl) {
			if (tempFile.isDirectory()) {
				getClassFile(tempFile, data);
				continue;
			}
			else if (tempFile.getName().endsWith(".class"))
				data.put(tempFile.getPath(), tempFile);
		}
		return data;
	}
}
