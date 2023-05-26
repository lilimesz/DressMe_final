package com.google.dressme.clothesPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.R


class ClothesGridLayout : Fragment() {


    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Clothes3>
    private lateinit var imageId:Array<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        imageId = arrayOf(
            R.drawable.dress1
        )
    }

    private fun getUserdata() {

        for (i in imageId.indices) {
            val clothes = Clothes3(imageId[i])
            newArrayList.add(clothes)
            //newArrayList.add(Clothes3(drawable))
        }
        newRecyclerView.adapter= ClothesAdapter(newArrayList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_clothes2, container, false)
        newRecyclerView = view.findViewById<View>(R.id.rcv) as RecyclerView
        newRecyclerView.layoutManager=GridLayoutManager(context,3)
        newArrayList= arrayListOf()
        getUserdata()
        return view
    }

}