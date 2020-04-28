package com.acorn.testanything.retrofit


import java.util.logging.Level

import okhttp3.internal.platform.Platform.INFO


/**
 * @author ihsan on 10/02/2017.
 */
internal class I private constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        fun log(type: Int, tag: String, msg: String) {
            val logger = java.util.logging.Logger.getLogger(tag)
            when (type) {
                INFO -> logger.log(Level.INFO, msg)
                else -> logger.log(Level.WARNING, msg)
            }
        }
    }
}
