package com.ben.ScanToPackage.annotation.handler;

import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;

import com.ben.ScanToPackage.annotation.AutoLoad;
import com.ben.ScanToPackage.annotation.Register;
import com.ben.ScanToPackage.pool.ObjectCacheFactory;
import com.ben.ScanToPackage.pool.PoolFactory;

/***
 * 自动装载字节码处理
 * 
 * @author Ben-P
 *
 */
public class AutoLoadHadler implements AnnotationHandler {

	static Class handlerClassName = AutoLoad.class;

	public CtClass process(CtClass ctClass, Object... obj) throws NotFoundException, CannotCompileException {

		//		ClassPool pool = ClassPool.getDefault();
		//		//		CtClass c = pool.get("com.ben.ScanToPackage.test.TestProxyEntity");
		//		CtClass c2 = pool.get("com.ben.ScanToPackage.test.AI");
		//		CtClass c3 = pool.get("com.ben.ScanToPackage.test.AutoLoadTest");
		//		CtField ctField = c3.getDeclaredField("a");
		//		System.out.println();
		//		System.out.println(ctField.getType().getName() + "   " + c2.getName());
		//		System.err.println(ctField.getType().equals(c2));
		//		System.out.println();

		//检查是否有autoload注解....缓存字段
		Object o = PoolFactory.instance().gets(ObjectCacheFactory.ANNOTATION, handlerClassName.getName(), ctClass.getName(), ObjectCacheFactory.FIELD);
		//		System.out.println(PoolFactory.instance().get(ObjectCacheFactory.ANNOTATION));
		if (null == o) {
			return ctClass;
		}
		//		System.err.println(o);
		Object x = null;
		CtField tempField;
		CtClass tempClass;
		AnnotationsAttribute annotationsAttribute;
		Annotation tempAnnotations;
		for (Entry<String, Object[]> en : ((Map<String, Object[]>) o).entrySet()) {
			//			Object mm0 = en.getKey();
			//			Object[] mm = en.getValue();
			tempField = (CtField) en.getValue()[0];
			tempAnnotations = (Annotation) en.getValue()[1];
			x = PoolFactory.instance().gets(ObjectCacheFactory.ANNOTATION, ObjectCacheFactory.AUTO_LOAD_X_TABLE, tempField.getType().getName());
			//			System.err.println("auto:" + en.getKey() + " type:" + ((CtField) en.getValue()[0]).getType().getName());
			//			System.out.println(x);
			if (null == x) {
				for (Entry<String, Object[]> registerClass : ((Map<String, Object[]>) PoolFactory.instance().get(ObjectCacheFactory.CLASS_$_PROXY)).entrySet()) {
					x = registerClass.getValue();
					if (x instanceof Class) {
						continue;
					}
					tempClass = (CtClass) registerClass.getValue()[0];
					annotationsAttribute = (AnnotationsAttribute) registerClass.getValue()[1];
					if (tempClass.isInterface())
						continue;
					if (tempField.getType().getName().equals(tempClass.getName()) || isIn4Interface(tempField.getType().getName(), tempClass) || tempField.getType().getName().equals(tempClass.getSuperclass().getName())) {
						if (tempField.getName().equals(annotationsAttribute.getAnnotation("com.ben.ScanToPackage.annotation.Register").getMemberValue("alias").toString().replaceAll("\"", "")) || tempField.getName().equals(classNameToShortNameAndFirstTOLower(tempClass.getName()))) {
							//System.out.println(tempField.getName() + " : " + tempClass.getName());
							//加入交叉缓存表
							//修改字节码
							ctClass.removeField(tempField);
							ctClass.addField(tempField, String.format("new %s()", tempClass.getName()));
						}
					}
				}
			}
		}
		//找对autoload缓存交叉表
		//一个一个匹配
		//修改对字段

		// private s a=new a();
		//		ClassPool pool = ClassPool.getDefault();
		//		CtClass c = pool.get("com.ben.ScanToPackage.test.TestProxyEntity");
		//		CtClass c2 = pool.get("com.ben.ScanToPackage.test.AutoLoadTestI");
		//		CtClass c3 = pool.get("com.ben.ScanToPackage.test.AutoLoadTest");
		//		CtField ctField = c3.getDeclaredField("a");
		//		System.err.println(ctField.getType().equals(c2));
		//		CtField ctField = c.getDeclaredField("autoLoadTest");
		//		// 1
		//		System.out.println(c2.isInterface());// 是否接口
		//		// 2
		//		System.err.println(ctField.getType().getName().equals(c2.getName()));// 是否同一个类
		//		// 2
		//		System.err.println(ctField.getType().getName().equals(c3.getInterfaces()[0].getName()));// 是否属于同个个派生树
		//		// 2
		//		System.err.println(c2.getSuperclass().getName());// 是否继承
		//		// 3
		//		/** 首字母转小写 c[0]+=32; */
		//		char[] chars = c3.getName().substring(c3.getName().lastIndexOf(".") + 1).toCharArray();
		//		System.out.println(new String(chars));
		//		chars[0] += 32;
		//		System.out.println(new String(chars));
		//		System.out.println(ctField.getName().toLowerCase().equals(new String(chars)));
		//		// 3
		//		// 名字都转小写校验
		//		System.out.println(ctField.getName().toLowerCase().equals(c3.getName().substring(c3.getName().lastIndexOf(".") + 1).toLowerCase()));
		//
		//		// for (int i = 0; i < 200; i++) {
		//		// System.out.println(i + " : " + (char) i);
		//		// }
		//		try {
		//			ctClass.writeFile("d:/Desktop/");
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		return ctClass;
	}

	public static void main(String[] args) throws NotFoundException {
		// new AutoLoadHadler().x0();
	}

	protected boolean isIn4Interface(String name, CtClass ctClass) throws NotFoundException {
		for (CtClass c : ctClass.getInterfaces()) {
			if (name.equals(c.getName()))
				return true;
		}
		return false;
	}

	protected String classNameToShortNameAndFirstTOLower(String str) {
		char[] chars = str.substring(str.lastIndexOf(".") + 1).toCharArray();
		chars[0] += 32;
		return new String(chars);
	}
}
