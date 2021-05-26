package com.acorn.testanything.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/5/26.
 */
class MyIntentService : IntentService("testService") {
    override fun onHandleIntent(intent: Intent?) {
        logI("onHandleIntent msg:${intent?.getStringExtra("msg")},thread:${Thread.currentThread()}")
        Thread.sleep(10000)
        logI("onHandleIntent msg(${intent?.getStringExtra("msg")}) finish")
    }

    override fun onCreate() {
        super.onCreate()
        logI("onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        logI("onBind")
        return super.onBind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logI("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        logI("onDestroy")
    }
}