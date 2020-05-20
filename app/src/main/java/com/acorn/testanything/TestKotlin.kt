package com.acorn.testanything

/**
 * Created by acorn on 2020/5/18.
 */
fun main() {
    val sourceList = mutableListOf(1, 2, 3)
    val copySet = sourceList.toMutableSet()
    //声明为非Mutable的只读list
    val copyReadList: List<Int> = sourceList
    copySet.add(3)
    copySet.add(4)
//    copyReadList.add(5) //非Mutable，不可修改
    println(copySet)
    println(sourceList)

    val numbers = listOf("one", "two", "three", "four")
    println(numbers.associateWith { it.length })

    //十进制转二进制
    println("15=${15.decimal2Binary()},16=${16.decimal2Binary()},12=${12.decimal2Binary()}")
    //位运算
    println("15&16=${15 and 16} 15|16=${15 or 16} 12&15=${12 and 15} 12|15=${12 or 15}")
}

/**
 * 十进制转二进制
 */
fun Int.decimal2Binary(): String {
    return Integer.toBinaryString(this)
}

fun String.binary2Decimal(): String {
    return Integer.valueOf(this, 2).toString()
}