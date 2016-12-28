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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alok Omkar on 2016-12-16.
 */

public class ProgramWikiActivity extends AppCompatActivity {

    private WebView webView;
    private String programWiki;
    private ContentLoadingProgressBar progressBar;
    private InterstitialAd interstitialAd;
    private AdView mAdView;
    private String WIKI_BASE_URL = "programercreek.blogspot.in";
    private CreekPreferences creekPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_wiki);
        creekPreferences = new CreekPreferences(ProgramWikiActivity.this);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        webView.setWebViewClient( new MyWebViewClient() );
        programWiki = getIntent().getExtras().getString(DatabaseHandler.KEY_WIKI);
        if( !creekPreferences.getWikiHelp() ) {
            creekPreferences.setWikihelp(true);
            AuxilaryUtils.generateBigTextNotification(ProgramWikiActivity.this, "Creek", "Welcome to Wiki of programs, browse through all programs and explanations here. Feel free to leave a comment.");
        }
        try {
            WIKI_BASE_URL = new URL(programWiki).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            WIKI_BASE_URL = "programercreek.blogspot.in";
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if( programWiki != null ) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.show();
            webView.loadUrl(programWiki);
        }
        initAds();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();

        //For creating test ads
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstital_wiki_ad_id));
        requestNewInterstitial();

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(WIKI_BASE_URL)) {
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
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                progressBar.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.hide();
            progressBar.setVisibility(View.GONE);
            CommonUtils.dismissProgressDialog();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        interstitialAd.loadAd(adRequest);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    boolean isAdShown = false;

    @Override
    public void onBackPressed() {
        if( !isAdShown ) {
            interstitialAd.show();
            isAdShown = true;
            return;
        }
        finish();
    }
}
