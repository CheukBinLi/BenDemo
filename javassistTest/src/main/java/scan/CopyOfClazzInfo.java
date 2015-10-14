package scan;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import create.old;

/***
 * 类信息
 * @author hnbh
 *
 */
public class CopyOfClazzInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Class clazz;
	private Map<Class, Annotation> classAnnotation;
	private Map<String, CopyOfClazzInfo.FieldInfo> Fields;
	private Map<String, CopyOfClazzInfo.MethodInof> Methods;

	public CopyOfClazzInfo(Class c) {
		this.clazz = c;
	}

	static class FieldInfo {
		private Field field;
		private Map<Class, Annotation> fieldAnnotation;

		public FieldInfo(Field field) {
			super();
			this.field = field;
			this.fieldAnnotation = scanAnnotation(field);
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public Map<Class, Annotation> getFieldAnnotation() {
			return fieldAnnotation;
		}

		public void setFieldAnnotation(Map<Class, Annotation> fieldAnnotation) {
			this.fieldAnnotation = fieldAnnotation;
		}

	}

	static class MethodInof {
		private Method method;
		private Map<Class, Annotation> methodAnnotation;

		public MethodInof(Method method) {
			super();
			this.method = method;
			this.methodAnnotation = scanAnnotation(method);
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public Map<Class, Annotation> getMethodAnnotation() {
			return methodAnnotation;
		}

		public void setMethodAnnotation(Map<Class, Annotation> methodAnnotation) {
			this.methodAnnotation = methodAnnotation;
		}

	}

	protected static final Map<Class, Annotation> scanAnnotation(Object o) {
		Annotation[] A = null;
		if (o instanceof Class)
			A = ((Class) o).getDeclaredAnnotations();
		else if (o instanceof Method)
			A = ((Method) o).getDeclaredAnnotations();
		else if (o instanceof Field)
			A = ((Field) o).getDeclaredAnnotations();
		else
			return null;
		Map<Class, Annotation> objAnnotation = new HashMap<Class, Annotation>();
		for (Annotation a : A) {
			objAnnotation.put(a.annotationType(), a);
		}
		return objAnnotation;
	}

	public Map<Class, Annotation> getClassAnnotation() {
		return classAnnotation;
	}

	public void setClassAnnotation(Map<Class, Annotation> classAnnotation) {
		this.classAnnotation = classAnnotation;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Map<String, CopyOfClazzInfo.FieldInfo> getFields() {
		return Fields;
	}

	public void setFields(Map<String, CopyOfClazzInfo.FieldInfo> fields) {
		Fields = fields;
	}

	public Map<String, CopyOfClazzInfo.MethodInof> getMethods() {
		return Methods;
	}

	public void setMethods(Map<String, CopyOfClazzInfo.MethodInof> methods) {
		Methods = methods;
	}

	public static void main(String[] args) {
		new CopyOfClazzInfo(old.class).scanAnnotation(old.class);
	}
}
