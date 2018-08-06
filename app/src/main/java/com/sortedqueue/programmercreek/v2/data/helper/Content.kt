package com.sortedqueue.programmercreek.v2.data.helper

data class Content(
        var contentString : String = "",
        var contentType : ContentType = ContentType(-1, "")
) {
    var isSelected : Boolean = false
}