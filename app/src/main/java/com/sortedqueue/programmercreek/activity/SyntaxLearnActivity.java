package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.ModuleDetailsFragment;
import com.sortedqueue.programmercreek.fragments.ModuleFragment;
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.tappx.sdk.android.TappxAdError;
import com.tappx.sdk.android.TappxInterstitial;
import com.tappx.sdk.android.TappxInterstitialListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SyntaxLearnActivity extends AppCompatActivity implements SyntaxNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private FragmentTransaction mFragmentTransaction;
    private ModuleFragment moduleFragment;
    private String TAG = SyntaxLearnActivity.class.getSimpleName();
    private ModuleDetailsFragment moduleDetailsFragment;
    @Override
    protected void onResume() {
        super.onResume();
        CreekApplication.getInstance().setAppRunning(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CreekApplication.getInstance().setAppRunning(false);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syntax_learn);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadTappxFullScreenAd();
        loadModulesFragment();
        checkFAB.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tappxInterstitial != null) tappxInterstitial.destroy();
    }

    private TappxInterstitial tappxInterstitial;
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
    }

    private boolean isFirstTime = true;
    private void loadModulesFragment() {
        getSupportActionBar().setTitle("Modules");
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        moduleFragment = (ModuleFragment) getSupportFragmentManager().findFragmentByTag(ModuleFragment.class.getSimpleName());
        if( moduleFragment == null ) {
            moduleFragment = new ModuleFragment();
        }

        checkFAB.setImageDrawable(ContextCompat.getDrawable(SyntaxLearnActivity.this, android.R.drawable.ic_media_play));
        if( isFirstTime ) {
            checkFAB.setVisibility(View.GONE);
            isFirstTime = false;
        }
        else {
            AnimationUtils.exitReveal(checkFAB);
        }

        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, moduleFragment, ModuleFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_syntax_learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onModuleLoad(final LanguageModule module, final LanguageModule nextModule ) {

        CommonUtils.displayProgressDialog(SyntaxLearnActivity.this, "Loading material");
        new FirebaseDatabaseHandler(SyntaxLearnActivity.this).initializeSyntax(module, new FirebaseDatabaseHandler.SyntaxInterface() {
            @Override
            public void getSyntaxModules(ArrayList<SyntaxModule> syntaxModules) {
                loadModuleDetailsFragment(module, nextModule, syntaxModules);
            }

            @Override
            public void onError(DatabaseError error) {
                Log.e(TAG, "Error : " + error.toString());
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    @Override
    public void setImageDrawable(int drawable) {
        checkFAB.setImageDrawable(ContextCompat.getDrawable(SyntaxLearnActivity.this, drawable));
    }

    private void loadModuleDetailsFragment(LanguageModule module, LanguageModule nextModule, ArrayList<SyntaxModule> syntaxModules) {
        getSupportActionBar().setTitle( new CreekPreferences(SyntaxLearnActivity.this).getProgramLanguage().toUpperCase() + " Syntax Learner");

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        moduleDetailsFragment = (ModuleDetailsFragment) getSupportFragmentManager().findFragmentByTag(ModuleDetailsFragment.class.getSimpleName());
        if( moduleDetailsFragment == null ) {
            moduleDetailsFragment = new ModuleDetailsFragment();
        }
        //checkFAB.setVisibility(View.VISIBLE);
        AnimationUtils.enterReveal(checkFAB);
        moduleDetailsFragment.setSyntaxNavigationListener(this);
        moduleDetailsFragment.setParameters( module, syntaxModules, nextModule );
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, moduleDetailsFragment, ModuleDetailsFragment.class.getSimpleName());
        mFragmentTransaction.commit();

        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onBackPressed() {
        String title = getSupportActionBar().getTitle().toString();
        Log.d(TAG, "onBackPress : " + title);
        if( !title.equals("Modules") ) {
            loadModulesFragment();
        }
        else {
            /*if( tappxInterstitial != null && tappxInterstitial.isReady() ) {
                tappxInterstitial.show();
            }
            else */{
                finish();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onClick(View view) {
        if( moduleDetailsFragment != null ) {
            moduleDetailsFragment.onScrollForward();
        }
    }
}
