package org.javassistTest;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class CreateFieldAndMethod {

	static final CreateFieldAndMethod newInstance = new CreateFieldAndMethod();

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

	public String objectToString(CtMethod ctMethod) throws NotFoundException {
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
		sb.append(String.format("%s %s(", ctMethod.getReturnType().getName(), ctMethod.getName()));
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
}
