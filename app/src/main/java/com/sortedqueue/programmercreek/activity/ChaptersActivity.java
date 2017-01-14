package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.fragments.ChapterDetailsFragment;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.interfaces.ChapterNavigationListener;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 05/01/17.
 */

public class ChaptersActivity extends AppCompatActivity implements ChapterNavigationListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private ChapterDetailsFragment chapterDetailsFragment;
    private ChaptersFragment chaptersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_module);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadChapterFragment();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void loadChapterFragment() {
        getSupportActionBar().setTitle("Chapters : " + new CreekPreferences(ChaptersActivity.this).getProgramLanguage().toUpperCase());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chaptersFragment = (ChaptersFragment) getSupportFragmentManager().findFragmentByTag(ChaptersFragment.class.getSimpleName());
        if( chaptersFragment == null ) {
            chaptersFragment = new ChaptersFragment();
        }
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, chaptersFragment, ChaptersFragment.class.getSimpleName());
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
        if( !title.equals("Chapters : " + new CreekPreferences(ChaptersActivity.this).getProgramLanguage().toUpperCase())) {
            loadChapterFragment();
        }
        else {
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
        Log.d("ChaptersActivity", "Selected chapter : " + chapter.toString());
        getSupportActionBar().setTitle(chapter.getChapterName());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chapterDetailsFragment = (ChapterDetailsFragment) getSupportFragmentManager().findFragmentByTag(ChapterDetailsFragment.class.getSimpleName());
        if( chapterDetailsFragment == null ) {
            chapterDetailsFragment = new ChapterDetailsFragment();
        }
        chapterDetailsFragment.setChapter(chapter);
        chapterDetailsFragment.setNextChapter( nextChapter );
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, chapterDetailsFragment, ChapterDetailsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
}
