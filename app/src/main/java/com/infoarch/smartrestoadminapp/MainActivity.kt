package com.infoarch.smartrestoadminapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.fragments.NotificationsFragment
import com.infoarch.smartrestoadminapp.fragments.RestaurantList
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mFunctions: FirebaseFunctions by lazy { FirebaseFunctions.getInstance() }
    private var restaurantList = ArrayList<RestaurantModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Layout
        setSupportActionBar(toolbar as Toolbar)
        setNavDrawer()

        // Get Restaurant List
        if (savedInstanceState == null) {
            getRestaurantList()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        toastMessage("Failed to load the list of restaurants.")
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
                            fragmentTransaction(RestaurantList.setRestaurantList(restaurantList))
                            navView.menu.getItem(0).isChecked = true
                        } else {
                            toastMessage("Empty restaurants list.")
                        }
                    }
                }
        }

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Restaurants -> fragmentTransaction(RestaurantList.setRestaurantList(restaurantList))
            R.id.nav_Notifications -> fragmentTransaction(NotificationsFragment())
            R.id.nav_Logout -> {
                mAuth.signOut()
                goToActivity<LoginActivity>()
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setNavDrawer() {
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar as Toolbar, R.string.open_drawer, R.string.close_drawer)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_Container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
