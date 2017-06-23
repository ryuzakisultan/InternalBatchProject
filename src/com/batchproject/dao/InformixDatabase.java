package com.batchproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.batchproject.util.CommonUtils;

class InformixDatabase {
	// Database driver and url
	private static String JDBC_DRIVER = "com.informix.jdbc.IfxDriver";
	private static String DB_URL = null;
	private static DataSource datasource;
	private static Connection connection = null;
	private static final Logger log = LogManager.getLogger(InformixDatabase.class.getName());

	static {
		initializeParams();
		initializeDataSource();
	}

	private static void initializeParams() {
		StringBuilder url = new StringBuilder(CommonUtils.dbURL);
		url.append("user=" + CommonUtils.user + ";");
		url.append("password=" + CommonUtils.password + ";");
		DB_URL = url.toString();
	}

	private static void initializeDataSource() {
		datasource = new DataSource();
		PoolProperties p = new PoolProperties();
		p.setDriverClassName(JDBC_DRIVER);
		p.setUrl(DB_URL);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		datasource.setPoolProperties(p);
	}

	public static Connection getConnection() {
		synchronized (connection) {
			try {
				connection = datasource.getConnection();
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				log.error(e, e);
			}
		}
		return connection;
	}
}
