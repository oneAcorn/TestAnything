package com.acorn.testanything.testWithOutput

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.okhttp.TestOkhttp
import kotlinx.android.synthetic.main.activity_output.*

/**
 * Created by acorn on 2020/3/1.
 */
class TestWithOutputActivity : AppCompatActivity(), IOutput {
    private val testItems: Array<ITest> = arrayOf(TestOkhttp())
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

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                curTest = testItems[position]
            }
        }
    }

    override fun println(str: String) {
        tv.text = "${tv.text}\n$str"
    }

    override fun clearLog() {
        tv.text = null
    }
}