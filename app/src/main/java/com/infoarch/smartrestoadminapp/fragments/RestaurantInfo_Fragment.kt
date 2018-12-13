package com.infoarch.smartrestoadminapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.infoarch.smartrestoadminapp.R
import kotlinx.android.synthetic.main.fragment_restaurant_info_.view.*

class RestaurantInfo_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_restaurant_info_, container, false)
        rootView.text_RestaurantTitle.text = arguments?.getString("Name")
        rootView.text_RestaurantAddress.text = arguments?.getString("Address")
        rootView.text_PhoneNumber.text = arguments?.getString("Phone")
        return rootView
    }

    companion object {

        fun setInformation(name:String,address:String,phone:String):RestaurantInfo_Fragment{
            val fragment = RestaurantInfo_Fragment()
            val data = Bundle()
            data.putString("Name",name)
            data.putString("Address",address)
            data.putString("Phone",phone)
            fragment.arguments = data
            return fragment
        }
    }


}
