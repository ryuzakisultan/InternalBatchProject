package com.batchproject.dbconnection;


public final class DBConnection {
	//Database driver and url
	private static final String JDBC_DRIVER = "com.informix.jdbc.IfxDriver";
	//TODO make connection string generic, make constructure
	private static final String DB_URL = "jdbc:informix-sqli://192.168.150.191:9209/cards:informixserver=inst0002_net_01;user=sahmed07;password=Temp/123;";
}
