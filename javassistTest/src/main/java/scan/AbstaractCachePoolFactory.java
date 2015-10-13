package scan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstaractCachePoolFactory implements CachePoolFactory {

	protected static final Map<Object, Object> CACHE = new HashMap<Object, Object>();

	static final AbstaractCachePoolFactory newInstance = new AbstaractCachePoolFactory() {
	};

	private AbstaractCachePoolFactory() {
	}

	public static final AbstaractCachePoolFactory newInstance() {
		return newInstance;
	}

	public Map<Object, Object> getCache() {
		return CACHE;
	}

	public <T> T get(Object o) {
		return (T) getCache().get(o);
	}

	public Object put(Object o, Object value) {
		return getCache().put(o, value);
	}

	public Object putList(Object o, Object... values) {
		return getCache().put(o, Arrays.asList(values));
	}

	public <T> T getNFloor(Object key, Integer... floor) throws Exception {
		List<Object> temp;
		int size = floor.length;
		temp = (List<Object>) getCache().get(key);
		for (int i = 0; i < size - 1; i++) {
			temp = (List<Object>) temp.get(floor[i]);
		}

		return (T) temp.get(floor[size - 1]);
	}

	public Integer[] addNFloor(Object key, Object value, Integer... floor) throws Exception {
		Object obj = getCache().get(key);
		List<Object> temp = null;
		if (null == obj) {
			temp = new ArrayList<Object>();
			getCache().put(key, temp);
		}
		else
			temp = (List<Object>) obj;
		obj = null;
		List<Object> X = null;
		int size = floor.length;
		List<Integer> path = new ArrayList<Integer>();
		for (int i = 0; i < size - 1; i++) {
			//长度
			if (floor[i] < temp.size()) {
				obj = temp.get(floor[i]);
				if (null == obj) {
					temp.set(floor[i], obj = new ArrayList<Object>());
					temp = (List<Object>) obj;
				}
				else {
					temp = (List<Object>) obj;
				}
				path.add(floor[i]);
			}
			//大于
		}
		return path.toArray(new Integer[0]);
	}

	public static void main(String[] args) throws Exception {
		ArrayList<Integer> ax = new ArrayList<Integer>();
		ax.add(99);
		ax.set(0, 44);
		System.out.println(ax.get(0));
		AbstaractCachePoolFactory a = newInstance;
		a.putList("小明", "a", 1, 2, 3, 4, 5, 6, 7, "小B");
		System.out.println(a.get("小明"));
		Integer[] A1 = a.addNFloor("学校分级", "野鸡小学", 1);
		Integer[] A2 = a.addNFloor("学校分级", "野鸡小学1-1班", 1, 1);
		Integer[] A3 = a.addNFloor("学校分级", "野鸡小学1-2班", 1, 1);
		Integer[] A4 = a.addNFloor("学校分级", "野鸡小学2", 2);
		System.out.println(a.getNFloor("学校分级", A1));
		System.out.println(a.getNFloor("学校分级", A2));
		System.out.println(a.getNFloor("学校分级", A3));
		System.out.println(a.getNFloor("学校分级", A4));
		Object o = a.get("学校分级");
		System.out.println(a.get("学校分级"));
	}
}
