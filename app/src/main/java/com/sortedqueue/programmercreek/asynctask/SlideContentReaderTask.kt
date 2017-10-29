package com.sortedqueue.programmercreek.asynctask

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.sortedqueue.programmercreek.constants.TYPE_SINGLE_RIGHT
import com.sortedqueue.programmercreek.constants.TYPE_TRUE_FALSE

import com.sortedqueue.programmercreek.database.InterviewQuestionModel
import com.sortedqueue.programmercreek.database.OptionModel
import com.sortedqueue.programmercreek.util.CommonUtils

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Scanner
import java.util.regex.Pattern

/**
 * Created by Alok on 27/07/17.
 */

class SlideContentReaderTask(private val context: Context, private val fileId: String, //Interface to communicate back the response to UI
                             private val onDataReadListener: OnDataReadListener?) : AsyncTask<Void, Void, Void>() {

    private var contentText: String? = null
    private val contentArrayList: ArrayList<InterviewQuestionModel>
    private val TAG = SlideContentReaderTask::class.java.simpleName

    interface OnDataReadListener {
        fun onDataReadComplete(contentArrayList: ArrayList<InterviewQuestionModel>)
    }

    //To display progress Dialog
    private val progressDialog: ProgressDialog? = null

    init {
        this.contentArrayList = ArrayList<InterviewQuestionModel>()
    }

    override fun doInBackground(vararg voids: Void): Void? {
        readFileAsList(context)
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
        CommonUtils.displayProgressDialog(context, "Loading questions")
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        CommonUtils.dismissProgressDialog()
        onDataReadListener?.onDataReadComplete(contentArrayList)
    }

    private fun readFileAsList(context: Context) {
        var line: String
        readFileFromRawDirectory(context.resources.getIdentifier(fileId,
                "raw", context.packageName))
        val inputStream = context.resources.openRawResource(
                context.resources.getIdentifier(fileId,
                        "raw", context.packageName))
        val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        contentText = ""
        var interviewQuestionModel: InterviewQuestionModel? = null
        val sc = Scanner(inputStream, "UTF-8")
        while (sc.hasNext()) {
            line = sc.nextLine()
            Log.d(TAG, "Read Line : " + line)
            if (line.contains("<question>")) {

                if (interviewQuestionModel != null) {
                    contentArrayList.add(interviewQuestionModel)
                    Log.d(TAG, "Question : " + interviewQuestionModel.question)
                }


                Log.d(TAG, "Forming question")
                interviewQuestionModel = InterviewQuestionModel()
                var question = ""
                line = sc.nextLine()
                while ((line) != "</>") {
                    question += line + "\n"
                    line = sc.nextLine()
                }
                if (sc.hasNext())
                    line = sc.nextLine()
                interviewQuestionModel.question = question


            }
            if (line.contains("<code>")) {
                var code = ""
                line = sc.nextLine()
                while ((line) != "</>") {
                    code += line + "\n"
                    line = sc.nextLine()
                }
                if (sc.hasNext())
                    line = sc.nextLine()
                if (interviewQuestionModel != null)
                    interviewQuestionModel.code = code
            }
            if (line.contains("<option>")) {
                line = sc.nextLine()
                while (sc.hasNext() && (line) != "</>") {
                    if (line.startsWith("a)")) {
                        val optionA = line.split(Pattern.quote("a)").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        interviewQuestionModel!!.optionModels = ArrayList<OptionModel>()
                        interviewQuestionModel.optionModels.add(OptionModel(1, optionA))
                        interviewQuestionModel.typeOfQuestion = TYPE_TRUE_FALSE
                        Log.d(TAG, "Options : " + optionA)
                        if (sc.hasNext())
                            line = sc.nextLine()
                    }
                    if (line.startsWith("b)")) {
                        val optionB = line.split(Pattern.quote("b)").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        if (sc.hasNext())
                            line = sc.nextLine()
                        interviewQuestionModel!!.optionModels.add(OptionModel(2, optionB))
                        interviewQuestionModel.typeOfQuestion = TYPE_TRUE_FALSE
                        Log.d(TAG, "Options : " + optionB)
                    }
                    if (line.startsWith("c)")) {
                        val optionC = line.split(Pattern.quote("c)").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        if (sc.hasNext())
                            line = sc.nextLine()
                        interviewQuestionModel!!.optionModels.add(OptionModel(3, optionC))
                        interviewQuestionModel.typeOfQuestion = TYPE_SINGLE_RIGHT
                        Log.d(TAG, "Options : " + optionC)
                    }
                    if (line.startsWith("d)")) {
                        val optionD = line.split(Pattern.quote("d)").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        interviewQuestionModel!!.optionModels.add(OptionModel(4, optionD))
                        interviewQuestionModel.typeOfQuestion = TYPE_SINGLE_RIGHT
                        Log.d(TAG, "Options : " + optionD)
                    }
                    line = sc.nextLine()
                }
                if (sc.hasNext())
                    line = sc.nextLine()
            }
            if (line.startsWith("Answer:")) {
                val answer = line.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }
                when (answer) {
                    "a" -> interviewQuestionModel!!.correctOption = 1
                    "b" -> interviewQuestionModel!!.correctOption = 2
                    "c" -> interviewQuestionModel!!.correctOption = 3
                    "d" -> interviewQuestionModel!!.correctOption = 4
                }
            }
            if (line.contains("<output>")) {
                var output = ""
                line = sc.nextLine()
                while (sc.hasNext() && (line) != "</>") {
                    output += line + "\n"
                    line = sc.nextLine()
                }
                if (sc.hasNext())
                    line = sc.nextLine()
                if (interviewQuestionModel != null)
                    interviewQuestionModel.output = output
            }
            if (line.contains("<Explanation>")) {
                var explanation = ""
                line = sc.nextLine()
                while (sc.hasNext() && (line) != "</>") {
                    explanation += line + "\n"
                    line = sc.nextLine()
                }
                if (sc.hasNext())
                    line = sc.nextLine()
                if (interviewQuestionModel != null)
                    interviewQuestionModel.explanation = explanation
            }

        }
        if (interviewQuestionModel != null) {
            contentArrayList.add(interviewQuestionModel)
            Log.d(TAG, "Question : " + interviewQuestionModel.question)
        }


    }

    private fun readFileFromRawDirectory(resourceId: Int): String {
        val iStream = context.resources.openRawResource(resourceId)
        println(iStream)
        try {

            var count = 0
            val bytes = ByteArray(iStream.available())
            val builder = StringBuilder()
            count = iStream.read(bytes, 0, iStream.available())
            while ((count) > 0) {
                builder.append(String(bytes, 0, count))
                count = iStream.read(bytes, 0, iStream.available())
            }
            iStream.close()

            return builder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

    }


}
