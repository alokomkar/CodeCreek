package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.LessonsAdapter;
import com.sortedqueue.programmercreek.asynctask.LessonFetchTask;

import com.sortedqueue.programmercreek.database.lessons.Lesson;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.interfaces.LessonNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 29/08/17.
 */

public class LessonsFragment extends Fragment implements LessonFetchTask.LessonFetcherTaskListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @BindView(R.id.modulesRecyclerView)
    RecyclerView lessonsRecyclerView;
    @BindView(R.id.adView)
    AdView adView;

    private String TAG = LessonsFragment.class.getSimpleName();
    private ArrayList<Lesson> lessons;
    private LessonsAdapter lessonsAdapter;
    private LessonNavigationListener lessonNavigationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);
        ButterKnife.bind(this, view);
        getModules();
        initAds();
        return view;
    }

    private void initAds() {
        /*if( CreekApplication.getCreekPreferences().getAdsEnabled() )*/ {
            MobileAds.initialize(getContext(), getString(R.string.mobile_banner_id));
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.GONE);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LessonNavigationListener) {
            lessonNavigationListener = (LessonNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        lessonNavigationListener = null;
        super.onDetach();
    }

    private void getModules() {
        lessonsRecyclerView.setVisibility(View.INVISIBLE);
        CommonUtils.displayProgressDialog(getContext(), "Loading chapters");
        new LessonFetchTask(this).execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        if( lessonsAdapter != null ) {
            lessonsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
        this.lessonsAdapter = new LessonsAdapter(getContext(), lessons, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {

            @Override
            public void onItemClick(int position) {
                lessonNavigationListener.onLessonSelected(lessonsAdapter.getItem(position));
            }
        });
        lessonsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        lessonsRecyclerView.setAdapter(lessonsAdapter);
        CommonUtils.dismissProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.slideInToLeft(lessonsRecyclerView);
            }
        }, 450);
    }
}
