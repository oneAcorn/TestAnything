package com.acorn.testanything.dsl

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by acorn on 2020/6/30.
 */

//typealias作用:给已有类型取一个别名，可以像使用原类型一样使用这个 “类型别名” 。
private typealias AfterTextChangedCallback = (s: Editable?) -> Unit
private typealias BeforeTextChangedCallback = (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit
private typealias OnTextChangedCallback = (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit

fun registerTextWatcher(function: TextWatcherBuilder.() -> Unit) {
    TextWatcherBuilder().also(function)
}

class TextWatcherBuilder : TextWatcher {
    private var beforeTextChangedCallback: BeforeTextChangedCallback? = null
    private var afterTextChangedCallback: AfterTextChangedCallback? = null
    private var onTextChangedCallback: OnTextChangedCallback? = null

    override fun afterTextChanged(s: Editable?) {
        afterTextChangedCallback?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChangedCallback?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChangedCallback?.invoke(s, start, before, count)
    }

    fun beforeTextChanged(callback: BeforeTextChangedCallback) {
        beforeTextChangedCallback = callback
    }

    fun afterTextChanged(callback: AfterTextChangedCallback) {
        afterTextChangedCallback = callback
    }

    fun onTextChangedCallback(callback: OnTextChangedCallback) {
        onTextChangedCallback = callback
    }
}