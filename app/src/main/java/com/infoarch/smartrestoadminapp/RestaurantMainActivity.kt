package com.infoarch.smartrestoadminapp

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.infoarch.smartrestoadminapp.components.ToolbarActivity
import com.infoarch.smartrestoadminapp.components.goToActivity
import com.infoarch.smartrestoadminapp.fragments.RestaurantInfo_Fragment
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_restaurant_main.*
import kotlinx.android.synthetic.main.fragment_restaurant_main.view.*

class RestaurantMainActivity : ToolbarActivity() {

    private lateinit var restaurant: RestaurantModel
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_main)

        toolbarToLoad(toolbar as Toolbar)
        enableHomeDisplay(true)
        (toolbar as Toolbar).setNavigationOnClickListener {
            super.onBackPressed()
        }

        getIntentExtras()

        title = restaurant.name
        Picasso.get().load(restaurant.image).fit().into(imageView_RestaurantLogo)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        floatingButton.setOnClickListener {
            goToActivity<EditRestaurantActivity>{
                this.putExtra("Key",restaurant.key)
                this.putExtra("Address",restaurant.address)
                this.putExtra("BgColor",restaurant.bgColor)
                this.putExtra("Color",restaurant.color)
                this.putExtra("Image",restaurant.image)
                this.putExtra("Name",restaurant.name)
                this.putExtra("PhoneNumber",restaurant.phoneNumber)
                this.putExtra("isSelected",restaurant.isSelected)
            }
        }


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_restaurant_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            when (position) {
                0 -> {
                    return RestaurantInfo_Fragment.setInformation(
                        restaurant.name,
                        restaurant.address,
                        restaurant.phoneNumber
                    )
                }
                1 -> {
                    return PlaceholderFragment.newInstance(position + 1)
                }
                2 -> {
                    return PlaceholderFragment.newInstance(position + 1)
                }
            }

            return PlaceholderFragment.newInstance(position + 1) //change
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_restaurant_main, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
