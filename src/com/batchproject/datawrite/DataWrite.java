package com.batchproject.datawrite;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.data.RecordSet;

public class DataWrite implements Runnable {
	BlockingQueue<RecordSet> recordSets;
	private static final Logger log = LogManager.getLogger(DataWrite.class.getName());
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
			log.error(e,e);
		}
	}
}
