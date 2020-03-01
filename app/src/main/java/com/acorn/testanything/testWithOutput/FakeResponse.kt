package com.acorn.testanything.testWithOutput

import java.io.Closeable

/**
 * Created by acorn on 2020/3/1.
 */
class FakeResponse : Closeable {
    override fun close() {
    }
}