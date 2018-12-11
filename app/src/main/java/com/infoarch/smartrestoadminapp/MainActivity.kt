package com.infoarch.smartrestoadminapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.infoarch.smartrestoadminapp.adapters.RestaurantAdapter
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.components.toastMessage
import com.infoarch.smartrestoadminapp.fragments.NotificationsFragment
import com.infoarch.smartrestoadminapp.fragments.RestaurantList
import com.infoarch.smartrestoadminapp.listeners.RecyclerRestaurantListener
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_restaurant_list.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Layout
        setSupportActionBar(toolbar as Toolbar)
        setNavDrawer()


        if (savedInstanceState == null){
            fragmentTransaction(RestaurantList())
            navView.menu.getItem(0).isChecked = true
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Restaurants -> fragmentTransaction(RestaurantList())
            R.id.nav_Notifications -> fragmentTransaction(NotificationsFragment())
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
