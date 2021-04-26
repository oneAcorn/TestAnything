package com.acorn.testanything.genericity

import android.view.ViewGroup
import android.widget.LinearLayout
import com.acorn.testanything.utils.AnyBean
import com.acorn.testanything.utils.GsonUtils
import com.google.gson.reflect.TypeToken

/**
 * Created by acorn on 2019-06-18.
 */

fun main() {
//    val comparator = Comparator<Number>()
//    comparator.compare(1, 2f)
//    val fatherList = mutableListOf<ViewGroup>()
//    val sonList = mutableListOf<LinearLayout>()
//    Collections.copy(fatherList, sonList)
    //error!
    //Collections.copy(sonList,fatherList)

    val jsonStr = """{code:1,data:{a:"abc"}}"""
    val type = object : TypeToken<ResultObject<AnyBean>>() {}.type
    println("type:$type,class:${type::class.java}")
    val result = GsonUtils.fromJsontoBean<ResultObject<AnyBean>>(jsonStr, type)
    println("fdsf")
    test2<ResultObject<AnyBean>>(jsonStr)
}

inline fun <reified T : ResultObject<*>> test2(jsonStr: String) {
    val type = object : TypeToken<ResultObject<AnyBean>>() {}.type
    val result = GsonUtils.fromJsontoBean<T>(jsonStr, type)
    println("ffff")
}

class TestGenericity {
    fun test1(g: Genericity1<LinearLayout>) {
        val g2: Genericity1<ViewGroup> = g.next()
    }
}