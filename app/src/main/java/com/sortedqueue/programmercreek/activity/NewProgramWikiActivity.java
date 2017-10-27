package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.ProgramWikiNavRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.ProgramWikiPagerAdapter;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class NewProgramWikiActivity extends AppCompatActivity implements View.OnClickListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.programWikiViewPager)
    ScrollableViewPager programWikiViewPager;
    @BindView(R.id.ProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.firstQuestionImageView)
    ImageView firstQuestionImageView;
    @BindView(R.id.prevQuestionImageView)
    ImageView prevQuestionImageView;
    @BindView(R.id.indexTextView)
    TextView indexTextView;
    @BindView(R.id.nextQuestionImageView)
    ImageView nextQuestionImageView;
    @BindView(R.id.lastQuestionImageView)
    ImageView lastQuestionImageView;
    @BindView(R.id.navigationLayout)
    RelativeLayout navigationLayout;
    @BindView(R.id.programRecyclerView)
    RecyclerView programRecyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ProgramWikiPagerAdapter programWikiPagerAdapter;
    private InterstitialAd interstitialAd;
    private Toolbar toolbar;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program_wiki);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Program Wiki : " + CreekApplication.Companion.getCreekPreferences().getProgramLanguage().toUpperCase());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        CommonUtils.displayProgressDialog(NewProgramWikiActivity.this, "Loading");
        new FirebaseDatabaseHandler(NewProgramWikiActivity.this).initializeProgramWiki(
                new FirebaseDatabaseHandler.ProgramWikiInterface() {
                    @Override
                    public void getProgramWiki(ArrayList<WikiModel> programWikis) {
                        setupNavRecyclerView(programWikis);
                        programWikiPagerAdapter = new ProgramWikiPagerAdapter(getSupportFragmentManager(), programWikis);
                        programWikiViewPager.setAdapter(programWikiPagerAdapter);
                        programWikiViewPager.setCanScroll(false);
                        progressBar.setMax(programWikis.size());
                        progressBar.setProgress(1);
                        indexTextView.setText(1 + "/" + programWikiPagerAdapter.getCount());
                        toggleVisiblity(0);
                        programWikiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                progressBar.setProgress(position + 1);
                                toggleVisiblity(position);
                                indexTextView.setText((position + 1) + "/" + programWikiPagerAdapter.getCount());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                        setupNavigationListener();
                        CommonUtils.dismissProgressDialog();
                    }


                    @Override
                    public void onError(DatabaseError error) {
                        CommonUtils.dismissProgressDialog();
                    }
                });
        if( !CreekApplication.Companion.getCreekPreferences().isPremiumUser() ) {
            initAds();
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);


    }

    private void setupNavRecyclerView(ArrayList<WikiModel> programWikis) {
        programRecyclerView.setLayoutManager( new LinearLayoutManager(NewProgramWikiActivity.this, LinearLayoutManager.VERTICAL, false));
        programRecyclerView.setAdapter( new ProgramWikiNavRecyclerAdapter(NewProgramWikiActivity.this, programWikis));
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
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
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        interstitialAd.loadAd(adRequest);
    }

    private void setupNavigationListener() {
        firstQuestionImageView.setOnClickListener(this);
        prevQuestionImageView.setOnClickListener(this);
        nextQuestionImageView.setOnClickListener(this);
        lastQuestionImageView.setOnClickListener(this);
    }

    private void toggleVisiblity(int position) {
        firstQuestionImageView.setVisibility(View.VISIBLE);
        prevQuestionImageView.setVisibility(View.VISIBLE);
        lastQuestionImageView.setVisibility(View.VISIBLE);
        nextQuestionImageView.setVisibility(View.VISIBLE);

        if (position == 0) {
            firstQuestionImageView.setVisibility(View.GONE);
            prevQuestionImageView.setVisibility(View.GONE);
        } else if (position == programWikiPagerAdapter.getCount() - 1) {
            lastQuestionImageView.setVisibility(View.GONE);
            nextQuestionImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstQuestionImageView:
                programWikiViewPager.setCurrentItem(0);
                break;
            case R.id.prevQuestionImageView:
                programWikiViewPager.setCurrentItem(programWikiViewPager.getCurrentItem() - 1);
                break;
            case R.id.nextQuestionImageView:
                programWikiViewPager.setCurrentItem(programWikiViewPager.getCurrentItem() + 1);
                break;
            case R.id.lastQuestionImageView:
                programWikiViewPager.setCurrentItem(programWikiPagerAdapter.getCount() - 1);
                break;
        }
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (!isAdShown && interstitialAd != null && interstitialAd.isLoaded() && !CreekApplication.Companion.getCreekPreferences().isPremiumUser() ) {
            interstitialAd.show();
            isAdShown = true;
            return;
        }
        finish();
    }

    @Override
    public void onItemClick(int position) {
        if( programWikiViewPager != null ) {
            programWikiViewPager.setCurrentItem(position);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
