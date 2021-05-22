package com.acorn.testanything.demo

import android.os.Bundle

/**
 * Created by acorn on 2021/5/22.
 */
data class Demo(
    val title: String,
    val id: Int? = null,
    val description: String? = null,
    val activity: Class<*>? = null,
    val bundle: Bundle? = null
)