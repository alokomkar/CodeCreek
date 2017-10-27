package com.sortedqueue.programmercreek.adapter

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
import com.sortedqueue.programmercreek.database.InterviewQuestionModel
import com.sortedqueue.programmercreek.database.OptionModel
import com.sortedqueue.programmercreek.util.ItemTouchHelperAdapter

import java.util.ArrayList
import java.util.Collections

import butterknife.BindView
import butterknife.ButterKnife

import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_MULTIPLE_RIGHT
import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_REARRANGE
import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_SINGLE_RIGHT
import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_TRUE_FALSE

/**
 * Created by Alok on 09/03/17.
 */
class InterviewQuestionsAdapter(private val interviewQuestionModel: InterviewQuestionModel, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<InterviewQuestionsAdapter.ViewHolder>(), ItemTouchHelperAdapter {
    private var isAnswerChecked = false
    private var context: Context? = null
    private val optionModels: ArrayList<OptionModel>

    init {
        this.optionModels = ArrayList<OptionModel>()
        optionModels.addAll(interviewQuestionModel.optionModels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_interview_option, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val optionModel = getItemAtPosition(position)
        holder.progamLineTxtView!!.text = optionModel!!.option

        if (interviewQuestionModel.typeOfQuestion == TYPE_MULTIPLE_RIGHT) {
            holder.optionCardView!!.setBackgroundColor(
                    ContextCompat.getColor(
                            context!!,
                            if (optionModel.isSelected)
                                R.color.md_blue_grey_100
                            else
                                R.color.white))
        }

        if (isAnswerChecked) {
            when (interviewQuestionModel.typeOfQuestion) {
                TYPE_TRUE_FALSE, TYPE_SINGLE_RIGHT -> {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (optionModel.optionId == interviewQuestionModel.correctOption)
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
                TYPE_MULTIPLE_RIGHT -> if (optionModel.isSelected) {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (interviewQuestionModel.correctOptions.contains(optionModel.optionId))
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
                TYPE_REARRANGE -> {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (optionModel.optionId == interviewQuestionModel.correctSequence[position])
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
            }
        }

        holder.reorderImageView!!.visibility = if (interviewQuestionModel.typeOfQuestion == TYPE_REARRANGE) View.VISIBLE else View.GONE


    }

    fun isAnswerChecked(isAnswerChecked: Boolean) {
        this.isAnswerChecked = isAnswerChecked
        notifyDataSetChanged()
    }

    private fun getItemAtPosition(position: Int): OptionModel? {
        return optionModels[position]
    }

    override fun getItemCount(): Int {
        return optionModels.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
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
        interviewQuestionModel.optionModels.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.progamLineTxtView)
        internal var progamLineTxtView: TextView? = null
        @BindView(R.id.reorderImageView)
        internal var reorderImageView: ImageView? = null
        @BindView(R.id.optionCardView)
        internal var optionCardView: LinearLayout? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return
            }
            when (interviewQuestionModel.typeOfQuestion) {
                TYPE_TRUE_FALSE, TYPE_SINGLE_RIGHT -> isAnswerChecked(true)
                TYPE_MULTIPLE_RIGHT -> {
                    val optionModel = getItemAtPosition(position)
                    if (optionModel != null) {
                        optionModel.isSelected = !optionModel.isSelected
                        notifyItemChanged(position)
                    }
                }
                TYPE_REARRANGE -> {
                }
            }
            adapterClickListner.onItemClick(position)
        }
    }


}
