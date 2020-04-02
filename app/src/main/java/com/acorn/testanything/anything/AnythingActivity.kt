package com.acorn.testanything.anything

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.log
import kotlinx.android.synthetic.main.activity_anything.*

/**
 * Created by acorn on 2019-08-01.
 */
class AnythingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
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
            infoTv.text =
                "手机型号:${android.os.Build.MODEL},品牌:${android.os.Build.BRAND},制造商:${android.os.Build.MANUFACTURER}"
        }

        alertDialogBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("hello")
                .setMessage("world")
                .setPositiveButton("ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        log("onSaveInstanceState")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        log("onRestoreInstanceState")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged,${newConfig.orientation}")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }
}