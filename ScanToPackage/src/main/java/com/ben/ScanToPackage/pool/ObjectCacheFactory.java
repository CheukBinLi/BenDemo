package com.ben.ScanToPackage.pool;

public interface ObjectCacheFactory {

	/***
	 * List<String>
	 */
	public final String SCAN_CLASS = "SCAN_CLASS";

	/***
	 * MAP<String,Class>
	 */
	public final String CLASS_$_PROXY = "CLASS";

	/***
	 * MAP<String,MAP<String,Method>> 第一节类全名 java.lang.String 第二节类方法全名:public void a(int boolen)
	 */
	public final String METHOD_$_PROXY = "METHOD$PROXY";

	/***
	 * MAP<String,MAP<String,MAP<String,Object>>> 第一节类全名 com.ben.ScanToPackage.annotation.register 第二节类方法全名:类名 第三节 方法/字段全名 private int a(int) : return CtMethod/CtField
	 */
	public final String ANNOTATION = "ANNOTATION";

	public final String METHOD = "METHOD";

	public final String FIELD = "FIELD";

	/***
	 * AUTO_LOAD_X_TABLE缓存交叉表
	 * <p>
	 * MAP<String,MAP<String,String>>
	 * <p>
	 * 第一节：接口/基类
	 * <p>
	 * 第二节: K:实现类/自身/子类（短名首子母小写）:V全名
	 */
	public final String AUTO_LOAD_X_TABLE = "AUTO_LOAD_X_TABLE";

	public <T> T get(Object key);

	public void add(Object key, Object Value);

	public <T> T gets(Object... key);

	public void adds(Object Value, Object... key);

	public Object arrays(Object... obj);

}
