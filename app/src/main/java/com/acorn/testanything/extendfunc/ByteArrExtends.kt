package com.acorn.testanything.extendfunc

import com.acorn.testanything.utils.ByteUtils

/**
 * Created by acorn on 2022/9/21.
 */

fun String.toByteArrayBigOrder(): ByteArray = ByteUtils.toByteArrayBigOrder(this)