package com.acorn.testanything.utils

import android.Manifest
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.acorn.testanything.testWithOutput.IOutput
import com.acorn.testanything.testWithOutput.ITest
import java.util.regex.Pattern

/**
 * Created by acorn on 2020/4/29.
 */
class SmsHelper(val mContext: Context, val handler: Handler, val onSmsListener: OnSmsListener) :
    ContentObserver(handler), ITest {

    override fun test(output: IOutput) {
        if (!PermissionUtils.hasPermissions(mContext, Manifest.permission.READ_SMS)) {
            PermissionUtils.requestPermissions(
                mContext, 0, true,
                Manifest.permission.READ_SMS
            )
            return
        }
        mContext.contentResolver.registerContentObserver(
            Uri.parse("content://sms/"), true, this
        )
    }

    override fun onChange(selfChange: Boolean, uri: Uri) {
        super.onChange(selfChange, uri)
        // 第一次回调 不是我们想要的 直接返回
        if (uri.toString() == "content://sms/raw") {
            return
        }
        // 第二次回调 查询收件箱里的内容
        val inboxUri = Uri.parse("content://sms/inbox")
        // 按时间顺序排序短信数据库
        val mCursor = mContext.getContentResolver().query(
            inboxUri, null, null,
            null, "date desc"
        )
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                // 获取手机号
                val address: String = mCursor.getString(mCursor.getColumnIndex("address"))
                Log.e("ADDRESS", address)
                // 获取短信内容
                val body: String = mCursor.getString(mCursor.getColumnIndex("body"))
                Log.e("ADDRESS", body)
                // 判断手机号是否为目标号码，服务号号码不固定请用正则表达式判断前几位。
                //加上这个判断必须知道发送方的电话号码，局限性比较高
//                if (!address.equals(mPhone)) {
//                    return;
//                }
                // 正则表达式截取短信中的6位验证码
                val regEx = "(?<![0-9])([0-9]{" + 6 + "})(?![0-9])"
                val pattern = Pattern.compile(regEx)
                val matcher = pattern.matcher(body)
                // 如果找到通过Handler发送给主线程
                while (matcher.find()) {
                    val code = matcher.group()
                    onSmsListener.onReceiveVerifyCode(code)
                }
            }
        }
        mCursor?.close()
    }

    interface OnSmsListener {
        fun onReceiveVerifyCode(code: String)
    }
}