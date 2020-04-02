package com.acorn.testanything.okhttp

/**
 * Created by acorn on 2020/3/1.
 */
class FakeOkHttpClient(builder: Builder) {
    var interceptors: List<FakeInterceptor>
    private var dispatcher: FakeDispatcher
    var connectTimeout: Int = 0
    var readTimeout: Int = 0
    var writeTimeout: Int = 0

    init {
        this.interceptors = builder.interceptors
        this.dispatcher = builder.dispatcher
        this.connectTimeout = builder.connectTimeout
        this.readTimeout = builder.readTimeout
        this.writeTimeout = builder.writeTimeout
    }

    fun dispatcher(): FakeDispatcher {
        return dispatcher
    }

    fun newCall(request: FakeRequest): FakeCall {
        return FakeRealCall(this, request)
    }

    class Builder {
        internal val interceptors: MutableList<FakeInterceptor> = ArrayList()
        internal var dispatcher: FakeDispatcher = FakeDispatcher()
        internal var connectTimeout: Int = 0
        internal var readTimeout: Int = 0
        internal var writeTimeout: Int = 0

        init {
            connectTimeout = 10_000
            readTimeout = 10_000
            writeTimeout = 10_000
        }

        fun addInterceptor(interceptor: FakeInterceptor):Builder{
            interceptors.add(interceptor)
            return this
        }

        fun build(): FakeOkHttpClient{
            return FakeOkHttpClient(this@Builder)
        }
    }
}