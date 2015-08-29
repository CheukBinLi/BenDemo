package com.ben.ScanToPackage.abandoned;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;

import com.ben.ScanToPackage.pool.DefaultCacheFactory;
import com.ben.ScanToPackage.pool.PoolFactory;

public class ScanClass2015_7_17 {

	// private static Map<String, ClassAttribute4JDK> clazzAttribute = new HashMap<String, ClassAttribute4JDK>();
	static ScanClass2015_7_17 newInstance = new ScanClass2015_7_17();

	public static ScanClass2015_7_17 newInstance() {
		return newInstance;
	}

	private ZipFile zipFile;

	final Map<String, ClassAttribute> ClassQueue = new HashMap<String, ClassAttribute>();

	public static void main(String[] args) throws Exception {
		// Enumeration<URL> en = Thread.currentThread().getContextClassLoader().getResources("com");
		// while (en.hasMoreElements())
		// System.out.println(en.nextElement().getPath().replaceAll("file:", ""));
		//
		// JarFile jarFile = new JarFile("file:/D:/Repository/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar".replaceAll("file:", ""));
		Map<String, ClassAttribute> a = ScanClass2015_7_17.newInstance.scanToPackage(/* "com/ben" */"org/springframework");
		// scanToPackage("org/springframework");
		// scanClass(clazzAttribute);

		PoolFactory.instance().add(DefaultCacheFactory.SCAN_CLASS, ScanClass2015_7_17.newInstance.scanToPackage("com/ben"));

		for (Entry<String, ClassAttribute> en : ((Map<String, ClassAttribute>) PoolFactory.instance().get(DefaultCacheFactory.SCAN_CLASS)).entrySet())
			System.out.println(en.getKey());

	}

	public Map<String, ClassAttribute> scanToPackage(String path) throws Exception {

		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
		Map<String, ClassAttribute> classManager = new HashMap<String, ClassAttribute>();
		String FilePath = path.replaceAll("\\\\", ".").replaceAll("/", ".");
		String JarPath = path.replaceAll("\\\\", "/");
		while (urls.hasMoreElements()) {
			URL u = urls.nextElement();
			if (u.getProtocol().toLowerCase().equals("jar")) {
				classManager.putAll(scanJarFile(u.getPath(), path));
			} else {
				classManager.putAll(scanFile(new File(u.getPath()), FilePath, true));
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
	public Map<String, ClassAttribute> scanJarFile(String path, String substring) throws IOException {
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
				ClassQueue.put(className, new ClassAttribute(className, className.substring(className.lastIndexOf(".") + 1)));
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
	private Map<String, ClassAttribute> scanFile(File file, String substring, boolean clearQueue) {
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
				ClassQueue.put(className, new ClassAttribute(className, f.getName().substring(0, f.getName().lastIndexOf(".class"))));
			}
		}
		return ClassQueue;
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
