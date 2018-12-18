package com.infoarch.smartrestoadminapp

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.infoarch.smartrestoadminapp.components.ToolbarActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_restaurant.*

class EditRestaurantActivity : ToolbarActivity() {

    private lateinit var mFunctions: FirebaseFunctions
    private lateinit var restaurant: RestaurantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_restaurant)

        mFunctions = FirebaseFunctions.getInstance()

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
                restaurant.name = editText_Name.text.toString()
//                restaurant.bgColor = "#fff"
//                restaurant.color = 4
//                restaurant.image = "https....."
                restaurant.address = editText_Address.text.toString()
                restaurant.phoneNumber = editText_Phone.text.toString()

                updateRestaurant(restaurant)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            toastMessage("Failed to save restaurant information")
                        } else {
                            if (task.result != null) {
                                toastMessage("Successful!!")
                            } else {
                                toastMessage("Error!!.")
                            }
                        }
                    }
                true
            }
            else -> false
        }
    }

    private fun updateRestaurant(restaurant: RestaurantModel): Task<Boolean> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "id" to restaurant.key,
            "name" to restaurant.name,
            "bgcolor" to restaurant.bgColor,
            "color" to restaurant.color.toString(),
            "image" to restaurant.image,
            "address" to restaurant.address,
            "phonenumber" to restaurant.phoneNumber
        )

        return mFunctions
            .getHttpsCallable("setRestaurant")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as Boolean
                result
            }
    }
}
