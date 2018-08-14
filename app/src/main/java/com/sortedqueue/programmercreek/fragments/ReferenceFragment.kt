package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.database.QuickReference
import kotlinx.android.synthetic.main.fragment_reference.*


/**
 * Created by Alok on 11/08/17.
 */

class ReferenceFragment : Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reference, container, false)
    }

    private var quickReference: QuickReference? = null
    private var language: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quickReference = arguments!!.getParcelable<QuickReference>("quickReference")
        language = arguments!!.getString("language")
        headerTextView!!.text = quickReference!!.header
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        contentRecyclerView!!.layoutManager = LinearLayoutManager(context)
        contentRecyclerView!!.adapter = CodeEditorRecyclerAdapter(context!!, quickReference!!.contentArray, language!!)
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
