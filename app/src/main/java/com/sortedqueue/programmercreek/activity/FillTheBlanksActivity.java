package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.FillBlanksPagerAdapter;
import com.sortedqueue.programmercreek.fragments.FillBlankFragment;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillTheBlanksActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.fillBlanksTextView)
    TextView fillBlanksTextView;
    @Bind(R.id.fillBlanksViewPager)
    ViewPager fillBlanksViewPager;
    @Bind(R.id.firstQuestionImageView)
    ImageView firstQuestionImageView;
    @Bind(R.id.prevQuestionImageView)
    ImageView prevQuestionImageView;
    @Bind(R.id.nextQuestionImageView)
    ImageView nextQuestionImageView;
    @Bind(R.id.lastQuestionImageView)
    ImageView lastQuestionImageView;
    @Bind(R.id.navigationLayout)
    RelativeLayout navigationLayout;
    @Bind(R.id.indexTextView)
    TextView indexTextView;
    private FillBlanksPagerAdapter fillBlanksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_the_blanks);
        ButterKnife.bind(this);
        fillBlanksViewPager.setOffscreenPageLimit(4);
        fillBlanksAdapter = new FillBlanksPagerAdapter(getSupportFragmentManager());
        fillBlanksViewPager.setAdapter(fillBlanksAdapter);

        fillBlanksViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);
                indexTextView.setText( (position + 1) + "/" + fillBlanksAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setTitle(0);

        setupListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fill_the_blanks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_done:
                checkSolution();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void checkSolution() {
        FillBlankFragment fillBlankFragment = (FillBlankFragment) fillBlanksAdapter.getItem(fillBlanksViewPager.getCurrentItem());
        if( fillBlankFragment != null ) {
            fillBlankFragment.checkSolution();
        }
    }

    private void setupListeners() {
        firstQuestionImageView.setOnClickListener(this);
        prevQuestionImageView.setOnClickListener(this);
        nextQuestionImageView.setOnClickListener(this);
        lastQuestionImageView.setOnClickListener(this);
    }

    public void setTitle(int position) {
        FillBlankFragment fillBlankFragment = (FillBlankFragment) fillBlanksAdapter.getItem(position);
        fillBlanksTextView.setText(AuxilaryUtils.getProgramTitle(fillBlankFragment.getmProgram_Index(), FillTheBlanksActivity.this, null));

        firstQuestionImageView.setVisibility(View.VISIBLE);
        prevQuestionImageView.setVisibility(View.VISIBLE);
        lastQuestionImageView.setVisibility(View.VISIBLE);
        nextQuestionImageView.setVisibility(View.VISIBLE);

        if( position == 0 ) {
            firstQuestionImageView.setVisibility(View.GONE);
            prevQuestionImageView.setVisibility(View.GONE);
        }
        else if( position == fillBlanksAdapter.getCount() -1 ) {
            lastQuestionImageView.setVisibility(View.GONE);
            nextQuestionImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.firstQuestionImageView :
                fillBlanksViewPager.setCurrentItem(0);
                break;
            case R.id.prevQuestionImageView :
                fillBlanksViewPager.setCurrentItem( fillBlanksViewPager.getCurrentItem() - 1 );
                break;
            case R.id.nextQuestionImageView :
                fillBlanksViewPager.setCurrentItem( fillBlanksViewPager.getCurrentItem() + 1 );
                break;
            case R.id.lastQuestionImageView :
                fillBlanksViewPager.setCurrentItem(fillBlanksAdapter.getCount() - 1);
                break;
        }
    }
}
