package com.onyx.international.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

	public static Map<String, String> buildKeysAndValues(String[] keys, String[] values) {
		Map<String, String> map = new HashMap<String, String>();
		int length1 = keys.length;
		int length2 = keys.length;
		
		if (length1 != length2) {
			return map;
		}
		
		for (int i=0; i<length1; ++i) {
			map.put(keys[i], values[i]);
		}
		
		return map;
	}
}
