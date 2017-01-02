package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.ProgramWikiPagerAdapter;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class NewProgramWikiActivity extends AppCompatActivity {

    @Bind(R.id.programWikiViewPager)
    ViewPager programWikiViewPager;
    @Bind(R.id.ProgressBar)
    android.widget.ProgressBar progressBar;
    private ProgramWikiPagerAdapter programWikiPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program_wiki);
        ButterKnife.bind(this);
        CommonUtils.displayProgressDialog(NewProgramWikiActivity.this, "Loading");
        new FirebaseDatabaseHandler(NewProgramWikiActivity.this).initializeProgramWiki(new FirebaseDatabaseHandler.ProgramWikiInterface() {
            @Override
            public void getProgramWiki(ArrayList<WikiModel> programWikis) {
                programWikiPagerAdapter = new ProgramWikiPagerAdapter(getSupportFragmentManager(), programWikis);
                programWikiViewPager.setAdapter(programWikiPagerAdapter);
                progressBar.setMax(programWikis.size());
                progressBar.setProgress(1);
                programWikiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            public void onError(DatabaseError error) {
                CommonUtils.dismissProgressDialog();
            }
        });




    }
}
