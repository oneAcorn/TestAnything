package com.acorn.testanything.memory

import android.os.Handler
import android.os.Message
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.genericity.Dog
import com.acorn.testanything.utils.logI
import java.lang.ref.WeakReference

/**
 * Created by acorn on 2021/5/24.
 */
class TestMemoryLeakActivity2 : BaseDemoAdapterActivity() {
    private val dog = Dog()
    val anyInterface = object : SomeInterface {
        override fun someCallback() {
            logI("StrictMode callback")
        }
    }
    private val handler = Handler()
    private var outterClassHandler: OutterClassHandler? = OutterClassHandler(WeakReference(this))

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo("泄漏方式1", description = "普通thread+interface,不泄露"),
            Demo("泄漏方式2", description = "使用thread+局部变量外部类,泄露"),
            Demo("泄漏方式3", description = "使用thread+Interface+局部变量外部类,泄露"),
            Demo("泄漏方式4", description = "使用内部类,泄露"),
            Demo("泄漏方式5", description = "使用thread+interface局部变量,泄露"),
            Demo("泄漏方式6", description = "使用内部类+handler,泄露"),
            Demo("泄漏方式7", description = "使用handler sendMessage,泄露"),
            Demo("泄漏方式8", description = "使用外部类handler sendMessage,泄露"),
            Demo("泄漏方式9", description = "使用WeakRefence,泄露"),
            Demo("泄漏方式10", description = "使用外部类Thread + WeakRefence,不泄露!"),
            Demo("泄漏方式11", description = "使用外部类Thread + WeakRefence<Callback>,不泄露!")
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            0 -> {
                leak1()
            }
            1 -> {
                leak2()
            }
            2 -> {
                leak3()
            }
            3 -> {
                leak4()
            }
            4 -> {
                leak5()
            }
            5 -> {
                leak6()
            }
            6 -> {
                leak7()
            }
            7 -> {
                leak8()
            }
            8 -> {
                leak9(WeakReference(this))
            }
            9 -> {
                leak10()
            }
            10 -> {
                leak11()
            }
        }
    }

    private fun leak1() {
        logI("leak1")
        Thread {
            object : SomeInterface {
                override fun someCallback() {

                }
            }
            Thread.sleep(100000)
        }.start()
    }

    private fun leak2() {
        Thread {
            dog.name = "abc"
            Thread.sleep(100000)
        }.start()
    }

    private fun leak3() {
        Thread {
            object : SomeInterface {
                override fun someCallback() {
                    dog.name = "abc"
                }
            }
            Thread.sleep(100000)
        }.start()
    }

    private fun leak4() {
        Thread {
            val ic = AnyInnerClass()
            Thread.sleep(100000)
        }.start()
    }

    private fun leak5() {
        Thread {
            anyInterface.someCallback()
            Thread.sleep(100000)
        }.start()
    }

    private fun leak6() {
        Thread {
            handler.post {
                anyInterface.someCallback()
            }
            Thread.sleep(100000)
        }.start()
    }

    private fun leak7() {
        Thread {
            handler.sendEmptyMessage(1001)
            Thread.sleep(100000)
        }.start()
    }

    private fun leak8() {
        Thread {
            outterClassHandler?.sendEmptyMessage(1001)
            Thread.sleep(100000)
        }.start()
    }

    private fun leak9(weakActivity: WeakReference<TestMemoryLeakActivity2>) {
        Thread {
            val activity = weakActivity.get()
            logI("StrictMode:$activity")
            activity?.logSomething()
            Thread.sleep(100000)
        }.start()
    }

    private fun leak10() {
        OutterThread(WeakReference(this)).start()
    }

    private fun leak11() {
        OutterThread() {
            logSomething()
        }.start()
    }

    fun logSomething() {
        logI("StrictMode logSomething")
    }

    override fun onDestroy() {
        super.onDestroy()
        outterClassHandler?.removeCallbacksAndMessages(null)
        outterClassHandler = null
    }

    class OutterClassHandler(private val weakActivity: WeakReference<TestMemoryLeakActivity2>) :
        Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            logI("StrictMode handleMessage")
            when (msg?.what) {
                1001 -> {
                    weakActivity.get()?.anyInterface
                }
            }
        }
    }

    class OutterThread(
        private val weakActivity: WeakReference<TestMemoryLeakActivity2>? = null,
        callback: (() -> Unit)? = null
    ) :
        Thread() {
        //如果直接使用callback就会泄漏
        private var weakCallback: WeakReference<() -> Unit>? = null

        init {
            if (callback != null) {
                weakCallback = WeakReference(callback)
            }
        }

        override fun run() {
            super.run()
            weakActivity?.get()?.logSomething()
//            callback?.invoke()
            weakCallback?.get()?.invoke()
            sleep(100000)
        }
    }

    inner class AnyInnerClass() {
        val aa = "fff"
    }
}