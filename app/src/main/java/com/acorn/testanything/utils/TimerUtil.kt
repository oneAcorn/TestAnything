package com.acorn.testanything.utils

import com.acorn.testanything.testWithOutput.IOutput
import com.acorn.testanything.testWithOutput.ITest
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/4/29.
 */
class TimerUtil : ITest {
    private var disposable: Disposable? = null

    override fun test(output: IOutput) {
        startCountDown(10, {
            output.output("count $it,disposable isDispose? ${disposable?.isDisposed}")
        }, {
            output.output("finished isDispose:${disposable?.isDisposed}")
        })
    }

    fun startCountDown(
        totalSecond: Int,
        onCounting: (Int) -> Unit,
        onCountDownFinished: () -> Unit
    ) {
        disposable = Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .map {
                totalSecond - it.toInt()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                if (it <= 0) {
                    disposable?.dispose()
                    onCountDownFinished()
                    return@subscribe
                }
                onCounting(it)
            }
    }

    fun dispose() {
        disposable?.dispose()
    }
}