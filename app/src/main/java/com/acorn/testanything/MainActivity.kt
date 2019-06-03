package com.acorn.testanything

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.acorn.testanything.testAnimator.TestAnimatorActivity
import com.acorn.testanything.testNested.NestedActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestActivity::class.java))
        }
        nestedBtn.setOnClickListener {
            startActivity(Intent(this, NestedActivity::class.java))
        }
        animatorBtn.setOnClickListener {
            startActivity(Intent(this, TestAnimatorActivity::class.java))
        }
    }
}
