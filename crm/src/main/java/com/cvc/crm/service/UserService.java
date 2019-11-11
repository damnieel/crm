package com.cvc.crm.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cvc.crm.dao.UserDao;
import com.cvc.crm.model.User;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Util;



public class UserService {

	private UserDao userDao;

	public UserService() {
		userDao = new UserDao();
	}

	public int save(User o) throws SQLException {
		return userDao.insert(o);
	}

	public int delete(String... ids) throws SQLException {
		int row = 0;
		for (String id : ids) {
			if (!Util.isWord(id, 1, 32)) {
				continue;
			}
			row += userDao.delete(id);
		}
		return row;
	}

	public Pager<User> query(Map<String, Object> map) throws SQLException {
		Util.trim(map);
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return userDao.query(map);
	}

	public User select(Map<String, Object> map) throws SQLException {
		return userDao.select(map);
	}

	public User select(String id) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return select(map);
	}

	public User select(String username, String password) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("password", password);
		return select(map);
	}

	public String replace(User... o) throws SQLException {
		userDao.del(o);
		int row = userDao.replace(o);
		return String.valueOf(row);
	}

}
