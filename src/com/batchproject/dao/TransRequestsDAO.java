package com.batchproject.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;

public abstract class TransRequestsDAO {
	private static String JDBC_DRIVER = "com.informix.jdbc.IfxDriver";

	public static final String queryFetchTransRequestsData = "select trace_audit_no, card_prg_id, trans_date from trans_requests where trace_audit_no >= ? and trace_audit_no <= ?;";
	private static final Logger log = LogManager.getLogger(TransRequestsDAO.class.getName());

	abstract public List<TransRequestObjectInfo> queuryTransRequests(int lowerTraceAuditNo, int upperTraceAuditNo);

	public static TransRequestsDAO getInstance(String driver) {
		if (driver != null && driver.equals(JDBC_DRIVER)) {
			log.info("Creating TransRequestInformixDAO instance");
			return new TransRequestInformixDAO();
		}
		return null;
	}
}
