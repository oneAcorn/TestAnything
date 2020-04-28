package com.acorn.testanything.retrofit

/**
 * Created by acorn on 2019-08-20.
 */
interface INetworkUI {
    /**
     * 显示加载框
     */
    fun showProgressDialog()

    /**
     * 关闭加载框
     */
    fun dismissProgressDialog()

    /**
     * 错误页面
     */
    fun showErrorLayout()

    /**
     * 正常显示的页面
     */
    fun showContentLayout()

    /**
     * 数据为空的页面
     */
    fun showNullLayout()

    /**
     * 显示toast
     */
    fun showToast(string: String)

    /**
     * 重新加载请求
     * 若使用自定义的error布局,此方法会自动寻找@id/retryBtn这个ID,并把此方法加入点击事件
     */
    fun retry()
}