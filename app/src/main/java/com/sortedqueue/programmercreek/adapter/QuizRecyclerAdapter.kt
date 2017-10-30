package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.QuizModel

import java.util.ArrayList




/**
 * Created by Alok on 30/12/16.
 */
class QuizRecyclerAdapter(private val context: Context, private val quizModels: ArrayList<QuizModel>, private val mProgramExplanationList: ArrayList<String>, private val adapterClickListner: CustomQuizAdapterListner) : RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder>() {
    private var isAnswerChecked = false

    interface CustomQuizAdapterListner {
        fun onOptionSelected(position: Int, option: String)
    }

    fun setAnswerChecked(answerChecked: Boolean) {
        isAnswerChecked = answerChecked
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_question_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizModel = quizModels[position]
        val solution = mProgramExplanationList[position]
        holder.questionTextView!!.text = quizModel.questionIndex.toString() + ". " + quizModel.question
        val optionsList = quizModel.optionsList
        var index = 0
        holder.option1TextView!!.text = optionsList[index++]
        holder.option2TextView!!.text = optionsList[index++]
        holder.option3TextView!!.text = optionsList[index++]
        holder.option4TextView!!.text = optionsList[index]

        holder.option1Layout!!.isSelected = holder.option1TextView!!.text.toString() == quizModel.selectedOption
        holder.option2Layout!!.isSelected = holder.option2TextView!!.text.toString() == quizModel.selectedOption
        holder.option3Layout!!.isSelected = holder.option3TextView!!.text.toString() == quizModel.selectedOption
        holder.option4Layout!!.isSelected = holder.option4TextView!!.text.toString() == quizModel.selectedOption

        if (isAnswerChecked) {
            if (holder.option1Layout!!.isSelected) {
                checkAndChangeUI(holder, holder.option1Layout!!, holder.option1TextView!!, solution, position)
            } else if (holder.option2Layout!!.isSelected) {
                checkAndChangeUI(holder, holder.option2Layout!!, holder.option2TextView!!, solution, position)
            } else if (holder.option3Layout!!.isSelected) {
                checkAndChangeUI(holder, holder.option3Layout!!, holder.option3TextView!!, solution, position)
            } else if (holder.option4Layout!!.isSelected) {
                checkAndChangeUI(holder, holder.option4Layout!!, holder.option4TextView!!, solution, position)
            }
        }
    }


    private fun checkAndChangeUI(holder: ViewHolder, optionLayout: LinearLayout, optionTextView: TextView, solution: String, index: Int) {

        holder.option1Layout!!.background = null
        holder.option2Layout!!.background = null
        holder.option3Layout!!.background = null
        holder.option4Layout!!.background = null

        holder.option1TextView!!.setTextColor(Color.BLACK)
        holder.option2TextView!!.setTextColor(Color.BLACK)
        holder.option3TextView!!.setTextColor(Color.BLACK)
        holder.option4TextView!!.setTextColor(Color.BLACK)

        optionTextView.setTextColor(Color.WHITE)
        optionLayout.background = ContextCompat.getDrawable(context, if (optionTextView.text.toString() == solution) R.drawable.button_check else R.drawable.button_clear)

    }

    override fun getItemCount(): Int {
        return quizModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        internal var questionTextView: TextView? = itemView.findViewById(R.id.questionTextView) as TextView

        internal var option1TextView: TextView? = itemView.findViewById(R.id.option1TextView) as TextView

        internal var option1Layout: LinearLayout? = itemView.findViewById(R.id.option1Layout) as LinearLayout

        internal var option2TextView: TextView? = itemView.findViewById(R.id.option2TextView) as TextView

        internal var option2Layout: LinearLayout? = itemView.findViewById(R.id.option2Layout) as LinearLayout

        internal var option3TextView: TextView? = itemView.findViewById(R.id.option3TextView) as TextView

        internal var option3Layout: LinearLayout? = itemView.findViewById(R.id.option3Layout) as LinearLayout

        internal var option4TextView: TextView? = itemView.findViewById(R.id.option4TextView) as TextView

        internal var option4Layout: LinearLayout? = itemView.findViewById(R.id.option4Layout) as LinearLayout

        internal var option1ImageView: ImageView? = itemView.findViewById(R.id.option1ImageView) as ImageView

        internal var option2ImageView: ImageView? = itemView.findViewById(R.id.option2ImageView) as ImageView

        internal var option3ImageView: ImageView? = itemView.findViewById(R.id.option3ImageView) as ImageView

        internal var option4ImageView: ImageView? = itemView.findViewById(R.id.option4ImageView) as ImageView


        init {

            option1Layout!!.setOnClickListener(this)
            option2Layout!!.setOnClickListener(this)
            option3Layout!!.setOnClickListener(this)
            option4Layout!!.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (isAnswerChecked) {
                return
            }
            val position = adapterPosition
            when (view.id) {
                R.id.option1Layout -> {
                    option1Layout!!.isSelected = true
                    option2Layout!!.isSelected = false
                    option3Layout!!.isSelected = false
                    option4Layout!!.isSelected = false
                    option1ImageView!!.isSelected = true
                    option2ImageView!!.isSelected = false
                    option3ImageView!!.isSelected = false
                    option4ImageView!!.isSelected = false
                }
                R.id.option2Layout -> {
                    option1Layout!!.isSelected = false
                    option2Layout!!.isSelected = true
                    option3Layout!!.isSelected = false
                    option4Layout!!.isSelected = false
                    option1ImageView!!.isSelected = false
                    option2ImageView!!.isSelected = true
                    option3ImageView!!.isSelected = false
                    option4ImageView!!.isSelected = false
                }
                R.id.option3Layout -> {
                    option1Layout!!.isSelected = false
                    option2Layout!!.isSelected = false
                    option3Layout!!.isSelected = true
                    option4Layout!!.isSelected = false
                    option1ImageView!!.isSelected = false
                    option2ImageView!!.isSelected = false
                    option3ImageView!!.isSelected = true
                    option4ImageView!!.isSelected = false
                }
                R.id.option4Layout -> {
                    option1Layout!!.isSelected = false
                    option2Layout!!.isSelected = false
                    option3Layout!!.isSelected = false
                    option4Layout!!.isSelected = true
                    option1ImageView!!.isSelected = false
                    option2ImageView!!.isSelected = false
                    option3ImageView!!.isSelected = false
                    option4ImageView!!.isSelected = true
                }
            }

            if (position != RecyclerView.NO_POSITION) {
                var optionSelected = ""
                if (option1Layout!!.isSelected) {
                    optionSelected = option1TextView!!.text.toString()
                } else if (option2Layout!!.isSelected) {
                    optionSelected = option2TextView!!.text.toString()
                } else if (option3Layout!!.isSelected) {
                    optionSelected = option3TextView!!.text.toString()
                } else if (option4Layout!!.isSelected) {
                    optionSelected = option4TextView!!.text.toString()
                }

                if (optionSelected != "") {
                    val quizModel = quizModels[position]
                    quizModel.selectedOption = optionSelected
                    notifyDataSetChanged()
                }
            }
        }
    }

}
