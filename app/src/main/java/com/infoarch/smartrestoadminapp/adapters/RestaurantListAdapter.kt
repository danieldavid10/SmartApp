package com.infoarch.smartrestoadminapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.infoarch.smartrestoadminapp.R
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_restaurant_item.view.*

class RestaurantListAdapter(val context: Context, val data: ArrayList<RestaurantModel>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder: RestaurantViewHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.adapter_restaurant_item,parent,false)
            viewHolder = RestaurantViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as RestaurantViewHolder
        }

        viewHolder.name.text = data[position].name
        viewHolder.address.text = data[position].address
        viewHolder.phoneNumber.text = data[position].phoneNumber
        Picasso.get().load(data[position].image).fit().into(viewHolder.image)

        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}

private class RestaurantViewHolder(view: View) {
    val name: TextView = view.restName
    val address: TextView = view.restAddress
    val phoneNumber: TextView = view.restPhoneNumber
    val image: ImageView = view.restImage
}
