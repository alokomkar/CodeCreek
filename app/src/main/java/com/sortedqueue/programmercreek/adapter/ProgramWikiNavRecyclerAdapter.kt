package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.WikiModel

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-01-19.
 */
class ProgramWikiNavRecyclerAdapter(private val context: Context, private val programWikis: ArrayList<WikiModel>) : RecyclerView.Adapter<ProgramWikiNavRecyclerAdapter.ViewHolder>() {
    private val mAdapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner
    private val mProgramType: String
    private var programLanguage: String? = null

    init {
        this.programLanguage = CreekApplication.creekPreferences!!.programLanguage
        this.mAdapterClickListner = context as CustomProgramRecyclerViewAdapter.AdapterClickListner
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        mProgramType = programLanguage!!.substring(0, 1).toUpperCase()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramWikiNavRecyclerAdapter.ViewHolder {
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.index_nav_list, parent, false)
        return ViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: ProgramWikiNavRecyclerAdapter.ViewHolder, position: Int) {

        val programWiki = programWikis[position]
        holder.programTypeTextView!!.text = mProgramType
        holder.txtViewProgDescription!!.text = programWiki.wikiHeader

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.programTypeTextView)
        internal var programTypeTextView: TextView? = null
        @BindView(R.id.txtViewProgDescription)
        internal var txtViewProgDescription: TextView? = null
        @BindView(R.id.lockedImageView)
        internal var lockedImageView: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
            lockedImageView!!.visibility = View.GONE
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mAdapterClickListner.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return programWikis.size
    }


}
