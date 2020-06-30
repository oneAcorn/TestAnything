package com.acorn.testanything.dsl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by acorn on 2020/6/30.
 */
class TestDSLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerTextWatcher {
            afterTextChanged {
                var text=it
            }
        }
    }
}