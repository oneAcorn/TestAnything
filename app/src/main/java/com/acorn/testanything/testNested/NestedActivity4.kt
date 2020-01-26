package com.acorn.testanything.testNested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_nested4.*

/**
 * Created by acorn on 2019-06-05.
 */
class NestedActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested4)
        with(myDependencyView) {
            isFocusableInTouchMode = false
            isFocusable = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = NestedAdapter(context)
        }
    }
}