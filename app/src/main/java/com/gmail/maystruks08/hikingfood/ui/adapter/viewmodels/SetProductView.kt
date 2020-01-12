package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class SetProductView(
    id: Long,
    name: String,
    portionForOnePeople: Int,
    portionForAllPeople: Int
) : ProductView(id, name, portionForOnePeople, portionForAllPeople), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(portionForOnePeople)
        parcel.writeInt(portionForAllPeople)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SetProductView> {
        override fun createFromParcel(parcel: Parcel): SetProductView {
            return SetProductView(parcel)
        }

        override fun newArray(size: Int): Array<SetProductView?> {
            return arrayOfNulls(size)
        }
    }

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)
}
