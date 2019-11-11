package com.cvc.crm.test;

import java.util.Date;

import com.cvc.crm.dao.UserDao;
import com.cvc.crm.model.User;
import com.cvc.crm.util.Secret;
import com.cvc.crm.util.Util;




public class UserTest {

	public static void main(String[] args) throws Exception {

		User o = new User(Util.id(), "xiaoming", Secret.encrypt("123465"), 0, 1, "1", "2018-07-12 15:16:13", new Date(), "2018-07-12 15:16:13", new Date());

		UserDao dao = new UserDao();

		dao.insert(o);

		System.out.println("--- End ---");

	}

}
