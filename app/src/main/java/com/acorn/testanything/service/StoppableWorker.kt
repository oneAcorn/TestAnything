package com.acorn.testanything.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/6/3.
 */
class StoppableWorker(context: Context, parms: WorkerParameters) : Worker(context, parms) {
    private var mIsStop = false

    override fun doWork(): Result {
        var index = 0
        while (!mIsStop) {
            logI("StoppableWorker is Running:${index}")
            Thread.sleep(5000)
            index++
        }
        return Result.success()
    }

    /**
     * 调用cancelWorkxxx()时才会触发
     * 只会work在运行时执行onStopped，已经执行完成去取消任务是不会触发onStopped方法的。
     */
    override fun onStopped() {
        super.onStopped()
        logI("onStopped,Thread:${Thread.currentThread()}")
        mIsStop = true
    }
}