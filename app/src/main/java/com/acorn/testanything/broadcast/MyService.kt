package com.acorn.testanything.broadcast

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
 * Created by acorn on 2020/8/10.
 */
class MyService : Service() {
    private lateinit var receiver: MyBroadcastReceiver
    private lateinit var receiverInner: BroadcastReceiverInService

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread() {
            while (true) {
                logI("I am alive")
                Thread.sleep(1000 * 5)
            }
        }
            .start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        logI("onCreate")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1047, createNotification())
        }
        val action = "com.acorn.testanything.MY_DYNAMIC_BROADCAST"
        val filter = IntentFilter()
        filter.addAction(action)
        receiver = MyBroadcastReceiver()
        registerReceiver(receiver, filter)

        receiverInner=BroadcastReceiverInService()
        registerReceiver(receiverInner,filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        unregisterReceiver(receiverInner)
    }

    private fun createNotification(): Notification? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "MyService"
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                channelID,
                "下载漫画服务",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true) //设置提示灯
            channel.lightColor = Color.GREEN //提示灯颜色
            channel.setShowBadge(true) //显示logo
            channel.description = "下载漫画呢" //设置描述
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
                .setContentTitle("下载图片服务启动中") //设置下拉菜单的标题
                .setSmallIcon(R.mipmap.ic_launcher) //设置状态栏的小图标
                .setContentText("要显示的内容") //设置上下文内容
                .setWhen(System.currentTimeMillis()) //设置通知发生时间
            builder.build()
        } else null
    }

    inner class BroadcastReceiverInService : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(
                context,
                "收到消息2${intent?.action},${intent?.getStringExtra("content")}", Toast.LENGTH_LONG
            ).show()
        }

    }
}