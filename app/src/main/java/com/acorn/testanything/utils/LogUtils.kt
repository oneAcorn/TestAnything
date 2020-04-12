package com.acorn.testanything.utils

import android.util.Log
import java.util.*

/**
 * Created by acorn on 2019-05-30.
 */


fun log(string: String) {
    Log.i("pagumaLarvata", string)
}

fun outputWithInfo(string: String) {
    println("${Thread.currentThread().name} ${Date(System.currentTimeMillis())}:$string")
}