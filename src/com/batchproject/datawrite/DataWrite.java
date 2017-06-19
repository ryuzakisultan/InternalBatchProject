package com.batchproject.datawrite;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class DataWrite implements Runnable {
	BlockingQueue<ResultSet> resultSets;
	ConcurrentHashMap<ResultSet, Connection> rsCon;
	ConcurrentHashMap<ResultSet, PreparedStatement> rsPs;
	
	PrintWriter writer;
	public DataWrite(BlockingQueue<ResultSet> resultSets, ConcurrentHashMap<ResultSet, Connection> rsCon, ConcurrentHashMap<ResultSet, PreparedStatement> rsPs, PrintWriter writer) {
		super();
		this.resultSets = resultSets;
		this.writer = writer;
		this.rsCon = rsCon;
		this.rsPs = rsPs;
	}
	@Override
	public void run() {
		try {
			//TODO synchronize write to file
			writeToFile(resultSets.take());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeToFile(ResultSet rs) {
		try {
			while (rs.next()) {
				//TODO change csv if required
				writer.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getDate(3));
				System.out.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getDate(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				Connection con = rsCon.get(rs);
				PreparedStatement ps = rsPs.get(rs);
				rsCon.remove(rs);
				rsPs.remove(rs);
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					ps.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
