package com.acorn.testanything

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.acorn.testanything.utils.log

/**
 * Created by acorn on 2019-05-30.
 */
class TestViewGroup @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
    ViewGroup(context, attributeSet, defStyle) {
    private val testview = TestView(context, null, 0, attributeSet).apply {
        layoutParams = RelativeLayout.LayoutParams(100, 100)
        setBackgroundColor(Color.parseColor("#ff00ffff"))
    }

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.TestAttrs, defStyle, 0).apply {
            log("parent:${getString(R.styleable.TestAttrs_testAAA)}")
            recycle()
        }
        addView(testview)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        //不调用这个方法会导致childView.measureWidth=0
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        //MeasureSpec.AT_MOST=wrap_content,MeasureSpec.EXACTLY=match_parent
        log(
            "widthMode:$widthSpecMode,heightMode:$heightSpecMode.\n" +
                    "widthSize:$widthSpecSize,heightSize:$heightSpecSize.\n" +
                    "AT_MOST:${MeasureSpec.AT_MOST},EXACTLY:${MeasureSpec.EXACTLY}"
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount <= 0)
            return
        for (i in 0 until childCount) {
            val childView = getChildAt(i)

            childView.layout(l, t, l + childView.measuredWidth, t + childView.measuredHeight)
        }
    }

}