package com.onyx.international.logger;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;


public class Logger {

	public static boolean isDebug = true;
	private String className = null;
	private static Map<String, Logger> mLoggerMap;
	
	public static Logger getLogger(Class<?> cls) {
		Logger logger = null;
		if (mLoggerMap == null) {
			mLoggerMap = new HashMap<String, Logger>();
			logger = new Logger(cls.getName());
			mLoggerMap.put(cls.getName(), logger);
		} else {
			logger = mLoggerMap.get(cls.getName());
			if (logger == null) {
				logger = new Logger(cls.getName());
				mLoggerMap.put(cls.getName(), logger);
			}
		}
		return logger;
	}
	
	private Logger(String className) {
		this.className = className;
	}
	
	public void debug(String msg) {
		if (isDebug) {
			Log.d(this.className, msg);
		}
	}
}
