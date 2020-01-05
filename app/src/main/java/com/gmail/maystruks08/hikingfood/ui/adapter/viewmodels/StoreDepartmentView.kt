package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class StoreDepartmentView(
    id: Long,
    val name: String
) : Parcelable, BaseViewModel(id) {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreDepartmentView> {
        override fun createFromParcel(parcel: Parcel): StoreDepartmentView {
            return StoreDepartmentView(parcel)
        }

        override fun newArray(size: Int): Array<StoreDepartmentView?> {
            return arrayOfNulls(size)
        }
    }

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}