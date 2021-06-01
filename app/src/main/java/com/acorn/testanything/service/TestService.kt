package com.acorn.testanything.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import com.acorn.testanything.R
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/5/21.
 */
class TestService : Service() {
    private lateinit var mReceiver: MyBroadcastReceiver

    companion object {
        const val BROAD_ISALIVE = "com.acorn.testanything.MyBroadcastReceiver.isAlive"
    }

    override fun onCreate() {
        super.onCreate()
        logI("onCreate")

        mReceiver = MyBroadcastReceiver()
        val filter = IntentFilter()
        filter.addAction(BROAD_ISALIVE)
        registerReceiver(mReceiver, filter)
        //service启动的线程并不会受service的生命周期影响
        Thread {
            while (true) {
                Thread.sleep(3000)
                logI("ThreadInService:I'm alive!")
            }
        }.start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        logI("onBind $intent")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logI("onStartCommand intent:$intent,Thread:${Thread.currentThread()}")
        val isCreateNotification = intent?.getBooleanExtra("isCreateNotification", false)
        if (isCreateNotification == true && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1047, createNotification())
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        unregisterReceiver(mReceiver)
        logI("onDestroy")
    }

    private fun createNotification(): Notification? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "TestService"
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                channelID,
                "testtest",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true) //设置提示灯
            channel.lightColor = Color.GREEN //提示灯颜色
            channel.setShowBadge(true) //显示logo
            channel.description = "服务正在运行" //设置描述
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC //设置锁屏可见
            manager.createNotificationChannel(channel)
            val builder =
                Notification.Builder(applicationContext)
            builder
                .setChannelId(channelID)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        R.mipmap.ic_launcher
                    )
                ) //设置下拉菜单中的图标
                .setContentTitle("哈哈哈哈") //设置下拉菜单的标题
                .setSmallIcon(R.mipmap.ic_launcher) //设置状态栏的小图标
                .setContentText("要显示的内容") //设置上下文内容
                .setWhen(System.currentTimeMillis()) //设置通知发生时间
            builder.build()
        } else null
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                BROAD_ISALIVE -> {
                    Toast.makeText(context, "I'm alive!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}