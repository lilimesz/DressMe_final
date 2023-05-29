package com.google.dressme.clothesPage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.InspectableProperty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.dressme.R
import com.google.dressme.clothesPage.newItem.NewItemPage


class ClothesGridLayout(
) : Fragment() {


    private lateinit var newRecyclerView: RecyclerView
    private lateinit var clothesAdapter: ClothesAdapter
    private lateinit var newArrayList: ArrayList<Clothes3>
    var clothesList: MutableList<Clothing> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getUserdata() {
        clothesList = NewItemPage.clothesList
        if (clothesList.isNotEmpty()) {


            //A tesztelések miatt szükség van néhány állandó elemre
            //Ebben a fázisban még nem létfontásságú eltárolni a tartalmakat
            addTestItems()

            for (i in clothesList.indices) {
                newArrayList.add(
                    Clothes3(
                        clothesList[i].getLabel(),
                        clothesList[i].getType(),
                        clothesList[i].getColor(),
                        clothesList[i].getImage()
                    )
                )
            }

            clothesAdapter = ClothesAdapter(newArrayList)
            newRecyclerView.adapter = clothesAdapter

            /*for (i in imageId.indices) {
                val clothes = Clothes3(imageId[i])
                newArrayList.add(clothes)
                //newArrayList.add(Clothes3(drawable))
            }

        */
        } else {
            Toast.makeText(context, "No items here!", Toast.LENGTH_LONG).show()
            clothesAdapter = ClothesAdapter(newArrayList)
            newRecyclerView.adapter = clothesAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_clothes2, container, false)

        //clothesList.add(ClothesUtils.Clothing(myLabel,myType,myColor,myImage))


        newRecyclerView = view.findViewById<View>(R.id.rcv) as RecyclerView
        //newRecyclerView.setHasFixedSize(true)
        newRecyclerView.layoutManager = GridLayoutManager(context, 3)
        newArrayList = arrayListOf()
        getUserdata()

        return view
    }


    private fun addTestItems() {
        clothesList.add(Clothing("Top", "T-Shirt", -5383962, R.drawable.tshirt_template.toInt()))
        clothesList.add(Clothing("Top", "Top", Color.WHITE, R.drawable.top_template.toInt()))
        clothesList.add(Clothing("Top", "T-Shirt", -1146130, R.drawable.tshirt_template.toInt()))
        clothesList.add(
            Clothing(
                "Top", "Undershirt", -2354116, R.drawable.undershirt_template.toInt()
            )
        )
        clothesList.add(Clothing("Top", "Body", -5383962, R.drawable.bodysuit_template.toInt()))
        clothesList.add(Clothing("Top", "T-Shirt", Color.WHITE, R.drawable.tshirt_template.toInt()))
        clothesList.add(
            Clothing(
                "Top", "Polo", -16751616, R.drawable.polo_shirt_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Top", "Polo", Color.WHITE, R.drawable.polo_shirt_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Jacket", "Jacket", Color.DKGRAY, R.drawable.jacket_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Jacket", "Jacket", -5383962, R.drawable.jacket_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Blazer", "Blazer", -463124, R.drawable.blazer_template.toInt()
            )
        )
        clothesList.add(Clothing("Hoodie", "-", Color.LTGRAY, R.drawable.hoodie_template.toInt()))
        clothesList.add(Clothing("Hoodie", "-", -1146130, R.drawable.hoodie_template.toInt()))
        clothesList.add(
            Clothing(
                "Longsleeve", "Shirt", -5383962, R.drawable.shirt_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Longsleeve", "Shirt", Color.LTGRAY, R.drawable.shirt_template.toInt()
            )
        )
        clothesList.add(
            Clothing(
                "Longsleeve", "Blouse", -16181, R.drawable.blouse_template.toInt()
            )
        )
        clothesList.add(Clothing("Pants", "Pants", -16751616, R.drawable.pants_template.toInt()))
        clothesList.add(Clothing("Pants", "Jeans", -5383962, R.drawable.jeans_template.toInt()))
        clothesList.add(Clothing("Pants", "Shorts", -1146130, R.drawable.shorts_template.toInt()))
        clothesList.add(Clothing("Skirt", "-", -2180985, R.drawable.skirt_template.toInt()))
        clothesList.add(Clothing("Dress", "-", -2354116, R.drawable.dress_template.toInt()))
        clothesList.add(Clothing("Shoes", "Shoes", Color.WHITE, R.drawable.shoes_template.toInt()))
        clothesList.add(Clothing("Shoes", "Boots", -64, R.drawable.boots_template.toInt()))
        clothesList.add(Clothing("Shoes", "Shoes", Color.LTGRAY, R.drawable.shoes_template.toInt()))
        clothesList.add(Clothing("Shoes", "Heels", Color.RED, R.drawable.heels_template.toInt()))
        clothesList.add(Clothing("Outwear", "-", Color.WHITE, R.drawable.outerwear_template))


    }
}