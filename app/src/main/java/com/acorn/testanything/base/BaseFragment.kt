package com.acorn.testanything.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Created by Acorn on 2016/12/13.
 */

abstract class BaseFragment : Fragment() {
    open var rootView: View? = null

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater.inflate(layoutId, container, false)
            findViews()
            setupListener()
        }
        return rootView
    }

    protected abstract fun findViews()

    protected abstract fun setupListener()

    protected fun findViewById(@IdRes id: Int): View? {
        return rootView?.findViewById(id)
    }
}
