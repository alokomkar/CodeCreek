package com.sortedqueue.programmercreek.v2.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseViewHolder(viewGroup: ViewGroup, @LayoutRes layoutId: Int) : RecyclerView.ViewHolder(LayoutInflater
        .from(viewGroup.context)
        .inflate(layoutId, viewGroup, false)) {

    init {
        itemView.setOnClickListener { onItemClickListener?.invoke(it) }
    }

    var onItemClickListener: ((View) -> Unit)? = null

}