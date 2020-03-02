package com.acorn.testanything.okhttp

import java.lang.AssertionError

/**
 * Created by acorn on 2020/3/2.
 */
class FakeRealInterceptorChain(
    private var interceptors: List<FakeInterceptor>,
    private var index: Int,
    private var request: FakeRequest,
    private var call: FakeCall,
    private var connectTimeout: Int,
    private var readTimeout: Int,
    private var writeTimeout: Int
) : FakeInterceptor.Chain {


    override fun request(): FakeRequest {
        return request
    }

    override fun proceed(request: FakeRequest): FakeResponse {
        if (index >= interceptors.size) throw AssertionError()

        //Call the next Interceptor in the chain
        val next = FakeRealInterceptorChain(
            interceptors, index + 1, request, call,
            connectTimeout, readTimeout, writeTimeout
        )
        val interceptor = interceptors[index]
        val response = interceptor.intercept(next)
        return response
    }
}