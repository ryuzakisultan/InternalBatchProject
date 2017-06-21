package com.batchproject.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;

public class DataFetchTask implements Callable<List<TransRequestObjectInfo>> {
	List<TransRequestObjectInfo> recordSet = null;
	private String queryFetchTransRequestsData = null;
	private int lowerTraceAuditNo;
	private int upperTraceAuditNo;
	private PreparedStatement preparedStatement =  null;
	private ResultSet resultSet = null;
	private Connection connection = null;
	private static final Logger log = LogManager.getLogger(DataFetchTask.class.getName());
	public List<TransRequestObjectInfo> call() {
		try {
			
			recordSet = new ArrayList<TransRequestObjectInfo>();
			
			preparedStatement = connection.prepareStatement(queryFetchTransRequestsData);
			preparedStatement.setInt(1, lowerTraceAuditNo);
			preparedStatement.setInt(2,  upperTraceAuditNo);
			
			log.info(queryFetchTransRequestsData);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				TransRequestObjectInfo transRequestObject = new TransRequestObjectInfo();
				transRequestObject.setTraceAuditNo(resultSet.getInt(1));
				transRequestObject.setCardPrgID(resultSet.getString(2));
				transRequestObject.setTransDate(resultSet.getDate(3));
				recordSet.add(transRequestObject);
			}
			System.out.println("batch fetched");
		} catch (SQLException e) {
			log.error(e,e);
		} finally {
			closeResultSet(resultSet);
			closeConnection(connection);
			closePreparedStatement(preparedStatement);
		}
		
		return recordSet;
	}
	private void closeResultSet(ResultSet recordSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				log.error(e,e);
			}
		}
	}
	
	private void closePreparedStatement(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				log.error(e,e);
			}
		}
	}
	
	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.error(e,e);
			}
		}
	}

	public DataFetchTask(Connection connection,String queryFetchTransRequestsData, int lowerTraceAuditNo, int upperTraceAuditNo) {
		super();
		this.connection = connection;
		this.queryFetchTransRequestsData = queryFetchTransRequestsData;
		this.lowerTraceAuditNo = lowerTraceAuditNo;
		this.upperTraceAuditNo = upperTraceAuditNo;
	}
	
}
