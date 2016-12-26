package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.SyntaxPagerAdapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.ModuleFragment;
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SyntaxLearnActivity extends AppCompatActivity implements SyntaxNavigationListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.syntaxLearnViewPager)
    ViewPager syntaxLearnViewPager;
    @Bind(R.id.ProgressBar)
    ProgressBar progressBar;
    @Bind(R.id.viewPagerLayout)
    LinearLayout viewPagerLayout;
    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private ModuleFragment moduleFragment;
    private String TAG = SyntaxLearnActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syntax_learn);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadModulesFragment();
    }

    private void loadModulesFragment() {
        getSupportActionBar().setTitle("Modules");
        viewPagerLayout.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        moduleFragment = (ModuleFragment) getSupportFragmentManager().findFragmentByTag(ModuleFragment.class.getSimpleName());
        if( moduleFragment == null ) {
            moduleFragment = new ModuleFragment();
        }
        mFragmentTransaction.replace(R.id.container, moduleFragment, ModuleFragment.class.getSimpleName());
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
        if (item.getItemId() == R.id.action_hint) {
            Snackbar.make(findViewById(android.R.id.content), "Display your hint here", Snackbar.LENGTH_LONG).show();
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onModuleLoad(final LanguageModule module) {

        CommonUtils.displayProgressDialog(SyntaxLearnActivity.this, "Loading material");
        new FirebaseDatabaseHandler(SyntaxLearnActivity.this).initializeSyntax(module, new FirebaseDatabaseHandler.SyntaxInterface() {
            @Override
            public void getSyntaxModules(ArrayList<SyntaxModule> syntaxModules) {
                setupViewPager(module, syntaxModules);
            }

            @Override
            public void onError(DatabaseError error) {
                Log.e(TAG, "Error : " + error.toString());
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    private void setupViewPager(LanguageModule module, ArrayList<SyntaxModule> syntaxModules) {
        getSupportActionBar().setTitle("Syntax Learner");
        viewPagerLayout.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        syntaxLearnViewPager.setOffscreenPageLimit(syntaxModules.size());
        SyntaxPagerAdapter syntaxPagerAdapter = new SyntaxPagerAdapter(getSupportFragmentManager(), module, syntaxModules);
        syntaxLearnViewPager.setAdapter(syntaxPagerAdapter);
        progressBar.setMax(syntaxModules.size());
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
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onBackPressed() {
        String title = getSupportActionBar().getTitle().toString();
        Log.d(TAG, "onBackPress : " + title);
        if( !title.equals("Modules") ) {
            loadModulesFragment();
        }
        else {
            super.onBackPressed();
        }
    }
}
