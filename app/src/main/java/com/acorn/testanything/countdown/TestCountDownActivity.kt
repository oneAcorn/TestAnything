package com.acorn.testanything.countdown

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acorn.testanything.R
import com.acorn.testanything.mmkv.Caches
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_test_countdown.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

/**
 * Created by acorn on 2020/7/9.
 */
class TestCountDownActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private val mCountdownViewModel: CountdownViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CountdownViewModel::class.java)
    }
    private var isBindService = false
    private val serviceConnection: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                isBindService = true
                if (service !is CountDownTimerService.MsgBinder) return
                val countdownService = service.service
                countdownService.setOnCountDownTimerListener(object :
                    CountDownTimerService.OnCountDownTimerListener {
                    override fun onTick1(sec: Int) {
                        countdownTv3.text = "Service CountdownTimer 经过:${sec}秒"
                    }

                    override fun onTick2(sec: Int) {
                        countdownTv4.text = "Service Rxjava 经过:${sec}秒"
                    }

                })
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_countdown)
        countdownBtn.setOnClickListener {
            countdown1()
            countdown2()
            countdown34()
            countdown5()
            countdown6()
            countdown7()
            Caches.lastCountTimeStamp = System.currentTimeMillis()
        }
        realTimeBtn.setOnClickListener {
            realTimeSpanTv.text =
                "真实经过时间:${(System.currentTimeMillis() - Caches.lastCountTimeStamp) / 1000}秒"
        }
    }

    private fun countdown1() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                countdownTv1.text = "Activity rxjava 经过:${it}秒"
            }
    }

    private fun countdown2() {
        mCountdownViewModel.countdownLiveData.observe(this, Observer {
            countdownTv2.text = "Activity MVVM rxjava 经过:${it}秒"
        })
        mCountdownViewModel.startCountdown()
    }

    /**
     * 测试表明，使用service+CountDownTimer最靠谱
     */
    private fun countdown34() {
        val serviceIntent = Intent(this, CountDownTimerService::class.java)
        serviceIntent.putExtra(CountDownTimerService.TOTAL_SEC, 10000)
        serviceIntent.putExtra(CountDownTimerService.COUNT_DOWN_MILL_STEP, 1000L)
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)
    }

    private fun countdown5() {
        countdown(10, 2) { sec ->
            countdownTv5.text = "倒计时:${sec}秒"
        }
    }

    private fun countdown6() {
        count(3) {
            countdownTv6.text = "CountUtil service CountDownTimer 经过:${it}秒"
        }
    }

    private fun countdown7() {
        val maxValue = Long.MAX_VALUE
        val countdownTimer = object : CountDownTimer(maxValue, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val millSec = maxValue - millisUntilFinished
                val sec = (millSec.toDouble() / 1000).roundToInt()
                countdownTv7.text = "Activity CountDownTimer 经过:${sec}秒"
            }

            override fun onFinish() {}
        }
        countdownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        if (isBindService)
            unbindService(serviceConnection)
    }
}