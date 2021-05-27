package com.acorn.testanything.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.genericity.Dog
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/5/26.
 */
class ThreadActivity : BaseDemoAdapterActivity() {
    private val myThread: MyThread by lazy { MyThread() }
    private val handlerThread: HandlerThread by lazy {
        //线程优先级,系统默认为5.范围[1-10].数值越大，优先级越大，cpu优先调动概率越大
        HandlerThread("test", 5)
    }
    private val threadLocalThreadList: MutableList<MyThread> by lazy { mutableListOf<MyThread>() }

    private lateinit var handler: Handler

    companion object {
        val mainThreadThreadLocal = ThreadLocal<String?>()
        val mainThreadInheritableThreadLocal = InheritableThreadLocal<String?>()
    }

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo(
                "普通Thread+Looper", subItems = arrayListOf(
                    Demo("启动Thread", id = 1000),
                    Demo("发送消息", id = 1001),
                    Demo("退出Looper", id = 1002, description = "退出后无法收到新消息")
                )
            ),
            Demo(
                "HandlerThread",
                description = "普通Thread+Looper或HandlerThread,两种写法没有本质不同,只是HandlerThread简化了调用而已.",
                subItems = arrayListOf(
                    Demo("启动Thread", id = 2000),
                    Demo("发送消息", id = 2001),
                    Demo("退出Looper", id = 2002, description = "退出后无法收到新消息")
                )
            ),
            Demo(
                "ThreadLocal",
                subItems = arrayListOf(
                    Demo("启动多个线程", id = 3000),
                    Demo(
                        "在主线程修改一些ThreadLocal", id = 3001,
                        description = "在主线程直接修改ThreadLocal后,是不能在线程中获取到对应的值的.(ps:没搞清楚InheritableThreadLocal怎么在线程见共享)"
                    ),
                    Demo(
                        "通过Handler在线程中修改一些ThreadLocal", id = 3004,
                        description = "这种方式可以正常获取对应的值,而且static的值也是线程独立的"
                    ),
                    Demo("打印ThreadLocal值", id = 3002),
                    Demo(
                        "清理ThreadLocal", id = 3003,
                        description = "如果不及时清理,会有内存泄漏风险"
                    )
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
            3000 -> {
                threadLoaclStartSomeThread()
            }
            3001 -> {
                threadLocalChangeVar()
            }
            3002 -> {
                threadLocalLog()
            }
            3003 -> {
                threadLocalClear()
            }
            3004 -> {
                threadLocalChangeVarByThreadItself()
            }
        }
    }

    private fun startMyThread() {
        myThread.start()
    }

    private fun sendMsgToMyThread() {
        myThread.mHandler.sendEmptyMessage(0)
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

    private fun threadLoaclStartSomeThread() {
        for (i in 0..3) {
            val myThread = MyThread()
            myThread.name = "Thread$i"
            threadLocalThreadList.add(myThread)
            myThread.start()
        }
    }

    private fun threadLocalChangeVar() {
        threadLocalThreadList[1].mThreadLocal.set("1111111111111")
        threadLocalThreadList[2].setThreadLocalValue("2222222222222222")
        threadLocalThreadList[3].mInheritableThreadLocal.set("主线程设置InheritableThreadLocal 33333")
        MyThread.staticThreadLocal.set("static fasdf")

        mainThreadThreadLocal.set("主线程ThreadThreadLocal")
        mainThreadInheritableThreadLocal.set("主线程InheritableThreadLocal")
    }

    private fun threadLocalChangeVarByThreadItself() {
        threadLocalThreadList[0].mHandler.sendMessage(Message.obtain().apply {
            what = 3001
            obj = "I am static 00000000000"
        })
        threadLocalThreadList[1].mHandler.sendMessage(Message.obtain().apply {
            what = 3000
            obj = "I am thread 111111111111111111"
        })
        threadLocalThreadList[2].mHandler.sendMessage(Message.obtain().apply {
            what = 3000
            obj = "I am thread 222222222222222"
        })
        threadLocalThreadList[3].mHandler.sendMessage(Message.obtain().apply {
            what = 3002
            obj = "I am InheritableThread 333333333"
        })

        threadLocalThreadList[2].mHandler.sendMessage(Message.obtain().apply {
            what = 3003
            obj = "I am Static InheritableThread 222222222222222"
        })
    }

    private fun threadLocalLog() {
        threadLocalThreadList.forEach {
            it.mHandler.sendEmptyMessage(1)
        }
    }

    private fun threadLocalClear() {
        threadLocalThreadList.forEach {
            it.mThreadLocal.remove()
        }
        MyThread.staticThreadLocal.remove()
    }

    class MyThread : Thread() {
        lateinit var mHandler: Handler
        val mThreadLocal = ThreadLocal<String?>()
        val mInheritableThreadLocal = InheritableThreadLocal<String?>()

        companion object {
            val staticThreadLocal = ThreadLocal<String?>()
            val staticInheritableThreadLocal = InheritableThreadLocal<String?>()
        }

        fun setThreadLocalValue(str: String?) {
            mThreadLocal.set(str)
        }

        fun quit() {
            mHandler.looper.quit()
        }

        override fun run() {
            super.run()
            logI("启动Looper")
            Looper.prepare()
            mHandler = Handler {
                when (it.what) {
                    0 -> { //log
                        logI("handle thread:${Thread.currentThread()}")
                    }
                    1 -> { //log ThreadLocal Info
                        logI(
                            "Thread($name):\nthreadLocal:${mThreadLocal.get()}," +
                                    "staticThreadLocal:${staticThreadLocal.get()} \n" +
                                    "InheritableThreadLocal:${mInheritableThreadLocal.get()}," +
                                    "Static InheritableThreadLocal:${staticInheritableThreadLocal.get()}\n" +
                                    "Main Thread ThreadLocal:${mainThreadThreadLocal.get()}," +
                                    "Main Thread InheritableThreadLocal:${mainThreadInheritableThreadLocal.get()}"
                        )
                    }
                    3000 -> { //测试threadLocal
                        mThreadLocal.set(it.obj as String?)
                    }
                    3001 -> { //测试threadLocal
                        staticThreadLocal.set(it.obj as String?)
                    }
                    3002 -> {
                        mInheritableThreadLocal.set(it.obj as String?)
                    }
                    3003 -> {
                        staticInheritableThreadLocal.set(it.obj as String?)
                    }
                }

                //@return True if no further handling is desired
                return@Handler true
            }
            Looper.loop()
            logI("结束死循环,退出Looper后才能走到这里")
        }
    }
}