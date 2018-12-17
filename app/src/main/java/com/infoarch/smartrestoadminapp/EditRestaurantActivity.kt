package com.infoarch.smartrestoadminapp

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.infoarch.smartrestoadminapp.components.ToolbarActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_restaurant.*

class EditRestaurantActivity : ToolbarActivity() {

    private lateinit var restaurant: RestaurantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_restaurant)

        restaurant = intent.getParcelableExtra("Restaurant")

        // Toolbar
        title = "Edit Information"
        toolbarToLoad(toolbar as Toolbar)
        enableHomeDisplay(true)
        (toolbar as Toolbar).setNavigationOnClickListener { super.onBackPressed() }

        editText_Name.setText(restaurant.name)
        editText_Address.setText(restaurant.address)
        editText_Phone.setText(restaurant.phoneNumber)
        Picasso.get().load(restaurant.image).fit().into(imageView_edit_image)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_restaurant_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_saveRestaurant -> {
                toastMessage("Saved Information")
                true
            }
            else -> false
        }
    }
}
