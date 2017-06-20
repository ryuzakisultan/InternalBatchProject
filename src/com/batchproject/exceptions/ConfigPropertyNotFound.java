package com.batchproject.exceptions;

@SuppressWarnings("serial")
public final class ConfigPropertyNotFound extends Exception {
	
	public ConfigPropertyNotFound(String name) {
		super("\"" + name + "\" property in config file not found");
	}

}
