package JavassistProxy;

import java.lang.reflect.Method;

public interface HI<T> {

	public T invokHandler(Object obj, Method method, Object... params) throws Throwable;

}
