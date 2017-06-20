package com.batchproject.data;

import java.io.PrintWriter;
import java.sql.Date;

public class TransRequestObject {
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
		System.out.println(traceAuditNo + "," + cardPrgID + "," + transDate);
	}
}
