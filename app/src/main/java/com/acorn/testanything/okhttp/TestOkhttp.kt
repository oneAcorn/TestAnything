package com.acorn.testanything.okhttp

import com.acorn.testanything.testWithOutput.IOutput
import com.acorn.testanything.testWithOutput.ITest
import okhttp3.*
import java.io.IOException

/**
 * Created by acorn on 2020/3/1.
 */
class TestOkhttp : ITest {
    override fun test(output: IOutput) {
        with(output) {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    val builder = request.newBuilder()
                    request = builder.addHeader("anyHeader", "haha").build()
                    println("Interceptor1 addHeader ${request.header("anyHeader")}")
                    chain.proceed(request)
                }
                .addInterceptor { chain ->
                    println("Interceptor2")
                    chain.proceed(chain.request())
                }
                .build()

            val request = Request.Builder()
                .url("https://www.baidu.com")
                .get()
                .build()
            val call: Call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("onFailure:${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    println("onResponse:${response.networkResponse().toString()}")
                }
            })
        }
    }
}