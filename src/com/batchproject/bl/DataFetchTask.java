package com.batchproject.bl;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;
import com.batchproject.dao.TransRequestsDAO;
import com.batchproject.util.CommonUtils;

public class DataFetchTask implements Callable<List<TransRequestObjectInfo>> {
	private int lowerTraceAuditNo;
	private int upperTraceAuditNo;
	private static final Logger log = LogManager.getLogger(DataFetchTask.class.getName());

	public List<TransRequestObjectInfo> call() {
		log.info("Getting TransRequestsInformixDAO instance");
		TransRequestsDAO transRequestsDAO = TransRequestsDAO.getInstance(CommonUtils.dbdriver);
		log.info("Set parameters for fetchTransRequestsData query");
		return transRequestsDAO.queuryTransRequests(lowerTraceAuditNo, upperTraceAuditNo);
	}
	
	public DataFetchTask(int lowerTraceAuditNo, int upperTraceAuditNo) {
		super();
		this.lowerTraceAuditNo = lowerTraceAuditNo;
		this.upperTraceAuditNo = upperTraceAuditNo;
	}
	
}
