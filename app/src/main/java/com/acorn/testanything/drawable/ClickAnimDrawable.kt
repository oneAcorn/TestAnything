package com.acorn.testanything.drawable

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.AccelerateInterpolator
import com.acorn.testanything.utils.dp

/**
 * @param initAlpha 初始透明度(0~255)
 * Created by acorn on 2020/12/29.
 */
class ClickAnimDrawable(private val size: Int = 20.dp, private val initAlpha: Int = 0) :
    Drawable() {
    private val innerCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#ffffff00")
        style = Paint.Style.FILL
    }
    private val outterCirclePaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#ffffff00")
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
    }
    private var tempInnerRadius = (size - 5).toFloat().dp
    private var tempOutterRadius = size.toFloat().dp
    private var tempAlpha: Int = initAlpha
    private lateinit var pressAnim: ValueAnimator

    init {
        initPressAnimator()
    }

    companion object {
        private const val ANIMATOR_SCALE_RATE = 1.8f
    }

    override fun draw(canvas: Canvas) {
        innerCirclePaint.alpha = tempAlpha
        outterCirclePaint.alpha = tempAlpha

        canvas.drawCircle(
            bounds.centerX().toFloat(),
            bounds.centerY().toFloat(),
            tempInnerRadius,
            innerCirclePaint
        )
        canvas.drawCircle(
            bounds.centerX().toFloat(),
            bounds.centerY().toFloat(),
            tempOutterRadius,
            outterCirclePaint
        )
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getIntrinsicHeight(): Int {
        return (size * ANIMATOR_SCALE_RATE).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (size * ANIMATOR_SCALE_RATE).toInt()
    }

    public fun press() {
        if (!pressAnim.isStarted) pressAnim.start()
    }

    private fun initPressAnimator() {
        val updateListener = ValueAnimator.AnimatorUpdateListener { animation ->
            val scale: Float = (animation.getAnimatedValue("mScale") as Number).toFloat()
            tempOutterRadius = scale * size / 2
            tempInnerRadius = (2.0f - scale).coerceAtLeast(0.6f) * size / 2
            val alpha: Int = (animation.getAnimatedValue("mAlpha") as Number).toInt()
            tempAlpha = alpha
            invalidateSelf()
        }
        val scaleHolder = PropertyValuesHolder.ofFloat("mScale", 1.0f,
            ANIMATOR_SCALE_RATE
        )
        val alphaHolder = PropertyValuesHolder.ofInt("mAlpha", 125, 255, 0)
        pressAnim = ValueAnimator.ofPropertyValuesHolder(scaleHolder, alphaHolder)
        pressAnim.interpolator = AccelerateInterpolator()
        pressAnim.addUpdateListener(updateListener)
        pressAnim.duration = 500
    }
}