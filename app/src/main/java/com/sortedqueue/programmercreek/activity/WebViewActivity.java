package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sortedqueue.programmercreek.R;


public class WebViewActivity extends AppCompatActivity {

	WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		//Bundle bundle = getIntent().getExtras();
		setTitle("My Profile");
		//String url = "http://365programperday.blogspot.in/";
		String url = "https://www.linkedin.com/pub/alok-omkar/b/917/333";
		load( url );
	}

	private void load(String url){
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }

}
