package com.acorn.testanything.testNested

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * Created by acorn on 2019-05-31.
 */
class NestedViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {
    init {
        orientation = VERTICAL
    }

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
//        val hiddenTop = dy > 0 && scrollY < 400
//        val showTop = dy < 0 && scrollY > 0 && !ViewCompat.canScrollVertically(target, -1)
//
//        if (hiddenTop || showTop) {
//            scrollBy(0, dy)
//            consumed?.let { it[1] = dy }
//        }
    }
}