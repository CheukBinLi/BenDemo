package com.ben.ScanToPackage.abandoned;

import java.util.Map;

import javassist.CtField;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;

public class ClassAttribute {

	private String fullName;
	private String aliasName;
	private AnnotationsAttribute annotations;
	private Map<CtField, AnnotationsAttribute> field;
	private Map<CtMethod, AnnotationsAttribute> method;

	public ClassAttribute() {
	}

	public ClassAttribute(String fullName, String aliasName) {
		this.fullName = fullName;
		this.aliasName = aliasName;
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

	public AnnotationsAttribute getAnnotations() {
		return annotations;
	}

	public void setAnnotations(AnnotationsAttribute annotations) {
		this.annotations = annotations;
	}

	public Map<CtField, AnnotationsAttribute> getField() {
		return field;
	}

	public void setField(Map<CtField, AnnotationsAttribute> field) {
		this.field = field;
	}

	public Map<CtMethod, AnnotationsAttribute> getMethod() {
		return method;
	}

	public void setMethod(Map<CtMethod, AnnotationsAttribute> method) {
		this.method = method;
	}

}
