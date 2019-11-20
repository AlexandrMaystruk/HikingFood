package com.gmail.maystruks08.hikingfood

import android.util.Log
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val stepsCount = 2
        val startFrom = 0
        val nodeTextList = mutableListOf("3", "O", "У")
        if (startFrom != 0) {
            for (i in 0 until startFrom) {
                nodeTextList.add(nodeTextList.removeAt(0))
            }
        }
        while (nodeTextList.lastIndex >= stepsCount){
            nodeTextList.removeAt(nodeTextList.lastIndex)
        }

        Log.d("TAGG", nodeTextList.toString())


    }
}
