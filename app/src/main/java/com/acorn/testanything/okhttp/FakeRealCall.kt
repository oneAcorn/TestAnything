package com.acorn.testanything.okhttp

import okhttp3.internal.NamedRunnable
import java.io.IOException
import java.io.InterruptedIOException
import java.lang.IllegalStateException
import java.util.concurrent.ExecutorService
import java.util.concurrent.RejectedExecutionException

/**
 * Created by acorn on 2020/3/1.
 */
class FakeRealCall(private var client: FakeOkHttpClient, private var originalRequest: FakeRequest) : FakeCall {
    private var executed = false

    override fun request(): FakeRequest {
        return originalRequest
    }

    override fun execute(): FakeResponse? {
        synchronized(this) {
            if (executed) throw IllegalStateException("Already Executed")
            executed = true
        }
        try {
            client.dispatcher().executed(this)
            val response = getResponseWithInterceptorChain()
            return response
        } catch (e: IOException) {

        } finally {
            client.dispatcher().finished(this@FakeRealCall)
        }
        return null
    }

    override fun enqueue(callback: FakeCallback) {
        synchronized(this) {
            check(!executed) { "Already Executed" }
            executed = true
        }
        client.dispatcher().enqueue(AsyncCall(callback))
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

    private fun getResponseWithInterceptorChain(): FakeResponse {
        val interceptors = mutableListOf<FakeInterceptor>()
        interceptors.addAll(client.interceptors)
        val chain = FakeRealInterceptorChain(
            interceptors, 0, originalRequest, this, client.connectTimeout,
            client.readTimeout, client.writeTimeout
        )
        return chain.proceed(originalRequest)
    }

    inner class AsyncCall(private val responseCallback: FakeCallback) : NamedRunnable("sa", "sdf") {

        fun executeOn(executorService: ExecutorService) {
            assert(!Thread.holdsLock(client.dispatcher()))
            var success = false
            try {
                executorService.execute(this)
                success = true
            } catch (e: RejectedExecutionException) {
                val interruptIoException = InterruptedIOException("executor rejected")
                interruptIoException.initCause(e)
                responseCallback.onFailure(this@FakeRealCall, interruptIoException)
            } finally {
                if (!success) {
                    client.dispatcher().finished(this)
                }
            }
        }

        fun host(): String? {
            return originalRequest.url()?.host()
        }

        override fun execute() {
            try {
                val response = getResponseWithInterceptorChain()
                responseCallback.onResponse(this@FakeRealCall, response)
            } catch (e: IOException) {

            } finally {
                client.dispatcher().finished(this)
            }
        }

    }
}