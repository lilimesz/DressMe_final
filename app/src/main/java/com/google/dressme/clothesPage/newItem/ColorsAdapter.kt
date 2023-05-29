package com.google.dressme.clothesPage.newItem

import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.dressme.R

class ColorsAdapter(private val colorsList:ArrayList<Colors>) :
RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    var onItemClick: ((Colors) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ShapeableImageView

        init {
            imageView = itemView.findViewById(R.id.color_pic_prev)
            itemView.setOnClickListener { onItemClick?.invoke(colorsList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_grid_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colorsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentColor = colorsList[position]
        holder.imageView.setColorFilter(currentColor.color, PorterDuff.Mode.MULTIPLY)

    }
}