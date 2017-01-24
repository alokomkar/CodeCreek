package com.sortedqueue.programmercreek.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HelpActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

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
}
