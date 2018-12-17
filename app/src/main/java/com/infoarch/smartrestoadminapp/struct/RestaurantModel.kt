package com.infoarch.smartrestoadminapp.struct

import android.os.Parcel
import android.os.Parcelable

class RestaurantModel(
    Key: String,
    Address: String,
    BgColor: String,
    Color: Int,
    Image: String,
    Name: String,
    PhoneNumber: String,
    IsSelected: Boolean
) : Parcelable {

    var key: String = Key
    var address: String = Address
    var bgColor: String = BgColor
    var color: Int = Color
    var image: String = Image
    //    var location
    var name: String = Name
    var phoneNumber: String = PhoneNumber
    var isSelected: Boolean = IsSelected

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(address)
        parcel.writeString(bgColor)
        parcel.writeInt(color)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantModel> {
        override fun createFromParcel(parcel: Parcel): RestaurantModel {
            return RestaurantModel(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantModel?> {
            return arrayOfNulls(size)
        }
    }
}