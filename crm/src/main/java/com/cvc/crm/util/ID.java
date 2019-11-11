package com.cvc.crm.util;

import java.util.List;

public final class ID {

	private static final String KEY = "id_incr_index";
	private volatile static int index;

	private ID() {
	}

	public static String id() {
		try {
			return id1();
		} catch (Exception e) {
			return id2();
		}
	}

	public static String id1() {

		Long incr = RDSUtils.incr(KEY);
		if (incr > 99999) {
			synchronized (ID.class) {
				if (incr > 99999) {
					RDSUtils.del(KEY);
					incr = RDSUtils.incr(KEY);
				}
			}
		}

		List<String> time = RDSUtils.time();

		String t0 = time.get(0);
		String t1 = time.get(1);

		return t0 + t1.substring(0, 3) + String.format("%05d", incr);

	}

	public static String id2() {
		if (index < 0 || index > 99999) {
			synchronized (ID.class) {
				if (index < 0 || index > 99999) {
					index = 0;
				}
			}
		}
		return Util.millis() + String.format("%05d", index++);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(id());
		}
	}

}
