package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/*import com.tappx.sdk.android.TappxAdError;
import com.tappx.sdk.android.TappxInterstitial;
import com.tappx.sdk.android.TappxInterstitialListener;*/

/**
 * Created by Alok on 05/01/17.
 */

public class ChaptersActivity extends AppCompatActivity implements ChapterNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.reputationProgressBar)
    ProgressBar reputationProgressBar;
    @BindView(R.id.reputationTextView)
    TextView reputationTextView;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.checkFAB)
    FloatingActionButton checkFAB;
    @BindView(R.id.shareTextView)
    TextView shareTextView;
    @BindView(R.id.shareNowTextView)
    TextView shareNowTextView;
    @BindView(R.id.laterTextView)
    TextView laterTextView;
    @BindView(R.id.shareLayout)
    RelativeLayout shareLayout;
    private FragmentTransaction mFragmentTransaction;
    private ChapterDetailsFragment chapterDetailsFragment;
    private ChaptersFragment chaptersFragment;
    private Handler handler;
    private CreekPreferences creekPreferences;
    private CreekUserStats creekUserStats;
    private Runnable runnable;
    private int previousLevel;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_module);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadChapterFragment();
        //loadTappxFullScreenAd();
        checkFAB.setOnClickListener(this);
        shareNowTextView.setOnClickListener(this);
        laterTextView.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

   /* private TappxInterstitial tappxInterstitial;
    private void loadTappxFullScreenAd() {
        tappxInterstitial = new TappxInterstitial(ChaptersActivity.this, getString(R.string.id_ad_tappx));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (tappxInterstitial != null) tappxInterstitial.destroy();
    }

    private boolean isFirstTime = true;

    private void loadChapterFragment() {
        getSupportActionBar().setTitle("Chapters : " + CreekApplication.getCreekPreferences().getProgramLanguage().toUpperCase());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chaptersFragment = (ChaptersFragment) getSupportFragmentManager().findFragmentByTag(ChaptersFragment.class.getSimpleName());
        if (chaptersFragment == null) {
            chaptersFragment = new ChaptersFragment();
        }
        checkFAB.setImageDrawable(ContextCompat.getDrawable(ChaptersActivity.this, android.R.drawable.ic_media_play));
        if (isFirstTime) {
            checkFAB.setVisibility(View.GONE);
            isFirstTime = false;
        } else {
            AnimationUtils.exitReveal(checkFAB);
        }
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, chaptersFragment, ChaptersFragment.class.getSimpleName());
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
    public void onBackPressed() {
        String title = getSupportActionBar().getTitle().toString();
        if (!title.equals("Chapters : " + CreekApplication.getCreekPreferences().getProgramLanguage().toUpperCase())) {
            loadChapterFragment();
        } else {
            /*if( tappxInterstitial != null && tappxInterstitial.isReady() ) {
                tappxInterstitial.show();
            }
            else {
                finish();
            }*/
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onChapterSelected(Chapter chapter, Chapter nextChapter) {
        Log.d("ChaptersActivity", "Selected chapter : " + chapter.toString());
        getSupportActionBar().setTitle(chapter.getChapterName());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chapterDetailsFragment = (ChapterDetailsFragment) getSupportFragmentManager().findFragmentByTag(ChapterDetailsFragment.class.getSimpleName());
        if (chapterDetailsFragment == null) {
            chapterDetailsFragment = new ChapterDetailsFragment();
        }
        //checkFAB.setVisibility(View.VISIBLE);
        AnimationUtils.enterReveal(checkFAB);
        chapterDetailsFragment.setChapter(chapter);
        chapterDetailsFragment.setNextChapter(nextChapter);
        chapterDetailsFragment.setOnChapterNavigationListener(this);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, chapterDetailsFragment, ChapterDetailsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void toggleFabDrawable(int drawable) {
        checkFAB.setImageDrawable(ContextCompat.getDrawable(ChaptersActivity.this, drawable));
    }

    @Override
    public void onProgessStatsUpdate(int points) {
        progressLayout.setVisibility(View.VISIBLE);
        animateProgress(points);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkFAB :
                if (chapterDetailsFragment != null) {
                    chapterDetailsFragment.onScrollForward();
                }
                break;
            case R.id.shareNowTextView :
                shareNow();
                AnimationUtils.slideOutToLeft(shareLayout);
                break;
            case R.id.laterTextView :
                AnimationUtils.slideOutToLeft(shareLayout);
                break;
        }

    }



    private int progressBarStatus;

    public void animateProgress(final int points) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = new Handler();
                }
                if (creekPreferences == null) {
                    creekPreferences = CreekApplication.getCreekPreferences();
                }
                creekUserStats = creekPreferences.getCreekUserStats();
                if (creekUserStats == null) {
                    reputationProgressBar.setVisibility(View.GONE);
                    reputationTextView.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    return;
                }
                final int progress = creekUserStats.getCreekUserReputation() % 100;
                reputationProgressBar.setVisibility(View.VISIBLE);
                reputationTextView.setVisibility(View.VISIBLE);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (progressBarStatus = 0; progressBarStatus <= progress; progressBarStatus++) {

                            handler.post(new Runnable() {
                                public void run() {
                                    if (reputationProgressBar != null) {
                                        reputationProgressBar.setProgress(progressBarStatus);

                                        reputationTextView.setText("You've gained " + points + "xp\n" + progressBarStatus + "% Complete");
                                        int level = creekUserStats.getCreekUserReputation() / 100;
                                        if (level > 0) {
                                            reputationTextView.setText("You've gained " + points + "xp\n" + progressBarStatus + "% Complete : Level : " + level);
                                        }
                                    }
                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (Exception ex) {
                            }
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressLayout.setVisibility(View.GONE);
                                int level = creekUserStats.getCreekUserReputation() / 100;
                                if (level > 0) {
                                    showShareLayout(level);
                                }

                            }
                        }, 1500);

                    }
                };
                new Thread(runnable).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (progressLayout != null) {
                progressLayout.setVisibility(View.GONE);
            }
        }

    }

    private void showShareLayout(int level) {
        previousLevel = creekPreferences.getLevel();
        if (previousLevel < level) {
            previousLevel = level - 1;
            shareTextView.setText("Congratulations on cracking the level "+ (level - 1) +". You are moving on to next level. Let's share your progress...");
            AnimationUtils.slideInToLeft(shareLayout);
            creekPreferences.setLevel(level);
        }
    }

    private void shareNow() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey Friends, I've just completed level "+previousLevel+" on "+getString(R.string.app_name)+"\n\nCheck out this app : \n" + getString(R.string.app_url));
        startActivity(Intent.createChooser(shareIntent, "Level up on : " + getString(R.string.app_name) + " App"));
        if( shareLayout.getVisibility() != View.GONE )
            shareLayout.setVisibility(View.GONE);
    }


}
