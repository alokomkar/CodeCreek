package com.sortedqueue.programmercreek.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.FillBlanksPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillTheBlanksActivity extends AppCompatActivity {

    @Bind(R.id.fillBlanksTextView)
    TextView fillBlanksTextView;
    @Bind(R.id.fillBlanksViewPager)
    ViewPager fillBlanksViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_the_blanks);
        ButterKnife.bind(this);
        fillBlanksViewPager.setAdapter( new FillBlanksPagerAdapter(getSupportFragmentManager()) );
    }
}
