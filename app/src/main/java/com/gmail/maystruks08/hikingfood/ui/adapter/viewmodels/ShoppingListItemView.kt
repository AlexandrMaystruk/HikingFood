package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class ShoppingListItemView(
    id: Long,
    val name: String,
    val totalWeight: Int,
    val unit: String
) : Parcelable, BaseSelectableViewModel(id) {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(totalWeight)
        parcel.writeString(unit)
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

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}