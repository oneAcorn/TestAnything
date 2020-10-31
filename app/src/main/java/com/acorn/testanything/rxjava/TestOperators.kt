package com.acorn.testanything.rxjava

import com.acorn.testanything.testWithOutput.IOutput
import com.acorn.testanything.testWithOutput.ITest
import com.acorn.testanything.utils.MyException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.BiFunction

/**
 * Created by acorn on 2020/5/29.
 */
class TestOperators : ITest {
    override fun test(output: IOutput) {
//        testCombineLatest(output)
        test1(output)
    }

    private fun test1(output: IOutput) {
        val list = mutableListOf<String>()
        list.add("a")
        list.add("bb")
        list.add("c")
        val a = Observable.fromIterable(list)
            .filter {
                output.output("filter:$it")
                it == "a"
            }
            .subscribe {
                output.output("subscribe:$it")
            }
    }

    private fun getIntObservable(): Observable<Int> {
        return Observable.create {
            val sleep = Random().nextInt(10) * 100L
            Thread.sleep(sleep)
            val res = Random().nextInt(10)
            if (res > 8) {
                it.onNext(res)
                it.onComplete()
            } else {
                it.onError(Throwable("error"))
            }
        }
    }

    private fun getStringObservable(tag: String): Observable<String> {
        return Observable.create {
            val sleep = Random().nextInt(10) * 100L
            Thread.sleep(sleep)
            val res = Random().nextInt(10)
            if (res > 8) {
                it.onNext("$tag $res")
                it.onComplete()
            } else {
                it.onError(MyException("$tag error"))
            }
        }
    }

    private fun testCombineLatest(output: IOutput) {
        val a = Observable.combineLatest(
            getStringObservable("A"),
            getStringObservable("B"),
            getStringObservable("C"),
            Function3<String, String, String, String> { t1, t2, t3 -> "$t1,$t2,$t3" })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<String>() {
                override fun onComplete() {
                    output.output("onComplete")
                }

                override fun onNext(t: String) {
                    output.output(t)
                }

                override fun onError(e: Throwable) {
                    output.output("onError ${e.message ?: "onError"}")
                }

            })
    }
}