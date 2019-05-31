package com.acorn.testanything.testNested

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.support.v4.view.ViewPager
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.OverScroller
import android.animation.ValueAnimator
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.animation.Interpolator
import com.acorn.testanything.R


/**
 * Created by acorn on 2019-05-31.
 */
class NestedViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {
    private val TAG = "NestedViewGroup"

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll")
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        Log.e(TAG, "onNestedScrollAccepted")
    }

    override fun onStopNestedScroll(target: View) {
        Log.e(TAG, "onStopNestedScroll")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.e(TAG, "onNestedScroll")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedPreScroll")
        val hiddenTop = dy > 0 && scrollY < mTopViewHeight
        val showTop = dy < 0 && scrollY >= 0 && !ViewCompat.canScrollVertically(target, -1)

        if (hiddenTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    private val TOP_CHILD_FLING_THRESHOLD = 3
    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        var consumed = consumed
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target is RecyclerView && velocityY < 0) {
            val firstChild = target.getChildAt(0)
            val childAdapterPosition = target.getChildAdapterPosition(firstChild)
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0f), consumed)
        } else {
            animateScroll(velocityY, computeDuration(velocityY), consumed)
        }
        return true
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        //不做拦截 可以传递给子View
        return false
    }

    override fun getNestedScrollAxes(): Int {
        Log.e(TAG, "getNestedScrollAxes")
        return 0
    }

    /**
     * 根据速度计算滚动动画持续时间
     * @param velocityY
     * @return
     */
    private fun computeDuration(velocityY: Float): Int {
        var velocityY = velocityY
        val distance: Int
        if (velocityY > 0) {
            distance = Math.abs(mTop!!.height - scrollY)
        } else {
            distance = Math.abs(mTop!!.height - (mTop!!.height - scrollY))
        }


        val duration: Int
        velocityY = Math.abs(velocityY)
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY))
        } else {
            val distanceRatio = distance.toFloat() / height
            duration = ((distanceRatio + 1) * 150).toInt()
        }

        return duration

    }

    private fun animateScroll(velocityY: Float, duration: Int, consumed: Boolean) {
        val currentOffset = scrollY
        val topHeight = mTop!!.height
        if (mOffsetAnimator == null) {
            mOffsetAnimator = ValueAnimator()
            mOffsetAnimator!!.interpolator = mInterpolator
            mOffsetAnimator!!.addUpdateListener { animation ->
                if (animation.animatedValue is Int) {
                    scrollTo(0, animation.animatedValue as Int)
                }
            }
        } else {
            mOffsetAnimator!!.cancel()
        }
        mOffsetAnimator!!.duration = Math.min(duration, 600).toLong()

        if (velocityY >= 0) {
            mOffsetAnimator!!.setIntValues(currentOffset, topHeight)
            mOffsetAnimator!!.start()
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                mOffsetAnimator!!.setIntValues(currentOffset, 0)
                mOffsetAnimator!!.start()
            }

        }
    }

    private var mTop: View? = null
    private var mNav: View? = null
    private var mViewPager: ViewPager? = null

    private var mTopViewHeight: Int = 0

    private var mScroller: OverScroller
    private var mVelocityTracker: VelocityTracker? = null
    private var mOffsetAnimator: ValueAnimator? = null
    private val mInterpolator: Interpolator? = null
    private var mTouchSlop: Int
    private var mMaximumVelocity: Int
    private var mMinimumVelocity:Int

    private val mLastY: Float = 0.toFloat()
    private val mDragging: Boolean = false

    init {
        orientation = LinearLayout.VERTICAL

        mScroller = OverScroller(context)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mMaximumVelocity = ViewConfiguration.get(context)
            .scaledMaximumFlingVelocity
        mMinimumVelocity = ViewConfiguration.get(context)
            .scaledMinimumFlingVelocity

    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }




    override fun onFinishInflate() {
        super.onFinishInflate()
        mTop = findViewById(R.id.id_stickynavlayout_topview)
        mNav = findViewById(R.id.id_stickynavlayout_indicator)
        val view = findViewById<View>(R.id.id_stickynavlayout_viewpager) as? ViewPager ?: throw RuntimeException(
            "id_stickynavlayout_viewpager show used by ViewPager !"
        )
        mViewPager = view
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getChildAt(0).measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val params = mViewPager!!.layoutParams
        params.height = measuredHeight - mNav!!.measuredHeight
        setMeasuredDimension(measuredWidth, mTop!!.measuredHeight + mNav!!.measuredHeight + mViewPager!!.measuredHeight)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTop!!.measuredHeight
    }


    fun fling(velocityY: Int) {
        mScroller.fling(0, scrollY, 0, velocityY, 0, 0, 0, mTopViewHeight)
        invalidate()
    }

    override fun scrollTo(x: Int,y: Int) {
        var varY=y
        if (y < 0) {
            varY = 0
        }
        if (y > mTopViewHeight) {
            varY = mTopViewHeight
        }
        if (y != scrollY) {
            super.scrollTo(x, varY)
        }
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }
    }
}