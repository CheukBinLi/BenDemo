package com.ben.ScanToPackage.test;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class CreateFieldAndMethod {

	static CreateFieldAndMethod newInstance = new CreateFieldAndMethod();

	public static CreateFieldAndMethod newInstance() {
		return newInstance;
	}

	public String objectToString(CtField ctField) throws NotFoundException {
		StringBuffer sb = new StringBuffer();
		if (Modifier.isPublic(ctField.getModifiers()))
			sb.append("public ");
		else if (Modifier.isPublic(ctField.getModifiers()))
			sb.append("private ");
		else if (Modifier.isProtected(ctField.getModifiers()))
			sb.append("protected ");
		if (Modifier.isFinal(ctField.getModifiers()))
			sb.append("final ");
		if (Modifier.isStatic(ctField.getModifiers()))
			sb.append("static ");
		if (Modifier.isSynchronized(ctField.getModifiers()))
			sb.append("synchronized ");
		if (Modifier.isTransient(ctField.getModifiers()))
			sb.append("transient ");
		if (Modifier.isVolatile(ctField.getModifiers()))
			sb.append("volatile ");
		if (Modifier.isStrict(ctField.getModifiers()))
			sb.append("strictfp ");
		if (Modifier.isAbstract(ctField.getModifiers()))
			sb.append("strictfp ");
		if (Modifier.isAnnotation(ctField.getModifiers()))
			sb.append("strictfp ");
		sb.append(ctField.getType().getName() + " " + ctField.getName());
		return sb.toString();
	}

	public String objectToString(CtMethod ctMethod, String appendSuffix) throws NotFoundException {
		StringBuffer sb = new StringBuffer();
		if (Modifier.isPublic(ctMethod.getModifiers()))
			sb.append("public ");
		else if (Modifier.isPublic(ctMethod.getModifiers()))
			sb.append("private ");
		else if (Modifier.isProtected(ctMethod.getModifiers()))
			sb.append("protected ");
		if (Modifier.isFinal(ctMethod.getModifiers()))
			sb.append("final ");
		if (Modifier.isStatic(ctMethod.getModifiers()))
			sb.append("static ");
		if (Modifier.isSynchronized(ctMethod.getModifiers()))
			sb.append("synchronized ");
		if (Modifier.isTransient(ctMethod.getModifiers()))
			sb.append("transient ");
		if (Modifier.isVolatile(ctMethod.getModifiers()))
			sb.append("volatile ");
		if (Modifier.isStrict(ctMethod.getModifiers()))
			sb.append("strictfp ");
		if (Modifier.isAbstract(ctMethod.getModifiers()))
			sb.append("strictfp ");
		if (Modifier.isAnnotation(ctMethod.getModifiers()))
			sb.append("strictfp ");
		// sb.append(ctMethod.getReturnType().getName() + " " + ctMethod.getName() + "(");
		sb.append(String.format("%s %s%s(", ctMethod.getReturnType().getName(), ctMethod.getName(), (null != appendSuffix ? appendSuffix : "")));
		CtClass[] params = ctMethod.getParameterTypes();
		for (int i = 1; i < params.length + 1; i++) {
			// sb.append((i == 1 ? "" : ",") + params[i - 1].getName() + " $" + i);
			sb.append(String.format("%s %s $%s", i == 1 ? "" : ",", params[i - 1].getName(), i));
		}
		sb.append(")");
		CtClass[] exceptions = ctMethod.getExceptionTypes();
		if (exceptions.length > 0)
			sb.append("throws ");
		for (int i = 0; i < exceptions.length; i++) {
			// sb.append((i == 0 ? "" : ",") + exceptions[i].getName());
			sb.append(String.format("%s%s", i == 0 ? "" : ",", exceptions[i].getName()));
		}
		sb.append("{}");
		return sb.toString();
	}

	/***
	 * 
	 * @param className
	 * @param method
	 * @return A.class.invoke(a,x);
	 * @throws NotFoundException
	 */
	public String getMethodInvokeString(String className, CtMethod method) throws NotFoundException {
		StringBuffer sb = new StringBuffer();
		CtClass[] params = method.getParameterTypes();
		String returnTypeName = method.getReturnType().getName();

		// clazz.getName()+".class.getDeclaredMethod(\""+m.getName()+"\").invoke("+clazz.getName()+".class.newInstance(),null);
		sb.append(String.format("%s.class.getDeclaredMethod(\"%s\",", className, method.getName()));
		String type = null;
		if (params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				type = params[i].getName();
				if (type.equals(Integer.TYPE.getName())) {
					type = "Integer";
				} else if (type.equals(Boolean.TYPE.getName())) {
					type = "Boolean";
				} else if (type.equals(Byte.TYPE.getName())) {
					type = "Byte";
				} else if (type.equals(Float.TYPE.getName())) {
					type = "Float";
				} else if (type.equals(Double.TYPE.getName())) {
					type = "Double";
				} else if (type.equals(Short.TYPE.getName())) {
					type = "Short";
				} else if (type.equals(Character.TYPE.getName())) {
					type = "Character";
				} else if (type.equals(Long.TYPE.getName())) {
					type = "Long";
				}
				sb.append((i == 0 ? "" : ",") + type + ".class");
			}
		} else
			sb.append("null");
		sb.append(")");
		// ).invoke(%s.class.newInstance(),
		sb.append(String.format(".invoke(%s.class.newInstance(),null)", className));
		// ##################################
		// 前加return //后加结束符

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
		System.err.println(sb.toString());
		return sb.toString();
	}
}
