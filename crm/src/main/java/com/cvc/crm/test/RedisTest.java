package com.cvc.crm.test;

import com.cvc.crm.util.RDSUtils;

public class RedisTest {

	public static void main(String[] args) {
		System.out.println("test redis server");
		System.out.println(RDSUtils.set("mykey", "qwer1234"));
		System.out.println(RDSUtils.get("mykey"));
		System.out.println(RDSUtils.del("mykey"));
		System.out.println(RDSUtils.get("mykey"));
		System.out.println(RDSUtils.set("mykey", "qwer"));
		System.out.println(RDSUtils.get("mykey"));
	}

}
