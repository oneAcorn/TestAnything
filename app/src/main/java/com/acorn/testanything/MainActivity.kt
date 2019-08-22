package com.acorn.testanything

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.acorn.testanything.RegEx.RegExActivity
import com.acorn.testanything.anything.AnythingActivity
import com.acorn.testanything.rxjava.RxJavaActivity
import com.acorn.testanything.testAnimator.TestAnimatorActivity
import com.acorn.testanything.testNested.NestedActivity2
import com.acorn.testanything.testNested.NestedActivity3
import com.acorn.testanything.testNested.NestedActivity4
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestActivity::class.java))
        }
        nestedBtn.setOnClickListener {
            startActivity(Intent(this, NestedActivity2::class.java))
        }
        nestedBtn2.setOnClickListener {
            startActivity(Intent(this, NestedActivity3::class.java))
        }
        nestedBtn3.setOnClickListener {
            startActivity(Intent(this, NestedActivity4::class.java))
        }
        animatorBtn.setOnClickListener {
            startActivity(Intent(this, TestAnimatorActivity::class.java))
        }
        regexBtn.setOnClickListener {
            startActivity(Intent(this, RegExActivity::class.java))
        }
        anyThingBtn.setOnClickListener {
            startActivity(Intent(this, AnythingActivity::class.java))
        }
        rxjavaBtn.setOnClickListener {
            startActivity(Intent(this, RxJavaActivity::class.java))
        }
    }
}
