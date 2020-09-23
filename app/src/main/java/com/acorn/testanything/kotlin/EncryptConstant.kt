package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/9/22.
 */
object EncryptConstant {
    //各标识长度
    const val HEADER_BYTE = 2
    const val TYPE_BYTE = 2
    const val LENGTH_BYTE = 4
    const val END_BYTE = 1
    const val TOTAL_HEADER_LENGTH = HEADER_BYTE + TYPE_BYTE + LENGTH_BYTE
    const val TOTAL_LENGTH_WITHOUT_DATA = HEADER_BYTE + TYPE_BYTE + LENGTH_BYTE + END_BYTE

    //Type
    //心跳
    const val TYPE_PULSE: Short = 0x00

    //1、心跳返回消息
    const val TYPE_PULSE_RESPONSE: Short = 0x600

    //请求对方公钥
    const val TYPE_PUBLIC_KEY_REQUEST: Short = 0x01

    //2、请求对方公钥返回消息
    const val TYPE_PUBLIC_KEY_RESPONSE: Short = 0x601

    //发送自己的公钥
    const val TYPE_SEND_PUBLIC_KEY: Short = 0x05

    //3、发送自己ID，公钥返回消息
    const val TYPE_SEND_PUBLIC_KEY_RESPONSE: Short = 0x605

    //发送请求会客厅密码
    const val TYPE_LIVING_ROOM_PWD_REQUEST: Short = 0x08

    //4、发送请求会客厅密码返回消息
    const val TYPE_LIVING_ROOM_PWD_RESPONSE: Short = 0x608

    //5、查询本终端相关请求命令信息(下线重新上线后，是否有其它人向本终端请求信息)
    const val TYPE_IS_SOMEONE_REQUEST_REQUEST: Short = 0x10

    /**
     * 下线重新上线后，是否有其它人向本终端请求信息,如果没有，服务端向其返回0，如果有则返回消息数量。
    同时服务将缓存中的相关消息发送给查询终端。具体消息参看相关消息内容格式
     */
    const val TYPE_IS_SOMEONE_REQUEST_RESPONSE: Short = 0x610

    //6、查询本终端相关返回结果信息(下线又上线后，是否有其它终端将查询结果返回给本终端)
    const val TYPE_IS_SOMEONE_RESPONSE_REQUEST: Short = 0x16

    /**
     * 6、查询本终端相关返回结果信息
     * 下线又上线后，是否有其它终端将查询结果返回给本终端,没有服务端返回0，否则返回消息条数。
    同时将缓存消息发送给查询终端，消息格式参看相关内容。
     */
    const val TYPE_IS_SOMEONE_RESPONSE_RESPONSE: Short = 0x616

    //7、发送会客厅密码（二维码扫描相关）
    const val TYPE_SEND_LIVING_ROOM_PWD_REQUEST: Short = 0x36

    //7、发送会客厅密码（二维码扫描相关）返回消息
    const val TYPE_SEND_LIVING_ROOM_PWD_RESPONSE: Short = 0x636

    //1、错误
    const val TYPE_ERROR: Short = 0x300
}