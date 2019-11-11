package com.cvc.crm.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cvc.crm.dao.ProductDao;
import com.cvc.crm.model.Product;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Util;



public class ProductService {

	private ProductDao productDao;

	public ProductService() {
		productDao = new ProductDao();
	}

	public Pager<Product> query(Map<String, Object> map) throws SQLException {
		Util.trim(map);
		return productDao.query(map == null ? new HashMap<String, Object>() : map);
	}

	public int save(Product o) throws SQLException {
		return productDao.insert(o);
	}

	public int delete(String... ids) throws SQLException {
		int row = 0;
		for (String id : ids) {
			if (!Util.isWord(id, 1, 64)) {
				continue;
			}
			row += productDao.delete(id);
		}
		return row;
	}

	public int update(String k, Object v, String... ids) throws SQLException {
		int row = 0;
		for (String id : ids) {
			if (!Util.isWord(id, 1, 64)) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put(k, v);
			row += update(map);
		}
		return row;
	}

	public int update(Map<String, Object> map) throws SQLException {
		return productDao.update(map == null ? new HashMap<String, Object>() : map);
	}

	public Product select(Map<String, Object> map) throws SQLException {
		Util.trim(map);
		return productDao.select(map == null ? new HashMap<String, Object>() : map);
	}

	public Product select(String id) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return select(map);
	}

}
