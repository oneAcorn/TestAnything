package com.acorn.testanything.okhttp

import java.io.IOException

/**
 * Created by acorn on 2020/3/2.
 */

fun main() {
    val client = FakeOkHttpClient.Builder()
        .addInterceptor(object : FakeInterceptor {
            override fun intercept(chain: FakeInterceptor.Chain): FakeResponse {
                println("intercept 1 proceed")
                return chain.proceed(chain.request())
            }
        })
        .addInterceptor(object : FakeInterceptor {
            override fun intercept(chain: FakeInterceptor.Chain): FakeResponse {
                println("last intercept generate result")
                return FakeResponse().apply { message = "haha you want access ${chain.request().url()}?" }
            }
        })
        .build()
    val request = FakeRequest.Builder()
        .url("https://www.baidu.com")
        .build()
    val call = client.newCall(request)
    call.enqueue(object : FakeCallback {
        override fun onFailure(call: FakeCall, e: IOException) {
            println("failure:${e.message}")
        }

        override fun onResponse(call: FakeCall, response: FakeResponse) {
            println("res:${response.message}")
        }

    })
}