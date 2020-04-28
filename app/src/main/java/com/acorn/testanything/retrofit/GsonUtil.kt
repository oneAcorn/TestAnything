package com.acorn.testanything.retrofit

import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by acorn on 2019-06-11.
 */
fun Any.toJson(): String? {
    try {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

fun Any.toRequestBody(contentType: String = "application/json;charset=utf-8"): RequestBody {
    return RequestBody.create(MediaType.parse(contentType), this.toJson())
}
