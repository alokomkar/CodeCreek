package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.ModulesRecyclerViewAdapter
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2016-12-25.
 */

class ModuleFragment : Fragment() {

    @BindView(R.id.modulesRecyclerView)
    internal var modulesRecyclerView: RecyclerView? = null
    @BindView(R.id.adView)
    internal var adView: AdView? = null

    private var syntaxNavigationListener: SyntaxNavigationListener? = null
    private var languageModules: ArrayList<LanguageModule>? = null
    private val TAG = ModuleFragment::class.java.simpleName
    private var moduleRecyclerAdapter: ModulesRecyclerViewAdapter? = null
    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_module, container, false)
        ButterKnife.bind(this, view)
        getModules()
        if (!CreekApplication.creekPreferences!!.isPremiumUser) {
            initAds()
        }
        return view
    }

    private fun initAds() {
        /*if(CreekApplication.getPreferences().getAdsEnabled())*/ run {
            MobileAds.initialize(context, getString(R.string.mobile_banner_id))
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            val adRequest = AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build()
            adView!!.loadAd(adRequest)
            adView!!.visibility = View.GONE
            adView!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adView!!.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SyntaxNavigationListener) {
            syntaxNavigationListener = context
        }
    }

    override fun onDetach() {
        syntaxNavigationListener = null
        super.onDetach()
    }

    private fun getModules() {
        modulesRecyclerView!!.visibility = View.INVISIBLE
        CommonUtils.displayProgressDialog(context, "Loading modules")
        FirebaseDatabaseHandler(context).initializeModules(object : FirebaseDatabaseHandler.ModuleInterface {
            override fun getModules(languageModules: ArrayList<LanguageModule>) {
                setupRecyclerView(languageModules)
            }

            override fun onError(error: DatabaseError) {
                Log.e(TAG, "Error : " + error.message + " : Details : " + error.details)
                CommonUtils.dismissProgressDialog()
            }
        })
    }

    private fun setupRecyclerView(languageModulesList: ArrayList<LanguageModule>) {
        this.languageModules = languageModulesList
        modulesRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        moduleRecyclerAdapter = ModulesRecyclerViewAdapter(context, languageModules!!, object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
            override fun onItemClick(position: Int) {
                if (position + 1 < languageModules!!.size) {
                    syntaxNavigationListener!!.onModuleLoad(languageModules!![position], languageModules!![position + 1])
                } else {
                    syntaxNavigationListener!!.onModuleLoad(languageModules!![position], null)
                }

            }
        })
        modulesRecyclerView!!.adapter = moduleRecyclerAdapter
        CommonUtils.dismissProgressDialog()
        Handler().postDelayed({ AnimationUtils.slideInToLeft(modulesRecyclerView) }, 450)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        if (moduleRecyclerAdapter != null) {
            moduleRecyclerAdapter!!.resetAdapter()
        }
    }
}
