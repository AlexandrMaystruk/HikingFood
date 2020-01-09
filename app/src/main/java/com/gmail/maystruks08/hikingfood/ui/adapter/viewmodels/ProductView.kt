package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

open class ProductView(
    id: Long,
    val name: String,
    val portionForOnePeople: Int,
    val portionForAllPeople: Int,
    val parentId: Long? = null
) : Parcelable, BaseViewModel(id) {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(portionForOnePeople)
        parcel.writeInt(portionForAllPeople)
        parentId?.let { parcel.writeLong(it) }
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

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}