package com.acorn.testanything.reflection

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by acorn on 2020/1/24.
 */
fun main() {
    //获取Class对象的三种方式
    //一 通过forName获取对象是无法确定类型的,所以使用无限制泛型通配符Class<*>(#kotlin)/Class<?>(#java)
    val user_class: Class<*> = Class.forName("com.acorn.testanything.reflection.User")
    (user_class.newInstance() as? User).let {
        it?.name = "wang"
        println(it?.name)
    }

    //二 通过已知类型获取对象(userTwo.javaClass(#kotlin)/userTwo.getClass(#java)),
    // 由于对象类型已知所以这个Class是User或其子类(Class<out User>(#kotlin)/Class<? extends User>(#java))
    val userTwo = User()
    val user_class_two: Class<out User> = userTwo.javaClass
    println("同一个类只有一个Class对象,所以user_class === user_class_two=${user_class === user_class_two}")

    //三 直接根据类型获取Class对象(User.class(#java)/User::class.java(#kotlin)),这种方式最直接,效率最高
    val user_class_three: Class<User> = User::class.java
    println("同一个类只有一个Class对象,所以user_class === user_class_three=${user_class === user_class_three}")

    //***************************************************************

    //反射获取信息
    println("类名${user_class.name}")
    println("父类名${user_class.superclass}")

    val constructors: Array<Constructor<*>> = user_class.declaredConstructors
    println("所有构造方法:")
    for (c in constructors) {
        println(c)
    }

    val methods: Array<Method> = user_class.declaredMethods
    println("所有方法:")
    for (m in methods) {
        println(m)
    }

    val fields: Array<Field> = user_class.declaredFields
    println("所有成员变量:")
    for (f in fields) {
        println(f)
    }

    //*****************************************************************

    //反射调用构造器
    //newInstance只能调用无参构造方法
    val userInstance1 = user_class.newInstance()

    //获取相应的构造方法
    val constructor: Constructor<*> =
        user_class.getConstructor(String::class.java, String::class.java)
    val user: User? = constructor.newInstance("张三", "18") as User
    println("User:${user?.name},${user?.age}")
    //******************************************************

    //反射调用方法
    val user2: User = user_class.newInstance() as User
    val method: Method = user_class.getMethod("setName", String::class.java)
    method.invoke(user2, "王五") //调用方法
    println("User:${user2.name},${user2.age}")
    //*****************************************
}