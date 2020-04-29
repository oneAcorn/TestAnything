package com.acorn.testanything.testWithOutput

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.okhttp.TestFakeOkHttp
import com.acorn.testanything.okhttp.TestOkhttp
import com.acorn.testanything.utils.SmsHelper
import com.acorn.testanything.utils.TimerUtil
import com.acorn.testanything.utils.log
import kotlinx.android.synthetic.main.activity_output.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by acorn on 2020/3/1.
 */
class TestWithOutputActivity : AppCompatActivity(), IOutput {
    private val testItems: Array<ITest> = arrayOf(
        TestOkhttp(), TestFakeOkHttp(), TimerUtil(),
        SmsHelper(this, Handler(), object : SmsHelper.OnSmsListener {
            override fun onReceiveVerifyCode(code: String) {
                Toast.makeText(
                    this@TestWithOutputActivity, "收到$code,当前线程:${Thread.currentThread()}",
                    Toast.LENGTH_LONG
                ).show()
                output("收到$code,当前线程:${Thread.currentThread()}")
            }
        })
    )
    private lateinit var curTest: ITest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)

        initSpinner()
        curTest = testItems[spinner.selectedItemPosition]

        testBtn.setOnClickListener {
            curTest.test(this@TestWithOutputActivity)
        }
        clearBtn.setOnClickListener {
            clearLog()
        }
        EventBus.getDefault().register(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        curTest.test(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(s: String) {
        log("收到消息:$s")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun initSpinner() {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.item_select,
            testItems.map {
                it.javaClass.simpleName
            })
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                curTest = testItems[position]
            }
        }
    }

    override fun output(str: String) {
        tv.text = "${tv.text}\n$str"
    }

    override fun clearLog() {
        tv.text = null
    }
}