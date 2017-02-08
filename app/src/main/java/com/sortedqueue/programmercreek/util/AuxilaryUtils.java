package com.sortedqueue.programmercreek.util;

import android.app.Activity;
import android.app.AlarmManager;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.messaging.RemoteMessage;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.DashboardActivity;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.receiver.NotificationPublisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import butterknife.ButterKnife;

import static android.R.id.message;


public class AuxilaryUtils {

    private static int progressBarStatus;
    private static String CONGRATS_GIF = "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/congratulations.gif?alt=media&token=90c039af-2a96-465b-ad72-3df6f03b7c19";


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

    public interface InviteDialogListener {
        void onInviteClick();
        void onLaterClick();
    }
    public static void displayAppInviteDialog(Context context, final InviteDialogListener inviteDialogListener ) {

        Builder alertDialogBuilder = new Builder(
                context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_app_invite, null);
        ButterKnife.bind(view);
        alertDialogBuilder.setView(view);

        TextView resultTextView = (TextView) view.findViewById(R.id.inviteTextView);
        resultTextView.setText("Congratulations on your progress!!\nHow do you like the app so far?\n\n" +
                "Would you like to invite your friends in your journey of learning?");
        ImageView imageView = (ImageView) view.findViewById(R.id.congratsImageView);
        // set title
        Glide.with(context)
                .load(CONGRATS_GIF)
                .asGif()
                .into(imageView);
        alertDialogBuilder.setTitle("Invite Friends");


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.invite, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        inviteDialogListener.onInviteClick();
                        dialog.dismiss();
                    }
                })
        .setNegativeButton(R.string.may_be_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inviteDialogListener.onLaterClick();
                dialogInterface.dismiss();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.AchievementDialogAnimation;

        alertDialog.show();
    }

    public static void displayInformation(Context context,
                                          int title,
                                          int description,
                                          DialogInterface.OnDismissListener onCancelListner) {

        final String preferenceString = context.getString(title).replaceAll("\\s+", "");
        final CreekPreferences creekPreferences = new CreekPreferences(context);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(description)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton(R.string.dont_show_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        creekPreferences.setShowDialog(preferenceString, false);
                    }
                })
                .setOnDismissListener( onCancelListner )
                .create();
        if( creekPreferences.getShowDialog(preferenceString) ) {
            alertDialog.show();
        }
        else {
            onCancelListner.onDismiss(new DialogInterface() {
                @Override
                public void cancel() {

                }

                @Override
                public void dismiss() {

                }
            });
        }

    }

    public static void scheduleImmediateNotification(Context context, RemoteMessage remoteMessage) {
        String notificationContent = generateRandomNotificationContent(context);
        Notification notification = generateNotification(context, "Did you know", notificationContent );
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long delay = 1000 * 15;
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        CreekPreferences creekPreferences = new CreekPreferences(context);
        if( !creekPreferences.isNotificationScheduled() ) {
            creekPreferences.setNotificationScheduled(true);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    public static void scheduleNotification( Context context ) {
        String notificationContent = generateRandomNotificationContent(context);
        Notification notification = generateNotification(context, "Did you know", notificationContent );
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long delay = 1000 * 60 * 60 * 8;
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        CreekPreferences creekPreferences = new CreekPreferences(context);
        if( !creekPreferences.isNotificationScheduled() ) {
            creekPreferences.setNotificationScheduled(true);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    private static String generateRandomNotificationContent(Context context) {

        /** generateRandomNotificationContent : TODO On New Language*/
        ArrayList<String> cNotifications = new ArrayList<>();
        cNotifications.addAll(Arrays.asList(context.getResources().getStringArray(R.array.c_notifications_array)));
        ArrayList<String> cppNotifications = new ArrayList<>();
        cppNotifications.addAll(Arrays.asList(context.getResources().getStringArray(R.array.cpp_notifications_array)));
        ArrayList<String> javaNotifications = new ArrayList<>();
        javaNotifications.addAll(Arrays.asList(context.getResources().getStringArray(R.array.java_notifications_array)));
        ArrayList<String> uspNotifications = new ArrayList<>();
        uspNotifications.addAll(Arrays.asList(context.getResources().getStringArray(R.array.usp_notifications_array)));
        String programLanguage = new CreekPreferences(context).getProgramLanguage();
        String notification = "";
        Random random = new Random();
        switch ( programLanguage ) {
            case "c" :
                notification = cNotifications.get(random.nextInt(cNotifications.size()));
                break;
            case "java" :
                notification = javaNotifications.get(random.nextInt(javaNotifications.size()));
                break;
            case "cpp" :
            case "c++" :
                notification = cppNotifications.get(random.nextInt(cppNotifications.size()));
                break;
            case "usp" :
                notification = uspNotifications.get(random.nextInt(uspNotifications.size()));
                break;
            case "sql" :
                notification = javaNotifications.get(random.nextInt(javaNotifications.size()));
                break;
        }

        return notification;
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

    public static ArrayList<String> splitProgramIntolines(String programCode) {
        String lines[] = programCode.split("\\r?\\n");
        return new ArrayList<>(Arrays.asList(lines));
    }

    public interface InputTextListener {
        void onSuccess( String text );
        void onDismiss();
    }
    public static void displayInputDialog(Context context, String title, String message, final InputTextListener inputTextListener ) {
        Builder alertDialogBuilder = new Builder(
                context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        ButterKnife.bind(view);
        final EditText inputEditText = (EditText) view.findViewById(R.id.inputEditText);
        TextView headerTextView = (TextView) view.findViewById(R.id.headerTextView);
        headerTextView.setText(title);
        if( message != null ) {
            inputEditText.setText(message);
        }
        alertDialogBuilder.setView(view);
        // set title
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
        alertDialogBuilder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputTextListener.onSuccess(inputEditText.getText().toString());
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void displayAchievementUnlockedDialog(Context context, String title, String message ) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message + ", Good work")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        new CreekPreferences(context).setShowInviteDialog(true);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.AchievementDialogAnimation;
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

    }

    public static String getProgramTitle(int program_Index, Context context ) {
        HashMap<Integer, ProgramIndex> program_indexHashMap = CreekApplication.getInstance().getProgram_indexHashMap();
        if( program_indexHashMap.containsKey(program_Index) ) {
            return CreekApplication.getInstance().getProgram_indexHashMap().get(program_Index).getProgram_Description();
        }
        return null;
    }

    public static void generateBigTextNotification(final Context context, final String notificationTitle, final String notificationContent) {
        final Notification noti = generateNotification(context, notificationTitle, notificationContent);
        final int mNotificationId = notificationContent.hashCode();
        final NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotifyMgr.notify(mNotificationId, noti);


    }

    private static Notification generateNotification(Context context, String notificationTitle, String notificationContent) {
        Intent resultIntent;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        resultIntent = new Intent(context, DashboardActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setTicker(notificationTitle)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                        .setContentTitle(notificationTitle)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent);
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setColor(Color.parseColor("#7B1FA2"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        return builder.build();
    }

    public static void generateBigNotification(final Context context, final String notificationTitle, final String notificationContent) {

        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/Infinite%20Programmer-feature-graphic.png?alt=media&token=7140ec19-5313-4c9d-8435-4f12a25cee34";
        generateImageNotification(context, notificationTitle, notificationContent, imageUrl);


    }

    //GIF : https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/congratulations.gif?alt=media&token=90c039af-2a96-465b-ad72-3df6f03b7c19

    public static void generateImageNotification(final Context context, final String notificationTitle, final String notificationContent, final String imageUrl) {

        Glide
                .with(context)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(SimpleTarget.SIZE_ORIGINAL, SimpleTarget.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        Intent resultIntent;
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                        resultIntent = new Intent(context, DashboardActivity.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                PendingIntent.getActivity(context, 0,
                                        resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)CreekApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
