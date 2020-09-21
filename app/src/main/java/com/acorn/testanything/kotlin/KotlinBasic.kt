package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/4/28.
 */

fun main() {
//    groupBy()
//    distinctBy()
    mapTest()
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
}

data class Data(var name: String, var age: Int)