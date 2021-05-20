package com.acorn.testanything.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Created by acorn on 2021/5/19.
 */
fun Activity.hideNavigationBar(){
    //隐藏虚拟按键，并且全屏

    //隐藏虚拟按键，并且全屏
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
        val v: View = window.decorView
        v.systemUiVisibility = View.GONE
    } else if (Build.VERSION.SDK_INT >= 19) {
        //for new api versions.
        val decorView: View = window.decorView
        //or View.SYSTEM_UI_FLAG_FULLSCREEN
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
        window.setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED, WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
    }
}