package com.batchproject.datawrite;

import java.io.BufferedWriter;
import java.sql.ResultSet;
import java.util.concurrent.BlockingQueue;

public class DataWrite implements Runnable {
	BlockingQueue<ResultSet> resultSets;
	BufferedWriter writer;
	public DataWrite(BlockingQueue<ResultSet> resultSets, BufferedWriter writer) {
		super();
		this.resultSets = resultSets;
		this.writer = writer;
	}
	@Override
	public void run() {
		try {
			writeToFile(resultSets.take());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private void writeToFile(ResultSet rs) {
		try {
			while (rs.next()) {
				
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
