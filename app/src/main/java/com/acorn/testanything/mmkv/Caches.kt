package com.acorn.testanything.mmkv

import com.tencent.mmkv.MMKV

/**
 * Created by acorn on 2020/10/31.
 */
object Caches {
    private val mmkv = MMKV.mmkvWithID("test", MMKV.SINGLE_PROCESS_MODE)

    var str1: String? by MMKVDelegate(mmkv, String::class.java)
    var testBean1: TestBean? by MMKVDelegate(mmkv, TestBean::class.java)

    var str2: String by mmkv.string()
    var testBean2: TestBean by mmkv.parcelable()
}