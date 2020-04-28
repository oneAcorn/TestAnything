package com.acorn.testanything.rxjava

import android.os.Parcel
import android.os.Parcelable
import com.acorn.testanything.retrofit.BaseResult

/**
 * Created by acorn on 2019-08-20.
 */
class TestResult() : BaseResult(), Parcelable {
    val `data`: List<Data>? = null
    val businessCode: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestResult> {
        override fun createFromParcel(parcel: Parcel): TestResult {
            return TestResult(parcel)
        }

        override fun newArray(size: Int): Array<TestResult?> {
            return arrayOfNulls(size)
        }
    }
}

data class Data(
    val _id: String,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}