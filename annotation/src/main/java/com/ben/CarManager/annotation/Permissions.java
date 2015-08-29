package com.ben.CarManager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Inherited
public @interface Permissions {
	/***
	 * 编号
	 * 
	 * @return
	 */
	String code();

	/***
	 * 描述
	 * 
	 * @return
	 */
	String describe() default "";

	/***
	 * 错误提示
	 * 
	 * @return
	 */
	String errorMsg() default "No relevant permissions";

	/***
	 * 是否启动
	 * 
	 * @return
	 */
	boolean struts() default true;
}
