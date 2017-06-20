package com.batchproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.config.Config;
import com.batchproject.data.RecordSet;
import com.batchproject.datawrite.DataWrite;
import com.batchproject.dbconnection.DBConnection;
import com.batchproject.exceptions.ConfigPropertyNotFound;
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
	private static final Logger log = LogManager.getLogger(Main.class.getName());
	public static void main(String[] args) {
		
		try {
			writer = new PrintWriter(new File(outputFileName));
		} catch (FileNotFoundException e) {
			log.error(e,e);
		}

		int lower = 0;
		int upper = 0;
		
		try {
			batchSize = Config.getIntValue("batchsize");
			threadPoolSize = Config.getIntValue("threadpoolsize");
			lower = Config.getIntValue("lower");
			upper = Config.getIntValue("upper");
		} catch (ConfigPropertyNotFound e) {
			log.error(e,e);
		}

		totalBatches = (int) Math.ceil((double) (upper - lower + 1) / batchSize);

		// TODO Initiate writedata and fetchthreads size;
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
}
