package com.acorn.testanything.testWithOutput

/**
 * Created by acorn on 2020/3/1.
 */
interface FakeCall {
    fun request(): FakeRequest
    fun execute(): FakeResponse
    fun enqueue(callback: FakeCallback)
    fun cancel()
    fun isExecuted(): Boolean
    fun isCaceled(): Boolean
}