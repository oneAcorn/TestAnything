package com.acorn.testanything.algorithm

import kotlin.math.pow

/**
 * Created by acorn on 2019/10/9.
 */

fun main() {
//    println("结果${findStep(6)}")
    question9()
}

/**
 * 打印出所有的"水仙花数"，所谓"水仙花数"是指一个三位数，其各位数字立方和等于该数本身。
 * 例如： 153是一个"水仙花数"，因为153=1的三次方＋5的三次方＋3的三次方。
 */
private fun question1() {
    for (i in 100..999) {
        var one: Int = i / 100
        var two: Int = (i - one * 100) / 10
//        方法二 var two: Int = i%100/10
        var three: Int = i - one * 100 - two * 10
//        方法二 var three: Int = i%10
        if (Math.pow(one.toDouble(), 3.0) + Math.pow(two.toDouble(), 3.0) + Math.pow(
                three.toDouble(),
                3.0
            ) == i.toDouble()
        ) {
            println("res:$i,$one$two$three")
        }
    }
}

/**
 * 有1、2、3、4个数字，能组成多少个互不相同且无重复数字的三位数？都是多少？
 */
private fun question2() {
    val arr = listOf<Int>(1, 2, 3, 4)
    var times = 0
    var totalHits = 0
    for (i in arr) {
        for (j in arr) {
            for (k in arr) {
                if (i != j && j != k && i != k) {
                    println("$i$j$k")
                    totalHits++
                }
                times++
            }
        }
    }
    println("循环次数:$times,找出${totalHits}个")
}

/**
 * 输出9*9口诀。
 */
private fun question3() {
    for (i in 1..9) {
        for (j in i..9) {
            println("$i*$j=${i * j}")
        }
    }
}

/**
 * 猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不瘾，
　　  　又多吃了一个 第二天早上又将剩下的桃子吃掉一半，又多吃了一个。
　　 以后每天早上都吃了前一天 剩下的一半零一个。
　　 到第10天早上想再吃时，见只剩下一个桃子了。求第一天共摘了多少。
 */
private fun question4() {
    var res = 1
    for (i in 0..9) {
        res = (res + 1) * 2
    }
    println("$res")
}

/**
 * 题目：判断101-200之间有多少个素数，并输出所有素数。
 * 素数/质数:一个大于1的自然数，除了1和它自身外，不能被其他自然数整除的数叫做质数
 */
private fun question5() {
    for (i in 101..200) {
        var isMyNumber = true
        //优化方式 for(j in 2..Math.sqrt(i))
        for (j in 2 until i) {
            if (i % j == 0) {
                isMyNumber = false
                break
            }
        }
        if (isMyNumber) {
            println("素数:$i")
        }
    }
}

/**
 * 题目：求s=a+aa+aaa+aaaa+aa…a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)
 */
private fun question6(num: Int, times: Int) {
    var tmp = 0
    var amount = 0
    for (i in 0 until times) {
        tmp += num * 10.0.pow(i.toDouble()).toInt()
        amount += tmp
        println("res:$tmp,amount:$amount")
    }
}

/**
 * 题目：一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在 第10次落地时，共经过多少米？第10次反弹多高？
 */
private fun question7() {
    var height = 100.0
    var totalDistance = height //总经过距离
    for (i in 0..9) {
        height /= 2
        totalDistance += height
    }
    println("height:$height,totalDistance:$totalDistance")
}

/**
 * 50个人围成一圈数到三和三的倍数时出圈，问剩下的人是谁？在原来的位置是多少？
 */
private fun question8() {
    val total = 50
    val step = 3
    var curNum = 1
    val list = mutableListOf<Int>()
    for (i in 1..total) {
        list.add(i)
    }
    var iterator = list.iterator()
    while (list.size > 1) {
        if (!iterator.hasNext()) {
            iterator = list.iterator()
            println("list:$list")
        }
        val num = iterator.next()
        if (curNum % step == 0) {
            iterator.remove()
        }
        curNum++
    }
    println("res:${list[0]}")
}

/**
 * n阶台阶,有2种走法,一种是1步1个台阶,一种是1步2个台阶,那么到顶端共有多少种走法
 */
private fun findStep(n: Int): Int {
    if (n == 0 || n == 1 || n == 2)
        return n
    return findStep(n - 1) + findStep(n - 2)
}

/**
 * 输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整数组成一个子数组。求所有子数组的和的最大值。
 * 例如：输入的数组为{1，-2， 3， 10， -4， 7， 2， -5}，和最大的子数组为{3， 10， -4， 7， 2}，因此输出为该子数组的和18。
 */
private fun question9() {
    val arr = arrayOf(1, -2, 3, 10, -4, 7, 2, -5)
    var maxSum = 0
    var start = 0
    var end = 0
    for (i in arr.indices) {
        var sum = 0
        for (j in i until arr.size) {
            sum += arr[j]
            if (sum > maxSum) {
                maxSum = sum
                start = i
                end = j
            }
        }
    }
    println("max:$maxSum,start:$start,end:$end")
}