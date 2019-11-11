package com.cvc.crm.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cvc.crm.dao.InfoDao;
import com.cvc.crm.model.Info;
import com.cvc.crm.util.Pager;
import com.cvc.crm.util.Util;



public class InfoService {

	private InfoDao infoDao;

	public InfoService() {
		infoDao = new InfoDao();
	}

	public int insert(Info... o) throws SQLException {
		return infoDao.insert(o);
	}
	public int replace(Info... o) throws SQLException {
		return infoDao.replace(o);
	}

	public Pager<Info> query(Map<String, Object> map) throws SQLException {
		Util.trim(map);
		return infoDao.query(map == null ? new HashMap<String, Object>() : map);
	}
	
	public List<Info> select(Map<String, Object> map) throws SQLException {
		Util.trim(map);
		return infoDao.select(map == null ? new HashMap<String, Object>() : map);
	}
	
	public Info select(String id) throws SQLException {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id",id);
		List<Info> list = select(map);
		if(list !=null && list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	
	public int delete(String... ids) throws SQLException {
		int row = 0;
		for (String id : ids) {
			if (!Util.isWord(id, 1, 64)) {
				continue;
			}
			row += infoDao.delete(id);
		}
		return row;
	}

}
