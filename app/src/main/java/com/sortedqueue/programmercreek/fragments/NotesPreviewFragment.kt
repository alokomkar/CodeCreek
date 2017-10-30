package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.activity.NewProgramWikiActivity
import com.sortedqueue.programmercreek.adapter.NotesPreviewRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.ProgramWikiNavRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.ProgramWikiRecyclerAdapter
import com.sortedqueue.programmercreek.database.NotesModel
import com.sortedqueue.programmercreek.database.ProgramWiki
import com.sortedqueue.programmercreek.database.WikiModel
import kotlinx.android.synthetic.main.fragment_notes.*

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-07-28.
 */
class NotesPreviewFragment : Fragment() {


    private var wikiModel = WikiModel()
    private var programWikis = ArrayList<ProgramWiki>()
    private var notesModelArrayList: ArrayList<NotesModel>? = null
    private var selectedTag: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_notes, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesModelArrayList = arguments.getParcelableArrayList<NotesModel>("notes")
        selectedTag = arguments.getString("selectedTag")

        wikiModel = WikiModel()
        programWikis = ArrayList<ProgramWiki>()
        wikiModel.syntaxLanguage = selectedTag

        for (notesModel in notesModelArrayList!!) {
            when (notesModel.noteType) {
                NotesModel.TYPE_CODE -> {
                }
                NotesModel.TYPE_LINK -> {
                }
                NotesModel.TYPE_HEADER -> {
                }
                NotesModel.TYPE_NOTES -> {
                }
            }
        }

        programRecyclerView!!.layoutManager = LinearLayoutManager(context)
        programRecyclerView!!.adapter = NotesPreviewRecyclerAdapter(notesModelArrayList!!)


    }

    companion object {

        fun newInstance(notesModelArrayList: ArrayList<NotesModel>, selectedTag: String): NotesPreviewFragment {
            val notesPreviewFragment = NotesPreviewFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("notes", notesModelArrayList)
            bundle.putString("selectedTag", selectedTag)
            notesPreviewFragment.arguments = bundle
            notesPreviewFragment.arguments = bundle
            return notesPreviewFragment
        }
    }


}
