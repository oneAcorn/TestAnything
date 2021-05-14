package com.acorn.testanything

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.PointerIcon
import android.view.ViewGroup
import android.widget.Toast
import com.acorn.testanything.utils.log
import com.acorn.testanything.utils.logI

/**
 * Created by acorn on 2019-05-30.
 */
class TestViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    ViewGroup(context, attributeSet, defStyle) {
    private val testview = TestView(context, null, R.attr.TestDefAttrs, attributeSet).apply {
//        layoutParams = RelativeLayout.LayoutParams(100, 100)
//        setBackgroundColor(Color.parseColor("#ff00ffff"))
    }

    init {
//        context.obtainStyledAttributes(attributeSet, R.styleable.TestAttrs, defStyle, 0).apply {
//            log("parent:${getString(R.styleable.TestAttrs_testAAA)}")
//            recycle()
//        }
//        addView(testview)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pointerIcon = PointerIcon.getSystemIcon(context, PointerIcon.TYPE_ARROW)
        }
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

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.car)
        logI("onResolvePointerIcon bitmap:$bitmap,sdkInt:${Build.VERSION.SDK_INT},x:${event?.x},y:${event?.y}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return PointerIcon.create(bitmap, 0f,0f)
        }
        return super.onResolvePointerIcon(event, pointerIndex)
    }

//    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
//        Toast.makeText(context, "onResolvePointerIcon", Toast.LENGTH_SHORT).show()
//        return super.onResolvePointerIcon(event, pointerIndex)
//    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount <= 0)
            return
        for (i in 0 until childCount) {
            val childView = getChildAt(i)

            childView.layout(l, t, l + childView.measuredWidth, t + childView.measuredHeight)
        }
    }

}