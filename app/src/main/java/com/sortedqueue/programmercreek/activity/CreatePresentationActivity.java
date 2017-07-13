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

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ScreenSlidePagerAdapter;
import com.sortedqueue.programmercreek.database.PresentationModel;
import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.CreateSlideFragment;
import com.sortedqueue.programmercreek.fragments.PresentationTitleFragment;
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/04/17.
 */

public class CreatePresentationActivity extends AppCompatActivity implements View.OnClickListener, PresentationCommunicationsListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.addCodeTextView)
    TextView addCodeTextView;
    @BindView(R.id.addCodeFAB)
    FloatingActionButton addCodeFAB;
    @BindView(R.id.addPhotoTextView)
    TextView addPhotoTextView;
    @BindView(R.id.addPhotoFAB)
    FloatingActionButton addPhotoFAB;
    @BindView(R.id.deleteSlideTextView)
    TextView deleteSlideTextView;
    @BindView(R.id.deleteSlideFAB)
    FloatingActionButton deleteSlideFAB;
    @BindView(R.id.addSlideTextView)
    TextView addSlideTextView;
    @BindView(R.id.addSlideFAB)
    FloatingActionButton addSlideFAB;
    @BindView(R.id.optionsFAB)
    FloatingActionButton optionsFAB;
    @BindView(R.id.pager)
    ViewPager pager;
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
        CreekPreferences creekPreferences = CreekApplication.getCreekPreferences();
        presentationModel.setPresenterEmail(creekPreferences.getSignInAccount());
        presentationModel.setPresenterName(creekPreferences.getAccountName());

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
        optionsFAB.setVisibility(View.GONE);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    private void initPagerAdapter() {
        fragmentArrayList.add(PresentationTitleFragment.getInstance());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        pager.setAdapter(mPagerAdapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setOffscreenPageLimit(mPagerAdapter.getCount());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position == 0 ) {
                    if( isFabOpen ) {
                        optionsFAB.callOnClick();
                    }
                    com.sortedqueue.programmercreek.util.AnimationUtils.exitRevealGone(optionsFAB);
                }
                else {
                    com.sortedqueue.programmercreek.util.AnimationUtils.enterReveal(optionsFAB);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                Fragment fragment = mPagerAdapter.getItem(pager.getCurrentItem());
                if( fragment instanceof CreateSlideFragment ) {
                    CreateSlideFragment createSlideFragment = (CreateSlideFragment) fragment;
                    if (createSlideFragment.validateContent()) {
                        addNewSlide();
                    }
                }
                else {
                    addNewSlide();
                }
                break;
            case R.id.deleteSlideFAB:
            case R.id.deleteSlideTextView:
                if (mPagerAdapter.getCount() > 2) {
                    mPagerAdapter.removeCurrentFragment(pager.getCurrentItem());
                    mPagerAdapter.notifyDataSetChanged();
                    pager.setOffscreenPageLimit(mPagerAdapter.getCount());
                } else {
                    CommonUtils.displaySnackBar(CreatePresentationActivity.this, R.string.presentation_needs_one_slide);
                }
                break;
            case R.id.addCodeFAB:
            case R.id.addCodeTextView:
                addToSlide(OPTION_CODE);
                break;
            case R.id.addPhotoTextView:
            case R.id.addPhotoFAB:
                addToSlide(OPTION_PHOTO);
                break;
        }
    }

    private void addNewSlide() {
        mPagerAdapter.addNewSlideFragment(new CreateSlideFragment());
        mPagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(mPagerAdapter.getCount() - 1);
        pager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private void addToSlide(int option_code) {
        CreateSlideFragment currentFragment = (CreateSlideFragment) mPagerAdapter.getItem(pager.getCurrentItem());
        if (currentFragment != null) {
            if (option_code == OPTION_CODE) {
                currentFragment.insertCode();
            } else {
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
        if (item.getItemId() == R.id.action_finish) {
            saveAndExit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        Fragment fragment = mPagerAdapter.getItem(pager.getCurrentItem());
        if( fragment instanceof  CreateSlideFragment ) {
            CreateSlideFragment createSlideFragment = (CreateSlideFragment) fragment;
            if (createSlideFragment.validateContent()) {
                onPresentationComplete();
                finish();
            }
        }

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
            fab_close.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    toggleVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            addSlideFAB.setClickable(false);
            deleteSlideFAB.setClickable(false);
            isFabOpen = false;


        } else {

            Fragment fragment = mPagerAdapter.getItem(pager.getCurrentItem());
            if( fragment instanceof  CreateSlideFragment ) {
                CreateSlideFragment createSlideFragment = (CreateSlideFragment) fragment;
                if (createSlideFragment != null) {
                    addPhotoTextView.setText(createSlideFragment.isPhotoVisible() ? R.string.remove_photo : R.string.add_photo);
                    addCodeTextView.setText(createSlideFragment.isCodeVisible() ? R.string.remove_code : R.string.add_code);
                }
            }
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

    private void toggleVisibility(int visibility) {
        deleteSlideFAB.setVisibility(visibility);
        addSlideTextView.setVisibility(visibility);
        deleteSlideFAB.setVisibility(visibility);
        deleteSlideTextView.setVisibility(visibility);
        addSlideFAB.setVisibility(visibility);
        addSlideTextView.setVisibility(visibility);
        addCodeFAB.setVisibility(visibility);
        addCodeTextView.setVisibility(visibility);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onPresentationTitle(String presentationTitle, String presentationDescription, ArrayList<String> tagsList) {
        presentationModel.setPresentationName(presentationTitle);
        presentationModel.setPresentationDescription(presentationDescription);
        HashMap<String, Integer> tagsMap = new HashMap<>();
        for( String tag : tagsList ) {
            tagsMap.put(tag, 1);
        }
        presentationModel.setTagList(tagsMap);
        addNewSlide();
    }

    @Override
    public void onPresentationCreation(String presentationId, SlideModel slideModel) {

        presentationModel.setPresentationPushId(presentationId);
        if (presentationModel.getPresentationImage() == null && slideModel.getSlideImageUrl() != null) {
            presentationModel.setPresentationImage(slideModel.getSlideImageUrl());
        }
        if (!slideModelArrayList.contains(slideModel))
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
