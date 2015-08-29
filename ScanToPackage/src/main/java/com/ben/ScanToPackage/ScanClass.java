package com.ben.ScanToPackage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.annotation.AnnotationAttributes;
import org.xml.sax.SAXException;

import com.ben.ScanToPackage.abandoned.ClassAttribute;
import com.ben.ScanToPackage.annotation.Register;
import com.ben.ScanToPackage.annotation.handler.AutoLoadHadler;
import com.ben.ScanToPackage.pool.ObjectCacheFactory;
import com.ben.ScanToPackage.pool.ObjectCacheFactory;
import com.ben.ScanToPackage.pool.PoolFactory;
import com.ben.ScanToPackage.test.AutoLoadTest;
import com.ben.ScanToPackage.test.AutoLoadTestI;
import com.ben.ScanToPackage.util.XmlReader;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;

public final class ScanClass {

	static final ScanClass newInstance = new ScanClass();

	public static ScanClass newInstance() {
		return newInstance;
	}

	private ScanClass() {
		super();
		try {
			//读取池配置
			String PoolInstance = new XmlReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream("ScanClassContext.xml"), "Context").get("PoolFactory").get(0).toString();
			//先修改池
			ClassPool pool = ClassPool.getDefault();
			CtClass ctClass = pool.get("com.ben.ScanToPackage.pool.PoolFactory");
			//			ctClass.defrost();
			CtField ctField = ctClass.getDeclaredField("cacheFactory");
			ctClass.removeField(ctField);
			ctClass.addField(ctField, String.format("%s.instance()", PoolInstance));
			ctClass.toClass();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	public static void main(String[] args) {
	//		ScanClass.newInstance();
	//		ObjectCacheFactory o = PoolFactory.instance();
	//		System.out.println(null == o);
	//	}

	private ZipFile zipFile;

	final List<String> ClassQueue = new ArrayList<String>();

	public static void main(String[] args) throws Exception {

		PoolFactory.instance().add(ObjectCacheFactory.SCAN_CLASS, ScanClass.newInstance.scanToPackage("com/ben"));

		//		for (String str : (List<String>) PoolFactory.instance().get(ObjectCacheFactory.SCAN_CLASS)) {
		//			System.out.println(str);
		//		}

		newInstance.classFilter((List<String>) PoolFactory.instance().get(ObjectCacheFactory.SCAN_CLASS));
		AutoLoadHadler autoLoadHadler = new AutoLoadHadler();
		for (Entry<String, Object[]> en : ((Map<String, Object[]>) PoolFactory.instance().get(ObjectCacheFactory.CLASS_$_PROXY)).entrySet()) {
			PoolFactory.instance().adds(PoolFactory.instance().arrays(autoLoadHadler.process((CtClass) en.getValue()[0]), en.getValue()[1]), ObjectCacheFactory.CLASS_$_PROXY, en.getKey());
			//			PoolFactory.instance().adds(autoLoadHadler.process((CtClass) en.getValue()[0], en.getValue()[1]).toClass(), ObjectCacheFactory.CLASS_$_PROXY, en.getKey());
		}
		for (Entry<String, Object[]> en : ((Map<String, Object[]>) PoolFactory.instance().get(ObjectCacheFactory.CLASS_$_PROXY)).entrySet()) {
			PoolFactory.instance().adds(((CtClass) en.getValue()[0]).toClass(), ObjectCacheFactory.CLASS_$_PROXY, en.getKey());
		}
		Object o = PoolFactory.instance().gets(ObjectCacheFactory.CLASS_$_PROXY, AutoLoadTest.class.getName());
		//		AutoLoadTest alt = (AutoLoadTest) ((CtClass) ((Object[]) PoolFactory.instance().gets(ObjectCacheFactory.CLASS_$_PROXY, AutoLoadTest.class.getName()))[0]).toClass().newInstance();
		AutoLoadTest alt = (AutoLoadTest) ((Class) PoolFactory.instance().gets(ObjectCacheFactory.CLASS_$_PROXY, AutoLoadTest.class.getName())).newInstance();
		alt.sayHello();
	}

	public List<String> scanToPackage(String path) throws Exception {

		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
		List<String> classManager = new ArrayList<String>();
		String FilePath = path.replaceAll("\\\\", ".").replaceAll("/", ".");
		String JarPath = path.replaceAll("\\\\", "/");
		while (urls.hasMoreElements()) {
			URL u = urls.nextElement();
			if (u.getProtocol().toLowerCase().equals("jar")) {
				classManager.addAll(scanJarFile(u.getPath(), path));
			} else {
				classManager.addAll(scanFile(new File(u.getPath()), FilePath, true));
			}
		}
		return classManager;
	}

	/***
	 * 
	 * @param path
	 *            file://e:/a.jar
	 * @param substring
	 *            com/ben
	 * @throws IOException
	 */
	private List<String> scanJarFile(String path, String substring) throws IOException {
		ClassQueue.clear();
		path = path.substring(0, path.lastIndexOf("!")).replaceAll("file:", "");
		zipFile = new ZipFile(path);
		Enumeration<ZipEntry> zips = (Enumeration<ZipEntry>) zipFile.entries();
		String className = null;
		while (zips.hasMoreElements()) {
			ZipEntry temp = zips.nextElement();
			if (!temp.isDirectory() && temp.getName().endsWith(".class") && temp.getName().substring(0, substring.length()).equals(substring)) {
				className = temp.getName();
				className = className.substring(className.indexOf(substring), className.lastIndexOf(".class")).replaceAll("\\\\", ".").replaceAll("/", ".");
				ClassQueue.add(className);
			}
		}
		return ClassQueue;
	}

	/***
	 * 
	 * @param file
	 *            /e:/a.jar
	 * 
	 * @param substring
	 *            com/ben
	 * 
	 * @param clearQueue
	 *            是否清空队列常量ClassQueue
	 * 
	 * @return
	 */
	private List<String> scanFile(File file, String substring, boolean clearQueue) {
		if (clearQueue)
			ClassQueue.clear();
		File[] filelis = file.listFiles();
		String className;
		for (File f : filelis) {
			if (f.isDirectory())
				scanFile(f, substring, false);
			else if (f.getName().endsWith(".class")) {
				className = f.getAbsolutePath().replaceAll("\\\\", ".").replaceAll("/", ".");
				className = className.substring(className.indexOf(substring), className.lastIndexOf(".class"));
				ClassQueue.add(className);
			}
		}
		return ClassQueue;
	}

	/***
	 * 过滤末注册class文件
	 * 
	 * @param classes
	 */
	private void classFilter(List<String> classes) {

		ClassPool pool = ClassPool.getDefault();
		Class register = Register.class;
		CtClass ctClass = null;
		for (String clazz : classes) {
			try {
				ctClass = pool.get(clazz);
				Object isRegister = ctClass.getAnnotation(register);
				// AnnotationsAttribute aa = (AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
				// aa.getAnnotation(type)
				if (null == isRegister)
					continue;
				// 添加到代理组
				PoolFactory.instance().adds(PoolFactory.instance().arrays(ctClass, ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag)), ObjectCacheFactory.CLASS_$_PROXY, ctClass.getName());
//				System.err.println(ctClass.getName());
				// 搜索annotation
				// method
				for (CtMethod m : ctClass.getDeclaredMethods()) {
					AnnotationsAttribute aa = (AnnotationsAttribute) m.getMethodInfo().getAttribute(AnnotationsAttribute.visibleTag);
					if (null == aa)
						continue;
					for (javassist.bytecode.annotation.Annotation a : aa.getAnnotations()) {
						// annotation-annotationClassName-className-method-MethodName-method
						PoolFactory.instance().adds(PoolFactory.instance().arrays(m, a), ObjectCacheFactory.ANNOTATION, a.getTypeName(), ctClass.getName(), ObjectCacheFactory.METHOD, m.getName(), m);
					}
				}
				// field
				for (CtField f : ctClass.getDeclaredFields()) {
					AnnotationsAttribute aa = (AnnotationsAttribute) f.getFieldInfo().getAttribute(AnnotationsAttribute.visibleTag);
					if (null == aa)
						continue;
					for (javassist.bytecode.annotation.Annotation a : aa.getAnnotations()) {
						// annotation-annotationClassName-className-field-fieldName-Field
						PoolFactory.instance().adds(PoolFactory.instance().arrays(f, a), ObjectCacheFactory.ANNOTATION, a.getTypeName(), ctClass.getName(), ObjectCacheFactory.FIELD, f.getName());
					}
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		PoolFactory.instance().add(ObjectCacheFactory.SCAN_CLASS, null);
		//		System.out.println(PoolFactory.instance().get(ObjectCacheFactory.ANNOTATION));

	}

	private Map<String, ClassAttribute> scanClass(Map<String, ClassAttribute> clazzAttribute) {
		ClassAttribute tempClassAttribute4CT = null;
		ClassPool classPool = ClassPool.getDefault();
		CtClass ctClass;
		Map<CtField, AnnotationsAttribute> tempFields;
		Map<CtMethod, AnnotationsAttribute> tempMethods;
		// class注解
		for (Entry<String, ClassAttribute> entry : clazzAttribute.entrySet()) {
			try {
				tempClassAttribute4CT = entry.getValue();
				ctClass = classPool.get(entry.getKey());
				// annotation
				tempClassAttribute4CT.setAnnotations((AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag));
				// field
				tempFields = new HashMap<CtField, AnnotationsAttribute>();
				for (CtField tempCtField : ctClass.getDeclaredFields()) {
					tempFields.put(tempCtField, (AnnotationsAttribute) tempCtField.getFieldInfo().getAttribute(AnnotationsAttribute.visibleTag));
				}
				tempClassAttribute4CT.setField(tempFields);
				// method
				tempMethods = new HashMap<CtMethod, AnnotationsAttribute>();
				for (CtMethod tempCtMethod : ctClass.getDeclaredMethods()) {
					tempMethods.put(tempCtMethod, (AnnotationsAttribute) tempCtMethod.getMethodInfo().getAttribute(AnnotationsAttribute.visibleTag));
				}
				tempClassAttribute4CT.setMethod(tempMethods);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			clazzAttribute.put(entry.getKey(), tempClassAttribute4CT);
		}
		return clazzAttribute;
	}
}
