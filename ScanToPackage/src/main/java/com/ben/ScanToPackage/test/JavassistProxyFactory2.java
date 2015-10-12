package com.ben.ScanToPackage.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassMap;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstantAttribute;

public class JavassistProxyFactory2 {

	static Map<String, Class> a = new HashMap<String, Class>();

	static Map<String, Object> MS0 = new HashMap<String, Object>();

	static Map<String, Object> MS1 = new HashMap<String, Object>();

	static java.util.Map<java.lang.String, java.lang.reflect.Field> fields = new java.util.HashMap<java.lang.String, java.lang.reflect.Field>();

	static java.util.Map<java.lang.String, java.lang.reflect.Method> method = new java.util.HashMap<java.lang.String, java.lang.reflect.Method>();

	public void x() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {

		Class clazz = A.class;
		Class handler = H.class;
		ClassPool classPool = ClassPool.getDefault();
		Map<String, Integer> Methods = new HashMap<String, Integer>();
		Method[] ms = clazz.getDeclaredMethods();
		for (int i = 0; i < ms.length; i++)
			Methods.put(ms[i].getName(), i);
		// 旧
		CtClass newCtClass = classPool.get(clazz.getName());
		// 新
		newCtClass.setName(newCtClass.getName() + "$JavassistProxy");
		newCtClass.setSuperclass(classPool.get(clazz.getName()));
		// newCtClass.freeze();
		// HandlerInterface
		CtField handlerInterfaceCtField = CtField.make("private com.ben.ScanToPackage.test.HI handler=" + handler.getName() + ";", newCtClass);
		CtMethod[] ctMethods = newCtClass.getDeclaredMethods();
		CtMethod newMethod = null;
		String methodStr = null;
		for (CtMethod m : ctMethods) {
			// methodStr = String.format("%s %s(a)", java.lang.reflect.Modifier.toString(m.getModifiers()), m.getName(),);
			ClassMap cm = new ClassMap();
			if (m.getName().equals("a3x")) {
				m.setName(m.getName() + "$proxy");
				newMethod = CtNewMethod.make("public void a3x(java.lang.String $1,int $2){ return $proceed($$);}", newCtClass, "this", m.getName());
				newCtClass.addMethod(newMethod);
			}
		}
		a.put("aaaa", newCtClass.toClass());
		try {
			newCtClass.writeFile("C:/Users/hnbh/Desktop");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		// Object xxx = A.class.getDeclaredMethod("a3", int);

		new JavassistProxyFactory2().x();
		// AI ai = (AI) a.get("a").newInstance();

		System.out.println(java.lang.reflect.Modifier.toString(1));

		A a1 = (A) a.get("aaaa").newInstance();

		System.out.println(a1.a3x("sdasdasd", 1));

		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
	}
}
