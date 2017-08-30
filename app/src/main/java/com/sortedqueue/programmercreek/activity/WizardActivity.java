package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.fragments.NewFillBlankFragment;
import com.sortedqueue.programmercreek.fragments.NewMatchFragment;
import com.sortedqueue.programmercreek.fragments.QuizFragment;
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WizardActivity extends AppCompatActivity implements WizardNavigationListener {

    @BindView(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private NewMatchFragment matchMakerFragment;
    private TestDragNDropFragment testDragNDropFragment;
    private QuizFragment quizFragment;
    private NewFillBlankFragment fillBlankFragment;
    private boolean isAdShown;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wizard, menu);
        return true;
    }

    private boolean isRewardVideoShown = false;
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if( item.getItemId() == R.id.action_hint ) {
            if( !isRewardVideoShown )
                showRewardedVideoDialog();
            else
                showSolutionFromFragment();
        }
        return true;


    }

    public void showRewardedVideoDialog() {
        AuxilaryUtils.displayInformation(WizardActivity.this, R.string.hint_video, R.string.reward_video_description,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showRewardedClick();
                    }
                },

                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        showRewardedClick();
                    }
                });
    }

    private void showRewardedClick() {
        final StartAppAd rewardedVideo = new StartAppAd(WizardActivity.this);

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener(new VideoListener() {

            @Override
            public void onVideoCompleted() {
                isRewardVideoShown = true;
                showSolutionFromFragment();
            }
        });

        /**
         * Load rewarded by specifying AdMode.REWARDED
         * We are using AdEventListener to trigger ad show
         */
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {

            @Override
            public void onReceiveAd(Ad arg0) {
                rewardedVideo.showAd();
            }

            @Override
            public void onFailedToReceiveAd(Ad arg0) {
                /**
                 * Failed to load rewarded video:
                 * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
                 * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
                 * 2. Is android API level above 16?
                 */
                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.getErrorMessage());
            }
        });
    }

    private void showSolutionFromFragment() {
        if( matchMakerFragment != null ) {
            showSolutionDialog( matchMakerFragment.getmProgramList() );
        }
        else if( testDragNDropFragment != null ) {
            showSolutionDialog( testDragNDropFragment.getmProgramList() );
        }
        else if( fillBlankFragment != null ) {
            //showSolutionDialog( fillBlankFragment.getmProgramList() );
            fillBlankFragment.addHintsToBlanks();
        }
        else if( quizFragment != null ) {
            showSolutionDialog( quizFragment.getmProgramList() );
        }
    }

    private void showSolutionDialog( ArrayList<String> solutionList ) {
        String solution = "";
        for( String string : solutionList ) {
            solution += string + "\n";
        }
        Log.d("SolutionProgram", solution);
        AuxilaryUtils.displayAlert("Solution", solution, WizardActivity.this);
    }

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
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST)) {
            case ProgrammingBuddyConstants.KEY_MATCH :
                loadMatchMakerFragment( bundle );
                break;
            case ProgrammingBuddyConstants.KEY_TEST:
                loadTestFragment( bundle );
                break;
            case ProgrammingBuddyConstants.KEY_QUIZ:
                loadQuizFragment( bundle );
                break;
            case ProgrammingBuddyConstants.KEY_FILL_BLANKS:
                loadFillBlanksFragment(bundle);
                break;
        }

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    @Override
    public void loadMatchMakerFragment(Bundle bundle) {
        isRewardVideoShown = false;
        setTitle("Match : " + bundle.getString(ProgrammingBuddyConstants.KEY_PROG_TITLE, ""));
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        matchMakerFragment = new NewMatchFragment();
        matchMakerFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, matchMakerFragment, NewMatchFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadTestFragment(Bundle bundle) {
        isRewardVideoShown = false;
        setTitle("Test : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        testDragNDropFragment = (TestDragNDropFragment) getSupportFragmentManager().findFragmentByTag(TestDragNDropFragment.class.getSimpleName());
        if( testDragNDropFragment == null ) {
            testDragNDropFragment = new TestDragNDropFragment();
        }
        testDragNDropFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, testDragNDropFragment, TestDragNDropFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadQuizFragment(Bundle bundle) {
        isRewardVideoShown = false;
        setTitle("Quiz : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        quizFragment = (QuizFragment) getSupportFragmentManager().findFragmentByTag(QuizFragment.class.getSimpleName());
        if( quizFragment == null ) {
            quizFragment = new QuizFragment();
        }
        quizFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, quizFragment, QuizFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadFillBlanksFragment(Bundle bundle) {
        isRewardVideoShown = false;
        setTitle("Fill blanks : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        fillBlankFragment = (NewFillBlankFragment) getSupportFragmentManager().findFragmentByTag(NewFillBlankFragment.class.getSimpleName());
        if( fillBlankFragment == null ) {
            fillBlankFragment = new NewFillBlankFragment();
        }
        fillBlankFragment.setBundle(bundle);
        fillBlankFragment.setmProgram_Index(((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_index());
        fillBlankFragment.setWizardMode( true );
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, fillBlankFragment, NewFillBlankFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }



    @Override
    public void onBackPressed() {
        String title = getTitle().toString();
        if( title.startsWith("Match") ) {
            if( matchMakerFragment != null ) {
                matchMakerFragment.onBackPressed();
            }
        }
        else if( title.startsWith("Test") ) {
            if( testDragNDropFragment != null ) {
                testDragNDropFragment.onBackPressed();
            }
        }
        else if( title.startsWith("Quiz") ) {
            if( quizFragment != null ) {
                quizFragment.onBackPressed();
            }
        }
        else if( title.startsWith("Fill") ) {
            if( fillBlankFragment != null ) {
                fillBlankFragment.onBackPressed();
            }
        }
        else super.onBackPressed();

    }

}
