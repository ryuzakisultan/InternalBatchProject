package com.batchproject.data;

import java.io.PrintWriter;
import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransRequestObject {
	private static final Logger log = LogManager.getLogger(TransRequestObject.class.getName());
	
	int traceAuditNo;
	String cardPrgID;
	Date transDate;
	public TransRequestObject(int traceAuditNo, String cardPrgID, Date transDate) {
		super();
		this.traceAuditNo = traceAuditNo;
		this.cardPrgID = cardPrgID;
		this.transDate = transDate;
	}
	public int getTraceAuditNo() {
		return traceAuditNo;
	}
	public String getCardPrgID() {
		return cardPrgID;
	}
	public Date getTransDate() {
		return transDate;
	}
	
	public void writeToFile(PrintWriter writer) {
		writer.println(traceAuditNo + "," + cardPrgID + "," + transDate);
	}
}
