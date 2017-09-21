package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.TopicDetailsAdapter;
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter;
import com.sortedqueue.programmercreek.asynctask.TopicDetailsTask;
import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.database.TopicDetails;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.ParallaxPageTransformer;
import com.sortedqueue.programmercreek.view.OneDirectionalScrollableViewPager;

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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
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
    private Unbinder unbinder;
    private ArrayList<TopicDetails> mLessons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolBar();
        CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
        new TopicDetailsTask(getContext(), "", this).execute();
    }

    private void setupToolBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Program Wiki : " + CreekApplication.getCreekPreferences().getProgramLanguage().toUpperCase());
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
        TopicDetails lesson = mLessons.get(position);
        toolbar.setTitle(lesson.getTopicDescription());
        ArrayList<Fragment> fragments = new ArrayList<>();
        for( SubTopics subTopics : lesson.getSubTopicsArrayList() ) {
            SubTopicFragment subTopicFragment = new SubTopicFragment();
            subTopicFragment.setSubTopics(subTopics);
            subTopicFragment.setNavigationListener(new BitModuleNavigationListener() {
                @Override
                public void onMoveForward() {

                }

                @Override
                public void onMoveBackward() {

                }

                @Override
                public void onTestTriggered(String testType) {

                }
            });
            fragments.add(subTopicFragment);
        }
        ((SubTopicFragment) (fragments.get(0))).setLastFirstIndicator(0);
        ((SubTopicFragment) (fragments.get(fragments.size() - 1))).setLastFirstIndicator(1);
        TutorialSlidesPagerAdapter adapter = new TutorialSlidesPagerAdapter(getChildFragmentManager(), fragments);
        topicDetailsViewPager.setAdapter(adapter);
        topicDetailsViewPager.setPageTransformer(true, new ParallaxPageTransformer());
        topicDetailsViewPager.setOffscreenPageLimit(3);
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onSuccess(ArrayList<TopicDetails> topicDetails) {
        mLessons = topicDetails;
        CommonUtils.dismissProgressDialog();
        if( topicDetails != null && topicDetails.size() > 0 )
            initLessons(0);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        topicsRecyclerView.setAdapter(new TopicDetailsAdapter( topicDetails, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {

            @Override
            public void onItemClick(int position) {
                initLessons(position);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }));
    }
}
