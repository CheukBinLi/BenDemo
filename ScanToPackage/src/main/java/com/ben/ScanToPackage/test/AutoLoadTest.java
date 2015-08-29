package com.ben.ScanToPackage.test;

import com.ben.ScanToPackage.annotation.AutoLoad;
import com.ben.ScanToPackage.annotation.Register;

@Register
public class AutoLoadTest implements AutoLoadTestI {

	@AutoLoad
	private AI a123;

	public void sayHello() {
		System.out.println(this.getClass().getName() + ":hello !");
		System.out.println(null == a123);
		System.out.println(a123.a2());
	}

}
