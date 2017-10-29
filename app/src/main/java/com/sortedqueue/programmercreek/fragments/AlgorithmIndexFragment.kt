package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.AlgorithmsRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-03-17.
 */

class AlgorithmIndexFragment : Fragment(), FirebaseDatabaseHandler.GetAllAlgorithmsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.programListRecyclerView)
    internal var programListRecyclerView: RecyclerView? = null
    private var algorithmsRecyclerAdapter: AlgorithmsRecyclerAdapter? = null

    private var algorithmNavigationListener: AlgorithmNavigationListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AlgorithmNavigationListener) {
            algorithmNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        algorithmNavigationListener = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_algorithm_index, container, false)
        ButterKnife.bind(this, view)
        fetchAlgorithmsList()
        return view
    }

    private fun fetchAlgorithmsList() {
        FirebaseDatabaseHandler(context).getAllAlgorithmIndex(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onSuccess(algorithmsIndexArrayList: ArrayList<AlgorithmsIndex>) {
        programListRecyclerView!!.layoutManager = LinearLayoutManager(context)
        algorithmsRecyclerAdapter = AlgorithmsRecyclerAdapter(context, this, algorithmsIndexArrayList)
        programListRecyclerView!!.adapter = algorithmsRecyclerAdapter
    }

    override fun onError(databaseError: DatabaseError) {

    }

    override fun onItemClick(position: Int) {
        algorithmNavigationListener!!.loadAlgorithmFragment(algorithmsRecyclerAdapter!!.getItemAtPosition(position))
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var indexFragment: AlgorithmIndexFragment? = null
        val instance: AlgorithmIndexFragment
            get() {
                if (indexFragment == null) {
                    indexFragment = AlgorithmIndexFragment()
                }
                return indexFragment!!
            }
    }


}
