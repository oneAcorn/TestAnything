package com.acorn.testanything.countdown

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2020/7/17.
 */

/**
 * 倒计时
 * @param sec 总倒计时(秒)
 * @param secStep 计时描述间隔(秒)
 */
fun AppCompatActivity.countdown(sec: Int, secStep: Int, callback: (sec: Int) -> Unit) {
    bindCountService(this) {
        it.startCountdown(sec * 1000L, secStep * 1000L, object : CountService.OnCountListener {
            override fun onTick(millSec: Long, sec: Int) {
                callback(sec)
            }
        })
    }
}

/**
 * 倒计时
 * @param millSec 总倒计时(毫秒)
 * @param millSecStep 计时描述间隔(毫秒)
 */
fun AppCompatActivity.countdownMill(
    millSec: Long,
    millSecStep: Long,
    callback: (millSec: Long) -> Unit
) {
    bindCountService(this) {
        it.startCountdown(millSec, millSecStep, object : CountService.OnCountListener {
            override fun onTick(millSec: Long, sec: Int) {
                callback(millSec)
            }
        })
    }
}

/**
 * 正计时
 * @param secStep 计时描述间隔(毫秒)
 */
fun AppCompatActivity.count(secStep: Int, callback: (sec: Int) -> Unit) {
    bindCountService(this) {
        it.startCount(secStep * 1000L, object : CountService.OnCountListener {
            override fun onTick(millSec: Long, sec: Int) {
                callback(sec)
            }
        })
    }
}

/**
 * 正计时
 * @param millSecStep 计时描述间隔(毫秒)
 */
fun AppCompatActivity.countMill(millSecStep: Long, callback: (millSec: Long) -> Unit) {
    bindCountService(this) {
        it.startCount(millSecStep, object : CountService.OnCountListener {
            override fun onTick(millSec: Long, sec: Int) {
                callback(millSec)
            }
        })
    }
}

private fun bindCountService(
    activity: AppCompatActivity,
    callback: (CountService.MyBinder) -> Unit
) {
    val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            if (service !is CountService.MyBinder) return
            callback(service)
        }

    }

    activity.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            logI("event:$event")
            if (event == Lifecycle.Event.ON_DESTROY) {
                activity.unbindService(serviceConnection)
            }
        }
    })

    val serviceIntent = Intent(activity, CountService::class.java)
    activity.bindService(serviceIntent, serviceConnection, AppCompatActivity.BIND_AUTO_CREATE)
}