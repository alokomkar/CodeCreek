package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener
import com.sortedqueue.programmercreek.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_presentation_title.*


/**
 * Created by Alok Omkar on 2017-04-26.
 */

class PresentationTitleFragment : Fragment(), View.OnClickListener {

    private var tagsRecyclerAdapter: TagsRecyclerAdapter? = null

    private var presentationCommunicationsListener: PresentationCommunicationsListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_presentation_title, container, false)


        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAllTags()
        setupListeners()
    }

    private fun setupListeners() {
        doneButton!!.setOnClickListener(this)
        cancelButton!!.setOnClickListener(this)
        addTagTextView!!.setOnClickListener(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PresentationCommunicationsListener)
            presentationCommunicationsListener = context
    }

    override fun onDetach() {
        super.onDetach()
        presentationCommunicationsListener = null
    }

    private fun fetchAllTags() {

        CommonUtils.displayProgressDialog(context, getString(R.string.loading))
        FirebaseDatabaseHandler(context).getAllTags(object : FirebaseDatabaseHandler.GetAllTagsListener {
            override fun onError(databaseError: DatabaseError) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onSuccess(tagModel: TagModel) {
                setupRecyclerView(tagModel)
            }
        })
    }

    private fun setupRecyclerView(tagModel: TagModel) {
        tagsRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tagsRecyclerAdapter = TagsRecyclerAdapter(tagModel.tagArrayList)
        tagsRecyclerView!!.adapter = tagsRecyclerAdapter
        CommonUtils.dismissProgressDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.doneButton -> validateAndSavePresentation()
            R.id.cancelButton -> activity.finish()
            R.id.addTagTextView -> {
                val newTag = addTagEditText!!.text.toString()
                if (newTag != null && newTag.trim { it <= ' ' }.length > 0) {
                    addTagEditText!!.clearComposingText()
                    tagsRecyclerAdapter!!.addTag(newTag)
                }
            }
        }
    }

    private fun validateAndSavePresentation() {
        val presentationTitle = presentationTitleEditText!!.text.toString()
        if (presentationTitle == null || presentationTitle.trim { it <= ' ' }.length == 0) {
            presentationTitleLayout!!.isErrorEnabled = true
            presentationTitleLayout!!.error = getString(R.string.required_field)
            return
        }

        presentationTitleLayout!!.error = null
        presentationTitleLayout!!.isErrorEnabled = false

        val presentationDescription = presentationDescriptionEditText!!.text.toString()
        if (presentationDescription == null || presentationDescription.trim { it <= ' ' }.length == 0) {
            presentationDescriptionLayout!!.isErrorEnabled = false
            presentationDescriptionLayout!!.error = getString(R.string.required_field)
            return
        }
        presentationDescriptionLayout!!.error = null
        presentationDescriptionLayout!!.isErrorEnabled = false

        if (presentationCommunicationsListener != null) {
            presentationCommunicationsListener!!.onPresentationTitle(presentationTitle, presentationDescription, tagsRecyclerAdapter!!.selectedTags)
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var presentationTitleFragment: PresentationTitleFragment? = null

        val instance: PresentationTitleFragment
            get() {
                if (presentationTitleFragment == null) {
                    presentationTitleFragment = PresentationTitleFragment()
                }
                return presentationTitleFragment!!
            }
    }
}
