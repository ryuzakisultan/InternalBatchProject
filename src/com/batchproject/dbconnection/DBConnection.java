package com.batchproject.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.batchproject.config.Config;

public final class DBConnection {
	//Database driver and url
	private static final String JDBC_DRIVER;
	//TODO make connection string generic, make constructure
	private static final String DB_URL;
	private static DataSource datasource;
	private static Connection con = null;
	
	
	
	static {
		JDBC_DRIVER = Config.getStringValue("driver");
		StringBuilder url = new StringBuilder(Config.getStringValue("url"));
		url.append("user=" + Config.getStringValue("user") + ";");
		url.append("password=" + Config.getStringValue("password") + ";");
		DB_URL = url.toString();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}