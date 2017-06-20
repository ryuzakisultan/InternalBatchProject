package com.batchproject.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.batchproject.config.Config;
import com.batchproject.exceptions.ConfigPropertyNotFound;

public final class DBConnection {
	//Database driver and url
	private static String JDBC_DRIVER = null;
	private static String DB_URL = null;
	private static DataSource datasource;
	private static Connection con = null;
	private static final Logger log = LogManager.getLogger(DBConnection.class.getName());
	
	static {
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
	
	static {
		datasource = new DataSource();
		PoolProperties p = new PoolProperties();
		p.setDriverClassName(JDBC_DRIVER);
		p.setUrl(DB_URL);
		p.setValidationQuery("SELECT 1");
		p.setMaxActive(1000);
		p.setInitialSize(100);
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