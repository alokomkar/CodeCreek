package com.sortedqueue.programmercreek.util

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.google.firebase.messaging.RemoteMessage
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.DashboardActivity
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.interfaces.UnlockByInviteInterface
import com.sortedqueue.programmercreek.receiver.NotificationPublisher
import com.sortedqueue.programmercreek.view.UserProgramDialog

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.Random

import butterknife.ButterKnife

import com.facebook.login.widget.ProfilePictureView.TAG


object AuxilaryUtils {

    private val progressBarStatus: Int = 0
    private val CONGRATS_GIF = "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/congratulations.gif?alt=media&token=90c039af-2a96-465b-ad72-3df6f03b7c19"


    fun showConfirmationDialog(activity: Activity) {

        val builder = Builder(activity)
        builder.setPositiveButton("Yes") { dialog, which ->
            dialog.dismiss()
            activity.finish()
        }

        builder.setNegativeButton("No") { dialog, which ->
            //dialog.dismiss();
        }

        builder.setMessage("All your progress will be lost. Are you sure you want to Quit?")
        builder.setTitle(activity.title)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.show()
    }

    interface PhotoOptionListener {
        fun onChoiceSelected(choice: Int)
    }

    val CHOICE_CAMERA = 0
    val CHOICE_GALLERY = 1
    fun displayPhotoDialog(context: Context, photoOptionListener: PhotoOptionListener) {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Take Photo") {
                photoOptionListener.onChoiceSelected(CHOICE_CAMERA)

            } else if (items[item] == "Choose from Library") {
                photoOptionListener.onChoiceSelected(CHOICE_GALLERY)

            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()

    }

    interface InviteDialogListener {
        fun onInviteClick()
        fun onLaterClick()
    }

    fun displayAppInviteDialog(context: Context, inviteDialogListener: InviteDialogListener) {

        val alertDialogBuilder = Builder(
                context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_app_invite, null)
        ButterKnife.bind(view)
        alertDialogBuilder.setView(view)

        val resultTextView = view.findViewById(R.id.inviteTextView) as TextView
        resultTextView.text = "Congratulations on your progress!!\nHow do you like the app so far?\n\n" + "Would you like to invite your friends in your journey of learning?"
        val imageView = view.findViewById(R.id.congratsImageView) as ImageView
        // set title
        Glide.with(context)
                .load(CONGRATS_GIF)
                .asGif()
                .into(imageView)
        alertDialogBuilder.setTitle("Invite Friends")


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.invite) { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    inviteDialogListener.onInviteClick()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.may_be_later) { dialogInterface, i ->
                    inviteDialogListener.onLaterClick()
                    dialogInterface.dismiss()
                }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.window!!.attributes.windowAnimations = R.style.AchievementDialogAnimation

