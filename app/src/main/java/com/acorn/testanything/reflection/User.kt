package com.acorn.testanything.reflection

/**
 * Created by acorn on 2020/1/24.
 */
class User {
    var name: String? = null
    var age: String? = null
    //字符串类型的birthday
    var birthday: String? = null

    constructor() {}

    constructor(name: String?, age: String?) {
        this.name = name
        this.age = age
    }
}