package com.ben.ScanToPackage.test;

import org.hibernate.annotations.BatchSize;

public class A$proxy extends A implements AI {

	@BatchSize(size = 101010)
	private final String aaaa = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	private H handler;

	public A$proxy() {
		try {
			this.handler = new H();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void a0$proxy() {
	}

	public String a1$proxy() {
		return "a1";
	}

	public String a2$proxy() {
		return "a2";
	}

	public String a3$proxy() {
		return "a3";
	}

	public Integer[] a5$proxy() {
		return null;
	}

	public int[] a4$proxy() {
		return null;
	}

	public byte a6$proxy() {
		return 0;
	}

	public float a7$proxy() {
		return 0.0F;
	}

	public boolean a8$proxy() {
		return false;
	}

	public long a9$proxy() {
		return 0L;
	}

	public short a10$proxy() {
		return 0;
	}

	public char a11$proxy() {
		return '\000';
	}

	public String a3$proxy(int i) {
		return null;
	}

	public String a3$proxy(int i, String a) {
		return null;
	}

	public String a3$proxy(int i, int a) {
		return null;
	}

	public String a3$proxy(String a, int z) {
		return null;
	}

	public String a3x$proxy(String a, int z) {
		return a;
	}

	public void a0() {
		try {
			this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
	}

	public String a1() {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a2() {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a3() {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public Integer[] a5() {
		Integer[] arrayOfInteger = null;
		try {
			arrayOfInteger = (Integer[]) this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return arrayOfInteger;
	}

	public int[] a4() {
		int[] arrayOfInt = null;
		try {
			arrayOfInt = (int[]) this.handler.invokHandler(this, null, null);
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return arrayOfInt;
	}

	public byte a6() {
		byte b = (byte) 0;
		try {
			b = ((Byte) this.handler.invokHandler(this, null, null)).byteValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return b;
	}

	public float a7() {
		float f = 0;
		try {
			f = ((Float) this.handler.invokHandler(this, null, null)).floatValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return f;
	}

	public boolean a8() {
		boolean bool = false;
		try {
			bool = ((Boolean) this.handler.invokHandler(this, null, null)).booleanValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return bool;
	}

	public long a9() {
		long l = 0;
		try {
			l = ((Long) this.handler.invokHandler(this, null, null)).longValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return l;
	}

	public short a10() {
		short s = (short) 0;
		try {
			s = ((Short) this.handler.invokHandler(this, null, null)).shortValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return s;
	}

	public char a11() {
		char c = (char) 0;
		try {
			c = ((Character) this.handler.invokHandler(this, null, null)).charValue();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return c;
	}

	public String a3(int paramInt) {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, new Object[] { paramInt });
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a3(int paramInt, String paramString) {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, new Object[] { paramInt, paramString });
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a3(int paramInt1, int paramInt2) {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, new Object[] { paramInt1, paramInt2 });
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a3(String paramString, int paramInt) {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, new Object[] { paramString, paramInt });
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}

	public String a3x(String paramString, int paramInt) {
		String str = null;
		try {
			str = (String) this.handler.invokHandler(this, null, new Object[] { paramString, paramInt });
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return str;
	}
}