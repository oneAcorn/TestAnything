package com.acorn.testanything.mmkv

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

/**
 * Created by acorn on 2020/6/9.
 */

/**
 * 判断clazz是否实现了interfaceClazz接口
 */
fun <T, R> isImplementInterface(clazz: Class<T>, interfaceClazz: Class<R>): Boolean {
    var isImplement = false
    for (c in clazz.interfaces) {
        if (c == interfaceClazz) {
            isImplement = true
        }
    }
    return isImplement
}

/**
 * 获取类型
 */
fun getType(type: Type): Type {
    when (type) {
        is ParameterizedType -> {
            return getGenericType(type)
        }
        is TypeVariable<*> -> {
            return getType(type.bounds[0])
        }
    }
    return type
}

fun getGenericType(type: ParameterizedType): Type {
    if (type.actualTypeArguments.isEmpty()) return type
    val actualType = type.actualTypeArguments[0]
    when (actualType) {
        is ParameterizedType -> {
            return actualType.rawType
        }
        is GenericArrayType -> {
            return actualType.genericComponentType
        }
        is TypeVariable<*> -> {
            return getType(actualType.bounds[0])
        }
    }
    return actualType
}