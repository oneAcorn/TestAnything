package com.acorn.testanything.testNested

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * Created by acorn on 2019-05-31.
 */
class NestedPagerAdapter constructor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val titles = listOf("标题1", "标题2", "标题3")

    override fun getItem(position: Int): Fragment {
        return NestedFragment.newInstance()
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}