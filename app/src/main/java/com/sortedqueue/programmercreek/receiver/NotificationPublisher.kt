package com.sortedqueue.programmercreek.receiver

/**
 * Created by Alok Omkar on 2017-01-24.
 */
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.sortedqueue.programmercreek.CreekApplication

class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        CreekApplication.creekPreferences!!.isNotificationScheduled = false
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        if (!CreekApplication.instance.isAppRunning) {
            notificationManager.notify(id, notification)
        }


    }

    companion object {

        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}