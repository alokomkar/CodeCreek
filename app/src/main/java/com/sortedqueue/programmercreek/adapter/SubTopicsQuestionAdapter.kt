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
import java.util.Arrays
import java.util.Collections
import java.util.regex.Pattern

import butterknife.BindView
import butterknife.ButterKnife

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.MULTI_CHOICE
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.REARRANGE
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.SINGLE_CHOICE

/**
 * Created by Alok on 06/10/17.
 */

class SubTopicsQuestionAdapter(private val optionModels: ArrayList<OptionModel>, private val quizType: String, private val adapterClickListner: CustomProgramRecyclerViewAdapter.AdapterClickListner) : RecyclerView.Adapter<SubTopicsQuestionAdapter.ViewHolder>(), ItemTouchHelperAdapter {


    private var isAnswerChecked = false
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTopicsQuestionAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_interview_option, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubTopicsQuestionAdapter.ViewHolder, position: Int) {
        val optionModel = getItemAtPosition(position)
        holder.progamLineTxtView!!.text = optionModel!!.option

        if (quizType == SINGLE_CHOICE || quizType == MULTI_CHOICE) {
            holder.optionCardView!!.setBackgroundColor(
                    ContextCompat.getColor(
                            context!!,
                            if (optionModel.isSelected)
                                R.color.md_blue_grey_100
                            else
                                R.color.white))
        }

        if (isAnswerChecked) {
            when (quizType) {
                SINGLE_CHOICE -> {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (optionModel.option == correctAnswers!![0])
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
                MULTI_CHOICE -> if (optionModel.isSelected) {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (correctAnswers!!.contains(optionModel.option))
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
                REARRANGE -> {
                    holder.optionCardView!!.setBackgroundColor(
                            ContextCompat.getColor(
                                    context!!,
                                    if (optionModel.option == correctAnswers!![position])
                                        R.color.md_green_500
                                    else
                                        R.color.md_red_500))
                    holder.progamLineTxtView!!.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
            }
        }

        holder.reorderImageView!!.visibility = if (quizType == REARRANGE) View.VISIBLE else View.GONE


    }

    fun isAnswerChecked(isAnswerChecked: Boolean) {
        this.isAnswerChecked = isAnswerChecked
        notifyDataSetChanged()
    }

    fun countCorrectAnswers(): Int {
        var correctAnswers = 0
        when (quizType) {
            SINGLE_CHOICE -> for (optionModel in optionModels) {
                if (optionModel.isSelected && optionModel.option == this.correctAnswers!![0]) {
                    correctAnswers = 1
                }
            }
            MULTI_CHOICE -> for (optionModel in optionModels) {
                if (optionModel.isSelected && this.correctAnswers!!.contains(optionModel.option)) {
                    correctAnswers++
                }
            }
            REARRANGE -> {
                var position = 0
                for (optionModel in optionModels) {
                    if (optionModel.option == this.correctAnswers!![position++]) {
                        correctAnswers++
                    }
                }
            }
        }
        return correctAnswers
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

    override fun onItemDismiss(position: Int) {}

    private var correctAnswers: ArrayList<String>? = null
    fun setCorrectAnswers(answer: String) {
        correctAnswers = ArrayList<String>()
        correctAnswers!!.addAll(Arrays.asList(*answer.split(Pattern.quote("|||").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
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
            when (quizType) {
                SINGLE_CHOICE, MULTI_CHOICE -> {
                    val optionModel = getItemAtPosition(position)
                    if (optionModel != null) {
                        optionModel.isSelected = !optionModel.isSelected
                        notifyItemChanged(position)
                    }
                }
                REARRANGE -> {
                }
            }
            adapterClickListner.onItemClick(position)
        }
    }


}
