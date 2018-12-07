package com.infoarch.smartrestoadminapp.listeners

import com.infoarch.smartrestoadminapp.struct.RestaurantModel

interface RecyclerRestaurantListener {
    fun onClick(restaurant:RestaurantModel, position: Int)
    fun onInformationClick(restaurant:RestaurantModel, position: Int)
}