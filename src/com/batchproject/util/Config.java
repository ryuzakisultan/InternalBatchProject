package com.batchproject.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.batchproject.excep.ConfigPropertyNotFound;

public final class Config {
	private static Properties prop = new Properties();
	private static InputStream input = null;
	
	private static final Logger log = LogManager.getLogger(Config.class.getName());
	
	private Config() {
		// Can't make instance
	}

	static {
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException e) {
			log.error(e,e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error(e,e);
				}
			}
		}
	}

	public static String getStringValue(String key) throws ConfigPropertyNotFound {
		String value = null;
		value = prop.getProperty(key);
		if (value == null) {
			throw new ConfigPropertyNotFound(key);
		}
		//TODO Logging
		return value;

	}

	public static int getIntValue(String key) throws ConfigPropertyNotFound{
		int value = 0;
		try {
			String s = prop.getProperty(key);
			if (s == null) {
				throw new ConfigPropertyNotFound(key);
			}
			value = Integer.parseInt(s);
			// TODO Logging
		} catch (NumberFormatException e) {
			log.error(e,e);
		}
		return value;
	}

}
