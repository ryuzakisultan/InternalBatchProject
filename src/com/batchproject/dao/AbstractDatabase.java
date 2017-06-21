package com.batchproject.dao;

import java.sql.Connection;

public abstract class AbstractDatabase {
	protected final String queryFetchTransRequestsData = "select trace_audit_no, card_prg_id, trans_date from trans_requests where trace_audit_no >= ? and trace_audit_no <= ?;";
	
	public static AbstractDatabase getDataSource(String driver) {
		if (driver.equals("com.informix.jdbc.IfxDriver")) {
			return new InformixDatabase();
		}
		return null;
	}
	
	public abstract Connection getConnection();
	
}
