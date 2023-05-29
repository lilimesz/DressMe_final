package com.google.dressme.clothesPage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.MainActivity
import com.google.dressme.R
import com.google.dressme.clothesPage.newItem.CameraView
import com.google.dressme.clothesPage.newItem.NewItemPage
import com.google.dressme.clothesPage.newItem.NewItemPage.Companion.clothesList


class ClothesMainPage : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Categories>
    private lateinit var iconId: Array<Int>
    private lateinit var descId: Array<String>
    private lateinit var mActivity: MainActivity
    private lateinit var clothesAdapter: ClothesMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        iconId = arrayOf(
            R.drawable.tshirt,
            R.drawable.long_sleeve,
            R.drawable.hood,
            R.drawable.long_sleeve,
            R.drawable.jeans,
            R.drawable.skirt,
            R.drawable.dress,
            R.drawable.sport_shoe,
            R.drawable.cap,
            R.drawable.overcoat,
            R.drawable.singlet

        )

        descId = arrayOf(
            "Tops",
            "Jackets & Blazers",
            "Hoodies",
            "Long-sleeves",
            "Pants",
            "Skirts",
            "Dresses",
            "Shoes",
            "Hats",
            "Outwears",
            "Other"
        )

    }

    private fun getUserdata() {

        for (i in iconId.indices) {
            val category = Categories(iconId[i], descId[i])
            newArrayList.add(category)
        }

        clothesAdapter = ClothesMainAdapter(newArrayList)
        newRecyclerView.adapter = clothesAdapter
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

        //clothesList = clothesList
        if (clothesList.isEmpty()) {
            addTestItems()
        }


        clothesAdapter.onItemClick = {
            val helper = "${it.desc[0]}${it.desc[1]}"
            mActivity.replaceFragment(ClothesGridLayout(helper))
        }

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
            manualBtn.setOnClickListener {
                mActivity.replaceFragment(
                    NewItemPage("T-Shirt", Color.WHITE.toColor())
                )
            }


        }


    }

    private fun addTestItems() {
        clothesList.add(Clothing("Top", "T-Shirt", -5383962, R.drawable.tshirt_template))
        clothesList.add(Clothing("Top", "Top", Color.WHITE, R.drawable.top_template))
        clothesList.add(Clothing("Top", "T-Shirt", -1146130, R.drawable.tshirt_template))
        clothesList.add(
            Clothing(
                "Top", "Undershirt", -2354116, R.drawable.undershirt_template
            )
        )
        clothesList.add(Clothing("Top", "Body", -5383962, R.drawable.bodysuit_template))
        clothesList.add(Clothing("Top", "T-Shirt", Color.WHITE, R.drawable.tshirt_template))
        clothesList.add(
            Clothing(
                "Top", "Polo", -16751616, R.drawable.polo_shirt_template
            )
        )
        clothesList.add(
            Clothing(
                "Top", "Polo", Color.WHITE, R.drawable.polo_shirt_template
            )
        )
        clothesList.add(
            Clothing(
                "Jacket", "Jacket", Color.DKGRAY, R.drawable.jacket_template
            )
        )
        clothesList.add(
            Clothing(
                "Jacket", "Jacket", -5383962, R.drawable.jacket_template
            )
        )
        clothesList.add(
            Clothing(
                "Blazer", "Blazer", -463124, R.drawable.blazer_template
            )
        )
        clothesList.add(Clothing("Hoodie", "-", Color.LTGRAY, R.drawable.hoodie_template))
        clothesList.add(Clothing("Hoodie", "-", -1146130, R.drawable.hoodie_template))
        clothesList.add(
            Clothing(
                "Longsleeve", "Shirt", -5383962, R.drawable.shirt_template
            )
        )
        clothesList.add(
            Clothing(
                "Longsleeve", "Shirt", Color.LTGRAY, R.drawable.shirt_template
            )
        )
        clothesList.add(
            Clothing(
                "Longsleeve", "Blouse", -16181, R.drawable.blouse_template
            )
        )
        clothesList.add(Clothing("Pants", "Pants", -16751616, R.drawable.pants_template))
        clothesList.add(Clothing("Pants", "Jeans", -5383962, R.drawable.jeans_template))
        clothesList.add(Clothing("Pants", "Shorts", -1146130, R.drawable.shorts_template))
        clothesList.add(Clothing("Skirt", "-", -2180985, R.drawable.skirt_template))
        clothesList.add(Clothing("Dress", "-", -2354116, R.drawable.dress_template))
        clothesList.add(Clothing("Shoes", "Shoes", Color.WHITE, R.drawable.shoes_template))
        clothesList.add(Clothing("Shoes", "Boots", -64, R.drawable.boots_template))
        clothesList.add(Clothing("Shoes", "Shoes", Color.LTGRAY, R.drawable.shoes_template))
        clothesList.add(Clothing("Shoes", "Heels", Color.RED, R.drawable.heels_template))
        clothesList.add(Clothing("Outwear", "-", Color.WHITE, R.drawable.outerwear_template))


    }

}

