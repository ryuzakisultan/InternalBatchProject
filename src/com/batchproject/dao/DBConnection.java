package com.batchproject.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.batchproject.excep.ConfigPropertyNotFound;
import com.batchproject.util.Config;

public final class DBConnection {
	//Database driver and url
	private static String JDBC_DRIVER = null;
	private static String DB_URL = null;
	private static DataSource datasource;
	private static Connection con = null;
	private static final Logger log = LogManager.getLogger(DBConnection.class.getName());
	
	static {
		initializeParams();
		initializeDataSource();
	}
	
	private static void initializeParams() {
		try {
			JDBC_DRIVER = Config.getStringValue("driver");
			StringBuilder url = new StringBuilder(Config.getStringValue("url"));
			url.append("user=" + Config.getStringValue("user") + ";");
			url.append("password=" + Config.getStringValue("password") + ";");
			DB_URL = url.toString();
		} catch (ConfigPropertyNotFound e) {
			log.error(e,e);
		}
	}
	
	private static void initializeDataSource() {
		datasource = new DataSource();
		PoolProperties p = new PoolProperties();
		p.setDriverClassName(JDBC_DRIVER);
		p.setUrl(DB_URL);
		p.setValidationQuery("SELECT 1");
		p.setMaxActive(1000);
		p.setInitialSize(10);
		datasource.setPoolProperties(p);
	}
	
	private DBConnection() {
		
	}
	
	public static Connection getConnection() {
		try {
			con = datasource.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			log.error(e,e);
		}
		return con;
	}
}