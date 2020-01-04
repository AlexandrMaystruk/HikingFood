package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class DayView(
    menuId: Long,
    val number: Int,
    val breakfastProducts: MutableList<ProductView>,
    val lunchProducts: MutableList<ProductView>,
    val dinnerProducts: MutableList<ProductView>,
    val breakfastTotalWeight: Int,
    val lunchTotalWeight: Int,
    val dinnerTotalWeight: Int,
    val breakfastTotalWeightForAll: Int,
    val lunchTotalWeightForAll: Int,
    val dinnerTotalWeightForAll: Int,
    val isRelaxDay: Boolean = false

) : Parcelable, BaseViewModel(menuId) {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.createTypedArrayList(ProductView) as MutableList<ProductView>,
        parcel.createTypedArrayList(ProductView) as MutableList<ProductView>,
        parcel.createTypedArrayList(ProductView) as MutableList<ProductView>,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(number)
        parcel.writeTypedList(breakfastProducts)
        parcel.writeTypedList(lunchProducts)
        parcel.writeTypedList(dinnerProducts)
        parcel.writeInt(breakfastTotalWeight)
        parcel.writeInt(lunchTotalWeight)
        parcel.writeInt(dinnerTotalWeight)
        parcel.writeInt(breakfastTotalWeightForAll)
        parcel.writeInt(lunchTotalWeightForAll)
        parcel.writeInt(dinnerTotalWeightForAll)
        parcel.writeByte(if (isRelaxDay) 1 else 0)
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

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}