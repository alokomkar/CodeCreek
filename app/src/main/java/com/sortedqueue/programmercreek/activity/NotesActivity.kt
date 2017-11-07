package com.sortedqueue.programmercreek.activity

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.view.CanvasView

import java.io.IOException
import java.util.Locale

/**
 * Created by Alok on 03/04/17.
 */

class NotesActivity : AppCompatActivity() {
    private var mScreenDensity: Int = 0
    private var mProjectionManager: MediaProjectionManager? = null
    private var mMediaProjection: MediaProjection? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mMediaProjectionCallback: MediaProjectionCallback? = null
    private var mToggleButton: ToggleButton? = null
    private var mMediaRecorder: MediaRecorder? = null
    private var canvasView: CanvasView? = null

    fun clearCanvas(view: View) {
        canvasView!!.clearCanvas()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        canvasView = findViewById(R.id.canvasView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mScreenDensity = metrics.densityDpi

        mMediaRecorder = MediaRecorder()

        mProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        mToggleButton = findViewById(R.id.toggle)
        mToggleButton!!.setOnClickListener { v ->
            if (ContextCompat.checkSelfPermission(this@NotesActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(this@NotesActivity,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@NotesActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this@NotesActivity, Manifest.permission.RECORD_AUDIO)) {
                    mToggleButton!!.isChecked = false
                    Snackbar.make(findViewById(android.R.id.content), R.string.label_permissions,
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE"
                    ) {
                        ActivityCompat.requestPermissions(this@NotesActivity,
                                arrayOf(Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
                                REQUEST_PERMISSIONS)
                    }.show()
                } else {
                    ActivityCompat.requestPermissions(this@NotesActivity,
                            arrayOf(Manifest.permission
                                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
                            REQUEST_PERMISSIONS)
                }
            } else {
                onToggleScreenShare(v)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (requestCode == SPEECH_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            Log.d(TAG, "STT : " + results)
            CommonUtils.displayToast(this@NotesActivity, results.toString())
            return
        }

        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode)
            return
        }
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this,
                    "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show()
            mToggleButton!!.isChecked = false
            return
        }

        mMediaProjectionCallback = MediaProjectionCallback()
        mMediaProjection = mProjectionManager!!.getMediaProjection(resultCode, data)
        mMediaProjection!!.registerCallback(mMediaProjectionCallback, null)
        mVirtualDisplay = createVirtualDisplay()
        mMediaRecorder!!.start()
    }

    fun onToggleScreenShare(view: View) {
        if ((view as ToggleButton).isChecked) {
            CommonUtils.displayToast(this@NotesActivity, R.string.start_recording)
            initRecorder()
            shareScreen()
            //openVoiceIntent();
        } else {
            mMediaRecorder!!.stop()
            mMediaRecorder!!.reset()
            Log.v(TAG, "Stopping Recording")
            CommonUtils.displayToast(this@NotesActivity, R.string.stop_recording)
            stopScreenSharing()
        }
    }

    private fun openVoiceIntent() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, SPEECH_REQUEST)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            AuxilaryUtils.displayAlert("No Voice App", "Your device doesn't support voice input - no app found", this@NotesActivity)
        }

    }


    private fun shareScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager!!.createScreenCaptureIntent(), REQUEST_CODE)
            return
        }
        mVirtualDisplay = createVirtualDisplay()
        mMediaRecorder!!.start()
    }

    private fun createVirtualDisplay(): VirtualDisplay {
        return mMediaProjection!!.createVirtualDisplay("NotesActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder!!.surface, null, null
                /*Handler*/)/*Callbacks*/
    }

    private fun initRecorder() {
        try {
            mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mMediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mMediaRecorder!!.setOutputFile(Environment
                    .getExternalStoragePublicDirectory(Environment
                            .DIRECTORY_DOWNLOADS).toString() + "/video.mp4")
            mMediaRecorder!!.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            mMediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mMediaRecorder!!.setVideoEncodingBitRate(512 * 1000)
            mMediaRecorder!!.setVideoFrameRate(30)
            val rotation = windowManager.defaultDisplay.rotation
            val orientation = ORIENTATIONS.get(rotation + 90)
            mMediaRecorder!!.setOrientationHint(orientation)
            mMediaRecorder!!.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private inner class MediaProjectionCallback : MediaProjection.Callback() {
        override fun onStop() {
            if (mToggleButton!!.isChecked) {
                mToggleButton!!.isChecked = false
                mMediaRecorder!!.stop()
                mMediaRecorder!!.reset()
                Log.v(TAG, "Recording Stopped")
            }
            mMediaProjection = null
            stopScreenSharing()
        }
    }

    private fun stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return
        }
        mVirtualDisplay!!.release()
        //mMediaRecorder.release(); //If used: mMediaRecorder object cannot
        // be reused again
        destroyMediaProjection()
    }

    public override fun onDestroy() {
        super.onDestroy()
        destroyMediaProjection()
    }

    private fun destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection!!.unregisterCallback(mMediaProjectionCallback)
            mMediaProjection!!.stop()
            mMediaProjection = null
        }
        Log.i(TAG, "MediaProjection Stopped")
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                if (grantResults.size > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onToggleScreenShare(mToggleButton!!)
                } else {
                    mToggleButton!!.isChecked = false
                    Snackbar.make(findViewById(android.R.id.content), R.string.label_permissions,
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE"
                    ) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        intent.data = Uri.parse("package:" + packageName)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        startActivity(intent)
                    }.show()
                }
                return
            }
        }
    }

    companion object {

        private val TAG = "NotesActivity"
        private val REQUEST_CODE = 1000
        private val DISPLAY_WIDTH = 720
        private val DISPLAY_HEIGHT = 1280
        private val ORIENTATIONS = SparseIntArray()
        private val REQUEST_PERMISSIONS = 10

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }

        //http://www.truiton.com/2014/06/android-speech-recognition-without-dialog-custom-activity/
        private val SPEECH_REQUEST = 9878
    }
}
