package com.sortedqueue.programmercreek.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;

/**
 * Created by Alok Omkar on 2016-12-16.
 */

public class ProgramWikiActivity extends AppCompatActivity {

    private WebView webView;
    private String programWiki;
    private ContentLoadingProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_wiki);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        webView.setWebViewClient( new MyWebViewClient() );
        programWiki = getIntent().getExtras().getString(DatabaseHandler.KEY_WIKI);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if( programWiki != null ) {
            webView.loadUrl(programWiki);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("programercreek.blogspot.in")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if( url.equalsIgnoreCase(DashboardActivity.PROGRAMER_CREEK_WIKI) ) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                progressBar.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.hide();
            progressBar.setVisibility(View.GONE);
            CommonUtils.dismissProgressDialog();
        }
    }
}
