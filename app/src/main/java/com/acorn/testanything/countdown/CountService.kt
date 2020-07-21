package com.acorn.testanything.countdown

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import kotlin.math.roundToInt

/**
 * Created by acorn on 2020/7/20.
 */
class CountService : Service() {
    private var onCountListener: OnCountListener? = null
    private var countdownTimer: CountDownTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return MyBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        countdownTimer?.cancel()
        return super.onUnbind(intent)
    }

    /**
     * 开始倒计时
     * @param totalMillSec 总倒计时毫秒数
     * @param millSecStep 计时间隔(毫秒)
     */
    private fun startCountdown(totalMillSec: Long, millSecStep: Long) {
        countdownTimer?.cancel()
        countdownTimer = object : CountDownTimer(totalMillSec, millSecStep) {
            override fun onTick(millisUntilFinished: Long) {
                onCountListener?.run {
                    val sec = (millisUntilFinished.toDouble() / 1000).roundToInt()
                    onTick(millisUntilFinished, sec)
                }
            }

            override fun onFinish() {
                onCountListener?.onTick(0L, 0)
            }
        }
        countdownTimer?.start()
    }


    /**
     * 开始计时
     * @param millSecStep 计时间隔(毫秒)
     */
    private fun startCount(millSecStep: Long) {
        countdownTimer?.cancel()
        val maxValue = Long.MAX_VALUE
        countdownTimer = object : CountDownTimer(maxValue, millSecStep) {
            override fun onTick(millisUntilFinished: Long) {
                onCountListener?.run {
                    val millSec = maxValue - millisUntilFinished
                    val sec = (millSec.toDouble() / 1000).roundToInt()
                    onTick(millSec, sec)
                }
            }

            override fun onFinish() {}
        }
        countdownTimer?.start()
    }

    internal inner class MyBinder : Binder() {
        fun startCount(millSecStep: Long, listener: OnCountListener) {
            if (millSecStep <= 0)
                throw IllegalArgumentException("millSec and millSecStep must greater than zero")
            this@CountService.onCountListener = listener
            startCount(millSecStep)
        }

        fun startCountdown(millSec: Long, millSecStep: Long, listener: OnCountListener) {
            if (millSec <= 0 || millSecStep <= 0)
                throw IllegalArgumentException("millSec and millSecStep must greater than zero")
            this@CountService.onCountListener = listener
            startCountdown(millSec, millSecStep)
        }
    }

    interface OnCountListener {
        fun onTick(millSec: Long, sec: Int)
    }
}