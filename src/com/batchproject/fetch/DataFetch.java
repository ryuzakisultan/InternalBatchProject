package com.batchproject.fetch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.Main;
import com.batchproject.data.RecordSet;

public class DataFetch implements Runnable {
	BlockingQueue<RecordSet> recordSets;
	
	String sql;
	int low;
	int high;
	PreparedStatement ps =  null;
	ResultSet rs = null;
	Connection con = null;
	private static final Logger log = LogManager.getLogger(DataFetch.class.getName());
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, low);
			ps.setInt(2,  high);
			rs = ps.executeQuery();
			
			RecordSet recordSet = new RecordSet(rs);
			recordSets.add(recordSet);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public DataFetch(Connection con, BlockingQueue<RecordSet> recordSets,String sql, int low, int high) {
		super();
		this.con = con;
		this.recordSets = recordSets;
		this.sql = sql;
		this.low = low;
		this.high = high;
	}
	
}
