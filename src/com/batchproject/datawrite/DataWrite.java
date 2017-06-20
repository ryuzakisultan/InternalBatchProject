package com.batchproject.datawrite;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

import com.batchproject.data.RecordSet;

public class DataWrite implements Runnable {
	BlockingQueue<RecordSet> recordSets;
	
	PrintWriter writer;
	public DataWrite(BlockingQueue<RecordSet> recordSets, PrintWriter writer) {
		super();
		this.recordSets = recordSets;
		this.writer = writer;
	}
	@Override
	public void run() {
		try {
			//TODO synchronize write to file
			recordSets.take().writeToFile(writer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
