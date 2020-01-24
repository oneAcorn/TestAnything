package com.acorn.testanything.reflection

import java.lang.reflect.Proxy

/**
 * 面向切片编程
 * Created by acorn on 2020/1/24.
 */
fun main() {
    val user = User("老张", "11")

    val loginInterface: ILogin = LoginImpl(user)
//    原始方法
//    loginInterface.login(user)
//    loginInterface.logout(user)

    //设置切面
    val loginInvocationHandler = LoginInvocationHandler(loginInterface)
    //反射创建代理
    val loginProxy: ILogin = Proxy.newProxyInstance(
        loginInterface.javaClass.classLoader,
        loginInterface.javaClass.interfaces,
        loginInvocationHandler
    ) as ILogin
    loginProxy.login(user)
    loginProxy.logout(user)
}