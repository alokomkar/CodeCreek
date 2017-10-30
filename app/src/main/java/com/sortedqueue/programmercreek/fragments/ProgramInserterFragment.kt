package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.ProgramInserterWikiAdapter
import com.sortedqueue.programmercreek.database.ProgramWiki
import com.sortedqueue.programmercreek.database.WikiModel
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_program_inserter.*

import java.util.ArrayList




/**
 * Created by Alok Omkar on 2017-01-11.
 */

class ProgramInserterFragment : Fragment(), View.OnClickListener {


    private val wikiModel = WikiModel()
    private val programWikis = ArrayList<ProgramWiki>()
    private var adapter: ProgramInserterWikiAdapter? = null
    private var programWiki: ProgramWiki? = null
    private var language: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_program_inserter, null)
        setupViews()
        return fragmentView
    }

    private fun setupViews() {
        language = CreekApplication.creekPreferences!!.programLanguage
        if (language == "c++") {
            language = "cpp"
        }
        if (language == "java") {

        }
        wikiIdEditText!!.setText(language)
        syntaxLanguageTextView!!.text = language

        wikiModel.syntaxLanguage = syntaxLanguageTextView!!.text.toString()
        deleteButton!!.setOnClickListener(this)
        saveButton!!.setOnClickListener(this)
        insertButton!!.setOnClickListener(this)
        formatCodeButton!!.setOnClickListener(this)
        clearButton!!.setOnClickListener(this)
        setupRecyelerView()
    }

    private fun setupRecyelerView() {
        adapter = ProgramInserterWikiAdapter(programWikis)
        programWikiRecyclerView!!.adapter = adapter
        programWikiRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.deleteButton -> deleteWiki()
            R.id.insertButton -> insertWiki()
            R.id.saveButton -> saveAllDetails()
            R.id.formatCodeButton -> formatCode()
            R.id.clearButton -> clearAllFields()
        }
    }

    private fun clearAllFields() {
        wikiIdEditText!!.setText(language)
        headerEditText!!.setText("")
        wikiHeaderEditText!!.setText("")
        explanationEditText!!.setText("")
        exampleEditText!!.setText("")
        ouptputEditText!!.setText("")
    }

    private fun formatCode() {
        val programCode = exampleEditText!!.text.toString().trim { it <= ' ' }
        var formattedCode = ""
        var splitString = programCode.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var lastIndex = 0
        for (string in splitString) {
            lastIndex++
            if (lastIndex == splitString.size) {
                formattedCode += string.trim { it <= ' ' }
            } else
                formattedCode += string.trim { it <= ' ' } + ";\n"

        }
        lastIndex = 0
        Log.d("Formatted", "Regex : ; \n $formattedCode")
        splitString = formattedCode.split("\\{".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        formattedCode = ""
        for (string in splitString) {
            lastIndex++
            if (lastIndex == splitString.size) {
                formattedCode += string.trim { it <= ' ' }
            } else
                formattedCode += string.trim { it <= ' ' } + " {\n"

        }
        Log.d("Formatted", "Regex : { \n $formattedCode")
        lastIndex = 0
        splitString = formattedCode.split("\\}".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        formattedCode = ""
        for (string in splitString) {
            /* lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string;
            }
            else*/
            formattedCode += string.trim { it <= ' ' } + "\n}\n"

        }
        Log.d("Formatted", "Regex : } \n $formattedCode")

        lastIndex = 0
        splitString = formattedCode.split("cout".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        formattedCode = ""
        for (string in splitString) {
            lastIndex++
            if (lastIndex == 1 /*|| lastIndex == splitString.length*/) {
                formattedCode += string.trim { it <= ' ' }
            } else
                formattedCode += "\ncout" + string.trim { it <= ' ' }

        }
        Log.d("Formatted", "Regex : cout \n $formattedCode")
        /*lastIndex = 0;
        splitString = formattedCode.split("cin");
        formattedCode = "";
        for( String string : splitString ) {
            lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string;
            }
            else
                formattedCode += "\ncin"+ string;

        }
        Log.d("Formatted", "Regex : cin \n " + formattedCode);*/

        Log.d("Formatted", "Regex : final \n " + formattedCode.trim { it <= ' ' })
        exampleEditText!!.setText(formattedCode)
    }


    private fun insertWiki() {
        var programWiki = ProgramWiki()
        programWiki.contentType = ProgramWiki.CONTENT_HEADER
        programWikis.add(programWiki)
        programWiki = ProgramWiki()
        programWiki.contentType = ProgramWiki.CONTENT_PROGRAM_EXPLANATION
        programWikis.add(programWiki)
        programWiki = ProgramWiki()
        programWiki.contentType = ProgramWiki.CONTENT_PROGRAM
        programWikis.add(programWiki)
        adapter!!.notifyDataSetChanged()
    }

    private fun deleteWiki() {
        if (programWikis.size == 3) {
            return
        }
        val size = programWikis.size
        programWikis.removeAt(size - 1)
        programWikis.removeAt(size - 2)
        programWikis.removeAt(size - 3)
        adapter!!.notifyDataSetChanged()
    }

    private fun validateEmptyEditText(editText: EditText): Boolean {
        val result = editText.text.toString().trim { it <= ' ' }.length == 0
        CommonUtils.displaySnackBar(activity, "Content missing")
        editText.requestFocus()
        return result
    }

    private fun saveAllDetails() {

        if (validateEmptyEditText(wikiHeaderEditText!!)) {
            return
        }
        if (validateEmptyEditText(wikiIdEditText!!)) {
            return
        }
        if (validateEmptyEditText(exampleEditText!!)) {
            return
        }
        if (validateEmptyEditText(explanationEditText!!)) {
            return
        }
        if (validateEmptyEditText(headerEditText!!)) {
            return
        }
        if (validateEmptyEditText(ouptputEditText!!)) {
            return
        }

        programWikis.clear()
        programWiki = ProgramWiki()
        programWiki!!.contentType = ProgramWiki.CONTENT_HEADER
        programWiki!!.header = headerEditText!!.text.toString()
        programWikis.add(programWiki!!)
        programWiki = ProgramWiki()
        programWiki!!.contentType = ProgramWiki.CONTENT_PROGRAM_EXPLANATION
        programWiki!!.programExplanation = explanationEditText!!.text.toString()
        programWikis.add(programWiki!!)
        programWiki = ProgramWiki()
        programWiki!!.contentType = ProgramWiki.CONTENT_PROGRAM
        programWiki!!.programExample = exampleEditText!!.text.toString()
        programWiki!!.output = ouptputEditText!!.text.toString()
        programWikis.add(programWiki!!)

        wikiModel.wikiHeader = wikiHeaderEditText!!.text.toString()
        wikiModel.wikiId = wikiIdEditText!!.text.toString()
        wikiModel.programWikis = programWikis
        FirebaseDatabaseHandler(context).writeProgramWiki(wikiModel)
    }
}
