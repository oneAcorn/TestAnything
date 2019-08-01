package com.acorn.testanything.anything

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_anything.*

/**
 * Created by acorn on 2019-08-01.
 */
class AnythingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anything)
        openAppBtn.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.coloros.wallet")
            if (intent == null) {
                Toast.makeText(this@AnythingActivity, "你没安装这个应用", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(intent)
            }
        }

        appInfoBtn.setOnClickListener {
            infoTv.text = "手机型号:${android.os.Build.MODEL},品牌:${android.os.Build.BRAND}"
        }
    }
}