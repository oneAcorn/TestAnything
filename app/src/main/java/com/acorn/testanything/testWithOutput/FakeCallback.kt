package com.acorn.testanything.testWithOutput

import java.io.IOException

/**
 * Created by acorn on 2020/3/1.
 */
interface FakeCallback {
    fun onFailure(call: FakeCall, e: IOException)

    fun onResponse(call: FakeCall, response: FakeResponse)
}