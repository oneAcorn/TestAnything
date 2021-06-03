package com.acorn.testanything.utils

import android.R.attr.data
import android.content.Context
import java.io.*


/**
 * Created by acorn on 2021/6/3.
 */
fun <T : Serializable> Context.saveSerializableByFile(key: String, value: T) {
    val fos: FileOutputStream
    val oos: ObjectOutputStream
    try {
        fos = openFileOutput(key, Context.MODE_PRIVATE)
        oos = ObjectOutputStream(fos)
        oos.writeObject(value)
        oos.close()
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T : Serializable> Context.getSerializableByFile(key: String): T? {
    var obj: T? = null
    val fis: FileInputStream
    val ois: ObjectInputStream
    try {
        fis = openFileInput(key)
        ois = ObjectInputStream(fis)
        obj = ois.readObject() as? T
        ois.close()
        fis.close()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return obj
}