package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.SyntaxPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SyntaxLearnActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.syntaxLearnViewPager)
    ViewPager syntaxLearnViewPager;
    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syntax_learn);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        syntaxLearnViewPager.setOffscreenPageLimit(10);
        SyntaxPagerAdapter syntaxPagerAdapter = new SyntaxPagerAdapter(getSupportFragmentManager());
        syntaxLearnViewPager.setAdapter(syntaxPagerAdapter);
        progressBar.setMax(10);
        progressBar.setProgress(1);
        syntaxLearnViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressBar.setProgress(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_syntax_learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_hint) {
            Snackbar.make(findViewById(android.R.id.content), "Display your hint here", Snackbar.LENGTH_LONG).show();
        } else if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
