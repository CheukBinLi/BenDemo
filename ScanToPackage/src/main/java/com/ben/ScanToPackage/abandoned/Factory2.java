package com.ben.ScanToPackage.abandoned;

import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassMap;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import com.ben.ScanToPackage.pool.DefaultCacheFactory;
import com.ben.ScanToPackage.pool.PoolFactory;
import com.ben.ScanToPackage.proxy.BaseClassProxyHandler;
import com.ben.ScanToPackage.test.A;
import com.ben.ScanToPackage.test.H;

public class Factory2 {

	public void x() throws NotFoundException, CannotCompileException, IOException {

		Class a = A.class;
		Class handler = H.class;
		ClassPool pool = ClassPool.getDefault();
		// 新
		CtClass ctClass = pool.get(a.getName());
		ctClass.defrost();
		ctClass.setName(ctClass.getName() + "$proxy");
		// ctClass.addConstructor(CtNewConstructor.make("{}", ctClass));
		// 继承
		ctClass.setSuperclass(pool.get(a.getName()));
		// 添加handler`2
		// CtField handlerInterfaceCtField = CtField.make("private com.ben.ScanToPackage.test.HI handler=new " + handler.getName() + "();", ctClass);
		CtField handlerInterfaceCtField = new CtField(pool.get("com.ben.ScanToPackage.proxy.BaseClassProxyHandler"), "handler", ctClass);
		handlerInterfaceCtField.setModifiers(Modifier.PRIVATE);
		ctClass.addField(handlerInterfaceCtField, "new " + handler.getName() + "()");
		// 方法修改
		CtMethod[] ctMethods = ctClass.getDeclaredMethods();
		CtMethod ctNewMethod = null;

		for (CtMethod m : ctMethods) {
			ctNewMethod = CtNewMethod.copy(m, ctClass, null);
			ctNewMethod.setName(m.getName() + "$proxy");
			ctClass.addMethod(ctNewMethod);
			if (m.getName().equals("a1")) {
				m.setBody("{" + convertReturnType(m.getReturnType().getName(), "$proceed(this,(java.lang.reflect.Method)DefaultCacheFactory.instance().get(\"" + getMethod(m, "$proxy") + "\"),null)") + "}", "handler", "invokHandler");
			}
		}

		PoolFactory.instance().add("AAAA", ctClass.toClass());
		// ctClass.writeFile("D:/Desktop/");

	}

	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException, IOException {
		PoolFactory.instance().add("public java.lang.String a1$proxy()1", "123");
		System.out.println(PoolFactory.instance().get("public java.lang.String a1$proxy()1"));
		Factory2 f = new Factory2();
		f.x();
		for (java.lang.reflect.Method m : ((Class) PoolFactory.instance().get("AAAA")).getDeclaredMethods()) {
			if (m.getName().contains("$proxy")) {
				// System.out.println(f.getMethod(m, null));
				PoolFactory.instance().add(f.getMethod(m, null), m);
			}
		}
		Class a = PoolFactory.instance().get("AAAA");
		A exA = (A) a.newInstance();
		Object o = PoolFactory.instance().get("public java.lang.String a1$proxy()");
		System.out.println(exA.a1());
		// for(Method m:)
		// Object[] o = { 1, 2, 3 };
		// Object o = com.ben.ScanToPackage.pool.DefaultCacheFactory.instance().getByMap("classname", "fullmethodname");
	}

	public String getMethod(CtMethod ctMethod, String appendSuffix) throws NotFoundException {
		// HI h=new H();
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.reflect.Modifier.toString(ctMethod.getModifiers()) + " ");
		sb.append(ctMethod.getReturnType().getName() + " ");
		sb.append(ctMethod.getName() + (null == appendSuffix ? "" : appendSuffix));
		sb.append("(");
		for (CtClass c : ctMethod.getParameterTypes()) {
			sb.append(c.getName() + " ");
		}
		if (sb.charAt(sb.length() - 1) == ' ')
			sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		System.err.println(sb.toString());
		return sb.toString();
	}

	public String getMethod(java.lang.reflect.Method method, String appendSuffix) {
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.reflect.Modifier.toString(method.getModifiers()) + " ");
		sb.append(method.getReturnType().getName() + " ");
		sb.append(method.getName() + (null == appendSuffix ? "" : appendSuffix));
		sb.append("(");
		for (Class c : method.getParameterTypes()) {
			sb.append(c.getName() + " ");
		}
		if (sb.charAt(sb.length() - 1) == ' ')
			sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public String convertReturnType(String returnTypeName, String invokeBody) {
		StringBuffer sb = new StringBuffer(invokeBody);
		if (returnTypeName.equals(Boolean.TYPE.getName())) {
			sb.insert(0, "return ((Boolean)");
			sb.append(").booleanValue()");
		} else if (returnTypeName.equals(Byte.TYPE.getName())) {
			sb.insert(0, "return ((Byte)");
			sb.append(").byteValue()");
		} else if (returnTypeName.equals(Float.TYPE.getName())) {
			sb.insert(0, "return ((Float)");
			sb.append(").floatValue()");
		} else if (returnTypeName.equals(Double.TYPE.getName())) {
			sb.insert(0, "return ((Double)");
			sb.append(").doubleValue()");
		} else if (returnTypeName.equals(Short.TYPE.getName())) {
			sb.insert(0, "return ((Short)");
			sb.append(").shortValue()");
		} else if (returnTypeName.equals(Character.TYPE.getName())) {
			sb.insert(0, "return ((Character)");
			sb.append(").charValue()");
		} else if (returnTypeName.equals(Long.TYPE.getName())) {
			sb.insert(0, "return ((Long)");
			sb.append(").longValue()");
		} else if (returnTypeName.equals(Integer.TYPE.getName())) {
			sb.insert(0, "return ((Integer)");
			sb.append(").intValue()");
		} else if (returnTypeName.equals("void")) {
		} else {
			sb.insert(0, "return (" + returnTypeName + ")");
		}
		sb.append(";");
		return sb.toString();
	}

}
