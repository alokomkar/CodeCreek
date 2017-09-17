package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.IntroTopicsFragment;
import com.sortedqueue.programmercreek.fragments.TopicDetailsFragment;
import com.sortedqueue.programmercreek.interfaces.NewIntroNavigationListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 15/09/17.
 */

public class NewIntroActivity extends AppCompatActivity implements NewIntroNavigationListener {

    @BindView(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intro);
        ButterKnife.bind(this);
        loadIntroTopicsFragment();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void loadIntroTopicsFragment() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container, new IntroTopicsFragment(), IntroTopicsFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void loadTopicDetailsFragment( String topic ) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        TopicDetailsFragment topicDetailsFragment = new TopicDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("topic", topic);
        topicDetailsFragment.setArguments(bundle);
        mFragmentTransaction.replace(R.id.container, topicDetailsFragment, TopicDetailsFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if( getSupportFragmentManager().getBackStackEntryCount() > 1 ) {
            getSupportFragmentManager().popBackStack();
        }
        else
            finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
