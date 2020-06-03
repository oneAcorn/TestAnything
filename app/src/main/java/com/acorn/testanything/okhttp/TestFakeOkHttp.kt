package com.acorn.testanything.okhttp

import com.acorn.testanything.testWithOutput.IOutput
import com.acorn.testanything.testWithOutput.ITest
import java.io.IOException

/**
 * Created by acorn on 2020/3/2.
 */

fun main() {
    TestFakeOkHttp().test(object : IOutput {
        override fun output(str: String) {
            println(str)
        }

        override fun outputByThread(str: String) {
            TODO("Not yet implemented")
        }

        override fun clearLog() {

        }

        override fun log(str: String) {}
    })
}

class TestFakeOkHttp : ITest {
    override fun test(output: IOutput) {
        with(output) {
            val client = FakeOkHttpClient.Builder()
                .addInterceptor(object : FakeInterceptor {
                    override fun intercept(chain: FakeInterceptor.Chain): FakeResponse {
                        output("intercept 1 proceed")
                        return chain.proceed(chain.request())
                    }
                })
                .addInterceptor(object : FakeInterceptor {
                    override fun intercept(chain: FakeInterceptor.Chain): FakeResponse {
                        output("last intercept generate result")
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
                    output("failure:${e.message}")
                }

                override fun onResponse(call: FakeCall, response: FakeResponse) {
                    output("res:${response.message}")
                }

            })
        }
    }
}