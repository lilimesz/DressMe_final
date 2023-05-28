package com.google.dressme.clothesPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.Categories
import com.google.dressme.R

class ClothesMainAdapter (private val categorySet: ArrayList<Categories>) :
        RecyclerView.Adapter<ClothesMainAdapter.ViewHolder>() {


            //var onItemClick: ((Categories) -> Unit)? = null

            class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

                var textView : TextView
                var imageView: ImageView


                init {
                    textView = view.findViewById(R.id.base_text_view)
                    imageView = view.findViewById(R.id.base_image)

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clothes_main_grid_item, parent,false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categorySet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = categorySet[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.textView.text = currentItem.desc

//        holder.itemView.setOnClickListener {
//            when(holder.textView.text) {
//                "T-Shirts",
//                "Hoodies",
//                "Overcoats",
//                "Jeans",
//                "Dresses",
//                "Shoes",
//                "Long sleeves",
//                "Boots",
//                "Hats"
//            }
//        }


    }
//    private fun replaceFragment(fragment: Fragment) {
//
//        val fragmentManager=supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_layout,fragment)
//        fragmentTransaction.commit()
//    }


}