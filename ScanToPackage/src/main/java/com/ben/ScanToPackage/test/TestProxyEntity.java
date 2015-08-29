package com.ben.ScanToPackage.test;

import com.ben.ScanToPackage.annotation.AutoLoad;

public class TestProxyEntity {

	@AutoLoad
	private AutoLoadTestI autoLoadTest;

	public void sayhello() {
		autoLoadTest.sayHello();
	}

}
