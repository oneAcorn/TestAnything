package com.acorn.testanything.LinkedList

import java.util.*

/**
 * Created by acorn on 2020/4/7.
 */
fun main() {
    val first = Node(0)
    val n1 = Node(1)
    val n2 = Node(2)
    val n3 = Node(3)
    val n4 = Node(4)
    val n5 = Node(5)
    val n6 = Node(6)
    val n7 = Node(7)

    val second = Node(10)
    val s1 = Node(9)

    first.next = n1
    n1.next = n2
    n2.next = n3
    n3.next = n4
    n4.next = n5
    n5.next = n6
    n6.next = n7
    second.next = s1
    s1.next = n4

    printRes(f2(first, second))
}


private fun simpleOne(f: Node, s: Node): Int {
    var crossValue: Int? = null
    var tmp: Node? = f
    while (tmp != null && crossValue == null) {
        var tmp2: Node? = s
        while (tmp2 != null && crossValue == null) {
            if (tmp == tmp2) {
                crossValue = tmp.data
            }
            tmp2 = tmp2.next
        }
        tmp = tmp.next
    }
    return crossValue ?: -1
}

private fun f2(f: Node, s: Node): Int {
    val stack1 = Stack<Node>()
    val stack2 = Stack<Node>()
    var tmp1: Node? = f
    while (tmp1 != null) {
        stack1.push(tmp1)
        tmp1 = tmp1.next
    }
    var tmp2: Node? = s
    while (tmp2 != null) {
        stack2.push(tmp2)
        tmp2 = tmp2.next
    }
    var crossValue = -1
    if (stack2.pop() != stack1.pop())
        return crossValue
    while (stack1.peek() == stack2.peek()) {
        crossValue = stack1.peek().data
        stack1.pop()
        stack2.pop()
    }
    return crossValue
}

private fun printRes(f: Int) {
    println("result:${f}")
}