package com.batchproject.bean;

import java.sql.Date;

public class TransRequestObjectInfo {

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
