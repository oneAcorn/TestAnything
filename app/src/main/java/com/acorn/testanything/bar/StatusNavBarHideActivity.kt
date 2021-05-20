package com.acorn.testanything.bar

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.acorn.testanything.R
import com.acorn.testanything.utils.NavigationBarHelper
import com.acorn.testanything.utils.hideNavigationBar
import kotlinx.android.synthetic.main.activity_status_bar_hide.*


/**
 * Created by acorn on 2021/5/19.
 */
class StatusNavBarHideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar_hide)

        updateNavBarState()
        hideNavBtn.setOnClickListener {
            NavigationBarHelper.hideBottomUIMenu(this)
            updateNavBarState()
        }
    }

    override fun onResume() {
        super.onResume()

        hideNavigationBar()
    }

    private fun updateNavBarState() {
        hideNavBtn.text = "NavBar:${NavigationBarHelper.hasNavigationBar(this)}"
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        }
//        isNavigationBarChanged(this, object : OnNavigationStateListener {
//            override fun onNavigationState(isShowing: Boolean, b: Int) {
//                Toast.makeText(
//                    this@StatusNavBarHideActivity,
//                    "isShowing:$isShowing",
//                    Toast.LENGTH_SHORT
//                ).show()
//                if (isShowing)
//                    hideNavigationBar()
//            }
//        })
    }

    /**
     * 虚拟导航栏显示、隐藏监听
     * 该方法只有在导航栏发生变化时才会触发
     */
    private fun isNavigationBarChanged(
        activity: Activity,
        onNavigationStateListener: OnNavigationStateListener?
    ) {
        val height: Int = getNavigationBarHeight(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            ViewCompat.setOnApplyWindowInsetsListener(activity.window.decorView) { v, insets ->
                var isShowing = false
                var b = 0
                if (insets != null) {
                    b = insets.systemWindowInsetBottom
                    isShowing = b == height
                }
                if (onNavigationStateListener != null && b <= height) {
                    onNavigationStateListener.onNavigationState(isShowing, b)
                }
                ViewCompat.onApplyWindowInsets(v, insets)
            }
        }
    }

    interface OnNavigationStateListener {
        fun onNavigationState(isShowing: Boolean, b: Int)
    }

    /**
     * 获取虚拟导航栏高度 方法1
     */
    private fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}