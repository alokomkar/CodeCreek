package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter;
import com.sortedqueue.programmercreek.database.lessons.BitModule;
import com.sortedqueue.programmercreek.database.lessons.Lesson;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 29/08/17.
 */

public class LessonDetailsFragment extends Fragment implements BitModuleNavigationListener{

    @BindView(R.id.lessonDetailsViewPager)
    ViewPager lessonDetailsViewPager;
    Unbinder unbinder;
    private Lesson lesson;

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lesson_details, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommonUtils.displayProgressDialog(getContext(), getString(R.string.loading));
        ArrayList<Fragment> fragments = new ArrayList<>();
        for( BitModule bitModule : lesson.getBitModules() ) {
            BitModuleFragment bitModuleFragment = new BitModuleFragment();
            bitModuleFragment.setBitModule(bitModule);
            bitModuleFragment.setNavigationListener(this);
            fragments.add(bitModuleFragment);
        }
        ((BitModuleFragment) (fragments.get(0))).setLastFirstIndicator(0);
        ((BitModuleFragment) (fragments.get(fragments.size() - 1))).setLastFirstIndicator(1);
        lessonDetailsViewPager.setAdapter(new TutorialSlidesPagerAdapter(getChildFragmentManager(), fragments));
        lessonDetailsViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        lessonDetailsViewPager.setOffscreenPageLimit(fragments.size());
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMoveForward() {
        lessonDetailsViewPager.setCurrentItem(lessonDetailsViewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void onMoveBackward() {
        lessonDetailsViewPager.setCurrentItem(lessonDetailsViewPager.getCurrentItem() - 1, true);
    }

    @Override
    public void onTestTriggered(String testType) {

    }
}
