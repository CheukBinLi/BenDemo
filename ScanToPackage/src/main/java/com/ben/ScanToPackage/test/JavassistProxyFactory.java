package com.ben.ScanToPackage.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
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

public class JavassistProxyFactory {

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
		// newCtClass.freeze();
		// HandlerInterface
		//CtField handlerInterfaceCtField = CtField.make("private JavassistProxy.HI handler=" + handler.getName() + ";", newCtClass);
		StringBuffer params = new StringBuffer();
		for (CtMethod m : newCtClass.getDeclaredMethods()) {
			System.err.println(java.lang.reflect.Modifier.toString(m.getModifiers()));
			System.out.println(m.toString());
			params.setLength(0);
			Object o = m.getParameterTypes();
			for (CtClass c : m.getParameterTypes())
				params.append(c.getName());
			MS1.put(m.getName() + " " + params.toString(), null);
			// Object o = m.getParameterTypes();
			// System.out.println(CreateFieldAndMethod.newInstance().getMethodInvokeString(newCtClass.getName(), m));
			// System.out.println("{System.out.println(\"" + newCtClass.getName() + "$JavassistProxy\");return "+clazz.getName()+".class.getDeclaredMethod(\""+m.getName()+"\").invoke("+clazz.getName()+".class.newInstance(),$$);}");
			// if (m.getReturnType().getName().equals("void"))
			// m.setBody("{System.out.println(\"" + newCtClass.getName() + "$JavassistProxy\");}");
			// // m.setBody(String.format("{return JavassistProxy.B.class.getDeclaredMethod(\"%s\").invoke(JavassistProxy.B.class.newInstance(), null);}", args));
			// else
			// // m.setBody("{System.out.println(\"" + newCtClass.getName() + "$JavassistProxy\");return "+clazz.getName()+".class.getDeclaredMethod(\""+m.getName()+"\").invoke("+clazz.getName()+".class.newInstance(),null);}");
			// // m.setBody("{return JavassistProxy.B.class.getDeclaredMethods()[0].getName();}");
			// m.setBody("{return JavassistProxy.B.class.getDeclaredMethod(\"" + m.getName() + "\",null).getName()+\"javassist!\";}");
			// m.setExceptionTypes(new CtClass[] { classPool.get("java.lang.InstantiationException") });
			// m.setExceptionTypes(new CtClass[] { classPool.get("java.lang.IllegalAccessException") });
			// // m.setExceptionTypes(new CtClass[] { classPool.get("java.lang.Throwable") });
			// m.setBody(CreateFieldAndMethod.newInstance().getMethodInvokeString(clazz.getName(), m));
		}
		// newCtClass.defrost();
		a.put("a", newCtClass.toClass());

	}

	public static void main(String[] args) throws Exception {

		// Object xxx = A.class.getDeclaredMethod("a3", int);

		System.out.println(A.class.getDeclaredMethods()[0]);
//		Object o = JavassistProxy.A.class.getDeclaredMethod("a0").invoke(JavassistProxy.A.class.newInstance(), null);
		new JavassistProxyFactory().x();
		AI ai = (AI) a.get("a").newInstance();
		System.out.println(ai.a1());
		AI a = new A();
		System.out.println(a.a1());
		Method[] ms = A.class.getDeclaredMethods();
		StringBuffer sb = new StringBuffer();
		for (Method m : ms) {
			sb.setLength(0);
			sb.append(m.getParameterTypes().toString());
			// for (Class c : m.getParameterTypes()) {
			// sb.append(c.getName());
			// }
			MS0.put(m.getName() + " " + sb.toString(), null);
		}

		for (Entry<String, Object> en : MS0.entrySet()) {
			System.err.println(en.getKey());
		}
		for (Entry<String, Object> en : MS1.entrySet()) {
			System.out.println(en.getKey());
		}

		System.out.println(java.lang.reflect.Modifier.toString(1));
	}
}
