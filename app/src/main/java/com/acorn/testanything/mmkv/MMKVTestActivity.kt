package com.acorn.testanything.mmkv

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import com.acorn.testanything.utils.logI
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_mmkv_test.*

/**
 * Created by acorn on 2020/10/31.
 */
class MMKVTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mmkv_test)
        btn1.setOnClickListener {
            Caches.str1 = "abc"
            Caches.testBean1 = TestBean(null, "abc")
            Caches.str2 = "def"
            Caches.testBean2 = TestBean("abc", "def")
        }
        readBtn.setOnClickListener {
            logI("caches ${Caches.str1},${Caches.testBean1},${Caches.str2},${Caches.testBean2}")
        }
    }
}