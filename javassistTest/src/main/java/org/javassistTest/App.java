package org.javassistTest;

import java.io.IOException;

import javax.persistence.Access;
import javax.persistence.Basic;

import org.hibernate.annotations.Any;
import org.hibernate.secure.spi.GrantedPermission;
import org.hibernate.secure.spi.JaccService;
import org.hibernate.secure.spi.PermissibleAction;
import org.hibernate.secure.spi.PermissionCheckEntityInformation;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ben.ScanToPackage.annotation.Register;

/**
 * Hello world!
 *
 */
@Repository
@Service
public class App extends Thread implements JaccService {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	private String str;

	private static String aa;

	@Basic
	static String ax = null;

	private String a;

	public void a() {
	}

	@Basic
	@SafeVarargs
	public void a2(String x, int u, StringBuffer sb) {
		System.out.println(x);
	}

	public void addPermission(GrantedPermission arg0) {
	}

	public void checkPermission(PermissionCheckEntityInformation arg0, PermissibleAction arg1) {
	}
}
