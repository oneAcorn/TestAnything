package com.acorn.testanything.okhttp

/**
 * Created by acorn on 2020/3/1.
 */
interface FakeInterceptor {

    fun intercept(chain: Chain): FakeResponse

    interface Chain {
        fun request(): FakeRequest
        fun proceed(request: FakeRequest):FakeResponse
    }
}