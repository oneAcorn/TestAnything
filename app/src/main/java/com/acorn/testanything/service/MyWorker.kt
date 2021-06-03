package com.acorn.testanything.service

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.acorn.testanything.mmkv.Caches
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2021/6/2.
 */
class MyWorker(context: Context, parms: WorkerParameters) : Worker(context, parms) {
    override fun doWork(): Result {
        //线程池,非主线程
        logI("Thread:${Thread.currentThread()},inputData:${inputData.getString("a")}")
        //更新进度
        setProgressAsync(Data.Builder().putInt("process", 30).build())
        Thread.sleep(2000)
        Caches.str1 = inputData.getString("a")
        //更新进度
        setProgressAsync(Data.Builder().putInt("process", 100).build())
        //这里不延时,是不会看到进度更新到100的,因为速度太快直接finish了.
        Thread.sleep(1000)
        val data = Data.Builder().putString("b", "My OutPutData").build()
        return Result.success(data)
    }

    /**
     * 取消Worker才会触发onStopped(),正常执行完毕的任务不会触发
     */
    override fun onStopped() {
        super.onStopped()
        logI("onStopped")
    }
}