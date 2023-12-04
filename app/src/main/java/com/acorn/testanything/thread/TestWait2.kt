package com.acorn.testanything.thread

import com.acorn.testanything.utils.outputWithInfo
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * 使用wait(),notify()实现的生产者消费者并发模型
 * Created by acorn on 2020/4/12.
 */
fun main() {
    val test = TestWait2()
    test.execute()
}

class TestWait2 {
    private val lock = Object()

    fun execute() {
        val executorService = Executors.newFixedThreadPool(2)
        executorService.execute(Printer())
        executorService.execute(Interrupter())
    }

    inner class Printer : Runnable {
        override fun run() {
            synchronized(lock) {
                while (true) {
                    for (i in 0..10) {
                        outputWithInfo("print:$i")
                    }
                    outputWithInfo("printer wait")
                    lock.wait()
                }
            }
        }
    }

    inner class Interrupter : Runnable {
        override fun run() {
            while (true) {
                val sleepMs = Random().nextInt(100).toLong()
                outputWithInfo("interrupter sleep $sleepMs")
                Thread.sleep(sleepMs)
                synchronized(lock) {
                    outputWithInfo("Interrupter notify")
                    lock.notify()
                }
            }
        }
    }
}