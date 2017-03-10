package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.InterviewQuestionsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewActivity extends AppCompatActivity {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private FragmentTransaction mFragmentTransaction;
    private InterviewQuestionsFragment interviewQuestionsFragment;

    @Override
    protected void onResume() {
        super.onResume();
        CreekApplication.getInstance().setAppRunning(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CreekApplication.getInstance().setAppRunning(false);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadInterviewQuestionsFragment();
    }

    private void loadInterviewQuestionsFragment() {

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        interviewQuestionsFragment = (InterviewQuestionsFragment) getSupportFragmentManager().findFragmentByTag(InterviewQuestionsFragment.class.getSimpleName());
        if (interviewQuestionsFragment == null) {
            interviewQuestionsFragment = new InterviewQuestionsFragment();
        }

        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, interviewQuestionsFragment, InterviewQuestionsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void fabClick( View view ) {
        if( interviewQuestionsFragment != null ) {
            interviewQuestionsFragment.checkAnswer();
        }
    }
}
