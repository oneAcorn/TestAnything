package com.acorn.testanything.kotlin

/**
 * kotlin实现的双重检测单例模式
 * Created by acorn on 2020/4/28.
 */
class DoubleCheckSingleton private constructor() {
    companion object {
        val instance: DoubleCheckSingleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DoubleCheckSingleton()
        }
    }
}