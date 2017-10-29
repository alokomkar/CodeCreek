package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ModulesRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.TopLearnersRecyclerAdapter
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.UserRanking
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener

import java.util.ArrayList
import java.util.Collections

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-02-12.
 */

class TopLearnerFragment : Fragment(), View.OnClickListener {

    @BindView(R.id.topLearnersRecyclerView)
    internal var topLearnersRecyclerView: RecyclerView? = null
    @BindView(R.id.swipeRefreshLayout)
    internal var swipeRefreshLayout: SwipeRefreshLayout? = null
    @BindView(R.id.facebookCardView)
    internal var facebookCardView: CardView? = null
    @BindView(R.id.googleCardView)
    internal var googleCardView: CardView? = null
    @BindView(R.id.twitterCardView)
    internal var twitterCardView: CardView? = null

    private val TWITTER_LINK = "https://twitter.com/Programmercreek"
    private val FACEBOOK_LINK = "https://www.facebook.com/Practice-Code-2073203816026988/"
    private val GOOGLE_PLUS_LINK = "https://plus.google.com/u/1/communities/117275222080442676688"
    @BindView(R.id.emptyTextView)
    internal var emptyTextView: TextView? = null

    private val syntaxNavigationListener: SyntaxNavigationListener? = null
    private val languageModules: ArrayList<LanguageModule>? = null
    private val TAG = ModuleFragment::class.java.simpleName
    private val moduleRecyclerAdapter: ModulesRecyclerViewAdapter? = null
    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_top_learners, container, false)
        ButterKnife.bind(this, view)
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout!!.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            calculateTopLearners()
        }

        calculateTopLearners()
        initListeners()
        return view
    }

    private fun initListeners() {
        facebookCardView!!.setOnClickListener(this)
        googleCardView!!.setOnClickListener(this)
        twitterCardView!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.googleCardView -> startIntent(GOOGLE_PLUS_LINK)
            R.id.facebookCardView -> startIntent(FACEBOOK_LINK)
            R.id.twitterCardView -> startIntent(TWITTER_LINK)
        }
    }

    private fun startIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun calculateTopLearners() {
        swipeRefreshLayout!!.isRefreshing = true
        FirebaseDatabaseHandler(context)
                .getTopLearners(
                        object : FirebaseDatabaseHandler.GetTopLearnersInterface {
                            override fun onSuccess(userRankings: ArrayList<UserRanking>) {
                                Collections.reverse(userRankings)
                                Log.d(TAG, "Top learners : " + userRankings)
                                setupAdapter(userRankings)
                                topLearnersRecyclerView!!.visibility = View.VISIBLE
                            }

                            override fun onFailure(databaseError: DatabaseError) {
                                topLearnersRecyclerView!!.visibility = View.GONE
                                swipeRefreshLayout!!.isRefreshing = false
                                swipeRefreshLayout!!.visibility = View.GONE
                                emptyTextView!!.visibility = View.VISIBLE
                            }
                        })
    }

    private fun setupAdapter(userRankings: ArrayList<UserRanking>) {
        topLearnersRecyclerView!!.layoutManager = LinearLayoutManager(context)
        topLearnersRecyclerView!!.adapter = TopLearnersRecyclerAdapter(context, userRankings)
        swipeRefreshLayout!!.isRefreshing = false
        if (userRankings.size == 0) {
            swipeRefreshLayout!!.visibility = View.GONE
            emptyTextView!!.visibility = View.VISIBLE
        } else {
            swipeRefreshLayout!!.visibility = View.VISIBLE
            emptyTextView!!.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var indexFragment: TopLearnerFragment? = null
        val instance: TopLearnerFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = TopLearnerFragment()
                }
                return indexFragment!!
            }
    }
}
