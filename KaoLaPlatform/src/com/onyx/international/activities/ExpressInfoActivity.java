package com.onyx.international.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.onyx.international.kaolaplatform.R;

public class ExpressInfoActivity extends BaseActivity {

	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_express_info);
		
		mWebView = (WebView) findViewById(R.id.webView_express_info);
		mWebView.getSettings().setJavaScriptEnabled(true);
		setTopBarLeftButtonTypeBack();
		
		setTitle(R.string.express_info);
		
		mWebView.loadUrl("http://m.kuaidi100.com/index_all.html?type=shentong&postid=768371781744#result");
	}
}
