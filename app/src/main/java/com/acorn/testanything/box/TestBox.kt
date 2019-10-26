package com.acorn.testanything.box

import com.acorn.testanything.utils.log

/**
 * 装箱与拆箱
 */

fun main() {
//    在Java中，通过装箱和拆箱在基本数据类型和包装类型之间相互转换。而Kotlin中，所有变量的成员方法和属性都是对象，当需要一个
//    可为 null 的引用时, 就会触发装箱操作， 装箱操作不保持对象的同一性。
    val a: Int? = 1000 //装箱操作
    val b: Int = 1000
    val c: Int = 1000
    print("a==b:${a == b}\nb==c:${b == c}\na===b:${a === b}\nb===c:${b === c}")
}