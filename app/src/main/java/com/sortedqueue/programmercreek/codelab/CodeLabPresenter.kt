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


        var codeBody = "#include \"stdio.h\"\n" + "#include \"conio.h\""
        when( programLanguage ) {
            "c" -> {
                codeShortCuts.add(CodeShortCuts("printf", "printf(\"\");"))
                codeShortCuts.add(CodeShortCuts("scanf", "scanf(\"\");"))
            }
            "java" -> {

                codeBody = "import java.util.*;\n" +
                        "import java.lang.*;\n" +
                        "import java.io.*;\n" +
                        "\n" +
                        "class CodeField\n" +
                        "{\n" +
                        "\tpublic static void main (String[] args) throws java.lang.Exception\n" +
                        "\t{\n" +
                        "\t}\n" +
                        "}"
                codeShortCuts.add(CodeShortCuts("sopln", "System.out.println(\"\");"))
                codeShortCuts.add(CodeShortCuts("sop", "System.out.println(\"\");"))
                codeShortCuts.add(CodeShortCuts("read_int", "Scanner sc = new Scanner(System.in);\n" +
                        "int inputInteger = sc.nextInt();"))
                codeShortCuts.add(CodeShortCuts("read_string", "Scanner sc = new Scanner(System.in);\\n\" +\n" +
                        "                        \"String inputString = sc.nextLine();\""))
            }
            "cpp", "c++" -> {
                codeBody = "#include \"iostream.h\"\n" +
                        "#include \"conio.h\"\n" +
                        "using namespace std;\n\n" +
                        "int main() {" +
                        "\n\n\n\n" +
                        "return 0;" +
                        "}"
                codeShortCuts.add(CodeShortCuts("cout", "cout << "))
                codeShortCuts.add(CodeShortCuts("cin", "cin >> "))
                codeShortCuts.add(CodeShortCuts("cerr", "cerr >> "))
            }
        }


        codeLabView.getCodeShortCuts(codeShortCuts, codeBody)
        codeLabView.hideProgress()
    }

    private val TAG: String = CodeLabPresenter::class.java.simpleName
    var trialMode = 0

    fun executeCode(input : String, sourceCode : String ) {

        val codeMap = HashMap<String, String>()
        codeMap.put("language", selectedLanguageIndex)
        codeMap.put("sourceCode", sourceCode)
        codeMap.put("input", input)
        codeLabView.startCodeExecuteAnimation()
        Handler().postDelayed({
            if( trialMode == 0 ) {
                codeLabView.onOutputSuccess("Emulate Success")
                codeLabView.stopCodeExecuteAnimation()
                trialMode = 1
            }
            else {
                trialMode = 0
                codeLabView.onOutputError("Emulate Failure")
                codeLabView.stopCodeExecuteAnimation()
            }

        }, 5000)
        /*val idResponseCall = submitCodeService.postCode(codeMap, RetrofitCreator.getTokenCompilerApi())
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
        })*/
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