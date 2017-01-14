package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.adapter.ProgramWikiPagerAdapter;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class NewProgramWikiActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.programWikiViewPager)
    ScrollableViewPager programWikiViewPager;
    @Bind(R.id.ProgressBar)
    ProgressBar progressBar;
    @Bind(R.id.firstQuestionImageView)
    ImageView firstQuestionImageView;
    @Bind(R.id.prevQuestionImageView)
    ImageView prevQuestionImageView;
    @Bind(R.id.indexTextView)
    TextView indexTextView;
    @Bind(R.id.nextQuestionImageView)
    ImageView nextQuestionImageView;
    @Bind(R.id.lastQuestionImageView)
    ImageView lastQuestionImageView;
    @Bind(R.id.navigationLayout)
    RelativeLayout navigationLayout;
    private ProgramWikiPagerAdapter programWikiPagerAdapter;
    private InterstitialAd interstitialAd;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program_wiki);
        ButterKnife.bind(this);
        CommonUtils.displayProgressDialog(NewProgramWikiActivity.this, "Loading");
        new FirebaseDatabaseHandler(NewProgramWikiActivity.this).initializeProgramWiki(
                new FirebaseDatabaseHandler.ProgramWikiInterface() {
            @Override
            public void getProgramWiki(ArrayList<WikiModel> programWikis) {
                programWikiPagerAdapter = new ProgramWikiPagerAdapter(getSupportFragmentManager(), programWikis);
                programWikiViewPager.setAdapter(programWikiPagerAdapter);
                programWikiViewPager.setCanScroll(false);
                progressBar.setMax(programWikis.size());
                progressBar.setProgress(1);
                indexTextView.setText( 1 + "/" + programWikiPagerAdapter.getCount());
                toggleVisiblity(0);
                programWikiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        progressBar.setProgress(position + 1);
                        toggleVisiblity(position);
                        indexTextView.setText( (position + 1) + "/" + programWikiPagerAdapter.getCount());
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
        initAds();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);


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
                finish();
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

        if( position == 0 ) {
            firstQuestionImageView.setVisibility(View.GONE);
            prevQuestionImageView.setVisibility(View.GONE);
        }
        else if( position == programWikiPagerAdapter.getCount() -1 ) {
            lastQuestionImageView.setVisibility(View.GONE);
            nextQuestionImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.firstQuestionImageView :
                programWikiViewPager.setCurrentItem(0);
                break;
            case R.id.prevQuestionImageView :
                programWikiViewPager.setCurrentItem( programWikiViewPager.getCurrentItem() - 1 );
                break;
            case R.id.nextQuestionImageView :
                programWikiViewPager.setCurrentItem( programWikiViewPager.getCurrentItem() + 1 );
                break;
            case R.id.lastQuestionImageView :
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
        if (!isAdShown) {
            interstitialAd.show();
            isAdShown = true;
            return;
        }
        finish();
    }
}
