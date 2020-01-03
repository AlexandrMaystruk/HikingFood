package com.gmail.maystruks08.hikingfood.ui.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.domain.entity.Unit
import com.gmail.maystruks08.hikingfood.core.base.adapter.TypesFactory
import com.gmail.maystruks08.hikingfood.core.base.adapter.ViewModel

class SetProductView(
    id: Int,
    name: String,
    portionForOnePeople: Int,
    portionForAllPeople: Int,
    unit: Unit,
    val products: List<ProductView>
) : ProductView(id, name, portionForOnePeople, portionForAllPeople, unit), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        Unit.fromValue(parcel.readString() ?: ""),
        parcel.createTypedArrayList(ProductView) as List<ProductView>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(portionForOnePeople)
        parcel.writeInt(portionForAllPeople)
        parcel.writeString(unit.type)
        parcel.writeTypedList(products)
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
