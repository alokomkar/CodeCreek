package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ChapterDetailsPagerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager;
import com.sortedqueue.programmercreek.view.SwipeDirection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 05/01/17.
 */

public class ChapterDetailsFragment extends Fragment implements WikiNavigationListner, ModuleDetailsScrollPageListener {

    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar progressBar;
    @Bind(R.id.syntaxLearnViewPager)
    OneDirectionalScrollableViewPager syntaxLearnViewPager;
    @Bind(R.id.viewPagerLayout)
    LinearLayout viewPagerLayout;
    @Bind(R.id.doneFAB)
    FloatingActionButton doneFAB;

    private Chapter chapter;
    private ChapterDetailsPagerAdapter chapterDetailsPagerAdapter;
    private CreekUserStats creekUserStats;
    private Chapter nextChapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wizard_module, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    private void setupViews() {

        syntaxLearnViewPager.setOffscreenPageLimit(3);
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
        toggleFabDrawable( progressBar.getProgress() );
        changeViewPagerBehavior(1);
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

    }

    private void changeViewPagerBehavior(int position) {
        int chapterProgress = getChapterProgress();
        Fragment fragment = chapterDetailsPagerAdapter.getItem(position);
        ChapterDetails chapterDetails = chapter.getChapterDetailsArrayList().get(position);
        if( fragment instanceof ProgramWikiFragment ) {
            syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.none);
        }
        else {
            if( chapterProgress != 0 && chapterProgress > chapterDetails.getProgressIndex() ) {
                syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.all);
            }
            else {
                TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
                if( testCompletionListener.isTestComplete() != -1 ) {
                    syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.all);
                }
                else {
                    syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.left);
                }
            }

        }
    }

    private int getChapterProgress() {
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        int chapterProgress = 0;
        switch ( chapter.getProgram_Language() ) {
            case "c" :
                chapterProgress = creekUserStats.getcProgressIndex();
                break;
            case "cpp" :
            case "c++" :
                chapterProgress = creekUserStats.getCppProgressIndex();
                break;
            case "java" :
                chapterProgress = creekUserStats.getJavaProgressIndex();
                break;
        }
        return chapterProgress;
    }

    private void toggleFabDrawable(final int progress) {
        int drawable = progress == progressBar.getMax() ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
        doneFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
    }

    @Override
    public void onScrollForward() {
        //Add validations here : if answer is complete - track and allow scrolling
        //if chapter is already complete, don't restrict movement
        int chapterProgress = getChapterProgress();
        Fragment fragment = chapterDetailsPagerAdapter.getItem(syntaxLearnViewPager.getCurrentItem());
        ChapterDetails chapterDetails = chapter.getChapterDetailsArrayList().get(syntaxLearnViewPager.getCurrentItem());
        if( chapterProgress != 0 && chapterProgress > chapterDetails.getProgressIndex() ) {
            fabAction();
        }
        else {
            if( fragment instanceof TestCompletionListener ) {
                TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
                switch ( testCompletionListener.isTestComplete() ) {
                    case ProgrammingBuddyConstants.KEY_MATCH :
                    case ProgrammingBuddyConstants.KEY_TEST : //Has same index as wiki - no changes for wiki
                    case ProgrammingBuddyConstants.KEY_QUIZ :
                    case ProgrammingBuddyConstants.KEY_FILL_BLANKS:
                    case ChapterDetails.TYPE_SYNTAX_MODULE :
                        fabAction();
                        updateCreekStats();
                        break;
                    case -1 :
                        CommonUtils.displaySnackBar(getActivity(), "Complete the test to proceed");
                        break;
                }
            }
        }
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
        switch ( new CreekPreferences(getContext()).getProgramLanguage() ) {
            case "c" :
                if( creekUserStats.getcProgressIndex() < chapterDetails.getProgressIndex() ) {
                    creekUserStats.setcProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            creekUserStats.addToUnlockedCLanguageModuleIdList( chapterDetails.getSyntaxId() );
                            creekUserStats.addToUnlockedCSyntaxModuleIdList( chapterDetails.getSyntaxId() +"_"+ chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            creekUserStats.addToUnlockedCProgramIndexList( Integer.parseInt(chapterDetails.getChapterReferenceId()) );
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedCWikiIdList( chapterDetails.getChapterReferenceId() );
                            break;
                    }
                }
                break;
            case "c++" :
            case "cpp" :
                if( creekUserStats.getCppProgressIndex() < chapterDetails.getProgressIndex() ) {
                    creekUserStats.setCppProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            creekUserStats.addToUnlockedCppLanguageModuleIdList( chapterDetails.getSyntaxId() );
                            creekUserStats.addToUnlockedCppSyntaxModuleIdList( chapterDetails.getSyntaxId() +"_"+ chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            creekUserStats.addToUnlockedCppProgramIndexList( Integer.parseInt(chapterDetails.getChapterReferenceId()) );
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedCppWikiIdList( chapterDetails.getChapterReferenceId() );
                            break;
                    }
                }
                break;
            case "java" :
                if( creekUserStats.getJavaProgressIndex() < chapterDetails.getProgressIndex() ) {
                    creekUserStats.setJavaProgressIndex(chapterDetails.getProgressIndex());
                    switch (chapterDetails.getChapterType()) {
                        case ChapterDetails.TYPE_SYNTAX_MODULE:
                            creekUserStats.addToUnlockedJavaLanguageModuleIdList( chapterDetails.getSyntaxId() );
                            creekUserStats.addToUnlockedJavaSyntaxModuleIdList( chapterDetails.getSyntaxId() +"_"+ chapterDetails.getChapterReferenceId());
                            break;
                        case ChapterDetails.TYPE_PROGRAM_INDEX:
                            creekUserStats.addToUnlockedJavaProgramIndexList( Integer.parseInt(chapterDetails.getChapterReferenceId()) );
                            break;
                        case ChapterDetails.TYPE_WIKI:
                            creekUserStats.addToUnlockedJavaWikiIdList( chapterDetails.getChapterReferenceId() );
                            break;
                    }
                }
                break;
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
    }

    @Override
    public void onBackPressed() {
        syntaxLearnViewPager.setCurrentItem( syntaxLearnViewPager.getCurrentItem() - 1);
    }

    public void setNextChapter(Chapter nextChapter) {
        this.nextChapter = nextChapter;
    }
}
