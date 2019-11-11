package com.cvc.crm.util;

public class Secret {

	// 单向加密
	public static String encrypt(String input) {

		if (input == null) {
			return null;
		}

		String prefix = "ead63962eea543b9"; // 干扰字符（前）
		String suffix = "a2300863a375e32c"; // 干扰字符（后）
		String newStr = prefix + input + suffix; // 组成新字符串

		try {
			return Util.md5(prefix + Util.sha1(newStr) + suffix);
		} catch (Exception e) {
			return null;
		}

	}

}
