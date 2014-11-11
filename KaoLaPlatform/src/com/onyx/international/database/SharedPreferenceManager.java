package com.onyx.international.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager {

	private static SharedPreferenceManager instance = null;
	
	private SharedPreferences sharedPreferences;
	
	public static SharedPreferenceManager sharedManager() {
		if (instance == null) {
			instance = new SharedPreferenceManager();
		}
		return instance;
	}
	
	public void init(Context context) {
		sharedPreferences = context.getSharedPreferences("info", 0);
	}
	
	private SharedPreferenceManager() {
		
	}
	
	public void setString(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}
	
	public void setInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}
	
	public void setBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, false);
	}
	
	public void clear(String key) {
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
