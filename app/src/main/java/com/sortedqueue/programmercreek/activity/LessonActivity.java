package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.lessons.Lesson;
import com.sortedqueue.programmercreek.fragments.LessonDetailsFragment;
import com.sortedqueue.programmercreek.fragments.LessonsFragment;
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 29/08/17.
 */

public class LessonActivity extends AppCompatActivity implements View.OnClickListener, LessonNavigationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shareTextView)
    TextView shareTextView;
    @BindView(R.id.shareNowTextView)
    TextView shareNowTextView;
    @BindView(R.id.laterTextView)
    TextView laterTextView;
    @BindView(R.id.shareLayout)
    RelativeLayout shareLayout;
    @BindView(R.id.reputationProgressBar)
    ProgressBar reputationProgressBar;
    @BindView(R.id.reputationTextView)
    TextView reputationTextView;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;
    @BindView(R.id.container)
    FrameLayout container;

    private FragmentTransaction mFragmentTransaction;
    private LessonsFragment lessonsFragment;
    private boolean isFirstTime;
    private LessonDetailsFragment lessonDetailsFragment;
    private InterstitialAd interstitialAd;
    private Lesson currentLesson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadLessons();
        initAds();

        shareNowTextView.setOnClickListener(this);
        laterTextView.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareNowTextView :
                shareNow("");
                AnimationUtils.slideOutToLeft(shareLayout);
                break;
            case R.id.laterTextView :
                AnimationUtils.slideOutToLeft(shareLayout);
                break;
        }
    }

    private void shareNow(String shareMessage) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage + "\n\nCheck out this app : \n" + getString(R.string.app_url));
        startActivity(Intent.createChooser(shareIntent, "Level up on : " + getString(R.string.app_name) + " App"));
        if( shareLayout.getVisibility() != View.GONE )
            shareLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadLessons() {
        getSupportActionBar().setTitle("Bits & Bytes : " + CreekApplication.getCreekPreferences().getProgramLanguage().toUpperCase());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        lessonsFragment = (LessonsFragment) getSupportFragmentManager().findFragmentByTag(LessonsFragment.class.getSimpleName());
        if (lessonsFragment == null) {
            lessonsFragment = new LessonsFragment();
        }
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, lessonsFragment, LessonsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void onLessonSelected(Lesson lesson) {
        getSupportActionBar().setTitle(lesson.getTitle());
        currentLesson = lesson;
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        lessonDetailsFragment = (LessonDetailsFragment) getSupportFragmentManager().findFragmentByTag(LessonDetailsFragment.class.getSimpleName());
        if (lessonDetailsFragment == null) {
            lessonDetailsFragment = new LessonDetailsFragment();
        }
        lessonDetailsFragment.setLesson(lesson);

        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, lessonDetailsFragment, LessonDetailsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
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

        String title = getSupportActionBar().getTitle().toString();
        if( currentLesson != null && title.equals(currentLesson.getTitle()) ) {
            loadLessons();
        }
        else {
            if (!isAdShown && interstitialAd.isLoaded() /*&& CreekApplication.getCreekPreferences().getAdsEnabled()*/) {
                interstitialAd.show();
                isAdShown = true;
                return;
            }
            finish();

        }
    }
}
