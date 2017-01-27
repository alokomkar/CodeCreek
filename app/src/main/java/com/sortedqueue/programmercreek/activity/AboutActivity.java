package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AboutActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		Button viewProfileBtn = (Button) findViewById(R.id.btn_view_profile);
		viewProfileBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/pub/alok-omkar/b/917/333"));
				startActivity(browserIntent);
			}
		});
		this.overridePendingTransition(R.anim.anim_slide_in_left,
				R.anim.anim_slide_out_left);

	}

	@Override
	protected void onResume() {
		super.onResume();
		CreekApplication.getInstance().setAppRunning(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		CreekApplication.getInstance().setAppRunning(false);
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
