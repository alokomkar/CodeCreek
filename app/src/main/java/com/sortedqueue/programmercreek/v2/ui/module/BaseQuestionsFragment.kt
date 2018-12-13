package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import com.sortedqueue.programmercreek.v2.base.BasePreferencesAPI
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import kotlinx.android.synthetic.main.fragment_module_questions.*

abstract class BaseQuestionsFragment : Fragment() {

    protected lateinit var mPreferencesAPI : BasePreferencesAPI

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if( context != null )
            mPreferencesAPI = PCPreferences( context )
    }

    fun onProgressStatsUpdate(points: Int) {
        progressLayout!!.visibility = View.VISIBLE
        animateProgress(points)
    }

    private var handler : Handler?= null
    private var progressBarStatus: Int = 0

    @SuppressLint("SetTextI18n")
    private fun animateProgress(points: Int) {
        reputationProgressBar.isIndeterminate = false
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = Handler()
                }

                val progress = 70
                reputationProgressBar!!.visibility = View.VISIBLE
                reputationTextView!!.visibility = View.VISIBLE
                val runnable = Runnable {
                    progressBarStatus = 0
                    while (progressBarStatus <= progress) {

                        handler!!.post {
                            if (reputationProgressBar != null) {
                                reputationProgressBar!!.progress = progressBarStatus

                                reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete"
                                val level = 250 / 100
                                if (level > 0) {
                                    completionTextView?.text = "$progressBarStatus%"
                                    reputationTextView!!.text = "Complete : Level : " + level
                                }
                            }
                        }
                        try {
                            Thread.sleep(40)
                        } catch (ex: Exception) {
                        }

                        progressBarStatus++
                    }
                    handler!!.postDelayed({ progressLayout!!.visibility = View.GONE }, 1500)
                }
                Thread(runnable).start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (progressLayout != null) {
                progressLayout!!.visibility = View.GONE
            }
        }

    }
}