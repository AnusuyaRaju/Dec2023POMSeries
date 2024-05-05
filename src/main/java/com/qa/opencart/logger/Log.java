package com.qa.opencart.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	
	//By using this Log classname we can print o/p thru Log.info() & if it's error Log().error() so we can get detailed o/p in console with date n timestamp
	//once refresh the proj, logs folder will be created inside application.log file generated n it contains logs
	
	//Inside log4j2 file append="true" means previous logs retain with new logs if it's false then it will generate only new logs everytime
	//<File name="File" fileName="logs/application.log" append="false">
	
	private static final Logger logger = LogManager.getLogger(Log.class);

	public static void info(String message) {
		logger.info(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void error(String message, Exception e) {
		logger.error(message, e);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

}
