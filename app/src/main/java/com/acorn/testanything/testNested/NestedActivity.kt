package com.acorn.testanything.testNested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_nested.*

/**
 * Created by acorn on 2019-05-31.
 */
class NestedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested)
        viewPager.adapter = NestedPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}