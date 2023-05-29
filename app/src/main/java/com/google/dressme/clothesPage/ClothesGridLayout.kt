package com.google.dressme.clothesPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.R
import com.google.dressme.clothesPage.newItem.NewItemPage


class ClothesGridLayout(
    private var helper:String
) : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var clothesAdapter: ClothesAdapter
    private lateinit var newArrayList: ArrayList<Clothes>
    private var clothesList: MutableList<Clothing> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        clothesList = NewItemPage.clothesList

        val view = inflater.inflate(R.layout.fragment_clothes2, container, false)

        newRecyclerView = view.findViewById<View>(R.id.rcv) as RecyclerView
        //newRecyclerView.setHasFixedSize(true)
        newRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newArrayList = arrayListOf()
        getUserdata()

        return view
    }

    private fun getUserdata() {

        if (clothesList.isNotEmpty()) {

            for (i in clothesList.indices) {
                if ("${clothesList[i].label[0]}${clothesList[i].label[1]}"==helper){
                    newArrayList.add(
                        Clothes(
                            clothesList[i].getLabel(),
                            clothesList[i].getType(),
                            clothesList[i].getColor(),
                            clothesList[i].getImage()
                        )
                    )
                }}

            clothesAdapter = ClothesAdapter(newArrayList)
            newRecyclerView.adapter = clothesAdapter

        } else {
            Toast.makeText(context, "No items here!", Toast.LENGTH_LONG).show()
            clothesAdapter = ClothesAdapter(newArrayList)
            newRecyclerView.adapter = clothesAdapter
        }
    }

}