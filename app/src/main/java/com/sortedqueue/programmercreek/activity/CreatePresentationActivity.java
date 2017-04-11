package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ScreenSlidePagerAdapter;
import com.sortedqueue.programmercreek.database.PresentationModel;
import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.SlideFragment;
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/04/17.
 */

public class CreatePresentationActivity extends AppCompatActivity implements View.OnClickListener, PresentationCommunicationsListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.deleteSlideFAB)
    FloatingActionButton deleteSlideFAB;
    @Bind(R.id.addSlideFAB)
    FloatingActionButton addSlideFAB;
    @Bind(R.id.optionsFAB)
    FloatingActionButton optionsFAB;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.deleteSlideTextView)
    TextView deleteSlideTextView;
    @Bind(R.id.addSlideTextView)
    TextView addSlideTextView;
    @Bind(R.id.addCodeTextView)
    TextView addCodeTextView;
    @Bind(R.id.addCodeFAB)
    FloatingActionButton addCodeFAB;
    @Bind(R.id.addPhotoTextView)
    TextView addPhotoTextView;
    @Bind(R.id.addPhotoFAB)
    FloatingActionButton addPhotoFAB;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    private ArrayList<Fragment> fragmentArrayList;
    private int OPTION_CODE = 1;
    private int OPTION_PHOTO = 2;
    private PresentationModel presentationModel;
    private ArrayList<SlideModel> slideModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_presentation);
        ButterKnife.bind(this);
        fragmentArrayList = new ArrayList<>();
        slideModelArrayList = new ArrayList<>();
        initPagerAdapter();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presentationModel = new PresentationModel();
        presentationModel.setPresenterName( new CreekPreferences(CreatePresentationActivity.this).getAccountName() );

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        optionsFAB.setOnClickListener(this);
        addSlideFAB.setOnClickListener(this);
        deleteSlideFAB.setOnClickListener(this);
        deleteSlideTextView.setOnClickListener(this);
        addSlideTextView.setOnClickListener(this);
        addCodeFAB.setOnClickListener(this);
        addPhotoFAB.setOnClickListener(this);
        addPhotoTextView.setOnClickListener(this);
        addCodeTextView.setOnClickListener(this);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void initPagerAdapter() {
        fragmentArrayList.add(new SlideFragment());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        pager.setAdapter(mPagerAdapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.optionsFAB:
                animateFAB();
                break;
            case R.id.addSlideFAB:
            case R.id.addSlideTextView:
                SlideFragment slideFragment = (SlideFragment) mPagerAdapter.getItem(pager.getCurrentItem());
                slideFragment.saveImage();
                mPagerAdapter.addNewSlideFragment(new SlideFragment());
                mPagerAdapter.notifyDataSetChanged();
                pager.setCurrentItem(mPagerAdapter.getCount() - 1);
                pager.setOffscreenPageLimit(mPagerAdapter.getCount());
                break;
            case R.id.deleteSlideFAB:
            case R.id.deleteSlideTextView:
                if (mPagerAdapter.getCount() > 1) {
                    mPagerAdapter.removeCurrentFragment(pager.getCurrentItem());
                    mPagerAdapter.notifyDataSetChanged();
                    pager.setOffscreenPageLimit(mPagerAdapter.getCount());
                } else {
                    CommonUtils.displaySnackBar(CreatePresentationActivity.this, R.string.presentation_needs_one_slide);
                }
                break;
            case R.id.addCodeFAB :
            case R.id.addCodeTextView :
                addToSlide(OPTION_CODE);
                break;
            case R.id.addPhotoTextView:
            case R.id.addPhotoFAB:
                addToSlide(OPTION_PHOTO);
                break;
        }
    }

    private void addToSlide(int option_code) {
        SlideFragment currentFragment = (SlideFragment) mPagerAdapter.getItem(pager.getCurrentItem());
        if( currentFragment != null ) {
            if( option_code == OPTION_CODE ){
                currentFragment.insertCode();
            }
            else {
                currentFragment.insertPhoto();
            }
            optionsFAB.callOnClick();
        }

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
        if( item.getItemId() == R.id.action_finish ) {
            saveAndExit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        onPresentationComplete();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void animateFAB() {
        if (isFabOpen) {

            optionsFAB.startAnimation(rotate_backward);
            addSlideFAB.startAnimation(fab_close);
            deleteSlideFAB.startAnimation(fab_close);
            addSlideTextView.startAnimation(fab_close);
            deleteSlideTextView.startAnimation(fab_close);
            addCodeFAB.startAnimation(fab_close);
            addCodeTextView.startAnimation(fab_close);
            addPhotoFAB.startAnimation(fab_close);
            addPhotoTextView.startAnimation(fab_close);
            addSlideFAB.setClickable(false);
            deleteSlideFAB.setClickable(false);
            isFabOpen = false;


        } else {

            optionsFAB.startAnimation(rotate_forward);
            addSlideFAB.startAnimation(fab_open);
            deleteSlideFAB.startAnimation(fab_open);
            addSlideTextView.startAnimation(fab_open);
            deleteSlideTextView.startAnimation(fab_open);
            addCodeFAB.startAnimation(fab_open);
            addCodeTextView.startAnimation(fab_open);
            addPhotoFAB.startAnimation(fab_open);
            addPhotoTextView.startAnimation(fab_open);
            addSlideFAB.setClickable(true);
            deleteSlideFAB.setClickable(true);
            isFabOpen = true;

        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onPresentationCreation(String presentationId, SlideModel slideModel) {

        presentationModel.setPresentationPushId(presentationId);
        if( presentationModel.getPresentationImage() == null && slideModel.getSlideImageUrl() != null ) {
            presentationModel.setPresentationImage(slideModel.getSlideImageUrl());
        }
        if( !slideModelArrayList.contains(slideModel) )
            slideModelArrayList.add(slideModel);
        presentationModel.setSlideModelArrayList(slideModelArrayList);

    }

    @Override
    public void onPresentationComplete() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(CreatePresentationActivity.this);
        firebaseDatabaseHandler.setPresentationPushId(null);
        firebaseDatabaseHandler.writeNewPresentation(presentationModel);
    }
}
