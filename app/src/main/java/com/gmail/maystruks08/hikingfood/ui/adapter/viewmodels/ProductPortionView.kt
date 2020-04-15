package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class ProductPortionView(
     id: Long,
    val name: String,
    val portionMin: Int,
    val portionMax: Int,
    val portionValue: Int
) : Parcelable, BaseViewModel(id) {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(portionMin)
        parcel.writeInt(portionMax)
        parcel.writeInt(portionValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductPortionView> {
        override fun createFromParcel(parcel: Parcel): ProductPortionView {
            return ProductPortionView(parcel)
        }

        override fun newArray(size: Int): Array<ProductPortionView?> {
            return arrayOfNulls(size)
        }
    }
}