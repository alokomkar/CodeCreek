package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
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
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.fragments.AlgorithmFragment;
import com.sortedqueue.programmercreek.fragments.AlgorithmIndexFragment;
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener;
//import com.startapp.android.publish.adsCommon.StartAppAd;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2017-03-17.
 */

public class AlgorithmListActivity extends AppCompatActivity implements AlgorithmNavigationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private FragmentTransaction mFragmentTransaction;

    private AlgorithmFragment algorithmFragment;

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
        checkFAB.setVisibility(View.GONE);
        loadAlgorithmFragment((AlgorithmsIndex) getIntent().getParcelableExtra(ProgrammingBuddyConstants.KEY_PROG_ID));
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_algorithm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if( item.getItemId() == R.id.action_share ) {
            shareProgram();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareProgram() {
        if( algorithmFragment != null ) {
            algorithmFragment.shareAlgorithm();
        }
    }

    @Override
    public void loadAlgoritmsListFragment() {
        getSupportActionBar().setTitle("Algorithms");
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, AlgorithmIndexFragment.getInstance(), AlgorithmIndexFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadAlgorithmFragment(AlgorithmsIndex algorithm) {
        getSupportActionBar().setTitle(algorithm.getProgramTitle());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        algorithmFragment = AlgorithmFragment.newInstance(algorithm);
        mFragmentTransaction.replace(R.id.container, algorithmFragment, AlgorithmFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        /*if( CreekApplication.getCreekPreferences().getAdsEnabled() ) {
            StartAppAd.onBackPressed(this);
            super.onBackPressed();
        }
        else */{
            finish();
        }

    }
}
