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
import android.widget.*
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.dressme.Home
import com.google.dressme.MainActivity
import com.google.dressme.R
import com.google.dressme.clothesPage.ClothesGridLayout
import com.google.dressme.clothesPage.ClothesMainPage
import com.google.dressme.clothesPage.Clothing
import java.util.*
import kotlin.properties.Delegates


class NewItemPage(
    private var label: String, private var color: Color
) : Fragment() {
    private lateinit var mActivity: MainActivity
    private var mainscreen: Boolean = true
    private lateinit var newPiece: ImageView
    private lateinit var sampleId: Array<Int>

    private lateinit var myLayout: LinearLayout
    private lateinit var bgBLur: FrameLayout

    private lateinit var categories: Array<String>
    private val labelTVArray = kotlin.collections.ArrayList<TextView?>(11)
    private lateinit var labelTV: TextView


    private lateinit var subcategories: Array<Array<String>>
    private var typeTVArray = ArrayList<TextView?>(emptyList())
    private lateinit var typeTV: TextView
    private lateinit var typeArray: Array<String>
    private var selectedType: Int? = null


    private lateinit var colorArrayList: ArrayList<Colors>
    private lateinit var colorRecyclerView: RecyclerView
    private lateinit var colorsAdapter: ColorsAdapter
    private lateinit var colorText: TextView
    private lateinit var dressColor: Color
    private var dressARGBColor by Delegates.notNull<Int>()

    private val lp = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private lateinit var backButton: Button
    private lateinit var doneButton: Button


    companion object {
        var clothesList: MutableList<Clothing> = arrayListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryArray()
        templateArray()


        mActivity = (activity as MainActivity)
        if (mainscreen) {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                mActivity.replaceFragment(ClothesMainPage())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_new_item_page, container, false)
        val bview = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bview.visibility = View.GONE

        bgBLur = view.findViewById(R.id.backgroundBlur)
        myLayout = view.findViewById(R.id.chooseLabel)

        //Buttons
        backButton = view.findViewById(R.id.backButton2)
        doneButton = view.findViewById(R.id.doneButton)


        //Image of the dress
        newPiece = view.findViewById(R.id.dressView)

        //Category
        labelTV = view.findViewById(R.id.label)
        typeTV = view.findViewById(R.id.dressType)
        colorText = view.findViewById(R.id.colorName)


        //OnClickListener a label TextView-ra manuális felülíráshoz
        Log.e("1", "check") //First N
        selectItem(labelTVArray, labelTV, categories, false)

        //Type
        //Ha még nincs felülírt típus, akkor...
        if (selectedType == null) {
            defaultPicSelect(getLabelID(label), getTypeID(label)) //Innen kapja a 4th N
            typeTVArray.ensureCapacity(typeArray.size)
            prepareLinearLO(typeTVArray, typeTV, typeArray, false) //Innen meg a 14th

        }

        selectItem(typeTVArray, typeTV, typeArray, true) //OnClickListener

        selectColor(view) //Color set & OnClicklistener & Recyclerview

        if (mainscreen) {

            backButton.setOnClickListener {
                mActivity.replaceFragment(ClothesMainPage())
            }
            doneButton.setOnClickListener {
                clothesList.add(
                    Clothing(
                        labelTV.text.toString(),
                        typeTV.text.toString(),
                        dressARGBColor,
                        sampleId[getIdForPic(getLabelID(labelTV.text.toString()),getTypeID(typeTV.text.toString()))]
                    )
                )
                Toast.makeText(context, "Item added!", Toast.LENGTH_LONG).show()
                mActivity.replaceFragment(Home())
                val bview =
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                bview.visibility = VISIBLE
            }
        }

        return view

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepareLinearLO(
        pairs: ArrayList<TextView?>,
        myLabel: TextView,
        textArray: Array<String>,
        isSubcategory: Boolean
    ) {
        if (typeArray.size != 1) {
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
                        selectedType = id + 1
                        myLayout.visibility = INVISIBLE
                        bgBLur.visibility = INVISIBLE
                        myLabel.text = this.text
                        if (!isSubcategory) {
                            labelID = id
                            pairs.clear()
                            defaultPicSelect(labelID, 0)
                            typeTV.text = typeArray[0]
                            if (typeArray.size != 1) {
                                selectItem(typeTVArray, typeTV, typeArray, true)
                            }
                        } else {
                            defaultPicSelect(labelID, selectedType!!)
                        }
                    }
                }
                myLayout.addView(pairs[l])


            }
        }
    }

    private fun defaultPicSelect(labelID: Int, typeID: Int): Boolean {
        if (selectedType != null) {

            label = subcategories[labelID][typeID]
        }
        for (i in subcategories.indices) {
            if (subcategories[i].size == 2) {
                if (subcategories[i][0] == label) {
                    typeArray = subcategories[i].copyOfRange(1, (subcategories[i].size))
                    newPiece.setImageResource(sampleId[getIdForPic(labelID, 0)])
                    labelTV.text = subcategories[i][0]
                    typeTV.text = typeArray[0]
                    return true
                }
            } else {
                for (j in 1 until subcategories[i].size) {
                    if (subcategories[i][j] == label) {
                        typeArray = subcategories[i].copyOfRange(1, (subcategories[i].size))
                        newPiece.setImageResource(sampleId[getIdForPic(i, j)])
                        labelTV.text = subcategories[i][0]
                        typeTV.text = subcategories[i][j]
                        return true
                    }
                }
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectItem(
        TVArray: ArrayList<TextView?>, TV: TextView, SArray: Array<String>, isSubcategory: Boolean
    ) {
        Log.e("2", "check") //Second N, Last N    //change17
        TV.setOnClickListener {
            Log.e("3", "check")
            if (TV.text.length > 2) {
                prepareLinearLO(TVArray, TV, SArray, isSubcategory)
                mainscreen = false
                myLayout.visibility = VISIBLE
                bgBLur.visibility = VISIBLE
                bgBLur.setOnClickListener {
                    myLayout.visibility = INVISIBLE
                    bgBLur.visibility = INVISIBLE
                    return@setOnClickListener
                }
            }
        }
    }


    private fun getLabelID(label: String): Int {
        for (i in subcategories.indices) {
            for (j in subcategories[i].indices) {
                if (subcategories[i][j] == label) {
                    return i
                }
            }
        }
        return -1
    }

    private fun getTypeID(label: String): Int {

        if (label == "-") {
            return 0
        }
        for (i in subcategories.indices) {
            if (subcategories[i].size == 2) {
                if (subcategories[i][0] == label) {
                    return 0
                }
            } else {
                for (j in 1 until subcategories[i].size) {
                    if (subcategories[i][j] == label) {
                        return j
                    }
                }
            }
        }
        return -1
    }

    private fun getIdForPic(labelID: Int, typeID: Int): Int {
        var picID = 0
        if (labelID != 0) { //tehát az adott darab nem az első kategóriában van
            for (i in 0 until labelID) {
                picID += (subcategories[i].size - 1)
            }
            if (typeID == 0) {
                return picID
            }
        }
        if (typeID != 0) {
            picID += typeID - 1
        }
        return picID
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
            R.drawable.outerwear_template,
            R.drawable.baseline_person_24
        )
    }

    private fun categoryArray() {

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

    }

    private fun colorListPrep() {

        val colorlist = ColorUtils().initColorList()
        for (i in colorlist.indices) {
            val color = Colors(
                Color.parseColor(
                    "#${
                        Integer.toHexString(
                            Color.rgb(
                                colorlist[i].r, colorlist[i].g, colorlist[i].b
                            )
                        )
                    }"
                )
            )
            colorArrayList.add(color)
        }
        colorsAdapter = ColorsAdapter(colorArrayList)
        colorRecyclerView.adapter = colorsAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectColor(view: View) {
        val colorLayout = view.findViewById<LinearLayout>(R.id.chooseColor)
        dressColor = Color.WHITE.toColor()
        dressARGBColor = color.toArgb()
        var colorName = ColorUtils().getColorNameFromRgb(
            dressARGBColor.red, dressARGBColor.green, dressARGBColor.blue
        )
        val colorListHere = ColorUtils().initColorList()
        for (i in colorListHere.indices) {
            if (colorListHere[i].name == colorName) {
                dressColor =
                    Color.rgb(colorListHere[i].r, colorListHere[i].g, colorListHere[i].b).toColor()
            }
        }
        newPiece.setColorFilter(dressColor.toArgb(), PorterDuff.Mode.MULTIPLY)
        colorText.text = colorName

        colorRecyclerView = view.findViewById<View>(R.id.chooseColorRCV) as RecyclerView
        colorRecyclerView.setHasFixedSize(true)
        colorRecyclerView.layoutManager = GridLayoutManager(context, 6)
        colorArrayList = arrayListOf()
        colorListPrep()

        colorText.setOnClickListener {
            colorLayout?.visibility = VISIBLE
            bgBLur.visibility = VISIBLE
            bgBLur.setOnClickListener {
                colorLayout?.visibility = INVISIBLE
                bgBLur.visibility = INVISIBLE
                return@setOnClickListener
            }

            colorsAdapter.onItemClick = {
                dressARGBColor = Color.parseColor("#${Integer.toHexString(it.color).drop(2)}")
                colorName = ColorUtils().getColorNameFromRgb(
                    dressARGBColor.red, dressARGBColor.green, dressARGBColor.blue
                )
                newPiece.setColorFilter(dressARGBColor, PorterDuff.Mode.MULTIPLY)
                colorText.text = colorName

                colorLayout?.visibility = INVISIBLE
                bgBLur.visibility = INVISIBLE

            }

        }

    }


}


