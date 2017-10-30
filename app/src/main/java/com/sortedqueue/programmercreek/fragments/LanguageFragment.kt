package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.ProgramLanguageAdapter
import com.sortedqueue.programmercreek.database.CreekUserDB
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramLanguage
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_language, container, false)


        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { getProgramLanguages() })
            return
        }
        swipeRefreshLayout!!.isRefreshing = true
        val firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
        firebaseDatabaseHandler.getAllProgramLanguages(object : FirebaseDatabaseHandler.GetProgramLanguageListener {
            override fun onSuccess(programLanguages: ArrayList<ProgramLanguage>) {
                /*ProgramLanguage programLanguage = new ProgramLanguage();
                programLanguage.setLanguageId("Java2");
                programLanguage.setProgramLanguage("Java Programming - II");
                programLanguage.setDescription("Explore more concepts of Java");
                programLanguages.add(programLanguage);*/
                setupRecyclerview(programLanguages)
            }

            override fun onError(databaseError: DatabaseError) {
                swipeRefreshLayout!!.isRefreshing = false
                CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
            }
        })
    }

    private fun setupRecyclerview(programLanguages: ArrayList<ProgramLanguage>) {
        this.programLanguages = programLanguages
        programLanguageRecyclerView!!.layoutManager = LinearLayoutManager(context)
        programLanguageRecyclerView!!.adapter = ProgramLanguageAdapter(context,
                programLanguages,
                object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
                    override fun onItemClick(position: Int) {
                        selectAndInitDb(position)
                    }
                })
        swipeRefreshLayout!!.isRefreshing = false
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
        Glide.with(context)
                .load(creekPreferences!!.getAccountPhoto())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
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
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { getFirebaseDBVerion() })
            return
        }
        swipeRefreshLayout!!.isRefreshing = true
        firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
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
            firebaseDatabaseHandler = FirebaseDatabaseHandler(context)
            firebaseDatabaseHandler!!.initializeProgramIndexes(object : FirebaseDatabaseHandler.ProgramIndexInterface {
                override fun getProgramIndexes(program_indices: ArrayList<ProgramIndex>) {
                    dashboardNavigationListener!!.hideLanguageFragment()
                }

                override fun onError(error: DatabaseError) {

                }
            })
        } else {
            dashboardNavigationListener!!.hideLanguageFragment()
        }
    }

    private fun logDebugMessage(message: String) {
        Log.d("LFragment", message)
    }

    private fun selectAndInitDb(position: Int) {
        if (!AuxilaryUtils.isNetworkAvailable) {
            CommonUtils.displaySnackBarIndefinite(activity, R.string.internet_unavailable, R.string.retry, View.OnClickListener { selectAndInitDb(position) })
            return
        }
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
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (progressBarStatus = 0; progressBarStatus <= progress; progressBarStatus++) {

                            handler.post(new Runnable() {
                                public void run() {
                                    if( reputationProgressBar != null ) {
                                        reputationProgressBar.setProgress(progressBarStatus);
                                        reputationTextView.setText(progressBarStatus +"% Complete");
                                        int level = creekPreferences.getCreekUserStats().getCreekUserReputation() / 100;
                                        if (level > 0) {
                                            nameTextView.setText(creekPreferences.getAccountName());
                                            nameTextView.append("\nLevel " + level);
                                        }
                                    }

                                }
                            });


                            try {
                                Thread.sleep(40);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }).start();*/
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