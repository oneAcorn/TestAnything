package com.acorn.testanything.kotlin

/**
 * Created by acorn on 2020/4/28.
 */
class KotlinBasic {
    var what: Int? = 0
        get() {
            return if (field == 0)
                3
            else
                10
        }
        private set
    var setField = 0
        set(value) {
            field = if (value > 0) 3 else value
        }
}