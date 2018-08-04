package com.sortedqueue.programmercreek.fragments


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.QuickReferencePagerAdapter
import com.sortedqueue.programmercreek.adapter.QuickRefernceRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter
import com.sortedqueue.programmercreek.database.QuickReference
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_quick_reference.*

import java.util.ArrayList





/**
 * Created by Alok on 04/08/17.
 */

class QuickReferenceFragment : Fragment(), CustomProgramRecyclerViewAdapter.AdapterClickListner, View.OnClickListener, ViewPager.OnPageChangeListener {


    private var tagsRecyclerAdapter: TagsRecyclerAdapter? = null
    private var selectedTag: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_quick_reference, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageRecyclerView!!.visibility = View.GONE
        headingTextView!!.text = "< Quick Reference"
        headingTextView!!.setOnClickListener { activity!!.onBackPressed() }
        fetchAllTags()
    }

    private fun fetchAllTags() {
        CommonUtils.displayProgressDialog(context, context!!.getString(R.string.loading))
        FirebaseDatabaseHandler(context!!).getAllTags(object : FirebaseDatabaseHandler.GetAllTagsListener {
            override fun onError(databaseError: DatabaseError) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onSuccess(tagModel: TagModel) {
                setupRecyclerView(tagModel)
            }
        })
    }

    private fun setupRecyclerView(tagModel: TagModel) {


        languageRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        tagsRecyclerAdapter = TagsRecyclerAdapter(tagModel.tagArrayList, 1, this)
        languageRecyclerView!!.adapter = tagsRecyclerAdapter
        tagsRecyclerAdapter!!.setSelectedTag("C")
        selectedTextView!!.text = "C"
        selectedTextView!!.setOnClickListener(this)
        onItemClick(0)
        CommonUtils.dismissProgressDialog()
    }

    @SuppressLint("StaticFieldLeak")
    private fun getAllReference() {
        languageRecyclerView!!.visibility = View.GONE

        object : AsyncTask<Void, Void, ArrayList<QuickReference>>() {

            override fun onPreExecute() {
                super.onPreExecute()
                progressLayout!!.visibility = View.VISIBLE
                referenceViewPager!!.visibility = View.GONE

            }

            override fun onPostExecute(quickReferences: ArrayList<QuickReference>) {
                super.onPostExecute(quickReferences)
                referenceViewPager!!.adapter = QuickReferencePagerAdapter(childFragmentManager, quickReferences, selectedTag!!.toLowerCase())
                progressLayout!!.visibility = View.GONE
                referenceViewPager!!.visibility = View.VISIBLE
                progressBar!!.max = quickReferences.size
                progressBar!!.progress = 1
                referenceViewPager!!.addOnPageChangeListener(this@QuickReferenceFragment)
            }

            override fun doInBackground(vararg voids: Void): ArrayList<QuickReference> {
                when (selectedTag) {
                    "C" -> return QuickReference.getCQuickReference()
                    "C++" -> return QuickReference.getCPPQuickReference()
                    "Java" -> return QuickReference.getJavaQuickReference()
                    "SQL" -> return QuickReference.getSQLQuickReference()
                    else -> return QuickReference.getCQuickReference()
                }
            }
        }.execute()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onItemClick(position: Int) {
        selectedTag = tagsRecyclerAdapter!!.getSelectedTag()
        selectedTextView!!.text = selectedTag
        getAllReference()
    }

    override fun onClick(v: View) {
        languageRecyclerView!!.visibility = View.VISIBLE
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        progressBar!!.progress = position + 1
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
