package com.acorn.testanything.utils

import android.util.TypedValue
import com.acorn.testanything.MyApplication

/**
 * Created by acorn on 2020/9/11.
 */

var Int.dp: Int
    get() = this.dp()
    set(value) {}
var Int.sp: Int
    get() = this.sp()
    set(value) {}
var Float.dp: Float
    get() = this.dp()
    set(value) {}
var Float.sp: Float
    get() = this.dp()
    set(value) {}

private fun Int.dp(): Int {
    return this.toFloat().dp().toInt()
}

private fun Int.sp(): Int {
    return this.toFloat().sp().toInt()
}

private fun Float.dp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        MyApplication.context.resources.displayMetrics
    )
}

private fun Float.sp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        MyApplication.context.resources.displayMetrics
    )
}