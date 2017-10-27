package com.sortedqueue.programmercreek.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.DragEvent
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.util.DoubleClickListener
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList
import java.util.Random

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alok Omkar on 2017-08-14.
 */
class MatchQuestionsDropAdapter : RecyclerView.Adapter<MatchQuestionsDropAdapter.ViewHolder> {

    var programList: ArrayList<ProgramTable>? = null
        private set
    private var prettifyHighlighter: PrettifyHighlighter? = null
    private var choiceDrawable: Drawable? = null
    private var correctDrawable: Drawable? = null
    private var wrongDrawable: Drawable? = null
    private var isChecked: Boolean = false
    private var white: Int = 0
    private var option: Int = 0
    private var answer: Int = 0
    private var wrong: Int = 0
    private var isFillBlanks = false

    constructor(mProgramList: ArrayList<ProgramTable>) {
        this.programList = mProgramList
        prettifyHighlighter = PrettifyHighlighter.instance
    }

    constructor(mProgramQuestionList: ArrayList<ProgramTable>, isFillBlanks: Boolean) {
        this.isFillBlanks = true
        this.programList = mProgramQuestionList
        prettifyHighlighter = PrettifyHighlighter.instance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        choiceDrawable = ContextCompat.getDrawable(parent.context, R.drawable.option)
        correctDrawable = ContextCompat.getDrawable(parent.context, R.drawable.answer)
        wrongDrawable = ContextCompat.getDrawable(parent.context, R.drawable.error)
        answer = ContextCompat.getColor(parent.context, R.color.md_green_200)
        option = ContextCompat.getColor(parent.context, R.color.md_blue_grey_100)
        wrong = ContextCompat.getColor(parent.context, R.color.md_red_200)
        white = ContextCompat.getColor(parent.context, R.color.white)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_match_question, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programTable = getItemAtPosition(position)
        if (programTable.isChoice) {
            if (programTable.program_Line == "") {
                if (isFillBlanks) {
                    if (programTable.isHintEnabled) {
                        holder.questionTextView!!.text = programTable.program_Line_Description
                    } else {
                        holder.questionTextView!!.text = ""
                    }

                } else {
                    holder.questionTextView!!.text = programTable.program_Line_Description
                }

            } else {
                val programLine = programTable.program_Line
                if (programLine.contains("<") || programLine.contains(">")) {
                    holder.questionTextView!!.text = programTable.program_Line
                    holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
                } else {
                    val programLineHtml = prettifyHighlighter!!.highlight("c", programLine)
                    if (Build.VERSION.SDK_INT >= 24) {
                        holder.questionTextView!!.text = Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        holder.questionTextView!!.text = Html.fromHtml(programLineHtml)
                    }
                }
            }

            if (isChecked) {
                holder.itemView.background = if (programTable.isCorrect) correctDrawable else wrongDrawable
                if (programTable.isCorrect) {
                    val programLine = programTable.program_Line
                    if (programLine.contains("<") || programLine.contains(">")) {
                        holder.questionTextView!!.text = programTable.program_Line
                        holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
                    } else {
                        val programLineHtml = prettifyHighlighter!!.highlight("c", programLine)
                        if (Build.VERSION.SDK_INT >= 24) {
                            holder.questionTextView!!.text = Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            holder.questionTextView!!.text = Html.fromHtml(programLineHtml)
                        }
                    }
                } else {
                    if (isFillBlanks) {
                        if (programTable.isHintEnabled && programTable.program_Line.trim { it <= ' ' }.length == 0) {
                            holder.questionTextView!!.text = programTable.program_Line_Description
                        } else {
                            val programLine = programTable.program_Line
                            if (programLine.contains("<") || programLine.contains(">")) {
                                holder.questionTextView!!.text = programTable.program_Line
                                holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
                            } else {
                                val programLineHtml = prettifyHighlighter!!.highlight("c", programLine)
                                if (Build.VERSION.SDK_INT >= 24) {
                                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY)
                                } else {
                                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml)
                                }
                            }
                        }

                    } else {
                        if (programTable.program_Line.trim { it <= ' ' }.length == 0)
                            holder.questionTextView!!.text = programTable.program_Line_Description
                        else {
                            val programLine = programTable.program_Line
                            if (programLine.contains("<") || programLine.contains(">")) {
                                holder.questionTextView!!.text = programTable.program_Line
                                holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
                            } else {
                                val programLineHtml = prettifyHighlighter!!.highlight("c", programLine)
                                if (Build.VERSION.SDK_INT >= 24) {
                                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY)
                                } else {
                                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml)
                                }
                            }
                        }
                    }

                }
                holder.questionTextView!!.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                holder.itemView.background = choiceDrawable
            }

        } else {
            val programLine = programTable.program_Line
            if (programLine.contains("<") || programLine.contains(">")) {
                holder.questionTextView!!.text = programTable.program_Line
                holder.questionTextView!!.setTextColor(Color.parseColor("#006699"))
            } else {
                val programLineHtml = prettifyHighlighter!!.highlight("c", programLine)
                if (Build.VERSION.SDK_INT >= 24) {
                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    holder.questionTextView!!.text = Html.fromHtml(programLineHtml)
                }
            }
            holder.itemView.setBackgroundColor(white)
            holder.itemView.background = null
            //holder.itemView.setOnDragListener(null);
            //holder.itemView.setOnClickListener(null);


        }
    }

    fun setChecked(checked: Boolean, programTables: ArrayList<ProgramTable>) {
        isChecked = checked
        this.programList = programTables
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): ProgramTable {
        return programList!![position]
    }

    override fun getItemCount(): Int {
        return programList!!.size
    }

    fun addHints(maxHints: Int) {
        var maxHint = 0
        val indexList = ArrayList<Int>()
        for (index in programList!!.indices) {
            val randomIndex = getRandomNumberInRange(0, programList!!.size - 1)
            if (!indexList.contains(randomIndex)) {
                indexList.add(randomIndex)
                if (maxHint == maxHints) {
                    break
                }
                val programTable = programList!![randomIndex]
                if (!programTable.isHintEnabled
                        && programTable.isChoice
                        && programTable.program_Line.trim { it <= ' ' }.length == 0) {
                    programTable.isHintEnabled = true
                    maxHint++
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun getRandomNumberInRange(min: Int, max: Int): Int {

        if (min >= max) {
            throw IllegalArgumentException("max must be greater than min")
        }

        val r = Random()
        return r.nextInt(max - min + 1) + min
    }

    fun enableHints() {
        isFillBlanks = false
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnDragListener, View.OnLongClickListener {
        @BindView(R.id.questionTextView)
        internal var questionTextView: TextView? = null
        @BindView(R.id.matchQuestionLayout)
        internal var matchQuestionLayout: LinearLayout? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnDragListener(this)
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick(v: View) {

                }

                override fun onDoubleClick(v: View) {

                }
            })

        }

        override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DROP -> {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val programTable = getItemAtPosition(position)
                        if (programTable.isChoice) {
                            //handle the dragged view being dropped over a drop view
                            val loacalView = dragEvent.localState as View
                            //stop displaying the view where it was before it was dragged
                            //view.setVisibility(View.INVISIBLE);
                            //view dragged item is being dropped on
                            val dropTarget = view.findViewById(R.id.questionTextView) as TextView
                            //view being dragged and dropped
                            val dropped = loacalView.findViewById(R.id.questionTextView) as TextView
                            dropped.setOnTouchListener(null)
                            //update the text in the target view to reflect the data being dropped
                            dropTarget.text = dropped.text

                            //make it bold to highlight the fact that an item has been dropped
                            //dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                            //if an item has already been dropped here, there will be a tag
                            val tag = dropTarget.tag
                            //if there is already an item here, set it back visible in its original place
                            if (tag != null) {
                                //the tag is the view id already dropped here
                                val existingID = tag as Int
                                //set the original view visible again
                                if (loacalView != null && loacalView.findViewById(existingID) != null)
                                    loacalView.findViewById(existingID).visibility = View.VISIBLE
                            }
                            //set the tag in the target view being dropped on - to the ID of the view being dropped
                            if (dropTarget != null && dropped != null)
                                dropTarget.tag = dropped.id

                            programTable.program_Line = dropped.text.toString()
                            notifyDataSetChanged()
                        }
                    }
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                }
                else -> {
                }
            }//no action necessary
            //no action necessary
            //no action necessary
            //no action necessary
            return true
        }


        override fun onLongClick(view: View): Boolean {
            val postion = adapterPosition
            if (postion != RecyclerView.NO_POSITION) {
                val programTable = getItemAtPosition(postion)
                if (programTable.isChoice) {
                    programTable.program_Line = ""
                    if (isFillBlanks) {
                        questionTextView!!.text = ""
                    } else {
                        questionTextView!!.text = programTable.program_Line_Description
                        questionTextView!!.setTextColor(ContextCompat.getColor(view.context, R.color.md_blue_grey_600))
                        questionTextView!!.background = choiceDrawable
                    }
                }
            }
            return false
        }


    }

}
