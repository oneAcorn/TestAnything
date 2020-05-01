package com.acorn.testanything.rxjava

import com.acorn.testanything.retrofit.BaseResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by acorn on 2019-06-10.
 */
interface HttpService {

    @Headers("Content-Type: application/json;charset=utf-8", "Accept: application/json")
    @GET("http://yapi.quantgroups.com/mock/187/vcc/help-center/api/suggest/type")
    fun testGetType(): Observable<TestResult>

    @Headers("Content-Type: application/json;charset=utf-8", "Accept: application/json")
    @GET("api/tab/loan/home/v4")
    fun testXyqb(): Observable<BaseResult>
}