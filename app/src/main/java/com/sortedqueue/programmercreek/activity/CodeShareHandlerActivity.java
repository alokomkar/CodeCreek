package com.sortedqueue.programmercreek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 25/05/17.
 */

public class CodeShareHandlerActivity extends AppCompatActivity {

    @Bind(R.id.codeTextView)
    TextView codeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_share);
        ButterKnife.bind(this);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            codeTextView.setText(sharedText);

            ArrayList<String> explanations = AuxilaryUtils.mapCodeToComments( CodeShareHandlerActivity.this, sharedText );
            if( explanations != null ) {
                String explanation = "";
                for( String explanationCode : explanations ) {
                    explanation += explanationCode + "\n";
                }
                if( explanation != "" ) {
                    codeTextView.append("\nExplanation\n\n" + explanation);
                }
            }
        }
    }
}
