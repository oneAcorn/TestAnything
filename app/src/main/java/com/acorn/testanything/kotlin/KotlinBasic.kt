package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/4/28.
 */

fun main() {
    var test = KotlinBasic()
//    test.inlineFun { "haha" }
    test.groupBy()
}

class KotlinBasic {
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
            println("结果:${it.key}")
            it.value.forEach { data ->
                println("结果2:${data.name},${data.age}")
                newList.add(data)
            }
        }
        println("\n\n final result:${newList}")
    }

    data class Data(var name: String, var age: Int)
}