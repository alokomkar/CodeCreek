package com.sortedqueue.programmercreek.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.MemorizeProgramActivity
import com.sortedqueue.programmercreek.activity.ProgramActivity
import com.sortedqueue.programmercreek.activity.ProgramListActivity
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler

import java.util.ArrayList





import com.sortedqueue.programmercreek.activity.ProgramListActivity.Companion.KEY_WIZARD
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by Alok on 08/08/17.
 */

class SearchFragment : Fragment(), TextWatcher, FirebaseDatabaseHandler.ProgramIndexInterface, CustomProgramRecyclerViewAdapter.AdapterClickListner {


    private var customProgramRecyclerViewAdapter: CustomProgramRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_search, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText!!.addTextChangedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        if (s.toString().length > 3) {
            FirebaseDatabaseHandler(context!!).searchPrograms(s.toString(), this)
        } else {
            if (customProgramRecyclerViewAdapter != null) {
                customProgramRecyclerViewAdapter!!.clearAll()
                showEmptyView()
            }
        }
    }


    override fun getProgramIndexes(program_indices: ArrayList<ProgramIndex>) {
        searchRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        customProgramRecyclerViewAdapter = CustomProgramRecyclerViewAdapter(context!!, program_indices, this)
        searchRecyclerView!!.adapter = customProgramRecyclerViewAdapter
        showEmptyView()
    }

    private fun showEmptyView() {
        val programsSize = customProgramRecyclerViewAdapter!!.itemCount
        noProgramsLayout!!.visibility = if (programsSize > 0) View.GONE else View.VISIBLE
        searchRecyclerView!!.visibility = if (programsSize > 0) View.VISIBLE else View.GONE
    }

    override fun onError(databaseError: DatabaseError) {

    }

    override fun onItemClick(position: Int) {

        val newIntentBundle = Bundle()
        var newIntent: Intent? = null
        newIntentBundle.putBoolean(KEY_WIZARD, true)
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, customProgramRecyclerViewAdapter!!.getItemAtPosition(position))
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1)
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, customProgramRecyclerViewAdapter!!.getItemAtPosition(position).program_Description)
        newIntent = Intent(context, ProgramActivity::class.java)
        newIntent.putExtras(newIntentBundle)
        startActivity(newIntent)

    }
}
