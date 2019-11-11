package com.cvc.crm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.User;
import com.cvc.crm.util.DBUtils;
import com.cvc.crm.util.Pager;


/**
 * 
 * 用户 Dao
 * 
 * @author chenfan
 * @version 1.0, 2017/03/21
 *
 */
public class UserDao {

	private static Log log = LogFactory.getLog(UserDao.class);

	public int save(String str, User... o) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(str).append(" INTO `user` (");
		sql.append(" ").append("`id`");
		sql.append(",").append("`username`");
		sql.append(",").append("`password`");
		sql.append(",").append("`type`");
		sql.append(",").append("`status`");
		sql.append(",").append("`desc`");
		sql.append(",").append("`created`");
		sql.append(",").append("`updated`");
		sql.append(") VALUES ");

		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < 8; i++) {
			sb.append(i >= 1 ? "," : "").append("?");
		}
		sb.append(")");

		for (int i = 0; i < o.length; i++) {
			sql.append(i >= 1 ? "," : "").append(sb);
			User obj = o[i];
			params.add(obj.getId());
			params.add(obj.getUsername());
			params.add(obj.getPassword());
			params.add(obj.getType());
			params.add(obj.getStatus());
			params.add(obj.getDesc());
			params.add(obj.getCreatedDateStr());
			params.add(obj.getLastModifiedDateStr());

		}

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public int delete(String id) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("DELETE FROM `user` WHERE `id` = ?");
		params.add(id);

		log.info("sql: " + sql);
		log.info("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public User select(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("SELECT");
		sql.append(" ").append("t1.`id`");
		sql.append(",").append("t1.`username`");
		sql.append(",").append("t1.`password`");
		sql.append(",").append("t1.`type`");
		sql.append(",").append("t1.`status`");
		sql.append(",").append("t1.`desc`");
		sql.append(",").append("t1.`created`");
		sql.append(",").append("t1.`updated`");
		sql.append(" FROM");
		sql.append(" ").append("`user`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");

		if (map.containsKey("id")) {
			sql.append(" AND t1.`id` = ?");
			params.add(map.get("id"));
		}

		if (map.containsKey("username")) {
			sql.append(" AND t1.`username` = ?");
			params.add(map.get("username"));
		}

		if (map.containsKey("password")) {
			sql.append(" AND t1.`password` = ?");
			params.add(map.get("password"));
		}

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().query(sql.toString(), new BeanHandler<User>(User.class), params.toArray());

	}

	public Pager<User> query(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 公共SQL
		sql.append(" FROM");
		sql.append(" ").append("`user`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");

		if (map.containsKey("id")) {
			sql.append(" AND t1.`id` = ?");
			params.add(map.get("id"));
		}

		if (map.containsKey("username")) {
			sql.append(" AND t1.`username` LIKE ?");
			params.add("%" + map.get("username") + "%");
		}

		if (map.containsKey("type")) {
			sql.append(" AND t1.`type` = ?");
			params.add(map.get("type"));
		}

		if (map.containsKey("status")) {
			sql.append(" AND t1.`status` = ?");
			params.add(map.get("status"));
		}

		// 统计SQL
		StringBuffer count = new StringBuffer();
		count.append("SELECT COUNT(1)");
		count.append(sql);

		// 分页SQL
		StringBuffer limit = new StringBuffer();
		limit.append("SELECT");
		limit.append(" ").append("t1.`id`");
		limit.append(",").append("t1.`username`");
		limit.append(",").append("t1.`password`");
		limit.append(",").append("t1.`type`");
		limit.append(",").append("t1.`status`");
		limit.append(",").append("t1.`desc`");
		limit.append(",").append("t1.`created`");
		limit.append(",").append("t1.`updated`");
		limit.append(sql);
		limit.append(" ORDER BY t1.`created` DESC");

		log.debug("sql(count): " + count);
		log.debug("sql(limit): " + limit);
		log.debug("params: " + params);

		return new Pager<User>(map.get("pageNumber"), map.get("pageSize"), count.toString(), limit.toString(), new BeanListHandler<User>(User.class), params.toArray());

	}

	public int del(User... o) throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(" DELETE FROM `user` ");

		for (int i = 0; i < o.length; i++) {
			sql.append(" ").append(i >= 1 ? "OR" : "WHERE").append(" `id` = ? ");
			params.add(o[i].getId());
		}

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public int insert(User... o) throws SQLException {
		return save("INSERT", o);
	}

	public int replace(User... o) throws SQLException {
		return save("REPLACE", o);
	}

}
