package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.fragments.MatchMakerFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WizardActivity extends AppCompatActivity {

    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private MatchMakerFragment matchMakerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        loadMatchMakerFragment( bundle );
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    private void loadMatchMakerFragment(Bundle bundle) {
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
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
