package com.ben.ScanToPackage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 
 * 动态代理
 * 
 * @ClassProxy (handlerClass="com.ben.ScanToPackage.proxy.defaultHadlerImpl")
 * @author Ben-P
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface ClassProxy {
	/***
	 * 
	 * @return 动态代理实现类
	 */
	String handlerClass();

}
