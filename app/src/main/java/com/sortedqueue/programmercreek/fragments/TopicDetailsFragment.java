package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.TopicDetailsAdapter;
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter;
import com.sortedqueue.programmercreek.asynctask.TopicDetailsTask;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.database.TopicDetails;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.util.ParallaxPageTransformer;
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager;
import com.sortedqueue.programmercreek.view.SwipeDirection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 15/09/17.
 */

public class TopicDetailsFragment extends Fragment implements TopicDetailsTask.TopicDetailsListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.topicDetailsViewPager)
    OneDirectionalScrollableViewPager topicDetailsViewPager;
    @BindView(R.id.content_intro)
    RelativeLayout contentIntro;
    @BindView(R.id.topicsRecyclerView)
    RecyclerView topicsRecyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.reputationProgressBar)
    ProgressBar reputationProgressBar;
    @BindView(R.id.reputationTextView)
    TextView reputationTextView;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;
    private Unbinder unbinder;
    private ArrayList<TopicDetails> mLessons;
    private NewIntroNavigationListener mNewIntroNavigationListener;
    private TopicDetailsAdapter topicDetailsAdapter;
    private TutorialSlidesPagerAdapter adapter;
    private TopicDetails lesson;
    private CreekPreferences creekPreferences;
    private int currentTopicPosition = 0;
    private Handler handler;
    private CreekUserStats creekUserStats;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        creekPreferences = CreekApplication.Companion.getCreekPreferences();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolBar();
        CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
        new TopicDetailsTask(getContext(), "", this).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewIntroNavigationListener)
            mNewIntroNavigationListener = (NewIntroNavigationListener) context;
    }

    private void setupToolBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Program Wiki : " + CreekApplication.Companion.getCreekPreferences().getProgramLanguage().toUpperCase());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                appCompatActivity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initLessons(int position) {
        lesson = mLessons.get(position);
        toolbar.setTitle(lesson.getTopicDescription());
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (SubTopics subTopics : lesson.getSubTopicsArrayList()) {
            SubTopicFragment subTopicFragment = new SubTopicFragment();
            subTopicFragment.setSubTopics(subTopics);
            subTopicFragment.setIntroNavigationListener(mNewIntroNavigationListener);
            subTopicFragment.setNavigationListener(new BitModuleNavigationListener() {
                @Override
                public void onMoveForward() {

                }

                @Override
                public void onMoveBackward() {

                }

                @Override
                public void onTestTriggered(String testType) {
                    topicDetailsViewPager.setAllowedSwipeDirection(testType != null ? SwipeDirection.none : SwipeDirection.all);
                    changeScrollBehavior(currentTopicPosition);
                }

                @Override
                public void showLevelUpDialog(int reputation) {
                    animateProgress(reputation);
                }
            });
            fragments.add(subTopicFragment);
        }
        ((SubTopicFragment) (fragments.get(0))).setLastFirstIndicator(0);
        ((SubTopicFragment) (fragments.get(fragments.size() - 1))).setLastFirstIndicator(1);
        adapter = new TutorialSlidesPagerAdapter(getChildFragmentManager(), fragments);
        topicDetailsViewPager.setAdapter(adapter);
        topicDetailsViewPager.setPageTransformer(true, new ParallaxPageTransformer());
        topicDetailsViewPager.setOffscreenPageLimit(3);
        progressBar.setProgress(1);
        progressBar.setMax(fragments.size());
        topicDetailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTopicPosition = position;
                changeScrollBehavior(position);
                progressBar.setProgress(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        CommonUtils.dismissProgressDialog();
    }

    private int progressBarStatus;

    public void animateProgress(final int points) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = new Handler();
                }
                if (creekPreferences == null) {
                    creekPreferences = CreekApplication.Companion.getCreekPreferences();
                }
                creekUserStats = creekPreferences.getCreekUserStats();
                if (creekUserStats == null) {
                    reputationProgressBar.setVisibility(View.GONE);
                    reputationTextView.setVisibility(View.GONE);
                    progressLayout.setVisibility(View.GONE);
                    return;
                }
                final int progress = creekUserStats.getCreekUserReputation() % 100;
                progressLayout.setVisibility(View.VISIBLE);
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
                                /*if (level > 0) {
                                    showShareLayout(level);
                                }*/

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

    private void changeScrollBehavior(int position) {

        SubTopics subTopics = lesson.getSubTopicsArrayList().get(position);
        if (subTopics.getTestMode() != null && !subTopics.getTestMode().isEmpty()) {
            topicDetailsViewPager.setAllowedSwipeDirection(
                    creekPreferences.isUnlockedTopic(subTopics.getSubTopicId()) ?
                            SwipeDirection.all :
                            SwipeDirection.left);
        } else {
            topicDetailsViewPager.setAllowedSwipeDirection(SwipeDirection.all);
        }

    }

    @Override
    public void onSuccess(ArrayList<TopicDetails> topicDetails) {
        mLessons = topicDetails;
        CommonUtils.dismissProgressDialog();
        if (topicDetails != null && topicDetails.size() > 0)
            initLessons(0);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        topicDetailsAdapter = new TopicDetailsAdapter(topicDetails, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {

            @Override
            public void onItemClick(int position) {
                initLessons(position);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        topicsRecyclerView.setAdapter(topicDetailsAdapter);
    }

    public boolean hideSubTopicFragment() {
        SubTopicFragment subTopicFragment = (SubTopicFragment) adapter.getItem(topicDetailsViewPager.getCurrentItem());
        if (subTopicFragment != null && subTopicFragment.isTestLoaded()) {
            subTopicFragment.hideSubTopicQuestionFragment();
            return true;
        }
        return false;
    }
}
