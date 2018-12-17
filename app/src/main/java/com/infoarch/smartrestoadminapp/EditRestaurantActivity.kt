package com.infoarch.smartrestoadminapp

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.infoarch.smartrestoadminapp.components.ToolbarActivity
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_restaurant.*

class EditRestaurantActivity : ToolbarActivity() {

    private lateinit var restaurant: RestaurantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_restaurant)

        title = "Edit Information"
        toolbarToLoad( toolbar as Toolbar)
        enableHomeDisplay(true)
        (toolbar as Toolbar).setNavigationOnClickListener {
            super.onBackPressed()
        }

        getIntentExtras()
        Picasso.get().load(restaurant.image).fit().into(imageView_edit_image)
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
