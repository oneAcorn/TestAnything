package com.acorn.testanything.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SystemUtils {

    public static String getDeviceUniqueId(Context context) {
        return getSerial() + getAndroidId(context);
    }


    public static String getSerial() {
        try {
            String str = Build.class.getField("SERIAL").get(null).toString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Administrator
     * 2017-1-13
     * TODO
     * ANDROID_ID
     * 2.2（Froyo，8）版本系统会不可信，来自主要生产厂商的主流手机，至少有一个普遍发现的bug，这些有问题的手机相同的ANDROID_ID: 9774d56d682e549c
     * 但是如果返厂的手机，或者被root的手机，可能会变
     *
     * @param context
     * @return
     */
    private static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }



    /**
     *   ANDROID_ID(恢复出厂+刷机会变) + 序列号(android 10会unknown/android 9需要设备权限)+品牌    +机型
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getUniqueIdentificationCode(FragmentActivity context){
        String androidId =  Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String uniqueCode ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            /** 需要权限 且仅适用9.0。 10.0后又不能获取了*/
            uniqueCode = androidId + Build.getSerial()+Build.BRAND+ Build.MODEL;
        }else{
            uniqueCode = androidId + Build.SERIAL+Build.BRAND+ Build.MODEL;
        }
        return uniqueCode;
    }

    /**
     * MD5加密 格式一致
     */
    private static String toMD5(String text){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString().substring(8,24);
    }
}
