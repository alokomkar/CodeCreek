package com.sortedqueue.programmercreek.util;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sortedqueue.programmercreek.CreekApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Alok on 08/09/17.
 */

public class CreekAnalytics {

    private static FirebaseAnalytics mFirebaseAnalytics;
    private static FirebaseAnalytics getInstance() {
        if( mFirebaseAnalytics == null )
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(CreekApplication.getInstance());
        return mFirebaseAnalytics;
    }

    public static void logEvent(String TAG, JSONObject jsonObject) {
        Bundle bundle = new Bundle();
        bundle.putString("ScreenName", TAG);
        bundle.putString("EventDetails", jsonObject.toString() );
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                bundle.putString(key, String.valueOf(jsonObject.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getInstance().logEvent("pcEvent", bundle);
    }

    public static void logEvent(String TAG, String event) {
        Bundle bundle = new Bundle();
        bundle.putString("ScreenName", TAG);
        bundle.putString("EventDetails", event );
        getInstance().logEvent("pcEvent", bundle);
    }
}
