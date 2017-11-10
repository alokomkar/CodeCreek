package com.sortedqueue.programmercreek.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.fragments.ModuleDetailsFragment
import com.sortedqueue.programmercreek.fragments.ModuleFragment
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.startapp.android.publish.adsCommon.StartAppAd

import java.util.ArrayList



import kotlinx.android.synthetic.main.activity_syntax_learn.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/*import com.tappx.sdk.android.TappxAdError;
import com.tappx.sdk.android.TappxInterstitial;
import com.tappx.sdk.android.TappxInterstitialListener;*/

class SyntaxLearnActivity : AppCompatActivity(), SyntaxNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    private var mFragmentTransaction: FragmentTransaction? = null
    private var moduleFragment: ModuleFragment? = null
    private val TAG = SyntaxLearnActivity::class.java.simpleName
    private var moduleDetailsFragment: ModuleDetailsFragment? = null
    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syntax_learn)

        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //loadTappxFullScreenAd();
        loadModulesFragment()
        checkFAB!!.setOnClickListener(this)
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    override fun onDestroy() {
        super.onDestroy()
        //if (tappxInterstitial != null) tappxInterstitial.destroy();
    }

    /*private TappxInterstitial tappxInterstitial;
    private void loadTappxFullScreenAd() {
        tappxInterstitial = new TappxInterstitial(SyntaxLearnActivity.this, getString(R.string.id_ad_tappx));
        tappxInterstitial.setAutoShowWhenReady(false);
        tappxInterstitial.loadAd();
        tappxInterstitial.setListener(new TappxInterstitialListener() {
            @Override
            public void onInterstitialLoaded(TappxInterstitial tappxInterstitial) {

            }

            @Override
            public void onInterstitialLoadFailed(TappxInterstitial tappxInterstitial, TappxAdError tappxAdError) {

            }

            @Override
            public void onInterstitialShown(TappxInterstitial tappxInterstitial) {

            }

            @Override
            public void onInterstitialClicked(TappxInterstitial tappxInterstitial) {

            }

            @Override
            public void onInterstitialDismissed(TappxInterstitial tappxInterstitial) {
                finish();
            }
        });
    }*/

    private var isFirstTime = true
    private fun loadModulesFragment() {
        supportActionBar!!.title = "Modules"
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        if (moduleFragment == null) {
            moduleFragment = ModuleFragment()
        }

        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@SyntaxLearnActivity, android.R.drawable.ic_media_play))
        if (isFirstTime) {
            checkFAB!!.visibility = View.GONE
            isFirstTime = false
        } else {
            AnimationUtils.exitReveal(checkFAB)
        }

        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, moduleFragment, ModuleFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_syntax_learn, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onModuleLoad(module: LanguageModule, nextModule: LanguageModule?) {

        if ( /*CreekApplication.creekPreferences!!.checkSyntaxUpdate() < 0 &&*/ !AuxilaryUtils.isNetworkAvailable ) {
            CommonUtils.displaySnackBar(this@SyntaxLearnActivity, R.string.internet_required, R.string.retry, View.OnClickListener { onModuleLoad(module, nextModule) })
            //CommonUtils.displayToast(this, R.string.enable_internet_to_download)
            return
        }

        CommonUtils.displayProgressDialog(this@SyntaxLearnActivity, "Loading material")
        FirebaseDatabaseHandler(this@SyntaxLearnActivity).initializeSyntax(module, object : FirebaseDatabaseHandler.SyntaxInterface {
            override fun getSyntaxModules(syntaxModules: ArrayList<SyntaxModule>) {
                loadModuleDetailsFragment(module, nextModule, syntaxModules)
            }

            override fun onError(error: DatabaseError) {
                Log.e(TAG, "Error : " + error.toString())
                CommonUtils.dismissProgressDialog()
            }
        })
    }

    override fun setImageDrawable(drawable: Int) {
        checkFAB!!.setImageDrawable(ContextCompat.getDrawable(this@SyntaxLearnActivity, drawable))
    }

    private fun loadModuleDetailsFragment(module: LanguageModule, nextModule: LanguageModule?, syntaxModules: ArrayList<SyntaxModule>) {
        supportActionBar!!.title = CreekApplication.creekPreferences!!.programLanguage.toUpperCase() + " Syntax Learner"

        mFragmentTransaction = supportFragmentManager.beginTransaction()
        if (moduleDetailsFragment == null) {
            moduleDetailsFragment = ModuleDetailsFragment()
        }
        //checkFAB.setVisibility(View.VISIBLE);
        AnimationUtils.enterReveal(checkFAB)
        moduleDetailsFragment!!.setSyntaxNavigationListener(this)
        moduleDetailsFragment!!.setParameters(module, syntaxModules, nextModule)
        mFragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        mFragmentTransaction!!.replace(R.id.container, moduleDetailsFragment, ModuleDetailsFragment::class.java.simpleName)
        mFragmentTransaction!!.commit()

        CommonUtils.dismissProgressDialog()
    }

    override fun onBackPressed() {
        val title = supportActionBar!!.title!!.toString()
        Log.d(TAG, "onBackPress : " + title)
        if (title != "Modules") {
            loadModulesFragment()
        } else {
            if (CreekApplication.creekPreferences!!.adsEnabled) {
                StartAppAd.onBackPressed(this)
                super.onBackPressed()
            } else {
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }

    override fun onClick(view: View) {
        if (moduleDetailsFragment != null) {
            moduleDetailsFragment!!.onScrollForward()
        }
    }
}
