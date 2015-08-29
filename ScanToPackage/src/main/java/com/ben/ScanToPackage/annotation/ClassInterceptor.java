package com.ben.ScanToPackage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 拦截器
 * 
 * @ClassInterceptor (interceptorHandler="com.ben.interceptorHandlerImpl")
 * @author Ben-P
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface ClassInterceptor {
	/***
	 * 
	 * @return 拦截器实现类
	 */
	String interceptorHandlerClass();
}
