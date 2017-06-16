package com.batchproject.fetch;

import java.sql.ResultSet;
import java.util.concurrent.BlockingQueue;

public class DataFetch implements Runnable {
	BlockingQueue<ResultSet> resultSets;

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	public DataFetch(BlockingQueue<ResultSet> resultSets) {
		super();
		this.resultSets = resultSets;
	}
	
}
