package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/4/28.
 */

fun main() {
    var test = KotlinBasic()
    test.inlineFun { "haha" }
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
}