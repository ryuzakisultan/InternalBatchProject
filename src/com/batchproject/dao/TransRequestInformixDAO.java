package com.batchproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.bean.TransRequestObjectInfo;

class TransRequestInformixDAO extends TransRequestsDAO {
	private PreparedStatement preparedStatement =  null;
	private ResultSet resultSet = null;
	private Connection connection = null;
	
	private static final Logger log = LogManager.getLogger(TransRequestInformixDAO.class.getName());

	public List<TransRequestObjectInfo> queuryTransRequests(int lowerTraceAuditNo, int upperTraceAuditNo) {
		List<TransRequestObjectInfo> recordSet = new ArrayList<TransRequestObjectInfo>();
		try {
			log.info("Getting InformixDatabase connection");
			connection = InformixDatabase.getConnection();
			log.info("Creating prepared statement");
			preparedStatement = connection.prepareStatement(queryFetchTransRequestsData);
			log.info("Setting parameters in preparedStatment");
			preparedStatement.setInt(1, lowerTraceAuditNo);
			preparedStatement.setInt(2,  upperTraceAuditNo);
			log.info("Executing: " + queryFetchTransRequestsData);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TransRequestObjectInfo transRequestObject = new TransRequestObjectInfo();
				transRequestObject.setTraceAuditNo(resultSet.getInt(1));
				transRequestObject.setCardPrgID(resultSet.getString(2));
				transRequestObject.setTransDate(resultSet.getDate(3));
				recordSet.add(transRequestObject);
			}
		} catch (SQLException e) {
			log.error(e,e);
		} finally {
			log.info("Closing result set");
			closeResultSet(resultSet);
			log.info("Closing prepared statement");
			closePreparedStatement(preparedStatement);
			log.info("Closing connection");
			closeConnection(connection);
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
}
