package com.acorn.testanything.testAnimator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.MotionEventCompat
import com.acorn.testanything.utils.dp
import kotlin.math.abs

/**
 * Created by acorn on 2020/12/29.
 */
class ClickView : View {
    private var clickDrawable: ClickAnimDrawable = ClickAnimDrawable(20.dp)

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private val minTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        clickDrawable.callback = this
        clickDrawable.setBounds(0, 0, clickDrawable.intrinsicWidth, clickDrawable.intrinsicHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        clickDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) return false
        //兼容
        when (MotionEventCompat.getActionMasked(event)) {
            MotionEvent.ACTION_DOWN -> startDragging(event)
            MotionEvent.ACTION_MOVE -> onDragging(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> stopDragging(event)
        }
        return true
    }

    private fun startDragging(event: MotionEvent?) {
        event ?: return
        lastX = event.x
        lastY = event.y
    }

    private fun onDragging(event: MotionEvent?) {

    }

    private fun stopDragging(event: MotionEvent?) {
        event ?: return
        val centerX = event.x
        val centerY = event.y
        val offsetX = abs(centerX - lastX)
        val offsetY = abs(centerY - lastY)
        if (offsetX < minTouchSlop && offsetY < minTouchSlop) {
            val clickDrawableRadius = clickDrawable.intrinsicWidth / 2
            val left = centerX - clickDrawableRadius
            val top = centerY - clickDrawableRadius
            val right = centerX + clickDrawableRadius
            val bottom = centerY + clickDrawableRadius
            clickDrawable.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            clickDrawable.press()
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        //允许drawable刷新自己以执行动画
        return who == clickDrawable || super.verifyDrawable(who)
    }
}