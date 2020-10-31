package com.acorn.testanything.mmkv

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by acorn on 2020/10/31.
 */
data class TestBean(val str1: String?, val str2: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(str1)
        parcel.writeString(str2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestBean> {
        override fun createFromParcel(parcel: Parcel): TestBean {
            return TestBean(parcel)
        }

        override fun newArray(size: Int): Array<TestBean?> {
            return arrayOfNulls(size)
        }
    }
}