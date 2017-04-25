package com.sortedqueue.programmercreek.receiver;

/**
 * Created by Alok Omkar on 2017-01-24.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sortedqueue.programmercreek.CreekApplication;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        CreekApplication.getCreekPreferences().setNotificationScheduled(false);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        if(!CreekApplication.getInstance().isAppRunning()) {
            notificationManager.notify(id, notification);
        }


    }
}