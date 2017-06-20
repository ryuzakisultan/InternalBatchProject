package com.batchproject.fetch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.data.RecordSet;
import com.batchproject.data.TransRequestObject;

public class DataFetch implements Runnable {
	BlockingQueue<List<TransRequestObject>> recordSets = null;
	
	String queryFetchTransRequestsData = null;
	int low;
	int high;
	PreparedStatement ps =  null;
	ResultSet rs = null;
	Connection con = null;
	private static final Logger log = LogManager.getLogger(DataFetch.class.getName());
	@Override
	public void run() {
		try {
			ps = con.prepareStatement(queryFetchTransRequestsData);
			ps.setInt(1, low);
			ps.setInt(2,  high);
/*			
			String formatedSQL = sql.replace("?","{}");
			
			log.info(SQL_MARKER,formatedSQL,low,high);*/
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TransRequestObject transRequestObject = new TransRequestObject();
				transRequestObject.setTraceAuditNo(rs.getInt(1));
				transRequestObject.setCardPrgID(cardPrgID);
				transRequestObject.setTransDate(transDate);
				transRequestObjects.add(transRequestObject);
			}
		} catch (SQLException e) {
			log.error(e,e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
		}
	}

	public DataFetch(Connection con, BlockingQueue<RecordSet> recordSets,String sql, int low, int high) {
		super();
		this.con = con;
		this.recordSets = recordSets;
		this.queryFetchTransRequestsData = sql;
		this.low = low;
		this.high = high;
	}
	
}
