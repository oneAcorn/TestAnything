package com.acorn.testanything.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
 

/**
 * Created by AndyMao on 2015/12/21.
 */
public class NavigationBarHelper {
 
 
    /**
     * 是否有虚拟键
     *
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = true, hasBackKey = true;
        boolean ret = false;
 
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
                hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            }
 
            if ((!hasMenuKey) && (!hasBackKey)) {
                ret = true;
            }
        } catch (Exception e) {
            ret = false;
        }
 
        return ret;
    }
 
    /**
     * 隐藏虚拟键
     */
    public static void hideNavigation(Activity context) {
 
        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
//            Logger.get().d("myth hideNavigation  " + context.getClass().getSimpleName());
 
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
 
    /**
     * 隐藏虚拟键
     */
    public static void hideNavigation(View view) {
 
        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
//            Logger.get().d("myth hideNavigation  " + view.getClass().getSimpleName());
 
//            | View.SYSTEM_UI_FLAG_FULLSCREEN
 
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
 
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity context) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = context.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 显示虚拟键
     */
    public static void showNavigation(View view) {
 
        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
//            Logger.get().d("myth hideNavigation  " + view.getClass().getSimpleName());
 
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
 
 
}