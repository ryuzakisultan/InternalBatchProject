package com.batchproject;

import java.io.IOException;
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
import com.batchproject.util.CommonUtils;

public class InternalBatchProject {
	private static List<Future<List<TransRequestObjectInfo>>> futureRecordSets = new ArrayList<Future<List<TransRequestObjectInfo>>>();
	private static final Logger log = LogManager.getLogger(InternalBatchProject.class.getName());

	public static void main(String[] args) {
		try {
			log.info("Initializing Thread Executor");
			ExecutorService executor = Executors.newFixedThreadPool(CommonUtils.threadPoolSize);
			TransRequestBatchTrack transRequestBatchTrack = new TransRequestBatchTrack();
			log.info("Initializing Data Fetch threads");
			while (transRequestBatchTrack.nextBatch()) {
				DataFetchTask dataFetchTask = new DataFetchTask(transRequestBatchTrack.getLowerTraceAuditNumber(),
						transRequestBatchTrack.getUpperTraceAuditNumber());
				Future<List<TransRequestObjectInfo>> futureResultSet = executor.submit(dataFetchTask);
				futureRecordSets.add(futureResultSet);
			}
			executor.shutdown();

			log.info("Starting DataWriteTask");
			new Thread(new DataWriteTask(futureRecordSets)).start();

		} catch (IOException e) {
			log.error(e, e);
		}
	}
}
