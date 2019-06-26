package com.acorn.testanything.RegEx

import java.util.regex.Pattern

/**
 * Created by acorn on 2019-06-05.
 */

fun main() {
    val test = Test()
    println("aaa:${test.myData?.str},bbb:${test.myData?.arr2?.let { it[0] }}")
    println(test.testMatcher("dfalsjglasj"))
    println(test.testMatcher("asbfc2312d"))
    println(test.testMatcher("12!sf_@dsfdfj"))
    println(test.testMatcher("1251235342"))
    println(test.testMatcher("!#@$@$!@$!@"))
}

class Test {
    var myData: MyData? = MyData()

    class MyData {
        var str: String? = null
        var arr = arrayListOf("fasf", "lll")
        var arr2: List<String>? = null
    }

    fun testMatcher(input: String): Boolean {
        return Pattern.compile("""(?=.*[a-zA-Z])(?=.*\d)[^\\]{6,12}""").matcher(input).find()
    }
}