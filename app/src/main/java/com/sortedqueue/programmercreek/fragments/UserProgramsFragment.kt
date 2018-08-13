package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.ProgramActivity
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.adapter.UserProgramRecyclerAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.UserProgramDetails
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.dashboard.DashboardNavigationListener
import kotlinx.android.synthetic.main.fragment_user_programs.*

import java.util.ArrayList




/**
 * Created by Alok on 16/05/17.
 */

class UserProgramsFragment : Fragment(), UserProgramRecyclerAdapter.UserProgramClickListener, FirebaseDatabaseHandler.GetAllUserProgramsListener, AdapterView.OnItemSelectedListener {

    private var adapter: UserProgramRecyclerAdapter? = null
    private var accessSpecifier: String? = null
    private var dashboardNavigationListener: DashboardNavigationListener? = null

    private val TAG = UserProgramsFragment::class.java.simpleName
    private var language = "All"

    private val businessType = arrayOf("All", "C", "C++", "Java")
    internal var adapterBusinessType: ArrayAdapter<String> ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_user_programs, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //language = getArguments().getString("Language");
        allProgramsRadioButton!!.isChecked = true
        allProgramsRadioButton!!.setOnCheckedChangeListener(checkChangedListener)
        myProgramsRadioButton!!.setOnCheckedChangeListener(checkChangedListener)
        adapterBusinessType = ArrayAdapter(context,
                R.layout.item_language_select, businessType)
        languageSpinner!!.adapter = adapterBusinessType
        languageSpinner!!.onItemSelectedListener = this
        //myFavoritesRadioButton.setOnCheckedChangeListener(checkChangedListener);
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout!!.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            fetchUserPrograms(accessSpecifier!!)
        }
        fetchUserPrograms("All programs")
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
    }

    private fun initAds() {
        /*if(CreekApplication.getPreferences().getAdsEnabled())*/ run {
            MobileAds.initialize(context, getString(R.string.mobile_banner_id))
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            val adRequest = AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build()
            if( adView != null ) {
                adView!!.loadAd(adRequest)
                adView!!.visibility = View.GONE
                adView!!.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        if( adView != null ) adView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private val checkChangedListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when (buttonView.id) {
            R.id.allProgramsRadioButton -> if (allProgramsRadioButton!!.isChecked) {
                languageSpinner!!.isEnabled = true
                fetchUserPrograms("All Programs")
            }
            R.id.myProgramsRadioButton -> if (myProgramsRadioButton!!.isChecked) {
                languageSpinner!!.isEnabled = false
                fetchUserPrograms("My Programs")
            }
        }/*case R.id.myFavoritesRadioButton :
                    if( myFavoritesRadioButton.isChecked() ) {
                        fetchUserPrograms("Favorites");
                    }
                    break;*/
    }

    private fun fetchUserPrograms(accessSpecifier: String) {
        this.accessSpecifier = accessSpecifier
        swipeRefreshLayout!!.isRefreshing = true
        FirebaseDatabaseHandler(context!!).getAllUserPrograms(accessSpecifier, language, this)
    }

    private fun setupRecyclerView(userProgramDetailsArrayList: ArrayList<UserProgramDetails>) {
        userProgramsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        adapter = UserProgramRecyclerAdapter(context!!, accessSpecifier!!, userProgramDetailsArrayList, this)
        userProgramsRecyclerView!!.adapter = adapter
        noProgramsLayout!!.visibility = View.GONE
        userProgramsRecyclerView!!.visibility = View.VISIBLE
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSuccess(userProgramDetailsArrayList: ArrayList<UserProgramDetails>) {
        setupRecyclerView(userProgramDetailsArrayList)
    }

    override fun onError(databaseError: DatabaseError?) {
        swipeRefreshLayout!!.isRefreshing = false
        noProgramsLayout!!.visibility = View.VISIBLE
        userProgramsRecyclerView!!.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {

        val userProgramDetails = adapter!!.getItemAtPosition(position)
        userProgramDetails.views = userProgramDetails.views + 1
        adapter!!.notifyDataSetChanged()

        FirebaseDatabaseHandler(context!!).updateViewCount(userProgramDetails)
        val newIntentBundle = Bundle()
        var newIntent: Intent? = null
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true)
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, userProgramDetails.programIndex)
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1)
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, userProgramDetails.programIndex.program_Description)
        newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, userProgramDetails.programTables)
        newIntent = Intent(context, ProgramActivity::class.java)
        newIntent.putExtras(newIntentBundle)
        startActivity(newIntent)

    }

    override fun onLikeClicked(isLiked: Boolean, position: Int) {
        val userProgramDetails = adapter!!.getItemAtPosition(position)
        /*if( isLiked ) {
            userProgramDetails.save(new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : " + userProgramDetails.getProgramId());
                }
            });
            userProgramDetails.getProgramIndex().save(new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : Program Index saved : " + userProgramDetails.getProgramId());
                }
            });
            RushCore.getInstance().save(userProgramDetails.getProgramTables(), new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : Program Tables saved : " + userProgramDetails.getProgramId());
                }
            });
        }
        else {

            new RushSearch().whereEqual("programId", userProgramDetails.getProgramId()).find(UserProgramDetails.class, new RushSearchCallback<UserProgramDetails>() {
                @Override
                public void complete(List<UserProgramDetails> list) {
                    for ( UserProgramDetails programDetails : list ) {
                        programDetails.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
            new RushSearch().whereEqual("userProgramId", userProgramDetails.getProgramId()).find(ProgramIndex.class, new RushSearchCallback<ProgramIndex>() {
                @Override
                public void complete(List<ProgramIndex> list) {
                    for ( ProgramIndex programIndex : list ) {
                        programIndex.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
            new RushSearch().whereEqual("userProgramId", userProgramDetails.getProgramId()).find(ProgramTable.class, new RushSearchCallback<ProgramTable>() {
                @Override
                public void complete(List<ProgramTable> list) {
                    for ( ProgramTable programTable : list ) {
                        programTable.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
        }*/
        FirebaseDatabaseHandler(context!!).updateLikes(isLiked, userProgramDetails)
        adapter!!.notifyDataSetChanged()
    }

    override fun onShareClicked(position: Int) {
        val userProgramDetails = adapter!!.getItemAtPosition(position)
        val shareString = userProgramDetails.shareString
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString + "\nCheck out this app : \n" + getString(R.string.app_url))
        startActivity(Intent.createChooser(shareIntent, "Share Program : " + userProgramDetails.programIndex.program_Description))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DashboardNavigationListener) {
            dashboardNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        dashboardNavigationListener = null
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        language = parent.selectedItem.toString()
        fetchUserPrograms(accessSpecifier!!)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var indexFragment: UserProgramsFragment? = null
        val instance: UserProgramsFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = UserProgramsFragment()
                }
                return indexFragment!!
            }

        fun newInstance(c: String): UserProgramsFragment {
            val userProgramsFragment = UserProgramsFragment()
            val bundle = Bundle()
            bundle.putString("Language", c)
            userProgramsFragment.arguments = bundle
            return userProgramsFragment
        }
    }
}
