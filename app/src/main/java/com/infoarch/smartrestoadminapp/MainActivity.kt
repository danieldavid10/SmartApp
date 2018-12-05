package com.infoarch.smartrestoadminapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.infoarch.smartrestoadminapp.adapters.RestaurantListAdapter
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Layout
    private lateinit var toolbar: Toolbar

    private lateinit var mFunctions: FirebaseFunctions
    private lateinit var items: ArrayList<RestaurantModel>
    private lateinit var adapterRestaurant: RestaurantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        goInfo.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this, InformationActivity::class.java)
            )
        }) // test

        mFunctions = FirebaseFunctions.getInstance()

        getRestaurantList()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // Error Message
                } else {
                    if (task.result != null) {
                        val Restaurants: ArrayList<HashMap<*, *>> = task.getResult()!!
                        items = ArrayList<RestaurantModel>()
                        for (restaurant in Restaurants) {
                            items.add(
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
                        adapterRestaurant = RestaurantListAdapter(applicationContext, items)
                        RestaurantsList.adapter = adapterRestaurant
                    } else {
                        // Error Message
                    }
                }
            })

    }

    private fun getRestaurantList(): Task<ArrayList<HashMap<*, *>>> {
        return mFunctions
            .getHttpsCallable("getUserRestaurants")
            .call()
            .continueWith { task ->
                val list = task.result!!.data
                (list as HashMap<*, *>)["restaurants"] as ArrayList<HashMap<*, *>>
            }
    }

}
