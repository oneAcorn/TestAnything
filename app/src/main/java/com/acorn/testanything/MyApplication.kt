package com.acorn.testanything

import android.content.Intent
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.acorn.testanything.broadcast.MyService

/**
 * Created by acorn on 2020/4/29.
 */
class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        val serviceIntent = Intent(applicationContext, MyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
}