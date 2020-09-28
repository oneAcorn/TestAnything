package com.acorn.testanything.utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * Created by acorn on 2020/9/27.
 */
fun Context.rawToFile(@RawRes resId: Int): File {
//    val inputStream = resources.openRawResource(resId)
    val path = filesDir.absolutePath + "/tmp"
    val file = File(path)
    if (!file.exists()) {
        file.mkdirs()
    }
//    val imgPath = "$path/temp.gif"
    val imgPath = SdUtils.copyResources(this, resId)
    return File(imgPath)
}