package com.sortedqueue.programmercreek.activity;

import android.content.Intent;
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
        TutorialModel tutorialModel = new TutorialModel("Add code and gain reputation 15xp for every code added\n\n 5xp for every like you get",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2Freputation_15.png?alt=media&token=9b3e0d32-5a0c-4788-83cb-28f4b02ca48d");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Download the file on your mobile.",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2FScreenshot_20170518-110150.png?alt=media&token=9985119a-a8b3-49a5-8f3c-6f14435fa2d7");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Copy the file on your system via USB cable\n\nEnsure your mobile is in storage mode to access the file",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2FScreenshot_20170518-110231.png?alt=media&token=7d1ea09f-56c1-4492-bfa9-401aeb400d6a");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Modify file template by replacing it with your content\n\nYou can do this on your system, replace the relevant sections with your code, explanation",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2FScreenshot_20170518-110423.png?alt=media&token=3d516f92-c9b9-4e65-af64-b0b76774d16e");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Copy file back to your mobile\n\nOnce again ensure your mobile is in storage mode to access the file",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2FScreenshot_20170518-110231.png?alt=media&token=7d1ea09f-56c1-4492-bfa9-401aeb400d6a");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Preview and save your program for your use later",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2FScreenshot_20170518-110507.png?alt=media&token=ebd9fabd-3106-489f-ac86-b521b9c8e941");
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
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
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
