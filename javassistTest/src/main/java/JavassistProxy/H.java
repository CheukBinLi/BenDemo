package JavassistProxy;

import java.lang.reflect.Method;

public class H implements HI {

	public Object invokHandler(Object obj, Method method, Object... params) throws Throwable {
		System.out.println("start");
		Object o = method.invoke(obj, params);
		System.out.println("finish");
		return o;
	}
}
