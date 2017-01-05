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
import com.sortedqueue.programmercreek.adapter.WizardModulePagerAdapter;
import com.sortedqueue.programmercreek.database.WizardModule;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 05/01/17.
 */

public class WizardModuleFragment extends Fragment {

    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar progressBar;
    @Bind(R.id.syntaxLearnViewPager)
    ScrollableViewPager syntaxLearnViewPager;
    @Bind(R.id.viewPagerLayout)
    LinearLayout viewPagerLayout;
    @Bind(R.id.doneFAB)
    FloatingActionButton doneFAB;

    private WizardModule wizardModule;
    private WizardModulePagerAdapter wizardModulePagerAdapter;

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

    public void setWizardModule(WizardModule wizardModule) {
        this.wizardModule = wizardModule;
    }

    private void setupViews() {

        syntaxLearnViewPager.setOffscreenPageLimit(wizardModule.getWizardModules().size());
        wizardModulePagerAdapter = new WizardModulePagerAdapter(getContext(), getChildFragmentManager(), wizardModule.getWizardModules());
        syntaxLearnViewPager.setAdapter(wizardModulePagerAdapter);
        syntaxLearnViewPager.setCanScroll(false);
        syntaxLearnViewPager.setAllowedSwipeDirection(ScrollableViewPager.SwipeDirection.left);
        progressBar.setMax(wizardModule.getWizardModules().size());
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void toggleFabDrawable(final int progress) {
        int drawable = progress == progressBar.getMax() ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
        doneFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
    }

    public void onScrollForward() {
        if (syntaxLearnViewPager.getCurrentItem() + 1 == wizardModule.getWizardModules().size()) {
            getActivity().onBackPressed();
        } else {
            syntaxLearnViewPager.setCurrentItem(syntaxLearnViewPager.getCurrentItem() + 1);
        }

    }
}
