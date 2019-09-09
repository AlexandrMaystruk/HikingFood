package com.gmail.maystruks08.hikingfood.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable

class DayView(
    val menuId: Long,
    val number: Int,
    val breakfastProducts: MutableList<ProductView>,
    val lunchProducts: MutableList<ProductView>,
    val dinnerProducts: MutableList<ProductView>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.createTypedArrayList(ProductView),
        parcel.createTypedArrayList(ProductView),
        parcel.createTypedArrayList(ProductView)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(menuId)
        parcel.writeInt(number)
        parcel.writeTypedList(breakfastProducts)
        parcel.writeTypedList(lunchProducts)
        parcel.writeTypedList(dinnerProducts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DayView> {
        override fun createFromParcel(parcel: Parcel): DayView {
            return DayView(parcel)
        }

        override fun newArray(size: Int): Array<DayView?> {
            return arrayOfNulls(size)
        }
    }
}