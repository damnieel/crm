package com.cvc.crm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cvc.crm.model.Product;
import com.cvc.crm.util.DBUtils;
import com.cvc.crm.util.Pager;


/**
 * 
 * 产品 Dao
 * 
 * @author dengrihui
 * @version 1.0, 2018/10/15
 *
 */
public class ProductDao {

	private static Log log = LogFactory.getLog(ProductDao.class);

	public int insert(Product o) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("INSERT INTO `product` (");
		sql.append(" ").append("`id`");
		sql.append(",").append("`name`");
		sql.append(",").append("`category`");
		sql.append(",").append("`style`");
		sql.append(",").append("`material`");
		sql.append(",").append("`color`");
		sql.append(",").append("`model`");
		sql.append(",").append("`image`");
		sql.append(",").append("`price`");
		sql.append(",").append("`status`");
		sql.append(",").append("`desc`");
		sql.append(",").append("`created`");
		sql.append(",").append("`updated`");
		sql.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		params.add(o.getId());
		params.add(o.getName());
		params.add(o.getCategory());
		params.add(o.getStyle());
		params.add(o.getMaterial());
		params.add(o.getColor());
		params.add(o.getModel());
		params.add(o.getImage());
		params.add(o.getPrice());
		params.add(o.getStatus());
		params.add(o.getDesc());
		params.add(o.getCreated());
		params.add(o.getUpdated());

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public int delete(String id) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("DELETE FROM `product` WHERE `id` = ?");
		params.add(id);

		log.info("sql: " + sql);
		log.info("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public int update(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("UPDATE");
		sql.append(" ").append("`product`");
		sql.append(" SET");
		sql.append(" ").append("`id` = ?");
		params.add(map.get("id"));

		if (map.containsKey("name")) {
			sql.append(",").append("`name` = ?");
			params.add(map.get("name"));
		}

		if (map.containsKey("category")) {
			sql.append(",").append("`category` = ?");
			params.add(map.get("category"));
		}
		
		if (map.containsKey("style")) {
			sql.append(",").append("`style` = ?");
			params.add(map.get("styleId"));
		}
		
		if (map.containsKey("material")) {
			sql.append(",").append("`material` = ?");
			params.add(map.get("material"));
		}
		
		if (map.containsKey("color")) {
			sql.append(",").append("`color` = ?");
			params.add(map.get("color"));
		}
		
		if (map.containsKey("model")) {
			sql.append(",").append("`model` = ?");
			params.add(map.get("model"));
		}
		
		if (map.containsKey("image")) {
			sql.append(",").append("`image` = ?");
			params.add(map.get("image"));
		}
		
		if (map.containsKey("price")) {
			sql.append(",").append("`price` = ?");
			params.add(map.get("price"));
		}

		if (map.containsKey("status")) {
			sql.append(",").append("`status` = ?");
			params.add(map.get("status"));
		}

		if (map.containsKey("desc")) {
			sql.append(",").append("`desc` = ?");
			params.add(map.get("desc"));
		}

		if (map.containsKey("created")) {
			sql.append(",").append("`created` = ?");
			params.add(map.get("created"));
		}

		if (map.containsKey("updated")) {
			sql.append(",").append("`updated` = ?");
			params.add(map.get("updated"));
		}

		sql.append(" WHERE");
		sql.append(" ").append("`id` = ?");
		params.add(map.get("id"));

		log.info("sql: " + sql);
		log.info("params: " + params);

		return DBUtils.getQueryRunner().update(sql.toString(), params.toArray());

	}

	public Product select(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("SELECT");
		sql.append(" ").append("t1.`id`");
		sql.append(",").append("t1.`name`");
		sql.append(",").append("t1.`category`").append(" AS ").append("`category`");
		sql.append(",").append("t1.`style`").append(" AS ").append("`style`");
		sql.append(",").append("t1.`material`").append(" AS ").append("`material`");
		sql.append(",").append("t1.`color`");
		sql.append(",").append("t1.`model`");
		sql.append(",").append("t1.`image`");
		sql.append(",").append("t1.`price`");
		sql.append(",").append("t1.`status`");
		sql.append(",").append("t1.`desc`");
		sql.append(",").append("t1.`created`");
		sql.append(",").append("t1.`updated`");
		sql.append(" FROM");
		sql.append(" ").append("`product`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");

		if (map.containsKey("id")) {
			sql.append(" AND t1.`id` = ?");
			params.add(map.get("id"));
		}

		if (map.containsKey("name")) {
			sql.append(" AND t1.`name` = ?");
			params.add(map.get("name"));
		}			

		log.debug("sql: " + sql);
		log.debug("params: " + params);

		return DBUtils.getQueryRunner().query(sql.toString(), new BeanHandler<Product>(Product.class), params.toArray());

	}

	public Pager<Product> query(Map<String, Object> map) throws SQLException {

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		// 公共SQL
		sql.append(" FROM");
		sql.append(" ").append("`product`").append(" AS ").append("t1");
		sql.append(" WHERE");
		sql.append(" ").append("1 = 1");

		if (map.containsKey("id")) {
			sql.append(" AND t1.`id` = ?");
			params.add(map.get("id"));
		}

		if (map.containsKey("name")) {
			sql.append(" AND t1.`name` LIKE ?");
			params.add("%" + map.get("name") + "%");
		}
		
		if (map.containsKey("price")) {
			sql.append(" AND t1.`price` = ?");
			params.add(map.get("price"));
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
		limit.append(",").append("t1.`name`");
		limit.append(",").append("t1.`category`").append(" AS ").append("`category`");
		limit.append(",").append("t1.`style`").append(" AS ").append("`style`");
		limit.append(",").append("t1.`material`").append(" AS ").append("`material`");
		limit.append(",").append("t1.`color`");
		limit.append(",").append("t1.`model`");
		limit.append(",").append("t1.`image`");
		limit.append(",").append("t1.`price`");
		limit.append(",").append("t1.`status`");
		limit.append(",").append("t1.`desc`");
		limit.append(",").append("t1.`created`");
		limit.append(",").append("t1.`updated`");
		limit.append(sql);
		limit.append(" ORDER BY t1.`created` DESC");

		log.debug("sql(count): " + count);
		log.debug("sql(limit): " + limit);
		log.debug("params: " + params);

		return new Pager<Product>(map.get("pageNumber"), map.get("pageSize"), count.toString(), limit.toString(), new BeanListHandler<Product>(Product.class), params.toArray());

	}

}
