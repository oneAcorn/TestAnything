package com.acorn.testanything.retrofit

import io.reactivex.observers.DisposableObserver
import java.lang.ref.WeakReference

/**
 * Created by acorn on 2019-08-20.
 */
open class BaseObserver<T : IResponse>(
    networkUI: INetworkUI?,
    private val model: ERROR_MODEL = ERROR_MODEL.LAYOUT,
    private val isShowProgressDialog: Boolean = true
) : DisposableObserver<T>() {
    private val weakUI = if (networkUI == null) null else WeakReference(networkUI)

    override fun onComplete() {
        if (isShowProgressDialog) {
            weakUI?.get()?.dismissProgressDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isShowProgressDialog) {
            weakUI?.get()?.showProgressDialog()
        }
    }

    override fun onNext(t: T) {
        val isNullData = isNullData(t)
        if (t.isSuccess() && !isNullData) {
            weakUI?.get()?.showContentLayout()
            success(t)
        } else if (isNullData) {
            weakUI?.get()?.showNullLayout()
            success(t)
        } else {
            doError(t = t)
        }
    }

    override fun onError(e: Throwable) {
        doError(e = e)
    }

    private fun doError(t: T? = null, e: Throwable? = null) {
        weakUI?.get()?.let {
            it.dismissProgressDialog()
            if (model == ERROR_MODEL.LAYOUT) {
                it.showErrorLayout()
            } else if (model == ERROR_MODEL.TOAST) {
                it.showToast(e?.message ?: "网络异常")
            }
        }
        error(t, e)
    }

    protected open fun isNullData(t: T): Boolean {
        return false
    }

    protected open fun error(t: T?, e: Throwable?) {

    }

    protected open fun success(t: T) {

    }

    enum class ERROR_MODEL {
        TOAST,
        LAYOUT,
        NONE
    }
}