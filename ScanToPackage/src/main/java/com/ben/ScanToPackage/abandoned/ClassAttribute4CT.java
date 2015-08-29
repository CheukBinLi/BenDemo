package com.ben.ScanToPackage.abandoned;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeSet;

import javassist.CtField;
import javassist.CtMethod;

public class ClassAttribute4CT {

	private String fullName;
	private String aliasName;
	private Map<String, Annotation> annotations;
	private Map<CtField, Map<String, Annotation>> field;
	private Map<CtMethod, Map<String, Annotation>> method;

	public ClassAttribute4CT() {
	}

	public ClassAttribute4CT(String fullName, String aliasName) {
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

	public Map<CtField, Map<String, Annotation>> getField() {
		return field;
	}

	public void setField(Map<CtField, Map<String, Annotation>> field) {
		this.field = field;
	}

	public Map<CtMethod, Map<String, Annotation>> getMethod() {
		return method;
	}

	public void setMethod(Map<CtMethod, Map<String, Annotation>> method) {
		this.method = method;
	}

}
