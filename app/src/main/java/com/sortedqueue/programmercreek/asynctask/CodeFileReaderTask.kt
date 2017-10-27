package com.sortedqueue.programmercreek.asynctask

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * Created by Alok on 27/07/17.
 */

class CodeFileReaderTask(private val progressBar: ProgressBar?, private val context: Context, private val fileId: String, //Interface to communicate back the response to UI
                         private val onDataReadListener: OnDataReadListener?) : AsyncTask<Void, Void, Void>() {
    private var code: String? = null

    interface OnDataReadListener {
        fun onDataReadComplete(code: String)
    }

    override fun doInBackground(vararg voids: Void): Void? {
        readFileAsList(context)
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        if (progressBar != null) {
            progressBar.visibility = View.GONE
        }
        onDataReadListener?.onDataReadComplete(code!!)
    }

    private fun readFileAsList(context: Context) {
        var line: String
        try {

            val inputStream = context.resources.openRawResource(
                    context.resources.getIdentifier(fileId,
                            "raw", context.packageName))
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val bufferedReader = BufferedReader(inputStreamReader)
            code = ""
            line = bufferedReader.readLine()
            while (!(line.isNullOrEmpty())) {
                code += line + "\n"
                line = bufferedReader.readLine()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
