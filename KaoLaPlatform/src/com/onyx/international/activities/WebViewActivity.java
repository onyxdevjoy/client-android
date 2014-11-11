package com.onyx.international.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.onyx.international.kaolaplatform.R;

public class WebViewActivity extends BaseActivity {

	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_web_view);
		
		setTopBarLeftButtonTypeBack();
		
		Bundle data = getIntent().getExtras();
		String url = data.getString("url");
		String title = data.getString("title");
		
		setTitle(title);
		
		mWebView = (WebView) findViewById(R.id.webView_activity_web_view);
		mWebView.loadUrl(url);
	}
}
