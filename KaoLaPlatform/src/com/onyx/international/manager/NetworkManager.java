package com.onyx.international.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.os.AsyncTask;

import com.onyx.international.network.HttpRequest;

public class NetworkManager {

	public static final String URL_BASE = "http://1.93.15.5:8080/admin/interface/";
	
	private static NetworkManager instance = null;
	
	public static NetworkManager sharedManager() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		
		return instance;
	}
	
	public void sendRequest(String subUrl, Map<String, String> keysAndValues,
			final NetworkManagerCallback callback) {
		StringBuffer sb = new StringBuffer(URL_BASE);
		
		sb.append(subUrl);
		sb.append("?");
		if (keysAndValues != null) {
			Set<Entry<String, String>> set = keysAndValues.entrySet();
			for (Iterator<Entry<String, String>> iter = set.iterator(); iter
					.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
						.next();
				sb.append((String) entry.getKey());
				sb.append("=");
				sb.append((String) entry.getValue());
				sb.append("&");
			}
		}
		final String url = sb.toString();
		System.out.println("-----url=" + url);
		NetworkTask task = new NetworkTask();
		task.execute(url, callback);
	}
	
	private class NetworkTask extends AsyncTask<Object, Integer, String> {

		NetworkManagerCallback mCallback;
		
		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			mCallback = (NetworkManagerCallback) params[1];
			return HttpRequest.sendGetRequest((String) params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mCallback.onNetworkRequestSuccess(result);
		}
	}
}
