package com.infoarch.smartrestoadminapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.infoarch.smartrestoadminapp.R
import com.infoarch.smartrestoadminapp.RestaurantMainActivity
import com.infoarch.smartrestoadminapp.adapters.RestaurantAdapter
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.listeners.RecyclerRestaurantListener
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*

class RestaurantList : Fragment() {

    private val layoutManager by lazy { LinearLayoutManager(context) }
    private var restaurantList: ArrayList<RestaurantModel>? = null
    private lateinit var adapter: RestaurantAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_RestaurantList)
        val rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        recycler = rootView.recyclerView_RestaurantsList as RecyclerView
        restaurantList = arguments?.getParcelableArrayList("RestaurantList")
        setRecyclerView(restaurantList!!)
        return rootView
    }

    companion object {
        fun setRestaurantList(restaurantList: ArrayList<RestaurantModel>): RestaurantList {
            val fragment = RestaurantList()
            val data = Bundle()
            data.putParcelableArrayList("RestaurantList", restaurantList)
            fragment.arguments = data
            return fragment
        }
    }

    private fun setRecyclerView(restaurantList: ArrayList<RestaurantModel>) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = layoutManager
        adapter = (RestaurantAdapter(restaurantList, object : RecyclerRestaurantListener {

            override fun onClick(restaurant: RestaurantModel, position: Int) {
                activity?.toastMessage(restaurant.name)
            }

            override fun onInformationClick(restaurant: RestaurantModel, position: Int) {
                activity?.goToActivity<RestaurantMainActivity> {
                    this.putExtra("Restaurant", restaurant)
                }
            }
        }))
        recycler.adapter = adapter
    }
}


