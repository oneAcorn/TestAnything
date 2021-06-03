package com.acorn.testanything.service

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.mmkv.Caches
import com.acorn.testanything.utils.getSerializableByFile
import com.acorn.testanything.utils.logI
import com.acorn.testanything.utils.saveSerializableByFile
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by acorn on 2021/5/21.
 */
class TestServiceActivity : BaseDemoAdapterActivity() {
    private var count = 0
    private var stoppableWorkerId: UUID? = null

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo(
                "startService",
                description = "android8.0以上,app在后台时(前台时可以直接用startService())必须使用startForegroundService()启动service," +
                        "并且service得及时(5秒内)调用startForeground()方法",
                subItems = arrayListOf(
                    Demo(
                        "延时5秒startForegroundService()",
                        description = "无论app前台后台都可以启动",
                        id = 1000
                    ),
                    Demo(
                        "延时5秒startService()",
                        description = "app在后台时无法成功启动.但如果app前台启动service后,再切换到后台," +
                                "是可以保持存活的,存活时间没有startForegroundService()持久.\n" +
                                "Service启动的线程并不会受到Service生命周期的影响.",
                        id = 1001
                    ),
                    Demo(
                        "Service是否存活",
                        description = "实际测试发现:startForegroundService()在开启多个其他App后仍能保持存活," +
                                "startService()则会在内存较少时destroy",
                        id = 1002
                    )
                )
            ),
            Demo(
                "IntentService",
                description = "IntentService onHandleIntent()回调在线程中运行.运行完毕onHandleIntent()后,自动onDestroy().",
                subItems = arrayListOf(
                    Demo("启动service", id = 2000),
                    Demo(
                        "发送消息",
                        description = "如果在上一个消息的onHandleIntent()回调还没处理完,那么会等到其结束后," +
                                "再收到新消息的onHandleIntent()回调.全部执行完成后destroy",
                        id = 2001
                    )
                )
            ),
            Demo(
                "WorkManager",
                description = "WorkManager的设计用意就是取代后台服务，由系统统一管理你的周期性后台服务，" +
                        "并且自动兼容API23以下，API23以下自动在底层使用AlarmManager + BroadcastReceiver实现，" +
                        "而高于API23会使用JobScheduler实现。所以这是一个能取代闹钟定时器的后台功能。" +
                        "并且在高版本里闹钟功能其实已经不太能正常使用了。使用WorkManager取代所有周期或者长时间的后台工作是必需必要的。",
                subItems = arrayListOf(
                    Demo("run worker", id = 3000),
                    Demo("observe worker", id = 3001)
                )
            ),
            Demo(
                "可停止的WorkManager",
                subItems = arrayListOf(
                    Demo("run worker", id = 4000),
                    Demo(
                        "cancel worker", id = 4001,
                        description = "取消任务后,如果任务正在运行会回调Worker的onStopped()"
                    )
                )
            )
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            1000 -> {
                Handler().postDelayed({
                    val intent = Intent(this, TestService::class.java)
                    intent.putExtra("isCreateNotification", true)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //android8.0以上,app在后台时必须使用此方法启动service,并且service得及时(5秒内)调用startForeground()方法
                        startForegroundService(intent)
                    } else {
                        Toast.makeText(this, "小于8.0", Toast.LENGTH_SHORT).show()
                    }
                }, 5000)
            }
            1001 -> {
                Handler().postDelayed({
                    val intent = Intent(this, TestService::class.java)
                    //即使在android8.0以上,只要app是在前台,调用此方法也没事.
                    startService(intent)
                }, 5000)
            }
            1002 -> {
                val intent = Intent(TestService.BROAD_ISALIVE)
                sendBroadcast(intent)
            }
            2000 -> {
                startIntentService()
            }
            2001 -> {
                sendMsgToIntentService()
            }
            3000 -> {
                runWorker()
            }
            3001 -> {
                observeWorker()
            }
            4000 -> {
                runStoppableWorker()
            }
            4001 -> {
                cancelWorkerById()
            }
        }
    }

    private fun startIntentService() {
        val intent = Intent(this, MyIntentService::class.java)
        startService(intent)
    }

    private fun sendMsgToIntentService() {
        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("msg", "msg$count")
        startService(intent)
        count++
    }

    private fun runWorker() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(false) //触发时设备是否充电
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) //触发时网络状态
            .setRequiresBatteryNotLow(false) //指定设备电池是否不应低于临界阈值
            .setRequiresStorageNotLow(false) //指定设备可用存储是否不应低于临界阈值
            .build()
//            .setRequiresDeviceIdle(true)//触发时设备是否为空闲,需要targetSdk>=23
//            .addContentUriTrigger(myUri, false) //指定内容{@link android.net.Uri}时是否应该运行{@link WorkRequest}更新

        val randomInt = Random().nextInt(100)
        val inputStr = "abcd$randomInt"
        val inputData = Data.Builder().putString("a", inputStr).build()
        logI("inputData:$inputStr")
        val request =
            OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(inputData)
                .setConstraints(constraints)
                .setInitialDelay(5L, TimeUnit.SECONDS)
                .keepResultsForAtLeast(1L, TimeUnit.HOURS) //设置任务的结果保存时间
                .build()
        saveSerializableByFile("workerId", request.id)
        WorkManager.getInstance(this)
            .enqueue(request)
    }

    private fun observeWorker() {
        val workerId = getSerializableByFile<UUID>("workerId") ?: return
        logI("observe Worker($workerId),${Caches.str1}")
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerId).observe(this,
            Observer<WorkInfo> {
                when (it.state) {
                    WorkInfo.State.BLOCKED -> {
                        logI("堵塞")
                    }
                    WorkInfo.State.RUNNING -> {
                        logI("正在运行,当前进度:${it.progress.getInt("process", 0)}%")
                    }
                    WorkInfo.State.ENQUEUED -> {
                        logI("任务入队")
                    }
                    WorkInfo.State.CANCELLED -> {
                        logI("任务取消")
                    }
                    WorkInfo.State.FAILED -> {
                        logI("失败")
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        logI("成功,返回值:${it.outputData.getString("b")}")
                    }
                }
            })
    }

    private fun runStoppableWorker() {
        val request =
            OneTimeWorkRequest.Builder(StoppableWorker::class.java)
                .setInitialDelay(2L, TimeUnit.SECONDS)
                .build()
        stoppableWorkerId = request.id
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this,
            Observer<WorkInfo> {
                when (it.state) {
                    WorkInfo.State.BLOCKED -> {
                        logI("堵塞")
                    }
                    WorkInfo.State.RUNNING -> {
                        logI("正在运行,当前进度:${it.progress.getInt("process", 0)}%")
                    }
                    WorkInfo.State.ENQUEUED -> {
                        logI("任务入队")
                    }
                    WorkInfo.State.CANCELLED -> {
                        logI("任务取消")
                    }
                    WorkInfo.State.FAILED -> {
                        logI("失败")
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        logI("成功,返回值:${it.outputData.getString("b")}")
                    }
                }
            })
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun cancelWorkerById() {
        stoppableWorkerId?.let {
            WorkManager.getInstance(this).cancelWorkById(it)
        }
    }
}