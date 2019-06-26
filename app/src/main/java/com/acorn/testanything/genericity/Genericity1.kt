package com.acorn.testanything.genericity

/**
 * Created by acorn on 2019-06-18.
 */
interface Genericity1<out T> {
    fun <T> next(): T
}