package com.acorn.testanything.thread

import com.acorn.testanything.utils.outputWithInfo
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * 使用wait(),notify()实现的生产者消费者并发模型
 * Created by acorn on 2020/4/12.
 */
fun main() {
    val test = TestWait()
    test.execute()
}

class TestWait {
    private var count = AtomicInteger()
    private val full = 3
    private val lock = Object()

    fun execute() {
        val executorService = Executors.newFixedThreadPool(8)
        for (i in 0..3) {
            executorService.execute(Producer())
            executorService.execute(Consumer())
        }
    }

    inner class Producer : Runnable {
        override fun run() {
            for (i in 0..9) {
                Thread.sleep(Random().nextInt(2500) + 500L)
                synchronized(lock) {
                    while (count.get() == full) { //进入等待状态
                        outputWithInfo("生产者进入等待状态")
                        lock.wait()
                    }
                    count.incrementAndGet()
                    outputWithInfo("生产了,余量$count")
                    lock.notifyAll()
                }
            }
        }
    }

    inner class Consumer : Runnable {
        override fun run() {
            for (i in 0..9) {
                Thread.sleep(Random().nextInt(2500) + 500L)
                synchronized(lock) {
                    while (count.get() == 0) {
                        outputWithInfo("消费者进入等待状态")
                        lock.wait()
                    }
                    count.decrementAndGet()
                    outputWithInfo("消费了,余量$count")
                    lock.notifyAll()
                }
            }
        }
    }
}