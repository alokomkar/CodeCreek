package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.lessons.Lesson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 29/08/17.
 */

public class LessonDetailsFragment extends Fragment {

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
