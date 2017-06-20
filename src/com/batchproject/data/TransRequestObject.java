package com.batchproject.data;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransRequestObject {
	private static final Logger log = LogManager.getLogger(TransRequestObject.class.getName());
	
	private int traceAuditNo = 0;
	private String cardPrgID = null;
	private Date transDate = null;
	
	public int getTraceAuditNo() {
		return traceAuditNo;
	}
	public String getCardPrgID() {
		return cardPrgID;
	}
	public Date getTransDate() {
		return transDate;
	}
	
	public void setTraceAuditNo(int traceAuditNo) {
		this.traceAuditNo = traceAuditNo;
	}
	public void setCardPrgID(String cardPrgID) {
		this.cardPrgID = cardPrgID;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
}
