package com.ben.CarManager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 调用方法的权限限制
 * 
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Inherited
public @interface Authority {

	public String code();// 权限代码

	public String errorMsg();// 返回错误消息

	public boolean isActive() default true;// 启动
}
