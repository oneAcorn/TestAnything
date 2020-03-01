package com.acorn.testanything.testWithOutput

import java.lang.AssertionError
import java.util.*

/**
 * Created by acorn on 2020/3/1.
 */
class FakeDispatcher {
    private val maxRequests = 64
    private val maxRequestPerHost = 5

    private fun promoteAndExecute():Boolean{
        return false
    }

    private fun <T> finished(calls: Deque<T>, call: T) {
        synchronized(this) {
            if (!calls.remove(call)) throw AssertionError("Call wasn't in-flight!")
        }
        promoteAndExecute()
    }
}