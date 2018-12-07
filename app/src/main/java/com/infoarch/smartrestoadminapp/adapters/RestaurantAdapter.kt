package com.infoarch.smartrestoadminapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.infoarch.smartrestoadminapp.R
import com.infoarch.smartrestoadminapp.components.inflate
import com.infoarch.smartrestoadminapp.listeners.RecyclerRestaurantListener
import com.infoarch.smartrestoadminapp.struct.RestaurantModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item.view.*

class RestaurantAdapter(
    private val restaurant: List<RestaurantModel>,
    private val listener: RecyclerRestaurantListener
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RestaurantVH {
        return RestaurantVH(parent.inflate(R.layout.restaurant_item))
    }

    override fun onBindViewHolder(holder: RestaurantVH, position: Int) {
        return holder.bind(restaurant[position], listener)
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    class RestaurantVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: RestaurantModel, listener: RecyclerRestaurantListener) = with(itemView) {
            // Fields of restaurant_item
            text_Name.text = restaurant.name
            text_Address.text = restaurant.address
            Picasso.get().load(restaurant.image).fit().into(imageView_Image)
            // Events
            setOnClickListener { listener.onClick(restaurant, adapterPosition) }
            button_Information.setOnClickListener { listener.onInformationClick(restaurant, adapterPosition) }
        }
    }
}





