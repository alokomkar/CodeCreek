package com.sortedqueue.programmercreek.codelab

import com.sortedqueue.programmercreek.database.CodeShortCuts
import java.util.*

/**
 * Created by Alok Omkar on 2017-11-19.
 */
class CodeLabPresenter( val codeLabView : CodeLabView ) {

    init {

    }

    fun getCodeShortCutsForLanguage( programLanguage : String ) {
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
    }
}