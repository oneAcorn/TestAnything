package com.acorn.testanything.reflection

/**
 * Created by acorn on 2020/1/24.
 */
class LoginImpl(private val user: User) : ILogin {

    override fun login(user: User) {
        println("已登录 ${user.name}")
    }

    override fun logout(user: User) {
        println("退出登录 ${user.name}")
    }
}