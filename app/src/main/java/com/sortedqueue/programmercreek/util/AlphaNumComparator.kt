package com.sortedqueue.programmercreek.util

/**
 * Created by Alok on 09/01/17.
 */

/*
     * The Alphanum Algorithm is an improved sorting algorithm for strings
     * containing numbers.  Instead of sorting numbers in ASCII order like
     * a standard sort, this algorithm sorts numbers in numeric order.
     *
     * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
     *
     *
     * This library is free software; you can redistribute it and/or
     * modify it under the terms of the GNU Lesser General Public
     * License as published by the Free Software Foundation; either
     * version 2.1 of the License, or any later version.
     *
     * This library is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
     * Lesser General Public License for more details.
     *
     * You should have received a copy of the GNU Lesser General Public
     * License along with this library; if not, write to the Free Software
     * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
     *
     */

import java.util.Comparator

/**
 * This is an updated version with enhancements made by Daniel Migowski,
 * Andre Bogus, and David Koelle

 * To convert to use Templates (Java 1.5+):
 * - Change "implements Comparator" to "implements Comparator<String>"
 * - Change "compare(Object o1, Object o2)" to "compare(String s1, String s2)"
 * - Remove the type checking and casting in compare().

 * To use this class:
 * Use the static "sort" method from the java.util.Collections class:
 * Collections.sort(your list, new AlphanumComparator());
</String> */
class AlphaNumComparator : Comparator<String> {
    private var comparator = NaturalComparator()


    constructor() {

    }

    private fun isDigit(ch: Char): Boolean {
        return ch.toInt() >= 48 && ch.toInt() <= 57
    }

    /** Length of string is passed in for improved efficiency (only need to calculate it once)  */
    private fun getChunk(s: String, slength: Int, marker: Int): String {
        var marker = marker
        val chunk = StringBuilder()
        var c = s[marker]
        chunk.append(c)
        marker++
        if (isDigit(c)) {
            while (marker < slength) {
                c = s[marker]
                if (!isDigit(c))
                    break
                chunk.append(c)
                marker++
            }
        } else {
            while (marker < slength) {
                c = s[marker]
                if (isDigit(c))
                    break
                chunk.append(c)
                marker++
            }
        }
        return chunk.toString()
    }

    override fun compare(s1: String, s2: String): Int {

        var thisMarker = 0
        var thatMarker = 0
        val s1Length = s1.length
        val s2Length = s2.length

        while (thisMarker < s1Length && thatMarker < s2Length) {
            val thisChunk = getChunk(s1, s1Length, thisMarker)
            thisMarker += thisChunk.length

            val thatChunk = getChunk(s2, s2Length, thatMarker)
            thatMarker += thatChunk.length

            // If both chunks contain numeric characters, sort them numerically
            var result = 0
            if (isDigit(thisChunk[0]) && isDigit(thatChunk[0])) {
                // Simple chunk comparison by length.
                val thisChunkLength = thisChunk.length
                result = thisChunkLength - thatChunk.length
                // If equal, the first different number counts
                if (result == 0) {
                    for (i in 0..thisChunkLength - 1) {
                        result = thisChunk[i] - thatChunk[i]
                        if (result != 0) {
                            return result
                        }
                    }
                }
            } else {
                result = comparator.compare(thisChunk, thatChunk)
            }

            if (result != 0)
                return result
        }

        return s1Length - s2Length
    }

    private class NaturalComparator : Comparator<String> {
        override fun compare(o1: String, o2: String): Int {
            return o1.compareTo(o2)
        }
    }
}
