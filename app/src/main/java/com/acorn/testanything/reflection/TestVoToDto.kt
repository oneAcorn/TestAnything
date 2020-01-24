package com.acorn.testanything.reflection

import java.text.SimpleDateFormat

/**
 * 利用反射实现的Vo转Dto工具
 * 第三方工具如Apache提供的BeanUtils有更好的实现
 * Created by acorn on 2020/1/24.
 */
fun main() {
    val user = User("老王", "19")
    user.birthday = "1999-12-4"
    val userDto = UserDto()

    copyProperties(user,userDto)
    println("userDto:${userDto.name},${userDto.birthday}")
}

private fun copyProperties(source: Any, target: Any) {
    for (field in source.javaClass.declaredFields) {
        //私有属性设置为可直接访问
        field.isAccessible = true
        //获取属性中的值
        val value: Any = field.get(source)

        val targetField = target.javaClass.getDeclaredField(field.name)
        targetField.isAccessible = true
        if (field.name == "birthday") {
            val date = (SimpleDateFormat("yyyy-MM-dd")).parse(value.toString())
            targetField.set(target, date.time)
        } else {
            targetField.set(target, value)
        }
    }
}