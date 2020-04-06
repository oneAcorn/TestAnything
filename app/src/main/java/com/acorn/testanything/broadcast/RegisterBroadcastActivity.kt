package com.acorn.testanything.broadcast

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_register_broadcast.*


/**
 * Created by acorn on 2020/4/6.
 */
class RegisterBroadcastActivity : AppCompatActivity() {
    private var receiver: MyBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_broadcast)
        //动态注册广播
        registerBtn.setOnClickListener {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_POWER_CONNECTED) //usb连接
            filter.addAction(Intent.ACTION_POWER_DISCONNECTED) //usb断开
            receiver = MyBroadcastReceiver()
            registerReceiver(receiver, filter) //动态注册广播
        }

        //发送自定义广播
        sendCustomBroadcastBtn.setOnClickListener {
            with(Intent()) {
                action = "com.acorn.testanything.MY_BROADCAST"
                putExtra("content", "我是广播内容")

                //android8.0以上需要使用显式意图
                component = ComponentName(this@RegisterBroadcastActivity, MyBroadcastReceiver::class.java)
                //如果是给另一个应用发广播
                //component = ComponentName("另一个应用接收者包名", "另一个应用接收者类名")

                sendBroadcast(this)
            }
        }

        val action = "com.acorn.testanything.MY_DYNAMIC_BROADCAST"
        //动态注册自定义广播
        registerCustomBtn.setOnClickListener {
            val filter = IntentFilter()
            filter.addAction(action)
            receiver = MyBroadcastReceiver()
            registerReceiver(receiver, filter)
        }

        sendCustomDynamicBroadcastBtn2.setOnClickListener {
            with(Intent()) {
                this.action = action
                putExtra("content", "动态注册")
                //动态注册的广播不需要使用显式意图(setComponent)
                sendBroadcast(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != receiver)
            unregisterReceiver(receiver)
    }
}