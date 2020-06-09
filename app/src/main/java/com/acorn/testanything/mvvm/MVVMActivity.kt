package com.acorn.testanything.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acorn.testanything.R
import com.acorn.testanything.utils.logI
import kotlinx.android.synthetic.main.activity_mvvm.*

/**
 * Created by acorn on 2020/6/9.
 */
class MVVMActivity : AppCompatActivity() {
    private val mTestViewModel: TestViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TestViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        initVm()
        initView()
    }

    private fun initVm() {
        mTestViewModel.getStateLD().observe(this, Observer {
            logI("observe:$it")
        })
    }

    private fun initView() {
        sendBtn.setOnClickListener {
            mTestViewModel.queryState()
        }
    }
}