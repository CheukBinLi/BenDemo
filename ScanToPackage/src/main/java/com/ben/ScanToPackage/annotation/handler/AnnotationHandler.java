package com.ben.ScanToPackage.annotation.handler;

import javassist.CtClass;

public interface AnnotationHandler {
	CtClass process(CtClass ctClass, Object... obj) throws Throwable;
}
