package com.batchproject.dbconnection;

import javax.sql.DataSource;

import com.batchproject.config.Config;

public final class DBConnection {
	//Database driver and url
	private static final String JDBC_DRIVER;
	//TODO make connection string generic, make constructure
	private static final String DB_URL;
	private static DataSource datasource;
	
	
	
	static {
		JDBC_DRIVER = Config.getStringValue("driver");
		StringBuilder url = new StringBuilder(Config.getStringValue("url"));
		url.append("user=" + Config.getStringValue("username") + ";");
		url.append("password" + Config.getStringValue("password") + ";");
		DB_URL = url.toString();
	}
	
	static {
		datasource = new DataSource();
		PoolProperties p = new PoolProperties();
		
		p.setDriverClassName(JDBC_DRIVER);
		p.setUrl(DB_URL);
        
        
	}
	
	private DBConnection() {
		
	}
}