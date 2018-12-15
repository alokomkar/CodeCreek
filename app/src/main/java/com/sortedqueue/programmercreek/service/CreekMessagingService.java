package com.sortedqueue.programmercreek.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

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


        Intent intent = new Intent();
        intent.setAction(ProgrammingBuddyConstants.INSTANCE.getCUSTOM_ACTION_NOTIFICATION());
        intent.putExtra(ProgrammingBuddyConstants.INSTANCE.getINTENT_EXTRA_NOTIFICATION_MESSAGE(), remoteMessage);
        this.sendBroadcast(intent);


        /*String from = remoteMessage.getFrom();
        String message = remoteMessage.get*/
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        new FirebaseDatabaseHandler(getApplicationContext()).updateMessageToken( refreshedToken );
    }
}
