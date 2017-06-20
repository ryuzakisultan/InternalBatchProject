package com.batchproject.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.Main;

public final class Config {
	private static Properties prop = new Properties();
	private static InputStream input = null;
	
	static String sql = "select trace_audit_no, card_prg_id, trans_date from trans_requests where trace_audit_no >= {} and trace_audit_no <= {};";
	private static final Logger log = LogManager.getLogger(Config.class.getName());
	
	private Config() {
		// Can't make instance
	}

	static {
		try {
			// TODO Logging
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException ex) {
			// TODO Logging
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String getStringValue(String key) {
		String value = null;
		value = prop.getProperty(key);
		if (value == null) {
			//TODO handle null pointer exception
		}
		// TODO Logging
		return value;

	}

	public static int getIntValue(String key) {
		int value = 0;

		try {
			String s = prop.getProperty(key);
			if (s == null) {
				//TODO handle null pointer exception
			}
			value = Integer.parseInt(s);
			// TODO Logging
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return value;
	}

}
