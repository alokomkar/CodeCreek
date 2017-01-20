package com.sortedqueue.programmercreek.activity;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2016-12-16.
 */

public class ProgramWikiActivity extends AppCompatActivity {

    @Bind(R.id.firstQuestionImageView)
    ImageView firstQuestionImageView;
    @Bind(R.id.indexTextView)
    TextView indexTextView;
    @Bind(R.id.lastQuestionImageView)
    ImageView lastQuestionImageView;
    private WebView webView;
    private String programWiki;
    private ContentLoadingProgressBar progressBar;
    private InterstitialAd interstitialAd;
    private AdView mAdView;
    private String WIKI_BASE_URL = "https://syntaxdb.com";
    private CreekPreferences creekPreferences;
    private ArrayList<ProgramIndex> program_indices;
    private ArrayList<String> programUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_wiki);
        ButterKnife.bind(this);
        creekPreferences = new CreekPreferences(ProgramWikiActivity.this);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        webView.setWebViewClient(new MyWebViewClient());
        if( getIntent().getExtras() != null ) {
            programWiki = getIntent().getExtras().getString(ProgrammingBuddyConstants.KEY_WIKI, WIKI_BASE_URL);
            program_indices = getIntent().getExtras().getParcelableArrayList(ProgrammingBuddyConstants.KEY_PROGRAM_LANGUAGE);
            if( program_indices != null ) {
                programUrls = new ArrayList<>();
                for( ProgramIndex program_index : program_indices ) {
                    programUrls.add(program_index.getWiki());
                }
            }
        }
        programWiki = WIKI_BASE_URL;

        if (!creekPreferences.getWikiHelp()) {
            creekPreferences.setWikihelp(true);
            AuxilaryUtils.generateBigTextNotification(ProgramWikiActivity.this, "SyntaxDB.com", "Search any syntax here");
        }
        try {
            WIKI_BASE_URL = new URL(programWiki).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            WIKI_BASE_URL = "programercreek.blogspot.in";
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        loadUrl();
        initAds();
        setupLiseners();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupLiseners() {
        firstQuestionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = programUrls.indexOf(programWiki);
                if( index + 1 != programUrls.size() ) {
                    programWiki = programUrls.get(++index);
                    loadUrl();
                }

            }
        });
        lastQuestionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = programUrls.indexOf(programWiki);
                if( index - 1 >= 0 ) {
                    programWiki = programUrls.get(--index);
                    loadUrl();
                }

            }
        });
    }

    private void loadUrl() {
        if (programWiki != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.show();
            webView.loadUrl(programWiki);
        }
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
                mAdView.setVisibility(View.GONE);
            }
        });
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstital_wiki_ad_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                finish();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
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
        if (!isAdShown) {
            interstitialAd.show();
            isAdShown = true;
            return;
        }
        finish();
    }
}
