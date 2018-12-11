package com.infoarch.smartrestoadminapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import com.infoarch.smartrestoadminapp.components.ToolbarActivity
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : ToolbarActivity() {

    private lateinit var restaurant: RestaurantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        toolbarToLoad(toolbar as Toolbar)
        enableHomeDisplay(true)

        getIntentExtras()

        message.text = restaurant.name

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_Information -> {
//                message.setText(R.string.title_home)
                message.text = restaurant.name
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Reservations -> {
                message.text = "Reservations Layout"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Promotions -> {
                message.text = "Promotions Layout"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun getIntentExtras() {
        restaurant = RestaurantModel(
            intent.getStringExtra("Key"),
            intent.getStringExtra("Address"),
            intent.getStringExtra("BgColor"),
            intent.getIntExtra("Color", 0),
            intent.getStringExtra("Image"),
            intent.getStringExtra("Name"),
            intent.getStringExtra("PhoneNumber"),
            intent.getBooleanExtra("isSelected", false)
        )
    }
}
