package com.batchproject.fetch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class DataFetch implements Runnable {
	BlockingQueue<ResultSet> resultSets;
	ConcurrentHashMap<ResultSet, Connection> rsCon;
	ConcurrentHashMap<ResultSet, PreparedStatement> rsPs;
	
	String sql;
	int low;
	int high;
	PreparedStatement ps =  null;
	
	Connection con;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, low);
			ps.setInt(2,  high);
			ResultSet rs = ps.executeQuery();
			
			rsCon.put(rs, con);
			rsPs.put(rs, ps);
			
			try {
				resultSets.put(rs);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
		
		
	}

	public DataFetch(Connection con, BlockingQueue<ResultSet> resultSets, ConcurrentHashMap<ResultSet, Connection> rsCon, ConcurrentHashMap<ResultSet, PreparedStatement> rsPs, String sql, int low, int high) {
		super();
		this.con = con;
		this.resultSets = resultSets;
		this.rsCon = rsCon;
		this.rsPs = rsPs;
		this.sql = sql;
		this.low = low;
		this.high = high;
	}
	
}
