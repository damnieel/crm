package com.cvc.crm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.Info;
import com.cvc.crm.util.DBUtils;
import com.cvc.crm.util.Pager;


public class InfoDao {

	private static Log log = LogFactory.getLog(InfoDao.class);

	private int save(String str, Info... o) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(str).append(" INTO `info` (");
		sql.append(" ").append("`id`");
		sql.append(",").append("`created_date`");
		sql.append(",").append("`remote_addr`");
		sql.append(",").append("`request_uri`");
		sql.append(",").append("`user_agent`");
		sql.append(",").append("`protocol`");
		sql.append(",").append("`method`");
		sql.append(",").append("`accept`");
		sql.append(",").append("`accept_language`");
		sql.append(",").append("`accept_encoding`");
		sql.append(",").append("`scan_time`");
		sql.append(",").append("`scan_place`");
		sql.append(") VALUES ");

		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < 12; i++) {
			sb.append(i >= 1 ? "," : "").append("?");
		}
		sb.append(")");

		for (int i = 0; i < o.length; i++) {
			sql.append(i >= 1 ? "," : "").append(sb);
			Info obj = o[i];
			params.add(obj.getId());
			params.add(obj.getCreatedDate());
			params.add(obj.getRemoteAddr());
			params.add(obj.getRequestUri());
			params.add(obj.getUserAgent());
			params.add(obj.getProtocol());
			params.add(obj.getMethod());
			params.add(obj.getAccept());
			params.add(obj.getAcceptLanguage());
			params.add(obj.getAcceptEncoding());
			params.add(obj.getScanTime());
			params.add(obj.getScanPlace());
		}

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public int insert(Info... o) throws SQLException {
		return save("INSERT", o);
	}

	public int replace(Info... o) throws SQLException {
		return save("REPLACE", o);
	}

	public Pager<Info> query(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 公共SQL
		sql.append(" FROM");
		sql.append(" ").append("`info`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");

		if (map.containsKey("scanTime")) {
			sql.append(" AND t1.`scan_time` >= ?");
			params.add(map.get("scanTime"));
		}
		if (map.containsKey("scanPlace")) {
			sql.append(" AND t1.`scan_place` = ?");
			params.add(map.get("scanPlace"));
		}
		if (map.containsKey("userAgent")) {
			sql.append(" AND t1.`user_agent` LIKE ?");
			params.add("%" + map.get("userAgent") + "%");
		}
		if (map.containsKey("remoteAddr")) {
			sql.append(" AND t1.`remote_addr` LIKE ?");
			params.add("%" + map.get("remoteAddr") + "%");
		}

		// 统计SQL
		StringBuffer count = new StringBuffer();
		count.append("SELECT COUNT(1)");
		count.append(sql);

		// 分页SQL
		StringBuffer limit = new StringBuffer();
		limit.append("SELECT");
		limit.append(" ").append("t1.`id`");
		limit.append(",").append("t1.`created_date`").append(" AS ").append("`createdDate`");
		limit.append(",").append("t1.`remote_addr`").append(" AS ").append("`remoteAddr`");
		limit.append(",").append("t1.`request_uri`").append(" AS ").append("`requestUri`");
		limit.append(",").append("t1.`user_agent`").append(" AS ").append("`userAgent`");
		limit.append(",").append("t1.`protocol`");
		limit.append(",").append("t1.`method`");
		limit.append(",").append("t1.`accept`");
		limit.append(",").append("t1.`scan_time`").append(" AS ").append("`scanTime`");
		limit.append(",").append("t1.`scan_place`").append(" AS ").append("`scanPlace`");
		limit.append(",").append("t1.`accept_language`").append(" AS ").append("`acceptLanguage`");
		limit.append(",").append("t1.`accept_encoding`").append(" AS ").append("`acceptEncoding`");
		limit.append(sql);
		limit.append(" ORDER BY t1.`created_date` DESC");

		log.debug("sql(count): " + count);
		log.debug("sql(limit): " + limit);
		log.debug("params: " + params);

		return new Pager<Info>(map.get("pageNumber"), map.get("pageSize"), count.toString(), limit.toString(), new BeanListHandler<Info>(Info.class), params.toArray());

	}

	public List<Info> select(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 公共SQL
		sql.append("SELECT");
		sql.append(" ").append("t1.`id`");
		sql.append(",").append("t1.`created_date`").append(" AS ").append("`createdDate`");
		sql.append(",").append("t1.`remote_addr`").append(" AS ").append("`remoteAddr`");
		sql.append(",").append("t1.`request_uri`").append(" AS ").append("`requestUri`");
		sql.append(",").append("t1.`user_agent`").append(" AS ").append("`userAgent`");
		sql.append(",").append("t1.`scan_time`").append(" AS ").append("`scanTime`");
		sql.append(",").append("t1.`scan_place`").append(" AS ").append("`scanPlace`");
		sql.append(",").append("t1.`protocol`");
		sql.append(",").append("t1.`method`");
		sql.append(",").append("t1.`accept`");
		sql.append(",").append("t1.`accept_language`").append(" AS ").append("`acceptLanguage`");
		sql.append(",").append("t1.`accept_encoding`").append(" AS ").append("`acceptEncoding`");

		sql.append(" FROM");
		sql.append(" ").append("`info`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");
		if (map.containsKey("id")) {
			sql.append(" AND t1.`id` = ?");
			params.add(map.get("id"));
		}
		if (map.containsKey("scanTime")) {
			sql.append(" AND t1.`scan_time` >= ?");
			params.add(map.get("scanTime"));
		}
		if (map.containsKey("scanPlace")) {
			sql.append(" AND t1.`scan_place` = ?");
			params.add(map.get("scanPlace"));
		}
		if (map.containsKey("userAgent")) {
			sql.append(" AND t1.`user_agent` LIKE ?");
			params.add("%" + map.get("userAgent") + "%");
		}
		if (map.containsKey("remoteAddr")) {
			sql.append(" AND t1.`remote_addr` LIKE ?");
			params.add("%" + map.get("remoteAddr") + "%");
		}

		sql.append(" ORDER BY t1.`created_date` DESC");

		log.debug("sql" + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().query(sql.toString(), new BeanListHandler<Info>(Info.class), params.toArray());
	}

	public int delete(String id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("DELETE FROM `info` WHERE `id` = ?");
		params.add(id);

		log.info("sql: " + sql);
		log.info("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());
	}
}
