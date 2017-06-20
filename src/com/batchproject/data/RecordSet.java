package com.batchproject.data;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.Main;

public class RecordSet {
	ArrayList<TransRequestObject> transRequestObjects = new ArrayList<TransRequestObject>();
	private static final Logger log = LogManager.getLogger(RecordSet.class.getName());
	public RecordSet(ResultSet rs) {
		bufferData(rs);
	}
	
	public void bufferData(ResultSet rs) {
		try {
			while (rs.next()) {
				TransRequestObject o = new TransRequestObject(rs.getInt(1),rs.getString(2),rs.getDate(3));
				transRequestObjects.add(o);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(PrintWriter writer) {
		for (TransRequestObject o : transRequestObjects) {
			o.writeToFile(writer);
		}
	}
	
	
	
}
