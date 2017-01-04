package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.SyntaxPagerAdapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 26/12/16.
 */

public class ModuleDetailsFragment extends Fragment implements ModuleDetailsScrollPageListener{

    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar progressBar;
    @Bind(R.id.syntaxLearnViewPager)
    ScrollableViewPager syntaxLearnViewPager;
    @Bind(R.id.viewPagerLayout)
    LinearLayout viewPagerLayout;
    private LanguageModule module;
    private ArrayList<SyntaxModule> syntaxModules;
    private SyntaxPagerAdapter syntaxPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module_details, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    private void setupViews() {

        syntaxLearnViewPager.setOffscreenPageLimit(syntaxModules.size());
        syntaxPagerAdapter = new SyntaxPagerAdapter(getChildFragmentManager(), module, syntaxModules, this);
        syntaxLearnViewPager.setAdapter(syntaxPagerAdapter);
        syntaxLearnViewPager.setCanScroll(false);
        progressBar.setMax(syntaxModules.size());
        progressBar.setProgress(1);
        syntaxLearnViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressBar.setProgress(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setParameters(LanguageModule module, ArrayList<SyntaxModule> syntaxModules) {
        this.module = module;
        this.syntaxModules = syntaxModules;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onScrollForward() {
        if( syntaxLearnViewPager.getCurrentItem() + 1 == syntaxPagerAdapter.getCount() ) {
            getActivity().onBackPressed();
        }
        else {
            syntaxLearnViewPager.setCurrentItem(syntaxLearnViewPager.getCurrentItem() + 1);
        }

    }
}
