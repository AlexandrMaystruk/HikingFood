package com.gmail.maystruks08.hikingfood.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.domain.entity.Unit

open class ShoppingListItemView(
    val productId: Int,
    val name: String,
    val totalWeight: Int,
    val price: Double = 0.0,
    val unit: Unit,
    var isPurchased: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        Unit.fromValue(parcel.readString() ?: ""),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(productId)
        parcel.writeString(name)
        parcel.writeInt(totalWeight)
        parcel.writeDouble(price)
        parcel.writeString(unit.type)
        parcel.writeByte(if (isPurchased) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingListItemView> {
        override fun createFromParcel(parcel: Parcel): ShoppingListItemView {
            return ShoppingListItemView(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingListItemView?> {
            return arrayOfNulls(size)
        }
    }
}