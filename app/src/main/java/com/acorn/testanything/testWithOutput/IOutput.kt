package com.acorn.testanything.testWithOutput

/**
 * Created by acorn on 2020/3/1.
 */
interface IOutput {
    fun output(str: String)

    fun outputByThread(str:String)

    fun log(str: String)

    fun clearLog()
}