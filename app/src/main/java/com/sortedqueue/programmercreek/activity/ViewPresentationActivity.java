package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 11/04/17.
 */

public class ViewPresentationActivity extends AppCompatActivity implements FirebaseDatabaseHandler.GetAllSlidesListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager pager;
    private String TAG = ViewPresentationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presentation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PresentationModel presentationModel = getIntent().getExtras().getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID);
        if( presentationModel != null ) {
            fetchSlides(presentationModel);
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void fetchSlides(PresentationModel presentationModel) {
        CommonUtils.displayProgressDialog(ViewPresentationActivity.this, "Loading");
        onSuccess(presentationModel.getSlideModelArrayList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_presentation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if( item.getItemId() == R.id.action_finish ) {
            saveAndExit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onSuccess(ArrayList<SlideModel> slideModelArrayList) {
        CommonUtils.dismissProgressDialog();
        Log.d(TAG, "Slides size : " + slideModelArrayList.size());
        pager.setAdapter(new ViewSlidesPagerAdapter(getSupportFragmentManager(), slideModelArrayList));
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setOffscreenPageLimit(slideModelArrayList.size());
    }

    @Override
    public void onFailure(DatabaseError databaseError) {
        CommonUtils.dismissProgressDialog();
    }
}
