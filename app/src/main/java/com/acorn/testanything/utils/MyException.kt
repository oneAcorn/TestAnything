package com.acorn.testanything.utils

import java.lang.Exception

/**
 * Created by acorn on 2020/5/29.
 */
class MyException(str: String,val code: Int? = 0) : Exception(str)