package com.sortedqueue.programmercreek.util

import java.util.HashMap

import prettify.PrettifyParser
import syntaxhighlight.ParseResult
import syntaxhighlight.Parser

class PrettifyHighlighter {

    private val parser = PrettifyParser()

    fun highlight(fileExtension: String, sourceCode: String): String {
        val highlighted = StringBuilder()
        val results = parser.parse(fileExtension, sourceCode)
        for (result in results) {
            val type = result.styleKeys[0]
            val content = sourceCode.substring(result.offset, result.offset + result.length)
            highlighted.append(String.format(FONT_PATTERN, getColor(type), content))
        }
        return highlighted.toString()
    }

    private fun getColor(type: String): String {
        return if (COLORS.containsKey(type)) COLORS[type]!! else COLORS["pln"]!!
    }

    companion object {
        //private static final Map<String, String> COLORS = buildColorsMap_Dark_BG();
        private val COLORS = buildColorsMap_White_BG()

        private val FONT_PATTERN = "<font color=\"#%s\">%s</font>"

        internal var mInstance: PrettifyHighlighter? = null
        val instance: PrettifyHighlighter
            get() {
                if (mInstance == null) {
                    mInstance = PrettifyHighlighter()
                }
                return mInstance!!
            }

        private fun buildColorsMap_Dark_BG(): Map<String, String> {
            val map = HashMap<String, String>()
            map.put("typ", "87cefa") //#517C96
            map.put("kwd", "00ff00") //#006600
            map.put("lit", "ffff00") //#6B6B00
            map.put("com", "999999") //#5C5C5C
            map.put("str", "ff4500") //#ff4500
            map.put("pun", "eeeeee") //#8F8F8F
            map.put("pln", "ffffff") //#000000
            return map
        }

        private fun buildColorsMap_White_BG(): Map<String, String> {
            val map = HashMap<String, String>()
            map.put("typ", "517C96") //#517C96
            map.put("kwd", "006600") //#006600
            map.put("lit", "006699") //#6B6B00
            map.put("com", "6B6B00") //#5C5C5C
            map.put("str", "ff4500") //#ff4500
            map.put("pun", "ff4500") //#8F8F8F
            map.put("pln", "000000") //#000000
            return map
        }
    }
}