package com.sortedqueue.programmercreek.util

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.LinkedList

import okhttp3.ResponseBody

/**
 * Created by Alok on 12/04/17.
 */

object FileUtils {

    val PERMISSION_REQUEST = 9999

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri!!, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.lastPathSegment
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)
        return null
    }

    fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }



    fun checkSelfPermission(activity: AppCompatActivity, permissions: Array<String>): Boolean {
        val deniedPermissions = checkDeniedPermissions(activity, permissions)
        if (deniedPermissions.size == 0) {
            return true
        } else {
            ActivityCompat.requestPermissions(activity, deniedPermissions, PERMISSION_REQUEST)
            return false
        }

    }

    fun checkDeniedPermissions(activity: AppCompatActivity, permissions: Array<String>): Array<String> {
        val deniedPermissions = ArrayList<String>()
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions.toTypedArray()
    }

    interface DownloadFileListner {
        fun onSuccess(fileUrl: File)
    }

    fun writeResponseBodyToDisk(context: Context, body: ResponseBody, htmlFile: String): Boolean {
        try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(context.getExternalFilesDir(null).toString() + File.separator + "Sample.html")
            val downloadFileListner = context as DownloadFileListner

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    Log.d(TAG, "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()
                downloadFileListner.onSuccess(futureStudioIconFile)
                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return false
        }

    }


    private val TAG = "MD5"

    fun checkMD5(md5: String, updateFile: File?): Boolean {
        if (TextUtils.isEmpty(md5) || updateFile == null) {
            Log.e(TAG, "MD5 string empty or updateFile null")
            return false
        }

        val calculatedDigest = calculateMD5(updateFile)
        if (calculatedDigest == null) {
            Log.e(TAG, "calculatedDigest null")
            return false
        }

        Log.v(TAG, "Calculated digest: " + calculatedDigest)
        Log.v(TAG, "Provided digest: " + md5)

        return calculatedDigest.equals(md5, ignoreCase = true)
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                    .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun calculateMD5(updateFile: File): String? {
        val digest: MessageDigest
        try {
            digest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Exception while getting digest", e)
            return null
        }

        val inputStream: InputStream
        try {
            inputStream = FileInputStream(updateFile)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Exception while getting FileInputStream", e)
            return null
        }

        val buffer = ByteArray(8192)
        var read: Int
        try {
            read = inputStream.read(buffer)
            while ( read > 0) {
                digest.update(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            val md5sum = digest.digest()
            val bigInt = BigInteger(1, md5sum)
            var output = bigInt.toString(16)
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0')
            return output
        } catch (e: IOException) {
            throw RuntimeException("Unable to process file for MD5", e)
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                Log.e(TAG, "Exception on closing MD5 input stream", e)
            }

        }
    }


}
