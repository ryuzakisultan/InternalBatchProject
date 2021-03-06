package com.batchproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.excep.ConfigPropertyNotFound;

public final class CommonUtils {
	public static int batchSize = 0;
	public static String dbdriver = null;
	public static String dbURL = null;
	public static String user = null;
	public static String password = null;
	public static int lowerTraceAuditNo = 0;
	public static int upperTraceAuditNo = 0;
	public static int threadPoolSize = 0;
	public static String outputFileName = null;

	private static final Logger log = LogManager.getLogger(CommonUtils.class.getName());

	private CommonUtils() {

	}

	static {
		try {
			log.info("Initializing constants");
			batchSize = Config.getIntValue("batchsize");
			dbdriver = Config.getStringValue("dbdriver");
			dbURL = Config.getStringValue("url");
			user = Config.getStringValue("user");
			password = Config.getStringValue("password");
			lowerTraceAuditNo = Config.getIntValue("lowertraceauditno");
			upperTraceAuditNo = Config.getIntValue("uppertraceauditno");
			threadPoolSize = Config.getIntValue("threadpoolsize");
			outputFileName = Config.getStringValue("outputfilename");
		} catch (ConfigPropertyNotFound e) {
			log.error(e, e);
		}
	}
}
