package com.acorn.testanything.thread

import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo

/**
 * Created by acorn on 2021/5/26.
 */
class ThreadActivity:BaseDemoAdapterActivity() {
    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo("普通Thread+Looper")
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        TODO("Not yet implemented")
    }

    private fun testLooper(){

    }

    class MyThread(private var isRunning:Boolean):Thread(){
        override fun run() {
            super.run()
            while (isRunning){

            }
        }
    }
}