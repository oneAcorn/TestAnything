package com.acorn.testanything.kotlin

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Created by acorn on 2020/4/28.
 */
fun main() {
//    test1()
    test2()
}

private fun test1() {
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

/**
 * 惰性启动的 async
可选的，async 可以通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的。
在这个模式下，只有结果通过 await 获取的时候协程才会启动，
或者在 Job 的 start 函数调用的时候。运行下面的示例：
 */
private fun test2() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomthing1() }
        val two = async(start = CoroutineStart.LAZY) { doSomthing2() }
        two.start()
        one.start()
        println("start")
        delay(1000L)
        println("start calculating")
        println("The answer is ${one.await() + two.await()}")
    }
    println("Total timeMill:$time")
}

suspend fun doSomthing1(): Int {
    println("doSomthing1")
    delay(1000L)
    return 22
}

suspend fun doSomthing2(): Int {
    println("doSomthing2")
    delay(1000L)
    return 33
}