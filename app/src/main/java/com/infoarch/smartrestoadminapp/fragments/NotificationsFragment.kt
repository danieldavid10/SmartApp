package com.infoarch.smartrestoadminapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.infoarch.smartrestoadminapp.R

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle(R.string.title_Notifications)
        val fragmentView =inflater.inflate(R.layout.fragment_notifications, container, false)

        return fragmentView
    }
}
