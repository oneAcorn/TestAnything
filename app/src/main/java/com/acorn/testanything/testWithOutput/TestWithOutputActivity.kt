package com.acorn.testanything.testWithOutput

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.okhttp.TestFakeOkHttp
import com.acorn.testanything.okhttp.TestOkhttp
import com.acorn.testanything.rxjava.RxHeart
import com.acorn.testanything.rxjava.TestOperators
import com.acorn.testanything.utils.SmsHelper
import com.acorn.testanything.utils.TimerUtil
import com.acorn.testanything.utils.logI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_output.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by acorn on 2020/3/1.
 */
class TestWithOutputActivity : AppCompatActivity(), IOutput {
    //各种测试类
    private val testItems: Array<ITest> = arrayOf(
        TestOkhttp(), TestFakeOkHttp(), TimerUtil(),
        SmsHelper.testInstance(this, this),
        RxHeart(), TestOperators(), TestUniqueCode(this)
    )
    private lateinit var curTest: ITest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)

        initSpinner()
        curTest = testItems[spinner.selectedItemPosition]

        //测试提交
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
        val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA)
        tv.text = "${tv.text}\n${formatter.format(Date())}:$str"
    }

    override fun log(str: String) {
        val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA)
        logI("${formatter.format(Date())}:$str")
    }

    override fun outputByThread(str: String) {
        val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA)
        val d = Observable.just<String>("${formatter.format(Date())}:$str")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s ->
                tv.text = "${tv.text}\n$s"
            }
    }

    override fun clearLog() {
        tv.text = null
    }
}