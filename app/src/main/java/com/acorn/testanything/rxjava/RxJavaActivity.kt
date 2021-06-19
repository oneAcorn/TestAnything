package com.acorn.testanything.rxjava

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.retrofit.BaseObserver
import com.acorn.testanything.retrofit.RetrofitUtil
import com.acorn.testanything.rxjava.Response.TestResult
import com.acorn.testanything.utils.MyException
import com.acorn.testanything.utils.log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2019-08-22.
 */
class RxJavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)


        btn1.setOnClickListener {
            Observable.create<String> {
                rLog("create")
                it.onNext("hello")
                it.onComplete()
            }
                .observeOn(Schedulers.newThread())
                .map {
                    rLog("map1")
                    it
                }
                .subscribe {
                    rLog("subscribe")
                }
        }

        btn2.setOnClickListener {
            Observable.create<String> {
                rLog("create1")
                it.onNext("hello")
                it.onComplete()
            }
                .doOnSubscribe {
                    rLog("doOnSubscribe1")
                }
                .map {
                    rLog("map1")
                    it
                }
                .doOnNext {
                    rLog("doOnNext")
                }
                .observeOn(Schedulers.newThread())
                .map {
                    rLog("map2")
                    it
                }
                .flatMap {
                    Observable.create<String> { emitter ->
                        rLog("create2")
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    rLog("map3")
                    it
                }
                .doOnSubscribe {
                    rLog("doOnSubscribe2")
                }
                .subscribeOn(Schedulers.computation())
                .subscribe {
                    rLog("subscribe")
                }
        }

        btn3.setOnClickListener {
            Observable.create<String> {
                rLog("create")
                it.onNext("...")
                it.onComplete()
            }
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    rLog("doOnSubscribe 1")
                }
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    rLog("doOnSubscribe 2")
                }
                .subscribe {
                    rLog("onNext")
                }
        }
        btn4.setOnClickListener {
            RetrofitUtil.instance.create(HttpService::class.java)
                .testGetType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<TestResult>(null) {

                })
        }
    }

    private fun rLog(str: String) {
        log("$str -> ${Thread.currentThread().name}")
    }

    /**
     * 获取指定名称线程
     */
    private fun getNamedScheduler(name: String): Scheduler {
        return Schedulers.from {
            Executors.newCachedThreadPool { r ->
                Thread(r, name)
            }
        }
    }

    /**
     * 固定时间轮询
     */
    fun pollAtFixedInterval() {
        val disposable = Observable.interval(0, 5, TimeUnit.SECONDS)
            .flatMap {
                //某个检查二维码被扫描状态的接口
                Observable.just(QrCodeBean(0, null, "EXPIRED"))
            }
            .takeUntil {
                when (it.imCodeStr) {
                    "BINDED" -> {
                        return@takeUntil true
                    }
                    "EXPIRED" -> { //二维码过期
                        throw MyException("EXPIRED", 1001)
                    }
                    else -> {
                        return@takeUntil false
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }

    /**
     * 根据接口调用时间调整的轮询
     */
    fun poll() {
        val disposable = Observable.create<QrCodeBean> {
            it.onNext(QrCodeBean(1, null, "BINDED"))
        }
            .doOnNext {
                when (it.imCodeStr) {
                    "BINDED" -> { //用户扫码,走到下一步
                    }
                    "EXPIRED" -> { //二维码过期
                        throw MyException("EXPIRED", 1001)
                    }
                    else -> {
                        throw MyException(it.imCodeStr ?: "unknown", 1000)
                    }
                }
            }
            .retryWhen {
                return@retryWhen it
                    .filter { throwable ->
                        val imException = throwable as? MyException
                        if (imException?.code == 1001) { //二维码过期直接抛出异常,重新生成新的二维码
                            throw throwable
                        } else {
                            return@filter true
                        }
                    }.delay(5, TimeUnit.SECONDS) //5秒轮询
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
    }

    /**
     * @param type 0:微信,1:其他
     */
    data class QrCodeBean(val type: Int, val bitmap: Bitmap?, val imCodeStr: String? = null)
}