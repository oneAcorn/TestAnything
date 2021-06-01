package com.acorn.testanything

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.acorn.testanything.broadcast.MyService
import com.tencent.mmkv.MMKV

/**
 * Created by acorn on 2020/4/29.
 */
class MyApplication : MultiDexApplication() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MMKV.initialize(this)
//        val serviceIntent = Intent(applicationContext, MyService::class.java)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(serviceIntent)
//        } else {
//            startService(serviceIntent)
//        }
        setupStrictMode()
    }

    /**
     * 检查各种问题，包括内存泄漏，以log形式提示
     */
    private fun setupStrictMode() {
        val builder = StrictMode.VmPolicy.Builder()
        builder
            .detectActivityLeaks()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectLeakedSqlLiteObjects()
        builder.penaltyLog()
        StrictMode.setVmPolicy(builder.build())
    }
}