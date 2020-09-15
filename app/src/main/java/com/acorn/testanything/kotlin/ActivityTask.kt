package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/9/11.
 */

fun main() {
    val task = ActivityTask()
    task.addActivity("a")
    task.addActivity("a")
    task.addActivity("b")
    task.addActivity("c")
    task.addActivity("d")
    task.addActivity("e")
    task.addActivity("a")
    task.addActivity("f")
    task.addActivity("g")
    task.removeActivity("adfs")
    println(task.activityNameList.toString())
}

class ActivityTask {
    val activityNameList = mutableListOf<String>()

    fun addActivity(activityName: String) {
        activityNameList.add(activityName)
    }

    fun removeActivity(activityName: String) {
        activityNameList
                .lastIndexOf(activityName)
                .takeIf {
                    it >= 0
                }?.let {
                    activityNameList.removeAt(it)
                }
    }
}