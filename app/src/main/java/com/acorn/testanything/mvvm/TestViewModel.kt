package com.acorn.testanything.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/6/9.
 */
class TestViewModel : ViewModel() {
    private val stateLD = MutableLiveData<Int>()

    fun queryState() {
        val disposable = Observable.just(1)
            .subscribeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                stateLD.value = 2
            }
            .observeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                stateLD.value = 3
            }
            .observeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                stateLD.value = 4
            }
            .subscribe {
                stateLD.value = 5
            }
    }

    fun getStateLD(): LiveData<Int> {
        return stateLD
    }

    override fun onCleared() {
        super.onCleared()
    }
}