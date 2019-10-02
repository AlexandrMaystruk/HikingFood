package com.gmail.maystruks08.hikingfood.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.domain.entity.Unit

open class ProductView(
    val id: Int,
    val name: String,
    val portionForOnePeople: Int,
    val portionForAllPeople: Int,
    val unit: Unit,
    val isChild: Boolean = false,
    var isSelected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        Unit.fromValue(parcel.readString() ?: ""),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(portionForOnePeople)
        parcel.writeInt(portionForAllPeople)
        parcel.writeString(unit.type)
        parcel.writeByte(if (isChild) 1 else 0)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductView> {
        override fun createFromParcel(parcel: Parcel): ProductView {
            return ProductView(parcel)
        }

        override fun newArray(size: Int): Array<ProductView?> {
            return arrayOfNulls(size)
        }
    }
}