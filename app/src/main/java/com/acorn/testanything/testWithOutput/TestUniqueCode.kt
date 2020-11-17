package com.acorn.testanything.testWithOutput

import androidx.fragment.app.FragmentActivity
import com.acorn.testanything.utils.SystemUtils

/**
 * Created by acorn on 2020/11/2.
 */
class TestUniqueCode(private val context: FragmentActivity) : ITest {
    override fun test(output: IOutput) {
        output.output(SystemUtils.getDeviceUniqueId(context))
    }
}