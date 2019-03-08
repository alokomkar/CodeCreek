package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.ProgramLanguageAdapter
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.dashboard.DashboardNavigationListener
import com.sortedqueue.programmercreek.database.firebase.FirebaseHelper
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekPreferences
import kotlinx.android.synthetic.main.fragment_language.*

import java.util.ArrayList




/**
 * Created by Alok on 02/01/17.
 */

class LanguageFragment : Fragment() {

    private var handler: Handler? = null

    private var creekPreferences: CreekPreferences? = null
    private var firebaseDatabaseHandler: FirebaseDatabaseHandler? = null
    private var dashboardNavigationListener: DashboardNavigationListener? = null
    private var programLanguages: ArrayList<ProgramLanguage>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseDatabaseHandler = FirebaseDatabaseHandler(context!!)
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout!!.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            getProgramLanguages()
        }
        creekPreferences = CreekApplication.creekPreferences
        getProgramLanguages()
        handler = Handler()
        animateProgress()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            animateProgress()
        }
    }

    private fun getProgramLanguages() {
        swipeRefreshLayout!!.isRefreshing = true
        CommonUtils.displayProgressDialog(context, R.string.loading_languages)
        val totalLocalLanguages = creekPreferences!!.totalLanguages
        if( totalLocalLanguages == 0 && !AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBar(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { initDB() })
            CommonUtils.displayToast(context!!, R.string.enable_internet_to_download)
            return
        }
        firebaseDatabaseHandler!!.getAllProgramLanguages(object : FirebaseDatabaseHandler.GetProgramLanguageListener {
            override fun onSuccess(programLanguages: ArrayList<ProgramLanguage>) {
                /*ProgramLanguage programLanguage = new ProgramLanguage();
                programLanguage.setLanguageId("Java2");
                programLanguage.setProgramLanguage("Java Programming - II");
                programLanguage.setDescription("Explore more concepts of Java");
                programLanguages.add(programLanguage);*/
                setupRecyclerView(programLanguages)
                CommonUtils.dismissProgressDialog()
            }

            override fun onError(databaseError: DatabaseError) {
                swipeRefreshLayout!!.isRefreshing = false
                CommonUtils.dismissProgressDialog()
                CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
            }
        })
    }

    private fun setupRecyclerView(programLanguages: ArrayList<ProgramLanguage>) {
        this.programLanguages = programLanguages
        programLanguageRecyclerView!!.layoutManager = LinearLayoutManager(context)
        programLanguageRecyclerView!!.adapter = ProgramLanguageAdapter(context!!,
                programLanguages,
                object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
                    override fun onItemClick(position: Int) {
                        selectAndInitDb(position)
                    }
                })
        swipeRefreshLayout!!.isRefreshing = false
        CommonUtils.dismissProgressDialog()
        var selectedPosition = -1
        val selectedLanguage = creekPreferences!!.programLanguage

        for (programLanguage in programLanguages) {
            selectedPosition++
            val language = programLanguage.languageId
            if (language.equals(selectedLanguage, ignoreCase = true)) {
                languageSelectionTextView!!.text = programLanguage.programLanguage
            }
        }
        initUserValues()

    }


    private fun initUserValues() {
        val requestOptions = RequestOptions()
        requestOptions.optionalFitCenter()
        requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA)
        requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA)
        requestOptions.placeholder(R.color.colorPrimary)
        requestOptions.fallback(R.color.md_grey_500)
        Glide.with(context!!)
                .setDefaultRequestOptions(requestOptions)
                .load(creekPreferences!!.getAccountPhoto())
                .into(profileImageView!!)
        nameTextView!!.text = creekPreferences!!.getAccountName()
        if (creekPreferences == null) {
            creekPreferences = CreekApplication.creekPreferences
        }
        if (creekPreferences!!.creekUserStats != null) {
            val level = creekPreferences!!.creekUserStats!!.creekUserReputation / 100
            if (level > 0) {
                nameTextView!!.text = creekPreferences!!.getAccountName()
                nameTextView!!.append("\nLevel " + level)
            }
        }
        profileImageView!!.setOnClickListener { dashboardNavigationListener!!.hideLanguageFragment() }
        getFirebaseDBVerion()
    }

    fun getFirebaseDBVerion() {
        //firebaseDatabaseHandler.writeCreekUserDB( new CreekUserDB() );
        //CommonUtils.displayProgressDialog(DashboardActivity.this, "Checking for updates");
        if (creekPreferences!!.creekUserDB == null && !AuxilaryUtils.isNetworkAvailable ) {
            CommonUtils.displaySnackBar(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { getFirebaseDBVerion() })
            CommonUtils.displayToast(context!!, R.string.enable_internet_to_download)
            return
        }
        swipeRefreshLayout!!.isRefreshing = true
        firebaseDatabaseHandler!!.readCreekUserDB(object : FirebaseDatabaseHandler.GetCreekUserDBListener {
            override fun onSuccess(creekUserDB: CreekUserDB) {
                swipeRefreshLayout!!.isRefreshing = false
                selectedLanguageCardView!!.visibility = if (creekPreferences!!.programLanguage == "") View.GONE else View.VISIBLE
                if (dashboardNavigationListener != null) {
                    dashboardNavigationListener!!.showInviteDialog()
                }
                //selectAndInitDb(0);
            }

            override fun onError(databaseError: DatabaseError) {
                swipeRefreshLayout!!.isRefreshing = false
                Log.d("LanguageFragment", databaseError.message)
                databaseError.toException().printStackTrace()
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun initDB() {
        logDebugMessage("Inserting all Programs Titles..")
        if (!creekPreferences!!.checkProgramIndexUpdate()) {
            if (!AuxilaryUtils.isNetworkAvailable) {
                CommonUtils.displaySnackBar(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { initDB() })
                CommonUtils.displayToast(context!!, R.string.enable_internet_to_download)
                return
            }
            FirebaseHelper( context!!, dashboardNavigationListener!! )
        } else {
            dashboardNavigationListener!!.hideLanguageFragment()
        }
    }

    private fun logDebugMessage(message: String) {
        Log.d("LFragment", message)
    }

    private fun selectAndInitDb(position: Int) {

        val selectedString = programLanguages!![position].languageId
        languageSelectionTextView!!.text = programLanguages!![position].programLanguage
        creekPreferences!!.programLanguage = selectedString.toLowerCase()
        creekPreferences!!.isProgramsOnly = (programLanguages!![position].isProgramsOnly.equals("true", ignoreCase = true))
        selectedLanguageCardView!!.visibility = if (creekPreferences!!.programLanguage == "") View.GONE else View.VISIBLE
        initDB()
    }

    override fun onDetach() {
        super.onDetach()
        this.dashboardNavigationListener = null
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DashboardNavigationListener) {
            this.dashboardNavigationListener = context
            dashboardNavigationListener!!.calculateReputation()
        }
    }

    private var progressBarStatus: Int = 0

    fun animateProgress() {

        if (reputationProgressBar != null) {

            if (handler == null) {
                handler = Handler()
            }
            if (creekPreferences == null) {
                creekPreferences = CreekApplication.creekPreferences
            }
            val creekUserStats = creekPreferences!!.creekUserStats
            if (creekUserStats == null) {
                reputationProgressBar!!.visibility = View.GONE
                reputationTextView!!.visibility = View.GONE
                return
            }
            val progress = creekUserStats.creekUserReputation % 100
            if (progress > 0) {
                reputationProgressBar!!.visibility = View.VISIBLE
                reputationTextView!!.visibility = View.VISIBLE
                progressBarStatus = progress
                if (reputationProgressBar != null) {
                    reputationProgressBar!!.progress = progressBarStatus
                    reputationTextView!!.text = progressBarStatus.toString() + "% Complete"
                    val level = creekPreferences!!.creekUserStats!!.creekUserReputation / 100
                    if (level > 0) {
                        nameTextView!!.text = creekPreferences!!.getAccountName()
                        nameTextView!!.append("\nLevel " + level)
                    }
                }
            } else {
                reputationProgressBar!!.visibility = View.GONE
                reputationTextView!!.visibility = View.GONE
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var indexFragment: LanguageFragment? = null
        val instance: LanguageFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = LanguageFragment()
                }
                return indexFragment!!
            }
    }
}