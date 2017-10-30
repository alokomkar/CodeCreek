package com.sortedqueue.programmercreek.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences

import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList



import kotlinx.android.synthetic.main.activity_program_wiki.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Alok Omkar on 2016-12-16.
 */

class ProgramWikiActivity : AppCompatActivity() {

    private var webView: WebView? = null
    private var programWiki: String? = null
    private var progressBar: ContentLoadingProgressBar? = null

    private var mAdView: AdView? = null
    private var WIKI_BASE_URL = "https://syntaxdb.com"
    private var creekPreferences: CreekPreferences? = null
    private var program_indices: ArrayList<ProgramIndex>? = null
    private var programUrls: ArrayList<String>? = null

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program_wiki)

        creekPreferences = CreekApplication.creekPreferences
        webView = findViewById(R.id.webView) as WebView
        progressBar = findViewById(R.id.progressBar) as ContentLoadingProgressBar
        webView!!.setWebViewClient(MyWebViewClient())
        if (intent.extras != null) {
            programWiki = intent.extras!!.getString(ProgrammingBuddyConstants.KEY_WIKI, WIKI_BASE_URL)
            program_indices = intent.extras!!.getParcelableArrayList<ProgramIndex>(ProgrammingBuddyConstants.KEY_PROGRAM_LANGUAGE)
            if (program_indices != null) {
                programUrls = ArrayList<String>()
                for (program_index in program_indices!!) {
                    programUrls!!.add(program_index.wiki)
                }
            }
        }
        programWiki = WIKI_BASE_URL

        if (!creekPreferences!!.wikiHelp) {
            creekPreferences!!.setWikihelp(true)
            AuxilaryUtils.generateBigTextNotification(this@ProgramWikiActivity, "SyntaxDB.com", "Search any syntax here")
        }
        try {
            WIKI_BASE_URL = URL(programWiki).host
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            WIKI_BASE_URL = "programercreek.blogspot.in"
        }

        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        loadUrl()
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        setupLiseners()
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun setupLiseners() {
        firstQuestionImageView!!.setOnClickListener {
            var index = programUrls!!.indexOf(programWiki)
            if (index + 1 != programUrls!!.size) {
                programWiki = programUrls!![++index]
                loadUrl()
            }
        }
        lastQuestionImageView!!.setOnClickListener {
            var index = programUrls!!.indexOf(programWiki)
            if (index - 1 >= 0) {
                programWiki = programUrls!![--index]
                loadUrl()
            }
        }
    }

    private fun loadUrl() {
        if (programWiki != null) {
            progressBar!!.visibility = View.VISIBLE
            progressBar!!.isIndeterminate = true
            progressBar!!.show()
            webView!!.loadUrl(programWiki)
        }
    }

    private var interstitialAd: InterstitialAd? = null
    private fun initAds() {
        if (CreekApplication.creekPreferences!!.adsEnabled) {
            MobileAds.initialize(applicationContext, getString(R.string.mobile_banner_id))
            mAdView = findViewById(R.id.adView) as AdView
            mAdView!!.visibility = View.GONE
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();

            //For creating test ads
            val adRequest = AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build()
            mAdView!!.loadAd(adRequest)
            mAdView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    mAdView!!.visibility = View.GONE
                }
            }
        }
        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = getString(R.string.interstital_wiki_ad_id)
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                finish()
            }

            override fun onAdFailedToLoad(i: Int) {
                super.onAdFailedToLoad(i)
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }
        }
        requestNewInterstitial()

    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build()
        interstitialAd!!.loadAd(adRequest)
    }

    override fun onBackPressed() {
        if (!isAdShown && interstitialAd!!.isLoaded /*&& CreekApplication.getPreferences().getAdsEnabled()*/) {
            interstitialAd!!.show()
            isAdShown = true
            return
        }
        finish()
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (Uri.parse(url).host == WIKI_BASE_URL) {
                // This is my web site, so do not override; let my WebView load the page
                return false
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
            progressBar!!.visibility = View.VISIBLE
            progressBar!!.isIndeterminate = true
            progressBar!!.show()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar!!.hide()
            progressBar!!.visibility = View.GONE
            CommonUtils.dismissProgressDialog()
        }
    }


    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    internal var isAdShown = false


}
