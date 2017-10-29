package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.database.QuickReference

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Alok on 11/08/17.
 */

class ReferenceFragment : Fragment() {

    @BindView(R.id.headerView)
    internal var headerView: View? = null
    @BindView(R.id.headerTextView)
    internal var headerTextView: TextView? = null
    @BindView(R.id.dividerView)
    internal var dividerView: View? = null
    @BindView(R.id.indicatorImageview)
    internal var indicatorImageview: ImageView? = null
    @BindView(R.id.contentRecyclerView)
    internal var contentRecyclerView: RecyclerView? = null
    internal var unbinder: Unbinder ?= null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_reference, container, false)
        unbinder = ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    private var quickReference: QuickReference? = null
    private var language: String? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quickReference = arguments.getParcelable<QuickReference>("quickReference")
        language = arguments.getString("language")
        headerTextView!!.text = quickReference!!.header
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        contentRecyclerView!!.layoutManager = LinearLayoutManager(context)
        contentRecyclerView!!.adapter = CodeEditorRecyclerAdapter(context, quickReference!!.contentArray, language!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    companion object {

        fun newInstance(quickReference: QuickReference, language: String): ReferenceFragment {
            val referenceFragment = ReferenceFragment()
            val bundle = Bundle()
            bundle.putString("language", language)
            bundle.putParcelable("quickReference", quickReference)
            referenceFragment.arguments = bundle
            return referenceFragment
        }
    }
}
