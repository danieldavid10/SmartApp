package com.infoarch.smartrestoadminapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.infoarch.smartrestoadminapp.R
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.fragment_restaurant_info_.view.*

class RestaurantInfo_Fragment : Fragment() {

    private var restaurant:RestaurantModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_restaurant_info_, container, false)
        restaurant = arguments?.getParcelable("Restaurant")
        rootView.text_RestaurantTitle.text = restaurant?.name
        rootView.text_RestaurantAddress.text = restaurant?.address
        rootView.text_PhoneNumber.text = restaurant?.phoneNumber
        return rootView
    }

    companion object {
        fun setInformation(restaurant: RestaurantModel): RestaurantInfo_Fragment {
            val fragment = RestaurantInfo_Fragment()
            val data = Bundle()
            data.putParcelable("Restaurant", restaurant)
            fragment.arguments = data
            return fragment
        }
    }
}
