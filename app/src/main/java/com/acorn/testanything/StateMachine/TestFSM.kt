package com.acorn.testanything.StateMachine

import java.lang.StringBuilder

/**
 * 有限状态机简写为FSM（Finite State Machine）
 * Created by acorn on 2022/2/14.
 */
fun main() {
    println(testFSM1("H__e_l____l_o"))
    println(testFSM2("""H__e_l__"wo_r＿＿＿ld"____lo"""))
}

/**
 * 去除一个字符串中连续的下划线，即 Ｈ＿＿ｅｌ＿＿＿ｌｏ　变成　Ｈ＿ｅｌ＿ｌｏ；
 */
private fun testFSM1(str: String): String {
    val charArr = str.toCharArray().iterator()
    var flag = 0
    val sb = StringBuilder()
    loop@ while (charArr.hasNext()) {
        val c = charArr.next()
        when (flag) {
            0 -> {
                if (c == '_') {
                    flag = 1
                }
                sb.append(c)
            }
            1 -> {
                if (c == '_') {
                    continue@loop
                }
                flag = 0
                sb.append(c)
            }
        }
    }
    return sb.toString();
}

/**
 * 去除连续的下划线但字符串中的连续下划线不变，比如H__e_l__"wo_r＿＿＿ld"____lo；
 */
private fun testFSM2(str: String): String {
    val charArr = str.toCharArray().iterator()
    var flag = 0
    val sb = StringBuilder()
    loop@ while (charArr.hasNext()) {
        val c = charArr.next()
        when (flag) {
            0 -> {
                if (c == '_') {
                    flag = 1
                }
                if (c == '"') {
                    flag = 2
                }
                sb.append(c)
            }
            1 -> {
                if (c == '_') {
                    continue@loop
                }
                if (c != '"') {
                    flag = 0
                }
                if (c == '"') {
                    flag = 2
                }
                sb.append(c)
            }
            2 -> {
                if (c == '"') {
                    flag = 0
                }
                sb.append(c)
            }
        }
    }
    return sb.toString();
}