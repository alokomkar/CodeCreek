package com.sortedqueue.programmercreek.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.LanguageConstants
import com.sortedqueue.programmercreek.database.firebase.Code
import com.sortedqueue.programmercreek.interfaces.CodeLabNavigationListener
import kotlinx.android.synthetic.main.fragment_code_language_list.*


/**
 * Created by Alok on 12/04/17.
 */

class CodeLanguageFragment : Fragment(), View.OnClickListener {


    private var codeLabNavigationListener: CodeLabNavigationListener? = null
    private var code: Code? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_code_language_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        code = Code()
        code!!.language = LanguageConstants.C_INDEX
        code!!.sourceCode = "#include<stdio.h>\n" +
                "#include<math.h>\n" +
                "int main(void)\n" +
                "{\n" +
                "   double Adjacent=2, Opposite=3, Hypotenuse=4;\n" +
                "   //Hypotenuse\n" +
                "   double Hypotenuse1 = (pow(Adjacent,2)) + (pow(Opposite,2));\n" +
                "   Hypotenuse1=sqrt(Hypotenuse1);\n" +
                "   printf(\"\\nHypotenuse: %lf\",Hypotenuse1);\n" +
                "   //Adjacent\n" +
                "   double Adjacent1 = (pow(Hypotenuse,2)) - (pow(Opposite,2)) ;\n" +
                "   Adjacent1=sqrt(Adjacent1);\n" +
                "   printf(\"\\nAdjacent: %lf\",Adjacent1);\n" +
                "     \n" +
                "   //Opposite\n" +
                "   double Opposite1 = (pow(Hypotenuse,2)) - (pow(Adjacent,2));\n" +
                "   Opposite1=sqrt(Opposite1);\n" +
                "   printf(\"\\nOpposite: %lf\",Opposite1);\n" +
                "   return 0;\n" +
                "}\n"
        setupListeners()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CodeLabNavigationListener) {
            codeLabNavigationListener = context
        }
    }

    private fun setupListeners() {
        cProgramsTextView!!.setOnClickListener(this)
        cppProgramsTextView!!.setOnClickListener(this)
        javaProgramsTextView!!.setOnClickListener(this)
        adaProgramsTextView!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cProgramsTextView -> codeLabNavigationListener!!.loadCompileCodeFragment(code)
            R.id.cppProgramsTextView -> codeLabNavigationListener!!.loadCompileCodeFragment(code)
            R.id.javaProgramsTextView -> codeLabNavigationListener!!.loadCompileCodeFragment(code)
            R.id.adaProgramsTextView -> codeLabNavigationListener!!.loadCompileCodeFragment(code)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var codeLanguageFragment: CodeLanguageFragment? = null
        val instance: CodeLanguageFragment
            get() {
                if (codeLanguageFragment == null) {
                    codeLanguageFragment = CodeLanguageFragment()
                }
                return codeLanguageFragment!!
            }
    }
}
