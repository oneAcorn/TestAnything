package com.acorn.testanything.genericity

/**
 * Created by acorn on 2019-06-18.
 */
class Comparator<in T> {
    fun compare(e1: T, e2: T): Int {
        return 1
    }
}