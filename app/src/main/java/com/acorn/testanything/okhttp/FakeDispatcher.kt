package com.acorn.testanything.okhttp

import okhttp3.internal.Util
import java.lang.AssertionError
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/3/1.
 */
class FakeDispatcher {
    private val maxRequests = 64
    private val maxRequestPerHost = 5
    //准备执行的任务的双向队列
    private val readyAsyncCalls = ArrayDeque<FakeRealCall.AsyncCall>()
    //正在执行的任务的双向队列
    private val runningAsyncCalls = ArrayDeque<FakeRealCall.AsyncCall>()
    private val runningSyncCalls = ArrayDeque<FakeRealCall>()
    private var executorService: ExecutorService? = null

    @Synchronized
    fun executorService(): ExecutorService {
        //创建核心线程数为0,最大线程数无限的线程池,总体类似Executors.newCachedThreadPool()
        return executorService ?: ThreadPoolExecutor(
            0, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
            SynchronousQueue<Runnable>(), Util.threadFactory("FakeOkHttp Dispatcher", false)
        )
    }

    @Synchronized
    fun executed(call: FakeRealCall) {
        runningSyncCalls.add(call)
    }

    fun enqueue(call:FakeRealCall.AsyncCall){
        synchronized(this){
            readyAsyncCalls.add(call)
        }
        promoteAndExecute()
    }

    private fun promoteAndExecute() {
        assert(!Thread.holdsLock(this))
        val executableCalls = mutableListOf<FakeRealCall.AsyncCall>()
        synchronized(this) {
            val iterator = readyAsyncCalls.iterator()
            while (iterator.hasNext()) {
                val asyncCall = iterator.next()
                if (runningAsyncCalls.size > maxRequests) { //不能大于最大请求数64
                    break
                }
                if (runningCallsForHost(asyncCall) > maxRequestPerHost) { //每个Host最多同时请求5个
                    continue
                }
                iterator.remove() //从准备执行的任务队列中移除
                //加入到执行中的队列
                executableCalls.add(asyncCall)
                runningAsyncCalls.add(asyncCall)
            }
        }

        for (asyncCall in executableCalls) {
            asyncCall.executeOn(executorService())
        }
    }

    private fun runningCallsForHost(asyncCall: FakeRealCall.AsyncCall): Int {
        var count = 0
        for (call in runningAsyncCalls) {
            if (asyncCall.host() != null && asyncCall.host() == call.host()) {
                count++
            }
        }
        return count
    }

    fun finished(asyncCall: FakeRealCall.AsyncCall) {
        finished(runningAsyncCalls, asyncCall)
    }

    fun finished(call: FakeRealCall) {
        finished(runningSyncCalls, call)
    }

    private fun <T> finished(calls: Deque<T>, call: T) {
        synchronized(this) {
            if (!calls.remove(call)) throw AssertionError("Call wasn't in-flight!")
        }
        promoteAndExecute()
    }
}