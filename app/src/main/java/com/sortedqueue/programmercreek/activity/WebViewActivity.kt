package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class WebViewActivity : AppCompatActivity() {

    internal var mWebView: WebView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        //Bundle bundle = getIntent().getExtras();
        title = "My Profile"
        //String url = "http://365programperday.blogspot.in/";
        val url = "https://www.linkedin.com/pub/alok-omkar/b/917/333"
        load(url)
    }

    private fun load(url: String) {
        mWebView = findViewById(R.id.webView1) as WebView
        mWebView!!.setWebViewClient(WebViewClient())
        mWebView!!.loadUrl(url)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

}
