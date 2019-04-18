package com.sortedqueue.programmercreek.v2.ui.chapters

import android.view.ViewGroup
import com.sortedqueue.programmercreek.v2.base.BaseAdapter
import com.sortedqueue.programmercreek.v2.data.model.Streak

class StreakAdapter : BaseAdapter<Streak, StreakViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): StreakViewHolder
        = StreakViewHolder(viewGroup)

}