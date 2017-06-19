package com.batchproject.datawrite;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class DataWrite implements Runnable {
	BlockingQueue<ResultSet> resultSets;
	PrintWriter writer;
	public DataWrite(BlockingQueue<ResultSet> resultSets, PrintWriter writer) {
		super();
		this.resultSets = resultSets;
		this.writer = writer;
	}
	@Override
	public void run() {
//		try {
//			//TODO synchronize write to file
//			writeToFile(resultSets.take());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("Hello");
	}
	
	private void writeToFile(ResultSet rs) {
		try {
			while (rs.next()) {
				//TODO change csv if required
				writer.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getDate(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
