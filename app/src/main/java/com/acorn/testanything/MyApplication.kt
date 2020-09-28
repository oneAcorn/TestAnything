package com.acorn.testanything

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.acorn.testanything.broadcast.MyService

/**
 * Created by acorn on 2020/4/29.
 */
class MyApplication : MultiDexApplication() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val serviceIntent = Intent(applicationContext, MyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
}