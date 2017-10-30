package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.CreatePresentationActivity
import com.sortedqueue.programmercreek.activity.ViewPresentationActivity
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.PresentationsListRecyclerAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.PresentationModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import kotlinx.android.synthetic.main.fragment_presentations.*

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-04-04.
 */
class PresentationsListFragment : Fragment(), View.OnClickListener, FirebaseDatabaseHandler.GetAllPresentationsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    private var adapter: PresentationsListRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_presentations, container, false)


        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseDatabaseHandler(context).getAllPresentations(this)
    }

    private fun setupRecyclerView(presentationModelArrayList: ArrayList<PresentationModel>) {
        presentationsRecyclerView!!.layoutManager = GridLayoutManager(context, 2)
        adapter = PresentationsListRecyclerAdapter(context, presentationModelArrayList, this)
        presentationsRecyclerView!!.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(v: View) {
        val intent = Intent(context, CreatePresentationActivity::class.java)
        startActivity(intent)
    }

    override fun onSuccess(presentationModelArrayList: ArrayList<PresentationModel>) {
        setupRecyclerView(presentationModelArrayList)
    }

    override fun onError(databaseError: DatabaseError?) {

    }

    override fun onItemClick(position: Int) {
        val presentationModel = adapter!!.getItemAtPosition(position)
        val intent = Intent(context, ViewPresentationActivity::class.java)
        intent.putExtra(ProgrammingBuddyConstants.KEY_PROG_ID, presentationModel)
        startActivity(intent)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var indexFragment: PresentationsListFragment? = null
        val instance: PresentationsListFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = PresentationsListFragment()
                }
                return indexFragment!!
            }
    }
}
