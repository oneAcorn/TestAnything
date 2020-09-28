package com.acorn.testanything.kotlin

import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by acorn on 2020/9/22.
 */

/**
 * 获取约定的类型信息
 */
fun ByteArray.getType(): Short {
    return ByteBuffer.wrap(this, EncryptConstant.HEADER_BYTE, EncryptConstant.TYPE_BYTE)
        .order(ByteOrder.LITTLE_ENDIAN).short
}

fun putDataByte(dataLength: Int, type: Short, callback: ByteBuffer.() -> Unit): ByteBuffer {
    return ByteBuffer.allocate(EncryptConstant.TOTAL_LENGTH_WITHOUT_DATA + dataLength)
        .apply {
            order(ByteOrder.LITTLE_ENDIAN)
            put(0x53)
            put(0x53)
            putShort(type)
            putInt(dataLength)
            callback()
            put(0x0d)
        }
}

fun ByteArray.getDataLength(): Int {
    return ByteBuffer.wrap(
        this,
        EncryptConstant.HEADER_BYTE + EncryptConstant.TYPE_BYTE,
        EncryptConstant.LENGTH_BYTE
    ).int
}

//fun ByteArray.getBody(): String {
//    return String(getBodyByteArray(), Charset.forName("utf-8"))
//}
//
///**
// * 获取data区的数据
// */
//fun ByteArray.getBodyByteArray(): ByteArray {
//    return if (size > 1) {
//        //由于ReadProtocol里截取的是除Header外的ByteArray，但是约定的data后面有一个1字节的End标识,所以要减去
//        ByteBuffer.wrap(this, 0, size - EncryptConstant.END_BYTE).array()
//    } else
//        this
//}

/**
 * 将字符串转换为固定长度的ByteArray，末尾补0
 */
fun String.toFixedByteArray(length: Int): ByteArray {
    val tempArray = this.toByteArray()
    if (tempArray.size > length)
        throw RuntimeException("this string'length more than $length")
    val byteArray = ByteArray(length)
    for ((index, byte) in tempArray.withIndex()) {
        byteArray[index] = byte
    }
    return byteArray
}

fun main() {
    val arr = "abc".toFixedByteArray(10)
    val bb = putDataByte(arr.size, EncryptConstant.TYPE_PUBLIC_KEY_RESPONSE) {
        put(arr)
    }
    val bbArr = bb.array()
    println("bbArr:$bbArr")

    val ss = ByteArray(8)
    ss[0] = 83
    ss[1] = 83
    ss[2] = 5
    ss[3] = 6
    ss[4] = 1
    ss[5] = 0
    ss[6] = 0
    ss[7] = 0
    val bf1 = ByteBuffer.wrap(ss, 4, 4)
    val byteArrNew = ByteArray(3)
    //由于ReadProtocol里截取的是除Header外的ByteArray，但是约定的data后面有一个1字节的End标识,所以要减去
    System.arraycopy(ss, 0, byteArrNew, 0, 3)
    println("")
}