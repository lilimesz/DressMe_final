package com.google.dressme.clothesPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.dressme.R

class ClothesAdapter(private val clothesList:ArrayList<Clothes3>) :
    RecyclerView.Adapter<ClothesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ShapeableImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(
            R.layout.grid_item,
            parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position < clothesList.size) {
        val currentItem = clothesList[position]
        holder.imageView.setImageResource(currentItem.image)}
        else {
            holder.imageView.setImageResource(R.drawable.baseline_add_24)
        }
        }



   override fun getItemCount(): Int {
        return clothesList.size+1
    }



}