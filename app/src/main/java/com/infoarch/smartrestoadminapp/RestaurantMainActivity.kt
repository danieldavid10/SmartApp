package com.infoarch.smartrestoadminapp

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
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

        // Toolbar
        toolbarToLoad(toolbar as Toolbar)
        enableHomeDisplay(true)
        (toolbar as Toolbar).setNavigationOnClickListener { super.onBackPressed() }

        restaurant = intent.getParcelableExtra("Restaurant")

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
            goToActivity<EditRestaurantActivity> {
                this.putExtra("Restaurant", restaurant)
            }
        }
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
                    return RestaurantInfo_Fragment.setInformation(restaurant)
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
