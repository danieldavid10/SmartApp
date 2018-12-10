package com.infoarch.smartrestoadminapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.infoarch.smartrestoadminapp.InformationActivity

import com.infoarch.smartrestoadminapp.R
import com.infoarch.smartrestoadminapp.adapters.RestaurantAdapter
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.listeners.RecyclerRestaurantListener
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*


class RestaurantList : Fragment() {

    private val mFunctions: FirebaseFunctions by lazy { FirebaseFunctions.getInstance() }
    private val layoutManager by lazy { LinearLayoutManager(context) }
    private var restaurantList = ArrayList<RestaurantModel>()
    private lateinit var adapter: RestaurantAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_RestaurantList)
        val rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false)
        recycler = rootView.recyclerView_RestaurantsList as RecyclerView

        getRestaurantList()
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    activity?.toastMessage("Failed to load the list of restaurants.")
                } else {
                    if (task.result != null) {
                        val restaurants: ArrayList<HashMap<*, *>> = task.result!!
                        for (restaurant in restaurants) {
                            restaurantList.add(
                                RestaurantModel(
                                    restaurant["key"].toString(),
                                    restaurant["address"].toString(),
                                    restaurant["bgcolor"].toString(),
                                    restaurant["color"].toString().toInt(),
                                    restaurant["image"].toString(),
                                    restaurant["name"].toString(),
                                    restaurant["phonenumber"].toString(),
                                    restaurant["isSelected"].toString().toBoolean()
                                )
                            )
                        }
                        setRecyclerView()
                    } else {
                        activity?.toastMessage("Empty restaurants list.")
                    }
                }
            }
        return rootView
    }

    private fun getRestaurantList(): Task<ArrayList<HashMap<*, *>>> {
        return mFunctions
            .getHttpsCallable("getUserAdminRestaurants")
            .call()
            .continueWith { task ->
                val list = task.result?.data
                (list as HashMap<*, *>)["restaurants"] as ArrayList<HashMap<*, *>>
            }
    }

    private fun setRecyclerView() {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = layoutManager
        adapter = (RestaurantAdapter(restaurantList, object : RecyclerRestaurantListener {

            override fun onClick(restaurant: RestaurantModel, position: Int) {
                activity?.toastMessage(restaurant.name)
            }

            override fun onInformationClick(restaurant: RestaurantModel, position: Int) {
                activity?.goToActivity<InformationActivity>()
            }
        }))
        recycler.adapter = adapter
    }
}
