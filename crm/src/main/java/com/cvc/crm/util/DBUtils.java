package com.cvc.crm.util;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

public final class DBUtils {

	private volatile static DataSource dataSource;

	private static String username;
	private static String password;
	private static String url;
	private static String driverClassName;
	private static String connectionProperties;
	private static boolean defaultAutoCommit;
	private static int maxActive;
	private static int maxIdle;
	private static long maxWait;

	static {
		try {
			Properties p = new Properties();
			p.load(DBUtils.class.getResourceAsStream("/jdbc.properties"));
			username = p.getProperty("username");
			password = p.getProperty("password");
			url = p.getProperty("url");
			driverClassName = p.getProperty("driverClassName");
			connectionProperties = p.getProperty("connectionProperties");
			defaultAutoCommit = Boolean.valueOf(p.getProperty("defaultAutoCommit"));
			maxActive = Integer.parseInt(p.getProperty("maxActive"));
			maxIdle = Integer.parseInt(p.getProperty("maxIdle"));
			maxWait = Integer.parseInt(p.getProperty("maxWait"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DBUtils() {
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			synchronized (DBUtils.class) {
				if (dataSource == null) {
					BasicDataSource ds = new BasicDataSource();
					ds.setUsername(username);
					ds.setPassword(password);
					ds.setUrl(url);
					ds.setDriverClassName(driverClassName);
					ds.setConnectionProperties(connectionProperties);
					ds.setDefaultAutoCommit(defaultAutoCommit);
					ds.setMaxActive(maxActive);
					ds.setMaxIdle(maxIdle);
					ds.setMaxWait(maxWait);
					ds.setTestOnBorrow(true);
					ds.setTestOnReturn(true);
					ds.setTestWhileIdle(true);
					dataSource = ds;
				}
			}
		}
		return dataSource;
	}

	public static QueryRunner getQueryRunner() {
		return new QueryRunner(getDataSource());
	}

}
