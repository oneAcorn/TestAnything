package com.acorn.jnidemo

/**
 * Created by acorn on 2019/9/10.
 */
class NativeHelper {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun testNdk():String
}