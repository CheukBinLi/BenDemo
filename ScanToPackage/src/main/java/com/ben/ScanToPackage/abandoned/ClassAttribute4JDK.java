package com.ben.ScanToPackage.abandoned;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeSet;

public class ClassAttribute4JDK {

	private String fullName;
	private String aliasName;
	private Map<String, Annotation> annotations;
	private Map<Field, Map<String, Annotation>> field;
	private Map<Method, Map<String, Annotation>> method;

	public ClassAttribute4JDK() {
	}

	public ClassAttribute4JDK(String fullName, String aliasName) {
		this.fullName = fullName;
		this.aliasName = aliasName;
		System.out.println(this.fullName + "  " + this.aliasName);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Map<String, Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<String, Annotation> annotations) {
		this.annotations = annotations;
	}

	public Map<Field, Map<String, Annotation>> getField() {
		return field;
	}

	public void setField(Map<Field, Map<String, Annotation>> field) {
		this.field = field;
	}

	public Map<Method, Map<String, Annotation>> getMethod() {
		return method;
	}

	public void setMethod(Map<Method, Map<String, Annotation>> method) {
		this.method = method;
	}

}
