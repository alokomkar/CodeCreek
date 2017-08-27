package com.sortedqueue.programmercreek.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.WizardActivity;
import com.sortedqueue.programmercreek.adapter.ChapterDetailsPagerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager;
import com.sortedqueue.programmercreek.view.SwipeDirection;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 05/01/17.
 */

public class ChapterDetailsFragment extends Fragment implements WikiNavigationListner, ModuleDetailsScrollPageListener {

    @BindView(R.id.ProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.syntaxLearnViewPager)
    OneDirectionalScrollableViewPager syntaxLearnViewPager;
    @BindView(R.id.viewPagerLayout)
    LinearLayout viewPagerLayout;
    @BindView(R.id.doneFAB)
    FloatingActionButton doneFAB;

    private Chapter chapter;
    private ChapterDetailsPagerAdapter chapterDetailsPagerAdapter;
    private CreekUserStats creekUserStats;
    private Chapter nextChapter;
    private ChapterNavigationListener onChapterNavigationListener;
    private int fabDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wizard_module, container, false);
        ButterKnife.bind(this, view);
        CommonUtils.displayProgressDialog(getContext(), "Loading modules");
        setupViews();
        return view;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    private void setupViews() {

        //syntaxLearnViewPager.setOffscreenPageLimit(chapter.getChapterDetailsArrayList().size());
        chapterDetailsPagerAdapter = new ChapterDetailsPagerAdapter(getContext(), this, getChildFragmentManager(), chapter.getChapterDetailsArrayList(), this, nextChapter);
        syntaxLearnViewPager.setAdapter(chapterDetailsPagerAdapter);
        syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.left);
        progressBar.setMax(chapter.getChapterDetailsArrayList().size());
        progressBar.setProgress(1);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScrollForward();
            }
        });
        toggleFabDrawable(progressBar.getProgress());
        changeViewPagerBehavior(0);
        syntaxLearnViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressBar.setProgress(position + 1);
                toggleFabDrawable(progressBar.getProgress());
                changeViewPagerBehavior(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int chapterProgress = getChapterProgress();
        int currentIndex = 0;
        if( chapterProgress != 0 ) {
            for( ChapterDetails chapterDetails : chapter.getChapterDetailsArrayList() ) {
                if( chapterProgress > chapterDetails.getProgressIndex() ) {
                    currentIndex++;
                }
            }
        }
        syntaxLearnViewPager.setCurrentItem(currentIndex);
        CommonUtils.dismissProgressDialog();

    }

    private void changeViewPagerBehavior(int position) {
        int chapterProgress = getChapterProgress();
        Fragment fragment = chapterDetailsPagerAdapter.getItem(position);
        ChapterDetails chapterDetails = chapter.getChapterDetailsArrayList().get(position);
        if (fragment instanceof ProgramWikiFragment) {
            syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.none);
        }
        else if( fragment instanceof TestCompletionListener ) {
            TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
            syntaxLearnViewPager.setAllowedSwipeDirection(
                    testCompletionListener.isTestComplete() != -1 ?
                            SwipeDirection.all :
                            SwipeDirection.none);
        }
        else {
            if (chapterProgress != 0 && chapterProgress > chapterDetails.getProgressIndex()) {
                syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.all);
            } else {
                TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
                syntaxLearnViewPager.setAllowedSwipeDirection(
                        testCompletionListener.isTestComplete() != -1 ?
                                SwipeDirection.all :
                                SwipeDirection.none);
            }

        }
    }

    private int getChapterProgress() {
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        int chapterProgress = 0;
        switch (chapter.getProgram_Language()) {
            case "c":
                chapterProgress = creekUserStats.getcProgressIndex();
                break;
            case "cpp":
            case "c++":
                chapterProgress = creekUserStats.getCppProgressIndex();
                break;
            case "java":
                chapterProgress = creekUserStats.getJavaProgressIndex();
                break;
            case "sql":
                chapterProgress = creekUserStats.getSqlProgressIndex();
                break;
        }
        return chapterProgress;
    }

    private void toggleFabDrawable(final int progress) {
        Fragment fragment = chapterDetailsPagerAdapter.getItem(progress - 1 );
        fabDrawable = progress == progressBar.getMax() ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
        if( fragment instanceof NewMatchFragment ) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp;
        }
        else if( fragment instanceof NewFillBlankFragment ) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp;
        }
        else if( fragment instanceof TestDragNDropFragment ) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp;
        }
        else if( fragment instanceof QuizFragment ) {
            fabDrawable = R.drawable.ic_help_outline_white_24dp;
        }
        if( fragment instanceof TestCompletionListener ) {
            TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
            if( testCompletionListener.isTestComplete() != -1 ) {
                fabDrawable = progress == progressBar.getMax() ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
            }
        }
        doneFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), fabDrawable));
        onChapterNavigationListener.toggleFabDrawable(fabDrawable);
    }

    @Override
    public void onScrollForward() {
        if( fabDrawable == R.drawable.ic_help_outline_white_24dp ) {
            showRewardedVideoDialog();
        }
        else {
            //Add validations here : if answer is complete - track and allow scrolling
            //if chapter is already complete, don't restrict movement
            int chapterProgress = getChapterProgress();
            Fragment fragment = chapterDetailsPagerAdapter.getItem(syntaxLearnViewPager.getCurrentItem());
            ChapterDetails chapterDetails = chapter.getChapterDetailsArrayList().get(syntaxLearnViewPager.getCurrentItem());
            if (chapterProgress != 0 && chapterProgress > chapterDetails.getProgressIndex()) {
                fabAction();
            } else {
                if (fragment instanceof TestCompletionListener) {
                    TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
                    switch (testCompletionListener.isTestComplete()) {
                        case ProgrammingBuddyConstants.KEY_MATCH:
                        case ProgrammingBuddyConstants.KEY_TEST: //Has same index as wiki - no changes for wiki
                        case ProgrammingBuddyConstants.KEY_QUIZ:
                        case ProgrammingBuddyConstants.KEY_FILL_BLANKS:
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            fabAction();
                            updateCreekStats();
                            break;
                        case -1:
                            CommonUtils.displaySnackBar(getActivity(), "Complete the test to proceed");
                            break;
                    }
                }
            }
        }

    }

    @Override
    public void toggleFABDrawable() {
        toggleFabDrawable(progressBar.getProgress());
    }

    private void fabAction() {

        if (syntaxLearnViewPager.getCurrentItem() + 1 == chapter.getChapterDetailsArrayList().size()) {
            getActivity().onBackPressed();
        } else {
            syntaxLearnViewPager.setCurrentItem(syntaxLearnViewPager.getCurrentItem() + 1);
        }
    }

    private void updateCreekStats() {
        ChapterDetails chapterDetails = chapterDetailsPagerAdapter.getChapterDetailsForPosition(syntaxLearnViewPager.getCurrentItem());
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch (CreekApplication.getCreekPreferences().getProgramLanguage()) {
            case "c":
                if (creekUserStats.getcProgressIndex() < chapterDetails.getProgressIndex()) {
                    creekUserStats.setcProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            creekUserStats.addToUnlockedCLanguageModuleIdList(chapterDetails.getSyntaxId());
                            creekUserStats.addToUnlockedCSyntaxModuleIdList(chapterDetails.getSyntaxId() + "_" + chapterDetails.getChapterReferenceId());
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE);
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
                            creekUserStats.addToUnlockedCProgramIndexList(Integer.parseInt(chapterDetails.getChapterReferenceId()));
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedCWikiIdList(chapterDetails.getChapterReferenceId());
                            break;
                    }
                }
                break;
            case "c++":
            case "cpp":
                if (creekUserStats.getCppProgressIndex() < chapterDetails.getProgressIndex()) {
                    creekUserStats.setCppProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE);
                            creekUserStats.addToUnlockedCppLanguageModuleIdList(chapterDetails.getSyntaxId());
                            creekUserStats.addToUnlockedCppSyntaxModuleIdList(chapterDetails.getSyntaxId() + "_" + chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
                            creekUserStats.addToUnlockedCppProgramIndexList(Integer.parseInt(chapterDetails.getChapterReferenceId()));
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedCppWikiIdList(chapterDetails.getChapterReferenceId());
                            break;
                    }
                }
                break;
            case "java":
                if (creekUserStats.getJavaProgressIndex() < chapterDetails.getProgressIndex()) {
                    creekUserStats.setJavaProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE);
                            creekUserStats.addToUnlockedJavaLanguageModuleIdList(chapterDetails.getSyntaxId());
                            creekUserStats.addToUnlockedJavaSyntaxModuleIdList(chapterDetails.getSyntaxId() + "_" + chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
                            creekUserStats.addToUnlockedJavaProgramIndexList(Integer.parseInt(chapterDetails.getChapterReferenceId()));
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedJavaWikiIdList(chapterDetails.getChapterReferenceId());
                            break;
                    }
                }
                break;
            case "sql":
                if (creekUserStats.getSqlProgressIndex() < chapterDetails.getProgressIndex()) {
                    creekUserStats.setSqlProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.MODULE_SCORE);
                            creekUserStats.addToUnlockedSqlLanguageModuleIdList(chapterDetails.getSyntaxId());
                            creekUserStats.addToUnlockedSqlSyntaxModuleIdList(chapterDetails.getSyntaxId() + "_" + chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            onChapterNavigationListener.onProgessStatsUpdate(CreekUserStats.PROGRAM_SCORE);
                            creekUserStats.addToUnlockedSqlProgramIndexList(Integer.parseInt(chapterDetails.getChapterReferenceId()));
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedSqlWikiIdList(chapterDetails.getChapterReferenceId());
                            break;
                    }
                }
                break;
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
    }

    @Override
    public void onBackPressed() {
        syntaxLearnViewPager.setCurrentItem(syntaxLearnViewPager.getCurrentItem() - 1);
    }

    @Override
    public void disableViewPager() {
        if (syntaxLearnViewPager != null) {
            syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.none);
            doneFAB.setEnabled(false);
        }
    }

    @Override
    public void enableViewPager() {
        if (syntaxLearnViewPager != null) {
            changeViewPagerBehavior(progressBar.getProgress() - 1);
            doneFAB.setEnabled(true);
        }

    }

    public void setNextChapter(Chapter nextChapter) {
        this.nextChapter = nextChapter;
    }

    public void setOnChapterNavigationListener(ChapterNavigationListener onChapterNavigationListener) {
        this.onChapterNavigationListener = onChapterNavigationListener;
    }

    public void showRewardedVideoDialog() {
        AuxilaryUtils.displayInformation(getContext(), R.string.hint_video, R.string.reward_video_description,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showRewardedClick();
                    }
                },

                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
    }

    private void showRewardedClick() {
        final StartAppAd rewardedVideo = new StartAppAd(getContext());

        /**
         * This is very important: set the video listener to be triggered after video
         * has finished playing completely
         */
        rewardedVideo.setVideoListener(new VideoListener() {

            @Override
            public void onVideoCompleted() {
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
        Fragment fragment = chapterDetailsPagerAdapter.getItem(progressBar.getProgress() - 1);
        if( fragment instanceof NewMatchFragment ) {
            showSolutionDialog(((NewMatchFragment)(fragment)).getmProgramList());
        }
        else if( fragment instanceof NewFillBlankFragment ) {
            ((NewFillBlankFragment)(fragment)).addHintsToBlanks();
        }
        else if( fragment instanceof TestDragNDropFragment ) {
            showSolutionDialog(((TestDragNDropFragment)(fragment)).getmProgramList());
        }
        else if( fragment instanceof QuizFragment ) {
            showSolutionDialog(((QuizFragment)(fragment)).getmProgramList());
        }
    }

    private void showSolutionDialog( ArrayList<String> solutionList ) {
        String solution = "";
        for( String string : solutionList ) {
            solution += string + "\n";
        }
        Log.d("SolutionProgram", solution);
        AuxilaryUtils.displayAlert("Solution", solution, getContext());
    }
}
