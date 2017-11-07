package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.DragNDropAdapter
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.interfaces.DragListenerInterface
import com.sortedqueue.programmercreek.interfaces.DropListenerInterface
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface
import com.sortedqueue.programmercreek.interfaces.SubTestCommunicationListener
import com.sortedqueue.programmercreek.util.ShuffleList
import com.sortedqueue.programmercreek.view.DragNDropListView
import kotlinx.android.synthetic.main.fragment_sub_test.*

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-01-26.
 */

class SubTestFragment : Fragment() {

    private var programTables: ArrayList<ProgramTable>? = null
    private var mProgramList: ArrayList<String>? = null
    private var mProgramCheckList: ArrayList<String>? = null
    private var mRandomTest: ArrayList<String>? = null
    private var programSize: Int = 0
    private var dragNDropAdapter: DragNDropAdapter? = null
    private var subTestCommunicationListener: SubTestCommunicationListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_sub_test, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SubTestCommunicationListener) {
            subTestCommunicationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        subTestCommunicationListener = null
    }

    private fun setupAdapter() {
        mProgramList = ArrayList<String>()
        mProgramCheckList = ArrayList<String>()
        var programLine: String? = null
        val iteraor = programTables!!.iterator()
        while (iteraor.hasNext()) {

            val newProgramTable = iteraor.next()
            programLine = newProgramTable.program_Line
            mProgramCheckList!!.add(programLine)
            mProgramList!!.add(programLine)
            /*if( programLine.contains("for") ) {
				mProgramList.add(programLine);
			}
			else {
				mProgramList.add(newProgramTable.getmProgram_Line_Html());
			}*/
        }

        mRandomTest = ArrayList<String>(mProgramList!!.size)
        for (item in mProgramList!!) {
            mRandomTest!!.add(item)
        }

        mRandomTest = ShuffleList.shuffleList(mRandomTest!!)
        programSize = mRandomTest!!.size
        dragNDropAdapter = DragNDropAdapter(context, intArrayOf(R.layout.dragitem), intArrayOf(R.id.programLineTextView), mRandomTest!!)
        dragNDropListView!!.adapter = dragNDropAdapter//new DragNDropAdapter(this,content)
        //mListView.setBackgroundResource(R.drawable.error);
        dragNDropListView!!.setDropListener(mDropListener)
        dragNDropListView!!.setRemoveListener(mRemoveListener)
        dragNDropListView!!.setDragListener(mDragListener)
        subTestCommunicationListener!!.submitSubTest(index, dragNDropAdapter!!.getmContent())
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun setProgramTables(programTables: ArrayList<ProgramTable>) {
        this.programTables = programTables
    }

    private val mDropListener = DropListenerInterface { from, to ->
        dragNDropAdapter!!.onDrop(from, to)
        dragNDropListView!!.invalidateViews()
        subTestCommunicationListener!!.submitSubTest(index, dragNDropAdapter!!.getmContent())
    }

    private val mRemoveListener = RemoveListenerInterface { which ->
        dragNDropAdapter!!.onRemove(which)
        dragNDropListView!!.invalidateViews()
    }

    private val mDragListener = object : DragListenerInterface {

        internal var backgroundColor = 0xe0103010.toInt()
        internal var defaultBackgroundColor: Int = 0

        override fun onDrag(x: Int, y: Int, listView: ListView?) {

        }

        override fun onStartDrag(itemView: View) {
            itemView.visibility = View.INVISIBLE
            defaultBackgroundColor = itemView.drawingCacheBackgroundColor
            //itemView.setBackgroundColor(backgroundColor);
            val imageView = itemView.findViewById<ImageView>(R.id.dragItemImageView)
            if (imageView != null) imageView.visibility = View.INVISIBLE
        }

        override fun onStopDrag(itemView: View) {
            itemView.visibility = View.VISIBLE
            //itemView.setBackgroundColor(defaultBackgroundColor);
            val imageView = itemView.findViewById<ImageView>(R.id.dragItemImageView)
            if (imageView != null) imageView.visibility = View.VISIBLE
        }

    }

    private var index: Int = 0
    fun setIndex(index: Int) {
        this.index = index
    }

    fun setSubmitTestCommunicationListener(subTestCommunicationListener: SubTestCommunicationListener) {
        this.subTestCommunicationListener = subTestCommunicationListener
    }
}
