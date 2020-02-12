package com.acorn.testanything.rxjava

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

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
