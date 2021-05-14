package com.acorn.testanything

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.PointerIcon
import android.view.View
import com.acorn.testanything.utils.log

/**
 * Created by acorn on 2019-05-29.
 */
class TestView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    parentAttrs: AttributeSet? = null
) :
    View(context, attributeSet, defStyle) {

    init {
        context.obtainStyledAttributes(parentAttrs, R.styleable.TestAttrs, defStyle, 0).apply {
            log("son:${getString(R.styleable.TestAttrs_testBBBB)}")
            recycle()
        }
        setBackgroundColor(Color.GREEN)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        if (widthSpecMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            width = Math.min(widthSpecSize, 100)
        } else if (widthSpecMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            width = widthSpecSize
        }

        var height = 0
        if (heightSpecMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            height = Math.min(heightSpecSize, 100)
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            height = heightSpecSize
        }
        setMeasuredDimension(width, height)
    }

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        return super.onResolvePointerIcon(event, pointerIndex)
    }
}