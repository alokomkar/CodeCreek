package com.sortedqueue.programmercreek.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R

import java.util.ArrayList
import java.util.HashMap



import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import kotlinx.android.synthetic.main.fragment_code_view.*

/**
 * Created by Alok Omkar on 2017-01-26.
 */

class CodeViewFragment : Fragment() {

    var programCode: ArrayList<String>? = null
        private set
    private var allCodeMap: HashMap<Int, ArrayList<String>>? = null
    private var programLanguage: String? = null
    private var totalModules: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_code_view, container, false)
        programLanguage = CreekApplication.creekPreferences!!.programLanguage
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        programCode = ArrayList<String>()
        allCodeMap = HashMap<Int, ArrayList<String>>()
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun submitSubTest(index: Int, content: ArrayList<String>) {
        if (allCodeMap == null) {
            allCodeMap = HashMap<Int, ArrayList<String>>()
        }
        if (programCode == null) {
            programCode = ArrayList<String>()
        }
        allCodeMap!!.put(index, content)
        programCode!!.clear()
        for (indexKey in 0..totalModules - 1) {
            val arrayList = allCodeMap!![indexKey]
            if (arrayList != null)
                programCode!!.addAll(arrayList)
        }
        initCodeView()
    }

    private fun initCodeView() {
        if (programCodeView != null) {
            var programLines = ""
            var position = 1
            var programDescription = ""
            for (program_table in programCode!!) {
                if (position == 1) {
                    programDescription += position.toString() + ". " + program_table
                    programLines += position++.toString() + ". "
                } else {
                    programDescription += "\n" + position + ". " + program_table
                    programLines += "\n" + position++ + ". "
                }
                programLines += program_table.trim { it <= ' ' }
            }
            programCodeView!!
                    .setOptions(Options.get(context)
                            .withLanguage(programLanguage!!)
                            .withCode(programLines)
                            .withTheme(ColorTheme.SOLARIZED_LIGHT))
        }

    }

    fun setTotalModules(totalModules: Int) {
        this.totalModules = totalModules
    }
}
