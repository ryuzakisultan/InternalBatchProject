package com.batchproject.bl;

import java.util.List;
import java.util.concurrent.Callable;

import com.batchproject.bean.TransRequestObjectInfo;
import com.batchproject.dao.TransRequestsDAO;
import com.batchproject.util.CommonUtils;

public class DataFetchTask implements Callable<List<TransRequestObjectInfo>> {
	private int lowerTraceAuditNo;
	private int upperTraceAuditNo;
	
	public List<TransRequestObjectInfo> call() {
		TransRequestsDAO transRequestsDAO = TransRequestsDAO.getInstance(CommonUtils.dbdriver);
		return transRequestsDAO.queuryTransRequests(lowerTraceAuditNo, upperTraceAuditNo);
	}
	

	public DataFetchTask(int lowerTraceAuditNo, int upperTraceAuditNo) {
		super();
		this.lowerTraceAuditNo = lowerTraceAuditNo;
		this.upperTraceAuditNo = upperTraceAuditNo;
	}
	
}
