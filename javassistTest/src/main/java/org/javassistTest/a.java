package org.javassistTest;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.bytecode.annotation.MemberValue;

import com.ben.ScanToPackage.annotation.*;

public class a {

	public void s() throws Exception {

		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("java.util.UUID");
		CtClass class1 = pool.get("org.javassistTest.App");
		class1.defrost();
		// 注解
		AnnotationsAttribute o = (AnnotationsAttribute) class1.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
		System.out.println("class注解：" + o.toString());

		Annotation classAnnotation = new Annotation("org.springframework.stereotype.Component", class1.getClassFile().getConstPool());
		o.addAnnotation(classAnnotation);

		class1.getClassFile().addAttribute(o);

		System.out.println(class1.getInterfaces()[0].getName());
		CtField f = class1.getDeclaredField("str");
		String callBacktype = f.getType().getName();
		String fieldName = f.getName();
		System.out.println("name:" + f.getName());
		System.out.println("name:" + Modifier.isPrivate(f.getModifiers()));
		System.out.println("name:" + Modifier.isStatic(f.getModifiers()));
		System.out.println("callBacktype:" + callBacktype);
		class1.removeField(f);
		// System.err.println("private " + cal**lBacktype + " " + fieldName + "=\"hello\";");
		f = CtField.make("private " + callBacktype + " " + fieldName + "=UUID.randomUUID().toString();", class1);
		class1.addField(f);

		// static string
		f = class1.getDeclaredField("aa");
		callBacktype = f.getType().getName();
		fieldName = f.getName();
		// String modified1=
		// 直接修改方法
		CtMethod m = class1.getDeclaredMethod("a");
		m.setBody("{System.out.println(str);System.out.println(\"ok\");System.err.println(\"wahaha\");}");

		f = class1.getField("ax");
		Object[] ctAnnotations = f.getAnnotations();
		FieldInfo fieldInfo = f.getFieldInfo();
		AnnotationsAttribute aa = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
		MemberValue mv = null;
		String memberName = null;
		for (Annotation a : aa.getAnnotations()) {
			System.err.println(a.getTypeName());
			Set<String> set = a.getMemberNames();
			if (null != set) {
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					memberName = it.next();
					mv = a.getMemberValue(memberName);
					System.out.println(memberName + " : " + mv.toString());
				}
			}
			// System.err.println(a.getMemberNames());
		}
		FieldInfo protypeFieldInfo = f.getFieldInfo();
		class1.removeField(f);

		String fieldStr = objectToString(f);
		System.out.println(fieldStr);
		System.err.println(Modifier.isStrict(f.getModifiers()));
		System.err.println(fieldStr + "=\"小小的喇叭\";");
		f = CtField.make(fieldStr + "=\"小小的喇叭\";", class1);

		pool.importPackage("com.ben.ScanToPackage.annotation.*");
		fieldInfo = f.getFieldInfo();
		AnnotationsAttribute xAnnotationsAttribute = new AnnotationsAttribute(fieldInfo.getConstPool(), AnnotationsAttribute.visibleTag);
		// 添加注解
		Annotation xAnnotation = new Annotation(Transient.class.getName(), fieldInfo.getConstPool());
		System.out.println("aaaaa:" + Transient.class.getName());
		xAnnotationsAttribute.addAnnotation(xAnnotation);
		// fieldInfo.addAttribute(xAnnotationsAttribute);
		// // 注解
		// Iterator<String> it;
		// MemberValue memberValue;
		// for (Annotation tempA : aa.getAnnotations()) {
		// xAnnotation = new Annotation(tempA.getTypeName(), fieldInfo.getConstPool());
		// Set<String> set = tempA.getMemberNames();
		// if (null != set) {
		// it = set.iterator();
		// while (it.hasNext()) {
		// memberName = it.next();
		// xAnnotation.addMemberValue(memberName, tempA.getMemberValue(memberName));
		// }
		// }
		// xAnnotationsAttribute.addAnnotation(xAnnotation);
		// }

