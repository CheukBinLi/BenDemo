package com.ben.ScanToPackage.abandoned;

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
import javassist.NotFoundException;

import com.ben.ScanToPackage.pool.DefaultCacheFactory;
import com.ben.ScanToPackage.pool.PoolFactory;
import com.ben.ScanToPackage.proxy.BaseClassProxyHandler;
import com.ben.ScanToPackage.test.A;
import com.ben.ScanToPackage.test.H;

public class Factory {

	public void x() throws NotFoundException, CannotCompileException {

		Class a = A.class;
		Class handler = H.class;
		ClassPool pool = ClassPool.getDefault();
		// 新
		CtClass ctClass = pool.get(a.getName());
		ctClass.setName(ctClass.getName() + "$proxy");
		//		ctClass.addConstructor(CtNewConstructor.make("{}", ctClass));
		// 继承
		ctClass.setSuperclass(pool.get(a.getName()));
		// 添加handler
		CtField handlerInterfaceCtField = CtField.make("private com.ben.ScanToPackage.test.H handler=" + handler.getName() + ".class.newInstance();", ctClass);
		ctClass.addField(handlerInterfaceCtField);
		// 方法修改
		CtMethod[] ctMethods = ctClass.getDeclaredMethods();
		CtMethod ctNewMethod = null;

		// for (CtField f : ctClass.getDeclaredFields()) {
		// System.out.println(java.lang.reflect.Modifier.toString(f.getModifiers()) + " " + f.getName());
		// }

		for (CtMethod m : ctMethods) {
			System.err.println(getMethod(m));
			// 改名
			// 方法加入pool
			// if()
			// ClassMap classMap = new ClassMap();
			// classMap.put("$this", "this");
			// classMap.put("$pool", "com.ben.ScanToPackage.pool.DefaultCacheFactory.instance().getByMap(\"classname\", \"fullmethodname\")");
			ctNewMethod = CtNewMethod.copy(m, ctClass, null);
			String params = "";
			if (ctNewMethod.getParameterTypes().length > 0)
				for (int i = 1; i < ctNewMethod.getParameterTypes().length + 1; i++) {
					if (i > 1)
						params += ",";
					params += "$" + i;
				}
			// System.out.println("{ return  $proceed(this,null" + (params.length() > 0 ? ",new Object[]{" + params + "}" : ",null") + ");}");
			System.out.println("{ " + (m.getReturnType().getName().equals("void") ? "" : "return ") + "$proceed(this,(java.lang.reflect.Method)com.ben.ScanToPackage.pool.DefaultCacheFactory.instance().get(\"" + getMethod(m) + "\")" + (params.length() > 0 ? ",new Object[]{" + params + "}" : ",null") + ");}");
			// m.setBody("{ "+(m.getReturnType().getName().equals("void")?"":"return ")+"$proceed(this,(java.lang.reflect.Method)com.ben.ScanToPackage.pool.DefaultCacheFactory.instance().get(\"" + getMethod(m) + "\")" + (params.length() > 0 ? ",new Object[]{" + params + "}" : ",null") + ");}", "handler", "invokHandler");
			//
			//
			// m.setBody("{ "+(m.getReturnType().getName().equals("void")?"":"return (java.lang.Object)")+"$proceed(this,null" + (params.length() > 0 ? ",new java.lang.Object[]{" + params + "}" : ",null") + ");}", "handler", "invokHandler");
			ctNewMethod.setName(m.getName() + "$proxy");
			ctClass.addMethod(ctNewMethod);
			m.setBody("{" + (m.getReturnType().getName().equals("void") ? "" : "return ") + "$proceed($$);}", "this", m.getName() + "$proxy");
		}
		PoolFactory.instance().add("AAAA", ctClass.toClass());

	}

	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
		Factory f = new Factory();
		f.x();
		for (java.lang.reflect.Method m : A.class.getDeclaredMethods()) {
			// System.out.println(f.getMethod(m));
			PoolFactory.instance().add(f.getMethod(m), m);
		}
		Class a = PoolFactory.instance().get("AAAA");
		A exA = (A) a.newInstance();
		// exA.a3();
		// for(Method m:)
		Object[] o = { 1, 2, 3 };
		// Object o = com.ben.ScanToPackage.pool.DefaultCacheFactory.instance().getByMap("classname", "fullmethodname");
	}

	public String getMethod(CtMethod ctMethod) throws NotFoundException {
		// HI h=new H();
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.reflect.Modifier.toString(ctMethod.getModifiers()) + " ");
		sb.append(ctMethod.getReturnType().getName() + " ");
		sb.append(ctMethod.getName());
		sb.append("(");
		for (CtClass c : ctMethod.getParameterTypes()) {
			sb.append(c.getName() + " ");
		}
		if (sb.charAt(sb.length() - 1) == ' ')
			sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public String getMethod(java.lang.reflect.Method method) {
		StringBuffer sb = new StringBuffer();
		sb.append(java.lang.reflect.Modifier.toString(method.getModifiers()) + " ");
		sb.append(method.getReturnType().getName() + " ");
		sb.append(method.getName());
		sb.append("(");
		for (Class c : method.getParameterTypes()) {
			sb.append(c.getName() + " ");
		}
		if (sb.charAt(sb.length() - 1) == ' ')
			sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
}
