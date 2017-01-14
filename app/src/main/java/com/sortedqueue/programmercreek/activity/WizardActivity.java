package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.fragments.FillBlankFragment;
import com.sortedqueue.programmercreek.fragments.MatchMakerFragment;
import com.sortedqueue.programmercreek.fragments.QuizFragment;
import com.sortedqueue.programmercreek.fragments.TestDragNDropFragment;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WizardActivity extends AppCompatActivity implements WizardNavigationListener {

    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private MatchMakerFragment matchMakerFragment;
    private TestDragNDropFragment testDragNDropFragment;
    private QuizFragment quizFragment;
    private FillBlankFragment fillBlankFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST)) {
            case ProgrammingBuddyConstants.KEY_MATCH :
                loadMatchMakerFragment( bundle );
                break;
            case ProgrammingBuddyConstants.KEY_TEST:
                loadTestFragment( bundle );
                break;
            case ProgrammingBuddyConstants.KEY_QUIZ:
                loadQuizFragment( bundle );
                break;
        }

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    @Override
    public void loadMatchMakerFragment(Bundle bundle) {
        setTitle("Match : " + bundle.getString(ProgrammingBuddyConstants.KEY_PROG_TITLE, ""));
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        matchMakerFragment = (MatchMakerFragment) getSupportFragmentManager().findFragmentByTag(MatchMakerFragment.class.getSimpleName());
        if( matchMakerFragment == null ) {
            matchMakerFragment = new MatchMakerFragment();
        }
        matchMakerFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, matchMakerFragment, MatchMakerFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadTestFragment(Bundle bundle) {
        setTitle("Test : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        testDragNDropFragment = (TestDragNDropFragment) getSupportFragmentManager().findFragmentByTag(TestDragNDropFragment.class.getSimpleName());
        if( testDragNDropFragment == null ) {
            testDragNDropFragment = new TestDragNDropFragment();
        }
        testDragNDropFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, testDragNDropFragment, TestDragNDropFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadQuizFragment(Bundle bundle) {
        setTitle("Quiz : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        quizFragment = (QuizFragment) getSupportFragmentManager().findFragmentByTag(QuizFragment.class.getSimpleName());
        if( quizFragment == null ) {
            quizFragment = new QuizFragment();
        }
        quizFragment.setBundle(bundle);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, quizFragment, QuizFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void loadFillBlanksFragment(Bundle bundle) {
        setTitle("Fill blanks : " + ((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_Description());
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        fillBlankFragment = (FillBlankFragment) getSupportFragmentManager().findFragmentByTag(FillBlankFragment.class.getSimpleName());
        if( fillBlankFragment == null ) {
            fillBlankFragment = new FillBlankFragment();
        }
        fillBlankFragment.setmProgram_Index(((ProgramIndex)(bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID))).getProgram_index());
        fillBlankFragment.setWizardMode( true );
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, fillBlankFragment, FillBlankFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        String title = getTitle().toString();
        if( title.startsWith("Match") ) {
            if( matchMakerFragment != null ) {
                matchMakerFragment.onBackPressed();
                return;
            }
        }
        else if( title.startsWith("Test") ) {
            if( testDragNDropFragment != null ) {
                testDragNDropFragment.onBackPressed();
                return;
            }
        }
        else if( title.startsWith("Quiz") ) {
            if( quizFragment != null ) {
                quizFragment.onBackPressed();
                return;
            }
        }
        finish();
    }

}
