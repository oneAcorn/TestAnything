package com.acorn.testanything.service

import android.content.Intent
import android.os.Build
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo
import kotlinx.android.synthetic.main.activity_rv_demo.*

/**
 * Created by acorn on 2021/5/21.
 */
class TestServiceActivity : BaseDemoAdapterActivity() {

    override fun getItems(): Array<Demo> {
        return arrayOf(
            Demo("startService")
        )
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            0 -> {
                val intent = Intent(this, TestService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
            }
        }
    }
}