package com.acorn.testanything.retrofit

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by acorn on 2019-08-20.
 */
open class BaseResult() : Parcelable, IResponse {
    val code: String? = null

    override fun isSuccess(): Boolean {
        return code?.equals("0000") == true
    }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseResult> {
        override fun createFromParcel(parcel: Parcel): BaseResult {
            return BaseResult(parcel)
        }

        override fun newArray(size: Int): Array<BaseResult?> {
            return arrayOfNulls(size)
        }
    }
}