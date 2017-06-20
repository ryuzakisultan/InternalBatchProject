package com.batchproject.data;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordSet {
	ArrayList<TransRequestObject> transRequestObjects = new ArrayList<TransRequestObject>();
	
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
