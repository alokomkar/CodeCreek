package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.PresentationModel

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok on 11/04/17.
 */
class PresentationsListRecyclerAdapter(private val context: Context, private val presentationModels: ArrayList<PresentationModel>, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<PresentationsListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_presentation_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presentationModel = presentationModels[position]
        holder.titleTextView!!.text = presentationModel.presentationName
        holder.subTitleTextView!!.text = presentationModel.presenterName
        Glide.with(context)
                .load(presentationModel.presentationImage)
                .asBitmap()
                .centerCrop()
                .error(R.color.md_blue_600)
                .placeholder(R.color.md_blue_600)
                .into(holder.slideImageView!!)
    }

    override fun getItemCount(): Int {
        return presentationModels.size
    }

    fun getItemAtPosition(position: Int): PresentationModel {
        return presentationModels[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @BindView(R.id.titleTextView)
        internal var titleTextView: TextView? = null
        @BindView(R.id.subTitleTextView)
        internal var subTitleTextView: TextView? = null
        @BindView(R.id.slideImageView)
        internal var slideImageView: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterClickListner.onItemClick(position)
            }
        }
    }

}
