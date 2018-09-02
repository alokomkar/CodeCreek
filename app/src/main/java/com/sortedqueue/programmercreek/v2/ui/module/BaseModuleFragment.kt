package com.sortedqueue.programmercreek.v2.ui.module

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.View
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import kotlinx.android.synthetic.main.fragment_new_module.*


abstract class BaseModuleFragment : BaseFragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if( context != null )
            mPreferencesAPI = PCPreferences( context )
    }

    fun onProgressStatsUpdate(points: Int) {
        progressLayout!!.visibility = View.VISIBLE
        animateProgress(points)
    }

    private var handler : Handler ?= null
    private var progressBarStatus: Int = 0

    @SuppressLint("SetTextI18n")
    private fun animateProgress(points: Int) {
        try {
            if (reputationProgressBar != null) {

                if (handler == null) {
                    handler = Handler()
                }

                val progress = 50
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
                                    reputationTextView!!.text = "You've gained " + points + "xp\n" + progressBarStatus + "% Complete : Level : " + level
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