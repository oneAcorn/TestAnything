package com.acorn.testanything.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by acorn on 2020/4/28.
 */
fun main() {
    val job = GlobalScope.launch { //在后台启用新协程并继续
        delay(1000) //非阻塞延迟1秒
        println("World")
    }
    println("Hello")

    Thread.sleep(1000) //阻塞主线程1秒，保证jvm存活
    runBlocking { //和上一行的sleep效果相同，都是阻塞线程
        delay(1000)
    }
}

class TestCoroutines {
}