        alertDialog.show()
    }

    fun displayInviteDialog(context: Context,
                            title: Int,
                            description: Int,
                            unlockByInviteInterface: UnlockByInviteInterface) {
        val preferenceString = context.getString(title).replace("\\s+".toRegex(), "")
        val creekPreferences = CreekApplication.creekPreferences
        val alertDialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(description)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it) { dialogInterface, i -> unlockByInviteInterface.onUnlockClick(i) }
                .setNeutralButton(R.string.dont_show_again) { dialogInterface, i ->
                    creekPreferences!!.setShowDialog(preferenceString, false)
                    dialogInterface.dismiss()
                    unlockByInviteInterface.onDismiss()
                }
                .create()
        if (creekPreferences!!.getShowDialog(preferenceString)) {
            alertDialog.show()
        } else {
            unlockByInviteInterface.onDismiss()
        }
    }

    fun displayInformation(context: Context,
                           title: Int,
                           description: Int,
                           onCancelListner: DialogInterface.OnDismissListener) {

        val preferenceString = context.getString(title).replace("\\s+".toRegex(), "")
        val creekPreferences = CreekApplication.creekPreferences
        val alertDialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(description)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it) { dialogInterface, i -> }
                .setNeutralButton(R.string.dont_show_again) { dialogInterface, i -> creekPreferences!!.setShowDialog(preferenceString, false) }
                .setOnDismissListener(onCancelListner)
                .create()
        if (creekPreferences!!.getShowDialog(preferenceString)) {
            alertDialog.show()
        } else {
            onCancelListner.onDismiss(object : DialogInterface {
                override fun cancel() {

                }

                override fun dismiss() {

                }
            })
        }

    }

    fun displayInformation(context: Context,
                           title: Int,
                           description: Int,
                           onClickListener: DialogInterface.OnClickListener,
                           onCancelListner: DialogInterface.OnDismissListener) {

        val preferenceString = context.getString(title).replace("\\s+".toRegex(), "")
        val creekPreferences = CreekApplication.creekPreferences
        val alertDialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(description)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it) { dialogInterface, i -> onClickListener.onClick(dialogInterface, i) }
                .setNeutralButton(R.string.dont_show_again) { dialogInterface, i -> creekPreferences!!.setShowDialog(preferenceString, false) }
                .setOnDismissListener(onCancelListner)
                .create()
        if (creekPreferences!!.getShowDialog(preferenceString)) {
            alertDialog.show()
        } else {
            onCancelListner.onDismiss(object : DialogInterface {
                override fun cancel() {

                }

                override fun dismiss() {

                }
            })
        }

    }

    fun displayInformation(context: Context,
                           title: String,
                           description: String,
                           onCancelListner: DialogInterface.OnDismissListener) {

        val preferenceString = title.replace("\\s+".toRegex(), "")
        val creekPreferences = CreekApplication.creekPreferences
        val alertDialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(description)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it) { dialogInterface, i -> }
                .setNeutralButton(R.string.dont_show_again) { dialogInterface, i -> creekPreferences!!.setShowDialog(preferenceString, false) }
                .setOnDismissListener(onCancelListner)
                .create()
        if (creekPreferences!!.getShowDialog(preferenceString)) {
            alertDialog.show()
        } else {
            onCancelListner.onDismiss(object : DialogInterface {
                override fun cancel() {

                }

                override fun dismiss() {

                }
            })
        }

    }

    fun scheduleImmediateNotification(context: Context, remoteMessage: RemoteMessage) {
        val notificationContent = generateRandomNotificationContent(context)
        val notification = generateNotification(context, "Did you know", notificationContent)
        val notificationIntent = Intent(context, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val delay = (1000 * 15).toLong()
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val creekPreferences = CreekApplication.creekPreferences
        if (!creekPreferences!!.isNotificationScheduled) {
            creekPreferences.isNotificationScheduled = true
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
        }
    }

    fun scheduleNotification(context: Context) {
        val notificationContent = generateRandomNotificationContent(context)
        val notification = generateNotification(context, "Did you know", notificationContent)
        val notificationIntent = Intent(context, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val delay = (1000 * 60 * 60 * 8).toLong()
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val creekPreferences = CreekApplication.creekPreferences
        if (!creekPreferences!!.isNotificationScheduled) {
            creekPreferences.isNotificationScheduled = true
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
        }
    }

    private fun generateRandomNotificationContent(context: Context): String {

        /** generateRandomNotificationContent : TODO On New Language */
        val cNotifications = ArrayList<String>()
        cNotifications.addAll(Arrays.asList(*context.resources.getStringArray(R.array.c_notifications_array)))
        val cppNotifications = ArrayList<String>()
        cppNotifications.addAll(Arrays.asList(*context.resources.getStringArray(R.array.cpp_notifications_array)))
        val javaNotifications = ArrayList<String>()
        javaNotifications.addAll(Arrays.asList(*context.resources.getStringArray(R.array.java_notifications_array)))
        val uspNotifications = ArrayList<String>()
        uspNotifications.addAll(Arrays.asList(*context.resources.getStringArray(R.array.usp_notifications_array)))
        val programLanguage = CreekApplication.creekPreferences!!.programLanguage
        var notification = ""
        val random = Random()
        when (programLanguage) {
            "c" -> notification = cNotifications[random.nextInt(cNotifications.size)]
            "java" -> notification = javaNotifications[random.nextInt(javaNotifications.size)]
            "cpp", "c++" -> notification = cppNotifications[random.nextInt(cppNotifications.size)]
            "usp" -> notification = uspNotifications[random.nextInt(uspNotifications.size)]
            "sql" -> notification = javaNotifications[random.nextInt(javaNotifications.size)]
            else -> notification = javaNotifications[random.nextInt(javaNotifications.size)]
        }

        return notification
    }


    fun displayUserProgramDialog(context: Context,
                                 title: String,
                                 message: String,
                                 userProgramDialogListener: UserProgramDialog.UserProgramDialogListener): AlertDialog {

        val alertDialogBuilder = Builder(
                context)

        // set title
        alertDialogBuilder.setTitle(title)

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Save") { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    userProgramDialogListener.onSave("")
                }
                .setNegativeButton("Preview") { dialog, which -> userProgramDialogListener.onPreview() }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.show()

        return alertDialog
    }

    fun displayAlert(title: String, message: String, context: Context) {

        val alertDialogBuilder = Builder(
                context)

        // set title
        alertDialogBuilder.setTitle(title)

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    dialog.cancel()
                }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.show()


    }

    fun splitProgramIntolines(programCode: String): ArrayList<String> {
        val lines = programCode.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ArrayList(Arrays.asList(*lines))
    }

    interface InputTextListener {
        fun onSuccess(text: String)
        fun onDismiss()
    }

    fun displayInputDialog(context: Context, title: String, message: String?, inputTextListener: InputTextListener) {
        val alertDialogBuilder = Builder(
                context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        ButterKnife.bind(view)
        val inputEditText = view.findViewById(R.id.inputEditText) as EditText
        val headerTextView = view.findViewById(R.id.headerTextView) as TextView
        headerTextView.text = title
        if (message != null) {
            inputEditText.setText(message)
        }
        alertDialogBuilder.setView(view)
        // set title
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    dialog.cancel()
                }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialogBuilder.setPositiveButton(R.string.done) { dialog, which ->
            inputTextListener.onSuccess(inputEditText.text.toString())
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }

    fun displayAchievementUnlockedDialog(context: Context, title: String, message: String) {

        val alertDialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message + ", Good work")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.got_it) { dialogInterface, i -> }
                .create()
        CreekApplication.creekPreferences!!.showInviteDialog = true
        alertDialog.window!!.attributes.windowAnimations = R.style.AchievementDialogAnimation
        alertDialog.show()
    }

    fun displayResultAlert(activity: Activity, title: String, message: String, pointsScore: Int, maxScore: Int) {

        val alertDialogBuilder = Builder(
                activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_result, null)
        ButterKnife.bind(view)
        alertDialogBuilder.setView(view)
        val progressBar = view.findViewById(R.id.progressBar) as ProgressBar
        val bigScoreTextView = view.findViewById(R.id.bigScoreTextView) as TextView
        val resultTextView = view.findViewById(R.id.resultTextView) as TextView
        bigScoreTextView.text = pointsScore.toString() + "/" + maxScore
        resultTextView.text = message
        progressBar.max = maxScore
        progressBar.progress = pointsScore
        // set title
        alertDialogBuilder.setTitle(title)


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    dialog.cancel()
                }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.show()

    }

    fun getProgramTitle(program_Index: Int, context: Context): String? {
        /*val program_indexHashMap = CreekApplication.instance.getProgram_indexHashMap()
        if (program_indexHashMap.containsKey(program_Index)) {
            return CreekApplication.instance.getProgram_indexHashMap().get(program_Index).getProgram_Description()
        }
        return null*/
        return "Program : " + program_Index + 1
    }

    fun generateBigTextNotification(context: Context, notificationTitle: String, notificationContent: String) {
        val noti = generateNotification(context, notificationTitle, notificationContent)
        val mNotificationId = notificationContent.hashCode()
        val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        noti.flags = noti.flags or Notification.FLAG_AUTO_CANCEL
        mNotifyMgr.notify(mNotificationId, noti)


    }

    private fun generateNotification(context: Context, notificationTitle: String, notificationContent: String): Notification {
        val resultIntent: Intent
        val stackBuilder = TaskStackBuilder.create(context)
        resultIntent = Intent(context, DashboardActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK
        stackBuilder.addNextIntent(resultIntent)
        val contentIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context)
                .setTicker(notificationTitle)
                .setStyle(NotificationCompat.BigTextStyle().bigText(notificationContent))
                .setContentTitle(notificationTitle)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setColor(Color.parseColor("#7B1FA2"))
                .setStyle(NotificationCompat.BigTextStyle().bigText(notificationContent))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
        builder.color = ContextCompat.getColor(context, R.color.colorPrimary)
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        return builder.build()
    }

    fun generateBigNotification(context: Context, notificationTitle: String, notificationContent: String) {

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/Infinite%20Programmer-feature-graphic.png?alt=media&token=7140ec19-5313-4c9d-8435-4f12a25cee34"
        generateImageNotification(context, notificationTitle, notificationContent, imageUrl)


    }

    //GIF : https://firebasestorage.googleapis.com/v0/b/creek-55ef6.appspot.com/o/congratulations.gif?alt=media&token=90c039af-2a96-465b-ad72-3df6f03b7c19

    fun generateImageNotification(context: Context, notificationTitle: String, notificationContent: String, imageUrl: String) {

        Glide
                .with(context)
                .load(imageUrl)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>(SimpleTarget.SIZE_ORIGINAL, SimpleTarget.SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        val resultIntent: Intent
                        val stackBuilder = TaskStackBuilder.create(context)
                        resultIntent = Intent(context, DashboardActivity::class.java)
                        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                        stackBuilder.addNextIntent(resultIntent)
                        val resultPendingIntent = PendingIntent.getActivity(context, 0,
                                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                        val builder = NotificationCompat.Builder(context)
                                .setTicker(notificationTitle)
                                .setStyle(NotificationCompat.BigTextStyle().bigText(notificationContent))
                                .setContentTitle(notificationTitle)
                                .setContentIntent(resultPendingIntent)

                        builder.setContentTitle(notificationTitle)
                                .setContentText(notificationContent)
                                .setColor(Color.parseColor("#7B1FA2"))
                                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                                .setSmallIcon(R.mipmap.ic_launcher)
                        builder.color = ContextCompat.getColor(context, R.color.colorPrimary)


                        val mNotificationId = notificationContent.hashCode()
                        val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        // hide the notification after its selected
                        val noti = builder.build()
                        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        //  noti.bigContentView = getComplexNotificationView(this, notificationTitle, notificationContent, "", model.getOrder().getDeliveryTime(), model.getOrder().getTotalQuantity(), model.getOrder().getDeliveryCharges());
                        noti.flags = noti.flags or Notification.FLAG_AUTO_CANCEL
                        mNotifyMgr.notify(mNotificationId, noti)
                    }
                })
    }

    val isNetworkAvailable: Boolean
        get() {
            val cm = CreekApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
            return isConnected
        }

    fun parseStringArray(context: Context, stringArrayResourceId: Int): HashMap<String, String> {
        val stringArray = context.resources.getStringArray(stringArrayResourceId)
        val outputArrayMap = HashMap<String, String>(stringArray.size)
        for (entry in stringArray) {
            val splitResult = entry.split("\\|".toRegex(), 2).toTypedArray()
            outputArrayMap.put(splitResult[0], splitResult[1])
        }
        return outputArrayMap
    }

    fun mapCodeToComments(context: Context, programCode: String): ArrayList<String> {
        val programComments = ArrayList<String>()
        val programLines = AuxilaryUtils.splitProgramIntolines(programCode)

        val commandExplanationMap = parseStringArray(context, R.array.c_command_map_array)
        Log.d(TAG, "programLines : " + programLines)
        Log.d(TAG, "mapCodeToComments : " + commandExplanationMap)
        for (programLine in programLines) {
            var explanation = programLine
            for (key in commandExplanationMap.keys) {
                if (explanation.contains(key)) {
                    explanation = explanation.replace(key.toRegex(), commandExplanationMap[key])
                   // explanation = explanation.replace(key.toRegex(), commandExplanationMap[key])
                }

            }
            if (explanation.contains("{")) {
                explanation = explanation.replace("\\{".toRegex(), "begin")
            }
            if (explanation.contains("}")) {
                explanation = explanation.replace("\\}".toRegex(), "end")
            }
            if (explanation.contains(";")) {
                explanation = explanation.replace(";".toRegex(), "")
            }
            if (!explanation.contains("==") && !explanation.contains("!=") && explanation.contains("=")) {
                explanation = "assign " + explanation
            }
            programComments.add(explanation)
        }
        return programComments

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun getFilePath(context: Context, uri: Uri): String {
        var currentApiVersion: Int
        try {
            currentApiVersion = android.os.Build.VERSION.SDK_INT
        } catch (e: NumberFormatException) {
            //API 3 will crash if SDK_INT is called
            currentApiVersion = 3
        }

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, arrayOf(id), null)

            val columnIndex = cursor!!.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        } else if (currentApiVersion <= Build.VERSION_CODES.HONEYCOMB_MR2 && currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null

            val cursorLoader = CursorLoader(
                    context,
                    uri, proj, null, null, null)
            val cursor = cursorLoader.loadInBackground()

            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(column_index)
            }
            return result!!
        } else {

            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
    }

}
