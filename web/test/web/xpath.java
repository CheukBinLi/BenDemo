package web;

import java.net.URISyntaxException;

public class xpath {

	public static void main(String[] args) throws URISyntaxException {
		// Object o = ClassLoader.getSystemClassLoader().getResource("");
		// File f = new File(new URI(o.toString()));
		// File[] fl = f.listFiles();
		// System.out.println(o);
		// for (File t : fl)
		// System.out.println(t.getName());

		Object o = System.getProperty("java.ext.dirs");
		System.err.println(o.toString());

	}

}
