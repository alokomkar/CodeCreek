package com.sortedqueue.programmercreek.codelab

import android.os.Handler
import android.util.Log
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.constants.LanguageConstants
import com.sortedqueue.programmercreek.database.CodeShortCuts
import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse
import com.sortedqueue.programmercreek.database.firebase.IdResponse
import com.sortedqueue.programmercreek.interfaces.retrofit.SubmitCodeService
import com.sortedqueue.programmercreek.network.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Alok Omkar on 2017-11-19.
 */
class CodeLabPresenter( val codeLabView : CodeLabView ) {

    private var submitCodeService: SubmitCodeService = RetrofitCreator.createService(SubmitCodeService::class.java)
    private lateinit var programLanguage : String
    private var selectedLanguageIndex = LanguageConstants.C_INDEX

    fun getCodeShortCutsForLanguage( programLanguage : String ) {
        codeLabView.showProgress(R.string.loading)
        this.programLanguage = programLanguage
        val codeShortCuts = ArrayList<CodeShortCuts>()
        codeShortCuts.add(CodeShortCuts("{}", "{\n\n}"))
        codeShortCuts.add(CodeShortCuts("TAB", "    "))
        codeShortCuts.add(CodeShortCuts(";", ";"))
        codeShortCuts.add(CodeShortCuts("++", "++"))
        codeShortCuts.add(CodeShortCuts("--", "--"))
        codeShortCuts.add(CodeShortCuts("<", "<"))
        codeShortCuts.add(CodeShortCuts(">", ">"))
        codeShortCuts.add(CodeShortCuts("()", "()"))
        codeShortCuts.add(CodeShortCuts("main", "void main()\n{\n\n\n}"))
        codeShortCuts.add(CodeShortCuts("int main", "int main()\n{\n\n\nreturn0;\n}"))
        codeShortCuts.add(CodeShortCuts("do_while", "do{ \n\n }while();"))
        codeShortCuts.add(CodeShortCuts("for_loop", "for( ; ; ){\n\n}"))
        codeShortCuts.add(CodeShortCuts("if", "if(  ){\n\n}"))
        codeShortCuts.add(CodeShortCuts("else", "else{\n\n}"))
        codeShortCuts.add(CodeShortCuts("else_if", "else{\n\n}"))
        codeShortCuts.add(CodeShortCuts("printf", "printf(\"\");"))
        codeShortCuts.add(CodeShortCuts("scanf", "scanf(\"\");"))
        codeShortCuts.add(CodeShortCuts("stdio", "#include \"stdio.h\""))
        codeShortCuts.add(CodeShortCuts("conio", "#include \"conio.h\""))
        codeLabView.getCodeShortCuts(codeShortCuts)
        codeLabView.hideProgress()
    }

    private val TAG: String = CodeLabPresenter::class.java.simpleName

    fun executeCode(input : String, sourceCode : String ) {

        val codeMap = HashMap<String, String>()
        codeMap.put("language", selectedLanguageIndex)
        codeMap.put("sourceCode", sourceCode)
        codeMap.put("input", input)
        codeLabView.startCodeExecuteAnimation()
        val idResponseCall = submitCodeService.postCode(codeMap, RetrofitCreator.getTokenCompilerApi())
        idResponseCall.enqueue(object : Callback<IdResponse> {
            override fun onResponse(call: Call<IdResponse>, response: Response<IdResponse>) {
                Log.d(TAG, "Execute Response : " + response.body().toString())
                Handler().postDelayed({ getProgramOutput(response.body()!!) }, 4000)
            }

            private fun getProgramOutput(body: IdResponse) {
                getOutputResponse(body.id!!)
            }

            override fun onFailure(call: Call<IdResponse>, t: Throwable) {
                Log.e(TAG, "Execute Error : " + t.message)
                t.printStackTrace()
                codeLabView.onError("Execute Error : " + t.message)
                codeLabView.stopCodeExecuteAnimation()
            }
        })
    }

    private fun getOutputResponse(submissionId: Int) {
        val codeOutputResponseCall = submitCodeService.getOutput(
                submissionId,
                RetrofitCreator.getTokenCompilerApi(),
                true,
                true,
                true,
                true,
                true)
        codeOutputResponseCall.enqueue(object : Callback<CodeOutputResponse> {
            override fun onResponse(call: Call<CodeOutputResponse>, response: Response<CodeOutputResponse>) {
                Log.d(TAG, "Output Response : " + response.body().toString())
                codeLabView.onOutputSuccess(response.body()!!.output)
                codeLabView.stopCodeExecuteAnimation()
            }

            override fun onFailure(call: Call<CodeOutputResponse>, t: Throwable) {
                Log.e(TAG, "Output Error : " + t.message)
                t.printStackTrace()
                codeLabView.onOutputError("Output Error : " + t.message)
                codeLabView.stopCodeExecuteAnimation()
            }
        })
    }
}