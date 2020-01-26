package com.acorn.testanything.memory

import android.os.Bundle

import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

import java.util.concurrent.TimeUnit

import io.reactivex.Observable

/**
 * Created by acorn on 2020/1/26.
 */
class FADSF : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable.interval(0, 1, TimeUnit.HOURS)
            .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe()
    }

}