		// fieldInfo.addAttribute(xAnnotationsAttribute);
		fieldInfo.addAttribute(getObjectAnnotationsAttribute(protypeFieldInfo, fieldInfo, xAnnotation));
		class1.addField(f);

		// method(删除再添加)--代理
		CtMethod ctMethod = class1.getDeclaredMethod("a2");
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		// 获取旧方法字符串
		String methodName = objectToString(ctMethod);
		// 旧方法改名
		ctMethod.setName("$1" + methodInfo.getName());
		// class1.removeMethod(ctMethod);
		System.out.println(methodName);
		ctMethod = CtNewMethod.make(methodName, class1);
		ctMethod.setBody("{System.out.println(\"修改过的A2!\");" + "$1" + ctMethod.getName() + "($$);}");
		class1.addMethod(ctMethod);

		class1.toClass();
		class1.freeze();

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

	public static AnnotationsAttribute getObjectAnnotationsAttribute(FieldInfo prototypeFieldInfo, FieldInfo newFieldInfo, Annotation... annotations) {
		AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) prototypeFieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
		AnnotationsAttribute result = new AnnotationsAttribute(newFieldInfo.getConstPool(), AnnotationsAttribute.visibleTag);
		String memberName;
		Set<String> set = null;
		Iterator<String> it;
		for (Annotation a : annotationsAttribute.getAnnotations()) {
			set = a.getMemberNames();
			if (null != set) {
				it = set.iterator();
				while (it.hasNext()) {
					memberName = it.next();
					a.addMemberValue(memberName, a.getMemberValue(memberName));
				}
			}
			result.addAnnotation(a);
		}
		for (Annotation a : annotations)
			result.addAnnotation(a);

		return result;
	}

	public static AnnotationsAttribute getObjectAnnotationsAttribute(MethodInfo prototypeMethodInfo, MethodInfo newMethodInfo, Annotation... annotations) {
		AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) prototypeMethodInfo.getAttribute(AnnotationsAttribute.visibleTag);
		AnnotationsAttribute result = new AnnotationsAttribute(newMethodInfo.getConstPool(), AnnotationsAttribute.visibleTag);
		String memberName;
		Set<String> set = null;
		Iterator<String> it;
		for (Annotation a : annotationsAttribute.getAnnotations()) {
			set = a.getMemberNames();
			if (null != set) {
				it = set.iterator();
				while (it.hasNext()) {
					memberName = it.next();
					a.addMemberValue(memberName, a.getMemberValue(memberName));
				}
			}
			result.addAnnotation(a);
		}
		for (Annotation a : annotations)
			result.addAnnotation(a);

		return result;
	}

	public static void main(String[] args) throws Exception {
		Date start = new Date();
		Date finishJavassist;
		a a1 = new a();
		a1.s();
		System.out.println("运行javassist时间:" + (new Date().getTime() - start.getTime()));
		finishJavassist = new Date();
		new App().a();
		System.out.println("运行1:" + (new Date().getTime() - finishJavassist.getTime()));
		finishJavassist = new Date();
		new App().a();
		System.out.println("运行2:" + (new Date().getTime() - finishJavassist.getTime()));
		finishJavassist = new Date();
		new App().a();
		System.out.println("运行4:" + (new Date().getTime() - finishJavassist.getTime()));
		finishJavassist = new Date();
		System.out.println(new App().ax);

		Field f = App.class.getDeclaredField("ax");
		java.lang.annotation.Annotation[] ax = f.getAnnotations();

		for (java.lang.annotation.Annotation A : ax) {
			System.err.println(A.toString());
		}

		new App().a2("111111111111111111111111", 1, null);

		java.lang.annotation.Annotation[] annotations = App.class.getDeclaredAnnotations();
		for (java.lang.annotation.Annotation a : annotations)
			System.out.println(a);
		
		Object o=com.ben.CarManager.model.App.class.getGenericSuperclass();
		for (Class c : com.ben.CarManager.model.App.class.getClasses())
			System.out.println(c);

	}
}
