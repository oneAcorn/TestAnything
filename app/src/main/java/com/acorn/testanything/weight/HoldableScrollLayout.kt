package com.acorn.testanything.weight

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.acorn.testanything.utils.logI
import com.acorn.testanything.utils.screenHeight
import java.lang.ref.WeakReference
import kotlin.math.absoluteValue


/**
 * Created by acorn on 2021/6/23.
 */
class HoldableScrollLayout : LinearLayout, NestedScrollingParent {
    private val mHandler: Handler
    //RecyclerView最大偏移举例
    private val maxScrollY = screenHeight / 2
    //handler的延时消息是否已经remove
    private var isMsgRemoved = false
    private var isAnimating = false
    private val resetAnim: ValueAnimator = ValueAnimator()
    //RecyclerView是否还能向上滚动
    private var canScrollUp = false
    private var canScrollDown = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        orientation = VERTICAL
        mHandler = ResetViewStateHandler(WeakReference(this))
        initAnim()
    }


    private fun initAnim() {
        resetAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                isAnimating = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                isAnimating = false
            }

            override fun onAnimationCancel(animation: Animator?) {
                isAnimating = false
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        resetAnim.addUpdateListener {
            scrollTo(0, (it.animatedValue as Int))
        }
        resetAnim.interpolator = DecelerateInterpolator()
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        if (nestedScrollAxes != ViewCompat.SCROLL_AXIS_VERTICAL) //只处理纵向滑动
            return super.onStartNestedScroll(child, target, nestedScrollAxes)
        if (scrollY.absoluteValue > maxScrollY)
            return super.onStartNestedScroll(child, target, nestedScrollAxes)
        canScrollUp = target.canScrollVertically(-1)
        canScrollDown = target.canScrollVertically(1)
        logI("onStartNestedScroll maxY:$maxScrollY,scrollY:$scrollY")
        if (isOffseted() || (target is RecyclerView && !canScrollUp || !canScrollDown)) {
            return true
        }
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        super.onNestedPreScroll(target, dx, dy, consumed)
        if (isAnimating)
            return
        logI("onNestedPreScroll dx:$dx,dy:$dy,scrollY:${scrollY}")
        if (!isMsgRemoved) {
            mHandler.removeCallbacksAndMessages(null)
            isMsgRemoved = true
        }
        if (!isOffseted()) {
            if (dy < 0 && canScrollUp) { //把RecyclerView往下拖动,如果RecyclerView可以往上滑,那就交给RecycleView自己处理
                return
            } else if (dy > 0 && canScrollDown) {
                return
            }
        }
        val realY = getRealScrollY(dy)
        if (realY != 0) {
            scrollBy(0, realY)
            consumed[1] = dy //消费掉RecyclerView的y轴滑动
        }
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        logI("onNestedPreFling velocityY:$velocityY")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    private fun getRealScrollY(y: Int): Int {
        val curScrollY = scrollY
        val targetScrollY = curScrollY + y
        var realY = y
        if (targetScrollY.absoluteValue > maxScrollY) { //不能超过最大可偏移距离
            if (curScrollY < 0) {
                realY = targetScrollY.absoluteValue - maxScrollY + y
            } else if (curScrollY > 0) {
                realY = y - (targetScrollY.absoluteValue - maxScrollY)
            }
        }
        logI("scrollBy curY:$scrollY,y:$y,realY:$realY")
        return realY
    }

    override fun onStopNestedScroll(child: View) {
        super.onStopNestedScroll(child)
        mHandler.sendMessageDelayed(mHandler.obtainMessage(), 5000)
        isMsgRemoved = false
    }

    /**
     * RecyclerView是否已经偏移(scrollBy())
     */
    private fun isOffseted(): Boolean {
        return scrollY != 0
    }

    private fun startResetAnim(y: Int) {
        resetAnim.cancel()
        resetAnim.setIntValues(y, 0)
        resetAnim.start()
    }

    private class ResetViewStateHandler(private val layout: WeakReference<HoldableScrollLayout>) :
        Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            removeCallbacksAndMessages(null)
            layout.get()?.run {
                startResetAnim(scrollY)
            }
        }
    }
}