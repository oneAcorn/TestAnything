package com.acorn.testanything.drawable

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.activity_drawable.*

/**
 * Created by acorn on 2021/1/8.
 */
class DrawableActivity : AppCompatActivity() {
    private var count = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            //View从API Level 11才加入setLayerType方法
//            //关闭硬件加速
//            layout1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        val drawable = OverlayDrawable2(
            Color.parseColor("#ccffff00"),
            Color.parseColor("#a0ff00ff"),
            Color.parseColor("#8000ffff")
            , overlayCardCount = 1
        )
        layout1.setPadding(
            layout1.paddingLeft,
            layout1.paddingTop,
            layout1.paddingRight,
            drawable.getTotalOverlayHeight().toInt()
        )
        layout1.background = drawable

        btn.setOnClickListener {
            (layout1.background as? OverlayDrawable2)?.run {
                overlayCardCount = count % 3
                layout1.setPadding(
                    layout1.paddingLeft,
                    layout1.paddingTop,
                    layout1.paddingRight,
                    getTotalOverlayHeight().toInt()
                )
            }
            count++
            layout1.invalidate()
        }
    }
}