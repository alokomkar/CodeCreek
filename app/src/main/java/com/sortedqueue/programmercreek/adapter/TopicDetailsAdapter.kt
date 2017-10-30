package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.TopicDetails
import com.sortedqueue.programmercreek.database.lessons.Lesson

import java.util.ArrayList




/**
 * Created by Alok on 15/09/17.
 */

class TopicDetailsAdapter(private val lessons: ArrayList<TopicDetails>, private val adapterClickListener: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<TopicDetailsAdapter.ViewHolder>() {
    private val animation: AlphaAnimation
    private val completePosition = 3
    private var completeDrawable: Drawable? = null
    private var inCompleteDrawable: Drawable? = null

    init {
        animation = AlphaAnimation(1f, 0f)
        animation.duration = 800
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicDetailsAdapter.ViewHolder {
        inCompleteDrawable = ContextCompat.getDrawable(parent.context, R.drawable.button_grey)
        completeDrawable = ContextCompat.getDrawable(parent.context, R.drawable.button_check)
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_topics, parent, false))
    }

    override fun onBindViewHolder(holder: TopicDetailsAdapter.ViewHolder, position: Int) {
        val lesson = getItem(position)
        holder.topicsTextView!!.text = lesson.topicDescription
        holder.dividerView!!.visibility = if (position == lessons.size - 1) View.GONE else View.VISIBLE
        holder.topicsTextView!!.setTextColor(holder.itemView.context.getColor(if (position <= completePosition) R.color.md_green_500 else R.color.md_grey_500))
        holder.dividerView!!.setBackgroundColor(holder.itemView.context.getColor(if (position <= completePosition) R.color.md_green_500 else R.color.md_grey_500))
        if (position == completePosition + 1) {
            holder.topicsTextView!!.animation = animation
            holder.topicsTextView!!.startAnimation(animation)
        } else {
            holder.topicsTextView!!.clearAnimation()
        }
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun getItem(position: Int): TopicDetails {
        return lessons[position]
    }


    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        itemView.findViewById(R.id.topicsTextView)
        internal var topicsTextView: TextView? = null
        itemView.findViewById(R.id.dividerView)
        internal var dividerView: View? = null

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != -1)
                adapterClickListener.onItemClick(position)
        }
    }
}
