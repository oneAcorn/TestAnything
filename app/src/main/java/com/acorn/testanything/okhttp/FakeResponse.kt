package com.acorn.testanything.okhttp

import java.io.Closeable

/**
 * Created by acorn on 2020/3/1.
 */
class FakeResponse : Closeable {
    var message: String? = null

    override fun close() {
    }
}