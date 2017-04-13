package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.firebase.Code;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.fragments.CodeLanguageFragment;
import com.sortedqueue.programmercreek.fragments.CompileCodeFragment;
import com.sortedqueue.programmercreek.interfaces.CodeLabNavigationListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 04/04/17.
 */

public class CodeLabActivity extends AppCompatActivity implements CodeLabNavigationListener, View.OnClickListener {

    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private FragmentTransaction mFragmentTransaction;
    private CodeLanguageFragment codeLanguageFragment;
    private CompileCodeFragment compileCodeFragment;
    private Bundle bundle;

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

        bundle = getIntent().getExtras();
        if( bundle == null ) {
            loadCodeLanguagesFragment();
        }
        else {
            Code code = bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID);
            loadCompileCodeFragment(code);
        }
        checkFAB.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    private boolean isFirstTime = true;
    @Override
    public void loadCompileCodeFragment( Code code ) {
        getSupportActionBar().setTitle("Code Lab : Hello world" );
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        compileCodeFragment = (CompileCodeFragment) getSupportFragmentManager().findFragmentByTag(ChaptersFragment.class.getSimpleName());
        if (compileCodeFragment == null) {
            compileCodeFragment = new CompileCodeFragment();
        }
        compileCodeFragment.setParameter( code );
        checkFAB.setImageDrawable(ContextCompat.getDrawable(CodeLabActivity.this, android.R.drawable.ic_media_play));
        AnimationUtils.enterReveal(checkFAB);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, compileCodeFragment, ChaptersFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadCodeLanguagesFragment() {
        getSupportActionBar().setTitle("Code Lab" );
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        codeLanguageFragment = (CodeLanguageFragment) getSupportFragmentManager().findFragmentByTag(CodeLanguageFragment.class.getSimpleName());
        if (codeLanguageFragment == null) {
            codeLanguageFragment = CodeLanguageFragment.getInstance();
        }
        checkFAB.setImageDrawable(ContextCompat.getDrawable(CodeLabActivity.this, android.R.drawable.ic_media_play));
        if( isFirstTime ) {
            checkFAB.setVisibility(View.GONE);
            isFirstTime = false;
        }
        else {
            AnimationUtils.exitReveal(checkFAB);
        }
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, codeLanguageFragment, CodeLanguageFragment.class.getSimpleName());
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
            loadCodeLanguagesFragment();
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
    public void onClick(View view) {
        if( compileCodeFragment != null ) {
            compileCodeFragment.executeProgram();
        }
    }
}
