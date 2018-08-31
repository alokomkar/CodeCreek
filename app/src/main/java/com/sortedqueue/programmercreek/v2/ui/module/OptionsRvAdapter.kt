package com.sortedqueue.programmercreek.v2.ui.module

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sortedqueue.programmercreek.R

import com.sortedqueue.programmercreek.util.ItemTouchHelperAdapter
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import java.util.*

class OptionsRvAdapter(private val questionType : Int,
                       private val optionModels: ArrayList<String>,
                       private var correctOptions : ArrayList<String>?,
                       private var baseAdapterClickListener: BaseAdapterClickListener<String> )
    : RecyclerView.Adapter<OptionsRvAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private var isAnswerChecked = false
    private var context: Context? = null
    private val selectedOptions = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_interview_option, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val optionModel = getItemAtPosition(position)
        holder.progamLineTxtView.text = optionModel

        if ( questionType == SimpleContent.mcq || questionType == SimpleContent.codeMcq ) {
            holder.optionCardView.setBackgroundColor(
                    ContextCompat.getColor(
                            context!!,
                            if (selectedOptions.contains(optionModel))
                                R.color.md_blue_grey_100
                            else
                                R.color.white))
        }

        if (isAnswerChecked) {
            when (questionType) {
                SimpleContent.mcq, SimpleContent.codeMcq -> {
                    if( correctOptions != null ) {
                        holder.optionCardView.setBackgroundColor(
                                ContextCompat.getColor(
                                        context!!,
                                        if (correctOptions!!.contains(optionModel))
                                            R.color.md_green_500
                                        else
                                            R.color.md_red_500))
                    }
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
                SimpleContent.rearrange -> {
                    if( correctOptions != null ) {
                        holder.optionCardView.setBackgroundColor(
                                ContextCompat.getColor(
                                        context!!,
                                        if (optionModel == correctOptions!![position].trim() )
                                            R.color.md_green_500
                                        else
                                            R.color.md_red_500))
                    }
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
            }
        }

        holder.reorderImageView.visibility = if (questionType == SimpleContent.rearrange) View.VISIBLE else View.GONE


    }

    fun isAnswerChecked(isAnswerChecked: Boolean) {
        this.isAnswerChecked = isAnswerChecked
        notifyDataSetChanged()
    }

    private fun getItemAtPosition(position: Int): String {
        return optionModels[position].trim()
    }

    override fun getItemCount(): Int {
        return optionModels.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(optionModels, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(optionModels, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        optionModels.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var progamLineTxtView: TextView = itemView.findViewById(R.id.progamLineTxtView)
        internal var reorderImageView: ImageView = itemView.findViewById(R.id.reorderImageView)
        internal var optionCardView: LinearLayout = itemView.findViewById(R.id.optionCardView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return
            }
            when (questionType) {
                SimpleContent.mcq, SimpleContent.codeMcq -> {
                    val optionModel = getItemAtPosition(position)

                    when {
                        correctOptions!!.size > 1 ->{
                            if( selectedOptions.contains(optionModel) ) {
                                selectedOptions.remove(optionModel)
                            }
                            else selectedOptions.add(optionModel)
                            notifyItemChanged(position)
                        }
                        correctOptions!!.size == 1 -> {
                            selectedOptions.clear()
                            selectedOptions.add(optionModel)
                            notifyDataSetChanged()
                        }
                    }



                }
                SimpleContent.fillBlanks -> baseAdapterClickListener.onItemClick( position, getItemAtPosition(position) )
            }

        }
    }


}