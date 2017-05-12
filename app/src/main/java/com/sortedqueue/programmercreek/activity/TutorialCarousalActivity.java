package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ViewSlidesPagerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.PresentationModel;
import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/05/17.
 */

public class TutorialCarousalActivity extends AppCompatActivity implements FirebaseDatabaseHandler.GetAllSlidesListener {


    @Bind(R.id.tutorialViewPager)
    ViewPager tutorialViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_carousal);
        ButterKnife.bind(this);
        PresentationModel presentationModel = getIntent().getExtras().getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID);
        if( presentationModel != null ) {
            fetchSlides(presentationModel);
        }

    }

    private void fetchSlides(PresentationModel presentationModel) {
        CommonUtils.displayProgressDialog(TutorialCarousalActivity.this, "Loading");
        onSuccess(presentationModel.getSlideModelArrayList());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(ArrayList<SlideModel> slideModelArrayList) {
        CommonUtils.dismissProgressDialog();
        tutorialViewPager.setAdapter(new ViewSlidesPagerAdapter(getSupportFragmentManager(), slideModelArrayList));
        tutorialViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        tutorialViewPager.setOffscreenPageLimit(slideModelArrayList.size());
    }

    @Override
    public void onFailure(DatabaseError databaseError) {
        CommonUtils.dismissProgressDialog();
    }
}
