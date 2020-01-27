package com.acorn.testanything.memory

import android.os.Bundle
import com.acorn.testanything.R
import com.acorn.testanything.utils.log
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 * Created by acorn on 2020/1/26.
 */
class TestMemoryLeakActivity : RxAppCompatActivity() {
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_memory_leak)

        disposable = Observable.interval(0, 2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(this.bindUntilEvent(ActivityEvent.DESTROY)) //这种方式可以解决内存泄漏的问题
            .subscribe {
                log("间隔接受数据$it")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        //我就不dispose(),测试内存泄漏用.这里即使dispose也会内存泄漏
        //除非使用RxLifeCycle才能解决
//        disposable?.dispose()
    }
}