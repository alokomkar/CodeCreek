package com.sortedqueue.programmercreek.util

import java.util.ArrayList
import java.util.Random

object ShuffleList {

    fun shuffleList(randomList: ArrayList<String>): ArrayList<String> {
        val n = randomList.size
        val random = Random()
        random.nextInt()
        for (i in 0..n - 1) {
            val change = i + random.nextInt(n - i)
            swap(randomList, i, change)
        }
        return randomList
    }


    private fun swap(list: ArrayList<String>, i: Int, change: Int) {
        val helper = list[i]
        list[i] = list[change]
        list[change] = helper
    }

}
