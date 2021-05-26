package com.acorn.testanything.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.acorn.testanything.R
import com.acorn.testanything.demo.Demo
import com.acorn.testanything.demo.DemoAdapter
import kotlinx.android.synthetic.main.activity_rv_demo.*

/**
 * Created by acorn on 2021/5/22.
 */
abstract class BaseDemoAdapterActivity : AppCompatActivity() {
    private val onItemClickListener: (data: Demo, position: Int) -> Unit =
        { data, position ->
            if (data.activity != null) {
                val intent = Intent(this, data.activity)
                if (null != data.bundle) {
                    intent.putExtras(data.bundle)
                }
                startActivity(intent)
            } else {
                onItemClick(data, position)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_demo)
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@BaseDemoAdapterActivity)
            adapter = DemoAdapter(this@BaseDemoAdapterActivity, getItems()).apply {
                onItemClickListener = this@BaseDemoAdapterActivity.onItemClickListener
            }
        }
    }

    abstract fun getItems(): Array<Demo>

    abstract fun onItemClick(data: Demo, idOrPosition: Int)
}