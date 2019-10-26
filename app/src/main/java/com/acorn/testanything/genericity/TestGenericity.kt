package com.acorn.testanything.genericity

import android.view.ViewGroup
import android.widget.LinearLayout
import java.util.*

/**
 * Created by acorn on 2019-06-18.
 */

fun main() {
    val comparator = Comparator<Number>()
    comparator.compare(1, 2f)
    val fatherList = mutableListOf<ViewGroup>()
    val sonList = mutableListOf<LinearLayout>()
    Collections.copy(fatherList, sonList)
    //error!
    //Collections.copy(sonList,fatherList)
}

class TestGenericity {
    fun test1(g: Genericity1<LinearLayout>) {
        val g2: Genericity1<ViewGroup> = g.next()
    }
}