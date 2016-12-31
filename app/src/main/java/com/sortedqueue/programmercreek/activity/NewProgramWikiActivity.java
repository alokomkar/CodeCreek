package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.ProgramWikiPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class NewProgramWikiActivity extends AppCompatActivity {

    @Bind(R.id.firstQuestionImageView)
    ImageView firstQuestionImageView;
    @Bind(R.id.prevQuestionImageView)
    ImageView prevQuestionImageView;
    @Bind(R.id.indexTextView)
    TextView indexTextView;
    @Bind(R.id.nextQuestionImageView)
    ImageView nextQuestionImageView;
    @Bind(R.id.lastQuestionImageView)
    ImageView lastQuestionImageView;
    @Bind(R.id.navigationLayout)
    RelativeLayout navigationLayout;
    @Bind(R.id.programWikiViewPager)
    ViewPager programWikiViewPager;

    private ProgramWikiPagerAdapter programWikiPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program_wiki);
        ButterKnife.bind(this);

        programWikiPagerAdapter = new ProgramWikiPagerAdapter(getSupportFragmentManager());
        programWikiViewPager.setAdapter(programWikiPagerAdapter);



    }
}
