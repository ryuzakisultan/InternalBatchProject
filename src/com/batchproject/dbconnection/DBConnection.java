package com.batchproject.dbconnection;

import com.batchproject.config.Config;

public final class DBConnection {
	//Database driver and url
	private static final String JDBC_DRIVER;
	//TODO make connection string generic, make constructure
	private static final String DB_URL = "jdbc:informix-sqli://192.168.150.191:9209/cards:informixserver=inst0002_net_01;user=sahmed07;password=Temp/123;";
	
	static {
		JDBC_DRIVER = Config.getStringValue("driver");
		
		StringBuilder url = new StringBuilder(Config.getStringValue("url"));
		url.append("user=" + Config.getStringValue("username") + ";");
		url.append("password" + Config.getStringValue("password") + ";");
	}
	
	private DBConnection() {
		
	}
}
