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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/05/17.
 */

public class TutorialCarousalActivity extends AppCompatActivity implements TutorialNavigationListener {


    @BindView(R.id.tutorialViewPager)
    ViewPager tutorialViewPager;

    private ArrayList<TutorialModel> tutorialModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_carousal);
        ButterKnife.bind(this);

        tutorialModels = new ArrayList<>();
        TutorialModel tutorialModel = new TutorialModel("Add code and gain reputation 15xp for every code added\n\nPractice other users programs and gain 30xp",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/code_import%2Freputation_15.png?alt=media&token=9b3e0d32-5a0c-4788-83cb-28f4b02ca48d");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Simply copy code from your browser, click SHARE",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare1.png?alt=media&token=de08f979-5df0-4c50-bdf9-c5cf987da4b6");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Choose Code share option",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare2.png?alt=media&token=c6f1375d-c76f-4cf0-8630-c93815280190");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Enter program name and set language",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare3.png?alt=media&token=71d178ae-3157-4900-a74c-beae3eb13318");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Preview your code",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare4.png?alt=media&token=ad6e84fd-b9b7-43a5-bc2a-d1acf5cab1de");
        tutorialModels.add(tutorialModel);
        tutorialModel = new TutorialModel("Click save to use program later, Gain reputaion : 25xp",
                "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/share_code%2Fshare5.png?alt=media&token=9a673c21-9c33-4384-b5c5-e61cea305527");
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
        /*Intent intent = new Intent();
        setResult(RESULT_OK, intent);*/
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
