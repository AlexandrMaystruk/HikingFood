package com.gmail.maystruks08.hikingfood.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable
import com.gmail.maystruks08.domain.entity.Unit

data class DefaultIngredientView(
    val id: String,
    val name: String,
    val portionForOnePeople: Int,
    val portionForAllPeople: Int,
    val unit: Unit,
    var isSelected: Boolean = true
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readInt(),
        Unit.fromValue(parcel.readString()?:""),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(portionForOnePeople)
        parcel.writeInt(portionForAllPeople)
        parcel.writeString(unit.type)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DefaultIngredientView> {
        override fun createFromParcel(parcel: Parcel): DefaultIngredientView {
            return DefaultIngredientView(parcel)
        }

        override fun newArray(size: Int): Array<DefaultIngredientView?> {
            return arrayOfNulls(size)
        }
    }
}