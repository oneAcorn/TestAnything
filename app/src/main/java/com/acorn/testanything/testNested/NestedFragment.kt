package com.acorn.testanything.testNested

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acorn.testanything.R
import com.acorn.testanything.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_nested.*

/**
 * Created by acorn on 2019-05-31.
 */
class NestedFragment : Fragment() {

    companion object {
        fun newInstance(): NestedFragment {
//        val args = Bundle()
            val fragment = NestedFragment()
//        fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nested,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListener()
    }

    fun setupListener() {
        with(recyclerView) {
            isFocusableInTouchMode = false
            isFocusable = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = NestedAdapter(context)
        }
    }
}