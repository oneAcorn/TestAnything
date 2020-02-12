package com.acorn.testanything.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*
import java.util.concurrent.Executors

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
}