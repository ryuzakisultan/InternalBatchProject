package com.batchproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.batchproject.config.Config;
import com.batchproject.data.RecordSet;
import com.batchproject.datawrite.DataWrite;
import com.batchproject.dbconnection.DBConnection;
import com.batchproject.fetch.DataFetch;

public class Main {
	// TODO blocking queue for results sets initialization
	static int batchSize;
	static int threadPoolSize;
	static int totalBatches;
	static PrintWriter writer;
	static String sql = "select trace_audit_no, card_prg_id, trans_date from trans_requests where trace_audit_no >= ? and trace_audit_no <= ?;";
	static String outputFileName = "output.csv";
	private static BlockingQueue<RecordSet> recordSets = new LinkedBlockingQueue<RecordSet>();
	
	public static void main(String[] args) {
		try {
			writer = new PrintWriter(new File(outputFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		batchSize = Config.getIntValue("batchsize");
		threadPoolSize = Config.getIntValue("threadpoolsize");

		int lower = Config.getIntValue("lower");
		int upper = Config.getIntValue("upper");

		totalBatches = (int) Math.ceil((double) (upper - lower + 1) / batchSize);
		// TODO Auto-generated method stub

		// TODO Initiate writedata and fetchthreads size;
		new Thread() {
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
				int start = lower;
				int end = lower+batchSize;
				for (int i = 0; i < totalBatches; ++i) {
					Connection con = DBConnection.getConnection();
					executor.execute(new DataFetch(con,recordSets,sql,start,Math.min(end,upper)));
					start = end + 1;
					end = end + batchSize;
					executor.execute(new DataWrite(recordSets, writer));
				}
				executor.shutdown();
				while (!executor.isTerminated()) {
					
				}
				writer.close();
			}
		}.start();
	}
}
