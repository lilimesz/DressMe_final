package com.google.dressme.clothesPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.R
import javax.inject.Inject

class ClothesMainAdapter @Inject constructor(private val categorySet: ArrayList<Categories>) :
    RecyclerView.Adapter<ClothesMainAdapter.ViewHolder>() {


    var onItemClick: ((Categories) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = categorySet[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.textView.text = currentItem.desc
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView
        var imageView: ImageView


        init {
            textView = view.findViewById(R.id.base_text_view)
            imageView = view.findViewById(R.id.base_image)

            itemView.setOnClickListener {
                onItemClick?.invoke(categorySet[adapterPosition]) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.clothes_main_grid_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categorySet.size
    }

}