package com.acorn.testanything.testWithOutput

import okhttp3.internal.NamedRunnable
import java.lang.IllegalStateException
import java.util.concurrent.ExecutorService

/**
 * Created by acorn on 2020/3/1.
 */
class FakeRealCall : FakeCall {
    private lateinit var client: FakeOkHttpClient
    private lateinit var originalRequest: FakeRequest
    private var executed = false

    fun FakeRealCall(client: FakeOkHttpClient, originalRequest: FakeRequest) {
        this.client = client
        this.originalRequest = originalRequest
    }

    override fun request(): FakeRequest {
        return originalRequest
    }

    override fun execute(): FakeResponse {
        synchronized(this) {
            if (executed) throw IllegalStateException("Already Executed")
            executed = true
        }
        return FakeResponse()
    }

    override fun enqueue(callback: FakeCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExecuted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCaceled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class AsyncCall(val responseCallback:FakeCallback) :NamedRunnable("sa","sdf"){

        fun executeOn(executorService: ExecutorService){
            assert(!Thread.holdsLock(client.dispatcher()))
        }

        override fun execute() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}