package com.sortedqueue.programmercreek.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener
import com.sortedqueue.programmercreek.util.CommonUtils
import com.sortedqueue.programmercreek.util.CreekAnalytics
import com.sortedqueue.programmercreek.util.ShuffleList

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList



import io.github.kbiakov.codeview.CodeView
import kotlinx.android.synthetic.main.fragment_fill_blank.*

class FillBlankFragment : Fragment(), UIProgramFetcherListener, CompoundButton.OnCheckedChangeListener, TestCompletionListener {



    private var mProgram_Index = 1
    private var shuffleList: ArrayList<String>? = null
    private var fillBlanksQuestionList: ArrayList<String>? = null
    private var isAnswered: Boolean = false
    private var firebaseDatabaseHandler: FirebaseDatabaseHandler? = null
    private var wizardMode: Boolean = false
    private var creekUserStats: CreekUserStats? = null
    private var mProgramIndex: ProgramIndex? = null

    private var programLanguage: String? = null
    private var bundle: Bundle? = null
    private val TAG = FillBlankFragment::class.java.simpleName

    fun setmProgram_Index(mProgram_Index: Int) {
        this.mProgram_Index = mProgram_Index
    }

    fun getmProgram_Index(): Int {
        return mProgram_Index
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fill_blank, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        programLanguage = CreekApplication.creekPreferences!!.programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        getProgram()
    }

