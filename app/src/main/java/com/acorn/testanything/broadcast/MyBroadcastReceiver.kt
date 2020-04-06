package com.acorn.testanything.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.acorn.testanything.utils.log

/**
 * Created by acorn on 2020/4/6.
 */
class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        log("收到消息${intent?.action}")
        Toast.makeText(context, "收到消息${intent?.action},${intent?.getStringExtra("content")}", Toast.LENGTH_LONG).show()
    }
}