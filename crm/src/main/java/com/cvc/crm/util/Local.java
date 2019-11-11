package com.cvc.crm.util;

import java.util.HashMap;
import java.util.Map;

public class Local {

	private static final ThreadLocal<Map<Object, Object>> local = new ThreadLocal<Map<Object, Object>>() {
		protected Map<Object, Object> initialValue() {
			return new HashMap<Object, Object>();
		}
	};

	public static Map<Object, Object> get() {
		return local.get();
	}

	public static Object get(Object key) {
		return get().get(key);
	}

	public static void put(Object key, Object value) {
		get().put(key, value);
	}

	public static Object id() {
		return get("id");
	}

}
