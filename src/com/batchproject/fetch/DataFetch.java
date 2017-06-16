package com.batchproject.fetch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class DataFetch implements Runnable {
	BlockingQueue<ResultSet> resultSets;
	String sql;
	int low;
	int high;
	
	Connection con;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, low);
			ps.setInt(2,  high);
			ResultSet rs = ps.executeQuery();
			try {
				resultSets.put(rs);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public DataFetch(Connection con, BlockingQueue<ResultSet> resultSets, String sql, int low, int high) {
		super();
		this.con = con;
		this.resultSets = resultSets;
		this.sql = sql;
		this.low = low;
		this.high = high;
	}
	
}
