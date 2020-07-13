package com.acorn.testanything.countdown

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_test_countdown.*
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/7/9.
 */
class CountdownViewModel : ViewModel() {
    private var disposable: Disposable? = null
    val countdownLiveData = MutableLiveData<Long>()

    fun startCountdown() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                countdownLiveData.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}