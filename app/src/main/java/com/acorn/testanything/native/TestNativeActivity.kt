package com.acorn.testanything.native

import android.os.Bundle
import android.widget.Toast
import com.acorn.jnidemo.NativeHelper
import com.acorn.testanything.base.BaseDemoAdapterActivity
import com.acorn.testanything.demo.Demo

/**
 * Created by acorn on 2021/7/5.
 */
class TestNativeActivity : BaseDemoAdapterActivity() {
    lateinit var helper: NativeHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper = NativeHelper()
    }

    override fun getItems(): Array<Demo> {
        return arrayOf(Demo(title = "测试native方法", id = 1000,
            description = "因为在打so库时,必须在CMakeLists.txt中声明对应的java类,所以NativeHelper的包名也不能换\n" +
                    "(此so库从我的JniDemo项目中,app/build/intermediates/cmake/debug/obj/arm64-v8a/libnative-lib.so中获取)"))
    }

    override fun onItemClick(data: Demo, idOrPosition: Int) {
        when (idOrPosition) {
            1000 -> {
                Toast.makeText(this, "c方法:${helper.testNdk()}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}