package com.acorn.testanything.kotlin

import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by acorn on 2020/4/28.
 */

fun main() {
//    groupBy()
//    distinctBy()
    mapTest()
//    test()
}

fun test() {
//    println("abcdefz".toFixedByteArray(10))
    val myArr = "12345".toByteArray()
    println("$myArr")
    val bf = ByteBuffer.wrap(myArr)
    println("myArr:$myArr,bf:$bf")
    println("126:${126.toByte()},128:${128.toByte()}")

    val byteBuffer = ByteBuffer.allocate(9)
        .apply {
            order(ByteOrder.BIG_ENDIAN)
            putChar(0x53.toChar())
            putShort(0x00)
            putInt(12947127)
            put(0x0d)
        }
    println("byteBuffer:$byteBuffer")

    //wrap
    val bb = ByteBuffer.wrap(byteBuffer.array(), 4, 4)
    println("长度字段:${bb.int}")
}

var what: Int? = 0
    get() {
        return if (field == 0)
            3
        else
            10
    }
    private set
var setField = 0
    set(value) {
        field = if (value > 0) 3 else value
    }

inline fun inlineFun(getString: () -> String?) {
    //err!!,inline函数的参数不能传给其他非inline方法
//        otherFunWantFun(getString)
    println(getString())
}

fun otherFunWantFun(getStr: () -> String?) {
    println("?${getStr()}")
}

fun groupBy() {
    val list = mutableListOf<Data>()
    list.add(Data("张三", 12))
    list.add(Data("张三", 17))
    list.add(Data("李四", 23))
    list.add(Data("张三", 11))
    list.add(Data("王五", 32))
    list.add(Data("李四", 22))
    list.add(Data("张三", 12))

    val newList = mutableListOf<Data>()
    list.groupBy {
        it.name
    }.forEach {
        it.value.forEach { data ->
            newList.add(data)
        }
    }
    println("\n\n final result:${newList}")
}

fun distinctBy() {
    val list = mutableListOf<Data>()
    list.add(Data("张三", 12))
    list.add(Data("张三", 17))
    list.add(Data("李四", 23))
    list.add(Data("张三", 11))
    list.add(Data("王五", 32))
    list.add(Data("李四", 22))
    list.add(Data("张三", 12))
    val newList = list.distinctBy { it.name }
    println("$newList")
}

fun mapTest() {
    val list = mutableListOf<Data>()
    list.add(Data("张三", 12))
    list.add(Data("张三", 17))
    list.add(Data("李四", 23))
    list.add(Data("张三", 11))
    list.add(Data("王五", 32))
    list.add(Data("李四", 22))
    list.add(Data("张三", 12))
    val newList = list.map { it.name }
    println("$newList")
    val newList2 = list.map {
        it.name
    }.filter {
        it != "张三"
    }.map {
        it + ".."
    }
    println("$newList2")
}

data class Data(var name: String, var age: Int)