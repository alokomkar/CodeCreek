package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.DashboardActivity;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AuxilaryUtils {

    private static int progressBarStatus;


    public static void showConfirmationDialog(final Activity activity) {

        Builder builder = new Builder(activity);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //dialog.dismiss();
            }
        });

        builder.setMessage("All your progress will be lost. Are you sure you want to Quit?");
        builder.setTitle(activity.getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.show();
    }


    public static void displayAlert(String title, String message, Context context) {

        Builder alertDialogBuilder = new Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void displayResultAlert(final Activity activity, String title, String message, int pointsScore, int maxScore) {

        Builder alertDialogBuilder = new Builder(
                activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_result, null);
        ButterKnife.bind(view);
        alertDialogBuilder.setView(view);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView bigScoreTextView = (TextView) view.findViewById(R.id.bigScoreTextView);
        TextView resultTextView = (TextView) view.findViewById(R.id.resultTextView);
        bigScoreTextView.setText(pointsScore + "/" + maxScore);
        resultTextView.setText(message);
        progressBar.setMax(maxScore);
        progressBar.setProgress(pointsScore);
        // set title
        alertDialogBuilder.setTitle(title);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        /*final Handler handler = new Handler();
        if( alertDialog.isShowing() ) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (progressBarStatus = 0; progressBarStatus <= 100; progressBarStatus++) {

                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });


                        try {
                            Thread.sleep(50);
                        } catch (Exception ex) {
                        }
                    }
                }
            }).start();
        }*/

    }

    public static String getProgramTitle(int program_Index, Context context, DatabaseHandler databaseHandler) {
        HashMap<Integer, Program_Index> program_indexHashMap = CreekApplication.getInstance().getProgram_indexHashMap();
        if( program_indexHashMap.containsKey(program_Index) ) {
            return CreekApplication.getInstance().getProgram_indexHashMap().get(program_Index).getProgram_Description();
        }
        return null;
    }

    public static void generateBigTextNotification(final Context context, final String notificationTitle, final String notificationContent) {


        Intent resultIntent;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        resultIntent = new Intent(context, DashboardActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setTicker(notificationTitle)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                        .setContentTitle(notificationTitle)
                        .setContentIntent(resultPendingIntent);
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setColor(Color.parseColor("#7B1FA2"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        final int mNotificationId = notificationContent.hashCode();
        final NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        final Notification noti = builder.build();
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotifyMgr.notify(mNotificationId, noti);


    }

    public static void generateBigNotification(final Context context, final String notificationTitle, final String notificationContent) {


        Glide
                .with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/Infinite%20Programmer-feature-graphic.png?alt=media&token=7140ec19-5313-4c9d-8435-4f12a25cee34")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(SimpleTarget.SIZE_ORIGINAL, SimpleTarget.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        Intent resultIntent;
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                        resultIntent = new Intent(context, DashboardActivity.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(context)
                                        .setTicker(notificationTitle)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                                        .setContentTitle(notificationTitle)
                                        .setContentIntent(resultPendingIntent);

                        builder.setContentTitle(notificationTitle)
                                .setContentText(notificationContent)
                                .setColor(Color.parseColor("#7B1FA2"))
                                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(resource))
                                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                                .setSmallIcon(R.mipmap.ic_launcher);
                        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));


                        final int mNotificationId = notificationContent.hashCode();
                        final NotificationManager mNotifyMgr =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                        // hide the notification after its selected
                        final Notification noti = builder.build();
                        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        //  noti.bigContentView = getComplexNotificationView(this, notificationTitle, notificationContent, "", model.getOrder().getDeliveryTime(), model.getOrder().getTotalQuantity(), model.getOrder().getDeliveryCharges());
                        noti.flags |= Notification.FLAG_AUTO_CANCEL;
                        mNotifyMgr.notify(mNotificationId, noti);
                    }
                });


    }

}
