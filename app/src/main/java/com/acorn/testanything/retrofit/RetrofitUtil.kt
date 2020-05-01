package com.acorn.testanything.retrofit

import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2019-06-11.
 */
class RetrofitUtil private constructor() {
    private val client = OkHttpClient.Builder().addInterceptor(
        LoggingInterceptor.Builder()
            .loggable(true)//DEBUG时拦截日志
            .setLevel(Level.BODY)//打印内容配置 Level.NONE不打印
            .log(Platform.INFO)//log级别
            //.tag("HttpLog")//标签 request&&response-
            .request("HttpLog_Request")
            .response("HttpLog_Response")
            .build())
            .addInterceptor { chain ->
                var request = chain.request()
                val builder = request.newBuilder()
                request = builder.apply {
                    //在此添加header
//                    addHeader("version", PackageUtils.getAppVersion())
//                    addHeader("appChannel", PublicUtils.getApkChannel(Res.getContext()))
//                    addHeader("channel", Constant.FROM_ANDROID)
//                    addHeader("appName", Constant.appName)
//                    addHeader("Connection", "close")
                }.build()
                chain.proceed(request)
            }
            .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()

    companion object {
        const val CONNECT_TIME_OUT = 60
        const val READ_TIME_OUT = 60
        const val WRITE_TIME_OUT = 60

        val instance: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitUtil().getRetrofit() }
    }

    private fun getRetrofit(): Retrofit = Retrofit
            .Builder()
            .baseUrl(getBaseUrl()).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    private fun getBaseUrl(): String {
        //        return Env.UAT ? "http://10.103.11.173:8080/brokerservice-server/" :
        //                "http://mapi.sfbest.com/brokerservice-server/";
        return "https://sappbackend.q-gp.com/"
    }
}