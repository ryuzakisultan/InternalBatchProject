package com.batchproject.data;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RecordSet {
	List<TransRequestObject> transRequestObjects = new ArrayList<TransRequestObject>();
	private static final Logger log = LogManager.getLogger(RecordSet.class.getName());
	public RecordSet(ResultSet rs) {
		bufferData(rs);
	}
	
	public void bufferData(ResultSet rs) {
		try {
			while (rs.next()) {
				TransRequestObject transRequestObject = new TransRequestObject();
				transRequestObject.setTraceAuditNo(rs.getInt(1));
				transRequestObject.setCardPrgID(cardPrgID);
				transRequestObject.setTransDate(transDate);
				transRequestObjects.add(transRequestObject);
			}
		} catch (SQLException e) {
			log.error(e,e);
		}
	}
	
	public void writeToFile(PrintWriter writer) {
		for (TransRequestObject o : transRequestObjects) {
			o.writeToFile(writer);
		}
	}
	
	
	
}
