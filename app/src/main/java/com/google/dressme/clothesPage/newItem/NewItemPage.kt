package com.google.dressme.clothesPage.newItem

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import com.google.dressme.MainActivity
import com.google.dressme.R
import java.util.*


class NewItemPage(
    private var label: String, private var color: Color
) : Fragment() {
    private lateinit var mActivity: MainActivity
    private val labelTVArray = kotlin.collections.ArrayList<TextView?>(11)
    private var typeTVArray =ArrayList<TextView?>(emptyList())
    private lateinit var labelsArray: Array<String>
    private lateinit var typeArray:Array<String>
    private lateinit var myLayout: LinearLayout
    private lateinit var typeTV: TextView
    private lateinit var bgBLur: FrameLayout
    private val lp = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
    private var mainscreen:Boolean = true
    private var selectedType: Int? = null
    private lateinit var subcategories: Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        labelsArray = arrayOf(
            "T-Shirt",
            "Jacket",
            "Hoodie",
            "Longsleeve",
            "Pants",
            "Skirt",
            "Dress",
            "Shoes",
            "Hat",
            "Outwear",
            "Other"
        )
        subcategories = arrayOf(
            resources.getStringArray(R.array.Top),
            resources.getStringArray(R.array.Jacket),
            resources.getStringArray(R.array.Hoodie),
            resources.getStringArray(R.array.Longsleeve),
            resources.getStringArray(R.array.Pants),
            resources.getStringArray(R.array.Skirt),
            resources.getStringArray(R.array.Dress),
            resources.getStringArray(R.array.Shoes),
            resources.getStringArray(R.array.Hat),
            resources.getStringArray(R.array.Outwear),
            resources.getStringArray(R.array.Other)
            )


        mActivity = (activity as MainActivity)
        if (mainscreen){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mActivity.replaceFragment(CameraView())
        }}


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_new_item_page, container, false)
        bgBLur = view.findViewById(R.id.backgroundBlur)
        myLayout = view?.findViewById(R.id.chooseLabel)!!

        //Image of the dress
        val newPiece = view.findViewById<ImageView>(R.id.dressView)

        //Category
        val labelTV= view.findViewById<TextView>(R.id.label)
        typeTV = view.findViewById(R.id.dressType)

        //Kategória beállítása az imageclassifiernek megfelelően
        labelTV.text = label

        //OnClickListener a label TextView-ra manuális felülíráshoz
        selectItem(labelTVArray,labelTV,labelsArray,false)

        //Type
        //Ha még nincs felülírt típus, akkor...
        if (selectedType == null){
            typeArray=subcategories[getID(label)] //..a labelnek megfelelő alkategória kiválasztás
            typeTVArray.ensureCapacity(typeArray.size)
            prepareLinearLO(typeTVArray,typeTV,typeArray,false)
            typeTV.text = typeArray[0] //..nulladik elem beállítása alapértelmezetten
            Log.e("BEFORE",typeTVArray.size.toString())

        }


        selectItem(typeTVArray,typeTV,typeArray,true) //OnClickListener

        //Color
        val colorText = view.findViewById<TextView>(R.id.colorName)
        val dressColor = color.toArgb()
        val colorName =
            ColorUtils().getColorNameFromRgb(dressColor.red, dressColor.green, dressColor.blue)
        newPiece.setColorFilter(dressColor, PorterDuff.Mode.MULTIPLY)
        colorText.text = colorName



        return view

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepareLinearLO(pairs: ArrayList<TextView?>, myLabel:TextView,textArray: Array<String>,isSubcategory: Boolean) {
        myLayout.removeAllViews()
       // pairs.ensureCapacity(textArray.size)
        for (l in textArray.indices) {


            pairs.add(l,TextView(context))
            pairs[l]!!.apply {
                id = l
                layoutParams = lp
                elevation = 20f
                text = textArray[l]
                textSize = 20f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setTextColor(Color.BLACK)
                setPadding(2, 2, 2, 20)
                setBackgroundColor(Color.WHITE)

                setOnClickListener {
                    selectedType = id
                    myLayout.visibility = INVISIBLE
                    bgBLur.visibility = INVISIBLE
                    myLabel.text = this.text //ez működik
                    if (!isSubcategory) {
                        typeArray=subcategories[getID(myLabel.text.toString())]
                        pairs.clear()
                        typeTV.text=typeArray[0]
                        selectItem(typeTVArray,typeTV,typeArray,true)
                    }
                }
            }
            myLayout.addView(pairs[l])
            Log.e("ASDASDA",pairs[l].toString())

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectItem(
        TVArray: ArrayList<TextView?>,
        TV:TextView,
        SArray:Array<String>,
        isSubcategory:Boolean) {
        TV.setOnClickListener {
            prepareLinearLO(TVArray,TV,SArray,isSubcategory)
            mainscreen = false
            myLayout.visibility= VISIBLE
            bgBLur.visibility= VISIBLE
        }

    }

    private fun getID(label:String) : Int {
        var itemId=-1
        for (i in labelsArray.indices) {
            if (labelsArray[i] == label) {
                itemId=i
                break
            }
        }
        return itemId
    }



}
