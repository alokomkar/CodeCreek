package com.sortedqueue.programmercreek.view

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter
import com.sortedqueue.programmercreek.database.ProgramIndex
import com.sortedqueue.programmercreek.database.ProgramTable
import com.sortedqueue.programmercreek.database.TagModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

/**
 * Created by Alok on 10/05/17.
 */

class UserProgramDialog : CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.codeRadioButton)
    internal var codeRadioButton: RadioButton? = null
    @BindView(R.id.explanationRadioButton)
    internal var explanationRadioButton: RadioButton? = null
    @BindView(R.id.codeView)
    internal var codeView: CodeView? = null
    @BindView(R.id.doneButton)
    internal var doneButton: Button? = null
    @BindView(R.id.saveButton)
    internal var saveButton: Button? = null
    @BindView(R.id.discardButton)
    internal var discardButton: Button? = null
    @BindView(R.id.accessSwitchCompat)
    internal var accessSwitchCompat: SwitchCompat? = null
    @BindView(R.id.accessTextView)
    internal var accessTextView: TextView? = null
    @BindView(R.id.presentationTitleEditText)
    internal var presentationTitleEditText: EditText? = null
    @BindView(R.id.languageRecyclerView)
    internal var languageRecyclerView: RecyclerView? = null
    @BindView(R.id.programTitleLayout)
    internal var programTitleLayout: LinearLayout? = null
    private var userProgramDialogListener: UserProgramDialogListener ?= null
    private var webUserProgramDialogListener: WebUserProgramDialogListener ?= null
    private var context: Context? = null
    private var builder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null
    private var programTables: ArrayList<ProgramTable>? = null
    private var programIndex: ProgramIndex? = null

    private var mode = -1
    private var dialogView: View? = null
    private var tagsRecyclerAdapter: TagsRecyclerAdapter? = null

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.codeRadioButton -> if (codeRadioButton!!.isChecked)
                showCode(R.id.codeRadioButton)
            R.id.explanationRadioButton -> if (explanationRadioButton!!.isChecked)
                showCode(R.id.explanationRadioButton)
            R.id.accessSwitchCompat -> if (accessSwitchCompat!!.isChecked) {
                accessTextView!!.text = "Public"
            } else {
                accessTextView!!.text = "Private"
            }
        }
    }

    private fun showCode(codeRadioButton: Int) {
        var code = ""
        when (codeRadioButton) {
            R.id.codeRadioButton -> for (programTable in programTables!!) {
                code += programTable.program_Line + "\n"
            }
            R.id.explanationRadioButton -> for (programTable in programTables!!) {
                code += programTable.program_Line_Description + "\n"
            }
        }
        codeView!!
                .setOptions(Options.get(context!!)
                        .withLanguage(programIndex!!.program_Language)
                        .withCode(code)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.saveButton -> if (validateFields()) {
                if (mode == -1)
                    userProgramDialogListener!!.onSave(accessTextView!!.text.toString())
                else
                    webUserProgramDialogListener!!.onSave(accessTextView!!.text.toString(), programIndex!!, programTables!!)
                alertDialog!!.dismiss()
            }
            R.id.doneButton -> if (validateFields()) {
                if (mode == -1)
                    userProgramDialogListener!!.onPreview()
                else
                    webUserProgramDialogListener!!.onPreview(programIndex!!, programTables!!)
            }
            R.id.discardButton -> {
                if (mode == -1)
                    userProgramDialogListener!!.onCancel()
                else
                    webUserProgramDialogListener!!.onCancel()
                alertDialog!!.dismiss()
            }
        }
    }

    private fun validateFields(): Boolean {

        if (mode == -1) return true

        val title = presentationTitleEditText!!.text.toString()
        if (title.trim { it <= ' ' }.length == 0) {
            CommonUtils.displayToast(context!!, "Enter program name")
            presentationTitleEditText!!.requestFocus()

            return false
        }
        if (tagsRecyclerAdapter!!.getSelectedTag() == "") {
            CommonUtils.displayToast(context!!, "Select language")

            return false
        }
        programIndex!!.program_Description = title
        programIndex!!.program_Language = tagsRecyclerAdapter!!.getSelectedTag()
        for (programTable in programTables!!) {
            programTable.program_Language = tagsRecyclerAdapter!!.getSelectedTag()
        }
        return true

    }

    fun showDialog() {
        alertDialog!!.show()
        accessSwitchCompat!!.isChecked = true
        codeRadioButton!!.isChecked = true

        programTitleLayout!!.visibility = if (mode == -1) View.GONE else View.VISIBLE
        if (mode != -1) {
            fetchAllTags()
        }
    }

    interface UserProgramDialogListener {
        fun onSave(accessSpecifier: String)

        fun onCancel()

        fun onPreview()
    }

    interface WebUserProgramDialogListener {
        fun onSave(accessSpecifier: String, programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>)

        fun onCancel()

        fun onPreview(programIndex: ProgramIndex, programTables: ArrayList<ProgramTable>)
    }

    constructor(context: Context,
                programIndex: ProgramIndex,
                programTables: ArrayList<ProgramTable>,
                userProgramDialogListener: UserProgramDialogListener) {
        this.context = context
        this.userProgramDialogListener = userProgramDialogListener
        this.programTables = programTables
        this.programIndex = programIndex
        initViews()
    }

    constructor(context: Context,
                programIndex: ProgramIndex,
                programTables: ArrayList<ProgramTable>,
                userProgramDialogListener: WebUserProgramDialogListener,
                mode: Int) {
        this.context = context
        this.mode = mode
        this.webUserProgramDialogListener = userProgramDialogListener
        this.programTables = programTables
        this.programIndex = programIndex
        initViews()
    }

    private fun initViews() {

        builder = AlertDialog.Builder(context!!)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
        builder!!.setTitle(programIndex!!.program_Description)

        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_program, null)
        ButterKnife.bind(this, dialogView!!)

        builder!!.setView(dialogView)
        alertDialog = builder!!.create()
        codeRadioButton!!.setOnCheckedChangeListener(this)
        explanationRadioButton!!.setOnCheckedChangeListener(this)
        accessSwitchCompat!!.setOnCheckedChangeListener(this)
        doneButton!!.setOnClickListener(this)
        saveButton!!.setOnClickListener(this)
        discardButton!!.setOnClickListener(this)


    }

    private fun fetchAllTags() {

        CommonUtils.displayProgressDialog(context, context!!.getString(R.string.loading))
        FirebaseDatabaseHandler(context!!).getAllTags(object : FirebaseDatabaseHandler.GetAllTagsListener {
            override fun onError(databaseError: DatabaseError) {
                CommonUtils.dismissProgressDialog()
            }

            override fun onSuccess(tagModel: TagModel) {
                setupRecyclerView(tagModel)
            }
        })
    }

    private fun setupRecyclerView(tagModel: TagModel) {
        languageRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tagsRecyclerAdapter = TagsRecyclerAdapter(tagModel.tagArrayList, 1)
        languageRecyclerView!!.adapter = tagsRecyclerAdapter
        CommonUtils.dismissProgressDialog()
    }
}
