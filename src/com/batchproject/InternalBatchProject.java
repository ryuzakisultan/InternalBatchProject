package com.batchproject;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;
import com.batchproject.bl.DataFetchTask;
import com.batchproject.bl.DataWriteTask;
import com.batchproject.bl.TransRequestBatchTrack;
import com.batchproject.dao.DBConnection;
import com.batchproject.util.CommonUtils;

public class InternalBatchProject {
	// TODO blocking queue for results sets initialization
	private static String queryFetchTransRequestsData = "select trace_audit_no, card_prg_id, trans_date from trans_requests where trace_audit_no >= ? and trace_audit_no <= ?;";
	private static List<Future<List<TransRequestObjectInfo>>> futureRecordSets = new ArrayList<Future<List<TransRequestObjectInfo>>>();
	private static final Logger log = LogManager.getLogger(InternalBatchProject.class.getName());

	public static void main(String[] args) {

		try {
			ExecutorService executor = Executors.newFixedThreadPool(CommonUtils.threadPoolSize);
			TransRequestBatchTrack transRequestBatchTrack = new TransRequestBatchTrack();
			while(transRequestBatchTrack.nextBatch()) {
				Connection con = DBConnection.getConnection();
				DataFetchTask dataFetchTask =  new DataFetchTask(con, queryFetchTransRequestsData, transRequestBatchTrack.getLowerTraceAuditNumber(), transRequestBatchTrack.getUpperTraceAuditNumber());
				Future<List<TransRequestObjectInfo>> futureResultSet = executor.submit(dataFetchTask);
				futureRecordSets.add(futureResultSet);
			}
			new Thread(new DataWriteTask(futureRecordSets)).start();
			
		} catch (IOException e) {
			log.error(e, e);
		} 
	}
}
