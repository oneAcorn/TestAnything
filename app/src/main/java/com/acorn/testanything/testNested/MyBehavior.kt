package com.acorn.testanything.testNested

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.acorn.testanything.R

/**
 * Created by acorn on 2019-06-05.
 */
class MyBehavior constructor(context: Context, attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.myDependencyView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        dependency as RecyclerView
        Log.i("MyBehavior", "scrollY:${dependency.scrollY},y:${dependency.y}")
        child.y = dependency.y
        return true
    }
}