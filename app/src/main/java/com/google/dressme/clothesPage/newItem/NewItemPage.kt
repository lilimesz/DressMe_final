package com.google.dressme.clothesPage.newItem

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
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
import java.util.Arrays.copyOfRange


class NewItemPage(
    private var label: String, private var color: Color
) : Fragment() {
    private lateinit var mActivity: MainActivity
    private lateinit var bgBLur: FrameLayout

    private lateinit var categories: Array<String>
    private lateinit var subcategories: Array<Array<String>>

    private lateinit var newPiece: ImageView
    private val labelTVArray = kotlin.collections.ArrayList<TextView?>(11)
    private var typeTVArray = ArrayList<TextView?>(emptyList())

    private lateinit var typeArray: Array<String>
    private lateinit var myLayout: LinearLayout
    private lateinit var typeTV: TextView
    private lateinit var labelTV: TextView
    private lateinit var sampleId: Array<Int>

    private val lp = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
    private var mainscreen: Boolean = true
    private var selectedType: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val Top = resources.getStringArray(R.array.Top)
        val Jacket = resources.getStringArray(R.array.Blazer_Jacket)
        val Hoodie = resources.getStringArray(R.array.Hoodie)
        val Longsleeve = resources.getStringArray(R.array.Longsleeve)
        val Pants = resources.getStringArray(R.array.Pants)
        val Skirt = resources.getStringArray(R.array.Skirt)
        val Dress = resources.getStringArray(R.array.Dress)
        val Shoes = resources.getStringArray(R.array.Shoes)
        val Hat = resources.getStringArray(R.array.Hat)
        val Outwear = resources.getStringArray(R.array.Outwear)
        val Other = resources.getStringArray(R.array.Other)

        categories = arrayOf(
            "Top",
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
            Top, Jacket, Hoodie, Longsleeve, Pants, Skirt, Dress, Shoes, Hat, Outwear, Other
        )

        templateArray()


        mActivity = (activity as MainActivity)
        if (mainscreen) {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                mActivity.replaceFragment(CameraView())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_new_item_page, container, false)
        bgBLur = view.findViewById(R.id.backgroundBlur)
        myLayout = view.findViewById(R.id.chooseLabel)


        //Image of the dress
        newPiece = view.findViewById(R.id.dressView)

        //Category
        labelTV = view.findViewById(R.id.label)
        typeTV = view.findViewById(R.id.dressType)


        //OnClickListener a label TextView-ra manuális felülíráshoz
        selectItem(labelTVArray, labelTV, categories, false)

        //Type
        //Ha még nincs felülírt típus, akkor...
        if (selectedType == null) {
            defaultPicSelect(getLabelID(label),getTypeID(label))
            typeTVArray.ensureCapacity(typeArray.size)
            prepareLinearLO(typeTVArray, typeTV, typeArray, false)

        }

        if (typeTV.text != "-") {
            selectItem(typeTVArray, typeTV, typeArray, true) //OnClickListener
        }

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
    private fun prepareLinearLO(
        pairs: ArrayList<TextView?>,
        myLabel: TextView,
        textArray: Array<String>,
        isSubcategory: Boolean
    ) {
        var labelID = getLabelID(myLabel.text.toString())
        myLayout.removeAllViews()
        for (l in textArray.indices) {
            pairs.add(l, TextView(context))
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
                    selectedType = id+1
                    myLayout.visibility = INVISIBLE
                    bgBLur.visibility = INVISIBLE
                    myLabel.text = this.text
                    if (!isSubcategory) {
                        labelID = id
                        pairs.clear()
                        defaultPicSelect(labelID,0)
                        typeTV.text = typeArray[0]
                        if (typeArray[0] != "-"){
                        selectItem(typeTVArray, typeTV, typeArray, true)}
                    } else {
                        defaultPicSelect(labelID, selectedType!!)
                    }
                }
            }
            myLayout.addView(pairs[l])

        }


    }

    private fun templateArray() {
        sampleId = arrayOf(
            R.drawable.top_template,
            R.drawable.tshirt_template,
            R.drawable.polo_shirt_template,
            R.drawable.bodysuit_template,
            R.drawable.undershirt_template,
            R.drawable.jacket_template,
            R.drawable.blazer_template,
            R.drawable.hoodie_template,
            R.drawable.longsleeve_template,
            R.drawable.shirt_template,
            R.drawable.blouse_template,
            R.drawable.pants_template,
            R.drawable.jeans_template,
            R.drawable.shorts_template,
            R.drawable.skirt_template,
            R.drawable.dress_template,
            R.drawable.shoes_template,
            R.drawable.heels_template,
            R.drawable.boots_template,
            R.drawable.hat_template,
            R.drawable.outerwear_template
        )
    }

    private fun defaultPicSelect(labelID: Int,typeID:Int) {
        Log.e("LABELID",labelID.toString())
        Log.e("TYPEID",typeID.toString())

        if (selectedType!=null){
            label=subcategories[labelID][typeID]
        }

        var found = false
        if (label == "Blazer_Jacket") {
            labelTV.text = "Jacket"
            typeArray = subcategories[1]
            typeTV.text = "choose one"
            typeTV.setTypeface(typeTV.typeface, Typeface.ITALIC)
            newPiece.setImageResource(R.drawable.jacket_template)
        } else {
            do {
                for (i in subcategories.indices) {
                    if (subcategories[i].size == 2) {
                        if (subcategories[i][0] == label) {
                            typeArray = subcategories[i].copyOfRange(1, (subcategories[i].size))
                            newPiece.setImageResource(sampleId[getIdForPic(labelID,0)])
                            labelTV.text = subcategories[i][0]
                            typeTV.text = typeArray[0]
                            found = true
                            break
                        }
                    } else {
                        for (j in 1 until subcategories[i].size) {
                            if (subcategories[i][j] == label) {
                                typeArray = subcategories[i].copyOfRange(1, (subcategories[i].size))
                                newPiece.setImageResource(sampleId[getIdForPic(i,j)])
                                labelTV.text = subcategories[i][0]
                                typeTV.text = subcategories[i][j]
                                found = true
                            }
                        }
                    }

                }
            } while (!found)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectItem(
        TVArray: ArrayList<TextView?>, TV: TextView, SArray: Array<String>, isSubcategory: Boolean
    ) {
        if (TV.text != "-") {
            TV.setOnClickListener {
                prepareLinearLO(TVArray, TV, SArray, isSubcategory)
                mainscreen = false
                myLayout.visibility = VISIBLE
                bgBLur.visibility = VISIBLE
            }
        }

    }

    private fun getLabelID(label: String): Int {
        var labelID = 0
        var found = false
        do {
            for (i in subcategories.indices) {
                for (j in subcategories[i].indices) {
                    if (subcategories[i][j] == label) {
                        labelID = i
                        found = true
                    }
                }
            }
        } while (!found)
        return labelID
    }

    private fun getTypeID(label: String): Int {
        var typeID = 0
        var found = false
        if (label == "-"){
            return 0
        }
        do {
            for (i in subcategories.indices) {
                for (j in 1 until subcategories[i].size) {
                    if (subcategories[i][j] == label) {
                        if (subcategories[i].size==2)
                        {return 0}
                        else{
                        typeID = j}
                        found = true
                    }
                }
            }
        } while (!found)
        return typeID
    }
    
    private fun getIdForPic(labelID : Int,typeID:Int): Int {
        var picID=0
        if (labelID != 0){
        for (i in 0 until  labelID) {
        picID += (subcategories[i].size-1)
        }}
        if (typeID == 0) {return picID}
        else{
        picID+=typeID-1}
        Log.e("PICID",picID.toString())
        return picID
    }


}


