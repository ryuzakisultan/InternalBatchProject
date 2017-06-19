package com.batchproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.batchproject.config.Config;
import com.batchproject.datawrite.DataWrite;

public class Main {
	//TODO blocking queue for results sets initialization
	static int batchSize;
	static int threadPoolSize;
	static int totalBatches;
	static PrintWriter writer;
	static String outputFileName = "output1.csv";
	private static BlockingQueue<ResultSet> resultSets = new LinkedBlockingQueue<ResultSet>();	 
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
		
		totalBatches = (int) Math.ceil((double)(upper - lower + 1)/batchSize);	
		System.out.println(threadPoolSize);
		// TODO Auto-generated method stub
		
		
		//TODO Initiate writedata threads
		new Thread() {
			public void run() {
				ExecutorService writerExecutor = Executors.newFixedThreadPool(threadPoolSize);
				 for (int i = 0; i < totalBatches; ++i) {
					 writerExecutor.execute(new DataWrite(resultSets,writer));
				 }
				writerExecutor.shutdown();
				while (!writerExecutor.isTerminated()) {
				}
			}
		}.start();

		
	}
}
