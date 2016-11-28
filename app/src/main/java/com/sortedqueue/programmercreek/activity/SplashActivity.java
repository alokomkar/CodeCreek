package com.sortedqueue.programmercreek.activity;

/**
 * Created by Alok Omkar on 2016-11-26.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sortedqueue.programmercreek.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                Intent i =  new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }

}
