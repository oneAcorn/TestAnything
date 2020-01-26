package com.acorn.testanything.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*

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
    }

    private fun rLog(str: String) {
//        recordTv.text =
//            recordTv.text.toString() + "\n".takeIf { recordTv.text.toString().isEmpty() == false } + "${str}:${Thread.currentThread().name}"
        log(str + ":${Thread.currentThread().name}")
    }
}