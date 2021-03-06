package com.acorn.testanything.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.acorn.testanything.utils.dp

/**
 * 每层的透明度效果相同
 * Created by acorn on 2021/1/8.
 */
class OverlayDrawable(
    @ColorInt private val mainColor: Int,
    @ColorInt private val overlayColor1: Int,
    @ColorInt private val overlayColor2: Int,
    private val overlayHeight: Float = 7f.dp,
    private val roundRadius: Float = 7f.dp
) :
    Drawable() {
    private var mainRectF: RectF = RectF()
    private var overlayRectF1: RectF = RectF()
    private var overlayRectF2: RectF = RectF()
    private var boundsF = RectF()
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
//        canvas.drawARGB(150, 0,0,0)
        //需要在onDraw里初始化color,不然再次绘制时会有问题
        paint.color = Color.parseColor("#ccffffff")
        //新建layer,这里paint的透明度才是决定整体透明度
        val layerId =
            canvas.saveLayer(0f, 0f, boundsF.right, boundsF.bottom, paint, Canvas.ALL_SAVE_FLAG)
        //这里需要用不透明的,不然会有问题
        paint.color = mainColor
        canvas.drawRoundRect(mainRectF, roundRadius, roundRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        //这里需要用不透明的,不然会有问题
        paint.color = overlayColor1
        canvas.drawRoundRect(overlayRectF1, roundRadius, roundRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        //这里需要用不透明的,不然会有问题
        paint.color = overlayColor2
        canvas.drawRoundRect(overlayRectF2, roundRadius, roundRadius, paint)
        paint.xfermode = null
        /**
         * canvas.saveLayer()方法会返回一个int值，用于表示layer的ID，
         * 在我们对这个新layer绘制完成后可以通过调用canvas.restoreToCount(layer)或者canvas.restore()
         * 把这个layer绘制到canvas默认的layer上去，这样就完成了一个layer的绘制工作。
         */
        canvas.restoreToCount(layerId)
    }

    fun getTotalOverlayHeight() = overlayHeight * 2

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        boundsF = RectF(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat()
        )
        mainRectF = RectF(
            boundsF.left, boundsF.top, boundsF.right,
            boundsF.bottom - overlayHeight * 2f
        )
        overlayRectF1 = RectF(
            boundsF.left, boundsF.bottom - overlayHeight * 4, boundsF.right,
            boundsF.bottom - overlayHeight
        )
        overlayRectF2 = RectF(
            boundsF.left, boundsF.bottom - overlayHeight * 3, boundsF.right,
            boundsF.bottom
        )
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }
}