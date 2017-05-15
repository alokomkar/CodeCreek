package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.TutorialSlidesPagerAdapter;
import com.sortedqueue.programmercreek.database.TutorialModel;
import com.sortedqueue.programmercreek.fragments.TutorialModelFragment;
import com.sortedqueue.programmercreek.interfaces.TutorialNavigationListener;
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/05/17.
 */

public class TutorialCarousalActivity extends AppCompatActivity implements TutorialNavigationListener {


    @Bind(R.id.tutorialViewPager)
    ViewPager tutorialViewPager;

    private ArrayList<TutorialModel> tutorialModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_carousal);
        ButterKnife.bind(this);

        tutorialModels = new ArrayList<>();
        TutorialModel tutorialModel = new TutorialModel("Download the file on your mobile.", "");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Copy the file on your system.", "");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Modify file template by replacing it with your content", "");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Copy file back to your mobile", "");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Preview and save your program for your use later", "");
        tutorialModels.add(tutorialModel);

        int index = 1;
        ArrayList<TutorialModelFragment> fragmentArrayList = new ArrayList<>();
        for( TutorialModel model : tutorialModels ) {
            TutorialModelFragment tutorialModelFragment = new TutorialModelFragment();
            tutorialModelFragment.setParameter( model, index++, tutorialModels.size() );
            fragmentArrayList.add(tutorialModelFragment);
        }

        tutorialViewPager.setAdapter(new TutorialSlidesPagerAdapter(getSupportFragmentManager(), fragmentArrayList));
        tutorialViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        tutorialViewPager.setOffscreenPageLimit(tutorialModels.size());

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onNextClick() {
        tutorialViewPager.setCurrentItem(tutorialViewPager.getCurrentItem() + 1);
    }

    @Override
    public void onPreviousClick() {
        tutorialViewPager.setCurrentItem(tutorialViewPager.getCurrentItem() - 1);
    }

    @Override
    public void onCancelClick() {
        onBackPressed();
    }
}
