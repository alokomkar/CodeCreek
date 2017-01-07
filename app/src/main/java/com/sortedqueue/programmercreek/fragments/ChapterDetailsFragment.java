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

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ChapterDetailsPagerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.WikiNavigationListner;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager;
import com.sortedqueue.programmercreek.view.SwipeDirection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 05/01/17.
 */

public class ChapterDetailsFragment extends Fragment implements WikiNavigationListner {

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
        chapterDetailsPagerAdapter = new ChapterDetailsPagerAdapter(getContext(), getChildFragmentManager(), chapter.getWizardModules(), this);
        syntaxLearnViewPager.setAdapter(chapterDetailsPagerAdapter);
        syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.left);
        progressBar.setMax(chapter.getWizardModules().size());
        progressBar.setProgress(1);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScrollForward();
            }
        });
        toggleFabDrawable( progressBar.getProgress() );
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
        Fragment fragment = chapterDetailsPagerAdapter.getItem(position);
        if( fragment instanceof ProgramWikiFragment ) {
            syntaxLearnViewPager.setAllowedSwipeDirection(SwipeDirection.none);
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

    private void toggleFabDrawable(final int progress) {
        int drawable = progress == progressBar.getMax() ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
        doneFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
    }

    public void onScrollForward() {
        //Add validations here : if answer is complete - track and allow scrolling
        Fragment fragment = chapterDetailsPagerAdapter.getItem(syntaxLearnViewPager.getCurrentItem());

        if( fragment instanceof TestCompletionListener ) {
            TestCompletionListener testCompletionListener = (TestCompletionListener) fragment;
            switch ( testCompletionListener.isTestComplete() ) {
                case ProgrammingBuddyConstants.KEY_MATCH :
                case ProgrammingBuddyConstants.KEY_TEST : //Has same index as wiki - no changes for wiki
                case ProgrammingBuddyConstants.KEY_QUIZ :
                case ChapterDetails.TYPE_SYNTAX_MODULE :
                    fabAction();
                    break;
                case -1 :
                    CommonUtils.displaySnackBar(getActivity(), "Complete the test to proceed");
                    break;
            }
        }

    }

    private void fabAction() {
        if (syntaxLearnViewPager.getCurrentItem() + 1 == chapter.getWizardModules().size()) {
            getActivity().onBackPressed();
        } else {
            syntaxLearnViewPager.setCurrentItem(syntaxLearnViewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onBackPressed() {
        syntaxLearnViewPager.setCurrentItem( syntaxLearnViewPager.getCurrentItem() - 1);
    }
}
