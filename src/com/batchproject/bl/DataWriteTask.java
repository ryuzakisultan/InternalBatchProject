package com.batchproject.bl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;
import com.batchproject.util.CommonUtils;

public class DataWriteTask implements Runnable {
	List<Future<List<TransRequestObjectInfo>>> resultSets = null;
	private static final Logger log = LogManager.getLogger(DataWriteTask.class.getName());
	PrintWriter writer;

	public DataWriteTask(List<Future<List<TransRequestObjectInfo>>> resultSets) throws IOException {
		super();
		writer = new PrintWriter(new BufferedWriter(new FileWriter(CommonUtils.outputFileName)));
		this.resultSets = resultSets;
	}

	@Override
	public void run() {
		try {
			for (Future<List<TransRequestObjectInfo>> futureResultSet : resultSets) {
				List<TransRequestObjectInfo> resultSet = futureResultSet.get();
				for (TransRequestObjectInfo transRequestObjectInfo : resultSet) {
					writer.println(transRequestObjectInfo.getTraceAuditNo() + ","
							+ transRequestObjectInfo.getCardPrgID() + "," + transRequestObjectInfo.getTransDate());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			log.error(e, e);
		} finally {
			writer.close();
		}
	}
}
