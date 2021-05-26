package com.acorn.testanything.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/5/26.
 */
class ThreadActivity : BaseDemoAdapterActivity() {
    private val myThread: MyThread by lazy { MyThread() }
    private val handlerThread: HandlerThread by lazy {
        //线程优先级,系统默认为5.范围[1-10].数值越大，优先级越大，cpu优先调动概率越大
        HandlerThread("test",5)
    }
    private lateinit var handler: Handler

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo(
                "普通Thread+Looper", subItems = arrayListOf(
                    Demo("启动Thread", id = 1000),
                    Demo("发送消息", id = 1001),
                    Demo("退出Looper", id = 1002)
                )
            ),
            Demo(
                "HandlerThread", subItems = arrayListOf(
                    Demo("启动Thread", id = 2000),
                    Demo("发送消息", id = 2001),
                    Demo("退出Looper", id = 2002)
                )
            )
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            1000 -> {
                startMyThread()
            }
            1001 -> {
                sendMsgToMyThread()
            }
            1002 -> {
                quitMyThreadLooper()
            }
            2000 -> {
                startHandlerThread()
            }
            2001 -> {
                sendMsgToHandlerThread()
            }
            2002 -> {
                quitHandlerThread()
            }
        }
    }

    private fun startMyThread() {
        myThread.start()
    }

    private fun sendMsgToMyThread() {
        myThread.mHandler.sendEmptyMessage(10011)
    }

    private fun quitMyThreadLooper() {
        myThread.quit()
    }

    private fun startHandlerThread() {
        //必须先开启线程
        handlerThread.start()
        handler = Handler(handlerThread.looper) {
            logI("handlerThread handleMsg:${it.what},thread:${Thread.currentThread()}")
            //@return True if no further handling is desired
            return@Handler true
        }
    }

    private fun sendMsgToHandlerThread() {
        handler.sendEmptyMessage(22222)
    }

    private fun quitHandlerThread() {
        handlerThread.looper.quit()
    }

    class MyThread : Thread() {
        lateinit var mHandler: Handler

        fun quit() {
            mHandler.looper.quit()
        }

        override fun run() {
            super.run()
            logI("启动Looper")
            Looper.prepare()
            mHandler = Handler {
                logI("handle msg:${it.what},thread:${Thread.currentThread()}")
                //@return True if no further handling is desired
                return@Handler true
            }
            Looper.loop()
            logI("结束死循环,退出Looper后才能走到这里")
        }
    }
}