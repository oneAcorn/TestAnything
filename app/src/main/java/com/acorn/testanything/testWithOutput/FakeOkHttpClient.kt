package com.acorn.testanything.testWithOutput

/**
 * Created by acorn on 2020/3/1.
 */
class FakeOkHttpClient {
    private lateinit var interceptors: List<FakeInterceptor>
    private lateinit var dispatcher: FakeDispatcher
    private var connectTimeout: Int = 0
    private var readTimeout: Int = 0
    private var writeTimeout: Int = 0

    fun FakeOkHttpClient(builder: Builder) {
        this.interceptors = builder.interceptors
        this.dispatcher = builder.dispatcher
        this.connectTimeout = builder.connectTimeout
        this.readTimeout = builder.readTimeout
        this.writeTimeout = builder.writeTimeout
    }

    public fun dispatcher(): FakeDispatcher {
        return dispatcher
    }

    companion object {
        class Builder {
            val interceptors: List<FakeInterceptor> = arrayListOf()
            lateinit var dispatcher: FakeDispatcher
            var connectTimeout: Int = 0
            var readTimeout: Int = 0
            var writeTimeout: Int = 0

            fun Builder() {
                dispatcher = FakeDispatcher()
                connectTimeout = 10_000
                readTimeout = 10_000
                writeTimeout = 10_000
            }
        }
    }
}