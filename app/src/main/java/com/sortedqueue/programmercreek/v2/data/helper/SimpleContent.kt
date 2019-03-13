package com.sortedqueue.programmercreek.v2.data.helper

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import java.util.regex.Pattern


class SimpleContent(var contentId: String = "",
                    var contentString: String = "",
                    var contentType: Int = 0,
                    var correctOptions: String = "") : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleContent

        if (contentId != other.contentId) return false

        return true
    }

    override fun hashCode(): Int {
        return contentId.hashCode()
    }

    fun getFormattedContent(): String {
        return if (contentType == content) {
            val formattedContent = contentString
                    .replace(".\n", ".\n\n")
                    .replace(". ", ".\n\n")
            formattedContent
        } else {
            contentString
        }
    }

    fun getFormattedContentList() : ArrayList<SimpleContent> {
        val formattedContent = getFormattedContent().split(".\n\n")
        val contentList = ArrayList<SimpleContent>()
        for( content in formattedContent ) {
            contentList.add(SimpleContent("",content.trim(), SimpleContent.content) )
        }
        return contentList
    }

    private fun getFillBlanksOptions(): ArrayList<String> {
        val p = Pattern.compile("<<(.*?)>>")
        val m = p.matcher(contentString)
        val options = ArrayList<String>()
        while (m.find()) {
            options.add(m.group(1))
        }
        return options
    }

    fun getFillBlanksQuestion(): String {

        val p = Pattern.compile("<<(.*?)>>")
        val m = p.matcher(contentString)
        var question = contentString
        while (m.find()) {
            question = question.replace(m.group(1), "________")
        }
        return question
    }

    var isAnimated = false

    fun getQuestion() : String = contentString.split("?")[0] + "?"

    fun getCode(): String = contentString.split("?")[1]

    fun getSyntax() : String = contentString.split("?")[1].split("Output :")[0]

    fun getSyntaxOutput(): String =  "Output :\n"+ contentString.split("?")[1].split("Output :")[1]

    fun getQuestionOptions() : ArrayList<String> {
      return when (contentType) {
          codeMcq -> ArrayList(contentString.split("??")[1].split("|||"))
          fillBlanks -> getFillBlanksOptions()
          else -> ArrayList(contentString.split("?")[1].split("|||"))
      }
    }

    fun getCorrectOptions() : ArrayList<String> {
      return if( contentType == fillBlanks ) getFillBlanksOptions()
          else
          ArrayList(correctOptions.split("|||"))
    }

    fun getSyntaxOptions() : ArrayList<String> {
        val options = ArrayList<String>()
        for ( option in getSyntax().split(" ") )
            if( option.trim().isNotEmpty() ) {
                options.add("$option ")
            }
        return options
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(contentId)
        writeString(contentString)
        writeInt(contentType)
        writeString(correctOptions)
    }


    fun checkAnswer( answerString: String ) : String {

        val p = Pattern.compile("<<(.*?)>>")
        val matcherQn = p.matcher(contentString)
        val matcherAns = p.matcher(answerString)

        var answer = answerString
        while (matcherAns.find() && matcherQn.find()) {
            answer = if( matcherAns.group(1) == matcherQn.group(1) ) {
                answer.replace(matcherAns.group(1), "<font color=\"#7CB342\">" +
                        "${matcherAns.group(1)}</font>")
            } else {
                answer.replace(matcherAns.group(1), "<font color=\"#FF5722\">" +
                        "${matcherAns.group(1)}</font>")
            }
        }
        return answer
    }




    companion object {
        const val header = 0

        const val content = 1

        const val bullets = 2

        const val code = 3

        const val image = 4

        const val mcq = 5

        const val rearrange = 6

        const val fillBlanks = 7

        const val codeMcq = 8 //Contains code in Question

        const val syntaxLearn = 9

        @JvmField
        val CREATOR: Parcelable.Creator<SimpleContent> = object : Parcelable.Creator<SimpleContent> {
            override fun createFromParcel(source: Parcel): SimpleContent = SimpleContent(source)
            override fun newArray(size: Int): Array<SimpleContent?> = arrayOfNulls(size)
        }
    }
}