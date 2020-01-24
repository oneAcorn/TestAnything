package com.acorn.testanything.reflection

import java.lang.Exception
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * 面向切面编程
 * Created by acorn on 2020/1/24.
 */
class LoginInvocationHandler : InvocationHandler {
    //需要代理的对象
    private lateinit var target: Any
    private var userName: String? = null
    private var function: String? = null

    private constructor() {}

    constructor(target: Any) {
        this.target = target
        val clazz = target.javaClass
        val field = clazz.getDeclaredField("user")
        field.isAccessible = true
        val value: User? = field.get(target) as? User
        userName = value?.name
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
        function = method.name
        before()
        val result: Any? = if (args != null) method.invoke(target, *args)
        else method.invoke(target)
        after()
        return result
    }

    private fun before() {
        println("日志:用户 $userName 开始使用方法:$function before")
    }

    private fun after() {
        println("日志:用户 $userName 结束使用方法:$function after")
    }
}