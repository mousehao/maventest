package util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Huawei
 *
 */
public class ThreadLocalMapUtil {
	private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<Map<String, Object>>();

	public static void put(String key, Object value) {
		Map<String, Object> item = threadContext.get();
		if (item == null) {
			item = new HashMap<String, Object>();
		}
		item.put(key, value);
		threadContext.set(item);
	}

	public static Object get(String key) {
		Map<String, Object> item = threadContext.get();
		Object result = null;
		if (item != null) {
			result = item.get(key);
		}
		return result;
	}

	public static void main(String[] args) {

	}
}
