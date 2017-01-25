package com.sortedqueue.programmercreek.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

/**
 * Created by Alok on 25/01/17.
 */

public class CreekMessagingService extends FirebaseMessagingService {

    private String TAG = CreekMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        String imageUrl = "";
        String messageBody = "";
        if (remoteMessage.getData().size() > 0) {
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
            AuxilaryUtils.generateBigTextNotification(this, getString(R.string.app_name), messageBody);
        }
        else {
            //TODO AuxilaryUtils.generateImageNotification(this, getString(R.string.app_name), messageBody, imageUrl);
        }
        /*String from = remoteMessage.getFrom();
        String message = remoteMessage.get*/
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
