package com.sortedqueue.programmercreek.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList




/**
 * Created by Alok on 06/01/17.
 */

class ChapterRecyclerAdapter(private val context: Context, private val chapters: ArrayList<Chapter>, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<ChapterRecyclerAdapter.ViewHolder>() {
    private val creekUserStats: CreekUserStats
    private var lastPosition = -1
    private val indexList: ArrayList<Int>

    init {
        this.indexList = ArrayList<Int>()
        this.creekUserStats = CreekApplication.instance.creekUserStats!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterRecyclerAdapter.ViewHolder, position: Int) {

        if (position + 1 > chapters.size) {
            holder.moduleLayout!!.visibility = View.GONE
            holder.moduleNameTextView!!.visibility = View.GONE
            holder.moduleDescriptionTextView!!.setText(R.string.navigate_to_program_index)
            holder.appCompatSeekBar!!.visibility = View.GONE
            holder.lockedImageView!!.visibility = View.INVISIBLE

        } else {

            holder.moduleLayout!!.visibility = View.VISIBLE
            holder.moduleNameTextView!!.visibility = View.VISIBLE
            holder.appCompatSeekBar!!.visibility = View.VISIBLE

            val chapter = chapters[position]
            holder.moduleNameTextView!!.text = chapter.chapterName
            holder.moduleDescriptionTextView!!.text = chapter.chapteBrief
            val isChapterEnabled: Boolean
            var chapterProgress = 0

            when (chapter.program_Language) {
                "c" -> {
                    isChapterEnabled = creekUserStats.getcProgressIndex() >= chapter.minStats
                    chapterProgress = creekUserStats.getcProgressIndex()
                    holder.lockedImageView!!.visibility = if (isChapterEnabled) View.INVISIBLE else View.VISIBLE
                }
                "cpp", "c++" -> {
                    isChapterEnabled = creekUserStats.cppProgressIndex >= chapter.minStats
                    chapterProgress = creekUserStats.cppProgressIndex
                    holder.lockedImageView!!.visibility = if (isChapterEnabled) View.INVISIBLE else View.VISIBLE
                }
                "java" -> {
                    isChapterEnabled = creekUserStats.javaProgressIndex >= chapter.minStats
                    chapterProgress = creekUserStats.javaProgressIndex
                    holder.lockedImageView!!.visibility = if (isChapterEnabled) View.INVISIBLE else View.VISIBLE
                }
                "sql" -> {
                    isChapterEnabled = creekUserStats.sqlProgressIndex >= chapter.minStats
                    chapterProgress = creekUserStats.sqlProgressIndex
                    holder.lockedImageView!!.visibility = if (isChapterEnabled) View.INVISIBLE else View.VISIBLE
                }
            }
            holder.lockedImageView!!.visibility = View.INVISIBLE
            val maxProgress = chapter.chapterDetailsArrayList.size
            holder.appCompatSeekBar!!.max = maxProgress
            chapterProgress = chapterProgress - chapter.minStats
            if (chapterProgress < 0) {
                chapterProgress = 0
            }
            chapterProgress = if (chapterProgress <= maxProgress) chapterProgress else maxProgress
            holder.appCompatSeekBar!!.progress = chapterProgress
            holder.appCompatSeekBar!!.visibility = if (chapterProgress == 0) View.GONE else View.VISIBLE

        }
        //startAnimation(holder.itemView, position * 150 );

    }

    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
            indexList.add(position)
        }
    }

    private fun startAnimation(itemView: View, delay: Int) {
        itemView.alpha = 0.0f
        itemView.animate().setStartDelay(delay.toLong()).setDuration(300).alpha(1.0f)
    }

    override fun getItemCount(): Int {
        return chapters.size + 1
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var moduleNameTextView: TextView = itemView.findViewById(R.id.moduleNameTextView) as TextView
        var moduleDescriptionTextView: TextView = itemView.findViewById(R.id.moduleDescriptionTextView) as TextView
        var headerTextView: TextView = itemView.findViewById(R.id.headerTextView) as TextView
        var lockedImageView: ImageView = itemView.findViewById(R.id.lockedImageView) as ImageView
        var appCompatSeekBar: SeekBar = itemView.findViewById(R.id.appCompatSeekBar) as SeekBar
        var moduleLayout: RelativeLayout = itemView.findViewById(R.id.moduleLayout) as RelativeLayout

        init {

            headerTextView.text = "Chapter"
            appCompatSeekBar.visibility = View.VISIBLE
            appCompatSeekBar.isActivated = false
            appCompatSeekBar.isEnabled = false
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (lockedImageView.visibility == View.VISIBLE) {
                    CommonUtils.displaySnackBar(context as Activity, R.string.chapter_locked)
                    return
                }
                adapterClickListner.onItemClick(position)
            }

        }
    }
}