    private fun getProgram() {

        if (answerLayout1 != null) {
            answerLayout1!!.alpha = 0.0f
            answerLayout2!!.alpha = 0.0f
            answerLayout3!!.alpha = 0.0f
            answerLayout4!!.alpha = 0.0f
        }
        if (bundle != null) {
            mProgramIndex = bundle!!.getParcelable<ProgramIndex>(ProgrammingBuddyConstants.KEY_PROG_ID)
            mProgram_Index = mProgramIndex!!.program_index
            val programTables = bundle!!.getParcelableArrayList<ProgramTable>(ProgrammingBuddyConstants.KEY_USER_PROGRAM)
            if (programTables != null && programTables.size > 0) {
                updateUI(programTables)
            } else {
                firebaseDatabaseHandler = FirebaseDatabaseHandler(context!!)
                firebaseDatabaseHandler!!.getProgramIndexInBackGround(mProgram_Index, object : FirebaseDatabaseHandler.GetProgramIndexListener {
                    override fun onSuccess(programIndex: ProgramIndex) {
                        headerTextView!!.text = programIndex.program_Description
                        mProgramIndex = programIndex
                        firebaseDatabaseHandler!!.getProgramTablesInBackground(mProgram_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                            override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                                updateUI(programTables)
                            }

                            override fun onError(databaseError: DatabaseError?) {
                                CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
                            }
                        })
                    }

                    override fun onError(databaseError: DatabaseError) {
                        CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
                    }
                })
            }
        } else {
            firebaseDatabaseHandler = FirebaseDatabaseHandler(context!!)
            firebaseDatabaseHandler!!.getProgramIndexInBackGround(mProgram_Index, object : FirebaseDatabaseHandler.GetProgramIndexListener {
                override fun onSuccess(programIndex: ProgramIndex) {
                    headerTextView!!.text = programIndex.program_Description
                    mProgramIndex = programIndex
                    firebaseDatabaseHandler!!.getProgramTablesInBackground(mProgram_Index, object : FirebaseDatabaseHandler.GetProgramTablesListener {
                        override fun onSuccess(programTables: ArrayList<ProgramTable>) {
                            updateUI(programTables)
                        }

                        override fun onError(databaseError: DatabaseError?) {
                            CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
                        }
                    })
                }

                override fun onError(databaseError: DatabaseError) {
                    CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data)
                }
            })
        }


    }

    private var fillBlanksIndex: ArrayList<Int>? = null

    override fun updateUI(program_TableList: ArrayList<ProgramTable>) {
        try {
            if (mProgramIndex != null) {
                CreekAnalytics.logEvent(TAG, JSONObject(Gson().toJson(mProgramIndex)))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val originalList = ArrayList<ProgramTable>()
        for (program_table in program_TableList) {
            originalList.add(program_table)
        }
        fillBlanksQuestionList = ProgramTable.getFillTheBlanksList(program_TableList) { fillBlanksIndex ->
            this@FillBlankFragment.fillBlanksIndex = fillBlanksIndex
            var index = 0
            option1TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option2TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option3TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option4TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option5TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option6TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option7TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
            option8TextView!!.text = "Options for line : " + (fillBlanksIndex[index++] + 1)
        }

        val solution_tables = ArrayList<ProgramTable>()

        for (i in fillBlanksQuestionList!!.indices) {
            val program_table = fillBlanksQuestionList!![i]
            val solution_table = originalList[i]
            if (program_table == "") {
                solution_tables.add(solution_table)
            }
        }


        setSolutionViews(solution_tables)
        setupQuestionViews(fillBlanksQuestionList!!)
        animateViews()
    }

    private fun animateViews() {
        var delay = 0
        val standardDelay = 270
        if (answerLayout1 != null) {
            initAnimations(answerLayout1!!, delay)
            delay = delay + standardDelay
            initAnimations(answerLayout2!!, delay)
            delay = delay + standardDelay
            initAnimations(answerLayout3!!, delay)
            delay = delay + standardDelay
            initAnimations(answerLayout4!!, delay)
        }
    }

    private fun initAnimations(frameLayout: LinearLayout, delay: Int) {
        frameLayout.animate().setStartDelay(delay.toLong()).setDuration(350).alpha(1.0f)
    }

    private fun setupQuestionViews(fillBlanksQuestionList: ArrayList<String>) {
        if (fillBlanksQuestionList.size > 4) {
            var position = 1
            var programDescription = ""
            programBlankLineTextView!!.text = ""
            for (program_table in fillBlanksQuestionList) {
                if (position == 1) {
                    programDescription += position.toString() + ". " + program_table
                    programBlankLineTextView!!.append(position++.toString() + ". ")
                } else {
                    programDescription += "\n" + position + ". " + program_table
                    programBlankLineTextView!!.append("\n" + position++ + ". ")
                }
                programBlankLineTextView!!.append(program_table.trim { it <= ' ' })
            }

            /*programCodeView.setOptions(Options.Default.get(getContext())
                    .withLanguage(programLanguage)
                    .withCode(programBlankLineTextView.getText().toString())
                    .withTheme(ColorTheme.MONOKAI));*/
        }
    }

    private var solutionTables: ArrayList<ProgramTable>? = null

    private fun setSolutionViews(solutionTables: ArrayList<ProgramTable>) {

        this.solutionTables = solutionTables

        answer1RadioButton1!!.setOnCheckedChangeListener(this)
        answer1RadioButton2!!.setOnCheckedChangeListener(this)
        answer1RadioButton3!!.setOnCheckedChangeListener(this)
        answer2RadioButton1!!.setOnCheckedChangeListener(this)
        answer2RadioButton2!!.setOnCheckedChangeListener(this)
        answer2RadioButton3!!.setOnCheckedChangeListener(this)
        answer3RadioButton1!!.setOnCheckedChangeListener(this)
        answer3RadioButton2!!.setOnCheckedChangeListener(this)
        answer3RadioButton3!!.setOnCheckedChangeListener(this)
        answer4RadioButton1!!.setOnCheckedChangeListener(this)
        answer4RadioButton2!!.setOnCheckedChangeListener(this)
        answer4RadioButton3!!.setOnCheckedChangeListener(this)
        answer5RadioButton1!!.setOnCheckedChangeListener(this)
        answer5RadioButton2!!.setOnCheckedChangeListener(this)
        answer5RadioButton3!!.setOnCheckedChangeListener(this)
        answer6RadioButton1!!.setOnCheckedChangeListener(this)
        answer6RadioButton2!!.setOnCheckedChangeListener(this)
        answer6RadioButton3!!.setOnCheckedChangeListener(this)
        answer7RadioButton1!!.setOnCheckedChangeListener(this)
        answer7RadioButton2!!.setOnCheckedChangeListener(this)
        answer7RadioButton3!!.setOnCheckedChangeListener(this)
        answer8RadioButton1!!.setOnCheckedChangeListener(this)
        answer8RadioButton2!!.setOnCheckedChangeListener(this)
        answer8RadioButton3!!.setOnCheckedChangeListener(this)

        val solutions = ArrayList<String>()
        for (program_table in solutionTables) {
            solutions.add(program_table.program_Line.trim { it <= ' ' })
        }

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[0])
        shuffleList!!.add(solutions[1])
        shuffleList!!.add(solutions[2])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer1RadioButton1!!.text = shuffleList!![0]
        answer1RadioButton2!!.text = shuffleList!![1]
        answer1RadioButton3!!.text = shuffleList!![2]


        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[1])
        shuffleList!!.add(solutions[2])
        shuffleList!!.add(solutions[3])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer2RadioButton1!!.text = shuffleList!![0]
        answer2RadioButton2!!.text = shuffleList!![1]
        answer2RadioButton3!!.text = shuffleList!![2]

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[2])
        shuffleList!!.add(solutions[3])
        shuffleList!!.add(solutions[4])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer3RadioButton1!!.text = shuffleList!![0]
        answer3RadioButton2!!.text = shuffleList!![1]
        answer3RadioButton3!!.text = shuffleList!![2]

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[3])
        shuffleList!!.add(solutions[4])
        shuffleList!!.add(solutions[5])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer4RadioButton1!!.text = shuffleList!![0]
        answer4RadioButton2!!.text = shuffleList!![1]
        answer4RadioButton3!!.text = shuffleList!![2]


        /** */

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[4])
        shuffleList!!.add(solutions[5])
        shuffleList!!.add(solutions[6])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer5RadioButton1!!.text = shuffleList!![0]
        answer5RadioButton2!!.text = shuffleList!![1]
        answer5RadioButton3!!.text = shuffleList!![2]


        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[5])
        shuffleList!!.add(solutions[6])
        shuffleList!!.add(solutions[7])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer6RadioButton1!!.text = shuffleList!![0]
        answer6RadioButton2!!.text = shuffleList!![1]
        answer6RadioButton3!!.text = shuffleList!![2]

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[6])
        shuffleList!!.add(solutions[7])
        shuffleList!!.add(solutions[0])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer7RadioButton1!!.text = shuffleList!![0]
        answer7RadioButton2!!.text = shuffleList!![1]
        answer7RadioButton3!!.text = shuffleList!![2]

        shuffleList = ArrayList<String>()
        shuffleList!!.add(solutions[7])
        shuffleList!!.add(solutions[0])
        shuffleList!!.add(solutions[1])

        shuffleList = ShuffleList.shuffleList(shuffleList!!)

        answer8RadioButton1!!.text = shuffleList!![0]
        answer8RadioButton2!!.text = shuffleList!![1]
        answer8RadioButton3!!.text = shuffleList!![2]



        checkButton!!.setOnClickListener { checkSolution() }


    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private var solutionsList: ArrayList<String>? = null

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

        if (solutionsList == null) {
            solutionsList = ArrayList<String>()
            var index = 0
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
            solutionsList!!.add(index++, "")
        }

        if (isChecked) {
            when (buttonView.id) {
                R.id.answer1RadioButton1 -> {
                    solutionsList!![0] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![0]] = buttonView.text.toString()
                }
                R.id.answer1RadioButton2 -> {
                    solutionsList!![0] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![0]] = buttonView.text.toString()
                }
                R.id.answer1RadioButton3 -> {
                    solutionsList!![0] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![0]] = buttonView.text.toString()
                }
                R.id.answer2RadioButton1 -> {
                    solutionsList!![1] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![1]] = buttonView.text.toString()
                }
                R.id.answer2RadioButton2 -> {
                    solutionsList!![1] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![1]] = buttonView.text.toString()
                }
                R.id.answer2RadioButton3 -> {
                    solutionsList!![1] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![1]] = buttonView.text.toString()
                }
                R.id.answer3RadioButton1 -> {
                    solutionsList!![2] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![2]] = buttonView.text.toString()
                }
                R.id.answer3RadioButton2 -> {
                    solutionsList!![2] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![2]] = buttonView.text.toString()
                }
                R.id.answer3RadioButton3 -> {
                    solutionsList!![2] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![2]] = buttonView.text.toString()
                }
                R.id.answer4RadioButton1 -> {
                    solutionsList!![3] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![3]] = buttonView.text.toString()
                }
                R.id.answer4RadioButton2 -> {
                    solutionsList!![3] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![3]] = buttonView.text.toString()
                }
                R.id.answer4RadioButton3 -> {
                    solutionsList!![3] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![3]] = buttonView.text.toString()
                }
            /** */
                R.id.answer5RadioButton1 -> {
                    solutionsList!![4] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![4]] = buttonView.text.toString()
                }
                R.id.answer5RadioButton2 -> {
                    solutionsList!![4] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![4]] = buttonView.text.toString()
                }
                R.id.answer5RadioButton3 -> {
                    solutionsList!![4] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![4]] = buttonView.text.toString()
                }
                R.id.answer6RadioButton1 -> {
                    solutionsList!![5] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![5]] = buttonView.text.toString()
                }
                R.id.answer6RadioButton2 -> {
                    solutionsList!![5] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![5]] = buttonView.text.toString()
                }
                R.id.answer6RadioButton3 -> {
                    solutionsList!![5] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![5]] = buttonView.text.toString()
                }
                R.id.answer7RadioButton1 -> {
                    solutionsList!![6] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![6]] = buttonView.text.toString()
                }
                R.id.answer7RadioButton2 -> {
                    solutionsList!![6] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![6]] = buttonView.text.toString()
                }
                R.id.answer7RadioButton3 -> {
                    solutionsList!![6] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![6]] = buttonView.text.toString()
                }
                R.id.answer8RadioButton1 -> {
                    solutionsList!![7] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![7]] = buttonView.text.toString()
                }
                R.id.answer8RadioButton2 -> {
                    solutionsList!![7] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![7]] = buttonView.text.toString()
                }
                R.id.answer8RadioButton3 -> {
                    solutionsList!![7] = buttonView.text.toString()
                    fillBlanksQuestionList!![fillBlanksIndex!![7]] = buttonView.text.toString()
                }
            }
            setupQuestionViews(fillBlanksQuestionList!!)
        }

    }

    fun checkSolution() {
        if (solutionsList != null && solutionsList!!.size > 0) {

            var checkedSolution = ""
            var i = 0
            var rightAnswers = 0
            if (answer1RadioButton1!!.isChecked) {
                checkedSolution = answer1RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer1RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer1RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer1RadioButton2!!.isChecked) {
                checkedSolution = answer1RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer1RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer1RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer1RadioButton3!!.isChecked) {
                checkedSolution = answer1RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer1RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer1RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer2RadioButton1!!.isChecked) {
                checkedSolution = answer2RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer2RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer2RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer2RadioButton2!!.isChecked) {
                checkedSolution = answer2RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer2RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer2RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer2RadioButton3!!.isChecked) {
                checkedSolution = answer2RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer2RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer2RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer3RadioButton1!!.isChecked) {
                checkedSolution = answer3RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer3RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer3RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer3RadioButton2!!.isChecked) {
                checkedSolution = answer3RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer3RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer3RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer3RadioButton3!!.isChecked) {
                checkedSolution = answer3RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer3RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer3RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer4RadioButton1!!.isChecked) {
                checkedSolution = answer4RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer4RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer4RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer4RadioButton2!!.isChecked) {
                checkedSolution = answer4RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer4RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer4RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer4RadioButton3!!.isChecked) {
                checkedSolution = answer4RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer4RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer4RadioButton3!!.setTextColor(Color.RED)
                }
            }
            /** */
            i++
            if (answer5RadioButton1!!.isChecked) {
                checkedSolution = answer5RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer5RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer5RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer5RadioButton2!!.isChecked) {
                checkedSolution = answer5RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer5RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer5RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer5RadioButton3!!.isChecked) {
                checkedSolution = answer5RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer5RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer5RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer6RadioButton1!!.isChecked) {
                checkedSolution = answer6RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer6RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer6RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer6RadioButton2!!.isChecked) {
                checkedSolution = answer6RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer6RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer6RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer6RadioButton3!!.isChecked) {
                checkedSolution = answer6RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer6RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer6RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer7RadioButton1!!.isChecked) {
                checkedSolution = answer7RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer7RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer7RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer7RadioButton2!!.isChecked) {
                checkedSolution = answer7RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer7RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer7RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer7RadioButton3!!.isChecked) {
                checkedSolution = answer7RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer7RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer7RadioButton3!!.setTextColor(Color.RED)
                }
            }
            i++
            if (answer8RadioButton1!!.isChecked) {
                checkedSolution = answer8RadioButton1!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer8RadioButton1!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer8RadioButton1!!.setTextColor(Color.RED)
                }
            }
            if (answer8RadioButton2!!.isChecked) {
                checkedSolution = answer8RadioButton2!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer8RadioButton2!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer8RadioButton2!!.setTextColor(Color.RED)
                }
            }
            if (answer8RadioButton3!!.isChecked) {
                checkedSolution = answer8RadioButton3!!.text.toString()
                if (checkedSolution.trim { it <= ' ' } == solutionTables!![i].program_Line.trim { it <= ' ' }) {
                    answer8RadioButton3!!.setTextColor(Color.GREEN)
                    rightAnswers++
                } else {
                    answer8RadioButton3!!.setTextColor(Color.RED)
                }
            }
            isAnswered = true
            var message = "Congratulations, you scored : $rightAnswers/8"
            when (rightAnswers) {
                1, 2 -> message = "You need improvement, retry again"
                3, 4 -> message = "You are half way there, Keep coming back"
                5, 6 -> message = "Almost perfect, you are one step away, retry"
                7, 8 -> message = "Congratulations, you've got it"
            }
            isAnswered = rightAnswers == 8
            CommonUtils.displaySnackBar(activity, "$message. You scored : $rightAnswers/8")
            if (wizardMode) {
                updateCreekStats()
            }
            //Check and disable radio groups, mark correct answers - as green and wrong ones as red
        }


    }

    private fun updateCreekStats() {

        creekUserStats = CreekApplication.instance.creekUserStats
        when (mProgramIndex!!.program_Language.toLowerCase()) {
            "c" -> creekUserStats!!.addToUnlockedCProgramIndexList(mProgramIndex!!.program_index + 1)
            "cpp", "c++" -> creekUserStats!!.addToUnlockedCppProgramIndexList(mProgramIndex!!.program_index + 1)
            "java" -> creekUserStats!!.addToUnlockedJavaProgramIndexList(mProgramIndex!!.program_index + 1)
            "usp" -> creekUserStats!!.addToUnlockedUspProgramIndexList(mProgramIndex!!.program_index + 1)
        }
        FirebaseDatabaseHandler(context!!).writeCreekUserStats(creekUserStats!!)
    }

    override fun isTestComplete(): Int {
        return if (isAnswered) ProgrammingBuddyConstants.KEY_FILL_BLANKS else -1
    }

    fun setWizardMode(wizardMode: Boolean) {
        this.wizardMode = wizardMode
    }

    fun setBundle(bundle: Bundle) {
        this.bundle = bundle
    }
}// Required empty public constructor