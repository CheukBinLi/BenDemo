package scan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class scan {

	public static Map<String, List<String>> doScan(String path) throws IOException {
		String[] paths = null;
		if (path.contains("*"))
			paths = path.split("\\*");
		else
			paths = new String[] { path };
		final String pathPattern = ("^" + path).replace("/*/", "/[^/]*/").replace("**", "*.*");
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(paths[0]);
		while (urls.hasMoreElements()) {
			URL u = urls.nextElement();
			String a = u.getFile();
			boolean b = a.matches(pathPattern);
			System.out.println(b);
			//if (u.getFile().matches(pathPattern)) {
				System.out.println(u.toString());
				if (u.getProtocol().equals("jar"))
					try {
						JarFile jarFile = new JarFile(new File(u.getPath().substring(0, u.getPath().lastIndexOf("!")).replaceAll("file:", "")));
						Enumeration<JarEntry> jars = jarFile.entries();
						while (jars.hasMoreElements()) {
							JarEntry jarEntry = jars.nextElement();
							if (jarEntry.getName().endsWith(".class") && isInIt(paths, jarEntry.getName())) {
								// System.err.println(jarEntry.getName() + " " + path + "  :" + jarEntry.getName().contains(path));
								System.out.println("-----------:" + jarEntry.getName());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
//		}
		return null;
	}

	private static boolean isInIt(final String[] path, final String filePath) {
		for (String str : path)
			if (!filePath.contains(str))
				return false;
		return true;
	}

	public static void main(String[] args) throws IOException {
		// doScan("antlr/actions");
		doScan("com/*/net");

		WeakHashMap<String, List<String>> weakHashMap = new WeakHashMap<String, List<String>>();
		WeakHashMap<String, String> weakHashMap1 = new WeakHashMap<String, String>();
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		weakHashMap.put("1", list);
		weakHashMap1.put("x", "xxxxxxxxxx");
		list = null;
		System.gc();
		System.out.println(weakHashMap.get("1").get(2));
		System.out.println(weakHashMap1.get("x"));

		String a = "org/a/aspectj/UnlockSignatureImpl.class";

		System.out.println(a.matches("^org/[^/]*/aspectj/*.*"));

		String b = "asdf/*/asdf/**";
		b = b.replace("/*/", "/[^/]*/");
		b = b.replace("**", ".*");
		System.out.println(b);
	}
}
