package com.infoarch.smartrestoadminapp.struct

class RestaurantModel {

    var key: String
    var address: String
    var bgColor: String
    var color: Int = 0
    var image: String
    //    var location
    var name: String
    var phoneNumber: String
    var isSelected: Boolean

    constructor(
        Key: String, Address: String, BgColor: String, Color: Int, Image: String,
        Name: String, PhoneNumber: String, isSelected: Boolean
    ) {

        this.key = Key
        this.address = Address
        this.bgColor = BgColor
        this.color = Color
        this.image = Image
        this.name = Name
        this.phoneNumber = PhoneNumber
        this.isSelected = isSelected
    }
}