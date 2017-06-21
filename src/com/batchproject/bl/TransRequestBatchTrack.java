package com.batchproject.bl;

import com.batchproject.util.CommonUtils;

public class TransRequestBatchTrack {
	private int currentLowerTraceAuditNumber = 0;
	private int currentUpperTraceAuditNumber = 0;
	private boolean batchStarted = false;
	
	public TransRequestBatchTrack() {
		currentLowerTraceAuditNumber = CommonUtils.lowerTraceAuditNo;
		currentUpperTraceAuditNumber = Math.min(CommonUtils.batchSize,CommonUtils.upperTraceAuditNo);
	}
	
	public boolean nextBatch() {
		if (currentLowerTraceAuditNumber < CommonUtils.upperTraceAuditNo) {
			if (batchStarted) {
				currentLowerTraceAuditNumber = currentUpperTraceAuditNumber + 1;
				currentUpperTraceAuditNumber = Math.min(currentUpperTraceAuditNumber + CommonUtils.batchSize, CommonUtils.upperTraceAuditNo);
			}
			batchStarted = true;
			return true;
		} else {
			return false;
		}
	}
	
	public int getLowerTraceAuditNumber() {
		return currentLowerTraceAuditNumber;
	}
	
	public int getUpperTraceAuditNumber() {
		return currentUpperTraceAuditNumber;
	}

}
