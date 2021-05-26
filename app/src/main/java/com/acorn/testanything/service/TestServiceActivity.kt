package com.acorn.testanything.service

import android.content.Intent
import android.os.Build
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import kotlinx.android.synthetic.main.activity_rv_demo.*

/**
 * Created by acorn on 2021/5/21.
 */
class TestServiceActivity : BaseDemoAdapterActivity() {
    private var count = 0

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo(
                "startService",
                description = "android8.0以上,app在后台时(前台时可以直接用startService())必须使用startForegroundService()启动service," +
                        "并且service得及时(5秒内)调用startForeground()方法",
                id = 1000
            ),
            Demo(
                "IntentService",
                description = "IntentService onHandleIntent()回调在线程中运行.运行完毕onHandleIntent()后,自动onDestroy().",
                subItems = arrayListOf(
                    Demo("启动service", id = 2000),
                    Demo(
                        "发送消息",
                        description = "如果在上一个消息的onHandleIntent()回调还没处理完,那么会等到其结束后," +
                                "再收到新消息的onHandleIntent()回调.全部执行完成后destroy",
                        id = 2001
                    )
                )
            )
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            1000 -> {
                val intent = Intent(this, TestService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //android8.0以上,app在后台时必须使用此方法启动service,并且service得及时(5秒内)调用startForeground()方法
                    startForegroundService(intent)
                } else {
                    //即使在android8.0以上,只要app是在前台,调用此方法也没事.
                    startService(intent)
                }
            }
            2000 -> {
                startIntentService()
            }
            2001 -> {
                sendMsgToIntentService()
            }
        }
    }

    private fun startIntentService() {
        val intent = Intent(this, MyIntentService::class.java)
        startService(intent)
    }

    private fun sendMsgToIntentService() {
        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("msg", "msg$count")
        startService(intent)
        count++
    }
}