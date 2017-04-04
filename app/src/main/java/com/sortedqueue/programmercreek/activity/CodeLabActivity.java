package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.fragments.CompileCodeFragment;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 04/04/17.
 */

public class CodeLabActivity extends AppCompatActivity implements ChapterNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private FragmentTransaction mFragmentTransaction;
    private ChapterDetailsFragment chapterDetailsFragment;
    private CompileCodeFragment compileCodeFragment;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_module);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadChapterFragment();
        checkFAB.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    private boolean isFirstTime = true;
    private void loadChapterFragment() {
        getSupportActionBar().setTitle("Code Lab : Hello world" );
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        compileCodeFragment = (CompileCodeFragment) getSupportFragmentManager().findFragmentByTag(ChaptersFragment.class.getSimpleName());
        if (compileCodeFragment == null) {
            compileCodeFragment = new CompileCodeFragment();
        }
        checkFAB.setImageDrawable(ContextCompat.getDrawable(CodeLabActivity.this, android.R.drawable.ic_media_play));
        /*if( isFirstTime ) {
            checkFAB.setVisibility(View.GONE);
            isFirstTime = false;
        }
        else {
            AnimationUtils.exitReveal(checkFAB);
        }*/
        checkFAB.setVisibility(View.VISIBLE);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, compileCodeFragment, ChaptersFragment.class.getSimpleName());
        mFragmentTransaction.commit();
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
        String title = getSupportActionBar().getTitle().toString();
        if (!title.equals("Code Lab : Hello world")) {
            loadChapterFragment();
        } else {
            finish();
        }
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

    @Override
    public void onChapterSelected(Chapter chapter, Chapter nextChapter) {
        Log.d("CodeLabActivity", "Selected chapter : " + chapter.toString());
        getSupportActionBar().setTitle(chapter.getChapterName());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chapterDetailsFragment = (ChapterDetailsFragment) getSupportFragmentManager().findFragmentByTag(ChapterDetailsFragment.class.getSimpleName());
        if (chapterDetailsFragment == null) {
            chapterDetailsFragment = new ChapterDetailsFragment();
        }
        //checkFAB.setVisibility(View.VISIBLE);
        AnimationUtils.enterReveal(checkFAB);
        chapterDetailsFragment.setChapter(chapter);
        chapterDetailsFragment.setNextChapter(nextChapter);
        chapterDetailsFragment.setOnChapterNavigationListener(this);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, chapterDetailsFragment, ChapterDetailsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void toggleFabDrawable(int drawable) {
        checkFAB.setImageDrawable(ContextCompat.getDrawable(CodeLabActivity.this, drawable));
    }

    @Override
    public void onClick(View view) {
        if( compileCodeFragment != null ) {
            compileCodeFragment.executeProgram();
        }
    }
}
