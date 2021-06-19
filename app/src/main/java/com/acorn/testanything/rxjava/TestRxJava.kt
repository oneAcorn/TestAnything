package com.acorn.testanything.rxjava

import android.graphics.Bitmap
import com.acorn.testanything.utils.MyException
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2019/12/11.
 */

fun main() {
    test()
}

fun test() {
    Observable.fromArray(arrayListOf("1", "2", 3))
        .subscribe() { t: ArrayList<Any>? ->
            println(t.toString())
        }
}



