package com.acorn.testanything.utils

import android.util.Log
import com.acorn.testanything.BuildConfig
import java.util.*

/**
 * Created by acorn on 2020/5/3.
 */
fun logI(str: String) {
    if (BuildConfig.DEBUG)
        Log.i("acornTag", str)
}

fun logE(str: String) {
    if (BuildConfig.DEBUG)
        Log.e("acornTag", str)
}

fun logE(e: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e("acornTag", "err", e)
}

private const val formatStr = "%s (line:%d)  %s"

/**
 * 这方法不适用于Kotlin的扩展方法
 */
private fun getFormatStr(log: String): String {
    val traceElement = Thread.currentThread().stackTrace[3]
    return String.format(
        Locale.getDefault(),
        formatStr,
        traceElement.fileName,
        traceElement.lineNumber,
        log
    )
}