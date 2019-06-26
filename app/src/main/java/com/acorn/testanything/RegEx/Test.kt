package com.acorn.testanything.RegEx

import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by acorn on 2019-06-05.
 */

fun main() {
    val test = Test()
//    println("aaa:${test.myData?.str},bbb:${test.myData?.arr2?.let { it[0] }}")
//    println(test.matchPassword("dfalsjglasj"))
//    println(test.matchPassword("asbfc2312d"))
//    println(test.matchPassword("12!sf_@dsfdfj"))
//    println(test.matchPassword("1251235342"))
//    println(test.matchPassword("!#@$@$!@$!@"))
    println(test.testMatch("regex not"))
    println(test.testMatch("regex"))
}

class Test {
    var myData: MyData? = MyData()

    class MyData {
        var str: String? = null
        var arr = arrayListOf("fasf", "lll")
        var arr2: List<String>? = null
    }

    /**
     * 6-12位数字,字母组合(两者必须都有),可以包含特殊字符
     */
    fun matchPassword(input: String): Boolean {
        return Pattern.compile("""(?=.*[a-zA-Z])(?=.*\d)[^\\]{6,12}""").matcher(input).find()
    }

    fun testMatch(input: String): String {
        val matcher: Matcher = Pattern.compile("regex|regex not").matcher(input)
        if (matcher.find()) {
            return matcher.group()
        }
        return "null"
    }
}