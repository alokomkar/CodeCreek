package com.sortedqueue.programmercreek.v2.data.helper

class SimpleContent( var contentId : String = "",
                     var contentString : String = "",
                     var contentType : Int = 0 )  {


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
        return if( contentType == content ) {
            val formattedContent = contentString
                    .replace(".\n", ".\n\n")
                    .replace(". ", ".\n\n")
            formattedContent
        }
        else {
            contentString
        }
    }

    companion object {
        const val header = 0
        const val content = 1
        const val bullets = 2
        const val code = 3
        const val image = 4
    }

}