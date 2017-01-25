package com.sortedqueue.programmercreek.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

/**
 * Created by Alok on 25/01/17.
 */

public class CreekFCMReceiver extends BroadcastReceiver {

    private String TAG = CreekFCMReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteMessage remoteMessage = intent.getParcelableExtra(ProgrammingBuddyConstants.INTENT_EXTRA_NOTIFICATION_MESSAGE);
        String imageUrl = "";
        String messageBody = "";
        if ( remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            messageBody = remoteMessage.getNotification().getBody();
        }

        if( remoteMessage.getData() != null ) {
            Log.d(TAG, "Message Notification Data: " + remoteMessage.getData());
            if( remoteMessage.getData().containsKey("image") ) {
                imageUrl = remoteMessage.getData().get("image");
            }
        }
        if( imageUrl.equals("") ) {
            AuxilaryUtils.generateBigTextNotification(context, context.getString(R.string.app_name), messageBody);
        }
        else {
            AuxilaryUtils.generateImageNotification(context, context.getString(R.string.app_name), messageBody, imageUrl);
        }
    }
}
