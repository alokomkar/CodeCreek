package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.ProgramInserterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgramInserterActivity extends AppCompatActivity {

    private FragmentTransaction mFragmentTransaction;
    private ProgramInserterFragment programInserterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_inserter);
        ButterKnife.bind(this);
        loadProgramInserterFragment();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void loadProgramInserterFragment() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        programInserterFragment
                = (ProgramInserterFragment) getSupportFragmentManager().findFragmentByTag(ProgramInserterFragment.class.getSimpleName());
        if( programInserterFragment == null ) {
            programInserterFragment = new ProgramInserterFragment();
        }
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, programInserterFragment, ProgramInserterFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
}
