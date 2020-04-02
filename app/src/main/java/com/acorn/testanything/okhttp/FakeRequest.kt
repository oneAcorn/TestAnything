package com.acorn.testanything.okhttp

import okhttp3.HttpUrl

/**
 * Created by acorn on 2020/3/1.
 */
class FakeRequest(builder: Builder) {
    private var url: HttpUrl? = null
    private lateinit var method: String

    init {
        this.url = builder.url
        this.method = builder.method
    }

    fun url(): HttpUrl? {
        return url
    }

    class Builder() {
        internal var url: HttpUrl? = null
        internal lateinit var method: String

        init {
            method = "GET"
        }

        constructor(request: FakeRequest? = null) : this() {
            this.method = request?.method ?: "GET"
            this.url = request?.url
        }

        fun url(url: HttpUrl): Builder {
            this.url = url
            return this
        }

        fun url(url: String): Builder {
            var mutableUrl = url
            if (url.regionMatches(0, "ws:", 0, 3, ignoreCase = true)) {
                mutableUrl = "http:" + url.substring(3)
            } else if (url.regionMatches(0, "wss:", 0, 4, ignoreCase = true)) {
                mutableUrl = "https:" + url.substring(4)
            }

            return url(HttpUrl.get(mutableUrl))
        }

        fun build(): FakeRequest {
            return FakeRequest(this)
        }
    }
}