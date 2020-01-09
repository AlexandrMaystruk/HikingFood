package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import java.util.*

class MenuView(
     id: Long,
    var name: String,
    var peopleCount: Int,
    var countOfReceptions: Int,
    var relaxDayCount: Int,
    var dateOfStartMenu: Date,
    var startFrom: TypeOfMeal,
    var totalWeight: Int
) : Parcelable , BaseViewModel(id) {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        Date(parcel.readLong()),
        TypeOfMeal.fromValue(parcel.readInt()),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(countOfReceptions)
        parcel.writeInt(peopleCount)
        parcel.writeInt(relaxDayCount)
        parcel.writeLong(dateOfStartMenu.time)
        parcel.writeInt(startFrom.type)
        parcel.writeInt(totalWeight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuView> {
        override fun createFromParcel(parcel: Parcel): MenuView {
            return MenuView(parcel)
        }

        override fun newArray(size: Int): Array<MenuView?> {
            return arrayOfNulls(size)
        }
    }

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}