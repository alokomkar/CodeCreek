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
import com.sortedqueue.programmercreek.fragments.SlideFragment;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 06/04/17.
 */

public class CreatePresentationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab2)
    FloatingActionButton fab2;
    @Bind(R.id.fab1)
    FloatingActionButton fab1;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.deleteSlideTextView)
    TextView deleteSlideTextView;
    @Bind(R.id.addSlideTextView)
    TextView addSlideTextView;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    private ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_presentation);
        ButterKnife.bind(this);
        fragmentArrayList = new ArrayList<>();

        initPagerAdapter();


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void initPagerAdapter() {
        fragmentArrayList.add(new SlideFragment());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        pager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                mPagerAdapter.addNewSlideFragment(new SlideFragment());
                mPagerAdapter.notifyDataSetChanged();
                pager.setCurrentItem(mPagerAdapter.getCount() - 1);
                break;
            case R.id.fab2:
                if( mPagerAdapter.getCount() > 1 ) {
                    mPagerAdapter.removeCurrentFragment(pager.getCurrentItem());
                    mPagerAdapter.notifyDataSetChanged();
                }
                else {
                    CommonUtils.displaySnackBar(CreatePresentationActivity.this, R.string.presentation_needs_one_slide);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_syntax_learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void animateFAB() {
        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            addSlideTextView.startAnimation(fab_close);
            deleteSlideTextView.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            addSlideTextView.startAnimation(fab_open);
            deleteSlideTextView.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;

        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
