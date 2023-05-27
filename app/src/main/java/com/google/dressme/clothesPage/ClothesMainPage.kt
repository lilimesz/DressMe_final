package com.google.dressme.clothesPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.Categories
import com.google.dressme.MainActivity
import com.google.dressme.R
import com.google.dressme.clothesPage.newItem.CameraView


class ClothesMainPage : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Categories>
    private lateinit var iconId: Array<Int>
    private lateinit var descId: Array<String>
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        iconId = arrayOf(
            R.drawable.tshirt,
            R.drawable.hood,
            R.drawable.overcoat,
            R.drawable.jeans,
            R.drawable.dress,
            R.drawable.sport_shoe,
            R.drawable.long_sleeve,
            R.drawable.boot,
            R.drawable.cap
        )

        descId = arrayOf(
            "T-Shirts",
            "Hoodies",
            "Overcoats",
            "Jeans",
            "Dresses",
            "Shoes",
            "Long sleeves",
            "Boots",
            "Hats"
        )

    }

    private fun getUserdata() {

        for (i in iconId.indices) {
            val category = Categories(iconId[i], descId[i])
            newArrayList.add(category)
        }
        newRecyclerView.adapter = ClothesMainAdapter(newArrayList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mActivity = (activity as MainActivity)
        val view = inflater.inflate(R.layout.clothes_main_page, container, false)
        newRecyclerView = view.findViewById<View>(R.id.rcv2) as RecyclerView
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newArrayList = arrayListOf()
        getUserdata()
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNewBtn = view.findViewById<ImageButton>(R.id.imageButton)
        val galleryBtn = view.findViewById<Button>(R.id.gallery_button)
        val cameraBtn = view.findViewById<Button>(R.id.camera_button)
        val manualBtn = view.findViewById<Button>(R.id.manual_button)
        addNewBtn.setOnClickListener {
            view.findViewById<FrameLayout>(R.id.choose_one).visibility = VISIBLE
            view.findViewById<LinearLayout>(R.id.choose_one_linear).visibility = VISIBLE

            galleryBtn.setOnClickListener {}
            cameraBtn.setOnClickListener { mActivity.replaceFragment(CameraView()) }
            manualBtn.setOnClickListener {/*
                mActivity.replaceFragment(
                    NewItemPage(
                        "T-Shirt",
                        Color.WHITE.toColor(),
                        TakenPic(Bitmap.createBitmap(R.drawable.tshirt_template)).labelList
                    )*/
            }


        }


    }

}

